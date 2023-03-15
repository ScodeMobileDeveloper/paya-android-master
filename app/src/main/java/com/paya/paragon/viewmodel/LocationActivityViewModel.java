package com.paya.paragon.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.paya.paragon.api.BuyProjects.BuyProjectsListData;
import com.paya.paragon.api.BuyProjects.BuyProjectsModel;
import com.paya.paragon.api.buy_properties.BuyPropertiesListData;
import com.paya.paragon.api.buy_properties.BuyPropertiesModel;
import com.paya.paragon.api.PayaAPICall;
import com.paya.paragon.base.BaseViewModel;
import com.paya.paragon.utilities.AppConstant;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LocationActivityViewModel extends BaseViewModel implements PayaAPICall.PayaAPICallListener {

    public boolean loadMore = false;
    private LocationActivityCallBack callBack;
    private PayaAPICall payaAPICall;
    private int pageCount = 0;
    private String searchPurpose;
    private String userId,from;

    public LocationActivityViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public void init(LocationActivityCallBack callBack, Intent intent, String from) {
        this.callBack = callBack;
        payaAPICall = new PayaAPICall(this);
        searchPurpose = intent.getExtras().getString(AppConstant.I_SEARCH_PURPOSE);
        userId = intent.getExtras().getString(AppConstant.I_USER_ID);
        this.from = from;

    }

    public void onLoadMoreClick() {
        loadMore = true;
        pageCount = pageCount + 1;
        callBack.showLoader();

        if(from.equalsIgnoreCase("Properties")){
            payaAPICall.initiatePropertyListAPICall(callBack.getLocationActivityContext(), pageCount, searchPurpose, userId);
        }else {
            payaAPICall.initiateProjectListAPICall(callBack.getLocationActivityContext(),pageCount,searchPurpose,userId);
        }

    }

    @Override
    public void onSuccess(int apiTransactionCode, String response) {
        /**
         * 0-->Properties
         * 1-->Project
         */
        if (0 == apiTransactionCode) {
            propertiesDataResponse(response);
        } else {
            projectDataResponse(response);
        }
    }

    @Override
    public void onFailure(int apiTransactionCode, String message) {
        callBack.dismissLoader();
    }

    private void propertiesDataResponse(String response) {
        if (response != null && !response.isEmpty()) {
            BuyPropertiesListData propertiesListData = new Gson().fromJson(response, BuyPropertiesListData.class);
            if (propertiesListData != null && propertiesListData.getPropertyLists() != null && !propertiesListData.getPropertyLists().isEmpty()) {
                callBack.loadPropertyMapData(propertiesListData.getPropertyLists());
            }
        }
        callBack.dismissLoader();
    }

    private void projectDataResponse(String response) {
        if (response != null && !response.isEmpty()) {
            BuyProjectsListData projectsListData = new Gson().fromJson(response, BuyProjectsListData.class);
            if (projectsListData != null && projectsListData.getProjectLists() != null && !projectsListData.getProjectLists().isEmpty()) {
                callBack.loadProjectMapData(projectsListData.getProjectLists());
            }
        }
        callBack.dismissLoader();
    }

    public interface LocationActivityCallBack {
        void showLoader();

        void dismissLoader();

        Context getLocationActivityContext();

        void loadPropertyMapData(List<BuyPropertiesModel> propertyLists);

        void loadProjectMapData(List<BuyProjectsModel> projectLists);
    }
}
