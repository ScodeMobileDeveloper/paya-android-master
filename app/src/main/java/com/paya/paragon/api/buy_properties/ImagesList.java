package com.paya.paragon.api.buy_properties;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImagesList {
    @SerializedName("0")
    @Expose
    private String _0;
    @SerializedName("propertyImageName")
    @Expose
    private String propertyImageName;

    public String get0() {
        return _0;
    }

    public void set0(String _0) {
        this._0 = _0;
    }

    public String getPropertyImageName() {
        return propertyImageName;
    }

    public void setPropertyImageName(String propertyImageName) {
        this.propertyImageName = propertyImageName;
    }
}
