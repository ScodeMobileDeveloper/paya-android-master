package com.paya.paragon.api.postProperty.randomID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UserPlanInfo {
    @SerializedName("id") @Expose private String id;
    @SerializedName("planTitle") @Expose private String planTitle;

    public UserPlanInfo(String id, String title) {
        this.id = id;
        this.planTitle = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }
}
