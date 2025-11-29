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
import io.github.photowey.ai.ragflow.client.webflux.dataset.DefaultWebfluxSyncRAGFlowDatasetClient;
import io.github.photowey.ai.ragflow.client.webflux.dataset.WebfluxRAGFlowDatasetClient;
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
        return new DefaultWebfluxSyncRAGFlowDatasetClient(
            this.ragflowPropertiesGetter(),
            this.ragflowWebClientFactory()
        );
    }

    @Bean("syncWebfluxRAGFlowClient")
    @ConditionalOnMissingBean(name = "syncWebfluxRAGFlowClient")
    public SyncWebfluxRAGFlowClient syncWebfluxRAGFlowClient() {
        return new DefaultWebfluxRAGFlowClient(
            this.datasetClient(),
            this.ragflowPropertiesGetter(),
            this.ragflowWebClientFactory()
        );
    }
}
