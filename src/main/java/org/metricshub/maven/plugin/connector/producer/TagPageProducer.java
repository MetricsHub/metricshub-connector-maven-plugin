package org.metricshub.maven.plugin.connector.producer;

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
import java.util.Map;
import java.util.Objects;
import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.plugin.logging.Log;

/**
 * Utility class for producing tag page related to connectors.
 */
public class TagPageProducer extends AbstractGroupConnectorsProducer {

	private final String tagName;

	/**
	 * Constructor for the tag page producer.
	 *
	 * @param logger  The logger used for logging.
	 * @param tagName The tag name.
	 */
	public TagPageProducer(Log logger, String tagName) {
		super(logger);
		this.tagName = tagName;
	}

	/**
	 * Produces the tag page report that lists all the connectors.
	 * @param sink                      The sink used for generating content.
	 * @param connectors                The map of connector identifiers to their corresponding JsonNodes.
	 * @param connectorSubdirectoryName The connector subdirectory name.
	 * @param enterpriseConnectorIds    The enterprise connector identifiers.
	 */
	public void produce(
		final Sink sink,
		final Map<String, JsonNode> connectors,
		final String connectorSubdirectoryName,
		final List<String> enterpriseConnectorIds
	) {
		Objects.requireNonNull(tagName, () -> "tagName cannot be null.");
		Objects.requireNonNull(connectors, () -> "connectors cannot be null.");
		Objects.requireNonNull(sink, () -> "sink cannot be null.");
		Objects.requireNonNull(logger, () -> "logger cannot be null.");

		logger.debug("Generating Tag Page: " + SinkHelper.buildPageFilename(tagName));

		buildHeadAndBody(sink, connectorSubdirectoryName, enterpriseConnectorIds, tagName, connectors);
	}

	@Override
	protected String getIntroductionText(final String title) {
		return String.format("Discover all the available connectors related to the <code>%s</code> tag.", title);
	}
}
