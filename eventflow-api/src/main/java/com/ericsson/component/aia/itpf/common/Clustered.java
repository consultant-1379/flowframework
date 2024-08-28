/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2014
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
 * This is a special type of {@link Controllable} interface for components which will be controlled as a cluster.
 * <p>
 * The flow engine will deliver a {@link ControlEvent} of interest to the component only, the {@link ControlEvent} is <b>not</b> sent to all
 * components in the flow.
 * 
 * @since 1.0.343
 * @see Controllable
 */
public interface Clustered extends Controllable {

}
