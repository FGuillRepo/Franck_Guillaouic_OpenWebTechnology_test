package com.guillaouic.test.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guillaouic.test.ViewModel.RequestPresenter;
import com.guillaouic.test.ViewModel.RequestView;
import com.guillaouic.test.activity.HistoryActivity;
import com.guillaouic.test.model.bookModel.Item;
import com.guillaouic.test.utils.Utils;
import com.guillaouic.test.activity.RepositoryDetailActivity;
import com.guillaouic.test.adapter.RepositoryAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import instagallery.app.com.gallery.R;


public class Search_fragment extends Fragment implements RequestView, RepositoryAdapter.OnItemRepoClickListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private View inflate;
    private RepositoryAdapter.OnItemRepoClickListener onItemRepoClickListener;
    public static RepositoryAdapter repositoryAdapter;
    private  ArrayList<Item> repositoryList = new ArrayList<>();
    private RequestPresenter mPresenter;

    @BindView(R.id.search_editText) EditText search_editText;
    @BindView(R.id.recyclerview) RecyclerView recyclerview;
    @BindView(R.id.animation_nonetwork) ProgressBar animation_nonetwork;
    @BindView(R.id.frame_nonetwork) RelativeLayout frame_nonetwork;
    @BindView(R.id.reconnect) LinearLayout reconnect;
    @BindView(R.id.frame_expand) LinearLayout frame_expand;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.no_item) LinearLayout no_item;
    @BindView(R.id.title_toolbar) TextView title_toolbar;
    @BindView(R.id.search_button) ImageView search_button;
    @BindView(R.id.history_button) Button history_button;

    public static Search_fragment newInstance() {
        Search_fragment myFragment = new Search_fragment();
        Bundle args = new Bundle();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflate = inflater.inflate(R.layout.fragment_search_keyword, container, false);
        ButterKnife.bind(this, inflate);
        swipeContainer.setOnRefreshListener(this);
        search_button.setOnClickListener(this);
        onItemRepoClickListener = this;
        Setup();

        mPresenter = new RequestPresenter(this);

        history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
            }
        });
        return inflate;
    }

    public void Setup() {
        title_toolbar.setText(getString(R.string.app_name));
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        repositoryAdapter = new RepositoryAdapter(getActivity(), repositoryList, recyclerview, onItemRepoClickListener);
        recyclerview.setAdapter(repositoryAdapter);
    }

    /*
     *   Callback presenter network data call
     */

    @Override
    public void RequestSuccess() {
        HideNetworkView();
        repositoryAdapter.notifyDataSetChanged();
        swipeContainer.setRefreshing(false);
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
        frame_expand.setVisibility(View.INVISIBLE);
        frame_nonetwork.setVisibility(View.VISIBLE);
        animation_nonetwork.setVisibility(View.VISIBLE);
        reconnect.setVisibility(View.INVISIBLE);
    }


    public void HideNetworkView() {
        frame_expand.setVisibility(View.VISIBLE);
        frame_nonetwork.setVisibility(View.INVISIBLE);
        animation_nonetwork.setVisibility(View.INVISIBLE);
        reconnect.setVisibility(View.INVISIBLE);
    }

    @Override
    public void RepoClickEvent(Item repository) {
        Intent intent = new Intent(getActivity(), RepositoryDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("repository", repository);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        if (Utils.isConnected(getActivity())){
            mPresenter.RequestBooks_NetworkData(getActivity(), "Code Complete");
            swipeContainer.setRefreshing(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_button:
            String search = search_editText.getText().toString();
            mPresenter.RequestBooks_NetworkData(getActivity(), search);
            break;
        }
    }
}