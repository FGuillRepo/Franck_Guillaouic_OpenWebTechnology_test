package com.guillaouic.test.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import instagallery.app.com.gallery.R;

/*
 *  Parent Fragment for Book fragments : Details_fragment, History_fragment, Search_fragment
 *  Use for Toolbar implementation
 *
 * */

public class BookParentFragment extends Fragment{

    private Toolbar toolbar;
    private boolean buttonHistory_visible = true;
    private AppCompatActivity appCompatActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void setToolbar(Toolbar bar, String titleToolbar) {
        toolbar = bar;
        appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        appCompatActivity.getSupportActionBar().setTitle(titleToolbar);
        setHasOptionsMenu(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appCompatActivity.finish();
            }
        });
    }

    public void setButton_History_Visible(boolean button_History_Visibility) {
        buttonHistory_visible = button_History_Visibility;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_history:
                // Do onlick on menu action here
                return true;
        }
        return false;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (buttonHistory_visible) {
            menu.findItem(R.id.action_history).setVisible(true);
        } else {
            menu.findItem(R.id.action_history).setVisible(false);
        }
        invalidateOptionsMenu();
        super.onPrepareOptionsMenu(menu);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void invalidateOptionsMenu() {
        getAppCompatActivity().invalidateOptionsMenu();
    }

    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }

}