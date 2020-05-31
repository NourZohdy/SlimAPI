package com.example.slimapi.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofiClient {
    private static final String BASE_URL = "http://192.168.43.72/myapi/public/";
    private static RetrofiClient mInstance;
    private Retrofit retrofit;

    private RetrofiClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofiClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofiClient();
        }
        return mInstance;
    }

    public API getApi(){
        return  retrofit.create(API.class);
    }
}
