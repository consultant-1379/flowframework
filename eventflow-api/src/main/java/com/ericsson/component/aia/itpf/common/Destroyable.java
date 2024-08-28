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
 * Interface for all components that should be invoked before being destroyed.
 * 
 * @author eborziv
 * 
 */
public interface Destroyable {

	/**
	 * Invoked before instance is destroyed. Last chance to do cleanup and close
	 * resources. Whenever module defining this instance is undeployed this
	 * method will be invoked. This method must not throw any exceptions.
	 */
	void destroy();

}
