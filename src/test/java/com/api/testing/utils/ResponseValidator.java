package com.api.testing.utils;

import io.restassured.response.Response;
import org.testng.Assert;
import io.qameta.allure.Step;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Utility class for validating API responses
 */
public class ResponseValidator {
    
    @Step("Validate JSON schema")
    public static void validateJsonSchema(Response response, String schemaPath) {
        TestLogger.logInfo("Validating JSON schema against " + schemaPath);
        response.then().assertThat().body(io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }

    @Step("Validate response contains field")
    public static void validateFieldExists(Response response, String jsonPath) {
        TestLogger.logInfo("Validating field exists: " + jsonPath);
        Object field = response.getBody().jsonPath().get(jsonPath);
        Assert.assertNotNull(field, "Field does not exist: " + jsonPath);
    }

    @Step("Validate field value")
    public static void validateFieldValue(Response response, String jsonPath, Object expectedValue) {
        TestLogger.logInfo(String.format("Validating field '%s' equals '%s'", jsonPath, expectedValue));
        Object actualValue = response.getBody().jsonPath().get(jsonPath);
        Assert.assertEquals(actualValue, expectedValue, 
            String.format("Field '%s' value mismatch. Expected: %s, Actual: %s", 
                jsonPath, expectedValue, actualValue));
    }

    @Step("Validate field type")
    public static void validateFieldType(Response response, String jsonPath, Class<?> expectedType) {
        TestLogger.logInfo(String.format("Validating field '%s' is of type %s", jsonPath, expectedType.getSimpleName()));
        Object field = response.getBody().jsonPath().get(jsonPath);
        Assert.assertTrue(expectedType.isInstance(field), 
            String.format("Field '%s' is not of type %s", jsonPath, expectedType.getSimpleName()));
    }

    @Step("Validate array size")
    public static void validateArraySize(Response response, String jsonPath, int expectedSize) {
        TestLogger.logInfo(String.format("Validating array '%s' has size %d", jsonPath, expectedSize));
        List<?> array = response.getBody().jsonPath().getList(jsonPath);
        Assert.assertEquals(array.size(), expectedSize, 
            String.format("Array '%s' size mismatch. Expected: %d, Actual: %d", 
                jsonPath, expectedSize, array.size()));
    }

    @Step("Validate array not empty")
    public static void validateArrayNotEmpty(Response response, String jsonPath) {
        TestLogger.logInfo("Validating array is not empty: " + jsonPath);
        List<?> array = response.getBody().jsonPath().getList(jsonPath);
        Assert.assertFalse(array.isEmpty(), "Array is empty: " + jsonPath);
    }

    @Step("Validate field matches pattern")
    public static void validateFieldPattern(Response response, String jsonPath, String pattern) {
        TestLogger.logInfo(String.format("Validating field '%s' matches pattern '%s'", jsonPath, pattern));
        String value = response.getBody().jsonPath().getString(jsonPath);
        Assert.assertTrue(Pattern.matches(pattern, value), 
            String.format("Field '%s' does not match pattern '%s'", jsonPath, pattern));
    }

    @Step("Validate numeric field range")
    public static void validateNumericRange(Response response, String jsonPath, Number min, Number max) {
        TestLogger.logInfo(String.format("Validating field '%s' is between %s and %s", jsonPath, min, max));
        Number value = response.getBody().jsonPath().get(jsonPath);
        double doubleValue = value.doubleValue();
        Assert.assertTrue(doubleValue >= min.doubleValue() && doubleValue <= max.doubleValue(), 
            String.format("Field '%s' value %s is not between %s and %s", jsonPath, value, min, max));
    }

    @Step("Validate response headers")
    public static void validateHeaders(Response response, Map<String, String> expectedHeaders) {
        TestLogger.logInfo("Validating response headers");
        expectedHeaders.forEach((header, expectedValue) -> {
            String actualValue = response.getHeader(header);
            Assert.assertEquals(actualValue, expectedValue, 
                String.format("Header '%s' value mismatch. Expected: %s, Actual: %s", 
                    header, expectedValue, actualValue));
        });
    }

    @Step("Validate response time")
    public static void validateResponseTime(Response response, long maxMillis) {
        TestLogger.logInfo(String.format("Validating response time is less than %d ms", maxMillis));
        Assert.assertTrue(response.getTime() < maxMillis, 
            String.format("Response time %d ms exceeds maximum %d ms", 
                response.getTime(), maxMillis));
    }

    @Step("Validate error response")
    public static void validateErrorResponse(Response response, int expectedStatusCode, String expectedError) {
        TestLogger.logInfo(String.format("Validating error response: status=%d, error='%s'", 
            expectedStatusCode, expectedError));
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Status code mismatch");
        String actualError = response.getBody().jsonPath().getString("error");
        Assert.assertEquals(actualError, expectedError, "Error message mismatch");
    }

    @Step("Validate pagination")
    public static void validatePagination(Response response, int expectedPage, int expectedPerPage, int expectedTotal) {
        TestLogger.logInfo("Validating pagination");
        Assert.assertEquals(response.getBody().jsonPath().getInt("page"), expectedPage, "Page number mismatch");
        Assert.assertEquals(response.getBody().jsonPath().getInt("per_page"), expectedPerPage, "Per page count mismatch");
        Assert.assertEquals(response.getBody().jsonPath().getInt("total"), expectedTotal, "Total count mismatch");
    }
}
