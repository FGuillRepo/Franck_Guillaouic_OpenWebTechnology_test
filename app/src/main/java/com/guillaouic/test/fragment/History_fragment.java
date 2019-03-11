package com.guillaouic.test.fragment;


import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guillaouic.test.Application;
import com.guillaouic.test.adapter.RepositoryAdapter;
import com.guillaouic.test.fragment.callback.SubscribeModel;
import com.guillaouic.test.viewmodel.BookViewModel;
import com.guillaouic.test.pojo.bookModel.Item;

import java.util.List;

import instagallery.app.com.gallery.R;
import instagallery.app.com.gallery.databinding.FragmentHistoryBinding;

/*
 *  History_fragment :show book history, data retrieve from database
 *
 * */

public class History_fragment extends BookParentFragment implements SubscribeModel {

    public RepositoryAdapter repositoryAdapter;
    private BookViewModel bookViewModel;
    private FragmentHistoryBinding mBinding;


    public static History_fragment newInstance() {
        History_fragment myFragment = new History_fragment();
        Bundle args = new Bundle();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        return mBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bookViewModel = new BookViewModel(Application.getApplication());
        mBinding.setModel(bookViewModel);
        mBinding.setCallbackhistory(bookViewModel.mHistoryClickCallBack);
        subscribeToModel(bookViewModel);

        Setup();

        bookViewModel.GetBook_fromDatabase();
    }

    @Override
    public void subscribeToModel(ViewModel model) {
        ((BookViewModel) model).getBookListDatabase().observe(getActivity(), new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> item) {
                if (item != null) {
                    repositoryAdapter.setBookList(item);
                }
            }
        });
    }

    public void Setup() {
        setToolbar(mBinding.toolbarLayout.toolbar, getString(R.string.screen_history));
        setButton_History_Visible(false);

        //  mBinding.toolbarL.toolbar.setText(getString(R.string.screen_history));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.recyclerview.setLayoutManager(layoutManager);
        mBinding.recyclerview.setItemAnimator(new DefaultItemAnimator());
        repositoryAdapter = new RepositoryAdapter(getActivity(), bookViewModel.recyclerViewClickCallback);
        mBinding.recyclerview.setAdapter(repositoryAdapter);
    }


}