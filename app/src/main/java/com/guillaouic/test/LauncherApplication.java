package com.guillaouic.test;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.guillaouic.test.activity.SearchActivity;

import com.guillaouic.test.R;


public class LauncherApplication extends Activity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_launcher);

        if (savedInstanceState == null) {
            Intent intent = new Intent(getBaseContext(), SearchActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
