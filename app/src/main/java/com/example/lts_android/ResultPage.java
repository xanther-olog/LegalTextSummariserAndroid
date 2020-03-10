package com.example.lts_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lts_android.entity.FlaskApiResponseBody;

import java.io.Serializable;

public class ResultPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        System.out.println();
        FlaskApiResponseBody flaskApiResponseBody= (FlaskApiResponseBody) getIntent().getSerializableExtra("data");


    }
}
