package com.paya.paragon.api.AgentDetail;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgentDetailData {
    @SerializedName("agentDetail")  @Expose private AgentDetail agentDetail;
    @SerializedName("imgUrl")  @Expose private String imageURL;
    @SerializedName("companyImageURL")  @Expose private String companyImageURL;
    @SerializedName("totalCount")  @Expose private String totalCount;

    public String getCompanyImageURL() {
        return companyImageURL;
    }

    public void setCompanyImageURL(String companyImageURL) {
        this.companyImageURL = companyImageURL;
    }

    public AgentDetail getAgentDetail() {
        return agentDetail;
    }

    public void setAgentDetail(AgentDetail agentDetail) {
        this.agentDetail = agentDetail;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }
}
