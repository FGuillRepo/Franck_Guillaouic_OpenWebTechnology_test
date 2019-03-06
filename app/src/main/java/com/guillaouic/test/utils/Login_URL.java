package com.guillaouic.test.activity.Utils;

import android.content.Context;

import instagallery.app.com.gallery.R;

/**
 * Created by Franck Guillaouic on 12/03/2018.
 */

public class Login_URL {

    String BASE_URL;
    String AUTHURL;
    String CLIENT_ID;
    String CLIENT_SECRET;
    String REDIRECT_URI;
    String GRANT_TYPE;
    Context context;
    String AuthenticationURL;

    public Login_URL(Context context){
        this.context=context;

         BASE_URL= context.getResources().getString(R.string.auth_base_url);
    }

    public String getAuthenticationURL() {
        return AuthenticationURL;
    }

    public String getREDIRECT_URI() {
        return REDIRECT_URI;
    }

    public String getCLIENT_ID() {
        return CLIENT_ID;
    }

    public String getBASE_URL() {
        return BASE_URL;
    }

    public String getAUTHURL() {
        return AUTHURL;
    }

    public String getCLIENT_SECRET() {
        return CLIENT_SECRET;
    }

    public String getGRANT_TYPE() {
        return GRANT_TYPE;
    }
}
