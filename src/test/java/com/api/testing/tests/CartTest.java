package com.api.testing.tests;

import com.api.testing.base.BaseTest;
import com.api.testing.services.CartService;
import com.api.testing.services.AuthenticationService;
import com.api.testing.services.ProductsService;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;

@Feature("Cart API")
public class CartTest extends BaseTest {
    
    private CartService cartService;
    private AuthenticationService authService;
    private ProductsService productsService;
    private final Faker faker = new Faker();
    private String authToken;
    private int testProductId;

    @BeforeClass
    public void setup() {
        cartService = new CartService();
        authService = new AuthenticationService();
        productsService = new ProductsService();
        
        // Login to get auth token
        Response loginResponse = authService.login("admin@practicesoftwaretesting.com", "welcome01");
        validateStatusCode(loginResponse.getStatusCode(), 200);
        authToken = loginResponse.getBody().jsonPath().getString("access_token");

        // Get a valid product ID for testing
        Response productsResponse = productsService.getAllProducts();
        validateStatusCode(productsResponse.getStatusCode(), 200);
        testProductId = productsResponse.getBody().jsonPath().getList("data.id", Integer.class).get(0);
    }

    @Test
    @Description("Get cart contents")
    public void getCart() {
        Response response = cartService.getCart();
        validateStatusCode(response.getStatusCode(), 200);
    }

    @Test
    @Description("Add item to cart")
    public void addToCart() {
        Response response = cartService.addToCart(testProductId, 1);
        validateStatusCode(response.getStatusCode(), 201);
        validateResponseNotNull(response.getBody().jsonPath().get("data.id"));
    }

    @Test
    @Description("Update cart item quantity")
    public void updateCartItem() {
        // First add item to cart
        Response addResponse = cartService.addToCart(testProductId, 1);
        validateStatusCode(addResponse.getStatusCode(), 201);
        
        int cartItemId = addResponse.getBody().jsonPath().getInt("data.id");
        int newQuantity = 2;
        
        Response response = cartService.updateCartItem(cartItemId, newQuantity);
        validateStatusCode(response.getStatusCode(), 200);
    }

    @Test
    @Description("Delete cart item")
    public void deleteCartItem() {
        // First add item to cart
        Response addResponse = cartService.addToCart(testProductId, 1);
        validateStatusCode(addResponse.getStatusCode(), 201);
        
        int cartItemId = addResponse.getBody().jsonPath().getInt("data.id");
        
        Response response = cartService.deleteCartItem(cartItemId);
        validateStatusCode(response.getStatusCode(), 204);
    }

    @Test
    @Description("Clear cart")
    public void clearCart() {
        // First add an item to cart
        cartService.addToCart(testProductId, 1);
        
        Response response = cartService.clearCart();
        validateStatusCode(response.getStatusCode(), 204);
    }
}
