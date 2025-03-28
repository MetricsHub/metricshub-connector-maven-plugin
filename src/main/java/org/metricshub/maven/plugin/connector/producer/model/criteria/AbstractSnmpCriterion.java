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
import org.apache.maven.doxia.sink.Sink;
import org.metricshub.maven.plugin.connector.producer.SinkHelper;

/**
 * An abstract base class for SNMP (Simple Network Management Protocol)
 * criteria.
 *
 * <p>
 * This class extends {@link AbstractCriterion} and provides common
 * functionality for SNMP-related criteria.
 * </p>
 *
 * @see AbstractCriterion
 */
public abstract class AbstractSnmpCriterion extends AbstractCriterion {

	/**
	 * Parent constructor of the SNMP criterion classes with the provided criterion.
	 *
	 * @param criterion The {@link JsonNode} representing the SNMP criterion.
	 */
	protected AbstractSnmpCriterion(JsonNode criterion) {
		super(criterion);
	}

	/**
	 * Gets the OID from the current SNMP criterion, or {@code null} if not present.
	 *
	 * @return The OID from the criterion, or {@code null} if not present.
	 */
	public String getOid() {
		return nonNullTextOrDefault(criterion.get("oid"), null);
	}

	/**
	 * Get the type of the SNMP criterion.
	 *
	 * @return the type of the criterion as string.
	 */
	protected abstract String getType();

	/**
	 * Builds the sink for SNMP-related information based on the current SNMP criterion.
	 *
	 * @param expectedResultStartMsg  Start message to use if the expected result directive is present.
	 * @param nonExpectedResultEndMsg End message to use if the expected result directive is not present.
	 * @param sink                    The sink to produce the criterion.
	 */
	protected void buildSnmpSink(
		final String expectedResultStartMsg,
		final String nonExpectedResultEndMsg,
		final Sink sink
	) {
		final String type = getType();

		// SNMP
		sink.listItem();
		final String oid = getOid();
		// SNMP Get
		sink.text("An ");
		sink.bold();
		sink.text(String.format("SNMP %s", type));
		sink.bold_();
		sink.rawText(String.format(" on the OID <code>%s</code>", SinkHelper.replaceWithHtmlCode(oid)));
		sink.text(" must return ");
		final String expectedResult = getExpectedResult();
		if (expectedResult != null) {
			sink.text(" " + expectedResultStartMsg);
			sink.rawText(String.format("<code>%s</code> (regular expression)", expectedResult));
		} else {
			sink.text(" " + nonExpectedResultEndMsg);
		}
		sink.listItem_();
	}
}
