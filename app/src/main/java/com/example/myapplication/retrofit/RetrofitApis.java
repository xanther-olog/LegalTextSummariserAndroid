package com.example.myapplication.retrofit;

import com.example.myapplication.entity.FlaskApiResponseBody;
import com.example.myapplication.entity.SourceFileRequestBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitApis {

    @POST("/main/myapplication")
    Call<FlaskApiResponseBody> sendSourceFileToFlask(@Body SourceFileRequestBody sourceFileRequestBody);
}
