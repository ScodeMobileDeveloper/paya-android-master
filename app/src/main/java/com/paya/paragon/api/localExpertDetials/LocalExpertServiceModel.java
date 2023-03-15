package com.paya.paragon.api.localExpertDetials;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class LocalExpertServiceModel {
    @SerializedName("servicesImage") @Expose private String servicesImage;
    @SerializedName("servicesName") @Expose private String servicesName;
    @SerializedName("servicesDescription") @Expose private String servicesDescription;

    public String getServicesImage() {
        return servicesImage;
    }

    public void setServicesImage(String servicesImage) {
        this.servicesImage = servicesImage;
    }

    public String getServicesName() {
        return servicesName;
    }

    public void setServicesName(String servicesName) {
        this.servicesName = servicesName;
    }

    public String getServicesDescription() {
        return servicesDescription;
    }

    public void setServicesDescription(String servicesDescription) {
        this.servicesDescription = servicesDescription;
    }
}
