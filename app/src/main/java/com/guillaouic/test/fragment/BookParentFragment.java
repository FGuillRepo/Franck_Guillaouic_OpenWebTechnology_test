package com.guillaouic.test.fragment;


import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guillaouic.test.database.Database;
import com.guillaouic.test.model.bookModel.Item;
import com.guillaouic.test.utils.DatabaseInitializer;

import instagallery.app.com.gallery.R;
import instagallery.app.com.gallery.databinding.FragmentDetailBinding;


public class BookParentFragment extends Fragment {

    private Toolbar toolbar;
    private ImageView back_button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void setToolbar(final Activity activity, ImageView back_button, TextView title, int titleToolbar){
        title.setText(titleToolbar);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

}