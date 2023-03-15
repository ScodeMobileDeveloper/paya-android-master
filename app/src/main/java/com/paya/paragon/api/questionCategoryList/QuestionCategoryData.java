package com.paya.paragon.api.questionCategoryList;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class QuestionCategoryData {

    @SerializedName("CategoriesList") @Expose private ArrayList<CategoriesList> CategoriesList;

    public ArrayList<CategoriesList> getCategoriesList() {
        return CategoriesList;
    }

    public void setCategoriesList(ArrayList<CategoriesList> categoriesList) {
        CategoriesList = categoriesList;
    }





}
