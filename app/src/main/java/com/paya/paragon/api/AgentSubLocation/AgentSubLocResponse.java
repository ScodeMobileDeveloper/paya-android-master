package com.paya.paragon.api.AgentSubLocation;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AgentSubLocResponse {
    @SerializedName("response") @Expose
    private String Response;
    @SerializedName("message") @Expose
    private String Message;
    @SerializedName("data") @Expose
    private ArrayList<AgentSubLocation> agentCityLocations;

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public ArrayList<AgentSubLocation> getAgentCityLocations() {
        return agentCityLocations;
    }

    public void setAgentCityLocations(ArrayList<AgentSubLocation> agentCityLocations) {
        this.agentCityLocations = agentCityLocations;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

   /* public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }*/





}
