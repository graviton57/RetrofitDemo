package com.havrylyuk.retrofit.service;

import com.havrylyuk.retrofit.model.Entities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Igor Havrylyuk on 08.03.2017.
 */

public interface EntityApiService {

    //http://api.geonames.org/countryInfoJSON?username=graviton57&formatted=true&lang=ru&style=FULL
    @GET("countryInfoJSON")
    Call<Entities> getCountries(
            @Query("lang") String lang,
            @Query("username") String userName,
            @Query("style") String style);
}
