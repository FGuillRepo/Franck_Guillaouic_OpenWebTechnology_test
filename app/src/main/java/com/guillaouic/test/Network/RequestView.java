package com.guillaouic.test.Network;

public interface RequestView {
    void ShowRequestProgress();

    void onError();

    void RequestSuccess();

    void noNetworkConnectivity();
}