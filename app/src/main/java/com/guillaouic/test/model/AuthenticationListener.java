package com.guillaouic.test.model;

public interface AuthenticationListener {
 
    void onCodeReceived(String auth_token);
 
}