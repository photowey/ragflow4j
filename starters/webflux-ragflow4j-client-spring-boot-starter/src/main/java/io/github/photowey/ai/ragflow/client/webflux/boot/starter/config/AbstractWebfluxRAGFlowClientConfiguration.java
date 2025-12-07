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
package io.github.photowey.ai.ragflow.client.webflux.boot.starter.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import io.github.photowey.ai.ragflow.client.starter.binder.PropertyBinders;
import io.github.photowey.ai.ragflow.client.webflux.DefaultWebfluxRAGFlowClient;
import io.github.photowey.ai.ragflow.client.webflux.SyncWebfluxRAGFlowClient;
import io.github.photowey.ai.ragflow.client.webflux.core.factory.RAGFlowWebClientFactory;
import io.github.photowey.ai.ragflow.client.webflux.dataset.DefaultSyncWebfluxRAGFlowDatasetClient;
import io.github.photowey.ai.ragflow.client.webflux.dataset.WebfluxRAGFlowDatasetClient;
import io.github.photowey.ai.ragflow.client.webflux.document.DefaultSyncWebfluxRAGFlowDocumentClient;
import io.github.photowey.ai.ragflow.client.webflux.document.WebfluxRAGFlowDocumentClient;
import io.github.photowey.ai.ragflow.core.constant.RAGFlowConstants;
import io.github.photowey.ai.ragflow.core.property.RAGFlowProperties;
import io.github.photowey.ai.ragflow.core.property.RAGFlowPropertiesGetter;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * {@code AbstractWebfluxRAGFlowClientConfiguration}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/29
 */
@Getter
@Accessors(fluent = true)
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public abstract class AbstractWebfluxRAGFlowClientConfiguration implements BeanFactoryPostProcessor {

    protected ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    public RAGFlowProperties ragflowProperties(Environment environment) {
        String prefix = RAGFlowConstants.Configuration.determineRAGFlowPropertyPrefix();
        return PropertyBinders.bind(environment, prefix, RAGFlowProperties.class);
    }

    @Bean
    @ConditionalOnMissingBean
    public RAGFlowWebClientFactory ragflowWebClientFactory() {
        return new RAGFlowWebClientFactory();
    }

    @Bean
    public RAGFlowPropertiesGetter ragflowPropertiesGetter() {
        return () -> this.beanFactory().getBean(RAGFlowProperties.class);
    }

    @Bean
    @ConditionalOnMissingBean
    public WebfluxRAGFlowDatasetClient datasetClient() {
        return new DefaultSyncWebfluxRAGFlowDatasetClient(
            this.ragflowPropertiesGetter(),
            this.ragflowWebClientFactory()
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public WebfluxRAGFlowDocumentClient documentClient() {
        return new DefaultSyncWebfluxRAGFlowDocumentClient(
            this.ragflowPropertiesGetter(),
            this.ragflowWebClientFactory()
        );
    }

    @Bean("syncWebfluxRAGFlowClient")
    @ConditionalOnMissingBean(name = "syncWebfluxRAGFlowClient")
    @SuppressWarnings("all")
    public SyncWebfluxRAGFlowClient syncWebfluxRAGFlowClient() {
        return new DefaultWebfluxRAGFlowClient(
            this.datasetClient(),
            this.documentClient(),
            this.ragflowPropertiesGetter(),
            this.ragflowWebClientFactory()
        );
    }
}
