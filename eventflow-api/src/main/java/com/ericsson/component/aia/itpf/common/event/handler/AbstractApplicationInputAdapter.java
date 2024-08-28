package com.ericsson.component.aia.itpf.common.event.handler;

import com.ericsson.component.aia.itpf.common.io.InputAdapter;

/**
 * AbstractApplicationInputAdapter - Application input adapters will extend and implement this
 */
public abstract class AbstractApplicationInputAdapter extends AbstractApplicationEventHandler implements InputAdapter {

    /**
     * This method is should always throw UnsupportedOperationException for an input adapter
     */
    @Override
    public final void inputEvents(final Object events) {
        throw new UnsupportedOperationException(
                "Operation not supported. File input adapter is always entry points on event chain!");
    }

}
