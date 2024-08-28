/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2015
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

package com.ericsson.component.aia.itpf.common.modeling.schema.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import com.ericsson.component.aia.itpf.common.modeling.flow.schema.gen.oss_common.EModelDefinition;
import com.ericsson.component.aia.itpf.common.modeling.schema.util.SchemaConstants.EncodingType;

/**
 * <p>
 * Utility that contains information about all available schemata, their dependencies and various other properties. In addition to the names of
 * schemas, this class also contains various helper methods that return JAXB and XSD-related information about the various schemata.
 * </p>
 */
public abstract class SchemaUtil {
    /*
     * A map containing the details of all available schemata. This map <b>must</b> be updated every time a new schema is created, or the dependencies
     * of an existing schema change. Failure to do so will lead to deployment and runtime errors. To update the map, add to the implementation of
     * SchemaDetailProvider.
     */
    private static final Map<String, SchemaDetail> details = new HashMap<>();

    /*
     * Static initializer block for the Map using ServiceLoader to fetch information about all schemata in the system.
     */
    static {
        /*
         * Via the Java ServiceLoader, we fetch all implementations that can give us information about the supported schemata. We then simply store
         * these schema details inside the map.
         */
        /*
         * final ServiceLoader<SchemaDetailProvider> serviceLoader = ServiceLoader.load(SchemaDetailProvider.class); final
         * Iterator<SchemaDetailProvider> iterator = serviceLoader.iterator(); while (iterator.hasNext()) { final SchemaDetailProvider detailProvider
         * = iterator.next(); final Collection<SchemaDetail> schemaDetails = detailProvider.getSchemaDetails(); for (final SchemaDetail schemaDetail :
         * schemaDetails) { details.put(schemaDetail.getSchemaName(), schemaDetail); } }
         */
        final FlowSchemaDetailProvider flowProvider = new FlowSchemaDetailProvider();
        for (final SchemaDetail schemaDetail : flowProvider.getSchemaDetails()) {
            details.put(schemaDetail.getSchemaName(), schemaDetail);
        }

    }

    /**
     * This is a temporary test method for the express purpose of testing the Service Loader in the JBOSS environment. It is not to be used by
     * services.
     *
     * @return Collection<String>
     */
    public static Collection<String> nonPublicDoNotUseServiceLoaderTest() {

        final List<String> result = new ArrayList<>();

        final ServiceLoader<SchemaDetailProvider> serviceLoader = ServiceLoader.load(SchemaDetailProvider.class);
        final Iterator<SchemaDetailProvider> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            final SchemaDetailProvider detailProvider = iterator.next();
            final Collection<SchemaDetail> schemaDetails = detailProvider.getSchemaDetails();
            for (final SchemaDetail schemaDetail : schemaDetails) {
                result.add(schemaDetail.getSchemaName());
            }
        }

        return result;
    }

    /**
     * <p>
     * Returns a (unmodifiable) collection containing the names of all schemata known.
     * </p>
     *
     * @return Collection of all schemata known.
     */
    public static Collection<String> getKnownSchemata() {
        return Collections.unmodifiableSet(details.keySet());
    }

    /**
     * <p>
     * Returns whether the supplied schema is known.
     * </p>
     *
     * @param schemaName
     *            The name of the schema
     * @return TRUE if the schema is known, FALSE otherwise.
     */
    public static boolean isKnownSchema(final String schemaName) {
        if (schemaName == null) {
            throw new NullPointerException(); // NOPMD by eeimhln on 15/04/13 11:07
        }

        return details.containsKey(schemaName);
    }

    /**
     * <p>
     * Returns whether the supplied schema is an <i>eModel</i> schema.
     * </p>
     *
     * @param schemaName
     *            The name of the schema
     * @return TRUE if the schema is an <i>eModel</i> schema, FALSE otherwise.
     */
    public static boolean isEModelSchema(final String schemaName) {
        final SchemaDetail schemaDetail = details.get(schemaName);
        final Class<?> rootJavaType = schemaDetail.getRootJavaType();
        if (rootJavaType == null) {
            return false;
        }
        return EModelDefinition.class.isAssignableFrom(rootJavaType);
    }

    /**
     * <p>
     * Returns the names of the schemata on which the supplied schema depends. Transitive dependencies will also be returned.
     * </p>
     * <p>
     * Not every schema has dependencies; hence, the returned collection may be empty.
     * </p>
     *
     * @param schemaName
     *            The schema name, for example "oss_eventtype".
     * @return The names of the schemas that the supplied schema depends on. Never null, but the returned collection may be empty.
     * @throws IllegalArgumentException
     *             if the supplied schema is not known.
     */
    public static Collection<String> getDependenciesFor(final String schemaName) {
        verifySchemaName(schemaName);
        return Collections.unmodifiableList(Arrays.asList(details.get(schemaName).getDependsOnSchemaNames()));
    }

    /**
     * Returns the name of the Java class used as root element for the specified model.
     *
     * @param schemaName
     *            The schema name
     * @return the name of the Java class used as root element for the specified model. Will be <code>null</code> if the Java root type name does not
     *         have a value defined.
     * @throws IllegalArgumentException
     *             if the supplied schema is not known.
     */
    public static String getRootJavaTypeName(final String schemaName) {
        verifySchemaName(schemaName);
        return details.get(schemaName).getRootJavaTypeName();
    }

    /**
     * <p>
     * Returns whether models of the supplied schema support namespaces.
     * </p>
     *
     * @param schemaName
     *            The schema name, for example "oss_eventtype".
     * @return whether the supplied schema supports namespaces
     * @throws IllegalArgumentException
     *             if the supplied schema is not known.
     */
    public static boolean isNamespaceSupportedFor(final String schemaName) {
        verifySchemaName(schemaName);
        return details.get(schemaName).doesSupportNamespace();
    }

    /**
     * <p>
     * Returns whether the models of the supplied schema are versioned
     * </p>
     *
     * @param schemaName
     *            The schema name, for example "oss_eventtype".
     * @return whether the models of the supplied schema are versioned
     * @throws IllegalArgumentException
     *             if the supplied schema is not known.
     */
    public static boolean areModelsVersionedFor(final String schemaName) {
        verifySchemaName(schemaName);
        return details.get(schemaName).areModelsVersioned();
    }

    /**
     * <p>
     * Returns the XSD namespace URI for the supplied schema.
     * </p>
     *
     * @param schemaName
     *            The schema name, for example "oss_eventtype".
     * @return Schema namespace URI for the schema. Will be <code>null</code> if the XSD namespace does not have a value defined.
     *
     * @throws IllegalArgumentException
     *             if the supplied schema is not known.
     */
    public static String getXsdNamespaceUriFor(final String schemaName) {
        verifySchemaName(schemaName);
        return details.get(schemaName).getXsdNamespace();
    }

    /**
     * <p>
     * Returns the Java package name containing the generated JAXB classes for the specified schema name.
     * </p>
     *
     * @param schemaName
     *            The schema name, for example "oss_eventtype".
     * @return Name of Java package containing the generated JAXB classes for the type of model. Will be <code>null</code> if the Java package name
     *         does not have a value defined.
     * @throws IllegalArgumentException
     *             if the supplied schema is not known.
     */
    public static String getJavaPackageFor(final String schemaName) {
        verifySchemaName(schemaName);
        return details.get(schemaName).getJavaPackageName();
    }

    /**
     * <p>
     * Returns the location of the XSD file within the JAR for the specified schema name.
     * </p>
     *
     * @param schemaName
     *            The schema name, for example "oss_eventtype".
     * @return Absolute path to XSD file resource. Will be <code>null</code> if the resource location does not have a value defined.
     * @throws IllegalArgumentException
     *             if the URI has an unexpected value or if the supplied schema is not known.
     */
    public static String getResourceLocation(final String schemaName) {
        verifySchemaName(schemaName);
        return details.get(schemaName).getResourceLocation();
    }

    /**
     * Returns the schema name for a given XSD namespace. This may be beneficial when a client reads in an XML file without knowing what kind of model
     * it is dealing with.
     *
     * @param xsdNamespace
     *            The XSD namespace
     * @return the schema name
     * @throws IllegalArgumentException
     *             if the XSD namespace is unknown.
     */
    public static String getSchemaNameFromXsdNamespace(final String xsdNamespace) {
        if (xsdNamespace == null) {
            throw new NullPointerException(); // NOPMD by eeimhln on 15/04/13 11:07
        }

        for (final SchemaDetail detail : details.values()) {
            if (detail.getXsdNamespace() != null && detail.getXsdNamespace().equals(xsdNamespace)) {
                return detail.getSchemaName();
            }
        }

        throw new IllegalArgumentException("XSD namespace '" + xsdNamespace + "' is not known to the system.");
    }

    /**
     * Returns the systemId of the supplied schema.
     *
     * @param schemaName
     *            The schema name, for example "oss_eventtype".
     * @return The XML systemId. Will be <code>null</code> if the system ID does not have a value defined.
     *
     * @throws IllegalArgumentException
     *             if the supplied schema is not known.
     */
    public static String getSystemId(final String schemaName) {
        verifySchemaName(schemaName);
        return details.get(schemaName).getSystemId();
    }

    /**
     * Returns whether the supplied schema name denotes an extension model.
     *
     * @param schemaName
     *            The schema name, for example "oss_eventtype".
     * @return TRUE if the schema is an extension type, FALSE otherwise.
     *
     * @throws IllegalArgumentException
     *             if the supplied schema is not known.
     */
    public static boolean isExtension(final String schemaName) {
        verifySchemaName(schemaName);
        return details.get(schemaName).isExtension();
    }

    /**
     * Returns the encoding type of the supplied schema.
     *
     * @param schemaName
     *            The schema name, for example "oss_eventtype".
     * @return The encoding type of the schema
     *
     * @throws IllegalArgumentException
     *             if the supplied schema is not known.
     */
    public static EncodingType getEncodingType(final String schemaName) {
        verifySchemaName(schemaName);
        return details.get(schemaName).getEncodingType();
    }

    /**
     * <p>
     * Returns whether the models of the supplied schema allow user created models. Will return false for models of types that do not support user
     * created models being supplied.
     * </p>
     *
     * @param schemaName
     *            Name of the schema to check if user created models are allowed (for example, "pfm_kpi").
     * @return true if user created models are allowed, otherwise false
     * @throws IllegalArgumentException
     *             if the supplied schema is not known.
     */
    public static boolean isUserCreatedAllowed(final String schemaName) {
        verifySchemaName(schemaName);
        return details.get(schemaName).isUserCreatedAllowed();
    }

    /**
     * <p>
     * Returns whether the models of the supplied schema use X.Y.Z format for versioning. Will return false for models of types that do not support
     * versioning.
     * </p>
     *
     * @param schemaName
     *            Name of the schema to get the version format for (for example, "oss_eventtype").
     * @return whether the models of the supplied schema use X.Y.Z format for versioning
     * @throws IllegalArgumentException
     *             if the supplied schema is not known.
     */
    public static boolean areVersionsInXyzFormat(final String schemaName) {
        verifySchemaName(schemaName);
        return details.get(schemaName).areVersionsInXyzFormat();
    }

    /**
     * Returns the schema name for the supplied systemId
     *
     * @param systemId
     *            The systemId
     * @return The schema name
     *
     * @throws IllegalArgumentException
     *             if the system ID is unknown.
     */
    public static String getSchemaNameFromSystemId(final String systemId) {
        if (systemId == null) {
            throw new NullPointerException(); // NOPMD by eeimhln on 15/04/13 11:07
        }

        for (final SchemaDetail detail : details.values()) {
            if (detail.getSystemId() != null && systemId.endsWith(detail.getSystemId())) {
                return detail.getSchemaName();
            }
        }

        throw new IllegalArgumentException("System ID '" + systemId + "' is not known to the system.");
    }

    /**
     * Given the supplied model extension schema, returns the schema name of the schema extended by it.
     *
     * @param extensionSchemaName
     *            Name of schema that is used to extend models of other schemata
     * @return the name of the schema extended by the supplied schema.
     *
     * @throws IllegalArgumentException
     *             if the supplied schema is not known or is not an extension type.
     */
    public static String getExtendedSchemaName(final String extensionSchemaName) {

        if (!isExtension(extensionSchemaName)) {
            throw new IllegalArgumentException("Supplied schema '" + extensionSchemaName + "'is not an extension schema.");
        }

        /*
         * This is a bit loose: We simply cut off the "_EXT" from the end of the schema. Works of course only so long as that extensions always get an
         * _EXT at the end...
         */
        return extensionSchemaName.substring(0, extensionSchemaName.length() - 4);
    }

    /**
     * Utility to verify input parameter
     */
    private static void verifySchemaName(final String schemaName) {
        if (schemaName == null) {
            throw new NullPointerException(); // NOPMD by eeimhln on 15/04/13 11:07
        }

        if (!details.containsKey(schemaName)) {
            throw new IllegalArgumentException("Schema '" + schemaName + "' is not known to the system.");
        }
    }

}
