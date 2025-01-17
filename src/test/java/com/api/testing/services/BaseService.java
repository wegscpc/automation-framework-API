package com.api.testing.services;

import io.restassured.specification.RequestSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import static io.restassured.RestAssured.given;

public abstract class BaseService {
    protected final ObjectMapper objectMapper;

    protected BaseService() {
        this.objectMapper = new ObjectMapper();
    }

    protected RequestSpecification getBaseRequest() {
        return given()
                .contentType("application/json");
    }

    protected String createJsonBody(String... keyValuePairs) {
        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Must provide key-value pairs");
        }

        ObjectNode jsonBody = objectMapper.createObjectNode();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            jsonBody.put(keyValuePairs[i], keyValuePairs[i + 1]);
        }
        return jsonBody.toString();
    }
}
