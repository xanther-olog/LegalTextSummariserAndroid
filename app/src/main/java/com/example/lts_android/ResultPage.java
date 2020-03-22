package com.example.lts_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lts_android.entity.FlaskApiResponseBody;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ResultPage extends AppCompatActivity {

    TextView demoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        FlaskApiResponseBody flaskApiResponseBody= (FlaskApiResponseBody) getIntent().getSerializableExtra("data");
        if(null!=flaskApiResponseBody){
            List<String> paras_list = flaskApiResponseBody.getParas_list();
            List<String> sortedSentenceList=flaskApiResponseBody.getSortedSentenceList();
            Map<String, Integer> tncs = flaskApiResponseBody.getTncs_dt();

            demoTextView=findViewById(R.id.demoTextView);
            demoTextView.setText(paras_list.get(0));
        }else {
            Toast.makeText(getApplicationContext(),
                    "Null object! Phone fek de!",Toast.LENGTH_SHORT).show();
        }

    }
}
