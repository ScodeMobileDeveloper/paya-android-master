package com.paya.paragon.api.listLocProject;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paya.paragon.utilities.ExtensionKt;

import java.util.List;

public class ListLocProjectData {

    @SerializedName("location") @Expose private List<RegionDataChild> location;
    @SerializedName("projects") @Expose private List<RegionDataChild> projects;
    @SerializedName("SubCommunity") @Expose private List<RegionDataChild> SubCommunity;


    public List<RegionDataChild> getSubCommunity() { return SubCommunity; }

    public void setSubCommunity(List<RegionDataChild> subCommunity) { SubCommunity = subCommunity; }

    public List<RegionDataChild> getLocation() {
        return location;
    }

    public void addOtherLocation() {
        if (!ExtensionKt.containsOther(location)) {
            location.add(new RegionDataChild("other", "Other", "33.2232", "43.6793"));
        }
    }

    public void setLocation(List<RegionDataChild> location) {
        this.location = location;
    }

    public List<RegionDataChild> getProjects() {
        return projects;
    }

    public void setProjects(List<RegionDataChild> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "ListLocProjectData{" +
                "location=" + location +
                ", projects=" + projects +
                ", SubCommunity=" + SubCommunity +
                '}';
    }
}
