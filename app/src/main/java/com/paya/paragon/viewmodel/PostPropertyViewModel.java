package com.paya.paragon.viewmodel;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.paya.paragon.PayaAppClass;
import com.paya.paragon.R;
import com.paya.paragon.activity.postProperty.PostPropertyPage01Activity;
import com.paya.paragon.activity.postProperty.PostPropertyPage05Activity;
import com.paya.paragon.api.PayaAPICall;
import com.paya.paragon.api.PostPropertyRegion.PostPropertyAPICall;
import com.paya.paragon.api.agentList.AgentDataListDATAItemObject;
import com.paya.paragon.api.agentList.AgentDataListDATAResponse;
import com.paya.paragon.api.bankListPropertyPost.BankListData;
import com.paya.paragon.api.bankListPropertyPost.BankListPropertyResponse;
import com.paya.paragon.api.bedRoomList.BedRoomInfo;
import com.paya.paragon.api.cityList.CityListingResponse;
import com.paya.paragon.api.editPostProperty.EditPostedPropertyResponse;
import com.paya.paragon.api.editPostProperty.PostedPropertyGalleryInfo;
import com.paya.paragon.api.listLocProject.ListLocProjectResponse;
import com.paya.paragon.api.postProperty.attributeListing.AllAttributesData;
import com.paya.paragon.api.postProperty.attributeListing.AttributeListingResponse;
import com.paya.paragon.api.postProperty.loadGalleryImage.LoadSavedImageResponse;
import com.paya.paragon.api.postProperty.loadGalleryImage.SavedImageInfo;
import com.paya.paragon.api.postProperty.loadVideo.LoadSavedVideoResponse;
import com.paya.paragon.api.postProperty.loadVideo.SavedVideoInfo;
import com.paya.paragon.api.postProperty.post.PostPropertyApiDataClass;
import com.paya.paragon.api.postProperty.post.PostPropertyResponse;
import com.paya.paragon.api.postProperty.postPropertyIndex.PostPropertyIndexData;
import com.paya.paragon.api.postProperty.randomID.RandomIdData;
import com.paya.paragon.api.postProperty.randomID.UserPlanInfo;
import com.paya.paragon.api.propertyDetails.AmenitiesModel;
import com.paya.paragon.base.BaseViewModel;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.base.commonClass.PairedValues;
import com.paya.paragon.utilities.AppConstant;
import com.paya.paragon.utilities.NumberTextWatcherForThousand;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.paya.paragon.activity.postProperty.PostPropertyPage01Activity.m_EditPostedPropertyData;
import static com.paya.paragon.activity.postProperty.PostPropertyPage01Activity.m_IsEditPostProperty;
import static com.paya.paragon.activity.postProperty.PostPropertyPage01Activity.m_PostPropertyAPIData;
import static com.paya.paragon.activity.postProperty.PostPropertyPage01Activity.m_PostPropertyId;
import static com.paya.paragon.activity.postProperty.PostPropertyPage01Activity.propertyDetailsEdit;

public class PostPropertyViewModel extends BaseViewModel implements PayaAPICall.PayaAPICallListener {

    private PostPropertyViewModelCallBack callBack;
    private PostPropertyAPICall postPropertyAPICall;
    private PayaAPICall payaAPICall;
    private ArrayList<BankListData> bankDataList;


    public boolean isImageUploaded = false;
    public boolean isPhoneNumberMandatory = false;
    public boolean isAreaMandatory = false;
    public boolean isBedRoomMandatory = false;
    public boolean isBathRoomMandatory = false;
    public boolean isLivingRoomMandatory = false;
    public boolean isKitchenMandatory = false;
    public boolean isFloorNumberMandatory = false;
    public boolean isMultipleSelectionEnable = false;
    public String selectedCurrencyID = "0";
    public int selectedCurrencyType = 0;
    public boolean rentSelected = false;

    public String amenitiesFromResponse,currencyType;
    public static final int MULTIPLE_PERMISSION_REQUEST = 1234;
    private String videoPropertyUrl = "";
    public boolean isAmenitiesFound = false;
    public LoadSavedImageResponse savedImageResponse;

    Context context;
    public PostPropertyViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public void initiatePostProperty(PostPropertyViewModelCallBack callBack) {
        this.callBack = callBack;
        this.postPropertyAPICall = new PostPropertyAPICall(this, callBack);
        payaAPICall = new PayaAPICall(this);
    }

    public void initiateCheckLimit() {
        postPropertyAPICall.checkUserPostPropertyLimit();
        if (AppConstant.USER_TYPE_ID_MAIN_AGENT == SessionManager.getUserTypeId(callBack.getPostPropertyContext())) {
            initiateSubAgentAPICall();
        }
    }

    private void initiateSubAgentAPICall() {
        payaAPICall.initiateAgentSubAgentListAPICall(0, 100, AppConstant.SORT_BY_DESC,
                "", SessionManager.getUserId(callBack.getPostPropertyContext()), AgentListFragmentViewModel.m_AGENT_LIST_FIRST_HIT);
    }

    public void  initiateAttributeAminitiesListAPI() {
        postPropertyAPICall.getAttributeAmenitiesListAPI();
            resetMandatoryFields();
    }

    public void updateDetailsForEditProperty() {
        try {
        if (m_IsEditPostProperty) {
            m_PostPropertyAPIData.setBluePrintimage(propertyDetailsEdit.getBluePrintimage());
            m_PostPropertyAPIData.setThreeSixtyView(propertyDetailsEdit.getThreeSixtyView());
            callBack.updateSavedImagesViews(getSavedImagesFromPostPropertyEdit(m_EditPostedPropertyData.getGallery()), m_EditPostedPropertyData.getImagePath());
            callBack.updateSavedVideoUrl(m_EditPostedPropertyData.getVideos());
            callBack.updateDescription(m_EditPostedPropertyData.getPropertyDetails().getPropertyDescription());
            if (propertyDetailsEdit.getBluePrintimage() != null && !propertyDetailsEdit.getBluePrintimage().isEmpty()) {
                callBack.updateBlurPrintBitMap(null, m_EditPostedPropertyData.getBluePrintimagePath() + propertyDetailsEdit.getBluePrintimage());
            }
        }else {

            callBack.updateDescription(m_PostPropertyAPIData.getPropertyDescription());
            postPropertyAPICall.initiateGetSavedImagesList(m_PostPropertyAPIData.getRandomPropertyID());
            if(m_PostPropertyAPIData.getBluePrintimage()!=null && !m_PostPropertyAPIData.getBluePrintimage().isEmpty())
                doProcessWithBluePrint(m_PostPropertyAPIData.getBluePrintimage()+AppConstant.BLUEPRINTNAME_SEPERATION_SYMBOL+m_PostPropertyAPIData.getBluePrintPath() );
                postPropertyAPICall.loadSavedVideoFromServer(m_PostPropertyAPIData.getRandomPropertyID());
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
       }
       public void initiateAminitiesListFromIntent(Intent intent) {
        if (intent != null && intent.hasExtra(AppConstant.I_AMENITIES)) {
            Utils.setAmenitiesIcon();
            GlobalValues.selectedAmenities = new ArrayList<>();
            GlobalValues.selectedAmenitiesID = new ArrayList<>();
            AttributeListingResponse listingData = new Gson().fromJson(intent.getExtras().getString(AppConstant.I_AMENITIES), AttributeListingResponse.class);
            callBack.updateAmenitiesInAdapter(listingData.getData().getAmenities());
        }
    }

    public void getLocationSelectionFieldValues(){
        Log.d("city","method call");
        postPropertyAPICall.getCityList(SessionManager.getLanguageID(callBack.getPostPropertyContext()));
    }
    public void getNeighbourhoodValues(String cityID,String query){

        postPropertyAPICall.getNeighbourhoodList(query,cityID);
    }
    private void resetMandatoryFields() {
        isPhoneNumberMandatory = false;
        isAreaMandatory = false;
        isBathRoomMandatory = false;
        isBedRoomMandatory = false;
        isLivingRoomMandatory = false;
        isKitchenMandatory = false;
        isFloorNumberMandatory = false;
    }
    /**
     * this is for only sub agent list API Call
     */
    @Override
    public void onSuccess(int apiTransactionCode, String response) {
        if (response != null && !response.isEmpty()) {
            AgentDataListDATAResponse agentDataListDATAResponse = new Gson().fromJson(response, AgentDataListDATAResponse.class);
            callBack.initiateSubAgentListSpinner(addDefaultData(agentDataListDATAResponse.getData()));
        }
    }

    private List<AgentDataListDATAItemObject> addDefaultData(List<AgentDataListDATAItemObject> data) {
        AgentDataListDATAItemObject defaultData = new AgentDataListDATAItemObject();
        defaultData.setName(callBack.getPostPropertyContext().getString(R.string.select));
        defaultData.setUserID("");
        defaultData.setPhone(SessionManager.getPhone(PayaAppClass.getAppInstance()));
        data.add(0, defaultData);
        return data;
    }

    /**
     * this is for only sub agent list API Call
     */
    @Override
    public void onFailure(int apiTransactionCode, String message) {
        if (AppConstant.NETWORK_FAILURE != apiTransactionCode) {
            callBack.initiateSubAgentListSpinner(null);
        }
    }

    @Override
    public void onAPISuccess(String response, int apiTransactionID) {
        if (AppConstant.GET_PP_CHECK_USER_LIMIT == apiTransactionID) {
            proceedAfterLimitCheck();
        } else if (AppConstant.GET_PP_RANDOM_ID == apiTransactionID) {
            onGetRandomIDResponse(response);
        } else if (AppConstant.GET_PP_PROPERTY_PROJECT_TYPES == apiTransactionID) {
            onGetPropertyProjectTypes(response);
        } else if (AppConstant.GET_PP_BANK_LIST == apiTransactionID) {
            onGetBankList(response);
        } else if (AppConstant.GET_PP_ATTR_AMN_LIST == apiTransactionID) {
            onGetAttrAminList(response);
        } else if (AppConstant.UPLOAD_BLUE_PRINT == apiTransactionID) {
            doProcessWithBluePrint(response);
        } else if (AppConstant.UPLOAD_GALLERY_IMAGE == apiTransactionID) {
            postPropertyAPICall.initiateGetSavedImagesList(m_PostPropertyAPIData.getRandomPropertyID());
        } else if (AppConstant.PP_GET_ALL_SAVED_IMAGE == apiTransactionID) {
                getSavedImages(response);
        } else if (AppConstant.GET_PP_SAVED_VIDEO == apiTransactionID) {
            getSavedVideos(response);
        } else if (AppConstant.PP_POST_PROPERTY == apiTransactionID || AppConstant.PP_EDIT_POST_PROPERTY == apiTransactionID) {
            getPostPropertyResponse(response, apiTransactionID);
        } else if (AppConstant.PP_GET_EDIT_PROPERTY_DETAILS == apiTransactionID) {
            getEditPostPropertyResponse(response);
        }else if(AppConstant.PP_CITY_LIST == apiTransactionID){
           getCityList(response);
        }else if(AppConstant.PP_NBH_LIST == apiTransactionID){
            getNeighbourhood(response);
        }
        else {
            callBack.dismissLoader();
        }
    }



    @Override
    public void onAPIFailure(String response, int apiTransactionID) {
        if (AppConstant.NETWORK_FAILURE == apiTransactionID) {
            callBack.noInternetMessage();
        } else if (AppConstant.UPLOAD_GALLERY_IMAGE == apiTransactionID || AppConstant.UPLOAD_BLUE_PRINT == apiTransactionID) {

                    postPropertyAPICall.initiateGetSavedImagesList(m_PostPropertyAPIData.getRandomPropertyID());

        }else if (AppConstant.GET_PP_BANK_LIST == apiTransactionID) {
            onNoMotgagedClick();
        }/* else if (AppConstant.UPLOAD_BLUE_PRINT == apiTransactionID || AppConstant.UPLOAD_GALLERY_IMAGE == apiTransactionID) {
            callBack.showToastMessage(-1, response);
        }*/
    }
    private void getCityList(String response) {
        if (response != null && !response.isEmpty()) {
            CityListingResponse cityListingResponse = new Gson().fromJson(response, CityListingResponse.class);
            callBack.updateCity(cityListingResponse);

        }
    }
    private void getNeighbourhood(String response){
        if (response != null && !response.isEmpty()) {
            ListLocProjectResponse listLocProjectResponse = new Gson().fromJson(response, ListLocProjectResponse.class);
            callBack.attachNBHRcvItems(listLocProjectResponse);

        }
    }
    private void proceedAfterLimitCheck() {
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())){
            onAPIFailure("", AppConstant.NETWORK_FAILURE);
        } else {
            if (m_IsEditPostProperty) {
                postPropertyAPICall.getEditPropertyDetails(m_PostPropertyId);
            } else {
                postPropertyAPICall.getRandomID();
            }
        }
    }

    private void onGetRandomIDResponse(String response) {
        RandomIdData randomIdData = new Gson().fromJson(response, RandomIdData.class);
        PostPropertyPage01Activity.m_PostPropertyId = String.valueOf(randomIdData.getRandomID());
        PostPropertyPage01Activity.m_RandomIdData = randomIdData;
        setPostPropertySomeDefaultValues();
        m_PostPropertyAPIData.setRandomPropertyID(String.valueOf(randomIdData.getRandomID()));
        m_PostPropertyAPIData.setFeatCount(randomIdData.getFeatCount());
        m_PostPropertyAPIData.setUserOwnerID(randomIdData.getUserOwnerID());
        m_PostPropertyAPIData.setPropCount(randomIdData.getPropCount());
        postPropertyAPICall.getPropertyAndProjectTypes();
        if (randomIdData.getUserPlanList() != null && !randomIdData.getUserPlanList().isEmpty()) {
            callBack.setPlanSelector(true, randomIdData.getUserPlanList());
        } else {
            callBack.setPlanSelector(false, null);
        }
    }

    private void onGetPropertyProjectTypes(String response) {
        PostPropertyIndexData postPropertyIndexData = new Gson().fromJson(response, PostPropertyIndexData.class);
        if (m_IsEditPostProperty) {
            updateEditPropertyDetails();
        } else {
            sellPropertyChecked();
        }
        callBack.setPropertyTypeSpinner(postPropertyIndexData);
        callBack.dismissLoader();
    }

    private void updateEditPropertyDetails() {
        setPostPropertySomeDefaultValues();
        updateAttributesAndAmenitiesMap();
        m_PostPropertyAPIData = new PostPropertyApiDataClass();
        m_PostPropertyAPIData.setRandomPropertyID(propertyDetailsEdit.getPropertyID());
        m_PostPropertyAPIData.setPropertyPurpose(propertyDetailsEdit.getPropertyPurpose());
        m_PostPropertyAPIData.setPropertyTypeID(propertyDetailsEdit.getPropertyTypeID());
        m_PostPropertyAPIData.setPropertyName(propertyDetailsEdit.getPropertyName());
        m_PostPropertyAPIData.setPropertyPrice(propertyDetailsEdit.getPropertyPrice());
        m_PostPropertyAPIData.setPropertyDescription(propertyDetailsEdit.getPropertyDescription());
        m_PostPropertyAPIData.setPropertyCurrentStatus(propertyDetailsEdit.getPropertyCurrentStatus());
        m_PostPropertyAPIData.setPropertyMortgage(propertyDetailsEdit.getPropertyMortgage());
        m_PostPropertyAPIData.setMorgBankID(propertyDetailsEdit.getMorgBankID());
        m_PostPropertyAPIData.setPropertyLatitude(propertyDetailsEdit.getPropertyLatitude());
        m_PostPropertyAPIData.setPropertyLongitude(propertyDetailsEdit.getPropertyLongitude());
        m_PostPropertyAPIData.setPhoneNoOne(propertyDetailsEdit.getPhoneNoOne());
        m_PostPropertyAPIData.setPhoneNoTwo(propertyDetailsEdit.getPhoneNoTwo());
        m_PostPropertyAPIData.setSubAgentId(propertyDetailsEdit.getSubAgentId());
        m_PostPropertyAPIData.setPropertyPricePerM2(propertyDetailsEdit.getPropertyPricePerM2());
        m_PostPropertyAPIData.setTotal_price(propertyDetailsEdit.getTotal_price());
        m_PostPropertyAPIData.setPropertyPrice_1(propertyDetailsEdit.getPropertyPrices().get1());
        m_PostPropertyAPIData.setPropertyPrice_5(propertyDetailsEdit.getPropertyPrices().get5());
        m_PostPropertyAPIData.setCountryCodeone(propertyDetailsEdit.getCountryCodeone());
        m_PostPropertyAPIData.setCountryCodeTwo(propertyDetailsEdit.getCountryCodeTwo());

        callBack.updateEditPropertyFields();
    }

    private void setPostPropertySomeDefaultValues() {
        m_PostPropertyAPIData.setLanguageID(SessionManager.getLanguageID(callBack.getPostPropertyContext()));
        m_PostPropertyAPIData.setUserID(SessionManager.getAccessToken(callBack.getPostPropertyContext()));
//        m_PostPropertyAPIData.setPhoneNoOne(SessionManager.getPhone(callBack.getPostPropertyContext()));
        m_PostPropertyAPIData.setAction(AppConstant.SAVE);
        m_PostPropertyAPIData.setIndProp(AppConstant.NO);
        m_PostPropertyAPIData.setExpired(AppConstant.NO);
        m_PostPropertyAPIData.setPropertyFeatured(AppConstant.OFF);
        m_PostPropertyAPIData.setProjectID("");
        m_PostPropertyAPIData.setMorgBankID("");
        m_PostPropertyAPIData.setPropertyContactAgent("");
        m_PostPropertyAPIData.setPropertyAltPhone("");
        m_PostPropertyAPIData.setPropertyAltEmail("");
        m_PostPropertyAPIData.setPropertyCheques("");
        m_PostPropertyAPIData.setRentTerm("");
        m_PostPropertyAPIData.setThreeSixtyView("");
        m_PostPropertyAPIData.setPropertyShowPhone(AppConstant.BOTH);
        m_PostPropertyAPIData.setPlanID("");
        m_PostPropertyAPIData.setParentCommunity("");
        m_PostPropertyAPIData.setSubCommunity("");
        m_PostPropertyAPIData.setBluePrintimage("");
        m_PostPropertyAPIData.setConMonth(Utils.getCurrentMonth());
        m_PostPropertyAPIData.setConYear(Utils.getCurrentYear());
    }

    /**
     * update views and data  while page come for edit post property.
     */
    public void updateAttributesAndAmenitiesMap() {
        if (propertyDetailsEdit != null) {
            Map<String, String> attributesAndAmenitiesMap = new HashMap<>();
            for (AllAttributesData attributesData : propertyDetailsEdit.getAttributes()) {
                if (attributesData.getPropertyAttrSelectedOptionID() != null && !attributesData.getPropertyAttrSelectedOptionID().isEmpty()) {
                    attributesAndAmenitiesMap.put(attributesData.getAttrID(), attributesData.getPropertyAttrSelectedOptionID());
                }
            }
            for (AmenitiesModel amenitiesModel : propertyDetailsEdit.getAmenities()) {
                if (propertyDetailsEdit.getSelectedAmenities() != null && !propertyDetailsEdit.getSelectedAmenities().isEmpty()) {
                    attributesAndAmenitiesMap.put(amenitiesModel.getAttributeID(), propertyDetailsEdit.getSelectedAmenities());
                    break;
                }
            }
            Utils.setAttrAmenMap(attributesAndAmenitiesMap);

        }
    }

    private void onGetBankList(String response) {
        BankListPropertyResponse bankListPropertyResponse = new Gson().fromJson(response, BankListPropertyResponse.class);
        bankDataList = bankListPropertyResponse.getBankListData();
        callBack.initiateBankList(bankDataList);
    }

    private void onGetAttrAminList(String response) {
        AttributeListingResponse listingData = new Gson().fromJson(response, AttributeListingResponse.class);
        amenitiesFromResponse = response;
        isAmenitiesFound = listingData.getData().getAmenities() != null && !listingData.getData().getAmenities().isEmpty();
        initiateAttributesList(listingData.getData().getAllAttributes());
    }

    private void initiateAttributesList(ArrayList<AllAttributesData> allAttributesList) {
        AllAttributesData bedRoomData;
        int bedRoomIndex = -1;
        new AllAttributesData();
        ArrayList<BedRoomInfo> bedRoomListData = new ArrayList<>();
        for (int i = 0; i < allAttributesList.size(); i++) {
            AllAttributesData data = allAttributesList.get(i);
            if (data.getAttrID().equalsIgnoreCase(Utils.ATTR_BED_ROOM_ID)) {
                bedRoomData = data;
                bedRoomIndex = i;
                if (bedRoomData.getAttrOptName() != null && !bedRoomData.getAttrOptName().equals("")) {
                    String[] bedRoomList = bedRoomData.getAttrOptName().split(",");
                    for (String item : bedRoomList) {
                        String[] itemSplit = item.split("_");
                        BedRoomInfo info = new BedRoomInfo();
                        info.setAttrOptName(itemSplit[0]);
                        info.setAttributeID(item);
                        info.setAttrID(bedRoomData.getAttrID());
                        bedRoomListData.add(info);
                    }

                }

            }
            callBack.setPropertyBedRoomFeature(bedRoomListData);

        }
        if (bedRoomIndex > -1) {
            allAttributesList.remove(bedRoomIndex);
        }
        callBack.setPropertyAttributeFeature(allAttributesList);
    }

    private void doProcessWithBluePrint(String response) {
        Bitmap bitmap = null;
        if (response != null && !response.isEmpty()) {
            String[] splitResponse = response.split(AppConstant.BLUEPRINTNAME_SEPERATION_SYMBOL);
            bitmap = BitmapFactory.decodeFile(splitResponse[1]);
            try {
                JSONObject jsonObject = new JSONObject(splitResponse[0]);
                if (m_PostPropertyAPIData == null) {
                    m_PostPropertyAPIData = new PostPropertyApiDataClass();
                }
                m_PostPropertyAPIData.setBluePrintimage(jsonObject.getJSONObject("data").getString("imageName"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        callBack.updateBlurPrintBitMap(bitmap, null);
    }

    private void getSavedImages(String response) {
        if (response != null && !response.isEmpty()) {
            savedImageResponse = new Gson().fromJson(response, LoadSavedImageResponse.class);
                callBack.updateSavedImagesViews(savedImageResponse.getData(), savedImageResponse.getImgUrl());
        } else {
            callBack.updateSavedImagesViews(null, null);
        }
    }

    private void getSavedVideos(String response) {
        if (response != null && !response.isEmpty()) {
            LoadSavedVideoResponse savedVideoResponse = new Gson().fromJson(response, LoadSavedVideoResponse.class);
            callBack.updateSavedVideoUrl(savedVideoResponse.getData());
        } else {
            callBack.updateSavedVideoUrl(null);
        }
    }

    private void getPostPropertyResponse(String response, int apiTransactionID) {
        GlobalValues.clearPropertyValues();
        String propertyId;
        if (AppConstant.PP_POST_PROPERTY == apiTransactionID) {
            PostPropertyResponse propertyResponse = new Gson().fromJson(response, PostPropertyResponse.class);
            propertyId = propertyResponse.getPropertyID();
        } else {
            propertyId = propertyDetailsEdit.getPropertyID();
        }
        callBack.onSubmitPostPropertySuccessfully(propertyId);

    }

    private void getEditPostPropertyResponse(String response) {
        EditPostedPropertyResponse editPostedPropertyResponse = new Gson().fromJson(response, EditPostedPropertyResponse.class);
        PostPropertyPage01Activity.m_EditPostedPropertyData = editPostedPropertyResponse.getData();
        propertyDetailsEdit = m_EditPostedPropertyData.getPropertyDetails();
        postPropertyAPICall.getPropertyAndProjectTypes();
    }

    public void onClickSelectBank() {
        if (bankDataList != null) {
            if (!bankDataList.isEmpty()) {
                callBack.initiateBankList(bankDataList);
            } else {
                onNoMotgagedClick();
            }
        } else {
            if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
                onAPIFailure("", AppConstant.NETWORK_FAILURE);
            } else {
                postPropertyAPICall.getBankListAPI();
            }
        }
    }

    public void onPropertyVideoUrlUpdate(CharSequence text) {
        videoPropertyUrl = text.toString();
    }

    public void onPropertyTitleTextChanged(CharSequence text) {
        m_PostPropertyAPIData.setPropertyName(String.valueOf(text));
    }

    public void onProperty360TextChanged(CharSequence text) {
        m_PostPropertyAPIData.setThreeSixtyView(String.valueOf(text));
    }

    public void
    onPropertyPriceTextChanged(CharSequence text) {
        m_PostPropertyAPIData.setPropertyPrice(NumberTextWatcherForThousand.trimCommaOfString(String.valueOf(text)));
    }

    public void onPhoneNoOneTextChanged(CharSequence text){

        m_PostPropertyAPIData.setPhoneNoOne(String.valueOf(text));
    }

    public void onPhoneNoTwoTextChanged(CharSequence text){
        m_PostPropertyAPIData.setPhoneNoTwo(String.valueOf(text));

    }


    public void onPropertyDescriptionTextChanged(CharSequence description) {
        m_PostPropertyAPIData.setPropertyDescription(String.valueOf(description));
    }

    public void onPage01NextClick() {
        if (m_PostPropertyAPIData.getPropertyTypeID() != null && !m_PostPropertyAPIData.getPropertyTypeID().isEmpty()) {
            if (m_PostPropertyAPIData.getPropertyName() != null && !m_PostPropertyAPIData.getPropertyName().isEmpty()) {
/*
                if (m_PostPropertyAPIData.getPropertyDescription() != null && !m_PostPropertyAPIData.getPropertyDescription().isEmpty()) {
*/
                if(!selectedCurrencyID.equals("0")){
                    if (m_PostPropertyAPIData.getPropertyPrice() != null && !m_PostPropertyAPIData.getPropertyPrice().isEmpty() && Utils.checkPriceNotHaveOnlyDot(m_PostPropertyAPIData.getPropertyPrice())
                            && Double.parseDouble(m_PostPropertyAPIData.getPropertyPrice()) > 0) {

                        if (m_PostPropertyAPIData.getPropertyPurpose() != null && m_PostPropertyAPIData.getPropertyPurpose().equalsIgnoreCase(AppConstant.PP_SELL)) {
                            if(selectedCurrencyType!=0){
                            if (m_PostPropertyAPIData.getPropertyMortgage() != null && m_PostPropertyAPIData.getPropertyMortgage().equalsIgnoreCase(AppConstant.YES)) {
                                if (m_PostPropertyAPIData.getMorgBankID() != null && !m_PostPropertyAPIData.getMorgBankID().isEmpty()) {

//                                                callBack.onNextClick();
                                    validatePhoneNumberifPresent();

                                } else {
                                    callBack.showToastMessage(R.string.select_bank, null);
                                }
                            } else {
//                                            callBack.onNextClick();
                                validatePhoneNumberifPresent();
                            }
                            }else {
                                callBack.showToastMessage(R.string.select_currency_terms, null);

                            }
                        } else {
//                                        callBack.onNextClick();
                            validatePhoneNumberifPresent();

                        }





                    } else {
                        callBack.showToastMessage(R.string.enter_property_price, null);
                    }
                }else {
                    callBack.showToastMessage(R.string.select_currency_type, null);
                }
                /*} else {
                    callBack.showToastMessage(R.string.enter_property_description, null);
                }*/
            } else {
                callBack.showToastMessage(R.string.enter_property_title, null);
            }
        } else {
            callBack.showToastMessage(R.string.select_property_type, null);
        }
    }


    public void  validatePhoneNumberifPresent(){
        if(!m_PostPropertyAPIData.getPhoneNoOne().isEmpty()){
            if(!Utils.isValidMobile(m_PostPropertyAPIData.getPhoneNoOne())){
                callBack.showToastMessage(R.string.valid_phone_number,null);

            }else{
                if(!m_PostPropertyAPIData.getPhoneNoTwo().isEmpty()) {
                    if(!Utils.isValidMobile(m_PostPropertyAPIData.getPhoneNoTwo())){
                        callBack.showToastMessage(R.string.valid_phone_number,null);

                    }else {
                        onPage03NextClick();
                    }
                }else {
                    onPage03NextClick();
                }
            }
        }else if(!m_PostPropertyAPIData.getPhoneNoTwo().isEmpty()) {
            if(!Utils.isValidMobile(m_PostPropertyAPIData.getPhoneNoTwo())){
                callBack.showToastMessage(R.string.valid_phone_number,null);

            }else {
                if(!m_PostPropertyAPIData.getPhoneNoOne().isEmpty()) {
                    if(!Utils.isValidMobile(m_PostPropertyAPIData.getPhoneNoOne())){
                        callBack.showToastMessage(R.string.valid_phone_number,null);

                    }else {
                        onPage03NextClick();
                    }
                }else {
                    onPage03NextClick();
                }            }

        }
        else {
            onPage03NextClick();
        }
    }


    public void onPage03NextClick() {
        if (isBedRoomMandatory) {
            if (!isAttributeValidationSuccess(Utils.ATTR_BED_ROOM_ID)) {
                return;
            }
        }
        if (callBack.getAttrList() != null && callBack.getAttrList().size() > 0) {
            for (int i = 0; i < callBack.getAttrList().size(); i++) {
                AllAttributesData data = callBack.getAttrList().get(i);
                if (Utils.ATTR_PHONE_NO_ID.equalsIgnoreCase(data.getAttrID()) || Utils.ATTR_AREA_ID.equalsIgnoreCase(data.getAttrID()) ||
                        Utils.ATTR_BATH_ROOM_ID.equalsIgnoreCase(data.getAttrID()) || Utils.ATTR_KITCHEN_ID.equalsIgnoreCase(data.getAttrID()) ||
                        Utils.ATTR_LIVING_ROOM_ID.equalsIgnoreCase(data.getAttrID()) || Utils.ATTR_BED_ROOM_ID.equalsIgnoreCase(data.getAttrID()) ||
                        Utils.ATTR_FLOOR_NUMBER_ID.equalsIgnoreCase(data.getAttrID())) {
                    if (!isAttributeValidationSuccess(data.getAttrID())) {
                        return;
                    }
                }
            }
            setValueInPropertyDataClass();

                if(selectedCurrencyID==AppConstant.IQD_ID){
                    m_PostPropertyAPIData.setPropertyPrice_1(m_PostPropertyAPIData.getPropertyPrice());
                    m_PostPropertyAPIData.setPropertyPrice_5("0");

                }else {
                    m_PostPropertyAPIData.setPropertyPrice_5(m_PostPropertyAPIData.getPropertyPrice());
                    m_PostPropertyAPIData.setPropertyPrice_1("0");

                }


            if(selectedCurrencyType == AppConstant.PRICE_PER_M2){
                m_PostPropertyAPIData.setPropertyPricePerM2(m_PostPropertyAPIData.getPropertyPrice());
                m_PostPropertyAPIData.setTotal_price("0");
            }else {
                m_PostPropertyAPIData.setTotal_price(m_PostPropertyAPIData.getPropertyPrice());
                m_PostPropertyAPIData.setPropertyPricePerM2("0");
            }

            Log.d("cs",m_PostPropertyAPIData.getPropertyCurrentStatus());


            callBack.onNextClick();
        }
    }

    public void onPage04NextClick() {
        setValueInPropertyDataClass();
        callBack.onNextClick();
        /*if (Utils.getAttrAmenMap() != null && Utils.getAttrAmenMap().containsKey(Utils.ATTR_AMENITIES_ID) && !Utils.getAttrAmenMap().get(Utils.ATTR_AMENITIES_ID).isEmpty()) {
            setValueInPropertyDataClass();
            callBack.onNextClick();
        } else {
            callBack.showToastMessage(R.string.please_select_atleast_amenities, null);
        }*/
    }

    private void setValueInPropertyDataClass() {
        if (m_PostPropertyAPIData == null)
            m_PostPropertyAPIData = new PostPropertyApiDataClass();
        m_PostPropertyAPIData.setAttributeList("");
        for (Map.Entry<String, String> map : Utils.getAttrAmenMap().entrySet()) {
            String attribute = map.getKey() + "-" + map.getValue();
            if (m_PostPropertyAPIData.getAttributeList() == null || m_PostPropertyAPIData.getAttributeList().equals("")) {
                m_PostPropertyAPIData.setAttributeList(attribute);
            } else {
                m_PostPropertyAPIData.setAttributeList(m_PostPropertyAPIData.getAttributeList() + ";" + attribute);
            }
        }
    }

    public void onUploadPhotoClick() {
        isMultipleSelectionEnable = true;
        if (checkPermissionAndRequest()) {
            callBack.initiateGalleryIntent();
        }
    }

    public void onUploadBluePrintClick() {
        isMultipleSelectionEnable = false;
        if (checkPermissionAndRequest()) {
            callBack.initiateGalleryIntent();
        }
    }

    public void onCloseBluePrintImage() {
        m_PostPropertyAPIData.setBluePrintimage("");
        callBack.updateBlurPrintBitMap(null, null);
    }

    private boolean checkPermissionAndRequest() {
        int result = ContextCompat.checkSelfPermission(callBack.getPostPropertyContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            result = ContextCompat.checkSelfPermission(callBack.getPostPropertyContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(callBack.getPostPropertyContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, MULTIPLE_PERMISSION_REQUEST);
                return false;
            }
        } else {
            ActivityCompat.requestPermissions(callBack.getPostPropertyContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, MULTIPLE_PERMISSION_REQUEST);
            return false;
        }
    }

    public void onUploadVideoUrlClick() {
        if (videoPropertyUrl.isEmpty()) {
            callBack.showToastMessage(R.string.enter_youtube_video_url, null);
        } else if (Utils.validateYoutubeUrl(videoPropertyUrl)) {
            if (videoPropertyUrl.contains("https://youtu.be/")) {
                videoPropertyUrl = videoPropertyUrl.replace("https://youtu.be/", "https://www.youtube.com/watch?v=");
            }
            postPropertyAPICall.uploadPropertyVideo(videoPropertyUrl, m_PostPropertyAPIData.getRandomPropertyID());
        } else {
            callBack.showToastMessage(R.string.valid_youtube_url, null);
        }
    }

    public void initiateDeletePropertyVideo(Dialog alertDialog, String videoId, int position) {
        postPropertyAPICall.deleteVideoFromServer(alertDialog, videoId, position, m_PostPropertyAPIData.getRandomPropertyID());
    }

    public void initiateEditPropertyVideo(Dialog alertDialog, String videoId, String title, String overview) {
        postPropertyAPICall.saveEditedVideoInfoToServer(alertDialog, videoId, title, overview, m_PostPropertyAPIData.getRandomPropertyID());
    }

    public void onPage05NextClick() {
        if (!isImageUploaded) {
            callBack.showToastMessage(R.string.valid_property_image, null);
            return;
        } else if (m_PostPropertyAPIData.getThreeSixtyView() != null &&
                !m_PostPropertyAPIData.getThreeSixtyView().isEmpty() && !Patterns.WEB_URL.matcher(m_PostPropertyAPIData.getThreeSixtyView()).matches()) {
            callBack.showToastMessage(R.string.valid_url, null);
            return;
        }
        if (m_IsEditPostProperty) {
            postPropertyAPICall.postEditedPropertyToServer(m_PostPropertyAPIData);
        } else {
            postPropertyAPICall.postPropertyToServer(m_PostPropertyAPIData);
        }
    }

    public void onYesMotgagedClick() {
        callBack.onMotagagedClicked(true);
    }

    public void onNoMotgagedClick() {
        callBack.onMotagagedClicked(false);
    }

    public void onPropertyTypeSpinnerClick() {
        callBack.onPropertyTypeSpinnerClick();
    }

    public void onPropertyPostBySpinnerClick() {
        callBack.onPropertyPostBySpinnerClick();
    }

    public void sellPropertyChecked() {
        rentSelected = false;
        callBack.setPropertyBasedOnSellAndRentType(true);
        underConstructionChecked();
    }

    public void rentPropertyChecked() {
        rentSelected = true;
        callBack.setPropertyBasedOnSellAndRentType(false);
        readyToMoveForRentChecked();
    }

    public void underConstructionChecked() {
        m_PostPropertyAPIData.setPropertyCurrentStatus(AppConstant.PP_UNDER_CONSTRUCTION);
    }

    public void readyToMoveForSellChecked() {
        m_PostPropertyAPIData.setPropertyCurrentStatus(AppConstant.PP_READY_TO_MOVE_SELL);
    }

    public void rentedChecked() {
        m_PostPropertyAPIData.setPropertyCurrentStatus(AppConstant.PP_RENTED);
    }

    public void readyToMoveForRentChecked() {
        callBack.updatePropertyCurrentStatus(AppConstant.PP_READY_TO_MOVE_RENT);
        m_PostPropertyAPIData.setPropertyCurrentStatus(AppConstant.PP_READY_TO_MOVE_RENT);
    }

    public void willBeReadySoonChecked() {
        m_PostPropertyAPIData.setPropertyCurrentStatus(AppConstant.PP_WILL_BE_READY_SOON);
    }



    public void usdChecked(CompoundButton button,boolean checked){
        if(checked){
            selectedCurrencyID = AppConstant.USD_ID;
            selectedCurrencyType = 0;
        }
        m_PostPropertyAPIData.setCurrencyID_5(AppConstant.USD_ID);
        m_PostPropertyAPIData.setCurrencyID_1(AppConstant.IQD_ID);

        callBack.setPriceperMTotalPriceUncheck(false);


    }
    public void iqdChecked(CompoundButton button,boolean checked){
        if(checked){
            selectedCurrencyID = AppConstant.IQD_ID;
            selectedCurrencyType = 0;

        }

        m_PostPropertyAPIData.setCurrencyID_1(AppConstant.IQD_ID);
        m_PostPropertyAPIData.setCurrencyID_5(AppConstant.USD_ID);

        callBack.setPriceperMTotalPriceUncheck(false);


    }
    public void priceinMChecked(CompoundButton button, boolean checked){
        if(checked)
            selectedCurrencyType = AppConstant.PRICE_PER_M2;





    }
    public void totalPriceChecked(CompoundButton button, boolean checked){
        if(checked)
            selectedCurrencyType = AppConstant.TOTAL_PRICE;




    }
    private boolean isAttributeValidationSuccess(String attrId) {
        if (attrId.equalsIgnoreCase(Utils.ATTR_BED_ROOM_ID) && isLivingRoomMandatory && (Utils.getAttrAmenMap() == null || !Utils.getAttrAmenMap().containsKey(attrId))) {
            callBack.showToastMessage(R.string.enter_attr_bed_room_spec, null);
            return false;
        }
        if (attrId.equalsIgnoreCase(Utils.ATTR_PHONE_NO_ID) && isPhoneNumberMandatory && (Utils.getAttrAmenMap() == null || !Utils.getAttrAmenMap().containsKey(attrId))) {
            callBack.showToastMessage(R.string.enter_attr_phone_no, null);
            return false;
        }
        if (attrId.equalsIgnoreCase(Utils.ATTR_AREA_ID) && isAreaMandatory &&
                (Utils.getAttrAmenMap() == null || !Utils.getAttrAmenMap().containsKey(attrId) || !Utils.checkPriceNotHaveOnlyDot(Utils.getAttrAmenMap().get(attrId)))) {
            callBack.showToastMessage(R.string.enter_attr_area, null);
            return false;
        }
        if (attrId.equalsIgnoreCase(Utils.ATTR_BATH_ROOM_ID) && isBathRoomMandatory && (Utils.getAttrAmenMap() == null || !Utils.getAttrAmenMap().containsKey(attrId))) {
            callBack.showToastMessage(R.string.enter_attr_bathroom, null);
            return false;
        }
        if (attrId.equalsIgnoreCase(Utils.ATTR_KITCHEN_ID) && isKitchenMandatory && (Utils.getAttrAmenMap() == null || !Utils.getAttrAmenMap().containsKey(attrId))) {
            callBack.showToastMessage(R.string.enter_attr_kitchen, null);
            return false;
        }
        if (attrId.equalsIgnoreCase(Utils.ATTR_LIVING_ROOM_ID) && isLivingRoomMandatory && (Utils.getAttrAmenMap() == null || !Utils.getAttrAmenMap().containsKey(attrId))) {
            callBack.showToastMessage(R.string.enter_attr_living_room, null);
            return false;
        }
        if (attrId.equalsIgnoreCase(Utils.ATTR_FLOOR_NUMBER_ID) && isFloorNumberMandatory &&
                (Utils.getAttrAmenMap() == null || !Utils.getAttrAmenMap().containsKey(attrId) || !Utils.checkPriceNotHaveOnlyDot(Utils.getAttrAmenMap().get(attrId)))) {
            callBack.showToastMessage(R.string.enter_attr_floor_number, null);
            return false;
        }
        return true;
    }

    public void uploadImageAndBluePrintForProperty(Intent data, PostPropertyPage05Activity context) {
        this.context = context;
        ArrayList<PairedValues> values = new ArrayList<>();
        if (isMultipleSelectionEnable) {
            values.add(new PairedValues("randomPropertyID", m_PostPropertyAPIData.getRandomPropertyID()));
            values.add(new PairedValues("imageCatID", AppConstant.IMAGE_CAT_ID));
        }

        if (data.getClipData() != null) {
            int count = data.getClipData().getItemCount();

            for (int i = 0; i < count; i++) {
                String filPath = Utils.getPath(callBack.getPostPropertyContext(), data.getClipData().getItemAt(i).getUri());
                File file = new File(filPath);
                long length = file.length()/1024;
                Log.d("imageSize",""+length);
                if(length>1024){
                    filPath = compressImage(data.getClipData().getItemAt(i).getUri());
                }


                if (filPath != null && !filPath.isEmpty()) {
                    if (isMultipleSelectionEnable) {
                        values.add(new PairedValues("myfile[]", new File(filPath)));
                    } else {
                        values.add(new PairedValues("bluePrintImage", new File(filPath)));
                        postPropertyAPICall.uploadImageFiles(values, AppConstant.UPLOAD_BLUE_PRINT, filPath, context);
                        m_PostPropertyAPIData.setBluePrintPath(filPath);
                        break;
                    }
                }
            }
        } else {
            String filPath = Utils.getPath(callBack.getPostPropertyContext(), data.getData());
            File file = new File(filPath);
            long length = file.length()/1024;
            Log.d("imageSize",""+length);
            if(length>1024){
                filPath = compressImage(data.getData());
            }
            if (isMultipleSelectionEnable) {
                values.add(new PairedValues("myfile[]", new File(filPath)));
            } else {
                values.add(new PairedValues("bluePrintImage", new File(filPath)));
                postPropertyAPICall.uploadImageFiles(values, AppConstant.UPLOAD_BLUE_PRINT, filPath, context);
                m_PostPropertyAPIData.setBluePrintPath(filPath);

            }
        }
        if (isMultipleSelectionEnable) {
            postPropertyAPICall.uploadImageFiles(values, AppConstant.UPLOAD_GALLERY_IMAGE, null,context);
        }
    }

    public String compressImage(Uri imagePath){

        String temPath="";
        try {
            Bitmap  original = MediaStore.Images.Media.getBitmap(context.getContentResolver(),imagePath);
            File path =  new File(context.getFilesDir()+File.separator+"Paya");

            if(!path.exists() && !path.isDirectory()){
            if (path.mkdirs()){
            }
            }

        String fileName = String.format("%d.jpg",System.currentTimeMillis());
        File finalFile = new File(path,fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(finalFile);
        original.compress(Bitmap.CompressFormat.JPEG,10,fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        temPath = finalFile.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
        }

        File file1 = new File(temPath);
        long length1 = file1.length()/1024;
        Log.d("imageSize",""+length1+temPath);

        return temPath;
    }

    private ArrayList<SavedImageInfo> getSavedImagesFromPostPropertyEdit(List<PostedPropertyGalleryInfo> gallery) {
        ArrayList<SavedImageInfo> imageInfoArrayList = new ArrayList<>();
        for (PostedPropertyGalleryInfo postedPropertyGalleryInfo : gallery) {
            SavedImageInfo savedImageInfo = new SavedImageInfo();
            savedImageInfo.setIsCoverImage(postedPropertyGalleryInfo.getIsCoverImage());
            savedImageInfo.setPropertyImageID(postedPropertyGalleryInfo.getPropertyImageID());
            savedImageInfo.setPropertyImageName(postedPropertyGalleryInfo.getPropertyImageName());
            imageInfoArrayList.add(savedImageInfo);
        }
        return imageInfoArrayList;
    }

    public void deleteGalleryImageFromServer(String imageID, int position) {
        postPropertyAPICall.deleteGalleryImageFromServer(imageID, m_PostPropertyAPIData.getRandomPropertyID());
    }

    public interface PostPropertyViewModelCallBack {

        void showLoader();

        void dismissLoader();

        void noInternetMessage();

        void onPropertyTypeSpinnerClick();

        void setPropertyBasedOnSellAndRentType(boolean isPropertyForSell);

        void setPriceperMTotalPriceUncheck(boolean isCurrencyTypeChanged);

        Activity getPostPropertyContext();

        void setPropertyTypeSpinner(PostPropertyIndexData postPropertyIndexData);

        void onMotagagedClicked(boolean isMotagagedYes);

        void initiateBankList(ArrayList<BankListData> bankListData);

        void onNextClick();

        void showToastMessage(int messageId, String message);

        void setPropertyBedRoomFeature(ArrayList<BedRoomInfo> bedRoomListData);

        void setPropertyAttributeFeature(ArrayList<AllAttributesData> allAttributesList);

        ArrayList<AllAttributesData> getAttrList();

        void updateAmenitiesInAdapter(ArrayList<AmenitiesModel> amenities);

        void initiateGalleryIntent();

        void updateSavedImagesViews(ArrayList<SavedImageInfo> savedImageInfoArrayList, String imgUrl);

        void updateBlurPrintBitMap(Bitmap bitmap, String bluePrintImageUrl);

        void updateSavedVideoUrl(ArrayList<SavedVideoInfo> savedVideoInfoArrayList);

        void setPlanSelector(boolean isPlanAvailable, ArrayList<UserPlanInfo> userPlanList);

        void onSubmitPostPropertySuccessfully(String propertyID);

        void updatePropertyCurrentStatus(String propertyCurrentStatus);

        void updateEditPropertyFields();

        void initiateSubAgentListSpinner(List<AgentDataListDATAItemObject> subAgentSpinnerData);

        void onPropertyPostBySpinnerClick();

        void updateDescription(String propertyDescription);



        void updateCity(CityListingResponse cityListingResponse);

        void attachNBHRcvItems(ListLocProjectResponse listLocProjectResponse);
    }
}
