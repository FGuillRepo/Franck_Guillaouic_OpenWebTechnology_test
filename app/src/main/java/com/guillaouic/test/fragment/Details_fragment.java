package com.guillaouic.test.fragment;


import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import instagallery.app.com.gallery.databinding.RowRecyclerLayoutitemBinding;


public class Details_fragment extends Fragment {

    public static RepositoryAdapter repositoryAdapter;
    private BookViewModel mainActivityViewModel;
    Item repository;
    RowRecyclerLayoutitemBinding mBinding;

    public static Details_fragment newInstance() {
        Details_fragment myFragment = new Details_fragment();
        Bundle args = new Bundle();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         mBinding = DataBindingUtil.inflate(inflater, R.layout.row_recycler_layoutitem, container, false);
        return mBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent= getActivity().getIntent();
        Bundle bundle= intent.getExtras();

        if (bundle!=null){
            Item item = (Item) bundle.getSerializable("item");
            Book book = (Book) bundle.getSerializable("book");
            mBinding.setBook(item);

        //   Database databasee= Database.getInstance(getActivity());
        //    databasee.insertInDatabase(databasee,book);
            DatabaseInitializer.populateAsync(Database.getInstance(getActivity()),book,getActivity());
            Log.d("bookkkk",book.getKind());
        }
    }

  /*  public void Setup() {
       // mBinding.title_toolbar.setText(getString(R.string.app_name));
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.recyclerview.setLayoutManager(layoutManager);
        mBinding.recyclerview.setItemAnimator(new DefaultItemAnimator());
        repositoryAdapter = new RepositoryAdapter(getActivity(),null);
        mBinding.recyclerview.setAdapter(repositoryAdapter);
    }*/

    /*
     *   Callback presenter network data call
     */

   /* @Override
    public void RequestSuccess() {
        HideNetworkView();
        repositoryAdapter.notifyDataSetChanged();
        mBinding.swipeContainer.setRefreshing(false);
    }*/


   /* @Override
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
    }*/




}