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

import java.util.Collection;

import com.ericsson.component.aia.itpf.common.Controllable;
import com.ericsson.component.aia.itpf.common.config.Configuration;
import com.ericsson.component.aia.itpf.common.event.ControlEvent;

/**
 * Context passed to every event handler. Instances of this interface are thread-safe and every component in the flow has its own unique instance of this interface. Instances of this interface are
 * created by flow engine and passed to all {@link EventHandler} components during initialization phase
 *
 * @see EventHandler
 * @see EventHandler#init(EventHandlerContext)
 * @author eborziv
 *
 */
public interface EventHandlerContext {

    /**
     * Returns configuration for this specific event handler instance.
     *
     * @return configuration for this specific event handler. Never returns null.
     */
    Configuration getEventHandlerConfiguration();

    /**
     * For a current {@link EventHandler} returns all subscribers as defined by flow definition. Returned collection is immutable. Subscribers of
     * every handler are handlers or IO adapters directly attached to it. Every handler can send events only to its subscribers. Flow descriptor
     * connects all handlers and thus defines subscribers for every handler.
     *
     * @see EventSubscriber
     * @return a collection of subscribers or empty collection if no one is subscribed to the current {@link EventHandler} instance.
     */
    Collection<EventSubscriber> getEventSubscribers();

    /**
     * Sends control event to all {@link Controllable} components in the current flow. It is the responsibility of engine to forward sent event to all {@link Controllable} components in the current
     * flow. It is up to specific {@link Controllable} components to handle different {@link ControlEvent} instances correctly.
     * <p>
     * Be careful when invoking this method during <tt>init</tt> phase as not all components in the flow might be ready to react to control events before being initialized properly.
     *
     * @param controlEvent
     *            the event to be sent. Must not be null. Must have unique type.
     */
    void sendControlEvent(ControlEvent controlEvent);

    /**
     * {@link EventHandler} components can access contextual data by name. This can be used for engine specific data. Check engine specific
     * documentation to see what is exposed.
     *
     * @param name
     *            the name of contextual data. Must not be null or empty string.
     * @return the value associated with specified name. Can be null. Check engine specific documentation for more information.
     */
    Object getContextualData(String name);

}