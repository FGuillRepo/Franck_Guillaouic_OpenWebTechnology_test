package com.guillaouic.test.fragment;


import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.guillaouic.test.fragment.callback.SubscribeModel;
import com.guillaouic.test.viewmodel.BookViewModel;
import com.guillaouic.test.pojo.bookModel.Book;
import com.guillaouic.test.pojo.bookModel.Item;
import com.guillaouic.test.adapter.RepositoryAdapter;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import instagallery.app.com.gallery.R;
import instagallery.app.com.gallery.databinding.FragmentSearchBinding;

/*
 *  Search_fragment :search a book by title, retrieve data from book API over network.
 *
 * */

public class Search_fragment extends BookParentFragment implements SubscribeModel {

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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        bookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        mBinding.setModel(bookViewModel);
        mBinding.setCallback(bookViewModel.mSearchClickCallback);
        mBinding.setCallbackhistory(bookViewModel.mHistoryClickCallBack);
        subscribeToModel(bookViewModel);
        Setup();
    }

    @Override
    public void subscribeToModel(AndroidViewModel model) {
        ((BookViewModel)model).getBookList().observe(this, new Observer<Book>() {
            @Override
            public void onChanged(@Nullable Book item) {
                if (item.getItems()!= null && item.getItems().size()>0) {
                    repositoryAdapter.setBookList(FilterBook(item));
                    bookViewModel.loading.set(View.INVISIBLE);
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
        setToolbar(mBinding.toolbarLayout.toolbar,getString(R.string.screen_search));
        setButton_History_Visible(true);

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



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_history:
                bookViewModel.mHistoryClickCallBack.onClick();
                return true;
        }
        return false;
    }


}