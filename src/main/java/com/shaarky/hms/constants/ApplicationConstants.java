package com.shaarky.hms.constants;

public final class ApplicationConstants {

    private ApplicationConstants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }

    // API
    public static final String API_BASE_PATH = "/api/v1";

    // Authentication
    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    // Content Types
    public static final String APPLICATION_JSON = "application/json";

    // Swagger
    public static final String SWAGGER_UI_PATH = "/swagger-ui/**";
    public static final String API_DOCS_PATH = "/v3/api-docs/**";

    // Validation
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int NAME_MAX_LENGTH = 100;
    public static final int EMAIL_MAX_LENGTH = 150;
    public static final int PHONE_MAX_LENGTH = 20;
}