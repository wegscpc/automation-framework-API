package com.api.testing.services;

import com.api.testing.config.Configuration;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class AuthenticationService {
    private static final String LOGIN_ENDPOINT = Configuration.LOGIN_ENDPOINT;
    private static final String REGISTER_ENDPOINT = Configuration.REGISTER_ENDPOINT;

    public Response login(String email, String password) {
        return given()
                .contentType("application/json")
                .body(new LoginRequest(email, password))
                .when()
                .post(LOGIN_ENDPOINT);
    }

    public Response register(String firstName, String lastName, String email, String password,
                           String address, String city, String state, String country,
                           String postcode, String phone) {
        return given()
                .contentType("application/json")
                .body(new RegistrationRequest(firstName, lastName, email, password, password,
                        address, city, state, country, postcode, phone))
                .when()
                .post(REGISTER_ENDPOINT);
    }

    private static class LoginRequest {
        private final String email;
        private final String password;

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() { return email; }
        public String getPassword() { return password; }
    }

    private static class RegistrationRequest {
        private final String first_name;
        private final String last_name;
        private final String email;
        private final String password;
        private final String password_confirmation;
        private final String address;
        private final String city;
        private final String state;
        private final String country;
        private final String postcode;
        private final String phone;

        public RegistrationRequest(String firstName, String lastName, String email,
                                 String password, String passwordConfirmation,
                                 String address, String city, String state,
                                 String country, String postcode, String phone) {
            this.first_name = firstName;
            this.last_name = lastName;
            this.email = email;
            this.password = password;
            this.password_confirmation = passwordConfirmation;
            this.address = address;
            this.city = city;
            this.state = state;
            this.country = country;
            this.postcode = postcode;
            this.phone = phone;
        }

        public String getFirst_name() { return first_name; }
        public String getLast_name() { return last_name; }
        public String getEmail() { return email; }
        public String getPassword() { return password; }
        public String getPassword_confirmation() { return password_confirmation; }
        public String getAddress() { return address; }
        public String getCity() { return city; }
        public String getState() { return state; }
        public String getCountry() { return country; }
        public String getPostcode() { return postcode; }
        public String getPhone() { return phone; }
    }
}
