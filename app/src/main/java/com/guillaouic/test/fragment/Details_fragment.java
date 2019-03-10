package com.guillaouic.test.fragment;


import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guillaouic.test.Application;
import com.guillaouic.test.ViewModel.InteractorImpl;
import com.guillaouic.test.adapter.RepositoryAdapter;
import com.guillaouic.test.database.Database;
import com.guillaouic.test.livedataviewmodel.BookViewModel;
import com.guillaouic.test.model.bookModel.Book;
import com.guillaouic.test.model.bookModel.Item;
import com.guillaouic.test.utils.DatabaseInitializer;

import java.util.ArrayList;
import java.util.List;

import instagallery.app.com.gallery.R;
import instagallery.app.com.gallery.databinding.FragmentDetailBinding;


public class Details_fragment extends BookParentFragment {

    FragmentDetailBinding mBinding;
    public static Details_fragment newInstance() {
        Details_fragment myFragment = new Details_fragment();
        Bundle args = new Bundle();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
           // Toolbar toolbar = mBinding.toolbar.;
            Intent intent = getActivity().getIntent();
            Bundle bundle = intent.getExtras();

            if (bundle != null) {
                Item item = (Item) bundle.getSerializable("item");
                mBinding.setBook(item);
                DatabaseInitializer.populateAsync(Database.getInstance(getActivity()), item);
            }
        }
    }

}