package com.jpmc.theater.util;

/**
 * Model of Validation Response
 *
 * @param valid true if no validation errors, false otherwise
 * @param message empty string if no validation errors, first error message otherwise
 */
public record ValidationResponse(boolean valid, String message) {}
