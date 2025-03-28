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
 * Represents a criterion for filtering based on a SQL query.
 *
 * @see AbstractCriterion
 */
public class SqlCriterion extends AbstractCriterion {

	/**
	 * Constructs SqlCriterion with the specified JSON criterion.
	 *
	 * @param criterion The JSON criterion for SQL check.
	 */
	@Builder
	public SqlCriterion(final JsonNode criterion) {
		super(criterion);
	}

	/**
	 * Gets the query from the current SQL criterion, or {@code null} if not present.
	 *
	 * @return The query from the criterion, or {@code null} if not present.
	 */
	public String getQuery() {
		return nonNullTextOrDefault(criterion.get("query"), null);
	}

	/**
	 * Produces the SQL criterion in the specified sink.
	 *
	 * @param sink The sink to produce the criterion.
	 */
	@Override
	public void produce(Sink sink) {
		// SQL
		sink.listItem();
		sink.text("The ");
		sink.bold();
		sink.text("SQL query");
		sink.bold_();
		sink.text(" below succeeds on the monitored database:");
		sink.list();
		sink.list();
		sink.listItem();
		sink.rawText(String.format("SQL Query: <code>%s</code>", SinkHelper.replaceWithHtmlCode(getQuery())));
		sink.listItem_();

		final String expectedResult = getExpectedResult();
		if (expectedResult != null) {
			sink.listItem();
			sink.rawText(String.format("Expected Result: <code>%s</code>", SinkHelper.replaceWithHtmlCode(expectedResult)));
			sink.listItem_();
		}

		// End the SQL criteria list
		sink.list_();
		sink.listItem_();
	}
}
