package com.tumblr.utils;

import com.tumblr.api.ApiService;
import com.tumblr.api.RetrofitClient;

public class ApiUtils {
    public static final String BASE_URL = "https://api.flickr.com/services/";

    public static ApiService getService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
