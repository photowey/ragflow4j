/*
 * Copyright (c) 2025-present The Ragflow4j Authors. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.photowey.ai.ragflow.core.formatter;

import java.util.Objects;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * {@code StringFormatter}.
 *
 * @author weichangjun
 * @version 2025.0.22.0.1
 * @since 2025/11/23
 */
public interface StringFormatter {

    /**
     * The default placeholder symbol used to denote argument positions.
     * Example: "Hello {}" will be replaced with "Hello World"
     */
    String MESSAGE_FORMATTER_PLACEHOLDER = "{}";

    /**
     * Formats a string based on the presence of "{}" placeholders.
     *
     * <p>
     * If the message pattern contains "{}", it uses SLF4J's MessageFormatter.
     * Otherwise, it falls back to the standard String.format, which supports %s, %d, etc.
     *
     * @param messagePattern the input string containing placeholders
     * @param args           the arguments to substitute into the pattern
     * @return the formatted string
     */
    static String format(String messagePattern, Object... args) {
        if (Objects.isNull(messagePattern) || messagePattern.trim().isEmpty()) {
            return messagePattern;
        }

        // Use SLF4J formatter if "{}" placeholder is present
        if (messagePattern.contains(MESSAGE_FORMATTER_PLACEHOLDER)) {
            FormattingTuple tuple = MessageFormatter.arrayFormat(messagePattern, args);
            return tuple.getMessage();
        }

        // Default to standard String.format for % style placeholders
        return String.format(messagePattern, args);
    }
}
