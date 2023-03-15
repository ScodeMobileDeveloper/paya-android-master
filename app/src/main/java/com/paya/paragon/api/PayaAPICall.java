package com.paya.paragon.api;

import android.content.Context;

import com.google.gson.Gson;
import com.paya.paragon.PayaAppClass;
import com.paya.paragon.api.BuyProjects.BuyProjectsListApi;
import com.paya.paragon.api.BuyProjects.BuyProjectsListData;
import com.paya.paragon.api.BuyProjects.BuyProjectsListResponse;
import com.paya.paragon.api.buy_properties.BuyPropertiesListApi;
import com.paya.paragon.api.buy_properties.BuyPropertiesListData;
import com.paya.paragon.api.buy_properties.BuyPropertiesListResponse;
import com.paya.paragon.api.agentList.AgentDataListAPI;
import com.paya.paragon.api.agentList.AgentDataListResponse;
import com.paya.paragon.api.agentList.SubAgentDataListAPI;
import com.paya.paragon.api.newAgent.NewAgentDetailsModel;
import com.paya.paragon.api.newAgent.NewAgentDetailsResponseModel;
import com.paya.paragon.api.newAgent.NewAgentPropertyList;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.AppConstant;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;
import com.paya.paragon.viewmodel.AgentListFragmentViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayaAPICall {

    private PayaAPICallListener listener;

    public PayaAPICall(PayaAPICallListener listener) {
        this.listener = listener;
    }

    public PayaAPICall() {
    }

    public void initiateUpdateCityForUserNotification(Context context) {
        if (Utils.NoInternetConnection(context)) {
            SessionManager.setCityUpdationForNotification(context, false);
        } else {
            ApiLinks.getClient().create(CityUpdateNotification.class)
                    .post(SessionManager.getLanguageID(context), SessionManager.getDeviceTokenForFCM(context),
                            AppConstant.ANDROID, SessionManager.getLocationId(context))
                    .enqueue(new Callback<StandardResponse>() {
                        @Override
                         public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                SessionManager.setCityUpdationForNotification(context, 1 == response.body().getCode());
                            } else {
                                SessionManager.setCityUpdationForNotification(context, false);
                            }
                        }

                        @Override
                        public void onFailure(Call<StandardResponse> call, Throwable t) {
                            SessionManager.setCityUpdationForNotification(context, false);
                        }
                    });
        }
    }

    public void initiateAgentListCall(int fileStartIndex, int responseReturnSize, String orderBy, String searchKeyWords, int apiHitType) {
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
            if (AgentListFragmentViewModel.m_AGENT_LIST_FIRST_HIT == apiHitType) {
                listener.onFailure(AppConstant.NETWORK_FAILURE, "");
            } else {
                listener.onFailure(apiHitType, "");
            }
        } else {
            ApiLinks.getClient().create(AgentDataListAPI.class)
                    .post(String.valueOf(fileStartIndex), String.valueOf(responseReturnSize), orderBy, searchKeyWords, SessionManager.getLanguageID(PayaAppClass.getAppInstance()))
                    .enqueue(new Callback<AgentDataListResponse>() {
                        @Override
                        public void onResponse(Call<AgentDataListResponse> call, Response<AgentDataListResponse> response) {
                            if (response.isSuccessful() && response.body() != null && AppConstant.API_SUCCESS.equalsIgnoreCase(response.body().getResponse())) {
                                listener.onSuccess(apiHitType, new Gson().toJson(response.body().getData()));
                            } else {
                                listener.onFailure(apiHitType, "");
                            }
                        }

                        @Override
                        public void onFailure(Call<AgentDataListResponse> call, Throwable t) {
                            listener.onFailure(apiHitType, "");
                        }
                    });
        }

    }

    public void initiateAgentDetailsAPICall(String agentId, int apiTransactionID) {
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
            listener.onFailure(AppConstant.NETWORK_FAILURE, "");
        } else {
            ApiLinks.getClient().create(NewAgentDetailsModel.class)
                    .post(agentId, SessionManager.getLanguageID(PayaAppClass.getAppInstance()))
                    .enqueue(new Callback<NewAgentDetailsResponseModel>() {
                        @Override
                        public void onResponse(Call<NewAgentDetailsResponseModel> call, Response<NewAgentDetailsResponseModel> response) {
                            if (response.isSuccessful() && response.body() != null && AppConstant.API_SUCCESS.equalsIgnoreCase(response.body().getResponse())) {
                                listener.onSuccess(apiTransactionID, new Gson().toJson(response.body().getData()));
                            } else {
                                listener.onFailure(apiTransactionID, "");
                            }
                        }

                        @Override
                        public void onFailure(Call<NewAgentDetailsResponseModel> call, Throwable t) {
                            listener.onFailure(apiTransactionID, "");
                        }
                    });
        }
    }

    public void initiateAgentSubAgentListAPICall(int fileStartIndex, int responseReturnSize, String orderBy, String searchKeyWords, String agentId, int apiTransactionID) {
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
            if (AgentListFragmentViewModel.m_AGENT_LIST_FIRST_HIT == apiTransactionID) {
                listener.onFailure(AppConstant.NETWORK_FAILURE, "");
            } else {
                listener.onFailure(apiTransactionID, "");
            }
        } else {
            ApiLinks.getClient().create(SubAgentDataListAPI.class)
                    .post(String.valueOf(fileStartIndex), String.valueOf(responseReturnSize), orderBy, searchKeyWords, SessionManager.getLanguageID(PayaAppClass.getAppInstance()), agentId)
                    .enqueue(new Callback<AgentDataListResponse>() {
                        @Override
                        public void onResponse(Call<AgentDataListResponse> call, Response<AgentDataListResponse> response) {
                            if (response.isSuccessful() && response.body() != null && AppConstant.API_SUCCESS.equalsIgnoreCase(response.body().getResponse())) {
                                listener.onSuccess(apiTransactionID, new Gson().toJson(response.body().getData()));
                            } else {
                                listener.onFailure(apiTransactionID, "");
                            }
                        }

                        @Override
                        public void onFailure(Call<AgentDataListResponse> call, Throwable t) {
                            listener.onFailure(apiTransactionID, "");
                        }
                    });
        }

    }

    public void initiateAgentPropertyListAPICall(String agentId, int apiTransactionID,int limitFrom,int limitTo) {
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
            listener.onFailure(apiTransactionID, "");
        } else {
            ApiLinks.getClient().create(NewAgentPropertyList.class)
                    .post(agentId, SessionManager.getLanguageID(PayaAppClass.getAppInstance()),String.valueOf(limitFrom),String.valueOf(limitTo))
                    .enqueue(new Callback<NewAgentDetailsResponseModel>() {
                        @Override
                        public void onResponse(Call<NewAgentDetailsResponseModel> call, Response<NewAgentDetailsResponseModel> response) {
                            if (response.isSuccessful() && response.body() != null && AppConstant.API_SUCCESS.equalsIgnoreCase(response.body().getResponse())) {
                                listener.onSuccess(apiTransactionID, new Gson().toJson(response.body().getData()));
                            } else {
                                listener.onFailure(apiTransactionID, "");
                            }
                        }

                        @Override
                        public void onFailure(Call<NewAgentDetailsResponseModel> call, Throwable t) {
                            listener.onFailure(apiTransactionID, "");
                        }
                    });
        }
    }

    public void initiatePropertyListAPICall(Context context, int pageCount, String searchPurpose, String userId) {
        ApiLinks.getClient().create(BuyPropertiesListApi.class).post(userId, SessionManager.getLanguageID(context), SessionManager.getLocationId(context),
                searchPurpose, pageCount + "", "Property", GlobalValues.selectedSortValue, GlobalValues.selectedMinPrice, GlobalValues.selectedMaxPrice,
                GlobalValues.selectedPropertyTypeID, GlobalValues.countryID, GlobalValues.selectedBedroomsNumber,GlobalValues.selectedFurnishedStaticValue, GlobalValues.selectedRegionId,
                "", "", "", "", "").enqueue(new Callback<BuyPropertiesListResponse>() {
            @Override
            public void onResponse(Call<BuyPropertiesListResponse> call, Response<BuyPropertiesListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String status = response.body().getResponse();
                    if (AppConstant.API_SUCCESS.equalsIgnoreCase(status)) {
                        BuyPropertiesListData data = response.body().getData();
                        listener.onSuccess(0, new Gson().toJson(data));
                    }
                } else {
                    listener.onFailure(0, "");
                }
            }

            @Override
            public void onFailure(Call<BuyPropertiesListResponse> call, Throwable t) {
                listener.onFailure(0, "");
            }
        });
    }

    public void initiateProjectListAPICall(Context context, int pageCount, String searchPurpose, String userId) {
        ApiLinks.getClient().create(BuyProjectsListApi.class).post(userId, SessionManager.getLanguageID(context), SessionManager.getLocationId(context),
                searchPurpose, pageCount + "", "Project", GlobalValues.selectedSortValue, GlobalValues.selectedMinPrice, GlobalValues.selectedMaxPrice,
                GlobalValues.selectedPropertyTypeID, GlobalValues.countryID, GlobalValues.selectedBedroomsNumber,GlobalValues.selectedFurnishedStaticValue, GlobalValues.selectedRegionId,
                "", "", "", "", "").enqueue(new Callback<BuyProjectsListResponse>() {
            @Override
            public void onResponse(Call<BuyProjectsListResponse> call, Response<BuyProjectsListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String status = response.body().getResponse();
                    if (AppConstant.API_SUCCESS.equalsIgnoreCase(status)) {
                        BuyProjectsListData data = response.body().getData();
                        listener.onSuccess(1, new Gson().toJson(data));
                    }
                } else {
                    listener.onFailure(0, "");
                }
            }

            @Override
            public void onFailure(Call<BuyProjectsListResponse> call, Throwable t) {
                listener.onFailure(0, "");
            }
        });
    }

    public interface PayaAPICallListener {
        void onSuccess(int apiTransactionCode, String response);

        void onFailure(int apiTransactionCode, String message);
    }
}
