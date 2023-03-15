package com.paya.paragon.api.statisticsList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class StatisticsListData {
    @SerializedName("totalcount") @Expose private Integer totalcount;
    @SerializedName("statitics") @Expose private ArrayList<StatisticsModel> statitics;

    public Integer getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(Integer totalcount) {
        this.totalcount = totalcount;
    }

    public ArrayList<StatisticsModel> getStatitics() {
        return statitics;
    }

    public void setStatitics(ArrayList<StatisticsModel> statitics) {
        this.statitics = statitics;
    }
}
