package com.example.hofprog.retrofit;

import com.example.hofprog.model.manage;
import com.example.hofprog.model.proger;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProgerApi {

    @GET("/message")
    Call<List<proger>> getAllprogger();
    @POST("/message")
    Call<proger> save(@Body proger msg);
    @PUT("/message/{id}")
    Call<proger> update(@Path("id") Integer id, @Body proger msg);
}
