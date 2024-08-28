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
package com.ericsson.component.aia.itpf.common.event.handler.annotation;

import java.lang.annotation.*;

/**
 * Annotation used by the camel subsystem for marking a class as an EventHandler
 * on deployment of its artifact e.g. EAR, WAR, JAR. The Camel Subsystem will
 * bind the marked class with its fully qualified class name in the camel jndi
 * registry. <b>This annotation should be used for all event handler types e.g
 * TypedEventInputHandler and EventInputHandler.</b>
 * 
 * @author eshacow
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventHandler {

    /**
     * The camel context name. The recommendation is to use the default empty
     * value which means a shared camel context through out all deployments.
     * 
     * @return The camel context name
     */
    public abstract String contextName() default "";

}
