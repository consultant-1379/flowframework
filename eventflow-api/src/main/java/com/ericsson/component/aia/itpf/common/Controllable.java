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
package com.ericsson.component.aia.itpf.common;

import com.ericsson.component.aia.itpf.common.event.ControlEvent;

/**
 * Interface for all components that are able to react on {@link ControlEvent}.
 * Whenever {@link ControlEvent} instance is received for particular flow engine
 * will try to deliver it to all {@link Controllable} instances in that flow.
 * <p>
 * {@link ControlEvent} instances can be sent by flow handlers to other flow
 * handlers, by engine or by any third party component (through engine).
 * <p>
 * It is the responsibility of every flow handler to react only to
 * {@link ControlEvent} instances it is interested in and to discard all the
 * others.
 * 
 * @author eborziv
 * 
 */
public interface Controllable {

	/**
	 * Invoked by engine whenever {@link ControlEvent} is to be delivered to
	 * component. This method must never throw exception.
	 * 
	 * @param controlEvent
	 *            control event to be delivered. Never null.
	 */
	void react(ControlEvent controlEvent);

}
