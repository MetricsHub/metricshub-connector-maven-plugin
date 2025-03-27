package org.metricshub.maven.plugin.connector.producer.model.criteria;

/*-
 * ╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲
 * MetricsHub Connector Maven Plugin
 * ჻჻჻჻჻჻
 * Copyright (C) 2023 MetricsHub
 * ჻჻჻჻჻჻
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱
 */

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import org.apache.maven.doxia.sink.Sink;

/**
 * Represents a criterion for filtering based on an IPMI request.
 *
 * @see AbstractCriterion
 */
public class IpmiCriterion extends AbstractCriterion {

	/**
	 * Constructs IpmiCriterion with the specified JSON criterion.
	 *
	 * @param criterion The JSON criterion for IPMI.
	 */
	@Builder
	public IpmiCriterion(final JsonNode criterion) {
		super(criterion);
	}

	/**
	 * Produces the criterion description.
	 *
	 * @param sink The sink to write to.
	 */
	@Override
	public void produce(final Sink sink) {
		sink.listItem();
		sink.rawText("The IPMI-related WMI classes are populated on Windows,");
		sink.listItem_();
		sink.listItem();
		sink.bold();
		sink.text("OR");
		sink.bold_();
		sink.rawText(" <code>ipmitool</code> works properly with the local IPMI driver on Linux and Solaris, ");
		sink.listItem_();
		sink.listItem();
		sink.bold();
		sink.text("OR");
		sink.bold_();
		sink.text(" IPMI-over-LAN has been enabled as an out-of-band interface");
		sink.listItem_();
	}
}
