package com.guillaouic.test.RetroFit;


import java.util.List;

import com.guillaouic.test.Model.Repository;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface RetrofitService {
    @Headers({
            "Accept: application/json; charset=utf-8",
            "Accept-Language: en",
    })
    @GET("users/google/repos?per_page=20&page=nbpage")
    Call<List<Repository>> getRepository(@Query("nbpage") int page);

}