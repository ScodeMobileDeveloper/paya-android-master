package com.paya.paragon.api.postProperty.postPropertyIndex;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paya.paragon.api.index.PropertyTypeMain;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class PostPropertyIndexData {
    @SerializedName("propertyType") @Expose private ArrayList<PropertyTypeMain> propertyType;

    public ArrayList<PropertyTypeMain> getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(ArrayList<PropertyTypeMain> propertyType) {
        this.propertyType = propertyType;
    }
}
