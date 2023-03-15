package com.paya.paragon.activity.postRequirements;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dpizarro.autolabel.library.AutoLabelUI;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.paya.paragon.R;
import com.paya.paragon.adapter.AdapterTypeSelector;
import com.paya.paragon.adapter.BedRoomAdapter;
import com.paya.paragon.adapter.PostPropertyRegionAdapter;
import com.paya.paragon.api.PostPropertyRegion.PostPropertyRegion1Api;
import com.paya.paragon.api.PostPropertyRegion.PropertyRegionResponse;
import com.paya.paragon.api.PostPropertyRegion.RegionData;
import com.paya.paragon.api.bedRoomList.BedRoomInfo;
import com.paya.paragon.api.bedRoomList.BedRoomListApi;
import com.paya.paragon.api.bedRoomList.BedRoomListResponse;
import com.paya.paragon.api.budgetList.BudgetPriceData;
import com.paya.paragon.api.index.IndexBuyRentApi;
import com.paya.paragon.api.index.IndexBuyRentResponse;
import com.paya.paragon.api.index.PropertyTypeMain;
import com.paya.paragon.api.index.PropertyTypeSub;
import com.paya.paragon.api.postProperty.attributeListing.AllAttributesData;
import com.paya.paragon.api.postRequirement.editPostRequirement.EditPostedRequirementsApi;
import com.paya.paragon.api.postRequirement.editPostRequirement.EditPostedRequirementsData;
import com.paya.paragon.api.postRequirement.editPostRequirement.EditPostedRequirementsResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.model.RequirementModel;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;
import com.paya.paragon.utilities.custompricerange.RangeSeekBar;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"RedundantCast", "HardCodedStringLiteral"})
public class ActivityRequirementPropertyType extends AppCompatActivity {

    DialogProgress mLoading;
    ArrayList<PropertyTypeMain> propertyTypeList = new ArrayList<>();
    RecyclerView propertyTypeRecycler, bedroomRecycler;
    RangeSeekBar<Integer> priceRangeBar;
    TextView tvMinPrice, tvMaxPrice;
    TableRow trMinMaxPrice;

    String imagePath = "";
    ArrayList<BedRoomInfo> bedRoomList;
    ArrayList<BudgetPriceData> buyMinPriceList = new ArrayList<>();
    ArrayList<BudgetPriceData> rentMinPriceList = new ArrayList<>();
    ArrayList<BudgetPriceData> buyMaxPriceList = new ArrayList<>();
    ArrayList<BudgetPriceData> rentMaxPriceList = new ArrayList<>();
    ArrayList<BudgetPriceData> filterPriceList = new ArrayList<>();
    BudgetPriceData selectedMinBudgetPriceModel, selectedMaxBudgetPriceModel;
    TextView txtContinue, tvPropertyType, textBedRooms;
    EditText editLocality;
    AutoLabelUI autoLabelLocation;
    ProgressBar search_progress;
    private CardView searchResultLay;
    private RecyclerView searchResult;
    int maxPrice = 0, minPrice = 0;
    Dialog dialog;
    LinearLayout layoutEditRequirementPurpose;
    RelativeLayout layoutBudget;
    RadioGroup rgPropertyPurpose;
    public RadioButton rbBuy, rbRent;
    private static RequirementModel requirementModel;
    public static EditPostedRequirementsData editPostRequirementData = null;

    private CheckBox checkbox_mortage;

    private ArrayList<RegionData> regionData;
    private ArrayList<RegionData> regionDataTemp = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.rl_requirement_property_type_parent_layout));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_requirement_property_type);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow_black);

        declarations();


        if (!Utils.NoInternetConnection(ActivityRequirementPropertyType.this)) {
            getIndexValues();
        } else {
            Utils.showAlertNoInternet(ActivityRequirementPropertyType.this);
        }

        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity();
            }
        });

        rgPropertyPurpose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                propertyPurposeChange(checkedId);
            }
        });

    }

    private void bedroom() {
        if (GlobalValues.selectedBedrooms == null || GlobalValues.selectedBedrooms.size() == 0) {
            //  requirementModel.setReqBedroom("");
        } else {
            StringBuilder number = new StringBuilder();
            int size = GlobalValues.selectedBedrooms.size();
            for (int i = 0; i < size; i++) {
                if (size == 1) {
                    number.append(GlobalValues.selectedBedrooms.get(0));
                } else {
                    if (i == GlobalValues.selectedBedrooms.size() - 1) {
                        number.append(GlobalValues.selectedBedrooms.get(i));
                    } else {
                        number.append(GlobalValues.selectedBedrooms.get(i)).append(",");
                    }
                }
            }
            requirementModel.setReqBedroom(number.toString());
        }


    }

    public static ArrayList<String> selectedRegions = new ArrayList<>();
    public static ArrayList<String> selectedRegionsText = new ArrayList<>();

    public void checkRegionId() {
        selectedRegions.clear();
        for (RegionData child : regionDataTemp) {

            selectedRegions.add(child.getRegionID());
            selectedRegionsText.add(child.getLocationName());

        }

        if (selectedRegions != null) {
            if (selectedRegions.size() == 0) {
                requirementModel.setLocationList("");
            } else {
                StringBuilder number = new StringBuilder();
                int size = selectedRegions.size();
                for (int i = 0; i < size; i++) {
                    if (size == 1) {
                        number = new StringBuilder(selectedRegions.get(0));
                    } else {
                        if (i == 0) {
                            number = new StringBuilder(selectedRegions.get(i));
                        } else {
                            number.append(",").append(selectedRegions.get(i));
                        }
                    }
                }
                requirementModel.setLocationList(number.toString());
            }
        }
    }

    private void nextActivity() {
        if (checkbox_mortage.isChecked()) {
            requirementModel.setMortgage("Yes");
        } else {
            requirementModel.setMortgage("No");
        }
        if (requirementModel.getPropertyTypeParentID() != null &&
                !requirementModel.getPropertyTypeParentID().equals("")) {
            checkRegionId();
            bedroom();

            if (!check_price()) {

                if (requirementModel.getLocationList() != null &&
                        !requirementModel.getLocationList().equals("")) {
                    SessionManager.setPostRequirement(ActivityRequirementPropertyType.this, requirementModel);
                    startActivity(new Intent(ActivityRequirementPropertyType.this,
                            ActivityRequirementContact.class));
                } else {
                    Utils.showToastMessage(ActivityRequirementPropertyType.this,
                            getString(R.string.select_any_location)).show();
                }
            }

        } else {
            Utils.showToastMessage(ActivityRequirementPropertyType.this,
                    getString(R.string.select_any_property_type)).show();
        }
    }

    private boolean check_price() {
        boolean val = false;
        if (requirementModel.getMinPrice() == null || requirementModel.getMinPrice().isEmpty() ||
                requirementModel.getMaxPrice() == null || requirementModel.getMaxPrice().isEmpty()) {
            return true;
        }
        if (Integer.parseInt(requirementModel.getMinPrice().replace("+", "")) == 0) {
            Toast.makeText(this, "Please select a minimum price", Toast.LENGTH_SHORT).show();
            val = true;
        } else if (Integer.parseInt(requirementModel.getMaxPrice().replace("+", "")) == 0) {
            Toast.makeText(this, "Please select a maximum price", Toast.LENGTH_SHORT).show();
            val = true;
        }
        return val;
    }


    //TODO Set Data
    private void declarations() {
        layoutEditRequirementPurpose = findViewById(R.id.layout_property_purpose_edit_requirement);
        layoutEditRequirementPurpose.setVisibility(View.GONE);
        rgPropertyPurpose = findViewById(R.id.radio_group_purpose_post_requirement);
        rbBuy = findViewById(R.id.radio_button_sell_post_requirement);
        rbRent = findViewById(R.id.radio_button_rent_post_requirement);
        propertyTypeRecycler = (RecyclerView) findViewById(R.id.property_type_recycler_requirement_property_type);
        propertyTypeRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        txtContinue = (TextView) findViewById(R.id.text_continue_requirement_property_type);
        mLoading = new DialogProgress(ActivityRequirementPropertyType.this);
        requirementModel = SessionManager.getPostRequirement(ActivityRequirementPropertyType.this);
        requirementModel.setPropertyTypeIds(new ArrayList<String>());
        requirementModel.setPropertyTypeNames(new ArrayList<String>());
        layoutBudget = findViewById(R.id.layout_budget_post_requirement);
        layoutBudget.setVisibility(View.GONE);
        tvPropertyType = findViewById(R.id.tv_property_type);
        bedroomRecycler = (RecyclerView) findViewById(R.id.bedroom_recycler);
        textBedRooms = (TextView) findViewById(R.id.bedroom_text);
        editLocality = (EditText) findViewById(R.id.edit_locality_post_property);
        autoLabelLocation = (AutoLabelUI) findViewById(R.id.auto_label_location_buy);
        search_progress = (ProgressBar) findViewById(R.id.search_progress);
        searchResultLay = (CardView) findViewById(R.id.searchResultLay);
        searchResult = findViewById(R.id.searchResult);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchResult.setLayoutManager(mLayoutManager);
        GlobalValues.selectedPropertyType = "";
        GlobalValues.selectedPropertyTypeID = "";
        GlobalValues.selectedBedrooms = new ArrayList<>();
        GlobalValues.selectedBedroomID = new ArrayList<>();

        checkbox_mortage = findViewById(R.id.checkbox_mortage);
        priceRangeBar = findViewById(R.id.price_range_bar);
        trMinMaxPrice = findViewById(R.id.tr_min_max_price);
        tvMinPrice = findViewById(R.id.tv_min_price);
        tvMaxPrice = findViewById(R.id.tv_max_price);
        updateRangeBarProperties();

        Intent propertyPurposeIntent = getIntent();
        if (propertyPurposeIntent != null && propertyPurposeIntent.hasExtra("propertyPurpose")) {
            if (propertyPurposeIntent.getBooleanExtra("propertyPurpose", false)) {
                checkbox_mortage.setVisibility(View.GONE);
            }

        }
        bedRoomList = new ArrayList<>();
        tvPropertyType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPropertyTypeSpinner();
            }
        });

        editLocality.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String text = editable.toString().trim();
                Log.d("LENGTH12345", editable.toString().length() + "");
                if (text.length() > 0) {
                    getSearchResult(text, false);
                } else {
                    searchResultLay.setVisibility(View.GONE);
                    Log.d("TEXT_WATCHER_!@#", "got");
                }


            }
        });

        editLocality.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    if (editLocality.getText().toString().length() == 0) {
                        searchResultLay.setVisibility(View.GONE);
                        return true;
                    }
                }
                return false;
            }
        });

        editLocality.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (Utils.isValidName(editLocality.getText().toString().trim()) && !editLocality.getText().toString().equals("")) {
                        getSearchResult(editLocality.getText().toString(), true);
                    } else
                        Toast.makeText(ActivityRequirementPropertyType.this, getResources().getString(R.string.valid_data), Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });
        autoLabelLocation.setOnRemoveLabelListener(new AutoLabelUI.OnRemoveLabelListener() {
            @Override
            public void onRemoveLabel(View view, int position) {
                if (regionDataTemp.size() > position)
                    regionDataTemp.remove(position);
            }
        });
    }


    private void updatePriceFilterValue() {
        if (requirementModel.getPurpose().equals("Sell") || rbBuy.isChecked()) {
            filterPriceList = Utils.getBuyPriceList();
        } else {
            filterPriceList = rentMaxPriceList;
        }
        if (!filterPriceList.isEmpty()) {
            priceRangeBar.setVisibility(View.VISIBLE);
            trMinMaxPrice.setVisibility(View.VISIBLE);
            updatePriceValue();
        } else {
            priceRangeBar.setVisibility(View.GONE);
            trMinMaxPrice.setVisibility(View.GONE);
        }
    }

    private void updateRangeBarProperties() {
        priceRangeBar.setNotifyWhileDragging(true);
        priceRangeBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                updateThePriceSelection(minValue, maxValue);
            }
        });
    }

    private void updateThePriceSelection(int leftValue, int rightValue) {
        int size = filterPriceList.size() - 1;
        if (priceRangeBar.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            selectedMinBudgetPriceModel = filterPriceList.get(Math.abs(size - rightValue));
            selectedMaxBudgetPriceModel = filterPriceList.get(Math.abs(size - leftValue));
        } else {
            selectedMinBudgetPriceModel = filterPriceList.get(leftValue);
            selectedMaxBudgetPriceModel = filterPriceList.get(rightValue);
        }
        updatePreSelectedValueInField();
    }

    private void updatePriceValue() {
        priceRangeBar.setRangeValues(0, filterPriceList.size() - 1);
        updateThePriceSelection(0, filterPriceList.size() - 1);
    }

    private void updatePreSelectedValueInField() {
        tvMinPrice.setText(String.format(getString(R.string.min_price_with_value), selectedMinBudgetPriceModel != null ? selectedMinBudgetPriceModel.getPriceValue() : filterPriceList.get(0).getPrice()));
        tvMaxPrice.setText(String.format(getString(R.string.max_price_with_value), selectedMaxBudgetPriceModel != null ? selectedMaxBudgetPriceModel.getPriceValue() : filterPriceList.get(filterPriceList.size() - 1).getPrice()));
        requirementModel.setMinPrice(selectedMinBudgetPriceModel.getPrice());
        requirementModel.setMinPriceValue(selectedMinBudgetPriceModel.getPriceValue());
        minPrice = Integer.parseInt(selectedMinBudgetPriceModel.getPrice().replace("+", ""));
        requirementModel.setMaxPrice(String.valueOf(selectedMaxBudgetPriceModel.getPrice()));
        requirementModel.setMaxPriceValue(String.valueOf(selectedMaxBudgetPriceModel.getPriceValue()));
        maxPrice = selectedMaxBudgetPriceModel.getPrice().contains("+") ? -1 : getValueFromString(selectedMaxBudgetPriceModel.getPrice());
    }

    private void updateGlobalBudgetPriceData() {
        minPrice = Integer.parseInt(selectedMinBudgetPriceModel.getPrice().replace("+", ""));
        maxPrice = selectedMaxBudgetPriceModel.getPrice().contains("+") ? -1 : getValueFromString(selectedMaxBudgetPriceModel.getPrice());
        GlobalValues.selectedMinBudgetPriceModel = selectedMinBudgetPriceModel;
        GlobalValues.selectedMaxBudgetPriceModel = selectedMaxBudgetPriceModel;
        GlobalValues.selectedMinPrice = selectedMinBudgetPriceModel.getPriceValue().contains("+") ? "" : selectedMinBudgetPriceModel.getPrice();
        GlobalValues.selectedMinPriceValue = selectedMinBudgetPriceModel.getPriceValue().contains("+") ? "" : selectedMinBudgetPriceModel.getPriceValue();
        GlobalValues.selectedMaxPrice = selectedMaxBudgetPriceModel.getPriceValue().contains("+") ? "" : selectedMaxBudgetPriceModel.getPrice();
        GlobalValues.selectedMaxPriceValue = selectedMaxBudgetPriceModel.getPriceValue().contains("+") ? "" : selectedMaxBudgetPriceModel.getPriceValue();
    }

    private int getValueFromString(String value) {
        String removedValue = value.replace("+", "").replace(",", "");
        return Integer.parseInt(removedValue);
    }

    public void getSearchResult(final String query, final boolean buttonClick) {
        checkRegionId();
        search_progress.setVisibility(View.VISIBLE);
        ApiLinks.getClient().create(PostPropertyRegion1Api.class).post(SessionManager.getLanguageID(this),
                query, requirementModel.getLocationList())
                .enqueue(new Callback<PropertyRegionResponse>() {
                    @Override
                    public void onResponse(Call<PropertyRegionResponse> call, Response<PropertyRegionResponse> response) {
                        String message = response.body().getMessage();
                        if (response.body().getResponse().equalsIgnoreCase("Success")) {
                            regionData = response.body().getData();
                            setSearchResult(regionData);
                            searchResult.setVisibility(View.VISIBLE);
                        } else {
                            searchResultLay.setVisibility(View.GONE);
                            if (buttonClick)
                                Toast.makeText(ActivityRequirementPropertyType.this, message, Toast.LENGTH_SHORT).show();
                        }
                        search_progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<PropertyRegionResponse> call, Throwable t) {
                        search_progress.setVisibility(View.GONE);
                        Toast.makeText(ActivityRequirementPropertyType.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                    }
                });


    }

    public void setSearchResult(ArrayList<RegionData> regionData) {
        searchResultLay.setVisibility(View.VISIBLE);
        searchResult.setNestedScrollingEnabled(false);
        PostPropertyRegionAdapter postPropertyRegionAdapter = new PostPropertyRegionAdapter(regionData, ActivityRequirementPropertyType.this);
        searchResult.setAdapter(postPropertyRegionAdapter);
        postPropertyRegionAdapter.setSelectCity(new PostPropertyRegionAdapter.SelectCityInterface() {
            @Override
            public void onCitySelected(RegionData data) {
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
                editLocality.setText("");
                searchResultLay.setVisibility(View.GONE);
                regionDataTemp.add(data);
                autoLabelLocation.addLabel(data.getLocationName(), regionDataTemp.lastIndexOf(data));
            }
        });


    }

    private void showPropertyTypeSpinner() {
        dialog = new Dialog(this);
        @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.show_property_type_selector, null);
        final ExpandableListView listView = (ExpandableListView) view.findViewById(R.id.list_view_prop_type_selector);
        final AdapterTypeSelector adapter = new AdapterTypeSelector(ActivityRequirementPropertyType.this, propertyTypeList);
        TextView any = (TextView) view.findViewById(R.id.spinner_selection_any);
        listView.setAdapter(adapter);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.show();

        for (int i = 0; i < adapter.getGroupCount(); i++) {
            listView.expandGroup(i);
        }

        any.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPropertyType.setText(getResources().getString(R.string.any));
                requirementModel.setPropertyTypeParentID("");
                GlobalValues.selectedPropertyTypeID = "";
                GlobalValues.selectedPropertyType = "";
                GlobalValues.selectedBedrooms = new ArrayList<>();
                GlobalValues.selectedBedroomID = new ArrayList<>();

                GlobalValues.selectedBedroomsNumber = "";
                findViewById(R.id.bedroomLay).setVisibility(View.GONE);
                dialog.dismiss();
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
                PropertyTypeSub child = propertyTypeList.get(groupPosition).getSubCategory().get(childPosition);
                tvPropertyType.setText(child.getPropertyTypeName());
                dialog.dismiss();

                   /* if (!requirementModel.getPropertyTypeIds().contains(child.getPropertyTypeID())) {
                        requirementModel.getPropertyTypeIds().add(child.getPropertyTypeID());
                    }*/
                requirementModel.setPropertyTypeParentID(child.getPropertyTypeID());
                requirementModel.getPropertyTypeNames().add(child.getPropertyTypeName());
                GlobalValues.selectedBedrooms = new ArrayList<>();
                GlobalValues.selectedBedroomID = new ArrayList<>();

                GlobalValues.selectedBedroomsNumber = "";
                getBedRoomNumbers();
                return false;
            }
        });
    }

    public void getBedRoomNumbers() {
        mLoading.show();
        ApiLinks.getClient().create(BedRoomListApi.class)
                .post(requirementModel.getPropertyTypeParentID(),SessionManager.getLanguageID(ActivityRequirementPropertyType.this))
                .enqueue(new Callback<BedRoomListResponse>() {
                    @Override
                    public void onResponse(Call<BedRoomListResponse> call, Response<BedRoomListResponse> response) {
                        if (response.isSuccessful()) {
                            mLoading.dismiss();
                            String message = response.body().getMessage();
                            if (response.body().getResponse().equals("Success")) {
                                bedRoomList = response.body().getData().getBedroom();
                                setBedRoomRecycler();
                            } else {
                                findViewById(R.id.bedroomLay).setVisibility(View.GONE);

                            }
                        } else {
                            findViewById(R.id.bedroomLay).setVisibility(View.GONE);
                            mLoading.dismiss();
                            Log.v("getBedRoomNumbers", "no response");
                        }
                    }

                    @Override
                    public void onFailure(Call<BedRoomListResponse> call, Throwable t) {
                        mLoading.dismiss();
                        Log.v("getBedRoomNumbers", "no response");
                    }
                });
    }

    String selectedBedroomsID = "";

    public void setBedRoomRecycler() {
        BedRoomAdapter bedRoomAdapter = new BedRoomAdapter(bedRoomList);
        bedroomRecycler.setAdapter(bedRoomAdapter);
        findViewById(R.id.bedroomLay).setVisibility(View.VISIBLE);
        selectedBedroomsID = bedRoomList.get(0).getAttributeID();
    }

    private void propertyPurposeChange(int checkedId) {
        switch (checkedId) {
            case R.id.radio_button_sell_post_requirement:
                rbRent.setChecked(false);
                updatePriceFilterValue();
                minPrice = 0;
                maxPrice = 0;
                requirementModel.setMinPrice("");
                requirementModel.setMinPriceValue("");
                requirementModel.setMaxPrice("");
                requirementModel.setMaxPriceValue("");
                requirementModel.setPurpose("Sell");
                break;
            case R.id.radio_button_rent_post_requirement:
                rbBuy.setChecked(false);
                updatePriceFilterValue();
                minPrice = 0;
                maxPrice = 0;
                requirementModel.setMinPrice("");
                requirementModel.setMinPriceValue("");
                requirementModel.setMaxPrice("");
                requirementModel.setMaxPriceValue("");
                requirementModel.setPurpose("Rent");
                break;
        }
    }

    private void setEditDataToUI() {
        layoutBudget.setVisibility(View.VISIBLE);
        layoutEditRequirementPurpose.setVisibility(View.VISIBLE);
        if (editPostRequirementData.getReqPurpose().equals("Sell")) {
            rbBuy.setChecked(true);
            rbRent.setChecked(false);
        } else {
            rbBuy.setChecked(false);
            rbRent.setChecked(true);
        }
        if (editPostRequirementData.getPropertyTypeID() != null && !editPostRequirementData.getPropertyTypeID().equals("")) {
            requirementModel.setPropertyTypeParentID(editPostRequirementData.getPropertyTypeID());
            getBedRoomNumbers();
        }

        if (editPostRequirementData.getPropertyTypeName() != null && !editPostRequirementData.getPropertyTypeName().equals("")) {
            tvPropertyType.setText(editPostRequirementData.getPropertyTypeName());
        }

        requirementModel.setFeaturesSizeList(new ArrayList<Integer>());
        requirementModel.setAttributeList(new ArrayList<AllAttributesData>());
        if (editPostRequirementData.getCityLocName() != null && editPostRequirementData.getCityLocName().size() > 0) {
            regionDataTemp.addAll(editPostRequirementData.getCityLocName());
            for (int i = 0; i < regionDataTemp.size(); i++) {
                autoLabelLocation.addLabel(regionDataTemp.get(i).getLocationName(), i);
            }
        }

        if (editPostRequirementData.getPropertyTypeID() != null && !editPostRequirementData.getPropertyTypeID().equals("")) {
            String[] strings = editPostRequirementData.getPropertyTypeID().split(",");
            requirementModel.setPropertyTypeIds(new ArrayList<>(Arrays.asList(strings)));
            requirementModel.setPropertyTypeIdsEdit(new ArrayList<>(Arrays.asList(strings)));
        }

        if (editPostRequirementData.getReqBedrooms() != null && !editPostRequirementData.getReqBedrooms().equals("")) {
            String[] strings = editPostRequirementData.getReqBedrooms().split(",");
            requirementModel.setReqBedroom(editPostRequirementData.getReqBedrooms());
            GlobalValues.selectedBedrooms.addAll(new ArrayList<>(Arrays.asList(strings)));
        }

        requirementModel.setPurpose(editPostRequirementData.getReqPurpose());
        requirementModel.setMinPrice(editPostRequirementData.getReqMinPrice());
        requirementModel.setMaxPrice(editPostRequirementData.getReqMaxPrice());
        requirementModel.setLocationList(editPostRequirementData.getReqLocalityIDs());
        requirementModel.setReqBedroom(editPostRequirementData.getReqBedrooms());

        requirementModel.setCountryCode(editPostRequirementData.getCountryCode());
        requirementModel.setName(editPostRequirementData.getReqName());
        requirementModel.setEmail(editPostRequirementData.getReqEmail());
        requirementModel.setPhone(editPostRequirementData.getReqPhone());
        updatePriceBarAndFieldWhileEdit();
    }

    private void updatePriceBarAndFieldWhileEdit() {
        int min = 0, max = filterPriceList.size() - 1;
        for (BudgetPriceData budgetPriceData : filterPriceList) {
            try {
                if (requirementModel.getMinPrice() != null && !requirementModel.getMinPrice().equals("")) {
                    int lhsValue = Integer.parseInt(budgetPriceData.getPrice().replace("+", ""));
                    int rhsValue = Integer.parseInt(requirementModel.getMinPrice().replace("+", ""));
                    if (lhsValue == rhsValue) {
                        min = filterPriceList.indexOf(budgetPriceData);
                    }
                }
                if (requirementModel.getMaxPrice() != null && !requirementModel.getMaxPrice().equals("")) {
                    int lhsValue = Integer.parseInt(budgetPriceData.getPrice().replace("+", ""));
                    int rhsValue = Integer.parseInt(requirementModel.getMaxPrice().replace("+", ""));
                    if (lhsValue == rhsValue) {
                        max = filterPriceList.indexOf(budgetPriceData);
                    }
                }
            } catch (Exception e) {
                FirebaseCrashlytics.getInstance().recordException(e);
            }
        }
        priceRangeBar.setSelectedMinValue(min);
        priceRangeBar.setSelectedMaxValue(max);
        updateThePriceSelection(min, max);
    }


    //TODO API Calls
    public void getIndexValues() {
        mLoading.show();
        ApiLinks.getClient().create(IndexBuyRentApi.class)
                .post(requirementModel.getPurpose(), GlobalValues.countryID, SessionManager.getLanguageID(this))
                .enqueue(new Callback<IndexBuyRentResponse>() {
                    @Override
                    public void onResponse(Call<IndexBuyRentResponse> call, Response<IndexBuyRentResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String status = response.body().getResponse();
                            int code = response.body().getCode();
                            if (code == 100 && status.equalsIgnoreCase("Success")) {
                                propertyTypeList = response.body().getData().getPropertyType();
                                buyMinPriceList = response.body().getData().getBuyMinPrice();
                                buyMaxPriceList = response.body().getData().getBuyMaxPrice();
                                rentMaxPriceList = response.body().getData().getRentMaxPrice();
                                rentMinPriceList = response.body().getData().getRentMinPrice();
                                imagePath = response.body().getData().getImagePath();
                                updatePriceFilterValue();
                                layoutBudget.setVisibility(View.VISIBLE);
                                if (requirementModel.getRequirementAction().equals("post")) {
                                    if (mLoading != null) {
                                        mLoading.dismiss();
                                    }
                                } else {
                                    getEditRequirementData();
                                }
                            } else {
                                Toast.makeText(ActivityRequirementPropertyType.this,
                                        message, Toast.LENGTH_SHORT).show();
                                mLoading.dismiss();
                            }
                        } else {
                            mLoading.dismiss();
                            Toast.makeText(ActivityRequirementPropertyType.this,
                                    getString(R.string.failed), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<IndexBuyRentResponse> call, Throwable t) {
                        mLoading.dismiss();
                        Toast.makeText(ActivityRequirementPropertyType.this,
                                getString(R.string.failed), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getEditRequirementData() {
        mLoading.show();
        ApiLinks.getClient().create(EditPostedRequirementsApi.class)
                .post(SessionManager.getAccessToken(ActivityRequirementPropertyType.this),
                        requirementModel.getRequirementID())
                .enqueue(new Callback<EditPostedRequirementsResponse>() {
                    @Override
                    public void onResponse(Call<EditPostedRequirementsResponse> call,
                                           Response<EditPostedRequirementsResponse> response) {
                        if (response.isSuccessful()) {
                            String status = response.body().getResponse();
                            if (status.equalsIgnoreCase("Success")) {
                                editPostRequirementData = response.body().getData();
                                setEditDataToUI();
                            }
                        } else {
                            Log.v("getEditRequirementData", "Failed");
                        }
                        mLoading.dismiss();
                    }

                    @Override
                    public void onFailure(Call<EditPostedRequirementsResponse> call, Throwable t) {
                        Log.v("getEditRequirementData", t.getMessage());
                        mLoading.dismiss();
                    }
                });
    }


    //TODO Main Functions
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
