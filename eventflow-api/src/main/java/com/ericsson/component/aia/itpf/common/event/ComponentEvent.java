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

import java.util.Map;

import com.ericsson.component.aia.itpf.common.event.handler.EventInputHandler;
import com.ericsson.component.aia.itpf.common.event.handler.TypedEventInputHandler;

/**
 * Common event type used for strongly typed events.
 * 
 * @author eborziv
 * @see TypedEventInputHandler
 * @see EventInputHandler
 * 
 */
public interface ComponentEvent {

	/**
	 * Returns headers associated with this event.
	 * 
	 * @return headers
	 */
	Map<String, Object> getHeaders();

	/**
	 * Returns payload of this event.
	 * 
	 * @return event payload
	 */
	Object getPayload();

	/**
	 * The event namespace. Must not be null or empty string.
	 * 
	 * @return
	 */
	String getNamespace();

	/**
	 * The event name. Must not be null or empty string.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * The event version. Must not be null or empty string. Must be in format
	 * <code>x.y.z</code>
	 * 
	 * @return
	 */
	String getVersion();

	/**
	 * Unique identifier of type this event belongs to.
	 * 
	 * @return
	 */
	long eventTypeId();

}
