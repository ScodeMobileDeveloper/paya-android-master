package com.paya.paragon.api.bedRoomList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BedRoomListData {
    @SerializedName("bedroom") @Expose private ArrayList<BedRoomInfo> bedroom;
    @SerializedName("otherAttributes") @Expose private ArrayList<PropertyAttribute> otherAttributes;

    public ArrayList<BedRoomInfo> getBedroom() {
        return bedroom;
    }

    public void setBedroom(ArrayList<BedRoomInfo> bedroom) {
        this.bedroom = bedroom;
    }

    public ArrayList<PropertyAttribute> getOtherAttributes() {
        return otherAttributes;
    }

    public void setOtherAttributes(ArrayList<PropertyAttribute> otherAttributes) {
        this.otherAttributes = otherAttributes;
    }
}
