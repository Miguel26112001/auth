package com.example.authentication.shared.infrastructure.config;

import com.example.authentication.iam.infrastructure.config.ai.AiToolsConfig;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for registering AI tool callbacks.
 * <p>
 * This class sets up the tool callback provider that enables the AI to invoke
 * methods annotated with {@link org.springframework.ai.tool.annotation.Tool}.
 * </p>
 */
@Configuration
public class ToolConfig {

  /**
   * Creates a {@link ToolCallbackProvider} bean that exposes the methods from
   * {@link AiToolsConfig} as callable tools for the AI.
   *
   * @param aiToolsConfig the configuration bean containing tool-annotated methods
   * @return a provider that makes the tool methods available to the AI chat client
   */
  @Bean
  public ToolCallbackProvider toolCallbackProvider(AiToolsConfig aiToolsConfig) {
    return MethodToolCallbackProvider.builder()
        .toolObjects(aiToolsConfig)
        .build();
  }
}