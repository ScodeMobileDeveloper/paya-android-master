package com.paya.paragon.api.langaugeList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguageList {
    @SerializedName("languageID") @Expose
    private String languageID;
    @SerializedName("languageName") @Expose private String languageName;

    public String getLanguageID() {
        return languageID;
    }

    public void setLanguageID(String languageID) {
        this.languageID = languageID;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
}
