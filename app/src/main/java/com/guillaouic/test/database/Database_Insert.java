package com.guillaouic.test.database;

import android.os.AsyncTask;
import com.guillaouic.test.pojo.bookModel.Item;


/*
 *  Class to insert books item in database asynchronously.
 *  Used in Details_fragment.
 *  Can be use from all class, ex : Database_Insert.insertItemAsync(Database.getInstance(getActivity()), item);
 * */

public class Database_Insert {

    public static void insertItemAsync(final Database db,Item book) {
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
            database.getItemDAO().insertBook(book);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}