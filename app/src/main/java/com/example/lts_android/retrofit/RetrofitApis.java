package com.example.lts_android.retrofit;

import com.example.lts_android.entity.SourceFileRequestBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitApis {

    @POST("/main/lts")
    Call<String> sendSourceFileToFlask(@Body SourceFileRequestBody sourceFileRequestBody);
}
