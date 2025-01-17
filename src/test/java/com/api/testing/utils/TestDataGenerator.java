package com.api.testing.utils;

import com.github.javafaker.Faker;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for generating test data
 */
public class TestDataGenerator {
    private static final Faker faker = new Faker();

    /**
     * Generate a random email address
     */
    public static String generateEmail() {
        return faker.internet().emailAddress();
    }

    /**
     * Generate a secure password meeting common requirements
     */
    public static String generateSecurePassword() {
        return String.format("%s#%s@%s!",
            faker.internet().password(8, 10, true, true),
            faker.number().digits(4),
            faker.letterify("??").toUpperCase());
    }

    /**
     * Generate a random phone number
     */
    public static String generatePhoneNumber() {
        return faker.phoneNumber().cellPhone();
    }

    /**
     * Generate a random address
     */
    public static String generateAddress() {
        return faker.address().streetAddress();
    }

    /**
     * Generate a random city
     */
    public static String generateCity() {
        return faker.address().city();
    }

    /**
     * Generate a random state
     */
    public static String generateState() {
        return faker.address().state();
    }

    /**
     * Generate a random country
     */
    public static String generateCountry() {
        return faker.address().country();
    }

    /**
     * Generate a random postal code
     */
    public static String generatePostalCode() {
        return faker.address().zipCode();
    }

    /**
     * Generate a random product name
     */
    public static String generateProductName() {
        return faker.commerce().productName();
    }

    /**
     * Generate a random price between min and max
     */
    public static double generatePrice(double min, double max) {
        return faker.number().randomDouble(2, (long)min, (long)max);
    }

    /**
     * Generate a random quantity between min and max
     */
    public static int generateQuantity(int min, int max) {
        return faker.number().numberBetween(min, max);
    }

    /**
     * Generate a random UUID
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generate a random future date string in ISO format
     */
    public static String generateFutureDate() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(
            faker.number().numberBetween(1, 365));
        return futureDate.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * Generate a random past date string in ISO format
     */
    public static String generatePastDate() {
        LocalDateTime pastDate = LocalDateTime.now().minusDays(
            faker.number().numberBetween(1, 365));
        return pastDate.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * Generate a random description
     */
    public static String generateDescription() {
        return faker.lorem().paragraph();
    }

    /**
     * Generate a random payment method
     */
    public static String generatePaymentMethod() {
        String[] methods = {"credit_card", "debit_card", "paypal", "cash"};
        return methods[faker.number().numberBetween(0, methods.length)];
    }

    /**
     * Generate a random shipping method
     */
    public static String generateShippingMethod() {
        String[] methods = {"standard", "express", "overnight", "pickup"};
        return methods[faker.number().numberBetween(0, methods.length)];
    }

    /**
     * Generate a random order status
     */
    public static String generateOrderStatus() {
        String[] statuses = {"pending", "processing", "shipped", "delivered", "cancelled"};
        return statuses[faker.number().numberBetween(0, statuses.length)];
    }
}
