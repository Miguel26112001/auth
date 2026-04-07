package com.example.authentication.shared.application.internal;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

/**
 * Service implementation for AI-related operations.
 * Provides chat functionality using Spring AI's ChatClient.
 */
@Service
public class AiServiceImpl {

  private final ChatClient chatClient;
  private final ToolCallbackProvider toolCallbackProvider;

  /**
   * Constructs an AiServiceImpl with the required ChatClient builder.
   *
   * @param builder the ChatClient builder used to create the ChatClient instance
   */
  public AiServiceImpl(ChatClient.Builder builder,
                       ToolCallbackProvider toolCallbackProvider) {
    this.chatClient = builder.build();
    this.toolCallbackProvider = toolCallbackProvider;
  }

  /**
   * Sends a message to the AI and returns the response.
   *
   * @param message the user's input message to be sent to the AI
   * @return the AI-generated response content
   */
  public String ask(String message) {
    return chatClient
        .prompt()
        .user(message)
        .call()
        .content();
  }

  /**
   * Sends a user query about users to the AI, enabling tool usage for user retrieval.
   * <p>
   * This method configures the AI assistant with a system prompt that instructs it to
   * invoke the {@code getUserByUsername} tool when the user asks about a specific user.
   * </p>
   *
   * @param userInput the user's input query about users
   * @return the AI-generated response content, potentially incorporating data from
   *         the {@code getUserByUsername} tool call
   */
  public String askAboutUsers(String userInput) {
    return chatClient
        .prompt()
        .system("""
            You are an assistant.
            If the user asks about a user, call the tool getUserByUsername.
        """)
        .user(userInput)
        .toolCallbacks(toolCallbackProvider)
        .call()
        .content();
  }
}
