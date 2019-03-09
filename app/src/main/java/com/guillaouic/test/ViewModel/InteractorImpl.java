package com.guillaouic.test.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.guillaouic.test.Application;
import com.guillaouic.test.RetroFit.RetroFitClient;
import com.guillaouic.test.database.Database;
import com.guillaouic.test.model.bookModel.Book;
import com.guillaouic.test.model.bookModel.Item;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class InteractorImpl implements Interactor {

    OnRequestFinishedListener listener;
    MutableLiveData<Book> data = new MutableLiveData<>();
    private Context context;


    public MutableLiveData<Book> getData() {
        return data;
    }

    // Request repository and fill recycler adapter
    public MutableLiveData<Book> getBooks_Network(String title) {

        Observable<Book> call = new RetroFitClient().getRetroFitServic().getBooks(title);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Book>() {

                    @Override
                    public void onNext(Book book) {
                        Log.d("searchclick", book.getItems().toString());

                        try {
                            data.postValue(book);

                        } catch (NullPointerException e) {
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return data;
    }

    @Override
    public void getData_Network(final Context context, final OnRequestFinishedListener listener, String title) {
        this.listener = listener;
    }

    @Override
    public void GetData_Database(String title) {
        getDatabase st = new getDatabase();
        st.execute();
    }



       public class getDatabase extends AsyncTask<Void, Void, Void> {
            private Database database;
            public getDatabase(){
            }
            @Override
            protected Void doInBackground(Void... voids) {
                database = Database.getInstance(context);
                //creating a task
                List<Book> bookFeteched = database.getItemDAO().fetchListBooks();

                //Do whatever
                for (int i =0;i< bookFeteched.size();i++) {
                    Log.d("bookinsert",bookFeteched.get(i).getKind());
                    break;
                }


                // data.postValue(book);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                listener.onSuccess();
            }
        }
}

