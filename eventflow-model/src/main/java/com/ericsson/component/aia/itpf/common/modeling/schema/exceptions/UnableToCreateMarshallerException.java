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
package com.ericsson.component.aia.itpf.common.modeling.schema.exceptions;

/**
 * Exception thrown when no unmarshaller can be provided.
 *
 */
public class UnableToCreateMarshallerException extends RuntimeException {

    private static final long serialVersionUID = -5806937633264596193L;

    /**
     * Exception thrown when no unmarshaller can be provided.
     *
     * @param message
     *            The exception message.
     *
     * @param cause
     *            The exception which caused the failure.
     */
    public UnableToCreateMarshallerException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
