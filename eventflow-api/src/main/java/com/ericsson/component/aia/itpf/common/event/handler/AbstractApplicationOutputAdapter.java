package com.ericsson.component.aia.itpf.common.event.handler;

import com.ericsson.component.aia.itpf.common.io.InputAdapter;

/**
 * AbstractApplicationOutputAdapter - Application output adapters will extend and implement this
 */
public abstract class AbstractApplicationOutputAdapter extends AbstractApplicationEventHandler implements InputAdapter {

    /**
     * Output adapters should always throw UnsupportedOperationException here as there wont be any additional subscribers
     * in the pipeline
     *
     * @param event
     *            the event to send to subscribers
     */
    public final void sendEvent(final Object event) {
        throw new UnsupportedOperationException(
                "Operation not supported. File input adapter is always entry points on event chain!");
    }

}
