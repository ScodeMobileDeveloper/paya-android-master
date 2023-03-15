package com.paya.paragon.base.commonClass;


import com.paya.paragon.api.budgetList.BudgetPriceData;
import com.paya.paragon.api.index.PropertyTypeMain;
import com.paya.paragon.api.listLocProject.RegionDataChild;
import com.paya.paragon.api.mySavedSearches.SavedSearchInfo;
import com.paya.paragon.api.propertyDetails.AmenitiesModel;
import com.paya.paragon.model.SpecificationInfo;
import com.paya.paragon.utilities.AppConstant;

import java.util.ArrayList;
import java.util.Arrays;

public class GlobalValues {
    public static int screen_height = 0;
    public static int screen_width = 0;

    //TODO Search Property and Project
    public static String selectedLocationID = "39";
    public static String countryID = "102";
    public static String countryCode = "iq";
    public static String selectedLocation = null;

    public static ArrayList<String> selectedBedrooms = null;
    public static ArrayList<String> selectedBedroomID = null;
    public static ArrayList<String> selectedNeighbourhoodID = null;


    public static String selectedBedroomsNumber = null;
    public static String selectedFurnishedStaticValue = null;
    public static String selectedFurnishedValue = null;
    public static String selectedBedroomsID = null;
    public static String selectedPropertyType = null;
    public static String selectedPropertyTypeID = "";

    public static String selectedAddlocationCityID = "";

    public static String selectedSortValue = "date/orderby/desc";
    public static String selectedSortText = null;
    public static BudgetPriceData selectedMinBudgetPriceModel = null;
    public static BudgetPriceData selectedMaxBudgetPriceModel = null;
    public static String selectedMinPrice = "0";
    public static String selectedMaxPrice = "0";
    public static String selectedMinPriceValue = null;
    public static String selectedMaxPriceValue = null;

    public static String searchPropertyPurpose = null;
    public static String selectedRegionId = null;
    public static ArrayList<String> selectedRegions = new ArrayList<>();
    public static ArrayList<String> selectedRegionsText = new ArrayList<>();
    public static ArrayList<RegionDataChild> regionDataTemp;
    public static ArrayList<RegionDataChild> regionDataTempAP;

    public static String possessionStatus = "";
    public static boolean isAgentPropertyScrollOver;

    public static String baseUrl = null;
    public static ArrayList<PropertyTypeMain> propertyTypes = null;

    public static String myPropertiesImageUrl = null;

    public static String shortlistedPropertiesImageUrl = null;
    public static String shortlistedProjectsImageUrl = null;


    //TODO Post Property
    public static ArrayList<PropertyTypeMain> postPropertyTypes = null;
    public static ArrayList<AmenitiesModel> postPropertyAmenities = null;

    public static ArrayList<String> selectedAmenities = null;
    public static ArrayList<String> selectedAmenitiesID = null;

    public static ArrayList<String> selectedBedRoomIDs = null;
    public static ArrayList<String> selectedBathRoomIDs = null;

    public static ArrayList<SpecificationInfo> savedAttributes = null;

    //TODO Default
    public static String address = "Karbala";

    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME =
            "com.google.android.gms.location.sample.locationaddress";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME +
            ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME +
            ".LOCATION_DATA_EXTRA";
    public static String planTitle;
    public static String selectedAgentSortByCount = AppConstant.SORT_BY_DESC;

    public static void clearPropertyValues() {
        selectedPropertyTypeID = null;
        selectedPropertyType = null;
        selectedAmenities = null;
        selectedAmenitiesID = null;
        savedAttributes = null;
        selectedBedRoomIDs = null;
        selectedBathRoomIDs = null;
        postPropertyTypes = null;
    }


    //TODO Post Requirements
    public static String selectedTypePostRequirement = null;
    public static String selectedMainTypePostRequirement = null;


    //TODO Post Property
    public static String selectedBedRoomID = null;
    public static String selectedBathRoomID = null;

    public static void clearBuyGlobalValues() {
        searchPropertyPurpose = "Sell";
        selectedLocationID = "44";
        selectedPropertyTypeID = "";
        selectedPropertyType = "";
        selectedRegionId = "";
        selectedMaxPrice = "0";
        selectedMinPrice = "0";
        selectedBedroomsNumber = "";
        selectedBedrooms = new ArrayList<>();
        selectedBedroomID = new ArrayList<>();
        possessionStatus = "";
        selectedSortValue = "date/orderby/desc";
        selectedRegions = new ArrayList<>();
        selectedMinBudgetPriceModel = null;
        selectedMaxBudgetPriceModel = null;
        selectedMinPriceValue = "";
        selectedMaxPriceValue = "";
        regionDataTemp = new ArrayList<>();
        regionDataTempAP = new ArrayList<>();
        selectedAgentSortByCount = AppConstant.SORT_BY_DESC;
        
        selectedFurnishedValue = "";
        selectedFurnishedStaticValue = "";
    }

    public static void setValuesForSearch(SavedSearchInfo savedSearchInfo) {
        if (savedSearchInfo.getSearchParameters().getSearchPropertyPurpose() != null
                && !savedSearchInfo.getSearchParameters().getSearchPropertyPurpose().equals("")) {
            searchPropertyPurpose = savedSearchInfo.getSearchParameters().getSearchPropertyPurpose();
        }

        if (savedSearchInfo.getSearchParameters().getSearchPropertyTypeID() != null
                && !savedSearchInfo.getSearchParameters().getSearchPropertyTypeID().equals("")) {
            selectedPropertyTypeID = savedSearchInfo.getSearchParameters().getSearchPropertyTypeID();
        }

        if (savedSearchInfo.getSaveSearchParameter().getPropertyTypeName() != null
                && !savedSearchInfo.getSaveSearchParameter().getPropertyTypeName().equals("")) {
            selectedPropertyType = savedSearchInfo.getSaveSearchParameter().getPropertyTypeName();
        }

        if (savedSearchInfo.getSaveSearchParameter().getSearchRegion() != null
                && !savedSearchInfo.getSaveSearchParameter().getSearchRegion().equals("")) {
            selectedRegionId = savedSearchInfo.getSaveSearchParameter().getSearchRegion();
        }

        if (savedSearchInfo.getSaveSearchParameter().getMinPrice() != null
                && !savedSearchInfo.getSaveSearchParameter().getMinPrice().equals("")) {
            selectedMinPrice = savedSearchInfo.getSearchMinPriceValue();
        }

        if (savedSearchInfo.getSaveSearchParameter().getMaxPrice() != null
                && !savedSearchInfo.getSaveSearchParameter().getMaxPrice().equals("")) {
            selectedMaxPrice = savedSearchInfo.getSearchMaxPriceValue();
        }

        if (savedSearchInfo.getSearchParameters().getSearchAttributesStr() != null
                && !savedSearchInfo.getSearchParameters().getSearchAttributesStr().equals("")) {
            selectedBedroomsNumber = savedSearchInfo.getSearchParameters().getSearchAttributesStr();
        }

        if (savedSearchInfo.getSearchParameters().getSortBy() != null
                && !savedSearchInfo.getSearchParameters().getSortBy().equals("")) {
            selectedSortValue = savedSearchInfo.getSearchParameters().getSortBy();
        }

        if (savedSearchInfo.getSearchParameters().getSearchPropertyBedRoom() != null
                && !savedSearchInfo.getSearchParameters().getSearchPropertyBedRoom().equals("")) {
            GlobalValues.selectedBedrooms = new ArrayList<>(Arrays
                    .asList(savedSearchInfo.getSearchParameters().getSearchPropertyBedRoom().split(",")));
        }

        if (savedSearchInfo.getSaveSearchParameter().getSearchRegion() != null
                && !savedSearchInfo.getSaveSearchParameter().getSearchRegion().equals("")) {
            GlobalValues.selectedRegions = new ArrayList<>(Arrays
                    .asList(savedSearchInfo.getSaveSearchParameter().getSearchRegion().split(",")));
        }

        if (selectedRegionsText == null) {
            selectedRegionsText = new ArrayList<>();
        }
       /* if (savedSearchInfo.getSaveSearchParameter().getCityDetails() != null
                && savedSearchInfo.getSaveSearchParameter().getCityDetails().size() > 0) {
            selectedRegionsText.add(savedSearchInfo.getSaveSearchParameter().getCityDetails().get(0).getCityName());

        }
        if (savedSearchInfo.getSaveSearchParameter().getLocationDetails() != null
                && savedSearchInfo.getSaveSearchParameter().getStateDetails().size() > 0) {
            selectedRegionsText.add(savedSearchInfo.getSaveSearchParameter().getLocationDetails().get(0).getCityLocName());
        }*/
    }

}
