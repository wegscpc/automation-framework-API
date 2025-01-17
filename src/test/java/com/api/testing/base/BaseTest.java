package com.api.testing.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import com.api.testing.config.Configuration;
import com.api.testing.utils.TestLogger;
import io.qameta.allure.Step;
import java.util.List;
import java.util.Map;

public class BaseTest {
    protected RequestSpecification requestSpec;

    @BeforeClass
    public void baseSetup() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(Configuration.BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Step("Validate response status code is {expectedStatusCode}")
    protected void validateStatusCode(int actualStatusCode, int expectedStatusCode) {
        TestLogger.logInfo(String.format("Validating status code: Expected=%d, Actual=%d", 
                                       expectedStatusCode, actualStatusCode));
        Assert.assertEquals(actualStatusCode, expectedStatusCode, 
                          "Status code validation failed");
    }

    @Step("Validate response field {field} is not null")
    protected void validateResponseNotNull(Object field) {
        TestLogger.logInfo("Validating field is not null");
        Assert.assertNotNull(field, "Response field is null");
    }

    @Step("Validate response field {field} equals {expectedValue}")
    protected void validateResponseField(Object actualValue, Object expectedValue, String field) {
        TestLogger.logInfo(String.format("Validating field '%s': Expected=%s, Actual=%s", 
                                       field, expectedValue, actualValue));
        Assert.assertEquals(actualValue, expectedValue, 
                          String.format("Field '%s' validation failed", field));
    }

    @Step("Validate response contains error message {expectedError}")
    protected void validateErrorMessage(Response response, String expectedError) {
        String actualError = response.getBody().jsonPath().getString("error");
        TestLogger.logInfo(String.format("Validating error message: Expected='%s', Actual='%s'", 
                                       expectedError, actualError));
        Assert.assertEquals(actualError, expectedError, 
                          "Error message validation failed");
    }

    @Step("Validate response list field {field} is not empty")
    protected void validateListNotEmpty(List<?> list, String field) {
        TestLogger.logInfo(String.format("Validating list field '%s' is not empty", field));
        Assert.assertFalse(list.isEmpty(), 
                          String.format("List field '%s' is empty", field));
    }

    @Step("Validate response contains all required fields")
    protected void validateRequiredFields(Response response, List<String> requiredFields) {
        Map<String, Object> responseBody = response.getBody().jsonPath().get("$");
        TestLogger.logInfo("Validating required fields: " + String.join(", ", requiredFields));
        
        for (String field : requiredFields) {
            Assert.assertTrue(responseBody.containsKey(field), 
                            String.format("Required field '%s' is missing", field));
            Assert.assertNotNull(responseBody.get(field), 
                               String.format("Required field '%s' is null", field));
        }
    }

    @Step("Validate response field {field} matches pattern {pattern}")
    protected void validateFieldPattern(String value, String pattern, String field) {
        TestLogger.logInfo(String.format("Validating field '%s' matches pattern '%s'", field, pattern));
        Assert.assertTrue(value.matches(pattern), 
                        String.format("Field '%s' does not match pattern '%s'", field, pattern));
    }

    @Step("Validate response field {field} length is between {minLength} and {maxLength}")
    protected void validateFieldLength(String value, int minLength, int maxLength, String field) {
        TestLogger.logInfo(String.format("Validating field '%s' length is between %d and %d", 
                                       field, minLength, maxLength));
        Assert.assertTrue(value.length() >= minLength && value.length() <= maxLength,
                        String.format("Field '%s' length is not between %d and %d", 
                                    field, minLength, maxLength));
    }

    @Step("Validate numeric field {field} is between {minValue} and {maxValue}")
    protected void validateNumericRange(Number value, Number minValue, Number maxValue, String field) {
        TestLogger.logInfo(String.format("Validating numeric field '%s' is between %s and %s", 
                                       field, minValue, maxValue));
        double doubleValue = value.doubleValue();
        Assert.assertTrue(doubleValue >= minValue.doubleValue() && doubleValue <= maxValue.doubleValue(),
                        String.format("Field '%s' value is not between %s and %s", 
                                    field, minValue, maxValue));
    }

    @Step("Validate response time is less than {maxMillis} milliseconds")
    protected void validateResponseTime(Response response, long maxMillis) {
        long responseTime = response.getTime();
        TestLogger.logInfo(String.format("Validating response time is less than %d ms. Actual: %d ms", 
                                       maxMillis, responseTime));
        Assert.assertTrue(responseTime < maxMillis,
                        String.format("Response time %d ms exceeds maximum %d ms", 
                                    responseTime, maxMillis));
    }
}
