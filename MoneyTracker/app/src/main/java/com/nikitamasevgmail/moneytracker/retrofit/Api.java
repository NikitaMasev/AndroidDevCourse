package com.nikitamasevgmail.moneytracker.retrofit;

import com.nikitamasevgmail.moneytracker.data.AddPriceResult;
import com.nikitamasevgmail.moneytracker.data.AuthResult;
import com.nikitamasevgmail.moneytracker.data.BalanceResult;
import com.nikitamasevgmail.moneytracker.data.Price;
import com.nikitamasevgmail.moneytracker.data.RemovePriceResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("auth")
    Call<AuthResult> auth(@Query("social_user_id") String userId);

    @GET("items")
    Call<List<Price>> getPrice(@Query("type") String type);

    @POST("items/add")
    Call<AddPriceResult> addPrice(@Query("price") int price, @Query("name") String name, @Query("type") String type);

    @POST("items/remove")
    Call<RemovePriceResult> removePrice(@Query("id") int id);

    @GET("balance")
    Call<BalanceResult> getBalance();

}
