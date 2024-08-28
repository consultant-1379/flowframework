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

/**
 * Instance of {@link EventInputHandler} that will be receiving events from current handler. Used to pass events downstream. Every {@link EventInputHandler} is wrapped in this object before being sent
 * to components in the flow. This is because only {@link EventInputHandler} instances in the flow know their identity and configuration.
 *
 * @author eborziv
 * @see EventHandlerContext#getEventSubscribers()
 *
 */
public interface EventSubscriber {

    /**
     * Returns unique identifier of this subscriber. Identifier is specified in flow xml descriptor. Identifier can be used to conditionally send events downstream only to specific subscribers.
     *
     * @return the unique identifier
     * @see AbstractEventHandler#sendEvent(Object, String)
     */
    String getIdentifier();

    /**
     * Sends event to this event subscriber.
     *
     * @param event
     *            the event to be sent downstream for processing. Can be null.
     */
    void sendEvent(Object event);

}
