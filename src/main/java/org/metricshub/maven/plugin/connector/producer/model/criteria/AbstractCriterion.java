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

import static org.metricshub.maven.plugin.connector.producer.JsonNodeHelper.nonNullTextOrDefault;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.apache.maven.doxia.sink.Sink;

/**
 * Abstract class representing a criterion.
 * <p>
 * This abstract class defines the contract for criterion types, providing a
 * method to accept a visitor implementing specific business logic.
 * </p>
 * <p>
 * This abstract class also exposes shared methods that are accessible to each
 * criterion.
 * </p>
 */
@AllArgsConstructor
public abstract class AbstractCriterion {

	protected static final String CODE_FORMAT = "<code>%s</code>";

	protected JsonNode criterion;

	/**
	 * Gets the expected result from the criterion, or a {@code null} if not present.
	 *
	 * @return The expected result from the criterion, or the {@code null} if not present.
	 */
	protected String getExpectedResult() {
		return nonNullTextOrDefault(criterion.get("expectedResult"), null);
	}

	/**
	 * Produces the criterion in the given sink.
	 *
	 * @param sink The sink to produce the criterion in.
	 */
	public abstract void produce(Sink sink);
}
