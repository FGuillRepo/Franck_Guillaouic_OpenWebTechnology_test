package com.guillaouic.test.livedataviewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;


import com.guillaouic.test.DataRepository;
import com.guillaouic.test.activity.DetailsActivity;
import com.guillaouic.test.activity.HistoryActivity;
import com.guillaouic.test.activity.RepositoryDetailActivity;
import com.guillaouic.test.adapter.HistoryClickCallback;
import com.guillaouic.test.adapter.RecyclerViewClickCallback;
import com.guillaouic.test.adapter.SearchClickCallback;
import com.guillaouic.test.model.bookModel.Book;
import com.guillaouic.test.model.bookModel.Item;
import com.guillaouic.test.utils.TextWatcherAdapter;

import java.util.List;
import java.util.Objects;


public class BookViewModel extends AndroidViewModel {

    private DataRepository repository;
    public final ObservableField<String> search =  new ObservableField<>("");
    private String TAG = BookViewModel.class.getSimpleName();
    
    private LiveData<Book> ItemList;

    public BookViewModel(Application application){
        super(application);
        repository = ((com.guillaouic.test.Application) application).getRepository();

    }

    /*
     *  Observer called in Search_Fragment, listening for changes.
     * */


    public LiveData<Book> getBookList() {
        if (ItemList == null) {
            ItemList = repository.getBook();
        }
        return ItemList;
    }

    public ObservableField<String> getTitle() {
        return search;
    }

    public String getSearch() {
        return search.get();
    }

    public Book getBook() {
        return ItemList.getValue();
    }


    /*
    *  OnClickListener Callback Search Button
    * */

    public final SearchClickCallback mSearchClickCallback = new SearchClickCallback() {
        @Override
        public void onClick(String search) {
            // Load data network from repository, link livedata repo --> ItemList
                repository.loadNetworkData().getBooks_Network(search);

        }

    };

    public final RecyclerViewClickCallback recyclerViewClickCallback = new RecyclerViewClickCallback() {
        @Override
        public void onClick(Item item) {
            Intent intent = new Intent(getApplication(), DetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", item);
            bundle.putSerializable("book", getBook());
            intent.putExtras(bundle);
            getApplication().startActivity(intent);
        }
    };


    public final HistoryClickCallback mHistoryClickCallBack = new HistoryClickCallback() {
        @Override
        public void onClick() {
            Intent intent = new Intent(getApplication(), HistoryActivity.class);
            getApplication().startActivity(intent);
        }
    };
    /*
     *  Observer for Editext search
     * */



    public TextWatcher watcher = new TextWatcherAdapter() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override public void afterTextChanged(Editable s) {
            if (!Objects.equals(search.get(), s.toString())) {
                search.set(s.toString());
            }
        }
    };


    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "on cleared called");
    }

    public DataRepository getRepository() {
        return repository;
    }

}