/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2014
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.component.aia.itpf.common.event.handler;

import com.ericsson.component.aia.itpf.common.Destroyable;

/**
 * All components capable of receiving events and expect result must implement this interface. This is not type safe interface and it is
 * responsibility of every implementation to perform type casting and disregard types it does not know how to process.
 * 
 * @see EventHandler
 * @see EventInputHandler
 * @see Destroyable
 * @author esrigur
 * 
 */
public interface ResultEventInputHandler extends EventInputHandler {

    /**
     * Invoked by flow engine to deliver new even to the current instance. Implementation of this method must be thread safe. Type casting should be
     * performed inside this method. This method should not throw any exceptions. The output from each handler is used as the input in the next
     * handler and output from the last handler is returned.
     * 
     * @param inputEvent
     *            the input event to be processed. Can be null.
     * @return the object expected as the result of the execution.
     * @see EventHandlerContext how to send events
     */
    Object onEventWithResult(Object inputEvent);

}
