package com.paya.paragon.api.cmsPages;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CmsPagesResponse {
    @SerializedName("response")
    @Expose
    private String Response;
    @SerializedName("code")
    @Expose
    private Integer Code;
    @SerializedName("message")
    @Expose
    private String Message;
    @SerializedName("data")
    @Expose
    private CmsPagesData cmsPagesData;

    public CmsPagesData getCmsPagesData() {
        return cmsPagesData;
    }

    public void setCmsPagesData(CmsPagesData cmsPagesData) {
        this.cmsPagesData = cmsPagesData;
    }

    public class CmsPagesData {
        @SerializedName("cms")
        @Expose
        private Cms cms;

        public Cms getCms() {
            return cms;
        }

        public void setCms(Cms cms) {
            this.cms = cms;
        }

        public class Cms {
            @SerializedName("contentTitle")
            @Expose
            private String contentTitle;
            @SerializedName("contentDescription")
            @Expose
            private String contentDescription;

            public String getContentTitle() {
                return contentTitle;
            }

            public void setContentTitle(String contentTitle) {
                this.contentTitle = contentTitle;
            }

            public String getContentDescription() {
                return contentDescription;
            }

            public void setContentDescription(String contentDescription) {
                this.contentDescription = contentDescription;
            }
        }

    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

}
