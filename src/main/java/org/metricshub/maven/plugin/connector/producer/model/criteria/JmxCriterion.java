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
import java.util.List;
import lombok.Builder;
import org.apache.maven.doxia.sink.Sink;
import org.metricshub.maven.plugin.connector.producer.JsonNodeHelper;
import org.metricshub.maven.plugin.connector.producer.SinkHelper;

/**
 * Represents a JMX criterion that checks for specific attributes in a JMX MBean.
 */
public class JmxCriterion extends AbstractCriterion {

	/**
	 * Creates a new JmxCriterion from the given JSON node.
	 * @param criterion The JSON node representing the JMX criterion.
	 */
	@Builder
	public JmxCriterion(JsonNode criterion) {
		super(criterion);
	}

	/**
	 * Produces the JMX criterion output for the given sink.
	 */
	@Override
	public void produce(final Sink sink) {
		final String objectName = getObjectName();
		final List<String> attributes = getAttributes();
		final String expectedResult = getExpectedResult();

		// JMX
		sink.listItem();
		sink.text("The ");
		sink.bold();
		sink.text("JMX request");
		sink.bold_();
		sink.text(" below succeeds when querying the monitored resource:");

		sink.list();
		sink.listItem();
		sink.rawText(String.format("Object Name: <code>%s</code>", SinkHelper.replaceWithHtmlCode(objectName)));
		sink.listItem_();

		sink.listItem();
		if (attributes.size() == 1) {
			sink.rawText(String.format("Attribute: <code>%s</code>", SinkHelper.replaceWithHtmlCode(attributes.get(0))));
		} else {
			sink.rawText(
				String.format("Attributes: <code>%s</code>", SinkHelper.replaceWithHtmlCode(String.join(",", attributes)))
			);
		}
		sink.listItem_();

		if (expectedResult != null) {
			sink.listItem();
			sink.rawText(
				String.format("Expected Result: <code>%s</code> (regex)", SinkHelper.replaceWithHtmlCode(expectedResult))
			);
			sink.listItem_();
		}

		sink.list_();
		sink.listItem_();
	}

	/**
	 * Gets the MBean attributes from the criterion. E.g. ["HeapMemoryUsage", "NonHeapMemoryUsage"]
	 *
	 * @return A list of MBean attribute names.
	 */
	private List<String> getAttributes() {
		return JsonNodeHelper.nodeToStringList(criterion.get("attributes"));
	}

	/**
	 * Gets the object name from the criterion. E.g. "java.lang:type=Memory"
	 *
	 * @return The object name from the criterion.
	 */
	public String getObjectName() {
		return JsonNodeHelper.nonNullTextOrDefault(criterion.get("objectName"), "");
	}
}
