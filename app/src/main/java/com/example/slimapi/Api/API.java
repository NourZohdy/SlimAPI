package com.example.slimapi.Api;

import com.example.slimapi.Model.DefaulteResponce;
import com.example.slimapi.Model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {

    @FormUrlEncoded
    @POST("createuser")
    Call<DefaulteResponce> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("school") String school
    );

    @FormUrlEncoded
    @POST("userlogin")
    Call<LoginResponse> userlogin(
            @Field("email") String email,
            @Field("password") String password
    );
}
