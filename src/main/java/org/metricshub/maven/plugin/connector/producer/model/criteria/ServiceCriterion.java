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
 * Represents a criterion for filtering based on a service presence.
 *
 * @see AbstractCriterion
 */
public class ServiceCriterion extends AbstractCriterion {

	/**
	 * Constructs ServiceCriterion with the specified JSON criterion.
	 *
	 * @param criterion The JSON criterion for service check.
	 */
	@Builder
	public ServiceCriterion(final JsonNode criterion) {
		super(criterion);
	}

	/**
	 * Gets the name from the current service criterion, or {@code null} if not present.
	 *
	 * @return The service name from the criterion, or {@code null} if not present.
	 */
	public String getName() {
		return nonNullTextOrDefault(criterion.get("name"), null);
	}

	/**
	 * Produces the criterion to the specified sink.
	 *
	 * @param sink The sink to produce the criterion to.
	 */
	@Override
	public void produce(final Sink sink) {
		// Windows Service
		sink.listItem();
		sink.text("The Windows service ");
		sink.bold();
		sink.text(getName());
		sink.bold_();
		sink.text(" is running");
		sink.listItem_();
	}
}
