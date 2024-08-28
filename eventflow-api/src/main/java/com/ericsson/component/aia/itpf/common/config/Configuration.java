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
package com.ericsson.component.aia.itpf.common.config;

import java.util.Map;

import com.ericsson.component.aia.itpf.common.event.handler.EventHandler;
import com.ericsson.component.aia.itpf.common.event.handler.EventHandlerContext;

/**
 * Configuration for event handler components. Configuration is read from flow xml descriptor and passed to all event handlers during initialization phase.
 *
 * @see EventHandler
 * @see EventHandlerContext
 * @author eborziv
 *
 */
public interface Configuration {

    /**
     * Returns configuration property value as integer (if it can be parsed into integer). This method can return null.
     *
     * @param propertyName
     *            the name of configuration property. Must not be null or empty string.
     * @return integer value of configuration with given property name or null if none could be found.
     * @throws NumberFormatException
     *             in case when property value was specified but could not be converted to Integer value
     */
    Integer getIntProperty(String propertyName);

    /**
     * Returns configuration property value as string.
     *
     * @param propertyName
     *            the name of configuration property. Must not be null or empty string.
     * @return string value of configuration with given property name or null if none could be found.
     */
    String getStringProperty(String propertyName);

    /**
     * Returns configuration property value as boolean. This method can return null.
     *
     * @param propertyName
     *            the name of configuration property. Must not be null or empty string.
     * @return boolean value of configuration with given property name or null if none could be found.
     */
    Boolean getBooleanProperty(String propertyName);

    /**
     * Return immutable map of all configuration properties available to this instance.
     *
     * @return immutable {@link Map} of all available configuration properties.
     */
    Map<String, Object> getAllProperties();

}
