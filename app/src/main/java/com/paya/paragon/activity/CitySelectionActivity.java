package com.paya.paragon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.paya.paragon.R;
import com.paya.paragon.PropertyProjectListActivity;
import com.paya.paragon.adapter.LocationListAdapter;
import com.paya.paragon.api.index.LocationInfo;
import com.paya.paragon.base.BaseViewModelActivity;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.databinding.ActivityLocationListBinding;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;
import com.paya.paragon.viewmodel.CitySelectionActivityViewModel;

import java.util.ArrayList;

public class CitySelectionActivity extends BaseViewModelActivity<CitySelectionActivityViewModel> implements
        CitySelectionActivityViewModel.CitySelectionActivityViewModelCallBack, LocationListAdapter.LocationListAdapterCallBack {

    private ActivityLocationListBinding binding;
    private LocationListAdapter adapter;
    private CitySelectionActivityViewModel viewModel;
    private ArrayList<LocationInfo> locationInfoList;
    private int previousPosition = -1;
    private int newPosition = -1;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_location_list);
        binding.setCallBack(this);
        Utils.changeLayoutOrientationDynamically(this, binding.getRoot());
        onCreateViewModel();
    }

    @Override
    public CitySelectionActivityViewModel onCreateViewModel() {
        viewModel = ViewModelProviders.of(this).get(CitySelectionActivityViewModel.class);
        viewModel.init(this);
        return viewModel;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, binding.clParent);
    }

    @Override
    public void showLoader() {
        showProgressBar(this);
    }

    @Override
    public void dismissLoader() {
        dismissProgressBar();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void updateLocationInfoList(ArrayList<LocationInfo> locationInfoList) {
        if (locationInfoList.isEmpty()) {
            binding.rvLocationList.setVisibility(View.GONE);
            binding.tvChooseLocation.setVisibility(View.GONE);
            binding.tvSave.setVisibility(View.GONE);
            binding.tvFailedMsg.setVisibility(View.VISIBLE);
        } else {
            binding.rvLocationList.setVisibility(View.VISIBLE);
            binding.tvChooseLocation.setVisibility(View.VISIBLE);
            binding.tvFailedMsg.setVisibility(View.GONE);
            updateAdapter(locationInfoList);
        }
    }

    private void updateAdapter(ArrayList<LocationInfo> locationInfoList) {
        this.locationInfoList = locationInfoList;
        if (adapter != null) {
            adapter.updateAdapterList(this.locationInfoList, previousPosition, newPosition);
        } else {
            adapter = new LocationListAdapter(locationInfoList, this);
            binding.rvLocationList.setLayoutManager(new LinearLayoutManager(this));
            binding.rvLocationList.setAdapter(adapter);
        }
    }

    @Override
    public void onSaveOfLocationInfo() {
        onLocationInfoSelection(locationInfoList.get(newPosition));
        GlobalValues.clearBuyGlobalValues();
        viewModel.initiateCityUpdationForNotificationCall(this);
        Intent in = new Intent(this, PropertyProjectListActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        in.putExtra("searchPropertyPurpose", "Sell");
        startActivity(in);
        finish();
    }

    @Override
    public void updateSelection(int newlySelectedPosition) {
        binding.tvSave.setVisibility(View.VISIBLE);
        previousPosition = newPosition != -1 ? newPosition : newlySelectedPosition;
        newPosition = newlySelectedPosition;
        locationInfoList.get(previousPosition).setLocationSelected(false);
        locationInfoList.get(newPosition).setLocationSelected(true);
        updateAdapter(locationInfoList);
    }

    @Override
    public void onLocationInfoSelection(LocationInfo selectedLocationInfo) {
        SessionManager.setLocationId(this, selectedLocationInfo.getCityID());
        SessionManager.setSelectedLocationId(this, selectedLocationInfo.getCityID());
        SessionManager.setLocationName(this, selectedLocationInfo.getCityName(), selectedLocationInfo.getCityLat(), selectedLocationInfo.getCityLng());
        SessionManager.setSelectedLocationName(this, selectedLocationInfo.getCityName(), selectedLocationInfo.getCityLat(), selectedLocationInfo.getCityLng());
    }
}
