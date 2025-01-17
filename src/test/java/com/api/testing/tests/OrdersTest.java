package com.api.testing.tests;

import com.api.testing.base.BaseTest;
import com.api.testing.services.OrdersService;
import com.api.testing.services.AuthenticationService;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;

@Feature("Orders API")
public class OrdersTest extends BaseTest {
    
    private OrdersService ordersService;
    private AuthenticationService authService;
    private final Faker faker = new Faker();
    private String authToken;

    @BeforeClass
    public void setup() {
        ordersService = new OrdersService();
        authService = new AuthenticationService();
        
        // Login to get auth token
        Response loginResponse = authService.login("admin@practicesoftwaretesting.com", "welcome01");
        validateStatusCode(loginResponse.getStatusCode(), 200);
        authToken = loginResponse.getBody().jsonPath().getString("access_token");
    }

    @Test
    @Description("Get all orders")
    public void getAllOrders() {
        Response response = ordersService.getAllOrders();
        validateStatusCode(response.getStatusCode(), 200);
        validateResponseNotNull(response.getBody().jsonPath().getList("data"));
    }

    @Test
    @Description("Get order by ID")
    public void getOrderById() {
        // First get all orders to get a valid ID
        Response allOrdersResponse = ordersService.getAllOrders();
        validateStatusCode(allOrdersResponse.getStatusCode(), 200);
        
        int orderId = allOrdersResponse.getBody().jsonPath().getList("data.id", Integer.class).get(0);
        
        Response response = ordersService.getOrderById(orderId);
        validateStatusCode(response.getStatusCode(), 200);
        validateResponseNotNull(response.getBody().jsonPath().get("data"));
    }

    @Test
    @Description("Create order")
    public void createOrder() {
        String paymentMethod = "Cash on Delivery";
        String shippingMethod = "Free Shipping";
        String addressId = "1";  // Assuming this is a valid address ID
        String billingAddressId = "1";  // Assuming this is a valid billing address ID
        
        Response response = ordersService.createOrder(paymentMethod, shippingMethod, 
                                                    addressId, billingAddressId);
        validateStatusCode(response.getStatusCode(), 201);
        validateResponseNotNull(response.getBody().jsonPath().get("data.id"));
    }

    @Test
    @Description("Update order status")
    public void updateOrderStatus() {
        // First create an order
        Response createResponse = ordersService.createOrder("Cash on Delivery", "Free Shipping", 
                                                          "1", "1");
        validateStatusCode(createResponse.getStatusCode(), 201);
        
        int orderId = createResponse.getBody().jsonPath().getInt("data.id");
        String newStatus = "shipped";
        
        Response response = ordersService.updateOrderStatus(orderId, newStatus);
        validateStatusCode(response.getStatusCode(), 200);
    }

    @Test
    @Description("Delete order")
    public void deleteOrder() {
        // First create an order to delete
        Response createResponse = ordersService.createOrder("Cash on Delivery", "Free Shipping", 
                                                          "1", "1");
        validateStatusCode(createResponse.getStatusCode(), 201);
        
        int orderId = createResponse.getBody().jsonPath().getInt("data.id");
        
        Response response = ordersService.deleteOrder(orderId);
        validateStatusCode(response.getStatusCode(), 204);
    }
}
