package org.sentrysoftware.maven.metricshub.connector.producer.model.criteria;

/*-
 * ╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲
 * MetricsHub Connector Maven Plugin
 * ჻჻჻჻჻჻
 * Copyright (C) 2023 Sentry Software
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

/**
 * Represents a criterion for filtering based on an SNMP GET request.
 *
 * @see AbstractCriterion
 */
public class SnmpGetCriterion extends AbstractSnmpCriterion {

	private static final String SNMP_GET_TYPE = "Get";

	/**
	 * Constructs SnmpGetCriterion with the specified JSON criterion.
	 *
	 * @param criterion The JSON criterion for SNMP GET check.
	 */
	@Builder
	public SnmpGetCriterion(final JsonNode criterion) {
		super(criterion);
	}

	@Override
	public void accept(ICriterionVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	protected String getType() {
		return SNMP_GET_TYPE;
	}
}
