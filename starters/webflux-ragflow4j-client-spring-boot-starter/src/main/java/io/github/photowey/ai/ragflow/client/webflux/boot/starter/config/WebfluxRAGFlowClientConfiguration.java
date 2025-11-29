package io.github.photowey.ai.ragflow.client.webflux.boot.starter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Configuration;

// @formatter:off

/**
 * {@code WebfluxRAGFlowClientConfiguration}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/29
 */
@Configuration
@ConditionalOnMissingClass("org.springframework.boot.autoconfigure.AutoConfiguration")
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class WebfluxRAGFlowClientConfiguration extends AbstractWebfluxRAGFlowClientConfiguration { }

// @formatter:on
