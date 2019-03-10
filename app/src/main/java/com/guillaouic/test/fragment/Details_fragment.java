package com.guillaouic.test.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guillaouic.test.database.Database;
import com.guillaouic.test.model.bookModel.Item;
import com.guillaouic.test.database.Database_Insert;

import instagallery.app.com.gallery.R;
import instagallery.app.com.gallery.databinding.FragmentDetailBinding;

/*
 *  Details_fragment :show book details
 *
 * */

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
            setToolbar(mBinding.toolbarLayout.toolbar, getString(R.string.screen_detail));
            setButton_History_Visible(false);

            Intent intent = getActivity().getIntent();
            Bundle bundle = intent.getExtras();

            if (bundle != null) {
                Item item = (Item) bundle.getSerializable("item");
                mBinding.setBook(item);
                Database_Insert.insertItemAsync(Database.getInstance(getActivity()), item);
            }
        }
    }

}