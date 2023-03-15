package com.paya.paragon.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.paya.paragon.api.PayaAPICall;
import com.paya.paragon.api.cityList.CityListingApi;
import com.paya.paragon.api.cityList.CityListingResponse;
import com.paya.paragon.api.index.LocationInfo;
import com.paya.paragon.base.BaseViewModel;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.SessionManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitySelectionActivityViewModel extends BaseViewModel implements PayaAPICall.PayaAPICallListener {

    private CitySelectionActivityViewModelCallBack callBack;
    private ArrayList<LocationInfo> locationInfoList;
    private PayaAPICall payaAPICall;

    public CitySelectionActivityViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public void init(CitySelectionActivityViewModelCallBack callBack) {
        this.callBack = callBack;
        payaAPICall = new PayaAPICall(this);
        initGetCityListAPICAll();
    }

    private void initGetCityListAPICAll() {
        callBack.showLoader();
        locationInfoList = new ArrayList<>();
        ApiLinks.getClient().create(CityListingApi.class).post(SessionManager.getLanguageID(callBack.getActivityContext()))
                .enqueue(new Callback<CityListingResponse>() {
                    @Override
                    public void onResponse(Call<CityListingResponse> call, Response<CityListingResponse> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            String message = response.body().getResponse();
                            if (message != null && message.equalsIgnoreCase("Success")) {
                                locationInfoList = response.body().getData().getCityList();
                            }
                        }
                        callBack.dismissLoader();
                        callBack.updateLocationInfoList(locationInfoList);
                    }

                    @Override
                    public void onFailure(Call<CityListingResponse> call, Throwable t) {
                        callBack.dismissLoader();
                        callBack.updateLocationInfoList(locationInfoList);
                    }
                });
    }

    @Override
    public void onSuccess(int apiTransactionCode, String response) {

    }

    @Override
    public void onFailure(int apiTransactionCode, String message) {

    }

    public void initiateCityUpdationForNotificationCall(Context context) {
        if (!SessionManager.isCityUpdateNotificationUpdated(context)) {
            payaAPICall.initiateUpdateCityForUserNotification(context);
        }
    }


    public interface CitySelectionActivityViewModelCallBack {

        void showLoader();

        void dismissLoader();

        Context getActivityContext();

        void updateLocationInfoList(ArrayList<LocationInfo> locationInfoList);

        void onSaveOfLocationInfo();
    }
}
