package com.example.hofprog.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServis {
    private Retrofit retrofit;

    public RetrofitServis() {
        initializateRetrofit();
    }

    private void initializateRetrofit() {
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.235.24:8080")//192.168.59.24  192.168.0.43
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }
    public Retrofit getRetrofit(){
        return retrofit;
    }
}
