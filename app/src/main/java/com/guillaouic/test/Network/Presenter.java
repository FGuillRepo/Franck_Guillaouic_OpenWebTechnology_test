package com.guillaouic.test.Network;

import android.content.Context;

public interface Presenter {
    void ReqestData(Context context, int page);
    void onDestroy();
}