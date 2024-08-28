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

/**
 * Interface for all components that can be uniquely identified. Identity is
 * always assigned to instances.
 * 
 * @author eborziv
 * 
 */
public interface Identifiable {

	/**
	 * Returns unique identifier for instance
	 * 
	 * @return the unique identifier of current component instance. Must not be
	 *         null or empty String. It is the responsibility of component
	 *         implementation to provide unique identifier for every instance.
	 */
	String getInstanceId();

	/**
	 * Invoked by engine to set unique identifier for instance. It is the
	 * responsibility of engine to invoke this method before any other method is
	 * invoked. Instances should simply store given value and return it as
	 * result of {@link #getInstanceId()} method invocation.
	 * 
	 * @param instanceId
	 *            unique identifier for instance. Never null or empty string.
	 */
	void setInstanceId(String instanceId);

}