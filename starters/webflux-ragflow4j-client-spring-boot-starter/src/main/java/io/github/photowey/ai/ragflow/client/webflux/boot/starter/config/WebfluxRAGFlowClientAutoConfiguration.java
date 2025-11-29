package io.github.photowey.ai.ragflow.client.webflux.boot.starter.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

/**
 * {@code WebfluxRAGFlowClientAutoConfiguration}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/29
 */
@AutoConfiguration
@ConditionalOnClass(AutoConfiguration.class)
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class WebfluxRAGFlowClientAutoConfiguration extends AbstractWebfluxRAGFlowClientConfiguration {
}
