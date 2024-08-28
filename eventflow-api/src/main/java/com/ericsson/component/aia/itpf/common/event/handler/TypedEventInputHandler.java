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

import com.ericsson.component.aia.itpf.common.event.ComponentEvent;
import com.ericsson.component.aia.itpf.common.event.ControlEvent;

/**
 * Input handler that knows how to handle strongly typed events.
 * 
 * @see ComponentEvent
 * @see EventInputHandler
 * @see EventHandlerContext
 * @author eborziv
 * 
 * @deprecated As of release 1.1.7, replaced by {@link ComponentEventInputHandler}. <br>
 *             Events should flow in one direction - downstream. If an event handler needs to respond to event processing downstream then a
 *             {@link ControlEvent} should be used.
 */
@Deprecated
public interface TypedEventInputHandler extends EventHandler {

    /**
     * 
     * @param inputEvent
     *        the input event to be processed
     * @return result that is to be forwarded to the next event handler in chain or null if nothing is to be forwarded.
     */
    ComponentEvent onEvent(ComponentEvent inputEvent);

}
