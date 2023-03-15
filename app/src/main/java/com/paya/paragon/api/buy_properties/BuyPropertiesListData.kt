package com.paya.paragon.api.buy_properties

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BuyPropertiesListData {
    @SerializedName("searchPropertyCount")
    @Expose
    var searchPropertyCount: String? = null

    @SerializedName("imageURL")
    @Expose
    var imageURL: String? = null

    @SerializedName("companyProfileLogoUrl")
    @Expose
    var companyProfileLogoUrl: String? = null

    @SerializedName("siteUrl")
    @Expose
    var siteUrl: String? = null

    @SerializedName("propertyLists")
    @Expose
    val propertyLists: List<BuyPropertiesModel?>? = null
}