package com.api.testing.config;

public class Configuration {
    public static final String BASE_URL = "http://api.practicesoftwaretesting.com/api/v1";
    
    // Authentication endpoints
    public static final String REGISTER_ENDPOINT = "/users/register";
    public static final String LOGIN_ENDPOINT = "/users/login";
    
    // Cart and Order endpoints
    public static final String CART_ENDPOINT = "/cart";
    public static final String ORDERS_ENDPOINT = "/orders";
    
    // User related endpoints
    public static final String USER_ADDRESS_ENDPOINT = "/account/addresses";
    
    // Product related endpoints
    public static final String BRANDS_ENDPOINT = "/brands";
    public static final String CATEGORIES_ENDPOINT = "/categories";
    public static final String PRODUCTS_ENDPOINT = "/products";
}
