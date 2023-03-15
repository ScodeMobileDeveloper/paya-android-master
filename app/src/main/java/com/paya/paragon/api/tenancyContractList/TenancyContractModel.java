package com.paya.paragon.api.tenancyContractList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class TenancyContractModel {
    @SerializedName("tenancyLandlord") @Expose private String tenancyLandlord;
    @SerializedName("tenancyTenant") @Expose private String tenancyTenant;
    @SerializedName("tenancyAdded") @Expose private String tenancyAdded;
    @SerializedName("tenancy_usrLink") @Expose private String tenancy_usrLink;

    public String getTenancyLandlord() {
        return tenancyLandlord;
    }

    public void setTenancyLandlord(String tenancyLandlord) {
        this.tenancyLandlord = tenancyLandlord;
    }

    public String getTenancyTenant() {
        return tenancyTenant;
    }

    public void setTenancyTenant(String tenancyTenant) {
        this.tenancyTenant = tenancyTenant;
    }

    public String getTenancyAdded() {
        return tenancyAdded;
    }

    public void setTenancyAdded(String tenancyAdded) {
        this.tenancyAdded = tenancyAdded;
    }

    public String getTenancy_usrLink() {
        return tenancy_usrLink;
    }

    public void setTenancy_usrLink(String tenancy_usrLink) {
        this.tenancy_usrLink = tenancy_usrLink;
    }
}
