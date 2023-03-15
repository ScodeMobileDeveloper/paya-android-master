package com.paya.paragon.api.localInformationList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocalInformationListResponse {
    @SerializedName("response") @Expose private String Response;
    @SerializedName("data") @Expose private List<LocalInformationListData> localList;

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public List<LocalInformationListData> getLocalList() {
        return localList;
    }

    public void setLocalList(List<LocalInformationListData> localList) {
        this.localList = localList;
    }
}
