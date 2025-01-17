package com.api.testing.services;

import com.api.testing.config.Configuration;
import io.restassured.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CategoriesService extends BaseService {
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    public Response getAllCategories() {
        return getBaseRequest()
                .when()
                .get(Configuration.CATEGORIES_ENDPOINT);
    }

    public Response getCategoryById(int categoryId) {
        return getBaseRequest()
                .when()
                .get(Configuration.CATEGORIES_ENDPOINT + "/" + categoryId);
    }

    public Response createCategory(String title, String slug, String description) {
        ObjectNode requestBody = objectMapper.createObjectNode()
                .put("title", title)
                .put("slug", slug)
                .put("description", description);

        return getBaseRequest()
                .body(requestBody.toString())
                .when()
                .post(Configuration.CATEGORIES_ENDPOINT);
    }
}
