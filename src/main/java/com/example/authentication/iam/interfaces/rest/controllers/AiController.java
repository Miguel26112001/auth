package com.example.authentication.iam.interfaces.rest.controllers;

import com.example.authentication.shared.application.internal.AiServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that handles AI-related requests.
 * Provides endpoints for interacting with AI services.
 */
@RestController
@RequestMapping("/api/v1/ai")
public class AiController {

  private final AiServiceImpl aiService;

  /**
   * Constructs an AiController with the required AI service implementation.
   *
   * @param aiService the AI service implementation
   */
  public AiController(AiServiceImpl aiService) {
    this.aiService = aiService;
  }

  /**
   * Processes a question and returns an AI-generated response.
   *
   * @param question the user's question to be processed by the AI service
   * @return the AI-generated answer
   */
  @GetMapping
  public String ask(@RequestParam String question) {
    return aiService.ask(question);
  }

  /**
   * Processes a question about users and returns an AI-generated
   * response using user information functions.
   *
   * @param query the user's query about users to be processed by the AI service
   * @return the AI-generated answer with user information
   */
  @GetMapping("/users")
  public String askAboutUsers(@RequestParam String query) {
    return aiService.askAboutUsers(query);
  }
}
