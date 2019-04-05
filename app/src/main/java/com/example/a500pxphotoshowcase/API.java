package com.example.a500pxphotoshowcase;

import com.example.a500pxphotoshowcase.Models.PageModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {


    String BaseURL = "https://api.500px.com/v1/";

    @GET("photos?")
    Call<PageModel> getPage(@Query("consumer_key") String consumerKey, @Query("feature") String feature,
                            @Query("image_size") String image_size, @Query("page") int page);



}
