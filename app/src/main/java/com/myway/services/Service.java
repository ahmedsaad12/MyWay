package com.myway.services;


import com.myway.models.ProductDataModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {

    @FormUrlEncoded
    @POST("api/contact")
    Call<ResponseBody> sendContact(@Field("name") String name,
                                   @Field("email") String email,
                                   @Field("title") String title,
                                   @Field("message") String message

    );

    @GET("api/product")
    Call<ProductDataModel> getproducts(@Query("product_id") String product_id,
                                       @Query("country") String country
    );

}