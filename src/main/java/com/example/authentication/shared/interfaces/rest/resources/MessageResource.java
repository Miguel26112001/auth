package com.example.authentication.shared.interfaces.rest.resources;

/**
 * Resource used to return a simple message in REST responses.
 *
 * <p>Typically used for error messages or informational responses.</p>
 */
public record MessageResource(String message) {
}
