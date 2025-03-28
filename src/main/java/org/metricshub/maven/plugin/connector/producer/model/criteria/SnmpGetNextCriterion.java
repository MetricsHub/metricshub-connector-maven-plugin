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
 * Represents a criterion for filtering based on an SNMP GET-NEXT request.
 *
 * @see AbstractCriterion
 */
public class SnmpGetNextCriterion extends AbstractSnmpCriterion {

	private static final String GET_NEXT_TYPE = "Get-Next";

	/**
	 * Constructs SnmpGetNextCriterion with the specified JSON criterion.
	 *
	 * @param criterion The JSON criterion for SNMP GET-NEXT check.
	 */
	@Builder
	public SnmpGetNextCriterion(final JsonNode criterion) {
		super(criterion);
	}

	@Override
	protected String getType() {
		return GET_NEXT_TYPE;
	}

	/**
	 * Produces the criterion to the specified sink.
	 *
	 * @param sink The sink to produce the criterion to.
	 */
	@Override
	public void produce(final Sink sink) {
		buildSnmpSink("a value in the same subtree and contains ", "a value in the same subtree", sink);
	}
}
