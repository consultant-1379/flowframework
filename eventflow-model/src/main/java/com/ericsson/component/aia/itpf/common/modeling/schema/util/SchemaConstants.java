/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2015
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

package com.ericsson.component.aia.itpf.common.modeling.schema.util;

/**
 * <p>
 * Contains constants for the various kinds of models (= schemata) that are supported.
 * </p>
 */
public abstract class SchemaConstants {

    /**
     * Denotes the encoding of a schema.
     */
    public enum EncodingType {
        /**
         * Schema is encoded in XSD
         */
        XSD,
        /**
         * Schema is encoded in DTD
         */
        DTD,
        /**
         * Schema is encoded in YANG
         */
        YANG,
        /**
         * Schema is encoded in ASN1
         */
        ASN1,
        /**
         * Model encoding type is unknown
         */
        UNKNOWN
    }

    /*
     * It is important that this class is kept up-to-date. It must be updated when a new schema is introduced into the OSS.
     */

    /**
     * The name of the global namespace. This namespace is to be used for all models that are of a type that do not support a namespace. Note: This
     * should not be confused with the XSD namespace URI, which is a completely different thing.
     */
    public static final String GLOBAL_MODEL_NAMESPACE = "global";

    /**
     * To be used as name for all models which are derived from a model owned by a node
     */
    public static final String NE_DEFINED = "NE-defined";

    /*
     * Below are a bunch of public constants that can be used to refer to the individual schemas. They are not further documented here, as the XSD
     * documentation should be consulted for their function.
     */

    /**
     * Constant to denote oss_common schema.
     */
    public static final String OSS_COMMON = "oss_common";

    /**
     * Constant to denote oss_cache schema.
     */
    public static final String OSS_CACHE = "oss_cache";

    /**
     * Constant to denote oss_cdt (complex data type) schema.
     */
    public static final String OSS_CDT = "oss_cdt";

    /**
     * Constant to denote oss_channel schema.
     */
    public static final String OSS_CHANNEL = "oss_channel";

    /**
     * Constant to denote oss_confparam (configuration parameter) schema.
     */
    public static final String OSS_CONFPARAM = "oss_confparam";

    /**
     * Constant to denote oss_edt (enum data type) schema.
     */
    public static final String OSS_EDT = "oss_edt";

    /**
     * Constant to denote oss_edt (enum data type) extension schema.
     */
    public static final String OSS_EDT_EXT = "oss_edt_ext";

    /**
     * Constant to denote oss_eventtype schema.
     */
    public static final String OSS_EVENTTYPE = "oss_eventtype";

    /**
     * Constant to denote oss_eventtype extension schema.
     */
    public static final String OSS_EVENTTYPE_EXT = "oss_eventtype_ext";

    /**
     * Constant to denote oss_eventtypesupport schema.
     */
    public static final String OSS_EVENTTYPESUPPORT = "oss_eventtypesupport";

    /**
     * Constant to denote oss_capability schema.
     */
    public static final String OSS_CAPABILITY = "oss_capability";

    /**
     * Constant to denote oss_capabilitysupport schema.
     */
    public static final String OSS_CAPABILITYSUPPORT = "oss_capabilitysupport";

    /*
     * TODO: It is not strictly correct to keep constants for non-core schemata in this class. What should really happen is that these specific
     * constants are moved into separate projects, together with the specific schemata and SchemaDetailProvider implementations.
     */

    /**
     * Constant to denote the mediation configuration schema
     */
    public static final String MED_CONFIGURATION = "med_configuration";

    /**
     * Constant to denote dps_common schema.
     */
    public static final String DPS_COMMON = "dps_common";

    /**
     * Constant to denote dps_primarytype schema.
     */
    public static final String DPS_PRIMARYTYPE = "dps_primarytype";

    /**
     * Constant to denote dps_primarytype extension schema.
     */
    public static final String DPS_PRIMARYTYPE_EXT = "dps_primarytype_ext";

    /**
     * Constant to denote dps_relationship schema.
     */
    public static final String DPS_RELATIONSHIP = "dps_relationship";

    /**
     * Constant to denote dps_relationship extension schema.
     */
    public static final String DPS_RELATIONSHIP_EXT = "dps_relationship_ext";

    /**
     * Constant to denote ext_alarmadaptation schema.
     */
    public static final String EXT_ALARMADAPTATION = "ext_alarmadaptation";

    /**
     * Constant to denote ext_integrationpointlibrary extension schema.
     */
    public static final String EXT_INTEGRATION_POINT_LIBRARY = "ext_integrationpointlibrary";

    /**
     * Constant to denote fbp_flow schema.
     */
    public static final String AIA_FLOW = "fbp_flow";

    /**
     * Constant to denote net_pmevents schema.
     */
    public static final String NET_PMEVENTS = "net_pmevents";

    /**
     * Constant to denote net_momdtd schema.
     */
    public static final String NET_MOMDTD = "net_momdtd";

    /**
     * Constant to denote net_yang schema.
     */
    public static final String NET_YANG = "net_yang";

    /**
     * Constant to denote net_smiv2 schema.
     */
    public static final String NET_SMIV2 = "net_smiv2";

    /**
     * Constant to denote net_etcm schema.
     */
    public static final String NET_ETCM = "net_etcm";

    /**
     * Constant to denote net_momdtdmapping schema.
     */
    public static final String NET_MOMDTDMAPPING = "net_momdtdmapping";

    /**
     * Constant to denote eps_mesa schema.
     */
    public static final String EPS_MESA = "eps_mesa";

    /**
     * Constant to denote pfm_measurement schema.
     */
    public static final String PFM_MEASUREMENT = "pfm_measurement";

    /**
     * Constant to denote pfm_kpi schema.
     */
    public static final String PFM_KPI = "pfm_kpi";

    /**
     * Constant to denote pfm_event schema.
     */
    public static final String PFM_EVENT = "pfm_event";

    /**
     * Constant to denote exs_exportfilter schema.
     */
    public static final String EXS_EXPORTFILTER = "exs_exportfilter";

    /**
     * Constant to denote ned_netypeinfo schema.
     */
    public static final String NED_NETYPEINFO = "ned_netypeinfo";

    /**
     * Constant to denote dlm_schedule schema.
     */
    public static final String DLM_SCHEDULE = "dlm_schedule";

    /**
     * Constant to denote cfm_miminfo schema.
     */
    public static final String CFM_MIMINFO = "cfm_miminfo";

    /**
     * Constant to denote tpl_templatetype schema.
     */
    public static final String TPL_TEMPLATE_TYPE = "tpl_templatetype";

    /**
     * Constant to denote oss_targetversion schema.
     */
    public static final String OSS_TARGETVERSION = "oss_targetversion";

    /**
     * Constant to denote oss_targettype schema.
     */
    public static final String OSS_TARGETTYPE = "oss_targettype";

}
