package com.nikitamasevgmail.moneytracker.retrofit;

import com.nikitamasevgmail.moneytracker.data.Price;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("/items")
    Call<List<Price>> getPrice(@Query("type") String type);

    @POST("/items/add")
    void addPrice(@Body() String type);

}
