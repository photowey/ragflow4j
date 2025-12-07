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
package io.github.photowey.ai.ragflow.core.exception;

import io.github.photowey.ai.ragflow.core.formatter.StringFormatter;

/**
 * {@code RAGFlowException}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/23
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class RAGFlowException extends RuntimeException {

    // @formatter:off

    public RAGFlowException() { }

    // @formatter:on

    public RAGFlowException(String message, Object... args) {
        super(StringFormatter.format(message, args));
    }

    public RAGFlowException(Throwable cause, String message, Object... args) {
        super(StringFormatter.format(message, args), cause);
    }

    public RAGFlowException(Throwable cause) {
        super(cause);
    }

    public RAGFlowException(
        Throwable cause,
        String message,
        boolean enableSuppression,
        boolean writableStackTrace,
        Object... args) {
        super(StringFormatter.format(message, args), cause, enableSuppression, writableStackTrace);
    }
}
