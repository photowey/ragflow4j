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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * {@code RootException}.
 *
 * @author weichangjun
 * @version 2025.0.22.0.1
 * @since 2025/11/23
 */
@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class RootException extends RuntimeException {

    public RootException() {
    }

    public RootException(String message) {
        super(message);
    }

    public RootException(String message, Throwable cause) {
        super(message, cause);
    }

    public RootException(Throwable cause) {
        super(cause);
    }

    public RootException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

