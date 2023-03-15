package com.paya.paragon.api.postProperty.communityList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class CommunityListingData {
    @SerializedName("cities") @Expose private ArrayList<CommunityNameData> cities;

    public ArrayList<CommunityNameData> getCities() {
        return cities;
    }

    public void setCities(ArrayList<CommunityNameData> cities) {
        this.cities = cities;
    }
}
