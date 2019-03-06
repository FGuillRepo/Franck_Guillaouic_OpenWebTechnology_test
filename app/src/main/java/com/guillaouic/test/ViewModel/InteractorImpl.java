package com.guillaouic.test.ViewModel;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.guillaouic.test.RetroFit.RetroFitClient;
import com.guillaouic.test.database.AppDatabase;
import com.guillaouic.test.model.bookModel.Book;
import com.guillaouic.test.model.bookModel.Item;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


import static com.guillaouic.test.fragment.Search_fragment.repositoryAdapter;
import static com.guillaouic.test.fragment.History_fragment.HistoryAdapter;

public class InteractorImpl implements Interactor {

    OnRequestFinishedListener listener;

    // Request repository and fill recycler adapter

    @Override
    public void getData_Network(final Context context, final OnRequestFinishedListener listener, String title) {
        this.listener = listener;
        Observable<Book> call = new RetroFitClient().getRetroFitService(context).getBooks(title);
        Log.d("book", title);

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Book>() {
                    @Override
                    public void onNext(Book book) {
                        InsertData st = new InsertData(context,book);
                        st.execute();
                        try {
                            for (Item repo : book.getItems()) {
                                repositoryAdapter.add(repo);
                            }

                            listener.onSuccess();

                        } catch (NullPointerException e) {
                            listener.onError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError();
                        Log.d("book", e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void GetData_Database(final Context context, final OnRequestFinishedListener listener) {
        this.listener = listener;
        getDatabase st = new getDatabase(context);
        st.execute();
    }

    class InsertData extends AsyncTask<Void, Void, Void> {
       private AppDatabase database;
       private Book book;

        Context context;
        public InsertData(Context context, Book book){
            this.book=book;
            this.context=context;
        }
        @Override
        protected Void doInBackground(Void... voids) {
           Book books = book;
             database = Room.databaseBuilder(context, AppDatabase.class, "mydb")
                    .allowMainThreadQueries()
                    .build();
            //creating a task
            database.getItemDAO().insertBook(book);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            List<Book> book = database.getItemDAO().fetchListBooks();
            List<Item> items= new ArrayList<>();

            for (Book repo : book) {
                items=repo.getItems();
                break;
            }

            for (Item repo : items) {
            }
        }
    }

        class getDatabase extends AsyncTask<Void, Void, Void> {
            private AppDatabase database;
            Context context;
            public getDatabase(Context context){
                this.context=context;
            }
            @Override
            protected Void doInBackground(Void... voids) {
                database = Room.databaseBuilder(context, AppDatabase.class, "mydb")
                        .allowMainThreadQueries()
                        .build();
                //creating a task
                List<Book> book = database.getItemDAO().fetchListBooks();
                List<Item> items= new ArrayList<>();

                for (Book repo : book) {
                    items=repo.getItems();
                    break;
                }

                for (Item repo : items) {
                    Log.d("book", repo.getId());
                    HistoryAdapter.add(repo);
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                listener.onSuccess();
            }
        }
}

