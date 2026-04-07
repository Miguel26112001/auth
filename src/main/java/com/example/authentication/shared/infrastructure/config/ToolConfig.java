package com.example.authentication.shared.infrastructure.config;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.authentication.iam.infrastructure.config.ai.AiToolsConfig;

@Configuration
public class ToolConfig {

  @Bean
  public ToolCallbackProvider toolCallbackProvider(AiToolsConfig aiToolsConfig) {
    return MethodToolCallbackProvider.builder()
        .toolObjects(aiToolsConfig)
        .build();
  }
}