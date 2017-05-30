package com.example.clean.app.web.controller;

import org.springframework.http.MediaType;

public final class MediaTypes {

    public static final String APPLICATION_JSON_V1_VALUE = "application/vnd.example.clean.v1+json";
    public final static String APPLICATION_VND_ERROR_JSON_VALUE = "application/vnd.error+json";

    public static final MediaType APPLICATION_JSON_V1 = MediaType.valueOf(APPLICATION_JSON_V1_VALUE);
    public final static MediaType APPLICATION_VND_ERROR_JSON = MediaType.valueOf(APPLICATION_VND_ERROR_JSON_VALUE);
}
