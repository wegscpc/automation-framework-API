package com.api.testing.tests;

import com.api.testing.base.BaseTest;
import com.api.testing.models.UserCredentials;
import com.api.testing.services.AuthenticationService;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Feature("Authentication API")
public class AuthenticationTest extends BaseTest {
    
    private AuthenticationService authService;
    private final Faker faker = new Faker();

    @BeforeClass
    public void setup() {
        authService = new AuthenticationService();
    }

    @Test
    @Description("Login with valid admin credentials")
    @Severity(SeverityLevel.BLOCKER)
    public void loginWithValidAdminCredentials() {
        UserCredentials adminUser = UserCredentials.createAdminCredentials();
        Response response = authService.login(adminUser.getEmail(), adminUser.getPassword());
        validateStatusCode(response.getStatusCode(), 200);
        validateResponseNotNull(response.getBody().jsonPath().get("access_token"));
    }

    @Test
    @Description("Login with valid customer credentials")
    @Severity(SeverityLevel.BLOCKER)
    public void loginWithValidCustomerCredentials() {
        UserCredentials customerUser = UserCredentials.createCustomerCredentials();
        Response response = authService.login(customerUser.getEmail(), customerUser.getPassword());
        validateStatusCode(response.getStatusCode(), 200);
        validateResponseNotNull(response.getBody().jsonPath().get("access_token"));
    }

    @Test
    @Description("Login with invalid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void loginWithInvalidCredentials() {
        Response response = authService.login("invalid@email.com", "wrongpassword");
        validateStatusCode(response.getStatusCode(), 401);
        validateResponseNotNull(response.getBody().jsonPath().get("error"));
    }

    @Test
    @Description("Login with empty credentials")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithEmptyCredentials() {
        Response response = authService.login("", "");
        validateStatusCode(response.getStatusCode(), 422);
        validateResponseNotNull(response.getBody().jsonPath().get("error"));
    }

    @Test
    @Description("Register new user successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void registerNewUser() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 20, true, true, true);
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String country = faker.address().country();
        String postcode = faker.address().zipCode();
        String phone = faker.phoneNumber().cellPhone();
        
        Response response = authService.register(firstName, lastName, email, password,
                address, city, state, country, postcode, phone);
        validateStatusCode(response.getStatusCode(), 201);
        validateResponseNotNull(response.getBody().jsonPath().get("id"));
        
        // Verify the user can login with the new credentials
        Response loginResponse = authService.login(email, password);
        validateStatusCode(loginResponse.getStatusCode(), 200);
        validateResponseNotNull(loginResponse.getBody().jsonPath().get("access_token"));
    }

    @Test
    @Description("Register user with existing email")
    @Severity(SeverityLevel.NORMAL)
    public void registerUserWithExistingEmail() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String country = faker.address().country();
        String postcode = faker.address().zipCode();
        String phone = faker.phoneNumber().cellPhone();
        
        Response response = authService.register(firstName, lastName, 
                "admin@practicesoftwaretesting.com", faker.internet().password(),
                address, city, state, country, postcode, phone);
        validateStatusCode(response.getStatusCode(), 422);
        validateResponseNotNull(response.getBody().jsonPath().get("error"));
    }

    @Test
    @Description("Register user with invalid email format")
    @Severity(SeverityLevel.NORMAL)
    public void registerUserWithInvalidEmail() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String country = faker.address().country();
        String postcode = faker.address().zipCode();
        String phone = faker.phoneNumber().cellPhone();
        
        Response response = authService.register(firstName, lastName, 
                "invalid-email", faker.internet().password(),
                address, city, state, country, postcode, phone);
        validateStatusCode(response.getStatusCode(), 422);
        validateResponseNotNull(response.getBody().jsonPath().get("error"));
    }

    @Test
    @Description("Register user with short password")
    @Severity(SeverityLevel.NORMAL)
    public void registerUserWithShortPassword() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String country = faker.address().country();
        String postcode = faker.address().zipCode();
        String phone = faker.phoneNumber().cellPhone();
        
        Response response = authService.register(firstName, lastName, 
                faker.internet().emailAddress(), "123",
                address, city, state, country, postcode, phone);
        validateStatusCode(response.getStatusCode(), 422);
        validateResponseNotNull(response.getBody().jsonPath().get("error"));
    }
}
