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

import com.ericsson.component.aia.itpf.common.Controllable;

/**
 * All components capable of receiving events must implement this interface. This is not type safe interface and it is responsibility of every implementation to perform type casting and disregard
 * events types it does not know how to process.
 *
 * @see EventHandler
 * @see Controllable
 * @author ecapati
 *
 */
public interface EventInputHandler extends EventHandler {

    /**
     * Invoked by flow engine to deliver new event to the current instance of event handler. Implementation of this method must be thread safe (but not synchronized). Type casting of received event
     * should be performed inside this method. This method should not throw any exceptions.
     *
     * @param inputEvent
     *            the input event to be processed. Can be null.
     * @see EventHandlerContext how to send events to other handlers
     */
    void onEvent(Object inputEvent);

}
