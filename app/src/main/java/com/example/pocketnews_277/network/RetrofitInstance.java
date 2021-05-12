package com.example.pocketnews_277.network;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitInstance {


    public static String BASE_URL = "https://newsapi.org/";
    private static Retrofit retrofit;

    public static Retrofit getRetroClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(MoshiConverterFactory.create()).build();
        }

        return retrofit;

    }
}
