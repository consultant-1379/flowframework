package com.ericsson.component.aia.itpf.common.event.handler;

/**
 * AbstractApplicationEventHandler - parser applications will extend and implement this
 */
public abstract class AbstractApplicationEventHandler extends AbstractEventHandler implements EventHandlerService, EventInputHandler {

    /**
     * Invoked only once during initialization but before any event processing.
     */
    @Override
    protected abstract void doInit();

    /**
     * Invoked by flow engine to deliver new events, send to inputEvents
     *
     * @param object - the event(s)
     */
    @Override
    public void onEvent(final Object object) {
        inputEvents( object );
    }

    /**
     * Called internally, this is the point where application implementaitons will receive events
     *
     * @param object - the input event(s)
     */
    protected abstract void inputEvents(final Object object);


    /**
     * Utility method that returns a String representation of a property value.
     *
     * @param propertyName - the property name
     * @return - String representation of property
     */
    protected String getStringProperty(final String propertyName) {
        final String propertyValue = getConfiguration().getStringProperty(propertyName);
        if ((propertyValue == null) || propertyValue.isEmpty()) {
            throw new IllegalStateException(propertyName + " must not be null or empty");
        }
        return propertyValue;
    }

    /**
     * Send the specified event(s) to the next set of subscribers( outputAdapter(s) defined in the flow.xml. )
     *
     * @param outputEvent
     *            the event to send to subscribers
     */
    public void sendEvent(final Object outputEvent) {
        sendToAllSubscribers(outputEvent);
    }

    @Override
    public void destroy() {
        super.destroy();
        destroyAll();
    }

    public abstract void destroyAll();
}
