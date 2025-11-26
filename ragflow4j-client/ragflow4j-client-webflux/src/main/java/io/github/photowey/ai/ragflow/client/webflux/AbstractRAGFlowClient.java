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
package io.github.photowey.ai.ragflow.client.webflux;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;

import org.springframework.web.reactive.function.client.WebClient;

import io.github.photowey.ai.ragflow.client.webflux.factory.WebClientFactory;
import io.github.photowey.ai.ragflow.core.constant.MessageConstants;
import io.github.photowey.ai.ragflow.core.domain.model.response.RAGFlowResponse;
import io.github.photowey.ai.ragflow.core.exception.RAGFlowException;
import io.github.photowey.ai.ragflow.core.property.RAGFlowPropertiesGetter;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * {@code AbstractRAGFlowClient}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/23
 */
@Data
@Accessors(fluent = true)
@RequiredArgsConstructor
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public abstract class AbstractRAGFlowClient implements Serializable {

    protected final RAGFlowPropertiesGetter getter;
    protected final WebClientFactory factory;

    protected <T> T unwrap(RAGFlowResponse<T> response, Supplier<String> fx) {
        if (Objects.isNull(response)) {
            throw new RAGFlowException(fx.get() + MessageConstants.UNKNOWN_ERROR);
        }

        return response.unwrap(fx);
    }

    // ----------------------------------------------------------------

    protected WebClient createFormdataWebClient(String deployKey) {
        return this.factory.createFormdataWebClient(deployKey, this.getter);
    }

    // ----------------------------------------------------------------

    protected WebClient createWebClient(String deployKey) {
        return this.factory.createWebClient(deployKey, this.getter);
    }
}
