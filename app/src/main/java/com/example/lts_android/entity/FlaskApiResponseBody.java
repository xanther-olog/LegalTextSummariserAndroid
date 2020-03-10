package com.example.lts_android.entity;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class FlaskApiResponseBody {
    List<String> sortedSentenceList=new ArrayList<>();
    List<String> paras_list=new ArrayList<>();
    LinkedTreeMap<String,Integer> tncs_dt=new LinkedTreeMap<>();
}
