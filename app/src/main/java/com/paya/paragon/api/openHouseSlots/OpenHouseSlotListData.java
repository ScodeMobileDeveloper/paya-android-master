package com.paya.paragon.api.openHouseSlots;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class OpenHouseSlotListData {
    @SerializedName("totalcount") @Expose private Integer totalCount;
    @SerializedName("openHouseList") @Expose private ArrayList<OpenHouseModel> openHouseList;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<OpenHouseModel> getOpenHouseList() {
        return openHouseList;
    }

    public void setOpenHouseList(ArrayList<OpenHouseModel> openHouseList) {
        this.openHouseList = openHouseList;
    }
}
