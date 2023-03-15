package com.paya.paragon.activity.postProperty;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.gson.Gson;
import com.paya.paragon.R;
import com.paya.paragon.activity.ImagesPreviewActivity;
import com.paya.paragon.adapter.AdapterSavedGalleryImages;
import com.paya.paragon.adapter.AdapterSavedVideos;
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
import com.paya.paragon.databinding.ActivityPostPropertyPage05Binding;
import com.paya.paragon.utilities.AppConstant;
import com.paya.paragon.utilities.Utils;
import com.paya.paragon.viewmodel.PostPropertyViewModel;

import java.util.ArrayList;
import java.util.List;

public class PostPropertyPage05Activity extends BaseViewModelActivity<PostPropertyViewModel> implements
        PostPropertyViewModel.PostPropertyViewModelCallBack {

    private ActivityPostPropertyPage05Binding binding;
    private PostPropertyViewModel viewModel;
    private int GET_FILE_REQUEST_CODE = 12;
    private AdapterSavedGalleryImages imageAdapter;
    private AdapterSavedVideos videoListAdapter;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_property_page_05);
        binding.setActivity(this);
        binding.setViewModel(onCreateViewModel());
        initiateToolBar();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, binding.cl05Parent);
    }

    private void initiateToolBar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow_black);
        binding.toolbarTitle.setText(" ");
        binding.toolbarTitle.setTextSize(16);
    }

    @Override
    public PostPropertyViewModel onCreateViewModel() {
        viewModel = ViewModelProviders.of(this).get(PostPropertyViewModel.class);
        viewModel.initiatePostProperty(this);
        viewModel.updateDetailsForEditProperty();
        return viewModel;
    }


    @Override
    public void initiateGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, viewModel.isMultipleSelectionEnable);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, getString(R.string.complete_action_using)), GET_FILE_REQUEST_CODE);
        } catch (Exception ex) {
            FirebaseCrashlytics.getInstance().recordException(ex);
            showToastMessage(R.string.install_filemanages, null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PostPropertyViewModel.MULTIPLE_PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initiateGalleryIntent();
                } else {
                    showToastMessage(R.string.permission_denied, null);
                }
                break;
        }
    }


    //TODO File Upload
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FILE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                viewModel.uploadImageAndBluePrintForProperty(data,PostPropertyPage05Activity.this);
            }
        }
    }

    @Override
    public void updateSavedImagesViews(ArrayList<SavedImageInfo> savedImageInfoArrayList, String imgUrl) {
        if (PostPropertyPage01Activity.m_IsEditPostProperty && PostPropertyPage01Activity.propertyDetailsEdit.getThreeSixtyView() != null) {
            binding.edit360View.setText(PostPropertyPage01Activity.propertyDetailsEdit.getThreeSixtyView());
        }
        if (savedImageInfoArrayList != null && !savedImageInfoArrayList.isEmpty()) {
            Log.d("photoUploadCheck",savedImageInfoArrayList.size()+" ");
            viewModel.isImageUploaded = true;
            binding.recyclerSavedImagesPostProperty.setVisibility(View.VISIBLE);
            binding.recyclerSavedImagesPostProperty.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            binding.recyclerSavedImagesPostProperty.setHasFixedSize(true);
            imageAdapter = new AdapterSavedGalleryImages(savedImageInfoArrayList, imgUrl);
            binding.recyclerSavedImagesPostProperty.setAdapter(imageAdapter);
            imageAdapter.setDeleteImageInterface(new AdapterSavedGalleryImages.DeleteImageInterface() {
                @Override
                public void onImageDeleted(String imageID, int position) {
                    viewModel.deleteGalleryImageFromServer(imageID, position);
                }

                @Override
                public void onImageClicked(SavedImageInfo savedImageInfo, int position, String imagePath) {
                    Intent intent  = new Intent(PostPropertyPage05Activity.this, ImagesPreviewActivity.class);
                    intent.putExtra(AppConstant.KEY_IMAGES, savedImageInfo);
                    intent.putExtra(AppConstant.KEY_IMAGE_PATH, imagePath);
                    startActivity(intent);
                }
            });
        } else {
            viewModel.isImageUploaded = false;
            binding.recyclerSavedImagesPostProperty.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateSavedVideoUrl(ArrayList<SavedVideoInfo> videoInfoArrayList) {
        binding.editVideoUrlPostProperty.setText(" ");
        if (videoInfoArrayList != null && !videoInfoArrayList.isEmpty()) {
            binding.recyclerSavedVideosContentPostProperty.setVisibility(View.VISIBLE);
            binding.recyclerSavedVideosContentPostProperty.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            binding.recyclerSavedVideosContentPostProperty.setHasFixedSize(true);
            videoListAdapter = new AdapterSavedVideos(this, videoInfoArrayList);
            binding.recyclerSavedVideosContentPostProperty.setAdapter(videoListAdapter);
            videoListAdapter.setDeleteVideoInfoInterface(new AdapterSavedVideos.DeleteVideoInfoInterface() {
                @Override
                public void onVideoInfoDelete(Dialog alertDialog, String videoId, int position) {
                    viewModel.initiateDeletePropertyVideo(alertDialog, videoId, position);
                }
            });
            videoListAdapter.setEditVideoInfoInterface(new AdapterSavedVideos.EditVideoInfoInterface() {
                @Override
                public void onVideoInfoEdited(Dialog alertDialog, String title, String overview, String videoId) {
                    viewModel.initiateEditPropertyVideo(alertDialog, videoId, title, overview);
                }
            });
        } else {
            binding.recyclerSavedVideosContentPostProperty.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateBlurPrintBitMap(Bitmap bitmap, String url) {
        if (bitmap != null) {
            binding.imageViewBlueprint.setImageBitmap(bitmap);
            binding.llButtonUploadBlueprint.setVisibility(View.GONE);
            binding.layoutBlueprint.setVisibility(View.VISIBLE);
        } else {
            if (url != null) {
                binding.llButtonUploadBlueprint.setVisibility(View.GONE);
                binding.layoutBlueprint.setVisibility(View.VISIBLE);
                Utils.loadUrlImage(binding.imageViewBlueprint, url, R.drawable.no_image_placeholder, true);
            } else {
                binding.llButtonUploadBlueprint.setVisibility(View.VISIBLE);
                binding.layoutBlueprint.setVisibility(View.GONE);
            }
        }
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
        Utils.showAlertNoInternet(PostPropertyPage05Activity.this);
    }

    @Override
    public void showToastMessage(int messageId, String message) {
        Toast.makeText(this, message != null ? message : messageId > 0 ? getString(messageId) : getString(R.string.please_try_after_some_times), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSubmitPostPropertySuccessfully(String propertyID) {
        Intent intent = new Intent(PostPropertyPage05Activity.this, ActivityPostPropertyPreview.class);
        Utils.getAttrAmenMap().clear();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("propertyID", propertyID);
        intent.putExtra("isEdit", true);
        startActivity(intent);
        finish();
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
    public void initiateSubAgentListSpinner(List<AgentDataListDATAItemObject> subAgentSpinnerData) {

    }

    /**
     * this is for post property 01 page
     */
    @Override
    public void onPropertyPostBySpinnerClick() {

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



    @Override
    public Activity getPostPropertyContext() {
        return PostPropertyPage05Activity.this;
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

    @Override
    public void onNextClick() {
        Log.e("onNextClick:#######", "" + new Gson().toJson(PostPropertyPage01Activity.m_PostPropertyAPIData));
    }

    /**
     * this is for post property 03 page (Property Specification)
     */
    @Override
    public void setPropertyBedRoomFeature(ArrayList<BedRoomInfo> bedRoomListData) {


    }

    @Override
    public void updateDescription(String propertyDescription) {
        binding.editPropertyOverviewPostProperty.setText(propertyDescription);

    }

    @Override
    public void updateCity(CityListingResponse cityListingResponse) {

    }

    @Override
    public void attachNBHRcvItems(ListLocProjectResponse listLocProjectResponse) {

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

    @Override
    public void updateAmenitiesInAdapter(ArrayList<AmenitiesModel> amenities) {

    }
}
