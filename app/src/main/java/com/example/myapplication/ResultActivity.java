package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import com.example.myapplication.entity.FlaskApiResponseBody;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    RelativeLayout rl1,rl2;
    ScrollView sv;
    Button[] b;
    int sum=30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        rl1=(RelativeLayout) findViewById(R.id.rl);
        sv=new ScrollView(ResultActivity.this);
        rl2=new RelativeLayout(ResultActivity.this);
        b=new Button[20];

        for(int i=1;i<15;i++)
        {
            b[i]=new Button(this);
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams((int) RelativeLayout.LayoutParams.WRAP_CONTENT,(int) RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin=50;
            params.topMargin=sum;
            b[i].setText("chu");
            b[i].setLayoutParams(params);
            rl2.addView(b[i]);
            sum=sum+100;
        }

        sv.addView(rl2);
        rl1.addView(sv);


        /*ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView1);
        LinearLayout myLayout = new LinearLayout(this);
        myLayout.setOrientation(LinearLayout.VERTICAL);
        int i, noOfSentences=10, prev=0;
        for (i=0;i<noOfSentences;i++){
            LinearLayout.LayoutParams rel_btn = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
            Button myButton = new Button(this);
            myButton.setId(i);
            rel_btn.height=WRAP_CONTENT;
            rel_btn.width=MATCH_PARENT;
            myButton.setLayoutParams(rel_btn);
            myButton.setText("SentenceSentenceSentenceSentenceSentenceSentenceSentenceSentenceSentenceSentenceSentenceSentenceSentenceSentenceSentenceSentenceSentenceSentenceSentenceSentenceSentenceSentenceSentenceSentence"+i);
            if(i==0){
                rel_btn.topMargin=0;
            }
            prev=myButton.getId();
            myLayout.addView(myButton);
        }
        setContentView(myLayout);*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
