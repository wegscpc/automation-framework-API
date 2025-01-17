package com.api.testing.config;

public class Configuration {
    public static final String BASE_URL = "https://api.practicesoftwaretesting.com/api";
    
    // Auth endpoints
    public static final String AUTH_ENDPOINT = "/auth/login";
    public static final String REGISTER_ENDPOINT = "/auth/register";
    
    // Product endpoints
    public static final String PRODUCTS_ENDPOINT = "/products";
    public static final String CATEGORIES_ENDPOINT = "/categories";
    public static final String BRANDS_ENDPOINT = "/brands";
    
    // User endpoints
    public static final String USERS_ENDPOINT = "/users";
    public static final String USER_ADDRESS_ENDPOINT = "/user/addresses";
    
    // Cart endpoints
    public static final String CART_ENDPOINT = "/cart";
    
    // Order endpoints
    public static final String ORDERS_ENDPOINT = "/orders";
    
    public static final int DEFAULT_TIMEOUT = 10000;
}
