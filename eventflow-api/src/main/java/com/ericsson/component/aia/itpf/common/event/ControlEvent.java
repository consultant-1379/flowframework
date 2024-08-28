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
package com.ericsson.component.aia.itpf.common.event;

import java.util.HashMap;
import java.util.Map;

import com.ericsson.component.aia.itpf.common.Controllable;
import com.ericsson.component.aia.itpf.common.event.handler.EventHandlerContext;

/**
 * Event used to control behaviour of {@link Controllable} components. Instances of this event can be sent from engine, outside world or from other
 * components in the flow. It is the responsibility of {@link Controllable} instances to agree on exact meaning of control events and data found in
 * it.
 * 
 * <p>
 * Different engines might send control events to some components. It is up to every event handler to decide whether to react on those events and how.
 * Check the documentation of your engine for further information.
 * 
 * @see Controllable
 * @see EventHandlerContext#sendControlEvent(ControlEvent)
 * @author eborziv
 * 
 */
public final class ControlEvent {

    /**
     * Events of this type are sent by engine to all interested components in the flow after flow has been fully initialized. All components in the
     * current flow have been successfully initialized before this event is sent. This is useful when some components in the flow need to know when
     * complete flow has been initialized and started.
     */
    public static final int FLOW_INITIALIZED_TYPE = 10;

    /**
     * Indicates that the flow component configuration is changed. This ControlEvent is sent to the flow component which should change and will
     * contain the configuration details.
     */
    public static final int CONFIGURATION_CHANGED = 11;

    private final int type;
    private final Map<String, Object> data = new HashMap<String, Object>();

    /**
     * Creates new instance of control event.
     * 
     * @param type
     *            event type. It is the responsibility of every application to ensure uniqueness of type. Range 0-100 is reserved for engine-related
     *            control events and events of those type should not be sent by event handlers.
     */
    public ControlEvent(final int type) {
        this.type = type;
    }

    /**
     * Returns the type of event
     * 
     * @return the type of event
     */
    public int getType() {
        return type;
    }

    /**
     * Returns the data for this event. Exact meaning of data has to be agreed between different {@link Controllable} components in the flow.
     * 
     * @return the data for this event. Never null.
     */
    public Map<String, Object> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ControlEvent [type=" + type + ", data=" + data + "]";
    }

}
