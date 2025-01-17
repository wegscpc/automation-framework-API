package com.api.testing.tests;

import com.api.testing.base.BaseTest;
import com.api.testing.services.ProductsService;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;

@Feature("Products API")
public class ProductsTest extends BaseTest {
    
    private ProductsService productsService;
    private final Faker faker = new Faker();

    @BeforeClass
    public void setup() {
        productsService = new ProductsService();
    }

    @Test
    @Description("Get all products")
    public void getAllProducts() {
        Response response = productsService.getAllProducts();
        validateStatusCode(response.getStatusCode(), 200);
        validateResponseNotNull(response.getBody().jsonPath().getList("data"));
    }

    @Test
    @Description("Get product by ID")
    public void getProductById() {
        // First get all products to get a valid ID
        Response allProductsResponse = productsService.getAllProducts();
        validateStatusCode(allProductsResponse.getStatusCode(), 200);
        
        int productId = allProductsResponse.getBody().jsonPath().getList("data.id", Integer.class).get(0);
        
        Response response = productsService.getProductById(productId);
        validateStatusCode(response.getStatusCode(), 200);
        validateResponseNotNull(response.getBody().jsonPath().get("data"));
    }

    @Test
    @Description("Search products")
    public void searchProducts() {
        String searchQuery = "tool";
        Response response = productsService.searchProducts(searchQuery);
        validateStatusCode(response.getStatusCode(), 200);
    }

    @Test
    @Description("Filter products by category")
    public void filterProductsByCategory() {
        Response response = productsService.filterProductsByCategory(1);
        validateStatusCode(response.getStatusCode(), 200);
    }

    @Test
    @Description("Filter products by brand")
    public void filterProductsByBrand() {
        Response response = productsService.filterProductsByBrand(1);
        validateStatusCode(response.getStatusCode(), 200);
    }

    @Test
    @Description("Create product")
    public void createProduct() {
        String title = faker.commerce().productName();
        String description = faker.lorem().paragraph();
        double price = Double.parseDouble(faker.commerce().price());
        int categoryId = 1;
        int brandId = 1;
        String image = "product-image.jpg";
        
        Response response = productsService.createProduct(title, description, price, 
                                                        categoryId, brandId, image);
        validateStatusCode(response.getStatusCode(), 201);
        validateResponseNotNull(response.getBody().jsonPath().get("data.id"));
    }
}
