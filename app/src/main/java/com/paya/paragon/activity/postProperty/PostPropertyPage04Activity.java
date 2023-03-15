package com.paya.paragon.activity.postProperty;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.paya.paragon.R;
import com.paya.paragon.adapter.AdapterAmenitiesSelector;
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
import com.paya.paragon.databinding.ActivityPostPropertyPage04Binding;
import com.paya.paragon.utilities.Utils;
import com.paya.paragon.viewmodel.PostPropertyViewModel;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class PostPropertyPage04Activity extends BaseViewModelActivity<PostPropertyViewModel> implements
        PostPropertyViewModel.PostPropertyViewModelCallBack {

    private ActivityPostPropertyPage04Binding binding;
    private PostPropertyViewModel viewModel;
    private AdapterAmenitiesSelector adapterAmenitiesSelector;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_property_page_04);
        binding.setActivity(this);
        binding.setViewModel(onCreateViewModel());
        initiateToolBar();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, binding.cl04Parent);
    }

    private void initiateToolBar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow_black);
        binding.toolbarTitle.setText(getString(R.string.amenities_optional));
        binding.toolbarTitle.setTextSize(16);
    }

    @Override
    public PostPropertyViewModel onCreateViewModel() {
        viewModel = ViewModelProviders.of(this).get(PostPropertyViewModel.class);
        viewModel.initiatePostProperty(this);
        viewModel.initiateAminitiesListFromIntent(getIntent());
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
        Utils.showAlertNoInternet(PostPropertyPage04Activity.this);
    }

    @Override
    public void showToastMessage(int messageId, String message) {
        Toast.makeText(this, message != null ? message : messageId > 0 ? getString(messageId) : getString(R.string.please_try_after_some_times), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateAmenitiesInAdapter(ArrayList<AmenitiesModel> amenitiesList) {
        adapterAmenitiesSelector = new AdapterAmenitiesSelector(amenitiesList);
        binding.recyclerAmenitiesContentPostProperty.setLayoutManager(new GridLayoutManager(this, 3));
        binding.recyclerAmenitiesContentPostProperty.setNestedScrollingEnabled(false);
        binding.recyclerAmenitiesContentPostProperty.setAdapter(adapterAmenitiesSelector);
        binding.recyclerAmenitiesContentPostProperty.clearFocus();
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
    public Activity getPostPropertyContext() {
        return PostPropertyPage04Activity.this;
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
    public void setPlanSelector(boolean isPlanAvailable, ArrayList<UserPlanInfo> userPlanList) {

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

    @Override
    public void onNextClick() {
        Timber.tag("onNextClick: ##########").e("%s", new Gson().toJson(PostPropertyPage01Activity.m_PostPropertyAPIData));
        startActivity(new Intent(PostPropertyPage04Activity.this, PostPropertyPage02Activity.class));
    }

    /**
     * this is for post property 03 page (Property Specification)
     */
    @Override
    public void setPropertyBedRoomFeature(ArrayList<BedRoomInfo> bedRoomListData) {

    }

    /**
     * this is for post property 03 page (Property Specification)
     */
    @Override
    public void setPropertyAttributeFeature(ArrayList<AllAttributesData> allAttributesList) {

    }

    /**
     * this is for post property 03 page (Property Specification)
     */
    @Override
    public ArrayList<AllAttributesData> getAttrList() {
        return null;
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
