package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.example.myapplication.entity.FlaskApiResponseBody;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultPage extends AppCompatActivity {

    RatingBar ratingBar;
    TextView heading;
    ProgressBar progressBar;

    List<String> paras_list;
    List<String> sortedSentenceList;
    Map<String, Integer> tncs_dt;
    Map<String, Integer> rankedDictionary;

    String[] tncs;
    String[] paras;

    int para_no;
    String para;

    ListView listView;
    TextView textView;
    String[] tncsListed;
    String[] listItem;
    String[] wordsToHL;
    int[] weight;

    int reduction_percent_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        progressBar = findViewById(R.id.progress2);

        Bundle b1 = getIntent().getExtras();
        reduction_percent_selected=b1.getInt("pr");

        Log.d("", "onCreate: ");

        FlaskApiResponseBody flaskApiResponseBody= (FlaskApiResponseBody) getIntent().getSerializableExtra("data");
        if(null!=flaskApiResponseBody){
            sortedSentenceList=flaskApiResponseBody.getSortedSentenceList();
            tncs_dt = flaskApiResponseBody.getTncs_dt();
            paras_list = flaskApiResponseBody.getParas_list();
            rankedDictionary = flaskApiResponseBody.getRankedDictionary();
        }else {
            Toast.makeText(getApplicationContext(),
                    "Null object! Phone fek de!",Toast.LENGTH_SHORT).show();
        }

        tncs = new String[sortedSentenceList.size()];
        tncs = sortedSentenceList.toArray(tncs);

        int sortedSentenceListSzAfterReduction=sortedSentenceList.size()-(sortedSentenceList.size()*reduction_percent_selected/100);
        int numbering=0;

        tncsListed = new String[sortedSentenceListSzAfterReduction];

        for (int itr=0; itr<sortedSentenceListSzAfterReduction; itr++){
            String trimmed=tncs[itr].trim();
            String tnc_numbered=Integer.toString(numbering+1)+". "+trimmed.substring(0,1).toUpperCase()+trimmed.substring(1);
            //tnc="-> "+tnc;
            tncsListed[numbering]=tnc_numbered;
            numbering++;
        }

        paras = new String[paras_list.size()];
        paras = paras_list.toArray(paras);

        listView=findViewById(R.id.listView);
        textView=findViewById(R.id.textView);
        listItem = tncsListed;
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_result_page, R.id.textView, listItem);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                final ProgressDialog progressDialog = new ProgressDialog(ResultPage.this,R.style.Theme_AppCompat_DayNight_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Redirecting to paragraph...");
                progressDialog.show();
                String value=tncs[position];
                para_no=tncs_dt.get(value);
                para=(String) Array.get(paras,para_no);
                String para_splitted[] = para.split(" ");
                List<String>  para_as_list;
                para_as_list = Arrays.asList(para_splitted);
                wordsToHL = new String[para_as_list.size()];
                weight = new int[para_as_list.size()];
                int itr=0;
                for(String word : para_as_list){
                    if(rankedDictionary.containsKey(word+" ")){
                        wordsToHL[itr]=word;
                        weight[itr]=rankedDictionary.get(word+" ");
                        itr++;
                    }
                }

                Intent i=new Intent(ResultPage.this,ParaPage.class);
                i.putExtra("currPara",para);
                i.putExtra("wordsToHL",wordsToHL);
                i.putExtra("weight",weight);
                startActivity(i);

                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                },2000);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popupMenu=new PopupMenu(ResultPage.this, listView);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_result,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.copy:
                                Toast.makeText(ResultPage.this, "TnC copied to clipboard!", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.refer:
                                Toast.makeText(ResultPage.this,"Redirecting to paragraph...", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(ResultPage.this,ParaPage.class);
                                i.putExtra("currPara",para);
                                i.putExtra("wordsToHL",wordsToHL);
                                i.putExtra("weight",weight);
                                startActivity(i);
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });

        heading=findViewById(R.id.heading);
        heading.setText("Summary");

        ratingBar=findViewById(R.id.rating);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(ResultPage.this,"You rated the summary "+String.valueOf(rating)+"/5.0 !",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
