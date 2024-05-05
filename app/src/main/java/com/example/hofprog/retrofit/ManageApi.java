package com.example.hofprog.retrofit;

import com.example.hofprog.model.manage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ManageApi {

    @GET("/messages")
    Call<List<manage>> getAllmanage();
    @POST("/messages")
    Call<manage> save(@Body manage msg);
    @PUT("/messages/{id}")
    Call<manage> update(@Path("id") Integer id, @Body manage msg);
}
