/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2017
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

package com.ericsson.component.aia.itpf.common.modeling.schema.util;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.*;

import com.ericsson.component.aia.itpf.common.modeling.schema.exceptions.UnableToCreateMarshallerException;

/**
 * Utility for retrieving various JAXB-related artifacts for DTD-based models: Marshallers, XmlReader.
 */
public class DtdModelHandlingUtil {

    private DtdModelHandlingUtil() {

    }

    /**
     * Returns a JAXB Unmarshaller for the specified schema name. The unmarshaller is typically used to read in a model file into a JAXB object tree.
     * Note for DTD-based models the marshaller is not good enough, the XmlReader must be used as well. See the example shown for the
     * {@link #getXmlReader(boolean)} method.
     *
     * @param schemaName
     *            The schema name.
     * @return JAXB unmarshaller for the specified schema name.
     *
     * @throws IllegalArgumentException
     *             if the supplied schema name is unknown.
     */
    public static Unmarshaller getUnmarshaller(final String schemaName) {

        if (!SchemaUtil.isKnownSchema(schemaName)) {
            throw new IllegalArgumentException("Schema '" + schemaName + "' is not known to the system.");
        }

        Unmarshaller unmarshaller = null;

        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(ModelHandlingUtil.getJaxbContextPath(schemaName));
            unmarshaller = jaxbContext.createUnmarshaller();
        } catch (final Exception ex) {
            throw new UnableToCreateMarshallerException("Error getting unmarshaller for '" + schemaName + "'.", ex);
        }

        return unmarshaller;
    }

    /**
     * Returns an XML Reader, which is optionally validating the XML contents as it is being read. The XML Reader, together with the Unmarshaller,
     * must be used when unmarshalling XML that is DTD-based. Coding example:
     * <p>
     *
     * <code>
     * Unmarshaller unmarshaller = DtdModelHandlingUtil.getUnmarshaller("oss_eventtype");<br/>
     * XMLReader xmlReader = DtdModelHandlingUtil.getXmlReader(true);<br/>
     * InputSource inSrc = new InputSource(...);<br/>
     * SAXSource saxSource = new SAXSource(xmlReader, inSrc);<br/>
     * Object rootElement = unmarshaller.unmarshal(saxSource);<br/>
     * </code>
     * <p>
     * The XMLReader can also be used for validation purposes. For this, instead of pulling in the objects via JAXB, the XML contents are simply
     * streamed in. An unmarshaller is not required. Code example:
     * <p>
     *
     * <code>
     * XMLReader xmlReader = DtdModelHandlingUtil.getXmlReader(true);<br/>
     * InputSource inSrc = new InputSource(...);<br/>
     * xmlReader.parse(inSrc);<br/>
     * </code>
     *
     * If so desired, a custom error-handler may be attached to the XML Reader by client code in order to get fine-grained information about
     * validation warnings and errors.
     *
     * @param validating
     *            whether the XMLReader shall validate the contents during reading. Note that setting this to TRUE (i.e. enabling validation) will
     *            slow down the unmarshalling considerably.
     * @return XML reader to use with unmarshaller.
     */
    public static XMLReader getXmlReader(final boolean validating) {

        try {
            final SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            if (validating) {
                parserFactory.setNamespaceAware(true);
                parserFactory.setValidating(true);
            }

            final SAXParser saxParser = parserFactory.newSAXParser();
            final XMLReader xmlReader = saxParser.getXMLReader();

            /*
             * The Entity Resolver takes care of fetching the DTD as advised inside the XML file. At the top of the XML file there should be a
             * processing directive in respect to the DOCTYPE with the system ID of the DTD schema.
             */
            xmlReader.setEntityResolver(new EntityResolver() {
                @Override
                public InputSource resolveEntity(final String publicId, final String systemId) throws SAXException, IOException {
                    final String soughtSchemaName = SchemaUtil.getSchemaNameFromSystemId(systemId);
                    if (soughtSchemaName != null) {
                        return new InputSource(this.getClass().getResourceAsStream(SchemaUtil.getResourceLocation(soughtSchemaName)));
                    } else {
                        return null;
                    }
                }
            });

            return xmlReader;

        } catch (final Exception ex) {
            throw new UnableToCreateMarshallerException("Error getting SAX reader", ex);
        }
    }

}
