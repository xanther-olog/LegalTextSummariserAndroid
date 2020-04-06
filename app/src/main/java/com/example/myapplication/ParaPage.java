package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.CellSignalStrength;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.String.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ParaPage extends AppCompatActivity {
    String lookUp;
    Map<String, Integer> rankedDictionary;
    TextView paraTextView;
    TextView paraTextView2;
    ToggleButton changeParaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_para_page);
        paraTextView=findViewById(R.id.textViewPara1);
        paraTextView2=findViewById(R.id.textViewPara2);
        changeParaView=findViewById(R.id.toggleButton1);

        final Bundle paraDetails = getIntent().getExtras();
        final String para = paraDetails.getString("currPara");

        final String para_copy = para;

        final String words_to_hl[] = paraDetails.getStringArray("wordsToHL");
        int weight[] = paraDetails.getIntArray("weight");

        final SpannableString paragraph = new SpannableString(para);
        paraTextView.setText(para);
        changeParaView.setChecked(false);
        changeParaView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    for (int itr=0; itr<words_to_hl.length;itr++){
                        if(words_to_hl[itr]!=null) {
                            final int finalItr = itr;
                            ClickableSpan clickableSpan = new ClickableSpan() {
                                @Override
                                public void onClick(@NonNull View paraTextView) {
                                    startActivity(new Intent(ParaPage.this,definitionWebView.class).putExtra("lookUp",words_to_hl[finalItr]));
                                }

                                @Override
                                public void updateDrawState(@NonNull TextPaint ds) {
                                    super.updateDrawState(ds);
                                    ds.setUnderlineText(false);
                                }
                            };
                            lookUp = words_to_hl[itr];
                            paragraph.setSpan(clickableSpan, para.indexOf(lookUp), para.indexOf(lookUp) + lookUp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }

                    paraTextView2.setText("");
                    paraTextView.setText(paragraph, TextView.BufferType.SPANNABLE);
                    paraTextView.setMovementMethod(LinkMovementMethod.getInstance());
                    paraTextView.setSelected(true);
                }

                else {
                    paraTextView.setText("");
                    paraTextView2.setText(para_copy);
                }
            }
        });

    }
}
