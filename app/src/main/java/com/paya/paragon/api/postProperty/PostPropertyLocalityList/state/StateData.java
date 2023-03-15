package com.paya.paragon.api.postProperty.PostPropertyLocalityList.state;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class StateData {

	public ArrayList<StateListItem> getStateList() {
		return stateList;
	}

	public void setStateList(ArrayList<StateListItem> stateList) {
		this.stateList = stateList;
	}

	@SerializedName("stateList")
	private ArrayList<StateListItem> stateList;
}