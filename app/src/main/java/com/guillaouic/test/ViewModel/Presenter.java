package com.guillaouic.test.ViewModel;

import android.content.Context;

public interface Presenter {
    void RequestBooks_NetworkData(Context context, String title);
    void RequestBooks_Database(Context context);
    void onDestroy();
}