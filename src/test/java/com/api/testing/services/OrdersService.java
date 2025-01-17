package com.api.testing.services;

import com.api.testing.config.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.response.Response;

public class OrdersService extends BaseService {
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    public Response getAllOrders() {
        return getBaseRequest()
                .when()
                .get(Configuration.ORDERS_ENDPOINT);
    }

    public Response getOrderById(int orderId) {
        return getBaseRequest()
                .when()
                .get(Configuration.ORDERS_ENDPOINT + "/" + orderId);
    }

    public Response createOrder(String paymentMethod, String shippingMethod, 
                              String addressId, String billingAddressId) {
        ObjectNode requestBody = objectMapper.createObjectNode()
                .put("payment_method", paymentMethod)
                .put("shipping_method", shippingMethod)
                .put("address_id", addressId)
                .put("billing_address_id", billingAddressId);

        return getBaseRequest()
                .body(requestBody.toString())
                .when()
                .post(Configuration.ORDERS_ENDPOINT);
    }

    public Response updateOrderStatus(int orderId, String status) {
        ObjectNode requestBody = objectMapper.createObjectNode()
                .put("status", status);

        return getBaseRequest()
                .body(requestBody.toString())
                .when()
                .put(Configuration.ORDERS_ENDPOINT + "/" + orderId);
    }

    public Response deleteOrder(int orderId) {
        return getBaseRequest()
                .when()
                .delete(Configuration.ORDERS_ENDPOINT + "/" + orderId);
    }
}
