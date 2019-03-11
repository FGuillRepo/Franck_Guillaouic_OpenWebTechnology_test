package com.guillaouic.test.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;


import com.guillaouic.test.repository.DataRepository;
import com.guillaouic.test.activity.DetailsActivity;
import com.guillaouic.test.activity.HistoryActivity;
import com.guillaouic.test.fragment.callback.HistoryClickCallback;
import com.guillaouic.test.fragment.callback.RecyclerViewClickCallback;
import com.guillaouic.test.fragment.callback.SearchClickCallback;
import com.guillaouic.test.pojo.bookModel.Book;
import com.guillaouic.test.pojo.bookModel.Item;
import com.guillaouic.test.utils.TextWatcherAdapter;

import java.util.List;
import java.util.Objects;

import instagallery.app.com.gallery.BR;


/*
 *  BookViewModel : Use to observe books lists, UI click callbacks and searchview.
 * */

public class BookViewModel extends AndroidViewModel {

    private DataRepository repository;

    private final ObservableField<String> search = new ObservableField<>("");

    private LiveData<Book> ItemList;
    private LiveData<List<Item>> ItemListDatabase;
    public ObservableInt loading;

    public BookViewModel(Application application) {
        super(application);
        repository = ((com.guillaouic.test.Application) application).getRepository();
        loading = new ObservableInt(View.GONE);

    }

    // Observer called in Search_Fragment, listening for network data call.

    public LiveData<Book> getBookList() {
        if (ItemList == null) {
            ItemList = repository.getBook();
        }
        return ItemList;
    }

    // Observer called in History_Fragment, listening for database call.

    public LiveData<List<Item>> getBookListDatabase() {
        if (ItemListDatabase == null) {
            ItemListDatabase = repository.getData_database();
        }
        return ItemListDatabase;
    }


    public void GetBook_fromDatabase() {
        repository.getBookDatabase();
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

    public DataRepository getRepository() {
        return repository;
    }


    // ClickCallback  Search Button, call Book API

    public final SearchClickCallback mSearchClickCallback = new SearchClickCallback() {
        @Override
        public void onClick(String search) {
            // Load  network data, linked livedata.
            repository.loadNetworkData().getBooks_Network(search);
            loading.set(View.VISIBLE);
        }

    };

    // ClickCallback RecyclerView click item

    public final RecyclerViewClickCallback recyclerViewClickCallback = new RecyclerViewClickCallback() {
        @Override
        public void onClick(Item item) {
            Intent intent = new Intent(getApplication(), DetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", item);
            intent.putExtras(bundle);
            getApplication().startActivity(intent);
        }
    };


    // ClickCallback History Button

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
        @Override
        public void afterTextChanged(Editable s) {
            if (!Objects.equals(search.get(), s.toString())) {
                search.set(s.toString());
            }
        }
    };


    @Override
    protected void onCleared() {
        super.onCleared();
    }

}