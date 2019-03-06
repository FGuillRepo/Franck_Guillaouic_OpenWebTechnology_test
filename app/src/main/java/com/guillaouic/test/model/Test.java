package com.guillaouic.test.model;

import android.databinding.ObservableField;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Observable;

public class Test   {

 public ObservableField<String> key=new ObservableField<>();

 public Test(String key){
     this.key.set(key);
 }

}