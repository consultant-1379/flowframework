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
package com.ericsson.component.aia.itpf.common.io;

import java.util.ServiceLoader;

import com.ericsson.component.aia.itpf.common.event.handler.EventInputHandler;

/**
 * All output adapters must implement this interface. Output adapter is usually last component in the flow capable of sending already processed event(s) to components external to the flow. All
 * implementations of this interface must be valid <a href="http://docs.oracle.com/javase/7/docs/api/java/util/ServiceLoader.html">SPI</a> implementations.
 * 
 * @see Adapter
 * @see EventInputHandler
 * @see ServiceLoader
 * @author eborziv
 * 
 */
public interface OutputAdapter extends Adapter, EventInputHandler {

}
