package com.guillaouic.test.utils;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.guillaouic.test.database.Database;
import com.guillaouic.test.model.bookModel.Book;
import com.guillaouic.test.model.bookModel.Item;

import java.util.ArrayList;
import java.util.List;

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final Database db,Book book, Context context) {
        InsertData task = new InsertData(db,book,context);
        task.execute();
    }

    public static void RetrieveAsync(@NonNull final Database db, Context context) {
        GetBook task = new GetBook(db,context);
        task.execute();
    }

    static class InsertData extends AsyncTask<Void, Void, Void> {
        private Book book;
        private Database database;
        Context context;
        public InsertData(Database database,Book book,Context context){
            this.book=book;
            this.database=database;
            this.context=context;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            database.getItemDAO().insertBook(book);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    static class GetBook extends AsyncTask<Void, Void, Void> {
        private Book book;
        private Database database;
        Context context;
        public GetBook(Database database,Context context){
            this.database=database;
            this.context=context;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            LiveData<List<Book>> book = database.getItemDAO().fetchListBooks();
            List<Item> items= new ArrayList<>();

            if(book != null){
                //Do whatever
                for (Book repo : book.getValue()) {
                    Log.d("bookinsert",repo.getKind());
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}