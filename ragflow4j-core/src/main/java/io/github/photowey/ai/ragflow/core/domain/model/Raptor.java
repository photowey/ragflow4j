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
package io.github.photowey.ai.ragflow.core.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code Raptor}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Raptor implements Serializable {

    private static final long serialVersionUID = 6802589331999497424L;

    @Min(1)
    @Max(1024)
    @JsonProperty("max_cluster")
    private Integer maxCluster;

    @Min(1)
    @Max(2048)
    @JsonProperty("max_token")
    private Integer maxToken;

    @JsonProperty("prompt")
    private String prompt;

    @Min(0)
    @JsonProperty("random_seed")
    private Integer randomSeed;

    @Min(0)
    @Max(1)
    @JsonProperty("threshold")
    private BigDecimal threshold;

    @JsonProperty("use_raptor")
    private Boolean useRaptor;

    // ----------------------------------------------------------------

    public Integer maxCluster() {
        return maxCluster;
    }

    public Integer maxToken() {
        return maxToken;
    }

    public String prompt() {
        return prompt;
    }

    public Integer randomSeed() {
        return randomSeed;
    }

    public BigDecimal threshold() {
        return threshold;
    }

    public Boolean useRaptor() {
        return useRaptor;
    }
}
