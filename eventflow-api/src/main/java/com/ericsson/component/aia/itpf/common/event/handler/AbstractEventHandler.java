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
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.component.aia.itpf.common.config.Configuration;

/**
 * Abstract class providing easier way to create {@link EventHandler} implementations.
 *
 * @see EventHandler
 * @author eborziv
 *
 */
public abstract class AbstractEventHandler implements EventHandler {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private EventHandlerContext eventHandlerContext;

    private volatile boolean isInitialized = false;

    /*
     * here we keep all subscribers in array so that it is fast to access them by position
     */
    private EventSubscriber[] subscriberArray;

    /*
     * Contains number of subscribers attached to this context.
     */
    private int numberOfSubscribers;

    /*
     * If there is only one event subscriber downstream then we optimize access to it, instead of asking for collection of possible subscribers and iterating through it.
     */
    private EventSubscriber onlyEventSubscriber;

    @Override
    public final void init(final EventHandlerContext ctx) {
        if (isInitialized) {
            throw new IllegalStateException("Unable to initialize component twice! Already initialized!");
        }
        if (ctx == null) {
            throw new IllegalArgumentException("Context must not be null");
        }
        if (ctx.getEventHandlerConfiguration() == null) {
            throw new IllegalArgumentException("Configuration must not be null");
        }
        eventHandlerContext = ctx;
        log.debug("Initialized component with context {}", eventHandlerContext);
        // try to optimize access if we have only one subscriber
        final Collection<EventSubscriber> subscribers = eventHandlerContext.getEventSubscribers();
        if (subscribers != null) {
            numberOfSubscribers = subscribers.size();
            subscriberArray = new EventSubscriber[numberOfSubscribers];
            if (numberOfSubscribers == 1) {
                final Iterator<EventSubscriber> iter = subscribers.iterator();
                onlyEventSubscriber = iter.next();
                log.info("Will optimize access to subscribers since there is only one available {}", onlyEventSubscriber);
            } else {
                log.info("Found {} subscribers in total. Unable to optimize access when sending events! Subscribers are {}", numberOfSubscribers, subscribers);
            }
            int i = 0;
            for (final EventSubscriber sub : subscribers) {
                subscriberArray[i] = sub;
                log.debug("Initialized subscriber {} at position {} for fast access", sub, i);
                i++;
            }
            log.debug("Successfully initialized {} subscribers for fast access", numberOfSubscribers);
        } else {
            log.warn("Did not find any subscribers!");
            numberOfSubscribers = 0;
        }
        log.info("Executing doInit() method...");
        this.doInit();
        log.info("Successfully invoked doInit() method!");
        isInitialized = true;
    }

    /**
     * Sends event to subscriber in specified position.
     *
     * @param event
     *            the event to be sent
     * @param subscriberPosition
     *            position of subscriber, starting from 0. Position must be in range (0-total number of subscribers-1)
     */
    protected final void sendEvent(final Object event, final int subscriberPosition) {
        if (subscriberArray == null) {
            throw new IllegalStateException("Subscriber array for fast access was not successfully initialized! Unable to send events to positional subscribers!");
        }
        if (subscriberPosition < 0) {
            throw new IllegalArgumentException("Unable to send event to subscriber at position " + subscriberPosition + ". Position must be non-negative integer!");
        }
        final int maxAllowedPosition = subscriberArray.length - 1;
        if (subscriberPosition > maxAllowedPosition) {
            throw new IllegalArgumentException("Unable to send even to subscriber at position " + subscriberPosition + ". No such subscriber! Only positions in range [0-" + maxAllowedPosition
                    + "] are available!");
        }
        subscriberArray[subscriberPosition].sendEvent(event);
        log.debug("Sent {} to subscriber at position #{}", event, subscriberPosition);
        if (log.isTraceEnabled()) {
            log.trace("Subscriber at position #{} is {}", subscriberPosition, subscriberArray[subscriberPosition]);
        }
    }

    /**
     * Sends event to subscriber identified by specified identifier.
     *
     * @param event
     *            the event to be sent
     * @param subscriberIdentifier
     *            unique identifier of subscriber as specified in flow descriptor. Must not be null or empty.
     * @return true if event was successfully sent or false otherwise (in case when subscriber with given identifier was not found).
     */
    protected final boolean sendEvent(final Object event, final String subscriberIdentifier) {
        if (subscriberIdentifier == null || subscriberIdentifier.trim().length() == 0) {
            throw new IllegalArgumentException("Subscriber identifier must not be null or empty string");
        }
        if (subscriberArray == null) {
            throw new IllegalStateException(
                    "Subscriber array for fast access was not successfully initialized! Unable to send events subscribers by identifier!");
        }
        log.debug("Sending event {} to subscriber with identifier {}", event, subscriberIdentifier);
        boolean subscriberFound = false;
        // later we can see to make this faster (using a map maybe), but since there will be only one or two subscribes I do not see this slowing things down too much
        for (final EventSubscriber es : this.subscriberArray) {
            if (es.getIdentifier().equals(subscriberIdentifier)) {
                es.sendEvent(event);
                log.debug("Successfully sent event {} to subscriber with identifier {}", event, subscriberIdentifier);
                subscriberFound = true;
                break;
            }
        }
        if (!subscriberFound) {
            log.warn("Was not able to find subscriber with identifier {}. Event was NOT send downstream!", subscriberIdentifier);
        }
        return subscriberFound;
    }

    /**
     * Sends specified event to all subscribers.
     *
     * @param inputEvent
     *            the event to be sent downstream to all subscribers of this {@link EventHandler} instance.
     */
    protected final void sendToAllSubscribers(final Object inputEvent) {
        // if only one subscriber available then access it directly and break
        if (onlyEventSubscriber != null) {
            onlyEventSubscriber.sendEvent(inputEvent);
            return;
        }
        if (eventHandlerContext == null) {
            log.error("Context is null but it must not be! Component is initialized={}. This is most likely problem with the initialization of component!", isInitialized);
            return;
        }
        final Collection<EventSubscriber> eventSubscribers = eventHandlerContext.getEventSubscribers();
        if (eventSubscribers == null || eventSubscribers.isEmpty()) {
            log.debug("There are no subscribers attached. Event {} will not be processed", inputEvent);
            return;
        }
        for (final EventSubscriber handler : eventSubscribers) {
            handler.sendEvent(inputEvent);
        }
    }

    /**
     * Returns {@link EventHandlerContext} instance associated with this instance of {@link EventHandler}.
     *
     * @return the eventHandlerContext
     */
    public final EventHandlerContext getEventHandlerContext() {
        return eventHandlerContext;
    }

    /**
     * Invoked only once during initialization of component, but before any event processing. It is safe to access {@link #getEventHandlerContext()} and {@link #getConfiguration()} methods inside this
     * method. This method should not throw any exceptions.
     */
    protected abstract void doInit();

    /**
     * Returns configuration of this instance.
     *
     * @return configuration of this instance
     */
    protected final Configuration getConfiguration() {
        if (eventHandlerContext == null) {
            throw new IllegalStateException("Unable to retrieve configuration when event handler context is null!");
        }
        return eventHandlerContext.getEventHandlerConfiguration();
    }

    protected final int getNumberOfSubscribers() {
        return numberOfSubscribers;
    }

    @Override
    public void destroy() {
        isInitialized = false;
    }
}
