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
import org.metricshub.maven.plugin.connector.producer.SinkHelper;

/**
 * Represents a criterion for filtering based on an HTTP request.
 *
 * @see AbstractCriterion
 */
public class HttpCriterion extends AbstractCriterion {

	/**
	 * Constructs HttpCriterion with the specified JSON criterion.
	 *
	 * @param criterion The JSON criterion for HTTP.
	 */
	@Builder
	public HttpCriterion(final JsonNode criterion) {
		super(criterion);
	}

	/**
	 * Gets the method from the criterion, or a default value if not present.
	 *
	 * @param defaultValue The default value to return if the method is not present.
	 * @return The method from the criterion, or the default value if not present.
	 */
	public String getMethodOrDefault(final String defaultValue) {
		return nonNullTextOrDefault(criterion.get("method"), defaultValue);
	}

	/**
	 * Gets the URL from the criterion, or {@code null} if not present.
	 *
	 * @return The URL from the criterion, or {@code null} if not present.
	 */
	public String getUrl() {
		return nonNullTextOrDefault(criterion.get("url"), null);
	}

	/**
	 * Gets the Path from the criterion, or {@code null} if not present.
	 *
	 * @return The Path from the criterion, or {@code null} if not present.
	 */
	public String getPath() {
		return nonNullTextOrDefault(criterion.get("path"), null);
	}

	/**
	 * Gets the header from the criterion, or {@code null} if not present.
	 *
	 * @return The header from the criterion, or {@code null} if not present.
	 */
	public String getHeader() {
		return nonNullTextOrDefault(criterion.get("header"), null);
	}

	/**
	 * Gets the body from the criterion, or {@code null} if not present.
	 *
	 * @return The body from the criterion, or {@code null} if not present.
	 */
	public String getBody() {
		return nonNullTextOrDefault(criterion.get("body"), null);
	}

	/**
	 * Gets the result content from the criterion, or a default value if not present.
	 *
	 * @param defaultValue The default value to return if the result content is not present.
	 * @return The result content from the criterion, or the default value if not present.
	 */
	public String getResultContentOrDefault(final String defaultValue) {
		return nonNullTextOrDefault(criterion.get("resultContent"), defaultValue);
	}

	/**
	 * Produces the HTTP criterion as a list item.
	 *
	 * @param sink The sink to write to.
	 */
	@Override
	public void produce(final Sink sink) {
		// HTTP
		sink.listItem();
		sink.text("The ");
		sink.bold();
		sink.text("HTTP Request");
		sink.bold_();
		sink.text(" below to the managed host succeeds:");
		sink.list();
		sink.listItem();
		// Retrieve URL and Path Fields values
		String urlField = getUrl();
		String pathField = getPath();
		// Initialize the final URL value
		String url = "";
		// If both URL and Path fields aren't null, concatenate them
		if (urlField != null && pathField != null) {
			url =
				String.format(
					"%s%s%s",
					urlField,
					urlField.endsWith("/") || pathField.startsWith("/") ? "" : "/",
					urlField.endsWith("/") && pathField.startsWith("/") ? pathField.substring(1) : pathField
				);
			// if Only URL field value is found, use it
		} else if (urlField != null) {
			url = urlField;
			// if Only Path field value is found, use it
		} else if (pathField != null) {
			url = pathField;
		}
		sink.rawText(
			String.format("<b>%s</b> <code>%s</code>", getMethodOrDefault("GET"), SinkHelper.replaceWithHtmlCode(url))
		);
		sink.listItem_();

		final String httpHeader = getHeader();
		if (httpHeader != null) {
			sink.listItem();
			sink.text("Request Header:");
			sink.lineBreak();
			sink.rawText(String.format(CODE_FORMAT, SinkHelper.replaceWithHtmlCode(httpHeader)));
			sink.listItem_();
		}

		final String httpBody = getBody();
		if (httpBody != null) {
			sink.listItem();
			sink.text("Request Body:");
			sink.lineBreak();
			sink.rawText(String.format(CODE_FORMAT, SinkHelper.replaceWithHtmlCode(httpBody)));
			sink.listItem_();
		}

		String expectedResult = getExpectedResult();
		if (expectedResult != null) {
			expectedResult = SinkHelper.replaceWithHtmlCode(expectedResult);
			final String resultContent = getResultContentOrDefault("body").toLowerCase();
			sink.listItem();
			if ("body".equals(resultContent)) {
				sink.rawText(String.format("The response body contains: <code>%s</code> (regex)", expectedResult));
			} else if ("header".equals(resultContent)) {
				sink.rawText(String.format("The response header contains: <code>%s</code> (regex)", expectedResult));
			} else if ("httpstatus".equals(resultContent)) {
				sink.rawText(String.format("The HTTP response status code contains: <code>%s</code> (regex)", expectedResult));
			} else {
				sink.rawText(
					String.format("The entire response (header + body) contains: <code>%s</code> (regex)", expectedResult)
				);
			}
			sink.listItem_();
		}
		sink.list_();
		sink.listItem_();
	}
}
