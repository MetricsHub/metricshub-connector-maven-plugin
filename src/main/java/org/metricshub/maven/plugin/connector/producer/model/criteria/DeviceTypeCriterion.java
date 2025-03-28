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

import static org.metricshub.maven.plugin.connector.producer.JsonNodeHelper.nodeToStringList;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import org.apache.maven.doxia.sink.Sink;
import org.metricshub.maven.plugin.connector.producer.model.common.OsType;

/**
 * Represents a criterion for filtering based on kept and excluded device types.
 *
 * @see AbstractCriterion
 */
public class DeviceTypeCriterion extends AbstractCriterion {

	/**
	 * Constructs DeviceTypeCriterion with the specified JSON criterion.
	 *
	 * @param criterion The JSON criterion for device type.
	 */
	@Builder
	public DeviceTypeCriterion(final JsonNode criterion) {
		super(criterion);
	}

	/**
	 * Gets the list of operating systems to be kept.
	 *
	 * @return A list of operating systems to be kept.
	 * @see #getOsList(String)
	 */
	public List<String> getKeptOsList() {
		return getOsList("keep");
	}

	/**
	 * Gets the list of operating systems based on the specified criterion key.
	 *
	 * @param key The criterion key indicating the type of operating systems.
	 * @return A list of operating systems based on the specified criterion key.
	 */
	private List<String> getOsList(final String key) {
		return nodeToStringList(criterion.get(key)).stream().map(OsType::detectDisplayName).collect(Collectors.toList());
	}

	/**
	 * Gets the list of operating systems to be excluded.
	 *
	 * @return A list of operating systems to be excluded.
	 * @see #getOsList(String)
	 */
	public List<String> getExcludedOsList() {
		return getOsList("exclude");
	}

	/**
	 * Produces the device type criterion as a list item.
	 *
	 * @param sink the sink to write to.
	 */
	@Override
	public void produce(final Sink sink) {
		// Operating System
		sink.listItem();
		final List<String> keptOsList = getKeptOsList();
		if (keptOsList != null && !keptOsList.isEmpty()) {
			sink.rawText("Operating System is <b>" + String.join("</b> or <b>", keptOsList) + "</b>");
		}
		final List<String> excludedOsList = getExcludedOsList();
		if (excludedOsList != null && !excludedOsList.isEmpty()) {
			sink.rawText("Operating System is <b>NOT " + String.join("</b> and <b>NOT ", excludedOsList) + "</b>");
		}
		sink.listItem_();
	}
}
