package com.api.testing.tests;

import com.api.testing.base.BaseTest;
import com.api.testing.services.UserAddressService;
import com.api.testing.services.AuthenticationService;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;

@Feature("User Address API")
public class UserAddressTest extends BaseTest {
    
    private UserAddressService userAddressService;
    private AuthenticationService authService;
    private final Faker faker = new Faker();
    private String authToken;

    @BeforeClass
    public void setup() {
        userAddressService = new UserAddressService();
        authService = new AuthenticationService();
        
        // Login to get auth token
        Response loginResponse = authService.login("admin@practicesoftwaretesting.com", "welcome01");
        validateStatusCode(loginResponse.getStatusCode(), 200);
        authToken = loginResponse.getBody().jsonPath().getString("access_token");
    }

    @Test
    @Description("Get all addresses")
    public void getAllAddresses() {
        Response response = userAddressService.getAllAddresses();
        validateStatusCode(response.getStatusCode(), 200);
        validateResponseNotNull(response.getBody().jsonPath().getList("data"));
    }

    @Test
    @Description("Get address by ID")
    public void getAddressById() {
        // First get all addresses to get a valid ID
        Response allAddressesResponse = userAddressService.getAllAddresses();
        validateStatusCode(allAddressesResponse.getStatusCode(), 200);
        
        int addressId = allAddressesResponse.getBody().jsonPath().getList("data.id", Integer.class).get(0);
        
        Response response = userAddressService.getAddressById(addressId);
        validateStatusCode(response.getStatusCode(), 200);
        validateResponseNotNull(response.getBody().jsonPath().get("data"));
    }

    @Test
    @Description("Create address")
    public void createAddress() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address1 = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String zipCode = faker.address().zipCode();
        String country = faker.address().country();
        String phone = faker.phoneNumber().phoneNumber();
        
        Response response = userAddressService.createAddress(firstName, lastName, 
                                                   address1, city, state, 
                                                   zipCode, country, phone);
        validateStatusCode(response.getStatusCode(), 201);
        validateResponseNotNull(response.getBody().jsonPath().get("data.id"));
    }

    @Test
    @Description("Update address")
    public void updateAddress() {
        // First create an address
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address1 = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String zipCode = faker.address().zipCode();
        String country = faker.address().country();
        String phone = faker.phoneNumber().phoneNumber();
        
        Response createResponse = userAddressService.createAddress(firstName, lastName, 
                                                                 address1, city, state, 
                                                                 zipCode, country, phone);
        validateStatusCode(createResponse.getStatusCode(), 201);
        
        int addressId = createResponse.getBody().jsonPath().getInt("data.id");
        
        // Update the address
        String newFirstName = faker.name().firstName();
        String newLastName = faker.name().lastName();
        String newAddress1 = faker.address().streetAddress();
        
        Response response = userAddressService.updateAddress(addressId, newFirstName, newLastName, 
                                                           newAddress1, city, state, 
                                                           zipCode, country, phone);
        validateStatusCode(response.getStatusCode(), 200);
    }

    @Test
    @Description("Delete address")
    public void deleteAddress() {
        // First create an address to delete
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address1 = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String zipCode = faker.address().zipCode();
        String country = faker.address().country();
        String phone = faker.phoneNumber().phoneNumber();
        
        Response createResponse = userAddressService.createAddress(firstName, lastName, 
                                                                 address1, city, state, 
                                                                 zipCode, country, phone);
        validateStatusCode(createResponse.getStatusCode(), 201);
        
        int addressId = createResponse.getBody().jsonPath().getInt("data.id");
        
        Response response = userAddressService.deleteAddress(addressId);
        validateStatusCode(response.getStatusCode(), 204);
    }
}
