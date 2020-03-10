package com.example.lts_android.retrofit;

import com.example.lts_android.entity.FlaskApiResponseBody;
import com.example.lts_android.entity.SourceFileRequestBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitApis {

    @POST("/main/myapplication")
    Call<FlaskApiResponseBody> sendSourceFileToFlask(@Body SourceFileRequestBody sourceFileRequestBody);
}
