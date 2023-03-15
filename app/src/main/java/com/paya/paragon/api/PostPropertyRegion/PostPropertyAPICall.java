package com.paya.paragon.api.PostPropertyRegion;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.paya.paragon.BuildConfig;
import com.paya.paragon.PayaAppClass;
import com.paya.paragon.R;
import com.paya.paragon.activity.postProperty.PostPropertyPage01Activity;
import com.paya.paragon.activity.postProperty.PostPropertyPage05Activity;
import com.paya.paragon.api.StandardResponse;
import com.paya.paragon.api.bankListPropertyPost.BankListPropertyApi;
import com.paya.paragon.api.bankListPropertyPost.BankListPropertyResponse;
import com.paya.paragon.api.cityList.CityListingApi;
import com.paya.paragon.api.cityList.CityListingResponse;
import com.paya.paragon.api.editPostProperty.EditPostedPropertyApi;
import com.paya.paragon.api.editPostProperty.EditPostedPropertyResponse;
import com.paya.paragon.api.listLocProject.ListLocProjApi;
import com.paya.paragon.api.listLocProject.ListLocProjectResponse;
import com.paya.paragon.api.postProperty.attributeListing.AttributeListingApi;
import com.paya.paragon.api.postProperty.attributeListing.AttributeListingResponse;
import com.paya.paragon.api.postProperty.deleteGalleryImage.DeleteSavedImageApi;
import com.paya.paragon.api.postProperty.deleteGalleryImage.DeleteSavedImageResponse;
import com.paya.paragon.api.postProperty.deleteSavedVideo.DeleteSavedVideoApi;
import com.paya.paragon.api.postProperty.deleteSavedVideo.DeleteSavedVideoResponse;
import com.paya.paragon.api.postProperty.loadGalleryImage.LoadSavedImageApi;
import com.paya.paragon.api.postProperty.loadGalleryImage.LoadSavedImageResponse;
import com.paya.paragon.api.postProperty.loadVideo.LoadSavedVideoApi;
import com.paya.paragon.api.postProperty.loadVideo.LoadSavedVideoResponse;
import com.paya.paragon.api.postProperty.post.PostPropertyApi;
import com.paya.paragon.api.postProperty.post.PostPropertyApiDataClass;
import com.paya.paragon.api.postProperty.post.PostPropertyResponse;
import com.paya.paragon.api.postProperty.postEdited.PostEditedPropertyApi;
import com.paya.paragon.api.postProperty.postEdited.PostEditedPropertyResponse;
import com.paya.paragon.api.postProperty.postPropertyIndex.PostPropertyIndexApi;
import com.paya.paragon.api.postProperty.postPropertyIndex.PostPropertyIndexResponse;
import com.paya.paragon.api.postProperty.randomID.RandomIdApi;
import com.paya.paragon.api.postProperty.randomID.RandomIdResponse;
import com.paya.paragon.api.postProperty.saveEditedVideoInfo.SaveEditedVideoInfoApi;
import com.paya.paragon.api.postProperty.saveEditedVideoInfo.SaveEditedVideoInfoResponse;
import com.paya.paragon.api.postProperty.uploadVideoUrl.UploadVideoUrlApi;
import com.paya.paragon.api.postProperty.uploadVideoUrl.UploadVideoUrlResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.FilePost;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.base.commonClass.PairedValues;
import com.paya.paragon.utilities.AppConstant;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;
import com.paya.paragon.viewmodel.PostPropertyViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostPropertyAPICall {
    private PostPropertyViewModel viewModel;
    private PostPropertyViewModel.PostPropertyViewModelCallBack callBack;

    public PostPropertyAPICall(PostPropertyViewModel viewModel, PostPropertyViewModel.PostPropertyViewModelCallBack callBack) {
        this.viewModel = viewModel;
        this.callBack = callBack;
    }

    public void checkUserPostPropertyLimit() {
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
            viewModel.onAPIFailure("", AppConstant.NETWORK_FAILURE);
        } else {
            callBack.showLoader();
            ApiLinks.getClient().create(RandomIdApi.class)
                    .checkLimitReached(SessionManager.getAccessToken(callBack.getPostPropertyContext()))
                    .enqueue(new Callback<StandardResponse>() {
                        @Override
                        public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                            if (response.isSuccessful()) {
                                String message = response.body().getMessage();
                                String status = response.body().getResponse();
                                if (!status.equalsIgnoreCase(AppConstant.API_SUCCESS)) {
                                    callBack.dismissLoader();
                                    Utils.showAlertErrorMessage(callBack.getPostPropertyContext(), message);
                                } else {
                                    viewModel.onAPISuccess("", AppConstant.GET_PP_CHECK_USER_LIMIT);
                                }
                            } else {
                                callBack.dismissLoader();
                                Utils.showAlertErrorMessage(callBack.getPostPropertyContext(), "");
                            }
                        }

                        @Override
                        public void onFailure(Call<StandardResponse> call, Throwable t) {
                            callBack.dismissLoader();
                            Utils.showAlertErrorMessage(callBack.getPostPropertyContext(), "");
                        }
                    });
        }
    }

    public void getRandomID() {
        Context context = callBack.getPostPropertyContext();
        ApiLinks.getClient().create(RandomIdApi.class)
                .post(SessionManager.getLanguageID(context), "List",
                        SessionManager.getAccessToken(context))
                .enqueue(new Callback<RandomIdResponse>() {
                    @Override
                    public void onResponse(Call<RandomIdResponse> call, Response<RandomIdResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String status = response.body().getResponse();
                            if (status.equalsIgnoreCase(AppConstant.API_SUCCESS)) {
                                viewModel.onAPISuccess(new Gson().toJson(response.body().getData()), AppConstant.GET_PP_RANDOM_ID);
                            } else if (response.body().getCode() != null && response.body().getCode() == 409) {
                                callBack.dismissLoader();
                                Utils.showAlertErrorMessage(callBack.getPostPropertyContext(), message);
                            } else {
                                callBack.dismissLoader();
                                Utils.showAlertErrorMessage(callBack.getPostPropertyContext(), "");
                            }
                        } else {
                            callBack.dismissLoader();
                            Utils.showAlertErrorMessage(callBack.getPostPropertyContext(), "");
                        }
                    }

                    @Override
                    public void onFailure(Call<RandomIdResponse> call, Throwable t) {
                        callBack.dismissLoader();
                        Utils.showAlertErrorMessage(callBack.getPostPropertyContext(), "");
                    }
                });
    }

    public void getEditPropertyDetails(String m_PostPropertyId) {
        callBack.showLoader();
        ApiLinks.getClient().create(EditPostedPropertyApi.class)
                .post(SessionManager.getLanguageID(callBack.getPostPropertyContext()), m_PostPropertyId,
                        SessionManager.getAccessToken(callBack.getPostPropertyContext()))
                .enqueue(new Callback<EditPostedPropertyResponse>() {
                    @Override
                    public void onResponse(Call<EditPostedPropertyResponse> call,
                                           Response<EditPostedPropertyResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String status = response.body().getResponse();
                            int code = response.body().getCode();
                            if (code == 4004 && status.equalsIgnoreCase("Success")) {
                                viewModel.onAPISuccess(new Gson().toJson(response.body()), AppConstant.PP_GET_EDIT_PROPERTY_DETAILS);
                            } else {
                                callBack.dismissLoader();
                                callBack.showToastMessage(0, message);
                            }

                            /*if (code == 4004 && status.equalsIgnoreCase("Success")) {
                                getPropertyIndexList();
                                propertyDetails = response.body().getData().getPropertyDetails();
                                savedVideos = response.body().getData().getVideos();
                                galleryImages = response.body().getData().getGallery();
                                imagePath = response.body().getData().getImagePath();
                                bluePrintimagePath = response.body().getData().getBluePrintimagePath();
                                setPropertyDetailsToUI(propertyDetails);
                                selectedPropertyTypeID = propertyDetails.getPropertyTypeID();
                            } else {
                                Toast.makeText(ActivityPostYourProperty.this, message, Toast.LENGTH_SHORT).show();
                                mLoading.dismiss();
                            }*/
                        } else {
                            callBack.showToastMessage(R.string.no_response, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<EditPostedPropertyResponse> call, Throwable t) {
                        callBack.showToastMessage(R.string.no_response, null);
                        callBack.dismissLoader();
                    }
                });
    }

    public void getPropertyAndProjectTypes() {
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
            viewModel.onAPIFailure("", AppConstant.NETWORK_FAILURE);
        } else {
            Context context = callBack.getPostPropertyContext();
            ApiLinks.getClient().create(PostPropertyIndexApi.class)
                    .post(SessionManager.getLanguageID(context))
                    .enqueue(new Callback<PostPropertyIndexResponse>() {
                        @Override
                        public void onResponse(Call<PostPropertyIndexResponse> call,
                                               Response<PostPropertyIndexResponse> response) {
                            if (response.isSuccessful()) {
                                String message = response.body().getMessage();
                                String status = response.body().getResponse();
                                int code = response.body().getCode();
                                if (code == 100 && status.equalsIgnoreCase(AppConstant.API_SUCCESS)) {
                                    viewModel.onAPISuccess(new Gson().toJson(response.body().getData()), AppConstant.GET_PP_PROPERTY_PROJECT_TYPES);
                                } else {
                                    callBack.dismissLoader();
                                    Utils.showAlertErrorMessage(callBack.getPostPropertyContext(), "");
                                }
                            } else {
                                callBack.dismissLoader();
                                Utils.showAlertErrorMessage(callBack.getPostPropertyContext(), "");
                            }
                        }

                        @Override
                        public void onFailure(Call<PostPropertyIndexResponse> call, Throwable t) {
                            callBack.dismissLoader();
                            Utils.showAlertErrorMessage(callBack.getPostPropertyContext(), "");
                        }
                    });
        }
    }

    public void getBankListAPI() {
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
            viewModel.onAPIFailure("", AppConstant.NETWORK_FAILURE);
        } else {
            callBack.showLoader();
            ApiLinks.getClient().create(BankListPropertyApi.class).post(SessionManager.getLanguageID(callBack.getPostPropertyContext()))
                    .enqueue(new Callback<BankListPropertyResponse>() {
                        @Override
                        public void onResponse(Call<BankListPropertyResponse> call, Response<BankListPropertyResponse> response) {
                            if (response.isSuccessful()) {
                                int code = response.body().getCode();
                                String status = response.body().getResponse();
                                String message = response.body().getMessage();
                                callBack.dismissLoader();
                                if (status.equalsIgnoreCase(AppConstant.API_SUCCESS)) {
                                    viewModel.onAPISuccess(new Gson().toJson(response.body()), AppConstant.GET_PP_BANK_LIST);
                                } else {
                                    viewModel.onAPIFailure("", AppConstant.GET_PP_BANK_LIST);
                                }
                            } else {
                                callBack.dismissLoader();
                                viewModel.onAPIFailure("", AppConstant.GET_PP_BANK_LIST);
                            }
                        }

                        @Override
                        public void onFailure(Call<BankListPropertyResponse> call, Throwable t) {
                            callBack.dismissLoader();
                            viewModel.onAPIFailure("", AppConstant.GET_PP_BANK_LIST);
                        }
                    });
        }
    }

    public void getAttributeAmenitiesListAPI() {
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
            viewModel.onAPIFailure("", AppConstant.NETWORK_FAILURE);
        } else {
            callBack.showLoader();
            Log.d("specifications",SessionManager.getLanguageID(callBack.getPostPropertyContext())+" "+PostPropertyPage01Activity.m_PostPropertyAPIData.getPropertyTypeID()+" "+
                    PostPropertyPage01Activity.m_PostPropertyId);

            ApiLinks.getClient().create(AttributeListingApi.class)
                    .post(SessionManager.getLanguageID(callBack.getPostPropertyContext()), PostPropertyPage01Activity.m_PostPropertyAPIData.getPropertyTypeID(),
                            PostPropertyPage01Activity.m_PostPropertyId)
                    .enqueue(new Callback<AttributeListingResponse>() {
                        @Override
                        public void onResponse(Call<AttributeListingResponse> call,
                                               Response<AttributeListingResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                String status = response.body().getResponse();
                                int code = response.body().getCode();
                                if (code == 4004 && status.equals(AppConstant.API_SUCCESS)) {
                                    viewModel.onAPISuccess(new Gson().toJson(response.body()), AppConstant.GET_PP_ATTR_AMN_LIST);
                                } else {
                                    Utils.showAlertErrorMessage(callBack.getPostPropertyContext(), "");
                                }
                                callBack.dismissLoader();
                            } else {
                                callBack.dismissLoader();
                                Utils.showAlertErrorMessage(callBack.getPostPropertyContext(), "");
                            }
                        }

                        @Override
                        public void onFailure(Call<AttributeListingResponse> call, Throwable t) {
                            callBack.dismissLoader();
                            Utils.showAlertErrorMessage(callBack.getPostPropertyContext(), "");
                        }
                    });
        }
    }

    public void uploadImageFiles(ArrayList<PairedValues> values, int apiCallId, String bitmap, PostPropertyPage05Activity context) {
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
            viewModel.onAPIFailure("", AppConstant.NETWORK_FAILURE);
        } else {
//                callBack.showLoader();


                FilePost filePost = new FilePost(BuildConfig.APP_BASE_URL + (AppConstant.UPLOAD_BLUE_PRINT == apiCallId ? "property/savePropertyBluePrintImage/" : "dashboard/saveGalleryMultiImages"), values,context);
            filePost.start(new FilePost.OnPostEntityListener() {
                @Override
                public void onCompleted(JSONObject jsonObject) {
                    try {
                        String status = jsonObject.getString("response");
                        Integer code = jsonObject.getInt("code");
                        String message = jsonObject.getString("message");

                        if (status.equalsIgnoreCase("Success")) {
                            if (bitmap != null) {
//                                callBack.dismissLoader();
                            }
                            Log.d("uploadImage",status);

                            viewModel.onAPISuccess(bitmap != null ? jsonObject.toString() + AppConstant.BLUEPRINTNAME_SEPERATION_SYMBOL + bitmap : jsonObject.toString(), apiCallId);
                        } else {
                            //callBack.dismissLoader();
                            Log.d("uploadImage",status);

                            viewModel.onAPIFailure(message, apiCallId);
                        }
                    } catch (JSONException e) {
//                            callBack.dismissLoader();


                        e.printStackTrace();
                        Log.d("uploadImage",e.getMessage());

                    }
                }

                @Override
                public void onError(String error) {
                    //callBack.dismissLoader();
                    viewModel.onAPIFailure(error, apiCallId);


                }
            });

           /* filePost.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/

        }
    }

    public void deleteGalleryImageFromServer(String propertyImageID, String propertyID) {
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
            viewModel.onAPIFailure("", AppConstant.NETWORK_FAILURE);
        } else {
            callBack.showLoader();
             ApiLinks.getClient().create(DeleteSavedImageApi.class).post(propertyImageID)
                    .enqueue(new Callback<DeleteSavedImageResponse>() {
                        @Override
                        public void onResponse(Call<DeleteSavedImageResponse> call,
                                               Response<DeleteSavedImageResponse> response) {
                            if (response.isSuccessful()) {
                                String message = response.body().getMessage();
                                String status = response.body().getResponse();
                                int code = response.body().getCode();
                                if (code == 100 && status.equals("Success")) {


                                   /* if(PostPropertyViewModel.selectedImageCount !=0){
                                        PostPropertyViewModel.selectedImageCount--;
                                    }*/
                                    initiateGetSavedImagesList(propertyID);
                                } else {
                                    viewModel.onAPISuccess(null, AppConstant.PP_GET_ALL_SAVED_IMAGE);
                                }
                            } else {
                                callBack.showToastMessage(R.string.no_response, null);
                            }
                            callBack.dismissLoader();
                        }

                        @Override
                        public void onFailure(Call<DeleteSavedImageResponse> call, Throwable t) {
                            callBack.showToastMessage(R.string.no_response, null);
                            callBack.dismissLoader();
                        }
                    });
        }
    }

    public void initiateGetSavedImagesList(String propertyID) {
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
            viewModel.onAPIFailure("", AppConstant.NETWORK_FAILURE);
        } else {
            callBack.showLoader();
            ApiLinks.getClient().create(LoadSavedImageApi.class).post(propertyID, AppConstant.IMAGE_CAT_ID)
                    .enqueue(new Callback<LoadSavedImageResponse>() {
                        @Override
                        public void onResponse(Call<LoadSavedImageResponse> call,
                                               Response<LoadSavedImageResponse> response) {
                            if (response.isSuccessful()) {
                                String message = response.body().getMessage();
                                String status = response.body().getResponse();
                                int code = response.body().getCode();
                                if (code == 100 && status.equals("Success")) {
                                    viewModel.onAPISuccess(new Gson().toJson(response.body()), AppConstant.PP_GET_ALL_SAVED_IMAGE);

                                   /* if(PostPropertyViewModel.selectedImageCount==response.body().getData().size()){

                                    }else {

                                        initiateGetSavedImagesList(propertyID);
                                    }*/


                                } else {

                                    viewModel.onAPISuccess(null, AppConstant.PP_GET_ALL_SAVED_IMAGE);
                                }
                            } else {

                                callBack.showToastMessage(R.string.no_response, null);

                            }
                            callBack.dismissLoader();

                        }

                        @Override
                        public void onFailure(Call<LoadSavedImageResponse> call, Throwable t) {
                            callBack.showToastMessage(R.string.no_response, null);
                            callBack.dismissLoader();
                        }
                    });
        }
    }


    //TODO Videos
    public void uploadPropertyVideo(String videoUrl, String propertyID) {
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
            viewModel.onAPIFailure("", AppConstant.NETWORK_FAILURE);
        } else {
            callBack.showLoader();
            ApiLinks.getClient().create(UploadVideoUrlApi.class)
                    .post(propertyID,
                            "Active",
                            "youtube",
                            videoUrl)
                    .enqueue(new Callback<UploadVideoUrlResponse>() {
                        @Override
                        public void onResponse(Call<UploadVideoUrlResponse> call,
                                               Response<UploadVideoUrlResponse> response) {
                            if (response.isSuccessful()) {
                                String message = response.body().getMessage();
                                String status = response.body().getResponse();
                                int code = response.body().getCode();
                                if (code == 100 && status.equals("Success")) {
                                    callBack.showToastMessage(R.string.video_uploaded_successfully, null);
                                    loadSavedVideoFromServer(propertyID);
                                } else {
                                    callBack.dismissLoader();
                                    callBack.showToastMessage(R.string.no_response, message);
                                }
                            } else {
                                callBack.dismissLoader();
                                callBack.showToastMessage(R.string.no_response, null);
                            }
                        }

                        @Override
                        public void onFailure(Call<UploadVideoUrlResponse> call, Throwable t) {
                            callBack.dismissLoader();
                            callBack.showToastMessage(R.string.no_response, null);
                        }
                    });
        }
    }

    public void loadSavedVideoFromServer(String propertyID) {
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
            viewModel.onAPIFailure("", AppConstant.NETWORK_FAILURE);
        } else {
            ApiLinks.getClient().create(LoadSavedVideoApi.class).post(propertyID)
                    .enqueue(new Callback<LoadSavedVideoResponse>() {
                        @Override
                        public void onResponse(Call<LoadSavedVideoResponse> call,
                                               Response<LoadSavedVideoResponse> response) {
                            if (response.isSuccessful()) {
                                String message = response.body().getMessage();
                                String status = response.body().getResponse();
                                int code = response.body().getCode();
                                if (code == 100 && status.equals("Success")) {
                                    viewModel.onAPISuccess(new Gson().toJson(response.body()), AppConstant.GET_PP_SAVED_VIDEO);
                                } else {
                                    viewModel.onAPISuccess(null, AppConstant.GET_PP_SAVED_VIDEO);
                                }
                            } else {
                                callBack.showToastMessage(R.string.no_response, null);
                            }
                            callBack.dismissLoader();
                        }

                        @Override
                        public void onFailure(Call<LoadSavedVideoResponse> call, Throwable t) {
                            callBack.dismissLoader();
                            callBack.showToastMessage(R.string.no_response, null);
                        }
                    });
        }
    }

    public void saveEditedVideoInfoToServer(final Dialog alertDialog, String videoId, String title, String overview, String propertyID) {
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
            viewModel.onAPIFailure("", AppConstant.NETWORK_FAILURE);
        } else {
            callBack.showLoader();
            ApiLinks.getClient().create(SaveEditedVideoInfoApi.class).post(videoId, "Active", title, overview)
                    .enqueue(new Callback<SaveEditedVideoInfoResponse>() {
                        @Override
                        public void onResponse(Call<SaveEditedVideoInfoResponse> call,
                                               Response<SaveEditedVideoInfoResponse> response) {
                            if (response.isSuccessful()) {
                                String message = response.body().getMessage();
                                String status = response.body().getResponse();
                                int code = response.body().getCode();
                                if (code == 100 && status.equals("Success")) {
                                    loadSavedVideoFromServer(propertyID);
                                } else {
                                    callBack.dismissLoader();
                                    callBack.showToastMessage(0, message);
                                }
                            } else {
                                callBack.dismissLoader();
                                callBack.showToastMessage(R.string.no_response, null);
                            }
                            alertDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<SaveEditedVideoInfoResponse> call, Throwable t) {
                            callBack.dismissLoader();
                            alertDialog.dismiss();
                            callBack.showToastMessage(R.string.no_response, null);
                        }
                    });
        }
    }

    public void deleteVideoFromServer(final Dialog alertDialog, String videoId, final int position, String propertyID) {
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
            viewModel.onAPIFailure("", AppConstant.NETWORK_FAILURE);
        } else {
            callBack.showLoader();
            ApiLinks.getClient().create(DeleteSavedVideoApi.class).post(videoId)
                    .enqueue(new Callback<DeleteSavedVideoResponse>() {
                        @Override
                        public void onResponse(Call<DeleteSavedVideoResponse> call,
                                               Response<DeleteSavedVideoResponse> response) {
                            if (response.isSuccessful()) {
                                String message = response.body().getMessage();
                                String status = response.body().getResponse();
                                int code = response.body().getCode();
                                if (code == 100 && status.equals("Success")) {
                                    callBack.showToastMessage(R.string.video_deleted_successfully, null);
                                    loadSavedVideoFromServer(propertyID);
                                } else {
                                    callBack.dismissLoader();
                                    callBack.showToastMessage(0, message);
                                }
                                alertDialog.dismiss();
                            } else {
                                callBack.dismissLoader();
                                alertDialog.dismiss();
                                callBack.showToastMessage(R.string.no_response, null);
                            }
                        }

                        @Override
                        public void onFailure(Call<DeleteSavedVideoResponse> call, Throwable t) {
                            callBack.dismissLoader();
                            alertDialog.dismiss();
                            callBack.showToastMessage(R.string.no_response, null);
                        }
                    });
        }
    }

    public void postPropertyToServer(PostPropertyApiDataClass postPropertyAPIData) {
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
            viewModel.onAPIFailure("", AppConstant.NETWORK_FAILURE);
        } else {
            Log.d("PostData",postPropertyAPIData.toString());
            callBack.showLoader();

           ApiLinks.getClient().create(PostPropertyApi.class).post(postPropertyAPIData.getLanguageID(),
                    postPropertyAPIData.getAction(), postPropertyAPIData.getRandomPropertyID(), postPropertyAPIData.getPlanID(),
                    postPropertyAPIData.getIndProp(), postPropertyAPIData.getPropCount(), postPropertyAPIData.getFeatCount(),
                    postPropertyAPIData.getUserOwnerID(), postPropertyAPIData.getExpired(), postPropertyAPIData.getPropertyPurpose(),
                    postPropertyAPIData.getPropertyFeatured(), postPropertyAPIData.getPropertyTypeID(), postPropertyAPIData.getProjectID(),
                    postPropertyAPIData.getPropertyName(),postPropertyAPIData.getPropertyPrice(), postPropertyAPIData.getPropertyDescription(),
                    postPropertyAPIData.getGoogleSearchText(), postPropertyAPIData.getGoogleLocality(), postPropertyAPIData.getGoogleCityName(),
                    postPropertyAPIData.getGoogleStateName(), postPropertyAPIData.getGoogleCountryName(), postPropertyAPIData.getPropertyZipCode(),
                    postPropertyAPIData.getPropertyAddress1(), postPropertyAPIData.getPropertyAddress2(), postPropertyAPIData.getPropertyLatitude(),
                    postPropertyAPIData.getPropertyLongitude(), postPropertyAPIData.getPropertyCurrentStatus(), postPropertyAPIData.getConMonth(),
                    postPropertyAPIData.getConYear(), postPropertyAPIData.getUserID(), postPropertyAPIData.getAttributeList(),
                    postPropertyAPIData.getParentCommunity(), postPropertyAPIData.getSubCommunity(), postPropertyAPIData.getPropertyMortgage(),
                    postPropertyAPIData.getMorgBankID(), postPropertyAPIData.getPropertyContactAgent(), postPropertyAPIData.getPropertyAltPhone(),
                    postPropertyAPIData.getPropertyAltEmail(), postPropertyAPIData.getPropertyCheques(), postPropertyAPIData.getRentTerm(),
                    postPropertyAPIData.getThreeSixtyView(), postPropertyAPIData.getBluePrintimage(),
                    postPropertyAPIData.getPropertyShowPhone(), postPropertyAPIData.getSubAgentId(), postPropertyAPIData.getPhoneNoOne(),
                    postPropertyAPIData.getPhoneNoTwo(),postPropertyAPIData.getCurrencyID_1(),postPropertyAPIData.getPropertyPrice_1(),
                    postPropertyAPIData.getCurrencyID_5(),postPropertyAPIData.getPropertyPrice_5(),
                    postPropertyAPIData.getPropertyPricePerM2(),postPropertyAPIData.getTotal_price(),postPropertyAPIData.getCountryCodeone(),postPropertyAPIData.getCountryCodeTwo()).enqueue(new Callback<PostPropertyResponse>() {
                @Override
                public void onResponse(Call<PostPropertyResponse> call, Response<PostPropertyResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String message = response.body().getMessage();
                        String status = response.body().getResponse();
                        int code = response.body().getCode();
                        if (code == 4004 && AppConstant.API_SUCCESS.equals(status)) {
                            Log.d("PostData",response.body()
                                    .getPropertyID());

                            viewModel.onAPISuccess(new Gson().toJson(response.body()), AppConstant.PP_POST_PROPERTY);
                        } else {
                            if (code == 409) {
                                Utils.showAlertErrorMessage(callBack.getPostPropertyContext(), message);
                            } else {
                                callBack.showToastMessage(0, message);
                            }
                        }
                    } else {
                        callBack.showToastMessage(R.string.no_response, null);
                    }
                    callBack.dismissLoader();
                }

                @Override
                public void onFailure(Call<PostPropertyResponse> call, Throwable t) {
                    callBack.dismissLoader();
                    callBack.showToastMessage(R.string.no_response, null);
                }
            });
        }
    }

    public void postEditedPropertyToServer(PostPropertyApiDataClass postPropertyAPIData) {
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
            viewModel.onAPIFailure("", AppConstant.NETWORK_FAILURE);
        } else {
            callBack.showLoader();

            ApiLinks.getClient().create(PostEditedPropertyApi.class).post(postPropertyAPIData.getLanguageID(),
                    postPropertyAPIData.getAction(), postPropertyAPIData.getRandomPropertyID(),
                    postPropertyAPIData.getIndProp(), postPropertyAPIData.getExpired(), postPropertyAPIData.getPropertyPurpose(),
                    postPropertyAPIData.getPropertyFeatured(), postPropertyAPIData.getPropertyTypeID(),
                    postPropertyAPIData.getPropertyName(), postPropertyAPIData.getPropertyPrice(), postPropertyAPIData.getPropertyDescription(),
                    postPropertyAPIData.getGoogleSearchText(), postPropertyAPIData.getGoogleLocality(), postPropertyAPIData.getGoogleCityName(),
                    postPropertyAPIData.getGoogleStateName(), postPropertyAPIData.getGoogleCountryName(), postPropertyAPIData.getPropertyZipCode(),
                    postPropertyAPIData.getPropertyAddress1(), postPropertyAPIData.getPropertyAddress2(), postPropertyAPIData.getPropertyLatitude(),
                    postPropertyAPIData.getPropertyLongitude(), postPropertyAPIData.getPropertyCurrentStatus(), postPropertyAPIData.getConMonth(),
                    postPropertyAPIData.getConYear(), postPropertyAPIData.getUserID(), postPropertyAPIData.getAttributeList(),
                    PostPropertyPage01Activity.propertyDetailsEdit.getPropertyDetailsID(),
                    postPropertyAPIData.getParentCommunity(), postPropertyAPIData.getSubCommunity(), postPropertyAPIData.getPropertyMortgage(),
                    postPropertyAPIData.getMorgBankID(), postPropertyAPIData.getPropertyContactAgent(), postPropertyAPIData.getPropertyAltPhone(),
                    postPropertyAPIData.getPropertyAltEmail(), postPropertyAPIData.getPropertyCheques(), postPropertyAPIData.getRentTerm(),
                    postPropertyAPIData.getThreeSixtyView(), postPropertyAPIData.getBluePrintimage(),
                    postPropertyAPIData.getPropertyShowPhone(),postPropertyAPIData.getPhoneNoOne(),postPropertyAPIData.getPhoneNoTwo(),
                    postPropertyAPIData.getCurrencyID_1(),postPropertyAPIData.getPropertyPrice_1(),
                    postPropertyAPIData.getCurrencyID_5(),postPropertyAPIData.getPropertyPrice_5(),
                    postPropertyAPIData.getPropertyPricePerM2(),postPropertyAPIData.getTotal_price(),postPropertyAPIData.getCountryCodeone(),postPropertyAPIData.getCountryCodeTwo())
                    .enqueue(new Callback<PostEditedPropertyResponse>() {
                        @Override
                        public void onResponse(Call<PostEditedPropertyResponse> call,
                                               Response<PostEditedPropertyResponse> response) {
                            if (response.isSuccessful()) {
                                String message = response.body().getMessage();
                                String status = response.body().getResponse();
                                int code = response.body().getCode();
                                if (code == 4004 && status.equals("Success")) {
                                    viewModel.onAPISuccess(new Gson().toJson(response.body()), AppConstant.PP_EDIT_POST_PROPERTY);
                                } else {
                                    if (code == 409) {
                                        Utils.showAlertErrorMessage(callBack.getPostPropertyContext(), message);
                                    } else {
                                        callBack.showToastMessage(0, message);
                                    }
                                }
                            } else {
                                callBack.showToastMessage(R.string.no_response, null);
                            }
                            callBack.dismissLoader();
                        }

                        @Override
                        public void onFailure(Call<PostEditedPropertyResponse> call, Throwable t) {
                            callBack.dismissLoader();
                            callBack.showToastMessage(R.string.no_response, null);
                        }
                    });
        }
    }

    public void getCityList(String languageID){
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
            viewModel.onAPIFailure("", AppConstant.NETWORK_FAILURE);
        } else {
            callBack.showLoader();
            ApiLinks.getClient().create(CityListingApi.class).post("" + languageID)
                    .enqueue(new Callback<CityListingResponse>() {
                        @Override
                        public void onResponse(Call<CityListingResponse> call, Response<CityListingResponse> response) {
                            if (response.isSuccessful()) {
                                String message = response.body().getResponse();
                                if (message != null && message.equalsIgnoreCase("Success")) {
                                    viewModel.onAPISuccess(new Gson().toJson(response.body()), AppConstant.PP_CITY_LIST);
                                }
                                else {
                                    callBack.showToastMessage(R.string.no_response, null);

                                }
                            }
                            else {
                                callBack.showToastMessage(R.string.no_response, null);

                            }
                            callBack.dismissLoader();
                        }

                        @Override
                        public void onFailure(Call<CityListingResponse> call, Throwable t) {
                            callBack.dismissLoader();
                        }
                    });
        }
    }

    public void getNeighbourhoodList(String query,String selectedCity){
        if (Utils.NoInternetConnection(PayaAppClass.getAppInstance())) {
            viewModel.onAPIFailure("", AppConstant.NETWORK_FAILURE);
        } else {
            callBack.showLoader();
           /* ApiLinks.getClient().create(ListLocProjApi.class).post(
                            GlobalValues.countryID,
                            query,
                            selectedCity,
                            GlobalValues.selectedRegionId)
                    .enqueue(new Callback<ListLocProjectResponse>() {
                        @Override
                        public void onResponse(Call<ListLocProjectResponse> call, Response<ListLocProjectResponse> response) {
                          if(response.isSuccessful()){
                              String message = response.body().getResponse();
                              if (message != null && message.equalsIgnoreCase("Success")) {
                                  viewModel.onAPISuccess(new Gson().toJson(response.body()), AppConstant.PP_NBH_LIST);
                              }
                              else {
                                  callBack.showToastMessage(R.string.no_response, null);

                              }
                          }else {
                              callBack.showToastMessage(R.string.no_response, null);

                          }

                            callBack.dismissLoader();
                        }

                        @Override
                        public void onFailure(Call<ListLocProjectResponse> call, Throwable t) {
                         callBack.dismissLoader();
                        }
                    });*/
        }

    }
}
