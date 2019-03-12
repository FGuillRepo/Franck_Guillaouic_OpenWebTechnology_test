package com.guillaouic.test.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;


import com.guillaouic.test.Application;
import com.guillaouic.test.repository.DataRepository;
import com.guillaouic.test.activity.DetailsActivity;
import com.guillaouic.test.activity.HistoryActivity;
import com.guillaouic.test.fragment.callback.HistoryClickCallback;
import com.guillaouic.test.fragment.callback.RecyclerViewClickCallback;
import com.guillaouic.test.fragment.callback.SearchClickCallback;
import com.guillaouic.test.pojo.Book;
import com.guillaouic.test.pojo.Item;
import com.guillaouic.test.utils.Message;
import com.guillaouic.test.utils.TextWatcherAdapter;
import com.guillaouic.test.utils.Utils;

import java.util.List;
import java.util.Objects;


/*
 *  BookViewModel : Use to observe books lists, UI click callbacks and searchview.
 * */

public class BookViewModel extends ViewModel {

    private DataRepository repository;
    private com.guillaouic.test.Application context;

    private final ObservableField<String> search = new ObservableField<>("");
    private MutableLiveData<String> errorMessage;
    private LiveData<List<Item>> itemListDatabase;
    private MutableLiveData<Book> bookList;
    public ObservableInt loading;


    public BookViewModel(com.guillaouic.test.Application application) {
        context = application;
        repository = ((Application) context).getRepository();
        itemListDatabase = repository.getBook_database();
        errorMessage = repository.getErrorMessage();
        bookList = repository.getBook();

        loading = new ObservableInt(View.GONE);
    }

    // Constructor use for unit test
    public BookViewModel() {
        repository = new DataRepository();
        bookList = new MutableLiveData<>();
        bookList = repository.getBook();
        loading = new ObservableInt(View.GONE);
    }


    // Observer called in Search_Fragment, listening for network book data call.

    public MutableLiveData<Book> getBooks() {
        return bookList;
    }

    // Observer called in History_Fragment, listening for database call.

    public LiveData<List<Item>> getbookListDatabase() {
        return itemListDatabase;
    }

    // Observer called in Fragments , listening for error call.

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
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

    public DataRepository getRepository() {
        return repository;
    }


    // ClickCallback  Search Button, call Book API

    public final SearchClickCallback mSearchClickCallback = new SearchClickCallback() {
        @Override
        public void onClick(String search) {
            if (Utils.isConnected(context) && getTitle().get().length()>0) {
                repository.loadNetworkData().getBooks_Network(search);
                loading.set(View.VISIBLE);
            } else {
                errorMessage.postValue(Message.no_network.name());
            }
        }
    };

    // ClickCallback RecyclerView click item

    public final RecyclerViewClickCallback recyclerViewClickCallback = new RecyclerViewClickCallback() {
        @Override
        public void onClick(Item item) {
            Intent intent = new Intent(context, DetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", item);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    };

    // ClickCallback History Button

    public final HistoryClickCallback mHistoryClickCallBack = new HistoryClickCallback() {
        @Override
        public void onClick() {
            Intent intent = new Intent(context, HistoryActivity.class);
            context.startActivity(intent);
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

    /*Testing functions*/


}
