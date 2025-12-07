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
package io.github.photowey.ai.ragflow.client.webflux.core.builder;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import io.github.photowey.ai.ragflow.core.domain.query.AbstractPaginationQuery;
import io.github.photowey.ai.ragflow.core.domain.query.dataset.ListDatasetQuery;
import io.github.photowey.ai.ragflow.core.domain.query.document.ListDocumentQuery;

/**
 * {@code QueryParamBuilder}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/26
 */
public final class QueryParamBuilder {

    // @formatter:off

    private QueryParamBuilder() { }

    // @formatter:on

    public static MultiValueMap<String, String> toQueryParams(ListDatasetQuery query) {
        return toPaginationQueryParams(query);
    }

    public static MultiValueMap<String, String> toQueryParams(ListDocumentQuery query) {
        MultiValueMap<String, String> params = toPaginationQueryParams(query);

        if (query.getCreateTimeFrom() != null && query.getCreateTimeFrom() > 0) {
            params.add("create_time_from", query.getCreateTimeFrom().toString());
        }
        if (query.getCreateTimeTo() != null && query.getCreateTimeTo() > 0) {
            params.add("create_time_to", query.getCreateTimeTo().toString());
        }

        if (query.getSuffix() != null) {
            for (String suffix : query.getSuffix()) {
                if (suffix != null) {
                    params.add("suffix", suffix);
                }
            }
        }

        if (query.getRun() != null) {
            for (String run : query.getRun()) {
                if (run != null) {
                    params.add("run", run);
                }
            }
        }

        return params;
    }

    private static MultiValueMap<String, String> toPaginationQueryParams(AbstractPaginationQuery query) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        if (query.getPage() != null && query.getPage() > 0) {
            params.add("page", query.getPage().toString());
        }
        if (query.getPageSize() != null && query.getPageSize() > 0) {
            params.add("page_size", query.getPageSize().toString());
        }
        if (query.getOrderby() != null) {
            params.add("orderby", query.getOrderby());
        }
        if (query.getDesc() != null) {
            params.add("desc", query.getDesc().toString());
        }

        if (query.getId() != null) {
            params.add("id", query.getId());
        }
        if (query.getName() != null) {
            params.add("name", query.getName());
        }

        if (query.getKeywords() != null) {
            params.add("keywords", query.getKeywords());
        }

        return params;
    }
}
