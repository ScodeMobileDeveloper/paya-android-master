package com.paya.paragon.api.langaugeList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LanguageListData {
    @SerializedName("languageList") @Expose private ArrayList<LanguageList> languageLists;

    public ArrayList<LanguageList> getLanguageLists() {
        return languageLists;
    }

    public void setLanguageLists(ArrayList<LanguageList> languageLists) {
        this.languageLists = languageLists;
    }
}
