package com.guillaouic.test.Network;

import android.content.Context;

public interface Presenter {
    void RequestRepository(Context context, int page);
    void onDestroy();
}