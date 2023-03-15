package com.paya.paragon.activity.postProperty;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.paya.paragon.R;
import com.paya.paragon.adapter.AdapterCityListing;
import com.paya.paragon.adapter.AdapterNeighbourhood;
import com.paya.paragon.api.agentList.AgentDataListDATAItemObject;
import com.paya.paragon.api.bankListPropertyPost.BankListData;
import com.paya.paragon.api.bedRoomList.BedRoomInfo;
import com.paya.paragon.api.cityList.CityListingResponse;
import com.paya.paragon.api.index.LocationInfo;
import com.paya.paragon.api.listLocProject.ListLocProjectResponse;
import com.paya.paragon.api.listLocProject.RegionDataChild;
import com.paya.paragon.api.postProperty.attributeListing.AllAttributesData;
import com.paya.paragon.api.postProperty.loadGalleryImage.SavedImageInfo;
import com.paya.paragon.api.postProperty.loadVideo.SavedVideoInfo;
import com.paya.paragon.api.postProperty.postPropertyIndex.PostPropertyIndexData;
import com.paya.paragon.api.postProperty.randomID.UserPlanInfo;
import com.paya.paragon.api.propertyDetails.AmenitiesModel;
import com.paya.paragon.base.BaseViewModelActivity;
import com.paya.paragon.databinding.ActivityPostPropertyLocationSelectionBinding;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.viewmodel.PostPropertyViewModel;

import java.util.ArrayList;
import java.util.List;

public class PostPropertyLocationSelection extends BaseViewModelActivity<PostPropertyViewModel> implements PostPropertyViewModel.PostPropertyViewModelCallBack {
    ActivityPostPropertyLocationSelectionBinding mLocationSelectingBinding;
    private PostPropertyViewModel viewModel;

    ArrayList<LocationInfo> locationInfo = new ArrayList<>();
    AdapterCityListing adapterCityListing;
    AdapterNeighbourhood mAdapterNeighbourhood;
    List<RegionDataChild> mNBHList = new ArrayList<>();
    List<RegionDataChild> mSearchNeighbourhoodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationSelectingBinding = DataBindingUtil.setContentView(this, R.layout.activity_post_property_location_selection);
        mLocationSelectingBinding.setActivity(this);
        mLocationSelectingBinding.setViewModel(onCreateViewModel());
        initiateToolBar();
        declarations();

    }

    private void initiateToolBar() {
        setSupportActionBar(mLocationSelectingBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow_black);
        mLocationSelectingBinding.toolbarTitle.setText(R.string.location);
        mLocationSelectingBinding.toolbarTitle.setTextSize(16);
    }

    @Override
    public PostPropertyViewModel onCreateViewModel() {
        viewModel = ViewModelProviders.of(this).get(PostPropertyViewModel.class);
        viewModel.initiatePostProperty(this);
        viewModel.getLocationSelectionFieldValues();
        return viewModel;
    }

    private void declarations() {

        final Dialog dialog = new Dialog(this);
        @SuppressLint("InflateParams") final View dialogView = getLayoutInflater().inflate(R.layout.show_price_list_selector, null);
        final SearchView searchView = dialogView.findViewById(R.id.sv_list_search);
        searchView.setVisibility(View.VISIBLE);
        final RecyclerView recyclerMinPrice = (RecyclerView) dialogView.findViewById(R.id.recycler_price_list);
        recyclerMinPrice.setLayoutManager(new LinearLayoutManager(PostPropertyLocationSelection.this));
        adapterCityListing = new AdapterCityListing(locationInfo, PostPropertyLocationSelection.this, SessionManager.getLocationId(this));
        recyclerMinPrice.setAdapter(adapterCityListing);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return false;
            }
        });
        mLocationSelectingBinding.textCityList.setOnClickListener(view -> dialog.show());

        adapterCityListing.setMinPriceInterface(locationInfo -> {
//                PostPropertyPage01Activity.m_PostPropertyAPIData.setGoogleCityName(locationInfo.getCityName());
            mLocationSelectingBinding.textCityList.setText(locationInfo.getCityName());
            viewModel.getNeighbourhoodValues(locationInfo.getCityID(), "");
            dialog.dismiss();
        });


        mAdapterNeighbourhood = new AdapterNeighbourhood();
        mLocationSelectingBinding.neighbourwoodRcv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mLocationSelectingBinding.neighbourwoodRcv.setHasFixedSize(true);
        mLocationSelectingBinding.neighbourwoodRcv.setAdapter(mAdapterNeighbourhood);

        mLocationSelectingBinding.searchNbh.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                NbhSearchList(newText);
                return false;
            }
        });

    }

    private void NbhSearchList(String newText) {
        mSearchNeighbourhoodList = new ArrayList<>();

        for (RegionDataChild info : mNBHList) {
            if (info.getOriginalText().toLowerCase().contains(newText.toLowerCase())) {
                mSearchNeighbourhoodList.add(info);
            }
        }
        if (!mSearchNeighbourhoodList.isEmpty()) {
            mAdapterNeighbourhood.updateNeighbourhood(mSearchNeighbourhoodList);

        }
    }

    public void searchList(String newText) {
        ArrayList<LocationInfo> searchedLocatinInfo = new ArrayList<>();

        for (LocationInfo info : locationInfo) {
            if (info.getCityName().toLowerCase().contains(newText.toLowerCase())) {
                searchedLocatinInfo.add(info);
            }
        }
        if (!searchedLocatinInfo.isEmpty()) {
            adapterCityListing.searchedLocationList(searchedLocatinInfo);

        }

    }

    @Override
    public void showLoader() {

    }

    @Override
    public void dismissLoader() {

    }

    @Override
    public void noInternetMessage() {

    }

    @Override
    public void onPropertyTypeSpinnerClick() {

    }

    @Override
    public void setPropertyBasedOnSellAndRentType(boolean isPropertyForSell) {

    }

    @Override
    public void setPriceperMTotalPriceUncheck(boolean isCurrencyTypeChanged) {

    }

    @Override
    public Activity getPostPropertyContext() {
        return PostPropertyLocationSelection.this;
    }

    @Override
    public void setPropertyTypeSpinner(PostPropertyIndexData postPropertyIndexData) {

    }

    @Override
    public void onMotagagedClicked(boolean isMotagagedYes) {

    }

    @Override
    public void initiateBankList(ArrayList<BankListData> bankListData) {

    }

    @Override
    public void onNextClick() {

    }

    @Override
    public void showToastMessage(int messageId, String message) {

    }

    @Override
    public void setPropertyBedRoomFeature(ArrayList<BedRoomInfo> bedRoomListData) {

    }

    @Override
    public void setPropertyAttributeFeature(ArrayList<AllAttributesData> allAttributesList) {

    }

    @Override
    public ArrayList<AllAttributesData> getAttrList() {
        return null;
    }

    @Override
    public void updateAmenitiesInAdapter(ArrayList<AmenitiesModel> amenities) {

    }

    @Override
    public void initiateGalleryIntent() {

    }

    @Override
    public void updateSavedImagesViews(ArrayList<SavedImageInfo> savedImageInfoArrayList, String imgUrl) {

    }

    @Override
    public void updateBlurPrintBitMap(Bitmap bitmap, String bluePrintImageUrl) {

    }

    @Override
    public void updateSavedVideoUrl(ArrayList<SavedVideoInfo> savedVideoInfoArrayList) {

    }

    @Override
    public void setPlanSelector(boolean isPlanAvailable, ArrayList<UserPlanInfo> userPlanList) {

    }

    @Override
    public void onSubmitPostPropertySuccessfully(String propertyID) {

    }

    @Override
    public void updatePropertyCurrentStatus(String propertyCurrentStatus) {

    }

    @Override
    public void updateEditPropertyFields() {

    }

    @Override
    public void initiateSubAgentListSpinner(List<AgentDataListDATAItemObject> subAgentSpinnerData) {

    }

    @Override
    public void onPropertyPostBySpinnerClick() {

    }

    @Override
    public void updateDescription(String propertyDescription) {

    }

    @Override
    public void updateCity(CityListingResponse cityListingResponse) {
        locationInfo.addAll(cityListingResponse.getData().getCityList());
    }

    @Override
    public void attachNBHRcvItems(ListLocProjectResponse listLocProjectResponse) {
        mAdapterNeighbourhood.updateNeighbourhood(listLocProjectResponse.getData().getLocation());
        this.mNBHList = listLocProjectResponse.getData().getLocation();
    }


}