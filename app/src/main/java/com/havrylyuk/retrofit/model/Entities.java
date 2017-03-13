package com.havrylyuk.retrofit.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Igor Havrylyuk on 08.03.2017.
 */
public class Entities extends ApiResponse {

    @SerializedName("geonames")
    private List<Entity> countries;

    public List<Entity> getCountries() {
        return countries;
    }
}
