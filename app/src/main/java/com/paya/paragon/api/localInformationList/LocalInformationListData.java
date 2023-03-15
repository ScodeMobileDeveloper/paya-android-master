package com.paya.paragon.api.localInformationList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocalInformationListData {
    @SerializedName("localinfoTypeID") @Expose private String localinfoTypeID;
    @SerializedName("localinfoTypeTitle") @Expose private String localinfoTypeTitle;
    @SerializedName("localTypeImage") @Expose private String localTypeImage;
    @SerializedName("localinfoTypeUrlKey") @Expose private String localinfoTypeUrlKey;

    public String getLocalinfoTypeID() {
        return localinfoTypeID;
    }

    public void setLocalinfoTypeID(String localinfoTypeID) {
        this.localinfoTypeID = localinfoTypeID;
    }

    public String getLocalinfoTypeTitle() {
        return localinfoTypeTitle;
    }

    public void setLocalinfoTypeTitle(String localinfoTypeTitle) {
        this.localinfoTypeTitle = localinfoTypeTitle;
    }

    public String getLocalTypeImage() {
        return localTypeImage;
    }

    public void setLocalTypeImage(String localTypeImage) {
        this.localTypeImage = localTypeImage;
    }

    public String getLocalinfoTypeUrlKey() {
        return localinfoTypeUrlKey;
    }

    public void setLocalinfoTypeUrlKey(String localinfoTypeUrlKey) {
        this.localinfoTypeUrlKey = localinfoTypeUrlKey;
    }
}
