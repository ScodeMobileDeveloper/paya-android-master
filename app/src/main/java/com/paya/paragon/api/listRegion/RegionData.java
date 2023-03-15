package com.paya.paragon.api.listRegion;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegionData {

    @SerializedName("text") @Expose private String text;
    @SerializedName("children") @Expose private List<RegionDataChild> children = null;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<RegionDataChild> getChildren() {
        return children;
    }

    public void setChildren(List<RegionDataChild> children) {
        this.children = children;
    }
}
