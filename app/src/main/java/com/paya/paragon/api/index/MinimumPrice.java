package com.paya.paragon.api.index;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MinimumPrice {
    @SerializedName("minvalue") @Expose private Integer minvalue;

    public Integer getMinvalue() {
        return minvalue;
    }

    public void setMinvalue(Integer minvalue) {
        this.minvalue = minvalue;
    }
}
