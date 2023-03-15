package com.paya.paragon.api.propertyDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuggestedProperties {
    @SerializedName("countProperties") @Expose private Integer countProperties;
    @SerializedName("properties") @Expose private List<SuggestedPropertyDetails> properties = null;

    public Integer getCountProperties() {
        return countProperties;
    }

    public void setCountProperties(Integer countProperties) {
        this.countProperties = countProperties;
    }

    public List<SuggestedPropertyDetails> getProperties() {
        return properties;
    }

    public void setProperties(List<SuggestedPropertyDetails> properties) {
        this.properties = properties;
    }
}
