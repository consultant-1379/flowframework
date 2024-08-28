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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ericsson.component.aia.itpf.common.modeling.flow.schema.gen.fbp_flow.FlowDefinition;
import com.ericsson.component.aia.itpf.common.modeling.flow.schema.gen.oss_common.EModelDefinition;

/**
 * Returns the Schema details for all Flow-related EModel schemata. This class is typically not used by clients, as it is internal to the modeling
 * toolchain. It is public due to classloading issues, but should be considered non-public for general use.
 */
public class FlowSchemaDetailProvider implements SchemaDetailProvider {
    /*
     * TODO
     *
     * This class at the moment is in the wrong project. It (and the Flow schemata) should be moved to a separate project in the "aia / Flow"
     * namespace in the future.
     */

    /*
     * (non-Javadoc)
     *
     * @see com.ericsson.oss.aia.modeling.schema.util.SchemaDetailProvider#getSchemaDetails()
     */
    @Override
    public Collection<SchemaDetail> getSchemaDetails() {

        final List<SchemaDetail> result = new ArrayList<>(30);
        result.add(new SchemaDetail(SchemaConstants.OSS_COMMON, new String[] {}, EModelDefinition.class));
        result.add(new SchemaDetail(SchemaConstants.AIA_FLOW, new String[] { SchemaConstants.OSS_COMMON }, FlowDefinition.class));
        return result;
    }

}
