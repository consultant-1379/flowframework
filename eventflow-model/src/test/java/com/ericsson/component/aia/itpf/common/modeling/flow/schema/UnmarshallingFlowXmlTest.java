/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2017
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.component.aia.itpf.common.modeling.flow.schema;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;

import com.ericsson.component.aia.itpf.common.modeling.flow.schema.gen.fbp_flow.*;
import com.ericsson.component.aia.itpf.common.modeling.schema.util.DtdModelHandlingUtil;
import com.ericsson.component.aia.itpf.common.modeling.schema.util.SchemaConstants;

public class UnmarshallingFlowXmlTest {

    @Test
    public void shouldUnmarshalXmlFileIntoPojos() throws JAXBException {
        final Unmarshaller unmarshaller = DtdModelHandlingUtil.getUnmarshaller(SchemaConstants.AIA_FLOW);

        final FlowDefinition root = (FlowDefinition) unmarshaller.unmarshal(new StreamSource("src/test/resources/fbp_flow.xml"));
        assertThat(root, notNullValue());
        assertThat(root.getOutput().get(0).getName(), equalTo("AvroOutputAdapter"));

        final SinkType sinkType = root.getOutput().get(0).getSinks().getSink().get(0);
        assertThat(sinkType.getUri(), equalTo("kafka://topic?fomat=avro"));
        assertThat(sinkType.getFilter().getRecords().getRecord().get(0).getByRegex().get(0).getValue(), equalTo("a.*"));
        assertThat(sinkType.getDataRouting().getPartitionStrategy().getByFunction().getValue(), equalTo(" (a + b +c) % 3 "));
        assertThat(sinkType.getProperty().size(), equalTo(9));

        final SourceType source = root.getInput().get(0).getSources().getSource().get(0);
        assertThat(source, notNullValue());
        assertThat(source.getUri(), equalTo("kafka://topic1?fomat=avro"));
        assertThat(source.getProperty().size(), equalTo(8));
    }

}
