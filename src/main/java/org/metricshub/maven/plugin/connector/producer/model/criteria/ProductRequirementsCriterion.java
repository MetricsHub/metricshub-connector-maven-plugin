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
import lombok.Builder;
import org.apache.maven.doxia.sink.Sink;

/**
 * Represents a criterion for filtering based on the engine version.
 *
 * @see AbstractCriterion
 */
public class ProductRequirementsCriterion extends AbstractCriterion {

	/**
	 * Constructs ProductRequirementsCriterion with the specified JSON criterion.
	 *
	 * @param criterion The JSON criterion for product requirements check.
	 */
	@Builder
	public ProductRequirementsCriterion(final JsonNode criterion) {
		super(criterion);
	}

	/**
	 * Gets the engine version from the current process criterion, or {@code null} if not present.
	 *
	 * @return The engine version from the criterion, or {@code null} if not present.
	 */
	public String getEngineVersion() {
		return nonNullTextOrDefault(criterion.get("engineVersion"), null);
	}

	/**
	 * Produces the criterion to the specified sink.
	 * @param sink The sink to produce the criterion to.
	 */
	public void produce(final Sink sink) {
		final String version = getEngineVersion();
		if (version != null) {
			sink.listItem();
			sink.text("The MetricsHub is in version ");
			sink.bold();
			sink.text(version);
			sink.bold_();
			sink.text(" or greater");
			sink.listItem_();
		}
	}
}
