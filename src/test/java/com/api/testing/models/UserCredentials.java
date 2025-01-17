package com.api.testing.models;

import lombok.Builder;
import lombok.Data;
import com.github.javafaker.Faker;

@Data
@Builder
public class UserCredentials {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postcode;
    private String phone;

    public static UserCredentials createAdminCredentials() {
        return UserCredentials.builder()
                .email("admin@practicesoftwaretesting.com")
                .password("welcome01")
                .build();
    }

    public static UserCredentials createCustomerCredentials() {
        return UserCredentials.builder()
                .email("customer@practicesoftwaretesting.com")
                .password("welcome01")
                .build();
    }

    public static class UserCredentialsBuilder {
        private static final Faker faker = new Faker();

        /**
         * Fills all fields with random data
         */
        public UserCredentialsBuilder withRandomData() {
            this.email = faker.internet().emailAddress();
            this.password = faker.internet().password(8, 20, true, true, true);
            this.firstName = faker.name().firstName();
            this.lastName = faker.name().lastName();
            this.address = faker.address().streetAddress();
            this.city = faker.address().city();
            this.state = faker.address().state();
            this.country = faker.address().country();
            this.postcode = faker.address().zipCode();
            this.phone = faker.phoneNumber().cellPhone();
            return this;
        }

        /**
         * Creates a builder with admin credentials
         */
        public UserCredentialsBuilder withAdminCredentials() {
            this.email = "admin@practicesoftwaretesting.com";
            this.password = "welcome01";
            return this;
        }

        /**
         * Creates a builder with customer credentials
         */
        public UserCredentialsBuilder withCustomerCredentials() {
            this.email = "customer@practicesoftwaretesting.com";
            this.password = "welcome01";
            return this;
        }
    }
}
