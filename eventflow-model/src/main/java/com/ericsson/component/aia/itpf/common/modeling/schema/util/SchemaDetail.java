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

/**
 * Utility to hold detailed information about a schema. Clients will typically not have a need to instantiate or otherwise use instances of this
 * class.
 */
public class SchemaDetail {

    /**
     * This is the XSD namespace URI prefix used for all OSS-defined schemata. It is typically used in conjunction with schema names to aid in XML
     * processing. Note it follows corporate rules in relation to how URIs in XSDs are to be named.
     */
    static final String XSD_URI_PREFIX = "urn:com:ericsson:schema:xml:oss:";

    /**
     * The schema name
     */
    private final String schemaName;

    /**
     * Denotes the schemata this schema depends on. May be empty array (not null) to denote no dependencies.
     */
    private final String[] dependsOnSchemataNames;

    /**
     * The simple Java class name of the root type of the model. For example, "EventTypeDefinition"
     */
    private final String rootJavaTypeName;

    /**
     * Whether this schema supports namespaces. If set to false, the namespace used for all models of this schema must be "global".
     */
    private final boolean supportsNamespace;

    /**
     * Whether models of this schema are versioned.
     */
    private final boolean modelsAreVersioned;

    /**
     * The Java package name that contains the classes produced for this schema
     */
    private final String javaPackageName;

    /**
     * The location of the actual XSD file as resource.
     */
    private final String resourceLocation;

    /**
     * The namespace of the XSD.
     */
    private final String xsdNamespace;

    /**
     * The systemId of the XSD.
     */
    private final String systemId;

    /**
     * Whether the schema is used for extending models of another schema
     */
    private final boolean extension;

    /**
     * How the schema is encoded
     */
    private final SchemaConstants.EncodingType encodingType;

    /**
     * When the schema is versioned, whether the version is in X.Y.Z format. (Typically network-related models do not use X.Y.Z format).
     *
     * If schema does not support versioning, then false is used.
     */
    private final boolean versionsInXyzFormat;

    /**
     * Whether the schema allows user created models
     */
    private final boolean userCreatedAllowed;

    /**
     * The Java class of the root type of the model
     */
    private final Class<?> javaRootType;

    /**
     * Simple constructor for oss-defined schemata (Emodel's) supporting namespace and version.
     *
     * @param schemaName
     *            The schema name
     * @param dependsOnSchemataNames
     *            The schemata this model depends on.
     * @param javaRootType
     *            The java root type of the generated JAXB
     */
    public SchemaDetail(final String schemaName, final String[] dependsOnSchemataNames, final Class<?> javaRootType) {
        this(schemaName, dependsOnSchemataNames, javaRootType, true, true);
    }

    /**
     * Simple constructor for oss-defined schemata.
     *
     * @param schemaName
     *            The schema name
     * @param dependsOnSchemataNames
     *            The schemata this model depends on.
     * @param javaRootType
     *            The java root type of the generated JAXB
     * @param supportsNamespace
     *            Whether the schema supports namespaces.
     * @param modelsAreVersioned
     *            Whether models of this type are versioned.
     */
    public SchemaDetail(final String schemaName, final String[] dependsOnSchemataNames, final Class<?> javaRootType, final boolean supportsNamespace,
                        final boolean modelsAreVersioned) {

        this(schemaName, dependsOnSchemataNames, javaRootType, supportsNamespace, modelsAreVersioned, "/schemata/" + schemaName + ".xsd",
                            XSD_URI_PREFIX + schemaName, schemaName + ".xsd", false, SchemaConstants.EncodingType.XSD,
                            modelsAreVersioned ? true : false, false);
    }

    /**
     * Constructor for any schema.
     *
     * @param schemaName
     *            The schema name
     * @param dependsOnSchemataNames
     *            The schemata this model depends on.
     * @param javaRootType
     *            The java root type of the generated JAXB
     * @param supportsNamespace
     *            Whether the schema supports namespaces.
     * @param modelsAreVersioned
     *            Whether models of this type are versioned.
     * @param resourceLocation
     *            The resource location of the XSD file within the generated JAR
     * @param xsdNamespace
     *            The namespace of the schema.
     * @param systemId
     *            The systemId
     * @param extension
     *            Whether the schema is an extension
     * @param encodingType
     *            How the schema is encoded
     * @param versionsInXyzFormat
     *            Whether the versions of this type are in X.Y.Z format (true) or any string (false)
     * @param userCreatedAllowed
     *            Whether the schema allows user created models
     */
    public SchemaDetail(final String schemaName, final String[] dependsOnSchemataNames, final Class<?> javaRootType, final boolean supportsNamespace,
                        final boolean modelsAreVersioned, final String resourceLocation, final String xsdNamespace, final String systemId,
                        final boolean extension, final SchemaConstants.EncodingType encodingType, final boolean versionsInXyzFormat,
                        final boolean userCreatedAllowed) {

        this.schemaName = schemaName;
        this.dependsOnSchemataNames = dependsOnSchemataNames == null ? new String[0] : dependsOnSchemataNames;
        this.javaRootType = javaRootType;
        this.javaPackageName = (javaRootType == null) ? null : javaRootType.getPackage().getName();
        this.rootJavaTypeName = (javaRootType == null) ? null : javaRootType.getSimpleName();
        this.supportsNamespace = supportsNamespace;
        this.modelsAreVersioned = modelsAreVersioned;
        this.resourceLocation = resourceLocation;
        this.xsdNamespace = xsdNamespace;
        this.systemId = systemId;
        this.extension = extension;
        this.encodingType = encodingType;
        this.versionsInXyzFormat = versionsInXyzFormat;
        this.userCreatedAllowed = userCreatedAllowed;
    }

    /**
     * @return the schemaName
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * @return the dependsOnSchemataNames
     */
    public String[] getDependsOnSchemaNames() {
        return dependsOnSchemataNames;
    }

    /**
     * @return the Java root type name. Will be <code>null</code> if the Java root type name does not have a value defined.
     */
    public String getRootJavaTypeName() {
        return rootJavaTypeName;
    }

    /**
     * @return the Java root type class. Will be <code>null</code> if the Java root type does not have a value defined.
     */
    public Class<?> getRootJavaType() {
        return javaRootType;
    }

    /**
     * @return whether namespace is supported
     */
    public boolean doesSupportNamespace() {
        return supportsNamespace;
    }

    /**
     * @return whether models of this type are versioned
     */
    public boolean areModelsVersioned() {
        return modelsAreVersioned;
    }

    /**
     * @return the java Package Name for the JAXB classes. Will be <code>null</code> if the Java package name does not have a value defined.
     */
    public String getJavaPackageName() {
        return javaPackageName;
    }

    /**
     * @return the resource location of the XSD file. Will be <code>null</code> if the resource location does not have a value defined.
     */
    public String getResourceLocation() {
        return resourceLocation;
    }

    /**
     * @return the XSD namespace. Will be <code>null</code> if the XSD namespace does not have a value defined.
     */
    public String getXsdNamespace() {
        return xsdNamespace;
    }

    /**
     * @return the system ID. Will be <code>null</code> if the system ID does not have a value defined.
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * @return whether this schema is used to model extensions
     */
    public boolean isExtension() {
        return extension;
    }

    /**
     * @return the encoding type
     */
    public SchemaConstants.EncodingType getEncodingType() {
        return encodingType;
    }

    /**
     * return whether versions are in XYZ format.
     *
     * @return boolean true if in InXyzFormat.
     */
    public boolean areVersionsInXyzFormat() {
        return versionsInXyzFormat;
    }

    /**
     * @return whether the schema allows user created models
     */
    public boolean isUserCreatedAllowed() {
        return userCreatedAllowed;
    }
}
