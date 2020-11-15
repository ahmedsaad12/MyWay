package com.endpoint.myway.services;


import com.endpoint.myway.models.CatalougDataModel;
import com.endpoint.myway.models.NewsDataModel;
import com.endpoint.myway.models.OfferDataModel;
import com.endpoint.myway.models.ProductDataModel;
import com.endpoint.myway.models.SettingDataModel;
import com.endpoint.myway.models.SettingModel;
import com.endpoint.myway.models.Slider_Model;

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
    @FormUrlEncoded
    @POST("api/add-to-group")
    Call<ResponseBody> Join(@Field("name") String name,
                                   @Field("email") String email,
                                   @Field("number") String title,
                                   @Field("address") String message

    );
    @GET("api/product")
    Call<ProductDataModel> getproducts(@Query("product_id") String product_id,
                                       @Query("country") String country
    );
    @GET("api/sale")
    Call<OfferDataModel> getoffers(@Query("sale_id") String sale_id,
                                     @Query("country") String country
    );
    @GET("api/news")
    Call<NewsDataModel> getnews(@Query("new_id") String new_id,
                                @Query("country") String country
    );
    @GET("api/client-system")
    Call<SettingDataModel> getsetting(@Query("client_sestem_id") String client_sestem_id,
                                      @Query("country") String country
    );
    @GET("api/catalog")
    Call<CatalougDataModel> getcataloug(@Query("catalog_id") String catalog_id,
                                        @Query("country") String country
    );
    @GET("api/setting")
    Call<SettingModel> getSetting();
    @GET("api/slider")
    Call<Slider_Model> get_slider();


}