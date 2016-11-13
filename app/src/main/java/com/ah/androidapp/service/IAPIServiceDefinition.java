package com.ah.androidapp.service;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * @author
 */
public interface IAPIServiceDefinition {

//    https://maps.googleapis.com/maps/api/timezone/json?location=39.6034810,-119.6822510&timestamp=1331161200&key=AIzaSyDxjc7X-0j_5qUiPKSvtVTaCHMfjheW1yU
    @GET("/maps/api/timezone/json")
    void getTimezoneInfo(@Query("location") String location, @Query("timestamp") String timestamp, @Query("key") String key, Callback<CallbackResponse> response);
}