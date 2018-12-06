package com.guillaouic.test.fragment;


import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.guillaouic.test.Model.Repository;
import com.guillaouic.test.Network.RequestPresenter;
import com.guillaouic.test.Network.RequestView;
import com.guillaouic.test.activity.RepositoryDetailActivity;

import instagallery.app.com.gallery.R;

import com.guillaouic.test.adapter.RepositoryAdapter;


public class Repository_fragment extends Fragment implements RequestView, RepositoryAdapter.OnItemRepoClickListener {

    private int currentPage = 1;
    private View inflate;
    private RepositoryAdapter.OnItemRepoClickListener onItemRepoClickListener;

    public static ArrayList<Repository> repositoryList = new ArrayList<>();
    public static RepositoryAdapter repositoryAdapter;

    @BindView(R.id.recyclerview) RecyclerView recyclerview;
    @BindView(R.id.animation_nonetwork) ProgressBar animation_nonetwork;
    @BindView(R.id.frame_nonetwork) RelativeLayout frame_nonetwork;
    @BindView(R.id.reconnect) LinearLayout reconnect;
    @BindView(R.id.frame_expand) RelativeLayout frame_expand;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.no_item) LinearLayout no_item;

    private RequestPresenter mPresenter;

    public static Repository_fragment newInstance() {
        Repository_fragment myFragment = new Repository_fragment();
        Bundle args = new Bundle();
        myFragment.setArguments(args);
        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_repository, container, false);
        ButterKnife.bind(this, inflate);
        onItemRepoClickListener = this;
        animation_nonetwork.getIndeterminateDrawable().setColorFilter(getActivity().getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN);

        InitRecyclerView();
        mPresenter = new RequestPresenter(this);
        mPresenter.RequestRepository(getActivity(), currentPage);

        return inflate;
    }


    public void InitRecyclerView() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        repositoryAdapter = new RepositoryAdapter(getActivity(), repositoryList, recyclerview, onItemRepoClickListener);
        recyclerview.setAdapter(repositoryAdapter);

        // Pagination, onEndScroll add next data repository
        repositoryAdapter.setOnLoadMoreListener(new RepositoryAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                // add progress item
                repositoryAdapter.addLoadingFooter();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //remove progress
                        repositoryAdapter.removeLoadingFooter();
                        loadNextPage();
                    }
                }, 100);

            }
        });
    }


    /*
     *   Callback presenter network data call
     */

    @Override
    public void RequestSuccess() {
        repositoryAdapter.notifyDataSetChanged();
        HideNetworkView();
        repositoryAdapter.setLoaded();
    }


    @Override
    public void ShowRequestProgress() {
        ShowLoading();
    }

    @Override
    public void onError() {
    }


    @Override
    public void noNetworkConnectivity() {
        HideNetworkView();
    }

    private void loadNextPage() {
        currentPage++;
        mPresenter.RequestRepository(getActivity(), currentPage);
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
    public void RepoClickEvent(Repository repository) {
        Intent intent = new Intent(getActivity(), RepositoryDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("repository", repository);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}