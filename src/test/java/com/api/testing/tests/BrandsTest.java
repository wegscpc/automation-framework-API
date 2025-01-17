package com.api.testing.tests;

import com.api.testing.base.BaseTest;
import com.api.testing.services.BrandsService;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;

@Feature("Brands API")
public class BrandsTest extends BaseTest {
    
    private BrandsService brandsService;
    private final Faker faker = new Faker();

    @BeforeClass
    public void setup() {
        brandsService = new BrandsService();
    }

    @Test
    @Description("Get all brands")
    public void getAllBrands() {
        Response response = brandsService.getAllBrands();
        validateStatusCode(response.getStatusCode(), 200);
        validateResponseNotNull(response.getBody().jsonPath().getList("data"));
    }

    @Test
    @Description("Get brand by ID")
    public void getBrandById() {
        // First get all brands to get a valid ID
        Response allBrandsResponse = brandsService.getAllBrands();
        validateStatusCode(allBrandsResponse.getStatusCode(), 200);
        
        int brandId = allBrandsResponse.getBody().jsonPath().getList("data.id", Integer.class).get(0);
        
        Response response = brandsService.getBrandById(brandId);
        validateStatusCode(response.getStatusCode(), 200);
        validateResponseNotNull(response.getBody().jsonPath().get("data"));
    }

    @Test
    @Description("Create brand")
    public void createBrand() {
        String title = faker.company().name();
        String slug = title.toLowerCase().replace(" ", "-");
        
        Response response = brandsService.createBrand(title, slug);
        validateStatusCode(response.getStatusCode(), 201);
        validateResponseNotNull(response.getBody().jsonPath().get("data.id"));
    }
}
