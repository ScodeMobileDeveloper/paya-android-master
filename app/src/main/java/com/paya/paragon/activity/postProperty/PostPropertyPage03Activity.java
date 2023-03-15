package com.paya.paragon.activity.postProperty;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.paya.paragon.R;
import com.paya.paragon.adapter.AdapterBedRooms;
import com.paya.paragon.adapter.AdapterSpecifications;
import com.paya.paragon.api.agentList.AgentDataListDATAItemObject;
import com.paya.paragon.api.bankListPropertyPost.BankListData;
import com.paya.paragon.api.bedRoomList.BedRoomInfo;
import com.paya.paragon.api.cityList.CityListingResponse;
import com.paya.paragon.api.listLocProject.ListLocProjectResponse;
import com.paya.paragon.api.postProperty.attributeListing.AllAttributesData;
import com.paya.paragon.api.postProperty.loadGalleryImage.SavedImageInfo;
import com.paya.paragon.api.postProperty.loadVideo.SavedVideoInfo;
import com.paya.paragon.api.postProperty.postPropertyIndex.PostPropertyIndexData;
import com.paya.paragon.api.postProperty.randomID.UserPlanInfo;
import com.paya.paragon.api.propertyDetails.AmenitiesModel;
import com.paya.paragon.base.BaseViewModelActivity;
import com.paya.paragon.databinding.ActivityPostPropertyPage03Binding;
import com.paya.paragon.utilities.AppConstant;
import com.paya.paragon.utilities.Utils;
import com.paya.paragon.viewmodel.PostPropertyViewModel;

import java.util.ArrayList;
import java.util.List;

public class PostPropertyPage03Activity extends BaseViewModelActivity<PostPropertyViewModel> implements
        PostPropertyViewModel.PostPropertyViewModelCallBack, AdapterSpecifications.AdapterSpecificationsCallback {

    private ActivityPostPropertyPage03Binding binding;
    private PostPropertyViewModel viewModel;
    private AdapterBedRooms adapterBedRooms;
    private AdapterSpecifications adapterSpecifications;
    private ArrayList<AllAttributesData> allAttributesList;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_property_page_03);
        binding.setActivity(this);
        binding.setViewModel(onCreateViewModel());
        initiateToolBar();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, binding.cl03Parent);
    }

    private void initiateToolBar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow_black);
        binding.toolbarTitle.setText(getString(R.string.property_specification));
        binding.toolbarTitle.setTextSize(16);
    }

    @Override
    public PostPropertyViewModel onCreateViewModel() {
        viewModel = ViewModelProviders.of(this).get(PostPropertyViewModel.class);
        viewModel.initiatePostProperty(this);
        viewModel.initiateAttributeAminitiesListAPI();
        return viewModel;
    }

    @Override
    public void showLoader() {
        showAnimatedProgressBar(this);
    }

    @Override
    public void dismissLoader() {
        dismissAnimatedProgressBar();
    }



    @Override
    public void noInternetMessage() {
        Utils.showAlertNoInternet(PostPropertyPage03Activity.this);
    }

    @Override
    public void showToastMessage(int messageId, String message) {
        Toast.makeText(this, message != null ? message : messageId > 0 ? getString(messageId) : getString(R.string.please_try_after_some_times), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPropertyBedRoomFeature(ArrayList<BedRoomInfo> bedRoomListData) {
        if (bedRoomListData != null && !bedRoomListData.isEmpty()) {
            binding.layoutRecyclerBedroomsPostProperty.setVisibility(View.VISIBLE);
            adapterBedRooms = new AdapterBedRooms(bedRoomListData, "");
            binding.recyclerBedroomsContentPostProperty.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            binding.recyclerBedroomsContentPostProperty.setHasFixedSize(true);
            binding.recyclerBedroomsContentPostProperty.setAdapter(adapterBedRooms);
            viewModel.isBedRoomMandatory = true;
        } else {
            binding.layoutRecyclerBedroomsPostProperty.setVisibility(View.GONE);
            viewModel.isBedRoomMandatory = false;
        }
    }

    @Override
    public void setPropertyAttributeFeature(ArrayList<AllAttributesData> attrList) {
        if (attrList.size() > 0) {
            allAttributesList = attrList;
            adapterSpecifications = new AdapterSpecifications(PostPropertyPage03Activity.this, allAttributesList, "none", this);
            binding.recyclerSpecificationsContentPostProperty.setLayoutManager(new LinearLayoutManager(this));
            binding.recyclerSpecificationsContentPostProperty.setHasFixedSize(true);
            binding.recyclerSpecificationsContentPostProperty.setAdapter(adapterSpecifications);
        }
    }

    @Override
    public ArrayList<AllAttributesData> getAttrList() {
        return allAttributesList;
    }

    /**
     * this is for post property 01 page
     */
    @Override
    public void updatePropertyCurrentStatus(String propertyCurrentStatus) {

    }

    /**
     * this is for post property 01 page
     */
    @Override
    public void updateEditPropertyFields() {

    }

    /**
     * this is for post property 01 page
     */
    @Override
    public void initiateSubAgentListSpinner(List<AgentDataListDATAItemObject> subAgentSpinnerData) {

    }

    /**
     * this is for post property 01 page
     */
    @Override
    public void onPropertyPostBySpinnerClick() {

    }

    @Override
    public void updateDescription(String propertyDescription) {

    }

    @Override
    public void updateCity(CityListingResponse cityListingResponse) {

    }

    @Override
    public void attachNBHRcvItems(ListLocProjectResponse listLocProjectResponse) {

    }

    /**
     * this is for post property 04 page (Property Amenities)
     */
    @Override
    public void updateAmenitiesInAdapter(ArrayList<AmenitiesModel> amenities) {
    }

    @Override
    public Activity getPostPropertyContext() {
        return PostPropertyPage03Activity.this;
    }

    /**
     * this is for post property 01 page
     */
    @Override
    public void setPlanSelector(boolean isPlanAvailable, ArrayList<UserPlanInfo> userPlanList) {

    }

    /**
     * this is for post property 01 page
     */
    @Override
    public void onPropertyTypeSpinnerClick() {
    }

    /**
     * this is for post property 01 page
     */
    @Override
    public void setPropertyBasedOnSellAndRentType(boolean isPropertyForSell) {

    }

    @Override
    public void setPriceperMTotalPriceUncheck(boolean isCurrencyTypeChanged) {

    }



    /**
     * this is for post property 01 page
     */
    @Override
    public void setPropertyTypeSpinner(PostPropertyIndexData postPropertyIndexData) {

    }

    /**
     * this is for post property 01 page
     */
    @Override
    public void onMotagagedClicked(boolean isMotagagedYes) {

    }

    /**
     * this is for post property 01 page
     */
    @Override
    public void initiateBankList(ArrayList<BankListData> bankListData) {

    }

    /**
     * this is for post property 05 page (Property Gallery, Videos and BluePrint upload page)
     */
    @Override
    public void initiateGalleryIntent() {

    }

    /**
     * this is for post property 05 page (Property Gallery, Videos and BluePrint upload page)
     */
    @Override
    public void updateSavedImagesViews(ArrayList<SavedImageInfo> savedImageInfoArrayList, String imgUrl) {

    }

    /**
     * this is for post property 05 page (Property Gallery, Videos and BluePrint upload page)
     */
    @Override
    public void updateBlurPrintBitMap(Bitmap bitmap, String bluePrintImageUrl) {

    }

    /**
     * this is for post property 05 page (Property Gallery, Videos and BluePrint upload page)
     */
    @Override
    public void updateSavedVideoUrl(ArrayList<SavedVideoInfo> savedVideoInfoArrayList) {

    }

    /**
     * this is for post property 05 page (Property Gallery, Videos and BluePrint upload page)
     */
    @Override
    public void onSubmitPostPropertySuccessfully(String propertyID) {

    }

    @Override
    public void onNextClick() {
        Intent intent = new Intent(PostPropertyPage03Activity.this, viewModel.isAmenitiesFound ? PostPropertyPage04Activity.class : PostPropertyPage05Activity.class);
        if (viewModel.isAmenitiesFound) {
            intent.putExtra(AppConstant.I_AMENITIES, viewModel.amenitiesFromResponse);
        }
        startActivity(intent);
    }

    @Override
    public void isPhoneNoMandatory(boolean isMandatory) {
        if (!viewModel.isPhoneNumberMandatory) {
            viewModel.isPhoneNumberMandatory = isMandatory;
        }
    }

    @Override
    public void isAreaMandatory(boolean isMandatory) {
        if (!viewModel.isAreaMandatory) {
            viewModel.isAreaMandatory = isMandatory;
        }
    }

    @Override
    public void isBathRoomMandatory(boolean isMandatory) {
        if (!viewModel.isBathRoomMandatory) {
            viewModel.isBathRoomMandatory = isMandatory;
        }
    }

    @Override
    public void isKitchenMandatory(boolean isMandatory) {
        if (!viewModel.isKitchenMandatory) {
            viewModel.isKitchenMandatory = isMandatory;
        }
    }

    @Override
    public void isBedRoomMandatory(boolean isMandatory) {
        if (!viewModel.isBedRoomMandatory) {
            viewModel.isBedRoomMandatory = isMandatory;
        }
    }

    @Override
    public void isFloorNumberMandatory(boolean isMandatory) {

    }

    @Override
    public void isLivingRoomMandatory(boolean isMandatory) {
        if (!viewModel.isLivingRoomMandatory) {
            viewModel.isLivingRoomMandatory = isMandatory;
        }
    }

    public void onBackClick() {
        onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
