package com.api.testing.tests;

import com.api.testing.base.BaseTest;
import com.api.testing.services.CategoriesService;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;

@Feature("Categories API")
public class CategoriesTest extends BaseTest {
    
    private CategoriesService categoriesService;
    private final Faker faker = new Faker();

    @BeforeClass
    public void setup() {
        categoriesService = new CategoriesService();
    }

    @Test
    @Description("Get all categories")
    public void getAllCategories() {
        Response response = categoriesService.getAllCategories();
        validateStatusCode(response.getStatusCode(), 200);
        validateResponseNotNull(response.getBody().jsonPath().getList("data"));
    }

    @Test
    @Description("Get category by ID")
    public void getCategoryById() {
        // First get all categories to get a valid ID
        Response allCategoriesResponse = categoriesService.getAllCategories();
        validateStatusCode(allCategoriesResponse.getStatusCode(), 200);
        
        int categoryId = allCategoriesResponse.getBody().jsonPath().getList("data.id", Integer.class).get(0);
        
        Response response = categoriesService.getCategoryById(categoryId);
        validateStatusCode(response.getStatusCode(), 200);
        validateResponseNotNull(response.getBody().jsonPath().get("data"));
    }

    @Test
    @Description("Create category")
    public void createCategory() {
        String title = faker.commerce().department();
        String slug = title.toLowerCase().replace(" ", "-");
        String description = faker.lorem().sentence();
        
        Response response = categoriesService.createCategory(title, slug, description);
        validateStatusCode(response.getStatusCode(), 201);
        validateResponseNotNull(response.getBody().jsonPath().get("data.id"));
    }
}
