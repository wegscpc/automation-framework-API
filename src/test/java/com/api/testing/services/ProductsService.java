package com.api.testing.services;

import com.api.testing.config.Configuration;
import io.restassured.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ProductsService extends BaseService {
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    public Response getAllProducts() {
        return getBaseRequest()
                .when()
                .get(Configuration.PRODUCTS_ENDPOINT);
    }

    public Response getProductById(int productId) {
        return getBaseRequest()
                .when()
                .get(Configuration.PRODUCTS_ENDPOINT + "/" + productId);
    }

    public Response searchProducts(String query) {
        return getBaseRequest()
                .queryParam("query", query)
                .when()
                .get(Configuration.PRODUCTS_ENDPOINT);
    }

    public Response filterProductsByCategory(int categoryId) {
        return getBaseRequest()
                .queryParam("category", categoryId)
                .when()
                .get(Configuration.PRODUCTS_ENDPOINT);
    }

    public Response filterProductsByBrand(int brandId) {
        return getBaseRequest()
                .queryParam("brand", brandId)
                .when()
                .get(Configuration.PRODUCTS_ENDPOINT);
    }

    public Response createProduct(String title, String description, double price, 
                                int categoryId, int brandId, String image) {
        ObjectNode requestBody = objectMapper.createObjectNode()
                .put("title", title)
                .put("description", description)
                .put("price", price)
                .put("category_id", categoryId)
                .put("brand_id", brandId)
                .put("image", image);

        return getBaseRequest()
                .body(requestBody.toString())
                .when()
                .post(Configuration.PRODUCTS_ENDPOINT);
    }
}
