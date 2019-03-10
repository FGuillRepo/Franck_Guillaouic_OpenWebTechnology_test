package com.guillaouic.test.fragment;


import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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

import com.guillaouic.test.adapter.HistoryClickCallback;
import com.guillaouic.test.adapter.SubscribeModel;
import com.guillaouic.test.livedataviewmodel.BookViewModel;
import com.guillaouic.test.ViewModel.RequestView;
import com.guillaouic.test.model.bookModel.Book;
import com.guillaouic.test.model.bookModel.Item;
import com.guillaouic.test.utils.Utils;
import com.guillaouic.test.adapter.RepositoryAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import instagallery.app.com.gallery.R;
import instagallery.app.com.gallery.databinding.FragmentSearchBinding;


public class Search_fragment extends Fragment implements SubscribeModel {

    private RepositoryAdapter repositoryAdapter;
    private BookViewModel bookViewModel;
    private FragmentSearchBinding mBinding;


    public static Search_fragment newInstance() {
        Search_fragment myFragment = new Search_fragment();
        Bundle args = new Bundle();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        mBinding.setModel(bookViewModel);
        mBinding.setCallback(bookViewModel.mSearchClickCallback);
        mBinding.setCallbackhistory(bookViewModel.mHistoryClickCallBack);
        subscribeToModel(bookViewModel);
        ShowLoading();
        Setup();

    }

    @Override
    public void subscribeToModel(AndroidViewModel model) {
        ((BookViewModel)model).getBookList().observe(this, new Observer<Book>() {
            @Override
            public void onChanged(@Nullable Book item) {
                if (item.getItems()!= null && item.getItems().size()>0) {
                    repositoryAdapter.setBookList(FilterBook(item));
                    HideNetworkView();
                }
            }
        });
    }

    public List<Item> FilterBook(Book item){
        final Collection bookList = new ArrayList<Item>();
            for (Item user : item.getItems()) {
                if (user.getVolumeInfo().getImageLinks() != null && user.getVolumeInfo().getImageLinks().getThumbnail().length() > 0) {
                    bookList.add(user);
                }
            }
            List<Item> book = new ArrayList(bookList);;
           return book;
    }


    public void Setup() {
       // mBinding.toolbar.getTitle().setText(getString(R.string.screen_search));
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.recyclerview.setLayoutManager(layoutManager);
        mBinding.recyclerview.setItemAnimator(new DefaultItemAnimator());
        repositoryAdapter = new RepositoryAdapter(getActivity(),bookViewModel.recyclerViewClickCallback);
        mBinding.recyclerview.setAdapter(repositoryAdapter);
    }

    /*
     *   Callback presenter network data call
     */



    public void ShowLoading() {
        mBinding.recyclerview.setVisibility(View.INVISIBLE);
        mBinding.frameNonetwork.setVisibility(View.VISIBLE);
        mBinding.animationNonetwork.setVisibility(View.VISIBLE);
        mBinding.reconnect.setVisibility(View.INVISIBLE);
    }


    public void HideNetworkView() {
        mBinding.recyclerview.setVisibility(View.VISIBLE);
        mBinding.frameNonetwork.setVisibility(View.INVISIBLE);
        mBinding.animationNonetwork.setVisibility(View.INVISIBLE);
        mBinding.reconnect.setVisibility(View.INVISIBLE);
    }



}