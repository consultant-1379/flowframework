/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

package com.ericsson.component.aia.itpf.common.modeling.schema.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

import com.ericsson.component.aia.itpf.common.modeling.schema.util.SchemaConstants.EncodingType;

/**
 * Utility for retrieving various JAXB-related artifacts for XSD-based models: Marshallers, Unmarshallers, Validators.
 */
public abstract class ModelHandlingUtil {

    private static final ConcurrentMap<String, JAXBContext> JAXBCONTEXT_MAP = new ConcurrentHashMap<>();

    /**
     * Returns a JAXB Unmarshaller for the specified schema name. The unmarshaller is typically used to read in a model file into a JAXB object tree.
     *
     * @param schemaName
     *            The schema name.
     * @param validating
     *            whether the unmarshaller shall validate the contents during unmarshalling. Note that setting this to TRUE (i.e. enable validation)
     *            will slow down the unmarshalling considerably.
     * @return JAXB unmarshaller for the specified schema name.
     *
     * @throws IllegalArgumentException
     *             if the supplied schema name is unknown.
     */
    public static Unmarshaller getUnmarshaller(final String schemaName, final boolean validating) {

        if (!SchemaUtil.isKnownSchema(schemaName)) {
            throw new IllegalArgumentException("Schema '" + schemaName + "' is not known to the system.");
        }

        Unmarshaller unmarshaller = null;

        try {
            if (!JAXBCONTEXT_MAP.containsKey(schemaName)) {
                final JAXBContext jaxbContext = JAXBContext.newInstance(getJaxbContextPath(schemaName));
                JAXBCONTEXT_MAP.putIfAbsent(schemaName, jaxbContext);
            }

            unmarshaller = JAXBCONTEXT_MAP.get(schemaName).createUnmarshaller();
        } catch (final Exception ex) {
            throw new RuntimeException("Error getting unmarshaller for '" + schemaName + "'.", ex); // NOPMD by eeimhln on 26/04/13 15:10
        }

        if (validating) {
            unmarshaller.setSchema(getSchema(schemaName));
        }

        return unmarshaller;
    }

    /**
     * Returns a JAXB Marshaller for the specified schema name. The marshaller may be used to marshall a tree of JAXB objects into a file. This is
     * typically then required when a model is created / derived on the fly, which now needs to be persisted.
     *
     * @param schemaName
     *            The schema name.
     * @param validating
     *            whether the marshaller shall validate the contents during marshalling.
     * @return JAXB marshaller for the specified schema name.
     * @throws IllegalArgumentException
     *             if the supplied schema name is unknown.
     */
    public static Marshaller getMarshaller(final String schemaName, final boolean validating) {

        if (!SchemaUtil.isKnownSchema(schemaName)) {
            throw new IllegalArgumentException("Schema '" + schemaName + "' is not known to the system.");
        }

        Marshaller marshaller = null;

        try {
            if (!JAXBCONTEXT_MAP.containsKey(schemaName)) {
                final JAXBContext jaxbContext = JAXBContext.newInstance(getJaxbContextPath(schemaName));
                JAXBCONTEXT_MAP.putIfAbsent(schemaName, jaxbContext);
            }

            marshaller = JAXBCONTEXT_MAP.get(schemaName).createMarshaller();
        } catch (final Exception ex) {
            throw new RuntimeException("Error getting marshaller for '" + schemaName + "'.", ex); // NOPMD by eeimhln on 26/04/13 15:10
        }

        if (validating) {
            marshaller.setSchema(getSchema(schemaName));
        }

        return marshaller;
    }

    /**
     * Returns a JAXB Validator that may be used to validate XML against the respective schema as denoted by the supplied schema name.
     *
     * @param schemaName
     *            the schema name.
     * @return JAXB Validator.
     */
    public static Validator getValidator(final String schemaName) {

        /*
         * The validator is different from XSDs to DTDs. For XSDs, this can be done via the standard JAXB Schema object. DTDs must be handled
         * differently.
         */
        if (SchemaUtil.getEncodingType(schemaName) == EncodingType.XSD) {
            return getSchema(schemaName).newValidator();
        }

        throw new RuntimeException("Cannot create a validator for models of schema " + schemaName);
    }

    /**
     * Returns the JAXB Schema Object for a schema. This object may be used for XML validation.
     *
     * @param schemaName
     *            the schema name.
     * @return JAXB Schema
     *
     * @throws IllegalArgumentException
     *             if the supplied schema name is unknown.
     */
    private static Schema getSchema(final String schemaName) {

        if (!SchemaUtil.isKnownSchema(schemaName)) {
            throw new IllegalArgumentException("Schema '" + schemaName + "' is not known to the system.");
        }

        /*
         * Get the model dependencies first.
         */
        final Collection<String> dependencies = SchemaUtil.getDependenciesFor(schemaName);

        /*
         * Build up a number of Source objects to help with XML schema validation. The source objects contain information for the XML implementation
         * in respect of the location of the XSDs, which are located inside the schemata JAR (which is on the classpath). The XSD for the actual
         * schema, and all dependencies, is added as Stream source.
         */
        final List<StreamSource> sources = new ArrayList<>();

        final StreamSource schemaSource = new StreamSource(ModelHandlingUtil.class.getResourceAsStream(SchemaUtil.getResourceLocation(schemaName)),
                            SchemaUtil.getSystemId(schemaName));
        sources.add(schemaSource);

        for (final String dependency : dependencies) {
            final StreamSource dependencySource =
                                new StreamSource(ModelHandlingUtil.class.getResourceAsStream(SchemaUtil.getResourceLocation(dependency)),
                                                    SchemaUtil.getSystemId(dependency));
            sources.add(dependencySource);
        }
        final StreamSource[] streamSources = sources.toArray(new StreamSource[sources.size()]);

        /*
         * Create Factory and Schema object.
         */

        try {
            SchemaFactory schemaFactory = null;

            if (SchemaUtil.getEncodingType(schemaName) == EncodingType.XSD) {
                schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            } else {
                throw new RuntimeException("JAXB Schema's not supported for models of schema '" + schemaName
                                    + "'. This method should not have been called.");
            }

            /*
             * The Resource Resolver is required to handle the import statements inside XSDs. When a schema (= XSD) is parsed, the dependencies on
             * other schemas must be resolved. This is what happens here. In effect, what happens is a reverse-mapping from the schema System ID to
             * the schema name. Once this is established, the other schema simply has be streamed in. This is what the LSHelper is for.
             */
            schemaFactory.setResourceResolver(new LSResourceResolver() {
                @Override
                public LSInput resolveResource(final String type, final String namespaceURI, final String publicId, final String systemId,
                                    final String baseURI) {

                    final String schemaName = SchemaUtil.getSchemaNameFromSystemId(systemId);

                    final InputStream resourceAsStream = this.getClass().getResourceAsStream(SchemaUtil.getResourceLocation(schemaName));
                    final LSInputHelper helper = new LSInputHelper(publicId, systemId, resourceAsStream);
                    return helper;
                }
            });
            final Schema schema = schemaFactory.newSchema(streamSources);

            return schema;
        } catch (final Exception ex) {
            throw new RuntimeException("Error getting schema for '" + schemaName + "'.", ex); // NOPMD by eeimhln on 26/04/13 15:11
        }
    }

    /**
     * Returns the JAXB context path for a schema.
     *
     * @param schemaName
     *            The name of the schema name.
     *
     * @return JAXB context path String.
     */
    public static String getJaxbContextPath(final String schemaName) {
        /*
         * The context is a concatenation of java package names containing the JAXB classes. Since the XSDs usually have dependencies, these also need
         * to be resolved.
         */
        final StringBuilder contextPath = new StringBuilder();
        contextPath.append(SchemaUtil.getJavaPackageFor(schemaName));

        final Collection<String> dependencies = SchemaUtil.getDependenciesFor(schemaName);
        for (final String dependency : dependencies) {
            contextPath.append(':');
            contextPath.append(SchemaUtil.getJavaPackageFor(dependency));
        }

        return contextPath.toString();
    }

}
