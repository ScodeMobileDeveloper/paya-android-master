package com.paya.paragon.activity.postProperty;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.PropertyProjectListActivity;
import com.paya.paragon.R;
import com.paya.paragon.adapter.AdapterBankList;
import com.paya.paragon.adapter.AdapterBedRooms;
import com.paya.paragon.adapter.AdapterPlanSpinner;
import com.paya.paragon.adapter.AdapterPropertyTypeSelector;
import com.paya.paragon.adapter.AdapterSpecifications;
import com.paya.paragon.adapter.SubAgentSpinnerAdapter;
import com.paya.paragon.api.agentList.AgentDataListDATAItemObject;
import com.paya.paragon.api.bankListPropertyPost.BankListData;
import com.paya.paragon.api.bedRoomList.BedRoomInfo;
import com.paya.paragon.api.cityList.CityListingResponse;
import com.paya.paragon.api.editPostProperty.EditPostedPropertyData;
import com.paya.paragon.api.editPostProperty.PropertyDetailsData;
import com.paya.paragon.api.index.PropertyTypeMain;
import com.paya.paragon.api.index.PropertyTypeSub;
import com.paya.paragon.api.listLocProject.ListLocProjectResponse;
import com.paya.paragon.api.postProperty.attributeListing.AllAttributesData;
import com.paya.paragon.api.postProperty.loadGalleryImage.SavedImageInfo;
import com.paya.paragon.api.postProperty.loadVideo.SavedVideoInfo;
import com.paya.paragon.api.postProperty.post.PostPropertyApiDataClass;
import com.paya.paragon.api.postProperty.postPropertyIndex.PostPropertyIndexData;
import com.paya.paragon.api.postProperty.randomID.RandomIdData;
import com.paya.paragon.api.postProperty.randomID.UserPlanInfo;
import com.paya.paragon.api.propertyDetails.AmenitiesModel;
import com.paya.paragon.base.BaseViewModelActivity;
import com.paya.paragon.databinding.ActivityPostPropertyPage01Binding;
import com.paya.paragon.utilities.AppConstant;
import com.paya.paragon.utilities.Utils;
import com.paya.paragon.viewmodel.PostPropertyViewModel;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  PostPropertyPage01Activity extends BaseViewModelActivity<PostPropertyViewModel> implements
        PostPropertyViewModel.PostPropertyViewModelCallBack,AdapterSpecifications.AdapterSpecificationsCallback{

    private ActivityPostPropertyPage01Binding binding;
    private PostPropertyViewModel viewModel;
    private Dialog propertyTypeSpinnerDialog;

    public static String m_PostPropertyId;
    public static boolean m_IsEditPostProperty = false;
    public static RandomIdData m_RandomIdData;
    public static EditPostedPropertyData m_EditPostedPropertyData;
    public static PostPropertyApiDataClass m_PostPropertyAPIData;
    public static PropertyDetailsData propertyDetailsEdit;



    private AdapterBedRooms adapterBedRooms;
    private AdapterSpecifications adapterSpecifications;
    private ArrayList<AllAttributesData> allAttributesList;

    boolean mPropertyTypeDropdownClicked = false;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_property_page_01);
        initiateToolBar();
        declarations();
        binding.setCallBack(this);
        checkInitialSteps(getIntent());
        binding.setViewModel(onCreateViewModel());
    }

    public void declarations() {


        
        binding.ccPhoneNoOne.registerPhoneNumberTextView(binding.etPhoneNoOne);
        binding.ccPhoneNoTwo.registerPhoneNumberTextView(binding.etPhoneNoTwo);




        binding.ccPhoneNoOne.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                m_PostPropertyAPIData.setCountryCodeone(binding.ccPhoneNoOne.getSelectedCountryCodeWithPlus());
            }
        });
        binding.ccPhoneNoTwo.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {

                m_PostPropertyAPIData.setCountryCodeTwo(binding.ccPhoneNoTwo.getSelectedCountryCodeWithPlus());

            }
        });



    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkInitialSteps(intent);
        binding.setViewModel(onCreateViewModel());
    }

    private void initiateToolBar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow_black);
        binding.toolbarTitle.setText(getString(R.string.add_property));
        binding.toolbarTitle.setTextSize(16);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, binding.ll01Parent);
    }

    private void checkInitialSteps(Intent intent) {
        clearValue();
        String action = intent.getStringExtra("action");
        propertyDetailsEdit = new PropertyDetailsData();
        m_PostPropertyAPIData = new PostPropertyApiDataClass();

//        m_PostPropertyAPIData.setCountryCodeone(binding.ccPhoneNoOne.getSelectedCountryCodeWithPlus());
//        m_PostPropertyAPIData.setCountryCodeTwo(binding.ccPhoneNoTwo.getSelectedCountryCodeWithPlus());

        if (action != null && action.equals("edit")) {
            m_PostPropertyId = intent.getStringExtra("propertyId");
            m_IsEditPostProperty = true;
        }



    }

    @Override
    public PostPropertyViewModel onCreateViewModel() {
        viewModel = ViewModelProviders.of(this).get(PostPropertyViewModel.class);
        viewModel.initiatePostProperty(this);
        viewModel.initiateCheckLimit();
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
        Utils.showAlertNoInternet(PostPropertyPage01Activity.this);
    }

    @Override
    public void showToastMessage(int messageId, String message) {
        Toast.makeText(this, message != null ? message : messageId > 0 ? getString(messageId) : getString(R.string.please_try_after_some_times), Toast.LENGTH_SHORT).show();
    }

    /**
     * this is for post property 03 page (Property Specification)
     */
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

    /**
     * this is for post property 03 page (Property Specification)
     */
    @Override
    public void setPropertyAttributeFeature(ArrayList<AllAttributesData> attrList) {
        if (attrList.size() > 0) {
            allAttributesList = attrList;
            adapterSpecifications = new AdapterSpecifications(PostPropertyPage01Activity.this, allAttributesList, "none", this);
            binding.recyclerSpecificationsContentPostProperty.setLayoutManager(new LinearLayoutManager(this));
            binding.recyclerSpecificationsContentPostProperty.setHasFixedSize(true);
            binding.recyclerSpecificationsContentPostProperty.setAdapter(adapterSpecifications);
        }
    }

    /**
     * this is for post property 03 page (Property Specification)
     */
    @Override
    public ArrayList<AllAttributesData> getAttrList() {
        return allAttributesList;

    }

    /**
     * this is for post property 04 page (Property Amenities)
     */
    @Override
    public void updateAmenitiesInAdapter(ArrayList<AmenitiesModel> amenities) {
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
    public void updatePropertyCurrentStatus(String propertyCurrentStatus) {
        if (AppConstant.PP_UNDER_CONSTRUCTION.equals(propertyCurrentStatus)) {
            binding.rbUc.setChecked(true);
        } else if (AppConstant.PP_READY_TO_MOVE_SELL.equals(propertyCurrentStatus)) {
            binding.rbRtmS.setChecked(true);
        } else if (AppConstant.PP_RENTED.equals(propertyCurrentStatus)) {
            binding.rbRtd.setChecked(true);
        } else if (AppConstant.PP_READY_TO_MOVE_RENT.equals(propertyCurrentStatus)) {
            binding.rbRtmR.setChecked(true);
        } else {
            binding.rbWrs.setChecked(true);
        }
    }

    @Override
    public void updateEditPropertyFields() {
        setPropertyBasedOnSellAndRentType(propertyDetailsEdit.getPropertyPurpose().equalsIgnoreCase(AppConstant.PP_SELL));
        updatePropertyCurrentStatus(propertyDetailsEdit.getPropertyCurrentStatus());
        binding.editPropertyPricePostProperty.setText(propertyDetailsEdit.getPropertyPrice());
        binding.editPropertyTitlePostProperty.setText(propertyDetailsEdit.getPropertyName());
//        binding.editPropertyOverviewPostProperty.setText(propertyDetailsEdit.getPropertyDescription());
       binding.etPhoneNoOne.setText(propertyDetailsEdit.getPhoneNoOne());
       binding.etPhoneNoTwo.setText(propertyDetailsEdit.getPhoneNoTwo());


        Log.d("cc",propertyDetailsEdit.getCountryCodeone()+" "+propertyDetailsEdit.getCountryCodeTwo());
        if(!propertyDetailsEdit.getCountryCodeone().isEmpty())
        binding.ccPhoneNoOne.setCountryForPhoneCode(Integer.parseInt(propertyDetailsEdit.getCountryCodeone()));
        if(!propertyDetailsEdit.getCountryCodeTwo().isEmpty())
            binding.ccPhoneNoTwo.setCountryForPhoneCode(Integer.parseInt(propertyDetailsEdit.getCountryCodeTwo()));


       if(propertyDetailsEdit.getPropertyPrices().get5().equalsIgnoreCase("0"))
           binding.radioButtonIqd.setChecked(true);

       else binding.radioButtonUsd.setChecked(true);

        if(!propertyDetailsEdit.getPropertyPricePerM2().equalsIgnoreCase("0"))
            binding.radioButtonPriceperM2.setChecked(true);

        else
            binding.radioButtonTotalPrice.setChecked(true);

        onMotagagedClicked(!propertyDetailsEdit.getPropertyMortgage().isEmpty() && propertyDetailsEdit.getPropertyMortgage().equalsIgnoreCase(AppConstant.YES));
    }

    @Override
    public void initiateSubAgentListSpinner(List<AgentDataListDATAItemObject> subAgentSpinnerData) {
        if (subAgentSpinnerData != null && !subAgentSpinnerData.isEmpty()) {
            binding.llPpropertyPostedBy.setVisibility(View.VISIBLE);
            SubAgentSpinnerAdapter adapter = new SubAgentSpinnerAdapter(this, R.layout.item_price_listing_model,
                    R.id.text_price_list_item, subAgentSpinnerData);
            binding.spnrSubAgent.setAdapter(adapter);
            if (m_IsEditPostProperty) {
                updateSubAgentViewWhileEdit(subAgentSpinnerData);
            }
            binding.spnrSubAgent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int spinnerPosition, long l) {
                    AgentDataListDATAItemObject subAgentData = (AgentDataListDATAItemObject) binding.spnrSubAgent.getItemAtPosition(spinnerPosition);
                    binding.selectorTextSubAgent.setText(subAgentData.getName());
                    m_PostPropertyAPIData.setSubAgentId(subAgentData.getUserID());
//                    m_PostPropertyAPIData.setPhoneNoOne(subAgentData.getPhone());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        } else {
            binding.llPpropertyPostedBy.setVisibility(View.GONE);
        }
    }

    private void updateSubAgentViewWhileEdit(List<AgentDataListDATAItemObject> subAgentList) {
        for (AgentDataListDATAItemObject subAgentData : subAgentList) {
            if (subAgentData.getUserID().equalsIgnoreCase(m_PostPropertyAPIData.getSubAgentId())) {
                binding.spnrSubAgent.setSelection(subAgentList.indexOf(subAgentData));
                break;
            }
        }
    }

    @Override
    public void onPropertyPostBySpinnerClick() {
        binding.spnrSubAgent.performClick();
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

    @Override
    public Activity getPostPropertyContext() {
        return PostPropertyPage01Activity.this;
    }

    @Override
    public void onPropertyTypeSpinnerClick() {
//        propertyTypeSpinnerDialog.show();

        if(!mPropertyTypeDropdownClicked){
            binding.propertyTypeDropdownLay.setVisibility(View.VISIBLE);
            mPropertyTypeDropdownClicked = true;

        }else {
            binding.propertyTypeDropdownLay.setVisibility(View.GONE);
            mPropertyTypeDropdownClicked = false;

        }
    }

    @Override
    public void setPlanSelector(boolean planAvailable, ArrayList<UserPlanInfo> planList) {
        binding.layoutPlanSelector.setVisibility(View.GONE);
        if (planAvailable) {
            ArrayList<UserPlanInfo> myPlans = new ArrayList<>();
            myPlans.addAll(planList);
            m_PostPropertyAPIData.setPlanID(planList.get(0).getId());
            AdapterPlanSpinner adapterPlanSpinner = new AdapterPlanSpinner(this, R.layout.item_price_listing_model,
                    R.id.text_price_list_item, myPlans);
            binding.spinnerPropertyPlan.setAdapter(adapterPlanSpinner);
            binding.spinnerPropertyPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int spinnerPosition, long id) {
                    UserPlanInfo selectedItem = (UserPlanInfo) binding.spinnerPropertyPlan.getAdapter().getItem(spinnerPosition);
                    binding.selectorPropertyPlan.setText(selectedItem.getPlanTitle());
                    m_PostPropertyAPIData.setPlanID(selectedItem.getId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    m_PostPropertyAPIData.setPlanID("");
                }
            });
            binding.selectorPropertyPlan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.spinnerPropertyPlan.performClick();
                }
            });
        } else {
            m_PostPropertyAPIData.setPlanID("");
        }
    }

    @Override
    public void setPropertyBasedOnSellAndRentType(boolean isPropertyForSell) {
        binding.radioButtonSellPostProperty.setChecked(isPropertyForSell);
        binding.radioButtonRentPostProperty.setChecked(!isPropertyForSell);
        m_PostPropertyAPIData.setPropertyPurpose(isPropertyForSell ? AppConstant.PP_SELL : AppConstant.PP_RENT);
        binding.textPropertyPricePostProperty.setText(Html.fromHtml(getString(isPropertyForSell ? R.string.price_star : R.string.mothly_rent_with_star)));
        binding.llMortgagedLayout.setVisibility(View.GONE);
        binding.rgSellPropertyStatus.setVisibility(isPropertyForSell ? View.VISIBLE : View.GONE);
        binding.rgRentPropertyStatus.setVisibility(isPropertyForSell ? View.GONE : View.VISIBLE);
        binding.radioGroupPriceType.setVisibility(isPropertyForSell ? View.VISIBLE : View.GONE);


    }

    @Override
    public void setPriceperMTotalPriceUncheck(boolean isCurrencyTypeChanged) {

        binding.radioGroupPriceType.clearCheck();


    }

    @Override
    public void onMotagagedClicked(boolean isMotagagedYes) {
        binding.radioButtonNoMortgaged.setChecked(!isMotagagedYes);
        binding.radioButtonYesMortgaged.setChecked(isMotagagedYes);
        m_PostPropertyAPIData.setPropertyMortgage(isMotagagedYes ? AppConstant.YES : AppConstant.NO);
        binding.llSelectBankLay.setVisibility(isMotagagedYes ? View.VISIBLE : View.GONE);
    }

    @Override
    public void initiateBankList(ArrayList<BankListData> bankListData) {
        final Dialog dialog = new Dialog(this);
        @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.show_price_list_selector, null);
        final RecyclerView recyclerCityList = (RecyclerView) view.findViewById(R.id.recycler_price_list);
        recyclerCityList.setLayoutManager(new LinearLayoutManager(this));
        AdapterBankList adapter = new AdapterBankList(bankListData);

        recyclerCityList.setAdapter(adapter);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.show();

        adapter.setDistrictInterface(new AdapterBankList.SelectDistrictInterface() {
            @Override
            public void onDistrictSelected(BankListData bankListData) {
                m_PostPropertyAPIData.setMorgBankID(bankListData.getBankID());
                binding.selectBank.setText(bankListData.getBankTitle());
                dialog.dismiss();
            }
        });
    }

    @Override
    public void setPropertyTypeSpinner(PostPropertyIndexData postPropertyIndexData) {
        AdapterPropertyTypeSelector adapter = new AdapterPropertyTypeSelector(this, postPropertyIndexData.getPropertyType());
        binding.listViewPropTypeSelector.setAdapter(adapter);

        if (m_IsEditPostProperty) {
            updateViewWhileEdit(postPropertyIndexData.getPropertyType());
        }

        for (int i = 0; i < adapter.getGroupCount(); i++) {
            binding.listViewPropTypeSelector.expandGroup(i);
        }

        binding.allTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textPropertyTypePostProperty.setText(getResources().getString(R.string.any));
                m_PostPropertyAPIData.setPropertyTypeID("");
                m_PostPropertyAPIData.setAttributeList("");
                if (Utils.getAttrAmenMap() != null) {
                    Utils.getAttrAmenMap().clear();
                }
            }
        });

        binding.listViewPropTypeSelector.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });

        binding.listViewPropTypeSelector.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                PropertyTypeSub child = postPropertyIndexData.getPropertyType().get(groupPosition).getSubCategory().get(childPosition);
                binding.textPropertyTypePostProperty.setText(child.getPropertyTypeName());
                binding.propertyTypeDropdownLay.setVisibility(View.GONE);
                m_PostPropertyAPIData.setPropertyTypeID(child.getPropertyTypeID());
                viewModel.initiateAttributeAminitiesListAPI();

                binding.tvTitleSpecifications.setText(String.format("%s %s",getResources().getString(R.string.adp_specification),child.getPropertyTypeName()));

                if (m_IsEditPostProperty) {
                    if (propertyDetailsEdit.getPropertyTypeID().equalsIgnoreCase(child.getPropertyTypeID())) {
                        viewModel.updateAttributesAndAmenitiesMap();
                    }
                } else {
                    if (Utils.getAttrAmenMap() != null) {
                        Utils.getAttrAmenMap().clear();
                    }
                }
                m_PostPropertyAPIData.setAttributeList("");
                return false;
            }
        });
        /*propertyTypeSpinnerDialog = new Dialog(this);
        @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.show_property_type_selector, null);
        final ExpandableListView listView = (ExpandableListView) view.findViewById(R.id.list_view_prop_type_selector);
        final AdapterPropertyTypeSelector adapter =
                new AdapterPropertyTypeSelector(PostPropertyPage01Activity.this, postPropertyIndexData.getPropertyType());
        TextView any = (TextView) view.findViewById(R.id.spinner_selection_any);
        listView.setAdapter(adapter);
        propertyTypeSpinnerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        propertyTypeSpinnerDialog.setContentView(view);
        if (m_IsEditPostProperty) {
            updateViewWhileEdit(postPropertyIndexData.getPropertyType());
        }
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            listView.expandGroup(i);
        }

        any.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textPropertyTypePostProperty.setText(getResources().getString(R.string.any));
                m_PostPropertyAPIData.setPropertyTypeID("");
                m_PostPropertyAPIData.setAttributeList("");
                if (Utils.getAttrAmenMap() != null) {
                    Utils.getAttrAmenMap().clear();
                }
                propertyTypeSpinnerDialog.dismiss();
            }
        });

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                PropertyTypeSub child = postPropertyIndexData.getPropertyType().get(groupPosition).getSubCategory().get(childPosition);
                binding.textPropertyTypePostProperty.setText(child.getPropertyTypeName());
                m_PostPropertyAPIData.setPropertyTypeID(child.getPropertyTypeID());
                viewModel.initiateAttributeAminitiesListAPI();

                binding.tvTitleSpecifications.setText(String.format("%s %s",getResources().getString(R.string.adp_specification),child.getPropertyTypeName()));

                if (m_IsEditPostProperty) {
                    if (propertyDetailsEdit.getPropertyTypeID().equalsIgnoreCase(child.getPropertyTypeID())) {
                        viewModel.updateAttributesAndAmenitiesMap();
                    }
                } else {
                    if (Utils.getAttrAmenMap() != null) {
                        Utils.getAttrAmenMap().clear();
                    }
                }
                m_PostPropertyAPIData.setAttributeList("");
                propertyTypeSpinnerDialog.dismiss();
                return false;
            }
        });*/
    }

    /**
     * update this text  while page come for edit post property.
     *
     * @param propertyType
     */
    private void updateViewWhileEdit(ArrayList<PropertyTypeMain> propertyType) {
        for (PropertyTypeMain main : propertyType) {
            ArrayList<PropertyTypeSub> sublist = main.getSubCategory();
            for (PropertyTypeSub typeSub : sublist) {
                if (propertyDetailsEdit.getPropertyTypeID().equals(typeSub.getPropertyTypeID())) {
                    binding.textPropertyTypePostProperty.setText(typeSub.getPropertyTypeName());

                    binding.tvTitleSpecifications.setText(String.format("%s %s",getString(R.string.adp_specification),typeSub.getPropertyTypeName()));
                    viewModel.initiateAttributeAminitiesListAPI();

                    break;
                }
            }
        }
    }

    @Override
    public void onNextClick() {
        if (Utils.getAttrAmenMap() == null) {
            Utils.setAttrAmenMap(new HashMap<>());
        }
        if (Utils.getAttrAmenMap().isEmpty() || !Utils.getAttrAmenMap().containsKey(Utils.ATTR_PHONE_NO_ID)) {
            Map<String, String> attrMap = Utils.getAttrAmenMap();
            attrMap.put(Utils.ATTR_PHONE_NO_ID, m_PostPropertyAPIData.getPhoneNoOne());
            Utils.setAttrAmenMap(attrMap);
        }
//        startActivity(new Intent(PostPropertyPage01Activity.this, PostPropertyPage02Activity.class));
        Intent intent = new Intent(PostPropertyPage01Activity.this, viewModel.isAmenitiesFound ? PostPropertyPage04Activity.class : PostPropertyPage02Activity.class);
        if (viewModel.isAmenitiesFound) {
            intent.putExtra(AppConstant.I_AMENITIES, viewModel.amenitiesFromResponse);
        }
        startActivity(intent);
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
        clearValue();
        Intent intent = new Intent(this, PropertyProjectListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("searchPropertyPurpose", "Sell");
        startActivity(intent);
        finish();
    }

    private void clearValue() {
        m_IsEditPostProperty = false;
        m_EditPostedPropertyData = new EditPostedPropertyData();
        m_PostPropertyAPIData = new PostPropertyApiDataClass();
        m_PostPropertyId = "";
        m_RandomIdData = new RandomIdData();
        propertyDetailsEdit = new PropertyDetailsData();
        if (Utils.getAttrAmenMap() != null) {
            Utils.getAttrAmenMap().clear();
        }
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
    public void isLivingRoomMandatory(boolean isMandatory) {
        if (!viewModel.isLivingRoomMandatory) {
            viewModel.isLivingRoomMandatory = isMandatory;
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
        if (!viewModel.isFloorNumberMandatory) {
            viewModel.isFloorNumberMandatory = isMandatory;
        }
    }
}
