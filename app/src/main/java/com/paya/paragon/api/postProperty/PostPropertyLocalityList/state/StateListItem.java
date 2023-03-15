package com.paya.paragon.api.postProperty.PostPropertyLocalityList.state;

import com.google.gson.annotations.SerializedName;


public class StateListItem{

	@SerializedName("stateName")
	private String stateName;

	@SerializedName("stateID")
	private String stateID;

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateID() {
		return stateID;
	}

	public void setStateID(String stateID) {
		this.stateID = stateID;
	}

	public Object getStateShortName() {
		return stateShortName;
	}

	public void setStateShortName(Object stateShortName) {
		this.stateShortName = stateShortName;
	}

	@SerializedName("stateShortName")
	private Object stateShortName;
}