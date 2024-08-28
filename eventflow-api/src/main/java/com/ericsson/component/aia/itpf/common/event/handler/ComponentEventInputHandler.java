/*------------------------------------------------------------------------------
 ********************************************************************************
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

import com.ericsson.component.aia.itpf.common.event.ComponentEvent;

/**
 * Input handler that knows how to handle strongly typed events.
 * 
 * @see EventHandler
 * @see ComponentEvent
 * @see EventHandlerContext
 * @author ejanjoj
 *
 */
public interface ComponentEventInputHandler extends EventHandler {

    /**
     * Invoked by flow engine to deliver new event to the current instance. <br>
     * <br>
     * Implementations of this method must be thread safe. This method should not throw any exceptions. <br>
     * <br>
     * To send the event downstream (to the next handler in the flow), fetch the subscribers using {@link EventHandlerContext#getEventSubscribers()}.
     * 
     * @param inputEvent
     *        the input event to be processed
     */
    void onEvent(ComponentEvent inputEvent);
}
