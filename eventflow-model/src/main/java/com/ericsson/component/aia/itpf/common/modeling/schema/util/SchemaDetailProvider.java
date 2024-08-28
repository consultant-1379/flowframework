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

package com.ericsson.component.aia.itpf.common.modeling.schema.util;

import java.util.Collection;

/**
 * Implementations of this interface are capable of returning information about schemata. The implementations of this API will be loaded via the Java
 * ServiceLoader, and hence must be registered in the usual way. It is possible to introduce new schemata into the system by implementing this
 * interface, and placing the XSD into an appropriate place (for example, folder "schemata" inside the resources folder).
 */
public interface SchemaDetailProvider {

    /**
     * Returns information about schemata. Returned value must not be null and collection members must not be null.
     *
     * @return Collection<SchemaDetail>
     */
    Collection<SchemaDetail> getSchemaDetails();
}
