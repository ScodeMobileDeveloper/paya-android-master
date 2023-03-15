package com.paya.paragon.api.index;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaximumPrice {
    @SerializedName("maxvalue") @Expose private Integer maxvalue;

    public Integer getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(Integer maxvalue) {
        this.maxvalue = maxvalue;
    }
}
