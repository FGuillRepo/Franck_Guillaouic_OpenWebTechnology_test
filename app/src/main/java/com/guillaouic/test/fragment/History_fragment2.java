package com.guillaouic.test.fragment;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guillaouic.test.Application;
import com.guillaouic.test.ViewModel.RequestView;
import com.guillaouic.test.adapter.RepositoryAdapter;
import com.guillaouic.test.database.Database;
import com.guillaouic.test.livedataviewmodel.BookViewModel;
import com.guillaouic.test.model.bookModel.Book;
import com.guillaouic.test.model.bookModel.Item;
import com.guillaouic.test.utils.DatabaseInitializer;
import com.guillaouic.test.utils.Utils;

import java.util.List;

import instagallery.app.com.gallery.R;
import instagallery.app.com.gallery.databinding.FragmentSearchKeywordBinding;


public class History_fragment2 extends Fragment implements RequestView, SwipeRefreshLayout.OnRefreshListener {

    public  RepositoryAdapter repositoryAdapter;
    private BookViewModel bookViewModel;
    private FragmentSearchKeywordBinding mBinding;



    public static History_fragment2 newInstance() {
        History_fragment2 myFragment = new History_fragment2();
        Bundle args = new Bundle();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_keyword, container, false);
        return mBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        mBinding.setModel(bookViewModel);
        mBinding.setCallbackhistory(bookViewModel.mHistoryClickCallBack);
        subscribeToModel(bookViewModel);
        Setup();
      // bookViewModel.getbook_fromDatabase();
        DatabaseInitializer.RetrieveAsync(Database.getInstance(getActivity()),getActivity());
    }


    private void subscribeToModel(final BookViewModel model) {

      /*  model.getBookList().observe(this, new Observer<Book>() {
            @Override
            public void onChanged(@Nullable Book item) {
                repositoryAdapter.setBookList(item.getItems());
            }
        });*/
    }

    public void Setup() {
       // mBinding.title_toolbar.setText(getString(R.string.app_name));
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.recyclerview.setLayoutManager(layoutManager);
        mBinding.recyclerview.setItemAnimator(new DefaultItemAnimator());
        repositoryAdapter = new RepositoryAdapter(getActivity(),null);
        mBinding.recyclerview.setAdapter(repositoryAdapter);
    }

    /*
     *   Callback presenter network data call
     */

    @Override
    public void RequestSuccess() {
        HideNetworkView();
        repositoryAdapter.notifyDataSetChanged();
        mBinding.swipeContainer.setRefreshing(false);
    }


    @Override
    public void ShowRequestProgress() {

    }

    @Override
    public void onError() {
    }


    @Override
    public void noNetworkConnectivity() {
        HideNetworkView();
    }


    public void ShowLoading() {
        mBinding.frameExpand.setVisibility(View.INVISIBLE);
        mBinding.frameNonetwork.setVisibility(View.VISIBLE);
        mBinding.animationNonetwork.setVisibility(View.VISIBLE);
        mBinding.reconnect.setVisibility(View.INVISIBLE);
    }


    public void HideNetworkView() {
        mBinding.frameExpand.setVisibility(View.VISIBLE);
        mBinding.frameNonetwork.setVisibility(View.INVISIBLE);
        mBinding.animationNonetwork.setVisibility(View.INVISIBLE);
        mBinding.reconnect.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onRefresh() {
        if (Utils.isConnected(getActivity())){
            mBinding.swipeContainer.setRefreshing(true);
        }
    }




}