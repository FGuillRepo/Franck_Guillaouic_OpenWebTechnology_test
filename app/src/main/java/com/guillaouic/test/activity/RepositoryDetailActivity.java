package com.guillaouic.test.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.guillaouic.test.Model.Repository;
import instagallery.app.com.gallery.R;
import rx.Subscription;
import rx.functions.Action1;


public class RepositoryDetailActivity extends Activity {

    private Subscription buttonClose;
    @BindView(R.id.close_button) ImageView close;

    @BindView(R.id.title) TextView title;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.updatedate) TextView updatedate;
    @BindView(R.id.login) TextView login;
    @BindView(R.id.title_toolbar) TextView title_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositorydetail);
        ButterKnife.bind(this,this);

        title_toolbar.setText(getString(R.string.screen_information));

        if (savedInstanceState==null){
            Intent intent= getIntent();
            Bundle bundle= intent.getExtras();

            if (bundle!=null){
                Repository repository = (Repository) bundle.getSerializable("repository");
                setInformation(title,repository.getName());
                setInformation(description,String.valueOf(repository.getDescription()));
                setInformation(login,repository.getOwner().getLogin());
                setInformation(updatedate,repository.getUpdatedAt());
            }
        }


        buttonClose = RxView.clicks(close).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                RepositoryDetailActivity.this.onBackPressed();
            }
        });

    }


    private void setInformation (TextView textView, String text){
        textView.setText(text);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override public void onDestroy(){
        super.onDestroy();
        if (!buttonClose.isUnsubscribed()){
            buttonClose.unsubscribe();
        }
    }

}