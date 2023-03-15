package com.paya.paragon.api.postRequirement.postRequirementLocation;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class PRLocationListData {

    @SerializedName("text") @Expose private String locationType;
    @SerializedName("children") @Expose private ArrayList<PRLocationListDataChild> children;

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public ArrayList<PRLocationListDataChild> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<PRLocationListDataChild> children) {
        this.children = children;
    }
}
