package com.paya.paragon.api.postRequirement.postRequirementLocation;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PRLocationListDataChild {
    @SerializedName("id") @Expose private String id;
    @SerializedName("text") @Expose private String sampleText;
    @SerializedName("originalText") @Expose private String originalText;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSampleText() {
        return sampleText;
    }

    public void setSampleText(String sampleText) {
        this.sampleText = sampleText;
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }
}
