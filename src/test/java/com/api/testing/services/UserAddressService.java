package com.api.testing.services;

import com.api.testing.config.Configuration;
import io.restassured.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class UserAddressService extends BaseService {
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    public Response getAllAddresses() {
        return getBaseRequest()
                .when()
                .get(Configuration.USER_ADDRESS_ENDPOINT);
    }

    public Response getAddressById(int addressId) {
        return getBaseRequest()
                .when()
                .get(Configuration.USER_ADDRESS_ENDPOINT + "/" + addressId);
    }

    public Response createAddress(String firstName, String lastName, String address1,
                                String city, String state, String zipCode, String country,
                                String phone) {
        ObjectNode requestBody = objectMapper.createObjectNode()
                .put("first_name", firstName)
                .put("last_name", lastName)
                .put("address1", address1)
                .put("city", city)
                .put("state", state)
                .put("zip_code", zipCode)
                .put("country", country)
                .put("phone", phone);

        return getBaseRequest()
                .body(requestBody.toString())
                .when()
                .post(Configuration.USER_ADDRESS_ENDPOINT);
    }

    public Response updateAddress(int addressId, String firstName, String lastName,
                                String address1, String city, String state,
                                String zipCode, String country, String phone) {
                ObjectNode requestBody = objectMapper.createObjectNode()
                .put("first_name", firstName)
                .put("last_name", lastName)
                .put("address1", address1)
                .put("city", city)
                .put("state", state)
                .put("zip_code", zipCode)
                .put("country", country)
                .put("phone", phone);

        return getBaseRequest()
                .body(requestBody.toString())
                .when()
                .put(Configuration.USER_ADDRESS_ENDPOINT + "/" + addressId);
    }

    public Response deleteAddress(int addressId) {
        return getBaseRequest()
                .when()
                .delete(Configuration.USER_ADDRESS_ENDPOINT + "/" + addressId);
    }
}
