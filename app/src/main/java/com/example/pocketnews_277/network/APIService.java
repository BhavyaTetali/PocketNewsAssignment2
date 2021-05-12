package com.example.pocketnews_277.network;

import com.example.pocketnews_277.model.NewsDataModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET(value = "v2/everything?language=en&sources=fox-news")
    Call<NewsDataModel> newsList(@Query("apiKey") String api_key, @Query("from") String today_date);

    @GET(value = "v2/everything?language=en&sources=fox-news")
    Call<NewsDataModel> searchNewsList(@Query("apiKey") String api_key, @Query("from") String today_date, @Query("q") String search_key);

    @GET(value = "v2/top-headlines?language=en")
    Call<NewsDataModel> trendingNewsList(@Query("apiKey") String api_key, @Query("category") String category);

}
