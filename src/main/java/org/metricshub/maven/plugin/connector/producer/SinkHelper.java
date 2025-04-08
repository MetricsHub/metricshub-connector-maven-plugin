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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.doxia.sink.SinkEventAttributes;
import org.apache.maven.doxia.sink.impl.SinkEventAttributeSet;

/**
 * Utility class providing helper methods for generating content in a Sink format.
 * <p>
 * The class includes methods for setting CSS classes, inserting code blocks with syntax highlighting,
 * outputting FontAwesome and Glyphicon icons, building HTML page filenames, etc.
 * </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SinkHelper {

	/**
	 * The non-breaking space character
	 */
	public static final String NON_BREAKING_SPACE = "&nbsp;";

	/**
	 * Use a compiled representation of a regular expression to find commas followed by non-whitespace
	 */
	private static final Pattern COMMA_FOLLOWED_BY_NON_WHITESPACE = Pattern.compile(",(?=\\S)");

	/**
	 * Creates an AttributeSet that sets the CSS class to the specified class
	 *
	 * @param className The class to be set
	 * @return the AttributeSet that can be used in any Sink element
	 */
	public static SinkEventAttributes setClass(final String className) {
		return setAttribute(SinkEventAttributes.CLASS, className);
	}

	/**
	 * Creates an AttributeSet that sets the specified attribute to the specified value
	 *
	 * @param attributeName The name of the attribute to be set
	 * @param value         The value to be set for the attribute
	 * @return the AttributeSet that can be used in any Sink element
	 */
	public static SinkEventAttributes setAttribute(final String attributeName, final String value) {
		// Create a new AttributeSet
		final SinkEventAttributes attributes = new SinkEventAttributeSet();

		// Set the attribute value
		attributes.addAttribute(attributeName, value);

		return attributes;
	}

	/**
	 * Inserts a code block into the provided Sink, applying syntax highlighting if a language is specified.
	 *
	 * @param sink     The Sink to which the code block will be inserted.
	 * @param language The programming language for syntax highlighting (null or empty for no highlighting).
	 * @param code     The code to be inserted into the code block.
	 */
	public static void insertCodeBlock(final Sink sink, final String language, final String code) {
		if (language != null && !language.isEmpty()) {
			sink.verbatim(setClass("language-" + language));
			sink.rawText("<code class=\"language-" + language + "\">");
		} else {
			sink.verbatim(null);
			sink.rawText("<code>");
		}
		sink.text(code);
		sink.rawText("</code>");
		sink.verbatim_();
	}

	/**
	 * Returns the HTML code to output a FontAwesome icon<br>
	 * Example:
	 * <pre>
	 * &lt;i class="fa fa-arrows-v" aria-hidden="true"&gt;&lt;/i&gt;
	 * </pre>
	 * <p>
	 * Note: Returns a question icon if specified icon is empty or null
	 * </p>
	 * @param iconName The name of the FontAwesome icon to insert (without the "fa-" prefix)
	 * @return the HTML code for this icon
	 */
	public static String faIcon(final String iconName) {
		String faIconName = "question";
		if (iconName != null && !iconName.isEmpty()) {
			faIconName = iconName;
		}
		return String.format("<i class=\"fa fa-%s\" aria-hidden=\"true\"></i>", faIconName);
	}

	/**
	 * Returns the HTML code to output a Glyphicon (Bootstrap) icon
	 * <br>
	 * Example:
	 * <pre>
	 * &lt;i class="glyphicon glyphicon-info-sign" aria-hidden="true"&gt;&lt;/i&gt;
	 * </pre>
	 * <p>
	 * Note: Returns a question icon if specified icon is empty or null
	 * </p>
	 * @param iconName The name of the Glyphicon icon to insert (without the "glyphicon-" prefix)
	 * @return the HTML code for this icon
	 */
	public static String glyphIcon(final String iconName) {
		String glyphIconName = "question-sign";
		if (iconName != null && !iconName.isEmpty()) {
			glyphIconName = iconName;
		}
		return String.format("<i class=\"glyphicon glyphicon-%s\" aria-hidden=\"true\"></i>", glyphIconName);
	}

	/**
	 * Create a bootstrap badge with the following content.
	 *
	 * @param content text of the label.
	 * @param customClassname custom class name to apply to the label.
	 * @return the HTML code for this badge.
	 */
	public static String bootstrapLabel(@NonNull final String content, final String customClassname) {
		return String.format(
			" <span class=\"%slabel label-default\">%s</span>",
			normalizeCustomClassName(customClassname),
			content
		);
	}

	/**
	 * Create a bootstrap badge with the following content.
	 *
	 * @param content text of the label.
	 * @param customClassname custom class name to apply to the label
	 * @return the HTML code for this badge.
	 */
	public static String bootstrapBadge(@NonNull final String content, String customClassname) {
		return String.format("<span class=\"%sbadge\">%s</span>", normalizeCustomClassName(customClassname), content);
	}

	/**
	 * Checks if the custom class name is null and returns an empty string if it is.
	 * Otherwise, it returns the custom class name trimmed with a trailing space.
	 *
	 * @param customClassname custom class name to apply to the label
	 * @return the normalized custom class name.
	 */
	private static String normalizeCustomClassName(String customClassname) {
		if (customClassname == null) {
			return "";
		}
		return customClassname.trim() + " ";
	}

	/**
	 * Builds the HTML page file name corresponding to the specified connector identifier.
	 *
	 * @param pageId The page identifier.
	 * @return The corresponding HTML page filename.
	 */
	public static String buildPageFilename(final String pageId) {
		return pageId.toLowerCase() + ".html";
	}

	/**
	 * Replaces commas followed by non-whitespace with spaces in the given input string.
	 *
	 * @param input The input string containing commas to be replaced.
	 * @return A new string with commas followed by non-whitespace replaced by spaces.
	 */
	public static String replaceCommaWithSpace(final String input) {
		// Use a Matcher to find matches
		final Matcher matcher = COMMA_FOLLOWED_BY_NON_WHITESPACE.matcher(input);

		return matcher.replaceAll(" ");
	}

	/**
	 * Replaces special characters in the given string with their corresponding HTML numeric codes.<br>
	 * This will avoid velocity evaluation errors.
	 *
	 * @param value The input string.
	 * @return The string with special characters replaced by their HTML numeric codes.
	 */
	public static String replaceWithHtmlCode(final String value) {
		return value.replace("$", "&#36;");
	}

	/**
	 * Creates an HTML hyperlink reference with the given content.
	 *
	 * @param link the URL to which the hyperlink points.
	 * @param content the text to display as the clickable hyperlink.
	 * @return a string containing the HTML code for the hyperlink
	 */
	public static String hyperlinkRef(final String link, final String content) {
		return String.format("<a href=\"%s\">%s</a>", link, content);
	}

	/**
	 * Creates an HTML hyperlink reference with the given content.
	 *
	 * @param link the path in the `connector` directory.
	 * @param content the text to display as the hyperlink.
	 * @return string containing the HTML code for the hyperlink.
	 */
	public static String gitHubHyperlinkRef(final String link, final String content) {
		return String.format(
			"" +
			"<a href=\"https://github.com/metricshub/community-connectors/tree/main/src/main/connector/%s\" " +
			"<i class=\"fa-brands fa-github\">" +
			"</i>" +
			" %s" +
			"</a>",
			link,
			content
		);
	}
}
