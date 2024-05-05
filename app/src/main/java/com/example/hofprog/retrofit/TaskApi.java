package com.example.hofprog.retrofit;

import com.example.hofprog.model.manage;
import com.example.hofprog.model.task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TaskApi {

    @GET("/messag")
    Call<List<task>> getAlltask();
    @POST("/messag")
    Call<task> save(@Body task msg);
    @PUT("/messag/{id}")
    Call<task> update(@Path("id") Integer id, @Body task ts);
}
