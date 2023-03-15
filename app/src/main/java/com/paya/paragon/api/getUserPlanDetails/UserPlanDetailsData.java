package com.paya.paragon.api.getUserPlanDetails;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class UserPlanDetailsData {
    @SerializedName("userDetails") @Expose private UserDetails userDetails;
    @SerializedName("countries") @Expose private List<CountryInfo> countries = null;
    @SerializedName("paymentGateways") @Expose private List<PaymentGateway> paymentGateways = null;
    @SerializedName("plandetails") @Expose private PlanDetails planDetails;

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public List<CountryInfo> getCountries() {
        return countries;
    }

    public void setCountries(List<CountryInfo> countries) {
        this.countries = countries;
    }

    public List<PaymentGateway> getPaymentGateways() {
        return paymentGateways;
    }

    public void setPaymentGateways(List<PaymentGateway> paymentGateways) {
        this.paymentGateways = paymentGateways;
    }

    public PlanDetails getPlanDetails() {
        return planDetails;
    }

    public void setPlanDetails(PlanDetails planDetails) {
        this.planDetails = planDetails;
    }
}
