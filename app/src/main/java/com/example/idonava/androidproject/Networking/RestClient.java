package com.example.idonava.androidproject.Networking;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient{
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    private static AuthInterceptor authInterceptor = new AuthInterceptor();
    private static OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build();

    private static Retrofit retrofit = new Retrofit.Builder().
            baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();
    public static MoviesService moviesService = retrofit.create(MoviesService.class);



}
