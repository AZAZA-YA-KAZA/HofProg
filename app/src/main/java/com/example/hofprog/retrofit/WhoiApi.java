package com.example.hofprog.retrofit;

import com.example.hofprog.model.whoi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WhoiApi {

    @GET("/messa")
    Call<List<whoi>> getAllwhoi();
    @POST("/messa")
    Call<whoi> save(@Body whoi msg);
}
