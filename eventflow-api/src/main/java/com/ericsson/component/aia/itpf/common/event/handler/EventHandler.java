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
package com.ericsson.component.aia.itpf.common.event.handler;

import com.ericsson.component.aia.itpf.common.Destroyable;
import com.ericsson.component.aia.itpf.common.event.ComponentEvent;

/**
 * Interface that is to be implemented by any handler capable of sending events to other event handlers.
 *
 * @see EventInputHandler
 * @see ComponentEvent
 * @author eborziv
 *
 */
public interface EventHandler extends Destroyable {

    /**
     * Invoked after event handler instance creation but before sending any event to that specific event handler instance. This method is invoked by flow engine. Flow engine will invoke this method
     * only once. In case this method throws exception then exception then implementation of flow engine decided what to do.
     *
     * @param ctx
     *            non-null event handler context instance
     */
    void init(EventHandlerContext ctx);

}
