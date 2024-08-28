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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.w3c.dom.ls.LSInput;

/**
 * Utility to help with XML validation. It is required in order to find the XSDs within the schemata JAR. For more information, see this post here:
 * http://stackoverflow.com/questions/1560422/how-to-validate-against-schema-in-jaxb-2-0-without-marshalling
 *
 */
class LSInputHelper implements LSInput {

    private String systemId;
    private String publicId;
    private final BufferedInputStream inputStream;

    /**
     * Utility to help with XML validation. It is required in order to find the XSDs within the schemata JAR. For more information, see this post
     * here:http://stackoverflow.com/questions/1560422/how-to-validate-against-schema-in-jaxb-2-0-without-marshalling
     *
     * @param publicId
     *            String
     * @param systemId
     *            String
     * @param resourceAsStream
     *            InputStream
     */
    LSInputHelper(final String publicId, final String systemId, final InputStream resourceAsStream) {
        this.publicId = publicId;
        this.systemId = systemId;
        this.inputStream = new BufferedInputStream(resourceAsStream);
    }

    @Override
    public String getSystemId() {
        return systemId;
    }

    @Override
    public void setSystemId(final String systemId) {
        this.systemId = systemId;
    }

    @Override
    public String getPublicId() {
        return publicId;
    }

    @Override
    public void setPublicId(final String publicId) {
        this.publicId = publicId;
    }

    @Override
    public String getBaseURI() {
        return null;
    }

    @Override
    public InputStream getByteStream() {
        return null;
    }

    @Override
    public boolean getCertifiedText() {
        return false;
    }

    @Override
    public Reader getCharacterStream() {
        return null;
    }

    @Override
    public String getEncoding() {
        return null;
    }

    @Override
    public String getStringData() {
        synchronized (inputStream) {
            try {
                final byte[] input = new byte[inputStream.available()];
                inputStream.read(input);
                final String contents = new String(input);
                return contents;
            } catch (final IOException ex) {
                throw new RuntimeException("There has been a problem reading the XSD.", ex); // NOPMD
            }
        }
    }

    @Override
    public void setBaseURI(final String baseURI) {
    }

    @Override
    public void setByteStream(final InputStream byteStream) {
    }

    @Override
    public void setCertifiedText(final boolean certifiedText) {
    }

    @Override
    public void setCharacterStream(final Reader characterStream) {
    }

    @Override
    public void setEncoding(final String encoding) {
    }

    @Override
    public void setStringData(final String stringData) {
    }

}
