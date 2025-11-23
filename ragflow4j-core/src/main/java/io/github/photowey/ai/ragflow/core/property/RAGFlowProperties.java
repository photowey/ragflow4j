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
package io.github.photowey.ai.ragflow.core.property;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import io.github.photowey.ai.ragflow.core.constant.RAGFlowConstants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code RAGFlowProperties}.
 *
 * @author weichangjun
 * @version 2025.0.22.0.1
 * @since 2025/11/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
public class RAGFlowProperties implements Serializable {

    private static final long serialVersionUID = -5444889005187004677L;

    @Valid
    private Client client = new Client();

    @Valid
    @NotEmpty(message = "The RAGFlow servers is required.")
    private Map<String/* deployKey */, Server> servers = new HashMap<>(2);

    @Valid
    @NotEmpty(message = "The RAGFlow datasets is required.")
    private Map<String/* datasetKey */, Dataset/* dataset */> datasets = new HashMap<>(2);

    // ----------------------------------------------------------------

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Client implements Serializable {

        private static final long serialVersionUID = 6325441139889167546L;

        private Sync sync = new Sync();
        private Async async = new Async();

        public Sync sync() {
            return sync;
        }

        public Async async() {
            return async;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sync implements Serializable {

        private static final long serialVersionUID = 6325441139889167546L;

        private boolean enabled = true;

        public boolean enabled() {
            return enabled;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Async implements Serializable {

        private static final long serialVersionUID = 6325441139889167546L;

        private boolean enabled = false;

        public boolean enabled() {
            return enabled;
        }
    }

    // ----------------------------------------------------------------

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Server implements Serializable {

        private static final long serialVersionUID = 3264865155639304359L;

        @NotBlank(message = "The RAGFlow address is required.")
        private String address;
        @NotBlank(message = "The RAGFlow apiKey is required.")
        private String apiKey;

        private Integer timeout = 30_000;

        // ----------------------------------------------------------------

        public String address() {
            return address;
        }

        public String apiKey() {
            return apiKey;
        }

        public Integer timeout(Integer deffaultValue) {
            if (Objects.nonNull(this.timeout)) {
                return this.timeout;
            }

            return deffaultValue;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Dataset implements Serializable {

        private static final long serialVersionUID = -4104176502530581031L;

        private String datasetKey;
        private String datasetId;

        // ----------------------------------------------------------------

        public String datasetKey() {
            return datasetKey;
        }

        public String datasetId() {
            return datasetId;
        }
    }

    public Optional<Server> tryAcquireServer() {
        return this.tryAcquireServer(RAGFlowConstants.Variable.GLOBAL_DEPLOY_KEY);
    }

    public Optional<Server> tryAcquireServer(String deployKey) {
        if (Objects.isNull(deployKey) || deployKey.trim().isEmpty()) {
            return this.tryAcquireServer();
        }

        return Optional.of(this.servers().get(deployKey));
    }

    public Optional<Dataset> tryAcquireDataset(String datasetKey) {
        return Optional.of(this.datasets().get(datasetKey));
    }

    // ----------------------------------------------------------------

    public Client client() {
        return client;
    }

    public Map<String, Server> servers() {
        return servers;
    }

    public Map<String/* datasetKey */, Dataset/* dataset */> datasets() {
        return datasets;
    }
}
