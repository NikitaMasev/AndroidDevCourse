package com.nikitamasevgmail.moneytracker.retrofit;

import com.nikitamasevgmail.moneytracker.data.Price;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("/items")
    Call<List<Price>> getPrice(@Query("type") String type);

    @GET("/items/add")
    void addPrice(@Query("type") String type);

}
