package com.api.testing.services;

import com.api.testing.config.Configuration;
import io.restassured.response.Response;

public class BrandsService extends BaseService {
    
    public Response getAllBrands() {
        return getBaseRequest()
                .when()
                .get(Configuration.BRANDS_ENDPOINT);
    }

    public Response getBrandById(int brandId) {
        return getBaseRequest()
                .when()
                .get(Configuration.BRANDS_ENDPOINT + "/" + brandId);
    }

    public Response createBrand(String title, String slug) {
        String requestBody = createJsonBody(
            "title", title,
            "slug", slug
        );

        return getBaseRequest()
                .body(requestBody)
                .when()
                .post(Configuration.BRANDS_ENDPOINT);
    }
}
