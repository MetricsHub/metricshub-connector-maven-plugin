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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.plugin.logging.Log;
import org.metricshub.maven.plugin.connector.Constants;

/**
 * Utility class for producing the connectors directory page.
 */
public class FullListingPageProducer extends AbstractPageProducer {

	private final String connectorSubdirectoryName;
	private final String tagSubdirectoryName;
	private final String projectName;

	/**
	 * Constructor for the main page producer.
	 *
	 * @param logger                     The logger used for logging.
	 * @param connectorSubdirectoryName  The connector subdirectory name.
	 * @param tagSubdirectoryName        The tag subdirectory name.
	 * @param projectName                The name of the project.
	 */
	public FullListingPageProducer(
		Log logger,
		String connectorSubdirectoryName,
		String tagSubdirectoryName,
		String projectName
	) {
		super(logger);
		this.connectorSubdirectoryName = connectorSubdirectoryName;
		this.tagSubdirectoryName = tagSubdirectoryName;
		this.projectName = projectName;
	}

	/**
	 * Produces the main page report that lists all the connectors.
	 *
	 * @param mainSink               The main sink used for generating content.
	 * @param connectors             The map of connector identifiers to their corresponding JsonNodes.
	 * @param enterpriseConnectorIds The enterprise connector identifiers.
	 * @param connectorTags          The set of connector tags.
	 */
	public void produce(
		final Sink mainSink,
		final Map<String, JsonNode> connectors,
		final List<String> enterpriseConnectorIds,
		final Set<String> connectorTags
	) {
		Objects.requireNonNull(connectorSubdirectoryName, () -> "connectorSubdirectoryName cannot be null.");
		Objects.requireNonNull(tagSubdirectoryName, () -> "tagSubdirectoryName cannot be null.");
		Objects.requireNonNull(mainSink, () -> "mainSink cannot be null.");
		Objects.requireNonNull(logger, () -> "logger cannot be null.");
		Objects.requireNonNull(connectors, () -> "connectors cannot be null.");
		Objects.requireNonNull(connectorTags, () -> "connectorTags cannot be null.");
		Objects.requireNonNull(projectName, () -> "projectName cannot be null.");

		logger.debug(
			String.format("Generating the connectors directory page %s", Constants.CONNECTORS_DIRECTORY_FILE_NAME)
		);

		final String displayProjectName = projectName.trim();
		final String title = buildTitle(displayProjectName);

		buildHead(mainSink, title);

		mainSink.body();

		// Title
		mainSink.section1();
		mainSink.sectionTitle1();
		mainSink.text(title);
		mainSink.sectionTitle1_();

		// Intro
		mainSink.paragraph();
		mainSink.text(
			String.format("This directory lists the Connectors of %s ${project.version}.", displayProjectName) +
			" Each page provides you with the details on each Connector, the targeted platform," +
			" the protocol used, the discovered components and monitored attributes."
		);
		mainSink.paragraph_();

		mainSink.sectionTitle2();
		mainSink.text("Connector Tags");
		mainSink.sectionTitle2_();

		mainSink.paragraph();
		mainSink.text(
			"The connectors are organized with tags to help you quickly find connectors that" +
			" meet specific category or vendor. Below is the list of available tags:"
		);
		mainSink.paragraph_();

		// Sort the entries in tagsSet
		connectorTags
			.stream()
			.sorted(String.CASE_INSENSITIVE_ORDER)
			.collect(Collectors.toCollection(LinkedHashSet::new))
			.forEach(tag ->
				mainSink.rawText(
					SinkHelper.bootstrapLabel(
						SinkHelper.hyperlinkRef(
							String.format(
								"%s/%s/%s.html",
								connectorSubdirectoryName,
								tagSubdirectoryName,
								tag.toLowerCase().replace(" ", "-")
							),
							tag
						),
						"metricshub-tag"
					)
				)
			);

		mainSink.sectionTitle2();
		mainSink.text("Full Listing");
		mainSink.sectionTitle2_();

		// Create the table
		buildConnectorsTable(mainSink, connectors, connectorSubdirectoryName, enterpriseConnectorIds, false);

		mainSink.section1_();

		mainSink.body_();

		mainSink.close();
	}

	/**
	 * Builds the title for the connectors page.
	 *
	 * @param displayProjectName The display name of the project.
	 * @return The constructed title.
	 */
	private static String buildTitle(final String displayProjectName) {
		final String normalizedName = displayProjectName.strip();
		if (normalizedName.toLowerCase(Locale.ROOT).endsWith(" connectors")) {
			return normalizedName;
		}
		return normalizedName + " Connectors";
	}
}
