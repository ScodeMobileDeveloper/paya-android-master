package com.paya.paragon.api.AgentSubLocation;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgentSubLocation {
    @SerializedName("cityLocID") @Expose private String cityLocID;
    @SerializedName("cityLocName") @Expose private String cityLocName;

    public String getCityLocID() {
        return cityLocID;
    }

    public void setCityLocID(String cityLocID) {
        this.cityLocID = cityLocID;
    }

    public String getCityLocName() {
        return cityLocName;
    }

    public void setCityLocName(String cityLocName) {
        this.cityLocName = cityLocName;
    }
}
