package com.api.testing.services;

import com.api.testing.config.Configuration;
import io.restassured.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CartService extends BaseService {
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    public Response getCart() {
        return getBaseRequest()
                .when()
                .get(Configuration.CART_ENDPOINT);
    }

    public Response addToCart(int productId, int quantity) {
        ObjectNode requestBody = objectMapper.createObjectNode()
                .put("product_id", productId)
                .put("quantity", quantity);

        return getBaseRequest()
                .body(requestBody.toString())
                .when()
                .post(Configuration.CART_ENDPOINT);
    }

    public Response updateCartItem(int cartItemId, int quantity) {
        ObjectNode requestBody = objectMapper.createObjectNode()
                .put("quantity", quantity);

        return getBaseRequest()
                .body(requestBody.toString())
                .when()
                .put(Configuration.CART_ENDPOINT + "/" + cartItemId);
    }

    public Response deleteCartItem(int cartItemId) {
        return getBaseRequest()
                .when()
                .delete(Configuration.CART_ENDPOINT + "/" + cartItemId);
    }

    public Response clearCart() {
        return getBaseRequest()
                .when()
                .delete(Configuration.CART_ENDPOINT);
    }
}
