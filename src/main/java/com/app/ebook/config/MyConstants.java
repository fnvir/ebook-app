package com.app.ebook.config;

import java.util.stream.Stream;

public class MyConstants {
    
    public static final String[] PUBLIC_URLS = { //any http method
            "/swagger-ui/**", 
            "/api-docs/**",
            "/api/auth/**",
    };
    
	public static final String USER_BASE_URL = "/api/users";
    public static final String[] USER_GET_URLS = {
            USER_BASE_URL,
            USER_BASE_URL + "/all", 
            USER_BASE_URL + "/{id}"
    };

    public static final String BOOK_BASE_URL = "/api/books";
    public static final String[] BOOK_GET_URLS = {
        BOOK_BASE_URL,
        BOOK_BASE_URL + "/**"
    };
    
    public static final String REVIEW_BASE_URL = "/api/reviews";
    public static final String[] REVIEW_GET_URLS = {
            REVIEW_BASE_URL,
            REVIEW_BASE_URL + "/**"
    };
    
    public static final String[] ADMIN_URLS = {
            "/api/admin/**"
    };
    
    public static final String[] PUBLIC_GET_URLS = Stream.of(USER_GET_URLS, BOOK_GET_URLS, REVIEW_GET_URLS).flatMap(Stream::of).toArray(String[]::new);
	
}
