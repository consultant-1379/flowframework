package com.ericsson.component.aia.itpf.common;

import com.ericsson.component.aia.itpf.common.event.ControlEvent;
import com.ericsson.component.aia.itpf.common.event.handler.EventHandler;

/**
 * A Monitorable flow component provides status information about itself.
 * <p>
 * The status will be monitored by an observer and the reported status may trigger action(s) if conditions are met (e.g. if load is too high then add
 * capacity and redistribute load). {@link Controllable#react(ControlEvent)} can be used to make a change in a monitored flow component.
 * <p>
 * It is introduced for adaptive deployment.
 * <p>
 * <ol>
 * <li>
 * <b>Example 1</b>: <br>
 * A Monitorable flow component monitors events processed per second. This is collected and distributed to the observers. If the {@link EventHandler}
 * status exceeds a solution defined threshold, then a new flow instance could be activated to share the load. The flow instances are configured for
 * the desired load distribution.</li><br>
 * <li>
 * <b>Example 2</b>: <br>
 * The observer determines that the flow instance is dead and activates a replacement.</li>
 * </ol>
 * 
 * @since 1.0.338
 */
public interface Monitorable extends Controllable {

    /**
     * Returns the current status for monitoring.
     * <p>
     * This will be collected by the processing engine and distributed to the observers.
     * <p>
     * The status information provided is solution specific (examples are measurements such as "number of events per second" in the last minute, or
     * the "number of files processed" for a given time period). <br>
     * It is the responsibility of Monitorable instances and their observers to agree on the format and content of the status object returned.
     * <p>
     * Must never throw and exception.
     * <p>
     * 
     * @return Object current status information. Must not be null.
     * */
    public Object getStatus();

}
