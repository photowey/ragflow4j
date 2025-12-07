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
package io.github.photowey.ai.ragflow.core.constant;

import java.util.Objects;

/**
 * {@code RAGFlowConstants}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/23
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public interface RAGFlowConstants {

    interface Header {
        String AUTHORIZATION = "Authorization";
        String CONTENT_TYPE = "Content-Type";
        String BEARER = "Bearer";
        String APPLICATION_JSON = "application/json";
        String MULTIPART_FORM_DATA = "multipart/form-data";
    }

    interface Variable {
        String GLOBAL_DEPLOY_KEY = "global";
    }

    @SuppressWarnings("all")
    interface Configuration {
        String RAGFLOW_PREFIX = "io.github.photowey.ai.ragflow";
        String RAGFLOW_PREFIX_ENV = "IO_GITHUB_PHOTOWEY_AI_RAGFLOW_PREFIX";

        static String determineRAGFlowPropertyPrefix() {
            String prefix = System.getenv(RAGFLOW_PREFIX_ENV);
            if (Objects.nonNull(prefix) && !prefix.trim().isEmpty()) {
                return prefix;
            }

            return System.getProperty(RAGFLOW_PREFIX, RAGFLOW_PREFIX);
        }
    }

    /**
     * Binary data size units (base-1024).
     * Note: KB = 1024 bytes, not 1000.
     */
    interface Bytes {
        int BYTE = 1;
        int UNIT = 1024;
        int KB = BYTE * UNIT;
        int MB = KB * UNIT;
        long GB = MB * UNIT;
        long TB = GB * UNIT;
        long PB = TB * UNIT;
        long EB = PB * UNIT;
    }
}
