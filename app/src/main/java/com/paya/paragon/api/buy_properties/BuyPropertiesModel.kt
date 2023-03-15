package com.paya.paragon.api.buy_properties

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BuyPropertiesModel : Serializable {
    @SerializedName("propertyUnitPriceRange")
    var propertyUnitPriceRange: String? = null

    @SerializedName("isNegotiable")
    var isNegotiable: String? = null

    @SerializedName("pricePerSqft")
    var pricePerSqft: String? = null

    @SerializedName("propertyName")
    var propertyName: String? = null

    @SerializedName("cityLocName")
    var cityLocName: String? = null

    @SerializedName("propertyAddedDate")
    var propertyAddedDate: String? = null

    @SerializedName("propertyCoverImage")
    var propertyCoverImage: String? = null

    @SerializedName("proLatitude")
    var proLatitude: String? = null

    @SerializedName("proLongitude")
    var proLongitude: String? = null

    @SerializedName("propertyID")
    var propertyID: String? = null

    @SerializedName("propertyFeatured")
    var propertyFeatured: String? = null

    @SerializedName("propertyFavourite")
    var propertyFavourite: String? = null

    @SerializedName("type")
    var type: String? = null

    @SerializedName("propertyplanEnquiryCountBalance")
    var propertyplanEnquiryCountBalance: String? = null

    @SerializedName("projectUserCompanyName")
    var projectUserCompanyName: String? = null

    @SerializedName("projectUserCompanyLogo")
    var projectUserCompanyLogo: String? = null

    @SerializedName("propertyBedRoom")
    var projectBedRoom: String? = null

    @SerializedName("propertyPrice")
    var propertyPrice: String? = null

    @SerializedName("propertySqrFeet")
    var propertySqrFeet: String? = null

    @SerializedName("propertyPlotArea")
    var propertyPlotArea: String? = null

    @SerializedName("propertyTypeName")
    var projectTypeName: String? = null

    @SerializedName("projectName")
    var projectName: String? = null

    @SerializedName("projectAddedDate")
    var projectAddedDate: String? = null

    @SerializedName("projectCoverImage")
    var projectCoverImage: String? = null

    @SerializedName("projectBuilderName")
    var projectBuilderName: String? = null

    @SerializedName("projectID")
    var projectID: String? = null

    @SerializedName("projectFeatured")
    var projectFeatured: String? = null

    @SerializedName("projectFavourite")
    var projectFavourite: String? = null

    @SerializedName("imageCount")
    var imageCount: String? = null

    @SerializedName("link")
    var link: String? = null

    @SerializedName("pricePerUnitValue")
    var pricePerUnitValue: String? = null

    @SerializedName("pricePerUnit")
    var pricePerUnit: String? = null

    @SerializedName("exact")
    var exact: String? = null

    @SerializedName("propertySoldOutStatus")
    var propertySoldOutStatus: String? = null

    @SerializedName("propertySoldOutPrice")
    var propertySoldOutPrice: String? = null

    @SerializedName("propertySoldOutDate")
    var propertySoldOutDate: String? = null

    @SerializedName("propertyTypeIcon")
    var propertyTypeIcon: String? = null

    @SerializedName("propertyBathRoom")
    var propertyBathRoom: String? = null

    @SerializedName("isOffer")
    var isOffer: String? = null

    @SerializedName("propertyOfferPrice")
    var propertyOfferPrice: String? = null

    @SerializedName("propertyOfferDiscount")
    var propertyOfferDiscount: String? = null

    @SerializedName("currencyID_1")
    var currencyID_1: String? = null

    @SerializedName("currencyID_5")
    var currencyID_5: String? = null

    @SerializedName("images")
    @Expose
    var images: List<ImagesList>? = null

    @SerializedName("userID")
    @Expose
    var userID: String? = null
}