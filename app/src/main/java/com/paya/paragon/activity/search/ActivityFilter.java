package com.paya.paragon.activity.search;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dpizarro.autolabel.library.AutoLabelUI;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.paya.paragon.R;
import com.paya.paragon.activity.login.ActivityLoginEmail;
import com.paya.paragon.adapter.AdapterCityListing;
import com.paya.paragon.adapter.AdapterPropertyTypeSelector;
import com.paya.paragon.adapter.AdapterSpecificationsFilter;
import com.paya.paragon.adapter.BedRoomAdapter;
import com.paya.paragon.adapter.CityAdapter;
import com.paya.paragon.adapter.SearchResultAdapter;
import com.paya.paragon.api.PayaAPICall;
import com.paya.paragon.api.bedRoomList.BedRoomInfo;
import com.paya.paragon.api.bedRoomList.BedRoomListApi;
import com.paya.paragon.api.bedRoomList.BedRoomListResponse;
import com.paya.paragon.api.budgetList.BudgetPriceData;
import com.paya.paragon.api.cityList.CityListingApi;
import com.paya.paragon.api.cityList.CityListingResponse;
import com.paya.paragon.api.filterAttributeListing.FilterAttributeListingApi;
import com.paya.paragon.api.filterAttributeListing.FilterAttributeListingResponse;
import com.paya.paragon.api.index.IndexBuyRentApi;
import com.paya.paragon.api.index.IndexBuyRentResponse;
import com.paya.paragon.api.index.LocationInfo;
import com.paya.paragon.api.index.PropertyTypeMain;
import com.paya.paragon.api.index.PropertyTypeSub;
import com.paya.paragon.api.listLocProject.ListLocProjApi;
import com.paya.paragon.api.listLocProject.ListLocProjectData;
import com.paya.paragon.api.listLocProject.ListLocProjectResponse;
import com.paya.paragon.api.listLocProject.RegionDataChild;
import com.paya.paragon.api.listLocProject.SearchDataLocProj;
import com.paya.paragon.api.postProperty.attributeListing.AllAttributesData;
import com.paya.paragon.api.saveSearch.SaveSearchApi;
import com.paya.paragon.api.saveSearch.SaveSearchResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.CustomExpandable;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.ExtensionKt;
import com.paya.paragon.utilities.PropertyProjectType;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;
import com.paya.paragon.utilities.custompricerange.RangeSeekBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@SuppressWarnings({"HardCodedStringLiteral", "RedundantCast", "NullableProblems", "ConstantConditions"})
public class ActivityFilter extends AppCompatActivity {

//    private boolean isNeighborhoodSelected = false;

    LinearLayout layoutProgress, propertyTypeHolder, layoutPossession, mPropertyTypeDDLay;
    TextView searchBuy, textPropertyType, textBedRooms, buyText, rentText, selectorPropertyType;
    private TextView rbStatusUpComing, rbStatusReadyToMove, rbStatusUnderConstruction;
    TextView bedroom_text, resetFilter, saveSearch;
    EditText clearFocus, searchEditText;
    TextView tvMinPrice, tvMaxPrice, cityListtv;
    TableRow trMinMaxPrice;
    RangeSeekBar<Integer> priceRangeBar;
    ExpandableListView mPropertyTypeListView;
    TextView AllTv;
    DialogProgress mLoading;
    AutoCompleteTextView editLocations;
    public Dialog alertDialog;
    ProgressBar search_progress;
    NestedScrollView mNestedScrollView;
    RecyclerView recyclerSpecifications;
    private AutoLabelUI autoLabelLocation;
    private CardView searchResultLay;
    private CustomExpandable searchResult;
    RecyclerView bedroomRecycler;
    private TextView text_city_list;
    RecyclerView mCityRcv;
    CityAdapter cityAdapter;

    private static String[] locationList;
    ArrayAdapter<String> adapterLocations;
    private List<String> locations;
    ArrayList<LocationInfo> locationInfoList;
    ArrayList<PropertyTypeMain> propertyTypeList;
    ArrayList<BedRoomInfo> bedRoomList;
    ArrayList<BudgetPriceData> buyMinPriceList, rentMinPriceList;
    ArrayList<BudgetPriceData> buyMaxPriceList, rentMaxPriceList;
    ArrayList<BudgetPriceData> filterPriceList;
    BudgetPriceData selectedMinBudgetPriceModel, selectedMaxBudgetPriceModel;
    ArrayList<AllAttributesData> allAttributesList = new ArrayList<>();
    private ArrayList<SearchDataLocProj> searchDataLocProjs;
    private ArrayList<LocationInfo> locationInfo;
    private ArrayList<LocationInfo> searchedLocatinInfo;

    private ListLocProjectData locProjectData;
    private RegionDataChild regionDataChild;

    boolean buySelected = false;
    int maxPrice = 0, minPrice = 0;
    String selectedLocation = "", imagePath = "", userID = "", searchTitle = "";
    String lastSelected = "buy", selectedType = "Sell", strPropertyCurrentStatus = "";
    private String selectedCityID = "";
    boolean isPropertyTabSelected;
    Boolean mCityDropdownClicked = false, mPropertyTypeDropdownClicked = false, mNeighbourhoodClicked = false;

    AdapterSpecificationsFilter adapterSpecifications;
    AdapterCityListing adapter;


    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.rl_filter_parent_layout));
    }

    @SuppressLint({"NewApi", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_filter);
        /*Toolbar Setup*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        mTitle.setText(R.string.select_filters);
        mTitle.setTypeface(mTitle.getTypeface(), Typeface.BOLD);
        GlobalValues.clearBuyGlobalValues();
        declarations();

        /*Search Button*/
        searchBuy.setOnClickListener(v -> actionSearchFilter());

        /*Internet connection check*/
        if (!Utils.NoInternetConnection(ActivityFilter.this)) {
            getIndexValues();
            getBedRoomNumbers();
            if (GlobalValues.selectedPropertyTypeID != null
                    && !GlobalValues.selectedPropertyTypeID.equalsIgnoreCase("")) {
                allAttributesList = (ArrayList<AllAttributesData>) getIntent().getSerializableExtra("attributes");
                if (allAttributesList.size() == 0) {
                    getAttributeList(GlobalValues.selectedPropertyTypeID);
                } else {
                    setAllAttributesDataToUI();
                }
            }
        } else {
            Utils.showAlertNoInternet(ActivityFilter.this);
        }

        /*Neighbourhood List ACP*/
        editLocations.setOnItemClickListener((parent, view, position, id) -> {
            closeKeyboard();
            selectedLocation = adapterLocations.getItem(position);
            locations.remove(selectedLocation);
            adapterLocations.remove(selectedLocation);
            for (LocationInfo info : locationInfoList) {
                if (info.getCityName().equals(selectedLocation)) {
                    GlobalValues.selectedLocationID = info.getCityID();
                    GlobalValues.selectedLocation = info.getCityName();
                }
            }
            autoLabelLocation.addLabel(selectedLocation);
            editLocations.setText("");
        });

        searchEditText.setOnTouchListener((view, motionEvent) -> {
            if (searchResultLay.getVisibility() == View.VISIBLE) {
                searchEditText.clearFocus();
//                closeKeyboard();
                ExtensionKt.hideKeyboard(searchEditText);
                return true;
            }
            return false;
        });


        searchEditText.setOnFocusChangeListener((view, b) -> {
            if (b) {
                if (searchResultLay.getVisibility() == View.VISIBLE) {
                    searchResultLay.setVisibility(View.GONE);
                } else {
                    if (locProjectData == null)
                        getSearchResult("", false, 1, false);
                    else {
                        if (GlobalValues.regionDataTemp.isEmpty())
                            setSearchResult(locProjectData);
                        else {
                            getSearchResult("", false, 1, false);
                        }

                    }
                }
            } else {
                searchResultLay.setVisibility(View.GONE);
                ExtensionKt.hideKeyboard(searchEditText);
            }
        });


        /*Neighbourhood Search*/
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                Timber.tag("LENGTH12345").d("%s", editable.toString().length());
                if (text.length() > 0) {
                    getSearchResult(text, false, 1, true);
                       /* if (text.charAt(text.length() - 1) == ' ') {
                            //searchByQuery(text);
                        } else {
                            //searchResult.setVisibility(View.GONE);
                        }*/
                } else {
                    searchResultLay.setVisibility(View.GONE);
                    Timber.tag("TEXT_WATCHER_!@#").d("got");
                    ExtensionKt.hideKeyboard(searchEditText);
                }
                /*if (!isNeighborhoodSelected) {
                    isNeighborhoodSelected = false;
                    getSearchResult(text, false, 1, true);
                }*/
            }
        });

        searchEditText.setOnKeyListener((view, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_DEL) {
                if (searchEditText.getText().toString().length() == 0) {
                    searchResultLay.setVisibility(View.GONE);
                    return true;
                }
            }
            return false;
        });

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (Utils.isValidName(searchEditText.getText().toString()) && !searchEditText.getText().toString().equals("")) {
                    getSearchResult(searchEditText.getText().toString(), true, 0, false);
                } else
                    Toast.makeText(ActivityFilter.this, getResources().getString(R.string.valid_data), Toast.LENGTH_LONG).show();
                return true;
            }
            return false;
        });

        /*Property Type*/
        selectorPropertyType.setOnClickListener(v -> {
            if (!mPropertyTypeDropdownClicked) {
                showPropertyTypeDropdown();
                mPropertyTypeDropdownClicked = true;
            } else {
                mPropertyTypeDDLay.setVisibility(View.GONE);
                mPropertyTypeDropdownClicked = false;
            }
        });

        /*Save Search*/
        saveSearch.setOnClickListener(v -> {
            if (SessionManager.getLoginStatus(ActivityFilter.this)) {
                alertSaveSearch();
            } else {
                startActivity(new Intent(ActivityFilter.this, ActivityLoginEmail.class));
            }
        });
        /*Reset Filter*/
        resetFilter.setOnClickListener(v -> actionResetFilter());

    }

    public void showPropertyTypeDropdown() {
        mPropertyTypeDDLay.setVisibility(View.VISIBLE);
        AdapterPropertyTypeSelector adapter = new AdapterPropertyTypeSelector(this, propertyTypeList);
        mPropertyTypeListView.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            mPropertyTypeListView.expandGroup(i);
        }
        AllTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectorPropertyType.setText(getResources().getString(R.string.any));
                mPropertyTypeDDLay.setVisibility(View.GONE);
                mPropertyTypeDropdownClicked = false;
                GlobalValues.selectedPropertyTypeID = null;
                GlobalValues.selectedPropertyType = null;
            }
        });

        mPropertyTypeListView.setOnGroupClickListener((expandableListView, view, i, l) -> false);

        mPropertyTypeListView.setOnChildClickListener((expandableListView, view, groupPosition, childPosition, l) -> {
            PropertyTypeSub child = propertyTypeList.get(groupPosition).getSubCategory().get(childPosition);
            selectorPropertyType.setText(child.getPropertyTypeName());
            mPropertyTypeDDLay.setVisibility(View.GONE);
            mPropertyTypeDropdownClicked = false;


            GlobalValues.savedAttributes = new ArrayList<>();
            if (GlobalValues.selectedPropertyTypeID != null
                    && !GlobalValues.selectedPropertyTypeID.equalsIgnoreCase("")) {
                if (!GlobalValues.selectedPropertyTypeID.equalsIgnoreCase(child.getPropertyTypeID())) {
                    getAttributeList(child.getPropertyTypeID());
                    GlobalValues.selectedPropertyTypeID = child.getPropertyTypeID();
                    GlobalValues.selectedPropertyType = child.getPropertyTypeName();
                    if (GlobalValues.selectedBedrooms != null) {
                        GlobalValues.selectedBedrooms.clear();
                        GlobalValues.selectedBedroomID.clear();
                    }
                }
            } else {
                GlobalValues.selectedPropertyTypeID = child.getPropertyTypeID();
                GlobalValues.selectedPropertyType = child.getPropertyTypeName();
                getAttributeList(child.getPropertyTypeID());
                if (GlobalValues.selectedBedrooms != null) {
                    GlobalValues.selectedBedrooms.clear();
                    GlobalValues.selectedBedroomID.clear();

                }
            }
            if (child.getPropertyTypeName().contains("Land") || child.getPropertyTypeName().contains("Lands")
                    || child.getPropertyTypeName().contains("Plot") || child.getPropertyTypeName().contains("Plots")) {
                layoutPossession.setVisibility(View.GONE);
                strPropertyCurrentStatus = "";
                bedRoomList.clear();
                bedroom_text.setVisibility(View.GONE);
                bedroomRecycler.setVisibility(View.GONE);


            } else {
                if (!isPropertyTabSelected) {
                    layoutPossession.setVisibility(View.VISIBLE);
                }
                strPropertyCurrentStatus = "";
                getBedRoomNumbers();

            }
            return false;
        });

    }

    private void actionResetFilter() {
        GlobalValues.clearBuyGlobalValues();
        allAttributesList = new ArrayList<>();
        GlobalValues.regionDataTemp.clear();
        autoLabelLocation.clear();
        declarations();
        setAllAttributesDataToUI();
        if (!Utils.NoInternetConnection(ActivityFilter.this)) {
            getIndexValues();
            getBedRoomNumbers();
        } else {
            Utils.showAlertNoInternet(ActivityFilter.this);
        }
        selectorPropertyType.setText(getString(R.string.select));
    }

    //TODO API Calls
    public void getIndexValues() {
        mLoading.show();
        ApiLinks.getClient().create(IndexBuyRentApi.class)
                .post("",
                        GlobalValues.countryID,
                        SessionManager.getLanguageID(this)
                ).enqueue(new Callback<IndexBuyRentResponse>() {
                    @Override
                    public void onResponse(Call<IndexBuyRentResponse> call, Response<IndexBuyRentResponse> response) {

                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            int code = response.body().getCode();
                            if (code == 100) {
                                GlobalValues.propertyTypes = new ArrayList<>();
                                GlobalValues.propertyTypes = response.body().getData().getPropertyType();
                                locationInfoList = response.body().getData().getLocations();
                                ArrayList<String> locArrayList = new ArrayList<>();
                                for (LocationInfo info : locationInfoList) {
                                    locArrayList.add(info.getCityName());
                                }
                                locationList = locArrayList.toArray(new String[locArrayList.size()]);
                                locations = new ArrayList<>(Arrays.asList(locationList));
                                propertyTypeList = response.body().getData().getPropertyType();
                                buyMinPriceList = response.body().getData().getBuyMinPrice();
                                buyMaxPriceList = response.body().getData().getBuyMaxPrice();
                                rentMaxPriceList = response.body().getData().getRentMaxPrice();
                                rentMinPriceList = response.body().getData().getRentMinPrice();
                                imagePath = response.body().getData().getImagePath();

                                for (int i = 0; i < propertyTypeList.size(); i++) {
                                    for (int j = 0; j < propertyTypeList.get(i).getSubCategory().size(); j++) {
                                        if (GlobalValues.selectedPropertyTypeID != null) {
                                            if (!GlobalValues.selectedPropertyTypeID.equals("") &&
                                                    GlobalValues.selectedPropertyTypeID.equals(propertyTypeList.get(i).getSubCategory().get(j).getPropertyTypeID())) {
                                                selectorPropertyType.setText(propertyTypeList.get(i).getSubCategory().get(j).getPropertyTypeName());

                                            }
                                        }

                                    }
                                }
                                Collections.sort(locations);
                                Arrays.sort(locationList);
                                Collections.sort(locationInfoList, new Comparator<LocationInfo>() {
                                    @Override
                                    public int compare(LocationInfo l1, LocationInfo l2) {
                                        return l1.getCityName().compareToIgnoreCase(l2.getCityName());
                                    }
                                });
                            } else {
                                Toast.makeText(ActivityFilter.this, message, Toast.LENGTH_SHORT).show();
                            }
                            mLoading.dismiss();
                            layoutProgress.setVisibility(View.GONE);
                            updatePriceFilterValue();
                            if (buySelected) {
                                if (!isPropertyTabSelected) {
                                    Utils.expand(layoutPossession, 200);
                                }
                            } else {
                                Utils.collapse(layoutPossession, 200);
                            }
                            setAdapters();
                        } else {
                            layoutProgress.setVisibility(View.GONE);
                            mLoading.dismiss();
                            Toast.makeText(ActivityFilter.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<IndexBuyRentResponse> call, Throwable t) {
                        mLoading.dismiss();
                        Toast.makeText(ActivityFilter.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void getAttributeList(String propertyTypeID) {
        mLoading.show();
        ApiLinks.getClient().create(FilterAttributeListingApi.class).post(propertyTypeID, SessionManager.getLanguageID(ActivityFilter.this))
                .enqueue(new Callback<FilterAttributeListingResponse>() {
                    @Override
                    public void onResponse(Call<FilterAttributeListingResponse> call,
                                           Response<FilterAttributeListingResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String status = response.body().getResponse();
                            //int code = response.body().getCode();
                            if (status.equalsIgnoreCase("Success")) {
                                allAttributesList = response.body().getData().getAllAttributes();
                            } else {
                                Log.v("getAttributeList", message);
                                allAttributesList = new ArrayList<>();
                            }
                            mLoading.dismiss();
                        } else {
                            allAttributesList = new ArrayList<>();

                            mLoading.dismiss();
                            Toast.makeText(ActivityFilter.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                        setAllAttributesDataToUI();
                    }

                    @Override
                    public void onFailure(Call<FilterAttributeListingResponse> call, Throwable t) {
                        mLoading.dismiss();
                        Log.v("getAttributeList", t.getMessage());
                        Toast.makeText(ActivityFilter.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getBedRoomNumbers() {
        bedRoomList.clear();
        mLoading.show();
        ApiLinks.getClient().create(BedRoomListApi.class)
                .post(GlobalValues.selectedPropertyTypeID, SessionManager.getLanguageID(ActivityFilter.this)).enqueue(new Callback<BedRoomListResponse>() {
                    @Override
                    public void onResponse(Call<BedRoomListResponse> call, Response<BedRoomListResponse> response) {
                        if (response.isSuccessful()) {
                            int code = response.body().getCode();
                            if (code == 100) {
                                bedRoomList = response.body().getData().getBedroom();
                            } else {
                                Log.v("getBedRoomNumbers", "No data");
                            }
                            setBedRoomRecycler();

                            mLoading.dismiss();
                        } else {
                            setBedRoomRecycler();
                            mLoading.dismiss();
                            Log.v("getBedRoomNumbers", "No data");
                        }
                    }

                    @Override
                    public void onFailure(Call<BedRoomListResponse> call, Throwable t) {
                        mLoading.dismiss();
                        Log.v("getBedRoomNumbers", "No data");
                    }
                });
    }

    private void saveSearchToServer(final Dialog saveDialog, final TextView saveSearchIcon,
                                    final EditText editTitle, final TextView saveSearch, final TextView message) {
        checkRegionId();
        StringBuilder regionList = new StringBuilder();
        if (GlobalValues.selectedRegions != null && GlobalValues.selectedRegions.size() > 0) {
            for (String region : GlobalValues.selectedRegions) {
                if (regionList.toString().equals("")) {
                    regionList = new StringBuilder(region);
                } else {
                    regionList.append(",").append(region);
                }
            }
        }
        if (GlobalValues.selectedBedroomsNumber != null) {
            if (GlobalValues.selectedBedrooms == null || GlobalValues.selectedBedrooms.size() == 0) {
                GlobalValues.selectedBedroomsNumber = "";
            } else {
                StringBuilder number = new StringBuilder(GlobalValues.selectedBedroomsID + "~");
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
                GlobalValues.selectedBedroomsNumber = number.toString();
            }
        }
        StringBuilder strAttributes = new StringBuilder();
        if (allAttributesList != null && allAttributesList.size() != 0) {
            ArrayList<AllAttributesData> allAttributes = allAttributesList;
            for (int i = 0; i < allAttributes.size(); i++) {
                if (allAttributes.get(i).getPropertyAttrSelectedOptionID() != null) {
                    if (!allAttributes.get(i).getPropertyAttrSelectedOptionID().equals("")
                            && !allAttributes.get(i).getPropertyAttrSelectedOptionID().equals("0")) {
                        if (strAttributes.toString().equals("")) {
                            strAttributes = new StringBuilder(allAttributes.get(i).getAttrID() +
                                    "~" + allAttributes.get(i).getPropertyAttrSelectedOptionID());

                        } else {
                            strAttributes.append(":").append(allAttributes.get(i).getAttrID()).append("~")
                                    .append(allAttributes.get(i).getPropertyAttrSelectedOptionID());

                        }

                    }
                }
            }
        }

            /*if (!strAttributes.toString().equals("")) {
                if (GlobalValues.selectedBedroomsNumber != null) {
                    if (GlobalValues.selectedBedroomsNumber.equals("")) {
                        GlobalValues.selectedBedroomsNumber = strAttributes.toString();
                    } else {
                        GlobalValues.selectedBedroomsNumber += ":" + strAttributes;
                    }
                } else {
                    GlobalValues.selectedBedroomsNumber = strAttributes.toString();
                }
            }*/

        mLoading.show();
        ApiLinks.getClient().create(SaveSearchApi.class)
                .post(userID,
                        "" + SessionManager.getLanguageID(ActivityFilter.this),
                        "en",
                        GlobalValues.countryID,
                        "india",
                        selectedType,
                        "",
                        "",
                        regionList.toString(),
                        GlobalValues.selectedMinPrice,
                        GlobalValues.selectedMaxPrice,
                        GlobalValues.selectedPropertyTypeID,
                        GlobalValues.selectedBedroomsNumber,
                        "normal",
                        selectedCityID,
                        searchTitle
                )
                .enqueue(new Callback<SaveSearchResponse>() {
                    @Override
                    public void onResponse(Call<SaveSearchResponse> call,
                                           Response<SaveSearchResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if ("Success".equalsIgnoreCase(response.body().getResponse())) {
                                mLoading.dismiss();
                                saveSearchIcon.setCompoundDrawablesRelativeWithIntrinsicBounds(0,
                                        R.drawable.icon_alert_save_search_done, 0, 0);
                                editTitle.setVisibility(View.GONE);
                                saveSearch.setVisibility(View.GONE);
                                message.setText(R.string.alert_text_search_saved);
                            } else {
                                mLoading.dismiss();
                                alertDialog.dismiss();
                                Toast.makeText(ActivityFilter.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            mLoading.dismiss();
                            saveDialog.dismiss();
                            Toast.makeText(ActivityFilter.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SaveSearchResponse> call, Throwable t) {
                        mLoading.dismiss();
                        saveDialog.dismiss();
                        Toast.makeText(ActivityFilter.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("NewApi")
    public void setSearchResult(ListLocProjectData locProjectData) {

        SearchDataLocProj searchDataLocProj = new SearchDataLocProj();
        SearchDataLocProj searchDataLocProj1 = new SearchDataLocProj();
        SearchDataLocProj searchDataLocProj2 = new SearchDataLocProj();
        searchDataLocProjs = new ArrayList<>();

        if (locProjectData.getLocation().size() > 0) {
            searchDataLocProj.setName("Community");
            searchDataLocProj.setChild(locProjectData.getLocation());
            searchDataLocProjs.add(searchDataLocProj);
        }
        if (locProjectData.getProjects().size() > 0) {
            searchDataLocProj1.setName("Projects");
            searchDataLocProj1.setChild(locProjectData.getProjects());
            searchDataLocProjs.add(searchDataLocProj1);
        }
        searchResultLay.setVisibility(View.VISIBLE);
        searchResult.setNestedScrollingEnabled(false);
        final SearchResultAdapter searchResultAdapter = new SearchResultAdapter(ActivityFilter.this,
                searchDataLocProjs);
        searchResult.setAdapter(searchResultAdapter);

        for (int i = 0; i < searchResultAdapter.getGroupCount(); i++)
            searchResult.expandGroup(i);

        searchResult.setOnGroupClickListener((parent, v, groupPosition, id) -> parent.isGroupExpanded(groupPosition));

        searchResult.setOnChildClickListener((expandableListView, view, i, i1, l) -> {
            regionDataChild = searchResultAdapter.getChild(i, i1);
            if (regionDataChild != null) {
                searchEditText.clearFocus();
                searchEditText.setText("");
                searchResultLay.setVisibility(View.GONE);
                closeKeyboard();
                ExtensionKt.hideKeyboard(searchEditText);
                GlobalValues.regionDataTemp.add(regionDataChild);
                searchDataLocProjs.clear();
                autoLabelLocation.addLabel(regionDataChild.getOriginalText(), GlobalValues.regionDataTemp.lastIndexOf(regionDataChild));
//                    selectorPropertyType.performClick();
            }
            return false;
        });
    }

    private void showHideNeighborHoodProgress(Boolean show) {
        search_progress.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    @SuppressWarnings("unused")
    public void getSearchResult(final String query, final boolean buttonClick, int value, boolean fromCitySelectionOrTyping) {
        if (!fromCitySelectionOrTyping) showHideNeighborHoodProgress(true);
        checkRegionId();
        String location_id = "2";
        if (!SessionManager.getLocationId(this).equals("-1"))
            location_id = SessionManager.getLocationId(this);
        Timber.tag("search").d(GlobalValues.countryID + " " + query + " " + location_id + " " + GlobalValues.selectedRegionId);
        ApiLinks.getClient().create(ListLocProjApi.class).post(
                        GlobalValues.countryID,
                        query,
                        location_id,
                        GlobalValues.selectedRegionId
                        )
                .enqueue(new Callback<ListLocProjectResponse>() {
                    @Override
                    public void onResponse(Call<ListLocProjectResponse> call, Response<ListLocProjectResponse> response) {
                        if (response != null && response.body() != null) {
                            String message = response.body().getMessage() != null ? response.body().getMessage() : "";
                            if (response.body().getResponse().equalsIgnoreCase("Success")) {

                                locProjectData = response.body().getData();
                                RegionDataChild dataChild = new RegionDataChild();
                                dataChild.setOriginalText(getResources().getString(R.string.all_nbh));
                                dataChild.setId("00");
                                List<RegionDataChild> allNbhList;
                                allNbhList = locProjectData.getLocation();
                                allNbhList.add(0, dataChild);
                                locProjectData.setLocation(allNbhList);


                                if (value == 1) {

                                    setSearchResult(locProjectData);

                                    searchResult.setVisibility(View.VISIBLE);
                                }
                            } else {
                                searchResultLay.setVisibility(View.GONE);
                                if (buttonClick)
                                    Toast.makeText(ActivityFilter.this, message, Toast.LENGTH_SHORT).show();
                            }
                            showHideNeighborHoodProgress(false);
                            layoutProgress.setVisibility(View.GONE);
                        } else {
                            mLoading.dismiss();
                            showHideNeighborHoodProgress(false);
                            Toast.makeText(ActivityFilter.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ListLocProjectResponse> call, Throwable t) {
                        mLoading.dismiss();
                        showHideNeighborHoodProgress(false);
                        Toast.makeText(ActivityFilter.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });

    }


    //TODO Alert
    private void alertSaveSearch() {
        alertDialog = new Dialog(ActivityFilter.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_save_search_popup, null);
        ImageView close = alert_layout.findViewById(R.id.close_alert);
        final EditText editTitle = alert_layout.findViewById(R.id.edit_title);
        final TextView saveSearch = alert_layout.findViewById(R.id.button_save_search);
        final TextView saveSearchIcon = alert_layout.findViewById(R.id.save_search_alert_icon_text);
        final TextView message = alert_layout.findViewById(R.id.save_search_message_text);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        saveSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTitle = editTitle.getText().toString().trim();
                if (!searchTitle.equals("")) {
                    closeKeyboard();
                    if (!Utils.NoInternetConnection(ActivityFilter.this)) {
                        saveSearchToServer(alertDialog, saveSearchIcon, editTitle, saveSearch, message);
                    } else {
                        alertDialog.dismiss();
                        Utils.showAlertNoInternet(ActivityFilter.this);
                    }
                } else {
                    Toast.makeText(ActivityFilter.this, getString(R.string.enter_search_title), Toast.LENGTH_SHORT).show();
                }

            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }


    //TODO Set data
    private void declarations() {
        layoutProgress = (LinearLayout) findViewById(R.id.progress_layout_property_search_buy);
        propertyTypeHolder = (LinearLayout) findViewById(R.id.propertyTypeHolder);
        clearFocus = (EditText) findViewById(R.id.clear_focus);
        searchBuy = (TextView) findViewById(R.id.buy_property_search_button);
        mLoading = new DialogProgress(ActivityFilter.this);
        rbStatusUpComing = (TextView) findViewById(R.id.radio_button_status_upcoming);
        rbStatusReadyToMove = (TextView) findViewById(R.id.radio_button_status_ready_to_move);
        rbStatusUnderConstruction = (TextView) findViewById(R.id.radio_button_status_under_construction);
        layoutPossession = findViewById(R.id.possession_lay);
        resetFilter = findViewById(R.id.reset_filter);
        saveSearch = findViewById(R.id.filter_save_search);
        mNestedScrollView = findViewById(R.id.nested_scroll_filter);

        isPropertyTabSelected = getIntent().getBooleanExtra("Properties_Tab_Selected", false);

        if (!isPropertyTabSelected) {
            layoutPossession.setVisibility(View.VISIBLE);
        }

        rbStatusUnderConstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentStatusChanged(rbStatusUnderConstruction.getId());
            }
        });

        rbStatusReadyToMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentStatusChanged(rbStatusReadyToMove.getId());
            }
        });

        rbStatusUpComing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentStatusChanged(rbStatusUpComing.getId());
            }
        });

        buyText = (TextView) findViewById(R.id.buy_text);
        rentText = (TextView) findViewById(R.id.rent_text);
        bedroomRecycler = (RecyclerView) findViewById(R.id.bedroom_recycler);
        bedroom_text = (TextView) findViewById(R.id.bedroom_text);
        buyMinPriceList = new ArrayList<>();
        buyMaxPriceList = new ArrayList<>();
        rentMaxPriceList = new ArrayList<>();
        rentMinPriceList = new ArrayList<>();
        filterPriceList = new ArrayList<>();
        textPropertyType = (TextView) findViewById(R.id.text_property_type_search_buy);
        selectorPropertyType = (TextView) findViewById(R.id.text_property_type_filter);
        propertyTypeList = new ArrayList<>();
        bedRoomList = new ArrayList<>();
        textBedRooms = (TextView) findViewById(R.id.text_bedroom_number_search_buy);
        editLocations = (AutoCompleteTextView) findViewById(R.id.edit_locations_buy);
        locationInfoList = new ArrayList<>();
        locations = new ArrayList<>();
        bedRoomList = new ArrayList<>();
        autoLabelLocation = (AutoLabelUI) findViewById(R.id.auto_label_location_buy);
        searchEditText = (EditText) findViewById(R.id.searchEditText);
        search_progress = (ProgressBar) findViewById(R.id.search_progress);
        searchResultLay = (CardView) findViewById(R.id.searchResultLay);
        searchResult = (CustomExpandable) findViewById(R.id.searchResult);

        priceRangeBar = findViewById(R.id.price_range_bar);
        trMinMaxPrice = findViewById(R.id.tr_min_max_price);
        tvMinPrice = findViewById(R.id.tv_min_price);
        tvMaxPrice = findViewById(R.id.tv_max_price);
        lastSelected = "buy";

        mPropertyTypeListView = findViewById(R.id.list_view_prop_type_selector);
        AllTv = findViewById(R.id.spinner_selection_any);
        mPropertyTypeDDLay = findViewById(R.id.property_type_dropdown_lay);

        mCityRcv = findViewById(R.id.city_rcv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mCityRcv.setLayoutManager(linearLayoutManager);

        cityListtv = findViewById(R.id.city_tv);

        cityListtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mCityDropdownClicked) {
                    mCityRcv.setVisibility(View.VISIBLE);
                    searchResultLay.setVisibility(View.GONE);
                    Utils.hideKeyboard(ActivityFilter.this, searchEditText);

                    cityAdapter = new CityAdapter(locationInfo, locationInfo -> {
                        cityListtv.setText(locationInfo.getCityName());
                        cityListtv.clearFocus();
                        mCityRcv.setVisibility(View.GONE);
                        selectedCityID = locationInfo.getCityID();
                        SessionManager.setLocationId(ActivityFilter.this, selectedCityID);
                        SessionManager.setLocationName(ActivityFilter.this, locationInfo.getCityName(),
                                locationInfo.getCityLat(), locationInfo.getCityLng());
                        new PayaAPICall().initiateUpdateCityForUserNotification(ActivityFilter.this);
                        searchEditText.clearFocus();
                        GlobalValues.regionDataTemp.clear();
                        autoLabelLocation.clear();
                        searchResultLay.setVisibility(View.GONE);
                        getSearchResult("", false, 0, true);
                        searchEditText.setText("");
//                        searchEditText.requestFocus();
//                        searchEditText.performClick();
                    });
                    mCityRcv.setAdapter(cityAdapter);
                    mCityDropdownClicked = true;
                } else {
                    mCityRcv.setVisibility(View.GONE);
                    mCityDropdownClicked = false;
                }

            }
        });


        if (GlobalValues.searchPropertyPurpose != null
                && !GlobalValues.searchPropertyPurpose.equals("")) {
            lastSelected = GlobalValues.searchPropertyPurpose;
            if (lastSelected.equalsIgnoreCase("rent")) {
                rentText.setSelected(true);
                buyText.setSelected(false);
                buySelected = false;
                selectedType = "Rent";
                strPropertyCurrentStatus = "";
                Utils.collapse(layoutPossession, 200);
            } else {
                buyText.setSelected(true);
                rentText.setSelected(false);
                buySelected = true;
                selectedType = "Sell";
                if (!isPropertyTabSelected) {
                    Utils.expand(layoutPossession, 200);

                }
            }
        }
        if (GlobalValues.selectedPropertyType != null && !GlobalValues.selectedPropertyType.equals("")) {
//            selectorPropertyType.setText(GlobalValues.selectedPropertyType);
            setBedRoomRecycler();
        }
        if (GlobalValues.possessionStatus != null && !GlobalValues.possessionStatus.equals("")) {
            togglePossessionStatus();
        }
        if (GlobalValues.regionDataTemp != null && GlobalValues.regionDataTemp.size() > 0) {
            for (RegionDataChild child : GlobalValues.regionDataTemp) {
                autoLabelLocation.addLabel(child.getOriginalText(), GlobalValues.regionDataTemp.lastIndexOf(child));
            }
            checkRegionId();
        } else GlobalValues.regionDataTemp = new ArrayList<>();

        autoLabelLocation.setOnRemoveLabelListener(new AutoLabelUI.OnRemoveLabelListener() {
            @Override
            public void onRemoveLabel(View view, int position) {
                if (GlobalValues.regionDataTemp.size() > position) {
                    GlobalValues.regionDataTemp.remove(position);
                }
            }
        });
        toggleSearchPurpose();

        if (SessionManager.getLoginStatus(ActivityFilter.this)) {
            userID = SessionManager.getAccessToken(ActivityFilter.this);
        }

        locationInfo = new ArrayList<>();
        text_city_list = (TextView) findViewById(R.id.text_city_list);
        getCityList();
        updateRangeBarProperties();
    }


    private void updateRangeBarProperties() {
        priceRangeBar.setNotifyWhileDragging(true);
        priceRangeBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                updateThePriceSelectionWhileChangingRangeBar(minValue, maxValue);
            }
        });
    }

    public void getCityList() {

        mLoading.show();
        ApiLinks.getClient().create(CityListingApi.class).post("" + SessionManager.getLanguageID(this))
                .enqueue(new Callback<CityListingResponse>() {
                    @Override
                    public void onResponse(Call<CityListingResponse> call, Response<CityListingResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getResponse();
                            if (message != null && message.equalsIgnoreCase("Success")) {
                                locationInfo.addAll(response.body().getData().getCityList());
                                updateCitySelection();
//  l                              showCityListSpinner();

                                // TODO commented below lines to enable filter reset
                                for (LocationInfo locationInfo : locationInfo) {
                                    if (SessionManager.getLocationId(ActivityFilter.this).equalsIgnoreCase(locationInfo.getCityID())) {
                                        cityListtv.setText(locationInfo.getCityName());
                                        SessionManager.setLocationName(ActivityFilter.this, locationInfo.getCityName(), locationInfo.getCityLat(), locationInfo.getCityLng());
                                        getSearchResult("", false, 0, true);
                                        break;
                                    }
                                }


                            } else
                                Toast.makeText(ActivityFilter.this, message, Toast.LENGTH_SHORT).show();


                            mLoading.dismiss();
                        } else {
                            Toast.makeText(ActivityFilter.this, "No Response", Toast.LENGTH_SHORT).show();
                            mLoading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<CityListingResponse> call, Throwable t) {
                        Toast.makeText(ActivityFilter.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        mLoading.dismiss();
                    }
                });
    }

    private void updateCitySelection() {
        for (LocationInfo cityDetails : locationInfo) {
            if (!Utils.getCityList().containsKey(cityDetails.getCityID())) {
                for (int id : Utils.getCityList().values()) {
                    if (cityDetails.getCityName().equalsIgnoreCase(getString(id))) {
                        Utils.getCityList().put(cityDetails.getCityID(), id);
                        break;
                    }
                }
            } else {
                break;
            }
        }
    }

    private void showCityListSpinner() {
        if (SessionManager.getLocationId(this).equals("-1")) {
            String address = GlobalValues.address;
            address = address.toLowerCase();
            for (int i = 0; i < locationInfo.size(); i++) {
                String cityName = locationInfo.get(i).getCityName();
                if (address.contains(cityName.toLowerCase())) {
                    selectedCityID = locationInfo.get(i).getCityID();
                    SessionManager.setLocationName(ActivityFilter.this, locationInfo.get(i).getCityName(),
                            locationInfo.get(i).getCityLat(), locationInfo.get(i).getCityLng());

                }
            }
            if (!selectedCityID.equals(""))
                SessionManager.setLocationId(ActivityFilter.this, selectedCityID);
            else {
                SessionManager.setLocationId(ActivityFilter.this, "1");

            }
        }
        //Update textview Based on language selection.
        for (LocationInfo locationInfo : locationInfo) {
            if (SessionManager.getLocationId(this).equalsIgnoreCase(locationInfo.getCityID())) {
                text_city_list.setText(locationInfo.getCityName());
                SessionManager.setLocationName(this, locationInfo.getCityName(), locationInfo.getCityLat(), locationInfo.getCityLng());
                break;
            }
        }
        selectedCityID = SessionManager.getLocationId(this);
        final Dialog dialog = new Dialog(this);
        @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.show_price_list_selector, null);
        final SearchView searchView = view.findViewById(R.id.sv_list_search);
        searchView.setVisibility(View.VISIBLE);
        final RecyclerView recyclerMinPrice = (RecyclerView) view.findViewById(R.id.recycler_price_list);
        recyclerMinPrice.setLayoutManager(new LinearLayoutManager(ActivityFilter.this));
        adapter = new AdapterCityListing(locationInfo, ActivityFilter.this, SessionManager.getLocationId(this));
        recyclerMinPrice.setAdapter(adapter);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        text_city_list.setOnClickListener(view1 -> dialog.show());

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

        adapter.setMinPriceInterface(locationInfo -> {
            text_city_list.setText(locationInfo.getCityName());
            selectedCityID = locationInfo.getCityID();
            SessionManager.setLocationId(ActivityFilter.this, selectedCityID);
            SessionManager.setLocationName(ActivityFilter.this, locationInfo.getCityName(),
                    locationInfo.getCityLat(), locationInfo.getCityLng());
            new PayaAPICall().initiateUpdateCityForUserNotification(ActivityFilter.this);
            GlobalValues.regionDataTemp.clear();
            autoLabelLocation.clear();
            dialog.dismiss();
            //getIndexList();
        });
        if (SessionManager.getLocationId(this).equals("-1")) {
            Toast.makeText(ActivityFilter.this, "Please select your City", Toast.LENGTH_LONG).show();
            dialog.show();
        }
    }

    public void searchList(String newText) {
        searchedLocatinInfo = new ArrayList<>();

        for (LocationInfo info : locationInfo) {
            if (info.getCityName().toLowerCase().contains(newText.toLowerCase())) {
                searchedLocatinInfo.add(info);
            }
        }
        if (searchedLocatinInfo.isEmpty()) {

        } else {
            adapter.searchedLocationList(searchedLocatinInfo);
        }

    }

    public void citySearchList(String newText) {
        searchedLocatinInfo = new ArrayList<>();

        for (LocationInfo info : locationInfo) {
            if (info.getCityName().toLowerCase().contains(newText.toLowerCase())) {
                searchedLocatinInfo.add(info);
            }
        }
        if (!searchedLocatinInfo.isEmpty()) {
            cityAdapter.updateCity(searchedLocatinInfo);
        }

    }

    private void togglePossessionStatus() {
        switch (GlobalValues.possessionStatus) {
            case "Upcoming":
                rbStatusUpComing.setSelected(true);
                rbStatusReadyToMove.setSelected(false);
                rbStatusUnderConstruction.setSelected(false);
                strPropertyCurrentStatus = "Upcoming";
                break;
            case "Ready to move":
                rbStatusUpComing.setSelected(false);
                rbStatusReadyToMove.setSelected(true);
                rbStatusUnderConstruction.setSelected(false);
                strPropertyCurrentStatus = "Ready to move";
                break;
            case "Under Construction":
                rbStatusUpComing.setSelected(false);
                rbStatusReadyToMove.setSelected(false);
                rbStatusUnderConstruction.setSelected(true);
                strPropertyCurrentStatus = "Under Construction";
                break;
        }
    }

    public void setBedRoomRecycler() {
        if (bedRoomList.size() > 0) {
            bedroomRecycler = (RecyclerView) findViewById(R.id.bedroom_recycler);
            BedRoomAdapter bedRoomAdapter = new BedRoomAdapter(bedRoomList);
            bedroomRecycler.setAdapter(bedRoomAdapter);
            bedroom_text.setVisibility(View.VISIBLE);
            bedroomRecycler.setVisibility(View.VISIBLE);
            GlobalValues.selectedBedroomsID = bedRoomList.get(0).getAttributeID();
        } else {
            bedroom_text.setVisibility(View.GONE);
            bedroomRecycler.setVisibility(View.GONE);
        }

    }

    private void setAllAttributesDataToUI() {
        recyclerSpecifications = (RecyclerView) findViewById(R.id.recycler_specifications_content_post_property);
        adapterSpecifications = new AdapterSpecificationsFilter(this, allAttributesList);
        recyclerSpecifications.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        recyclerSpecifications.setNestedScrollingEnabled(false);
        recyclerSpecifications.setHasFixedSize(true);
        recyclerSpecifications.setAdapter(adapterSpecifications);
        adapterSpecifications.notifyDataSetChanged();
        recyclerSpecifications.clearFocus();
    }

    public void toggleSearchPurpose() {
        rentText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buySelected = false;
                buyText.setSelected(false);
                rentText.setSelected(true);
                if (lastSelected.equalsIgnoreCase("Sell") || lastSelected.equalsIgnoreCase("buy")) {
                    resetGlobalValues();
                    updatePriceFilterValue();
                }
                lastSelected = "rent";
                selectedType = "Rent";
                strPropertyCurrentStatus = "";
                Utils.collapse(layoutPossession, 200);
            }
        });

        buyText.setOnClickListener(view -> {
            buySelected = true;
            buyText.setSelected(true);
            rentText.setSelected(false);
            if (lastSelected.equalsIgnoreCase("rent")) {
                resetGlobalValues();
                updatePriceFilterValue();
            }
            lastSelected = "buy";
            selectedType = "Sell";
            if (!isPropertyTabSelected) {
                Utils.expand(layoutPossession, 200);
            }
        });
        GlobalValues.searchPropertyPurpose = lastSelected;
    }

    private void resetGlobalValues() {
        GlobalValues.selectedMinBudgetPriceModel = null;
        GlobalValues.selectedMaxBudgetPriceModel = null;
        GlobalValues.selectedMinPrice = "";
        GlobalValues.selectedMinPriceValue = "";
        GlobalValues.selectedMaxPrice = "";
        GlobalValues.selectedMaxPriceValue = "";
    }

    private void currentStatusChanged(int checkedId) {
        switch (checkedId) {
            case R.id.radio_button_status_upcoming:
                if (!rbStatusUpComing.isSelected()) {
                    rbStatusUpComing.setSelected(true);
                    rbStatusReadyToMove.setSelected(false);
                    rbStatusUnderConstruction.setSelected(false);
                    strPropertyCurrentStatus = "Upcoming";
                    GlobalValues.possessionStatus = "Upcoming";
                } else {
                    rbStatusUpComing.setSelected(false);
                    strPropertyCurrentStatus = "";
                    GlobalValues.possessionStatus = "";
                }
                break;
            case R.id.radio_button_status_ready_to_move:
                if (!rbStatusReadyToMove.isSelected()) {
                    rbStatusUpComing.setSelected(false);
                    rbStatusReadyToMove.setSelected(true);
                    rbStatusUnderConstruction.setSelected(false);
                    strPropertyCurrentStatus = "Ready to move";
                    GlobalValues.possessionStatus = "Ready to move";
                } else {
                    rbStatusReadyToMove.setSelected(false);
                    strPropertyCurrentStatus = "";
                    GlobalValues.possessionStatus = "";
                }

                break;
            case R.id.radio_button_status_under_construction:
                if (!rbStatusUnderConstruction.isSelected()) {
                    rbStatusUpComing.setSelected(false);
                    rbStatusReadyToMove.setSelected(false);
                    rbStatusUnderConstruction.setSelected(true);
                    strPropertyCurrentStatus = "Under Construction";
                    GlobalValues.possessionStatus = "Under Construction";
                } else {
                    rbStatusUnderConstruction.setSelected(false);
                    strPropertyCurrentStatus = "";
                    GlobalValues.possessionStatus = "";
                }

                break;
        }
    }

    private void setAdapters() {
        adapterLocations = new ArrayAdapter<>(this, R.layout.auto_complete_custom_item,
                R.id.autoCompleteItem, locations);
        editLocations.setAdapter(adapterLocations);
        adapterLocations.notifyDataSetChanged();
    }

    private void updatePriceFilterValue() {
        if (buySelected) {
            filterPriceList = Utils.getBuyPriceList();
        } else {
            filterPriceList = rentMaxPriceList;
        }
        Log.d("min", "" + filterPriceList);

        if (!filterPriceList.isEmpty()) {
            priceRangeBar.setVisibility(View.VISIBLE);
            trMinMaxPrice.setVisibility(View.VISIBLE);


            updatePriceValue();
        } else {
            priceRangeBar.setVisibility(View.GONE);
            trMinMaxPrice.setVisibility(View.GONE);
        }
    }

    private void resetDataToPreviousSelection() {
        if (GlobalValues.selectedMinBudgetPriceModel != null) {
            selectedMinBudgetPriceModel = GlobalValues.selectedMinBudgetPriceModel;
        } else {
            selectedMinBudgetPriceModel = filterPriceList.get(0);
        }
        if (GlobalValues.selectedMaxBudgetPriceModel != null) {
            selectedMaxBudgetPriceModel = GlobalValues.selectedMaxBudgetPriceModel;
        } else {
            selectedMaxBudgetPriceModel = filterPriceList.get(filterPriceList.size() - 1);
        }
        updatePreSelectedValueInField();
    }

    private void updateThePriceSelectionWhileChangingRangeBar(Integer leftValue, Integer rightValue) {
        try {
            int size = filterPriceList.size() - 1;
            if (priceRangeBar.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                selectedMinBudgetPriceModel = filterPriceList.get(Math.abs(size - rightValue));
                selectedMaxBudgetPriceModel = filterPriceList.get(Math.abs(size - leftValue));
            } else {
                selectedMinBudgetPriceModel = filterPriceList.get(leftValue);
                selectedMaxBudgetPriceModel = filterPriceList.get(rightValue);
            }
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            if (buySelected) {
                filterPriceList = Utils.getBuyPriceList();
            } else {
                filterPriceList = rentMaxPriceList;
            }
            if (!filterPriceList.isEmpty()) {
                selectedMinBudgetPriceModel = filterPriceList.get(0);
                selectedMaxBudgetPriceModel = filterPriceList.get(filterPriceList.size() - 1);
            } else {
                selectedMinBudgetPriceModel = new BudgetPriceData();
                selectedMinBudgetPriceModel.setPrice(String.valueOf(0));
                selectedMinBudgetPriceModel.setPriceValue(Utils.convertToCurrencyFormat(10, String.valueOf(0)));
                selectedMaxBudgetPriceModel = new BudgetPriceData();
                selectedMaxBudgetPriceModel.setPrice(String.valueOf(5000));
                selectedMaxBudgetPriceModel.setPriceValue(Utils.convertToCurrencyFormat(10, String.valueOf(5000)));
            }
        }
        updatePreSelectedValueInField();
    }

    private void updatePriceValue() {
        priceRangeBar.setRangeValues(0, filterPriceList.size() - 1);
        resetDataToPreviousSelection();
        int min = 0, max = filterPriceList.size() - 1;
        Log.d("min", "" + filterPriceList.get(0).getPriceValue());

        if (GlobalValues.selectedMinBudgetPriceModel != null && GlobalValues.selectedMaxBudgetPriceModel != null) {
            for (BudgetPriceData budgetPriceData : filterPriceList) {
                if (budgetPriceData.getPriceValue().equalsIgnoreCase(GlobalValues.selectedMinBudgetPriceModel.getPriceValue())) {
                    min = filterPriceList.indexOf(budgetPriceData);
                    Log.d("min", "" + filterPriceList.indexOf(budgetPriceData));

                }
                if (budgetPriceData.getPriceValue().equalsIgnoreCase(GlobalValues.selectedMaxBudgetPriceModel.getPriceValue())) {
                    max = filterPriceList.indexOf(budgetPriceData);
                }
            }
        }
        int size = filterPriceList.size() - 1;

        if (priceRangeBar.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            priceRangeBar.setSelectedMinValue(Math.abs(size - max));
            priceRangeBar.setSelectedMaxValue(Math.abs(size - min));
        } else {
            priceRangeBar.setSelectedMinValue(min);
            priceRangeBar.setSelectedMaxValue(max);
        }
    }

    private void updatePreSelectedValueInField() {
        tvMinPrice.setText(String.format(getString(R.string.min_price_with_value), selectedMinBudgetPriceModel != null ? selectedMinBudgetPriceModel.getPriceValue() : filterPriceList.get(0).getPrice()));
        tvMaxPrice.setText(String.format(getString(R.string.max_price_with_value), selectedMaxBudgetPriceModel != null ? selectedMaxBudgetPriceModel.getPriceValue() : filterPriceList.get(filterPriceList.size() - 1).getPrice()));
        minPrice = getValueFromString(selectedMinBudgetPriceModel != null ? selectedMinBudgetPriceModel.getPriceValue() : filterPriceList.get(0).getPrice());
        maxPrice = selectedMaxBudgetPriceModel.getPriceValue().contains("+") ? -1 : getValueFromString(selectedMaxBudgetPriceModel.getPriceValue());
    }

    private void updateGlobalBudgetPriceData() {
        try {
            if (selectedMinBudgetPriceModel == null || selectedMaxBudgetPriceModel == null) {
                if (selectedMinBudgetPriceModel == null) {
                    if (!filterPriceList.isEmpty()) {
                        selectedMinBudgetPriceModel = filterPriceList.get(0);
                    } else {
                        selectedMinBudgetPriceModel = new BudgetPriceData();
                        selectedMinBudgetPriceModel.setPrice(String.valueOf(0));
                        selectedMinBudgetPriceModel.setPriceValue(Utils.convertToCurrencyFormat(10, String.valueOf(0)));
                    }
                }
                if (selectedMaxBudgetPriceModel == null) {
                    if (!filterPriceList.isEmpty()) {
                        selectedMaxBudgetPriceModel = filterPriceList.get(filterPriceList.size() - 1);
                    } else {
                        selectedMaxBudgetPriceModel = new BudgetPriceData();
                        selectedMaxBudgetPriceModel.setPrice(String.valueOf(5000));
                        selectedMaxBudgetPriceModel.setPriceValue(Utils.convertToCurrencyFormat(10, String.valueOf(5000)));
                    }
                }
            }
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            selectedMinBudgetPriceModel = new BudgetPriceData();
            selectedMinBudgetPriceModel.setPrice(String.valueOf(0));
            selectedMinBudgetPriceModel.setPriceValue(Utils.convertToCurrencyFormat(10, String.valueOf(0)));
            selectedMaxBudgetPriceModel = new BudgetPriceData();
            selectedMaxBudgetPriceModel.setPrice(String.valueOf(5000));
            selectedMaxBudgetPriceModel.setPriceValue(Utils.convertToCurrencyFormat(10, String.valueOf(5000)));
        }
        minPrice = Integer.parseInt(checkPriceValue(selectedMinBudgetPriceModel).replace("+", " "));
        maxPrice = selectedMaxBudgetPriceModel.getPriceValue().contains("+") ? -1 : getValueFromString(selectedMaxBudgetPriceModel.getPriceValue());
        GlobalValues.selectedMinBudgetPriceModel = selectedMinBudgetPriceModel;
        GlobalValues.selectedMaxBudgetPriceModel = selectedMaxBudgetPriceModel;
        GlobalValues.selectedMinPrice = selectedMinBudgetPriceModel.getPriceValue().contains("+") ? "" : selectedMinBudgetPriceModel.getPrice();
        GlobalValues.selectedMinPriceValue = selectedMinBudgetPriceModel.getPriceValue().contains("+") ? "" : selectedMinBudgetPriceModel.getPriceValue();
        GlobalValues.selectedMaxPrice = selectedMaxBudgetPriceModel.getPriceValue().contains("+") ? "" : selectedMaxBudgetPriceModel.getPrice();
        GlobalValues.selectedMaxPriceValue = selectedMaxBudgetPriceModel.getPriceValue().contains("+") ? "" : selectedMaxBudgetPriceModel.getPriceValue();
    }

    private String checkPriceValue(BudgetPriceData budgetPriceModel) {
        return budgetPriceModel != null && budgetPriceModel.getPrice() != null && !budgetPriceModel.getPrice().isEmpty() ? budgetPriceModel.getPrice() : "0";
    }

    private int getValueFromString(String value) {
        String removedValue = value.replace("+", "").replace(",", "");
        return Integer.parseInt(removedValue);
    }

    @SuppressLint("NewApi")
    private void closeKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showPropertyTypeSpinner() {
        final Dialog dialog = new Dialog(this);

        @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.show_property_type_selector, null);
        final ExpandableListView listView = (ExpandableListView) view.findViewById(R.id.list_view_prop_type_selector);
        final AdapterPropertyTypeSelector adapter = new AdapterPropertyTypeSelector(this, propertyTypeList);
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
                selectorPropertyType.setText(getResources().getString(R.string.any));
                GlobalValues.selectedPropertyTypeID = null;
                GlobalValues.selectedPropertyType = null;
                dialog.dismiss();
            }
        });

        listView.setOnGroupClickListener((parent, v, groupPosition, id) -> true);

        listView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            PropertyTypeSub child = propertyTypeList.get(groupPosition).getSubCategory().get(childPosition);
            selectorPropertyType.setText(child.getPropertyTypeName());


            GlobalValues.savedAttributes = new ArrayList<>();
            if (GlobalValues.selectedPropertyTypeID != null
                    && !GlobalValues.selectedPropertyTypeID.equalsIgnoreCase("")) {
                if (!GlobalValues.selectedPropertyTypeID.equalsIgnoreCase(child.getPropertyTypeID())) {
                    getAttributeList(child.getPropertyTypeID());
                    GlobalValues.selectedPropertyTypeID = child.getPropertyTypeID();
                    GlobalValues.selectedPropertyType = child.getPropertyTypeName();
                    if (GlobalValues.selectedBedrooms != null) {
                        GlobalValues.selectedBedrooms.clear();
                        GlobalValues.selectedBedroomID.clear();
                    }
                }
            } else {
                GlobalValues.selectedPropertyTypeID = child.getPropertyTypeID();
                GlobalValues.selectedPropertyType = child.getPropertyTypeName();
                getAttributeList(child.getPropertyTypeID());
                if (GlobalValues.selectedBedrooms != null) {
                    GlobalValues.selectedBedrooms.clear();
                    GlobalValues.selectedBedroomID.clear();

                }
            }
            if (child.getPropertyTypeName().contains("Land") || child.getPropertyTypeName().contains("Lands")
                    || child.getPropertyTypeName().contains("Plot") || child.getPropertyTypeName().contains("Plots")) {
                layoutPossession.setVisibility(View.GONE);
                strPropertyCurrentStatus = "";
                bedRoomList.clear();
                bedroom_text.setVisibility(View.GONE);
                bedroomRecycler.setVisibility(View.GONE);


            } else {
                if (!isPropertyTabSelected) {
                    layoutPossession.setVisibility(View.VISIBLE);
                }
                strPropertyCurrentStatus = "";
                getBedRoomNumbers();

            }
            dialog.dismiss();
            return false;
        });
    }

    private void actionSearchFilter() {
        //need to change this code
        if (!Utils.NoInternetConnection(ActivityFilter.this)) {
            updateGlobalBudgetPriceData();
            if (GlobalValues.selectedMinPrice == null || GlobalValues.selectedMinPrice.equals("")) {
                GlobalValues.selectedMinPrice = "All";
            }
            if (GlobalValues.selectedMaxPrice == null || GlobalValues.selectedMaxPrice.equals("")) {
                GlobalValues.selectedMaxPrice = "0";
            }
            if (GlobalValues.selectedPropertyTypeID == null || GlobalValues.selectedPropertyTypeID.equals("")) {
                GlobalValues.selectedPropertyTypeID = "";
            }
            if (GlobalValues.possessionStatus == null || GlobalValues.possessionStatus.equals("")) {
                GlobalValues.possessionStatus = strPropertyCurrentStatus;
            }
            GlobalValues.searchPropertyPurpose = lastSelected;
            if (GlobalValues.selectedBedroomsNumber != null) {
                if (GlobalValues.selectedBedrooms == null || GlobalValues.selectedBedrooms.size() == 0) {
                    GlobalValues.selectedBedroomsNumber = "";
                } else {
                    StringBuilder number = new StringBuilder(GlobalValues.selectedBedroomsID + "~");
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
                    GlobalValues.selectedBedroomsNumber = number.toString();
                }
            }
            StringBuilder strAttributes = new StringBuilder();
            if (allAttributesList != null && allAttributesList.size() != 0) {
                ArrayList<AllAttributesData> allAttributes = allAttributesList;
                for (int i = 0; i < allAttributes.size(); i++) {
                    if (allAttributes.get(i).getPropertyAttrSelectedOptionID() != null) {
                        if (!allAttributes.get(i).getPropertyAttrSelectedOptionID().equals("")
                                && !allAttributes.get(i).getPropertyAttrSelectedOptionID().equals("0")) {
                            if (strAttributes.toString().equals("")) {
                                strAttributes = new StringBuilder(allAttributes.get(i).getAttrID() +
                                        "~" + allAttributes.get(i).getPropertyAttrSelectedOptionID());

                            } else {
                                strAttributes.append(":").append(allAttributes.get(i).getAttrID()).append("~")
                                        .append(allAttributes.get(i).getPropertyAttrSelectedOptionID());

                            }

                        }
                    }
                }
            }

          /*  if (!strAttributes.toString().equals("")) {
                if (GlobalValues.selectedBedroomsNumber != null) {
                    if (GlobalValues.selectedBedroomsNumber.equals("")) {
                        GlobalValues.selectedBedroomsNumber = strAttributes.toString();
                    } else {
                        GlobalValues.selectedBedroomsNumber += ":" + strAttributes;
                    }
                } else {
                    GlobalValues.selectedBedroomsNumber = strAttributes.toString();
                }
            }*/

            checkRegionId();
            String type;
            PropertyProjectType propertyProjectType;
            if (lastSelected.equals("buy") || lastSelected.equals("Sell")) {
                type = "Sell";
                propertyProjectType = PropertyProjectType.BUY;
            } else {
                type = "Rent";
                propertyProjectType = PropertyProjectType.RENT;
            }


            Intent intent = getIntent();
            intent.putExtra(Utils.TYPE, propertyProjectType);
            intent.putExtra("selectedType", type);
            intent.putExtra("attributes", allAttributesList);
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            Utils.showAlertNoInternet(ActivityFilter.this);
        }
    }

    public boolean getFurnishedStatus(String furnishedId) {
        return Utils.ATTR_FURNISHED_ID.equalsIgnoreCase(furnishedId);
    }

    public void checkRegionId() {
        GlobalValues.selectedRegions.clear();
        for (RegionDataChild child : GlobalValues.regionDataTemp) {

            if (!ExtensionKt.checkEquals(child.getId(), "00", true)) {
                GlobalValues.selectedRegions.add(child.getId());
            }
            GlobalValues.selectedRegionsText.add(child.getOriginalText());

        }

        if (GlobalValues.selectedRegions != null) {
            if (GlobalValues.selectedRegions.size() == 0) {
                GlobalValues.selectedRegionId = "";
            } else {
                StringBuilder number = new StringBuilder();
                int size = GlobalValues.selectedRegions.size();
                for (int i = 0; i < size; i++) {
                    if (size == 1) {
                        number = new StringBuilder(GlobalValues.selectedRegions.get(0));
                    } else {
                        if (i == 0) {
                            number = new StringBuilder(GlobalValues.selectedRegions.get(i));
                        } else {
                            number.append(",").append(GlobalValues.selectedRegions.get(i));
                        }
                    }
                }
                GlobalValues.selectedRegionId = number.toString();
            }
        }
    }

    //TODO Main Functions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
           /* case R.id.home_menu_alert:
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(this, "touched", Toast.LENGTH_SHORT).show();
        return super.onTouchEvent(event);
    }
}
