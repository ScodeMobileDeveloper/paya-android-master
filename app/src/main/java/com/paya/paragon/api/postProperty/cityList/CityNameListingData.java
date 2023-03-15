package com.paya.paragon.api.postProperty.cityList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class CityNameListingData {
    @SerializedName("cities") @Expose private ArrayList<CityNameData> cities;

    public ArrayList<CityNameData> getCities() {
        return cities;
    }

    public void setCities(ArrayList<CityNameData> cities) {
        this.cities = cities;
    }
}
