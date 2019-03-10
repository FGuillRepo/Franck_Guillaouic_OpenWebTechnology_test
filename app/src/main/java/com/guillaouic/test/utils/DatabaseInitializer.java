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



    public static void populateAsync(@NonNull final Database db,Item book) {
        InsertData task = new InsertData(db,book);
        task.execute();
    }


    static class InsertData extends AsyncTask<Void, Void, Void> {
        private Item book;
        private Database database;

        public InsertData(Database database,Item book){
            this.book=book;
            this.database=database;
        }
        @Override
        protected Void doInBackground(Void... voids) {
         //   database.getItemDAO().clearBooktable();
            database.getItemDAO().insertBook(book);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}