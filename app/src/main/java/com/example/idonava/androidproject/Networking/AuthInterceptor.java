package com.example.idonava.androidproject.Networking;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String originalUrl = originalRequest.url().url().toString();

        String newUrl = originalUrl.concat("?api_key=26e38c69a783e3391c7b11262c21b47e");
        Request.Builder requestBuilder = originalRequest.newBuilder().url(newUrl);

        Request request = requestBuilder.build();
        return chain.proceed(request);

    }
}
