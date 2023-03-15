package com.paya.paragon.api.shortlistedProjects;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class ShortlistedProjectListData {
    @SerializedName("totalCount") @Expose private String totalCount;
    @SerializedName("imgPath") @Expose private String imgPath;
    @SerializedName("projectlist")  @Expose private ArrayList<ShortlistedProjectModel> projectlist;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public ArrayList<ShortlistedProjectModel> getProjectList() {
        return projectlist;
    }

    public void setProjectList(ArrayList<ShortlistedProjectModel> projectList) {
        this.projectlist = projectlist;
    }
}
