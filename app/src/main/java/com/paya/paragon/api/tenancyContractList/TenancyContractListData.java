package com.paya.paragon.api.tenancyContractList;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class TenancyContractListData {
    @SerializedName("totalcount") @Expose private Integer totalcount;
    @SerializedName("contracts") @Expose private ArrayList<TenancyContractModel> contracts;

    public Integer getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(Integer totalcount) {
        this.totalcount = totalcount;
    }

    public ArrayList<TenancyContractModel> getContracts() {
        return contracts;
    }

    public void setContracts(ArrayList<TenancyContractModel> contracts) {
        this.contracts = contracts;
    }
}
