package com.api.testing.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.qameta.allure.Step;

/**
 * Utility class for test logging
 */
public class TestLogger {
    private static final Logger logger = LoggerFactory.getLogger(TestLogger.class);

    @Step("{message}")
    public static void logInfo(String message) {
        logger.info(message);
    }

    @Step("WARNING: {message}")
    public static void logWarning(String message) {
        logger.warn(message);
    }

    @Step("ERROR: {message}")
    public static void logError(String message) {
        logger.error(message);
    }

    @Step("ERROR: {message}")
    public static void logError(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    @Step("Starting test: {testName}")
    public static void logTestStart(String testName) {
        logger.info("=== Starting Test: {} ===", testName);
    }

    @Step("Completed test: {testName}")
    public static void logTestEnd(String testName) {
        logger.info("=== Completed Test: {} ===", testName);
    }

    @Step("API Request - {method} {endpoint}")
    public static void logApiRequest(String method, String endpoint) {
        logger.info("API Request - {} {}", method, endpoint);
    }

    @Step("API Response - Status: {statusCode}")
    public static void logApiResponse(int statusCode, String responseBody) {
        logger.info("API Response - Status: {}", statusCode);
        logger.debug("Response Body: {}", responseBody);
    }

    @Step("Test Data - {description}")
    public static void logTestData(String description, Object data) {
        logger.info("Test Data - {}: {}", description, data);
    }

    @Step("Validation - {description}")
    public static void logValidation(String description, Object expected, Object actual) {
        logger.info("Validation - {}", description);
        logger.info("Expected: {}", expected);
        logger.info("Actual: {}", actual);
    }

    @Step("Setup - {description}")
    public static void logSetup(String description) {
        logger.info("Setup - {}", description);
    }

    @Step("Cleanup - {description}")
    public static void logCleanup(String description) {
        logger.info("Cleanup - {}", description);
    }
}
