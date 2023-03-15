package com.paya.paragon.activity.search;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dpizarro.autolabel.library.AutoLabelUI;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.paya.paragon.R;
import com.paya.paragon.PropertyProjectListActivity;
import com.paya.paragon.adapter.AdapterCityListing;
import com.paya.paragon.adapter.AdapterTypeSelector;
import com.paya.paragon.adapter.BedRoomAdapter;
import com.paya.paragon.adapter.PropertyTypeAdapter;
import com.paya.paragon.adapter.SearchResultAdapter;
import com.paya.paragon.api.bedRoomList.BedRoomInfo;
import com.paya.paragon.api.bedRoomList.BedRoomListApi;
import com.paya.paragon.api.bedRoomList.BedRoomListResponse;
import com.paya.paragon.api.budgetList.BudgetPriceData;
import com.paya.paragon.api.cityList.CityListingApi;
import com.paya.paragon.api.cityList.CityListingResponse;
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
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.CustomExpandable;
import com.paya.paragon.utilities.DialogProgress;
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

@SuppressWarnings({"HardCodedStringLiteral", "RedundantCast"})
public class SearchOptionActivity extends AppCompatActivity {

    LinearLayout propertyTypeHolder;
    TextView searchBuy, textPropertyType, textBedRooms, buyText, rentText;
    TextView bedroom_text;
    EditText clearFocus;
    TextView tvMinPrice, tvMaxPrice;
    TableRow trMinMaxPrice;
    RangeSeekBar<Integer> priceRangeBar;

    DialogProgress mLoading;
    AutoCompleteTextView editLocations;
    BudgetPriceData selectedMinBudgetPriceModel, selectedMaxBudgetPriceModel;
    private static String[] locationList;
    ArrayAdapter<String> adapterLocations;
    private List<String> locations;

    ArrayList<LocationInfo> locationInfoList;
    ArrayList<PropertyTypeMain> propertyTypeList;
    ArrayList<BedRoomInfo> bedRoomList;
    ArrayList<BudgetPriceData> buyMinPriceList, rentMinPriceList;
    ArrayList<BudgetPriceData> buyMaxPriceList, rentMaxPriceList;
    ArrayList<BudgetPriceData> filterPriceList;
    ProgressBar search_progress;
    private AutoLabelUI autoLabelLocation;
    EditText searchEditText;
    private CardView searchResultLay;
    private CustomExpandable searchResult;
    String selectedLocation = "", imagePath = "";
    RecyclerView propertyTypeRecycler, bedroomRecycler;
    private ArrayList<SearchDataLocProj> searchDataLocProjs;
    private ListLocProjectData locProjectData;
    private RegionDataChild regionDataChild;
    boolean buySelected = false;
    String lastSelected = "";
    String purpose = "";
    int maxPrice = 0, minPrice = 0;
    TextView tvPropertyType;

    private TextView text_city_list;
    private ArrayList<LocationInfo> locationInfo;
    private String selectedCityID = "";
    int count = 0;
    private String langName = "";

    // DialogProgress progress;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_property_search_buy);
        langName = SessionManager.getLanguageName(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);

        mTitle.setTypeface(mTitle.getTypeface(), Typeface.BOLD);
        Intent intent = getIntent();
        lastSelected = intent.getStringExtra("comingFrom") != null ? intent.getStringExtra("comingFrom") : "Buy";
        declarations();
        if (lastSelected.equals("Buy")) {
            buySelected = true;
            purpose = "Sell";
            mTitle.setText(R.string.buy);
        } else {
            buySelected = false;
            purpose = "Rent";
            mTitle.setText(R.string.rent);
        }

        searchBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionSearchListing();
            }
        });


        GlobalValues.regionDataTemp = new ArrayList<>();

        editLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
            }
        });


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
                Log.d("LENGTH12345", editable.toString().length() + "");
                if (text.length() > 2) {
                    getSearchResult(text, false);
                } else {
                    searchResultLay.setVisibility(View.GONE);
                    Log.d("TEXT_WATCHER_!@#", "got");
                }

            }
        });

        searchEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_DEL) {
                    if (searchEditText.getText().toString().length() == 0) {
                        searchResultLay.setVisibility(View.GONE);
                        return true;
                    }
                }
                return false;
            }
        });

        searchEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (Utils.isValidName(searchEditText.getText().toString()) && !searchEditText.getText().toString().equals("")) {
                        getSearchResult(searchEditText.getText().toString(), true);
                    } else
                        Toast.makeText(SearchOptionActivity.this, getResources().getString(R.string.valid_data), Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });

        GlobalValues.clearBuyGlobalValues();

        if (!Utils.NoInternetConnection(SearchOptionActivity.this)) {
            getIndexValues();
            getCityList();
            getBedRoomNumbers();
        } else {
            Utils.showAlertNoInternet(SearchOptionActivity.this);
        }
    }


    private void actionSearchListing() {
        if (!Utils.NoInternetConnection(SearchOptionActivity.this)) {
            updateGlobalBudgetPriceData();
            if (GlobalValues.selectedMinPrice == null || GlobalValues.selectedMinPrice.equals("")) {
                GlobalValues.selectedMinPrice = "";
            }
            if (GlobalValues.selectedMaxPrice == null || GlobalValues.selectedMaxPrice.equals("")) {
                GlobalValues.selectedMaxPrice = "";
            }
            if (GlobalValues.selectedPropertyTypeID == null || GlobalValues.selectedPropertyTypeID.equals("")) {
                GlobalValues.selectedPropertyTypeID = "";
            }
            GlobalValues.searchPropertyPurpose = lastSelected;
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

            checkRegionId();
            String type;
            if (lastSelected.equals("Buy")) {
                type = "Sell";
            } else {
                type = "Rent";
            }
            GlobalValues.possessionStatus = "";

            //default newest listing
            GlobalValues.selectedSortText = getString(R.string.new_listing);
            GlobalValues.selectedSortValue = "date/orderby/desc";


            Intent in = new Intent(SearchOptionActivity.this, PropertyProjectListActivity.class);
            in.putExtra("searchPropertyPurpose", type);
            startActivity(in);
        } else {
            Utils.showAlertNoInternet(SearchOptionActivity.this);
        }
    }

    public void checkRegionId() {
        GlobalValues.selectedRegions.clear();
        for (RegionDataChild child : GlobalValues.regionDataTemp) {
            GlobalValues.selectedRegions.add(child.getId());
            GlobalValues.selectedRegionsText.add(child.getOriginalText());
        }

        if (GlobalValues.selectedRegions == null || GlobalValues.selectedRegions.size() == 0) {
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

    @SuppressLint("NewApi")
    public void setSearchResult(ListLocProjectData locProjectData) {
        SearchDataLocProj searchDataLocProj = new SearchDataLocProj();
        //SearchDataLocProj searchDataLocProj1 = new SearchDataLocProj();
        SearchDataLocProj searchDataLocProj2 = new SearchDataLocProj();
        searchDataLocProjs = new ArrayList<>();
        if (locProjectData.getLocation().size() > 0) {
            searchDataLocProj.setName("Community");
            searchDataLocProj.setChild(locProjectData.getLocation());
            searchDataLocProjs.add(searchDataLocProj);
        }

        if (locProjectData.getProjects().size() > 0) {
            searchDataLocProj2.setName("Projects");
            searchDataLocProj2.setChild(locProjectData.getProjects());
            searchDataLocProjs.add(searchDataLocProj2);
        }

        searchResultLay.setVisibility(View.VISIBLE);
        searchResult.setNestedScrollingEnabled(false);
        final SearchResultAdapter searchResultAdapter = new SearchResultAdapter(SearchOptionActivity.this, searchDataLocProjs);
        searchResult.setAdapter(searchResultAdapter);

        for (int i = 0; i < searchResultAdapter.getGroupCount(); i++)
            searchResult.expandGroup(i);

        searchResult.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent,
                                        View v, int groupPosition, long id) {
                return parent.isGroupExpanded(groupPosition);
            }
        });
        searchResult.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                regionDataChild = searchResultAdapter.getChild(i, i1);
                // callProductList(sideMenuCategory);
                closeKeyboard();
                searchEditText.setText("");
                searchResultLay.setVisibility(View.GONE);
                GlobalValues.regionDataTemp.add(regionDataChild);
          /*      GlobalValues.selectedRegions.add(regionDataChild.getId());
                GlobalValues.selectedRegionsText.add(regionDataChild.getOriginalText());*/
                searchDataLocProjs.clear();
                autoLabelLocation.addLabel(regionDataChild.getOriginalText(), GlobalValues.regionDataTemp.lastIndexOf(regionDataChild));
                return false;
            }
        });
        autoLabelLocation.setOnRemoveLabelListener(new AutoLabelUI.OnRemoveLabelListener() {
            @Override
            public void onRemoveLabel(View view, int position) {
                GlobalValues.regionDataTemp.remove(position);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.rootView));
        if (!langName.equalsIgnoreCase(SessionManager.getLanguageName(this)))
            recreate();
    }

    public void getSearchResult(final String query, final boolean buttonClick) {
        search_progress.setVisibility(View.VISIBLE);
        checkRegionId();
        String location_id = "2";
        if (!SessionManager.getLocationId(this).equals("-1"))
            location_id = SessionManager.getLocationId(this);
      /*  ApiLinks.getClient().create(ListLocProjApi.class).post(GlobalValues.countryID,
                query,
                location_id,
                GlobalValues.selectedRegionId)
                .enqueue(new Callback<ListLocProjectResponse>() {
                    @Override
                    public void onResponse(Call<ListLocProjectResponse> call,
                                           Response<ListLocProjectResponse> response) {
                        String message = response.body().getMessage();
                        if (response.body().getResponse().equalsIgnoreCase("Success")) {
                            locProjectData = response.body().getData();
                            setSearchResult(locProjectData);
                            searchResult.setVisibility(View.VISIBLE);
                        } else {
                            searchResultLay.setVisibility(View.GONE);
                            if (buttonClick)
                                Toast.makeText(SearchOptionActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                        search_progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ListLocProjectResponse> call, Throwable t) {
                        mLoading.dismiss();
                        search_progress.setVisibility(View.GONE);
                        Toast.makeText(SearchOptionActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });*/


    }


    //TODO API Calls
    public void getIndexValues() {
        mLoading.show();
        ApiLinks.getClient().create(IndexBuyRentApi.class)
                .post(purpose, GlobalValues.countryID, SessionManager.getLanguageID(this)).enqueue(new Callback<IndexBuyRentResponse>() {
            @Override
            public void onResponse(Call<IndexBuyRentResponse> call, Response<IndexBuyRentResponse> response) {

                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    int code = response.body().getCode();
                    if (code == 100) {
                        count++;
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
                        Collections.sort(locations);
                        Arrays.sort(locationList);
                        Collections.sort(locationInfoList, new Comparator<LocationInfo>() {
                            @Override
                            public int compare(LocationInfo l1, LocationInfo l2) {
                                return l1.getCityName().compareToIgnoreCase(l2.getCityName());
                            }
                        });
                    }
                    mLoading.dismiss();
                    updatePriceFilterValue();
                    setAdapters();
                } else {
                    mLoading.dismiss();

                }
            }

            @Override
            public void onFailure(Call<IndexBuyRentResponse> call, Throwable t) {
                mLoading.dismiss();
            }
        });
    }

    private void updatePriceFilterValue() {
        if (buySelected) {
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

    private int getValueFromString(String value) {
        String removedValue = value.replace("+", "").replace(",", "");
        return Integer.parseInt(removedValue);
    }

    public void getBedRoomNumbers() {
        mLoading.show();
        ApiLinks.getClient().create(BedRoomListApi.class)
                .post(GlobalValues.selectedPropertyTypeID,SessionManager.getLanguageID(SearchOptionActivity.this))
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
                                bedroom_text.setVisibility(View.GONE);
                                bedroomRecycler.setVisibility(View.GONE);
                            }
                        } else {
                            bedroom_text.setVisibility(View.GONE);
                            bedroomRecycler.setVisibility(View.GONE);
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

    private void declarations() {
        propertyTypeHolder = (LinearLayout) findViewById(R.id.propertyTypeHolder);
        clearFocus = (EditText) findViewById(R.id.clear_focus);
        searchBuy = (TextView) findViewById(R.id.buy_property_search_button);
        mLoading = new DialogProgress(SearchOptionActivity.this);
        tvPropertyType = findViewById(R.id.tv_property_type);

        buyText = (TextView) findViewById(R.id.buy_text);
        rentText = (TextView) findViewById(R.id.rent_text);
        propertyTypeRecycler = (RecyclerView) findViewById(R.id.property_type_recycler);
        bedroomRecycler = (RecyclerView) findViewById(R.id.bedroom_recycler);
        bedroom_text = (TextView) findViewById(R.id.bedroom_text);
        buyMinPriceList = new ArrayList<>();
        buyMaxPriceList = new ArrayList<>();
        rentMaxPriceList = new ArrayList<>();
        rentMinPriceList = new ArrayList<>();
        filterPriceList = new ArrayList<>();

        textPropertyType = (TextView) findViewById(R.id.text_property_type_search_buy);
        propertyTypeList = new ArrayList<>();
        bedRoomList = new ArrayList<>();
        textBedRooms = (TextView) findViewById(R.id.text_bedroom_number_search_buy);

        editLocations = (AutoCompleteTextView) findViewById(R.id.edit_locations_buy);
        locationInfoList = new ArrayList<>();
        locations = new ArrayList<>();
        bedRoomList = new ArrayList<>();
        GlobalValues.selectedBedrooms = new ArrayList<>();
        GlobalValues.selectedBedroomID = new ArrayList<>();

        GlobalValues.selectedRegions = new ArrayList<>();
        GlobalValues.selectedRegionsText = new ArrayList<>();
        autoLabelLocation = (AutoLabelUI) findViewById(R.id.auto_label_location_buy);
        searchEditText = (EditText) findViewById(R.id.searchEditText);
        search_progress = (ProgressBar) findViewById(R.id.search_progress);
        searchResultLay = (CardView) findViewById(R.id.searchResultLay);
        searchResult = (CustomExpandable) findViewById(R.id.searchResult);

        priceRangeBar = findViewById(R.id.price_range_bar);
        trMinMaxPrice = findViewById(R.id.tr_min_max_price);
        tvMinPrice = findViewById(R.id.tv_min_price);
        tvMaxPrice = findViewById(R.id.tv_max_price);

        GlobalValues.selectedPropertyType = "";
        GlobalValues.selectedPropertyTypeID = "";
        locationInfo = new ArrayList<>();
        text_city_list = (TextView) findViewById(R.id.text_city_list);
        tvPropertyType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPropertyTypeSpinner();
            }
        });
        updateRangeBarProperties();
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
        try {
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
        updateThePriceSelection(0, filterPriceList.size() - 1);
    }

    private void updatePreSelectedValueInField() {
        tvMinPrice.setText(String.format(getString(R.string.min_price_with_value), selectedMinBudgetPriceModel != null ? selectedMinBudgetPriceModel.getPriceValue() : filterPriceList.get(0).getPrice()));
        tvMaxPrice.setText(String.format(getString(R.string.max_price_with_value), selectedMaxBudgetPriceModel != null ? selectedMaxBudgetPriceModel.getPriceValue() : filterPriceList.get(filterPriceList.size() - 1).getPrice()));
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
        minPrice = Integer.parseInt(checkPriceValue(selectedMinBudgetPriceModel).replace("+", ""));
        maxPrice = selectedMaxBudgetPriceModel.getPriceValue().contains("+") ? -1 : getValueFromString(selectedMaxBudgetPriceModel.getPriceValue());
        GlobalValues.selectedMinBudgetPriceModel = selectedMinBudgetPriceModel;
        GlobalValues.selectedMaxBudgetPriceModel = selectedMaxBudgetPriceModel;
        GlobalValues.selectedMinPrice = selectedMinBudgetPriceModel.getPriceValue().contains("+") ? "" : selectedMinBudgetPriceModel.getPrice();
        GlobalValues.selectedMinPriceValue = selectedMinBudgetPriceModel.getPriceValue().contains("+") ? "" : selectedMinBudgetPriceModel.getPriceValue();
        GlobalValues.selectedMaxPrice = selectedMaxBudgetPriceModel.getPriceValue().contains("+") ? "" : selectedMaxBudgetPriceModel.getPrice();
        GlobalValues.selectedMaxPriceValue = selectedMaxBudgetPriceModel.getPriceValue().contains("+") ? "" : selectedMaxBudgetPriceModel.getPriceValue();
    }

    private String checkPriceValue(BudgetPriceData budgetPriceModel) {
        return budgetPriceModel!= null && budgetPriceModel.getPrice()!= null && !budgetPriceModel.getPrice().isEmpty() ? budgetPriceModel.getPrice() : "0";
    }

    private void showPropertyTypeSpinner() {
        final Dialog dialog = new Dialog(this);
        @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.show_property_type_selector, null);
        final ExpandableListView listView = (ExpandableListView) view.findViewById(R.id.list_view_prop_type_selector);
        final AdapterTypeSelector adapter = new AdapterTypeSelector(SearchOptionActivity.this, propertyTypeList);
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
                GlobalValues.selectedPropertyTypeID = "";
                GlobalValues.selectedPropertyType = "";
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
                if (!GlobalValues.selectedPropertyType.equals(child.getPropertyTypeName())) {
                    GlobalValues.selectedPropertyType = child.getPropertyTypeName();
                    GlobalValues.selectedPropertyTypeID = child.getPropertyTypeID();
                    textBedRooms.setHint(getResources().getString(R.string.select));
                    GlobalValues.selectedBedrooms = new ArrayList<>();
                    GlobalValues.selectedBedroomID = new ArrayList<>();

                    GlobalValues.selectedBedroomsNumber = "";
                    textBedRooms.setText("");
                    getBedRoomNumbers();
                }
                return false;
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
                                count++;
                                locationInfo.clear();
                                locationInfo.addAll(response.body().getData().getCityList());
                                showCityListSpinner();
                            } else
                                mLoading.dismiss();
                        } else {
                            mLoading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<CityListingResponse> call, Throwable t) {
                        mLoading.dismiss();
                    }
                });
    }

    private void showCityListSpinner() {
        if (SessionManager.getLocationId(this).equals("-1")) {
            String address = GlobalValues.address;
            address = address.toLowerCase();
            for (int i = 0; i < locationInfo.size(); i++) {
                String cityName = locationInfo.get(i).getCityName();
                if (address.contains(cityName.toLowerCase())) {
                    selectedCityID = locationInfo.get(i).getCityID();
                    SessionManager.setLocationName(SearchOptionActivity.this, locationInfo.get(i).getCityName(),
                            locationInfo.get(i).getCityLat(), locationInfo.get(i).getCityLng());

                }
            }
            if (!selectedCityID.equals(""))
                SessionManager.setLocationId(SearchOptionActivity.this, selectedCityID);
            else {
                SessionManager.setLocationId(SearchOptionActivity.this, "1");

            }
        }
        text_city_list.setText(SessionManager.getLocationName(this));
        final Dialog dialog = new Dialog(this);
        @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.show_price_list_selector, null);
        final RecyclerView recyclerMinPrice = (RecyclerView) view.findViewById(R.id.recycler_price_list);
        recyclerMinPrice.setLayoutManager(new LinearLayoutManager(SearchOptionActivity.this));
        AdapterCityListing adapter = new AdapterCityListing(locationInfo, SearchOptionActivity.this, SessionManager.getLocationId(this));
        recyclerMinPrice.setAdapter(adapter);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        text_city_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        adapter.setMinPriceInterface(new AdapterCityListing.SelectCityInterface() {
            @Override
            public void onPriceSelected(LocationInfo locationInfo) {
                text_city_list.setText(locationInfo.getCityName());
                selectedCityID = locationInfo.getCityID();
                SessionManager.setLocationId(SearchOptionActivity.this, selectedCityID);
                SessionManager.setLocationName(SearchOptionActivity.this, locationInfo.getCityName(),
                        locationInfo.getCityLat(), locationInfo.getCityLng());
                GlobalValues.regionDataTemp.clear();
                autoLabelLocation.clear();
                dialog.dismiss();
                //getIndexList();
            }
        });
        if (SessionManager.getLocationId(this).equals("-1")) {
            Toast.makeText(SearchOptionActivity.this, "Please select your City", Toast.LENGTH_LONG).show();
            dialog.show();
        } else {
            // getIndexList();
        }
    }

    private void setAdapters() {
        adapterLocations = new ArrayAdapter<>(this, R.layout.auto_complete_custom_item,
                R.id.autoCompleteItem, locations);
        editLocations.setAdapter(adapterLocations);
        adapterLocations.notifyDataSetChanged();
    }

    @SuppressLint("NewApi")
    private void closeKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void setPropertyTypeRecycler(List<PropertyTypeSub> typeMainList) {
        bedRoomList = new ArrayList<>();
        propertyTypeRecycler.setAdapter(new PropertyTypeAdapter(SearchOptionActivity.this,
                imagePath, typeMainList, new PropertyTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PropertyTypeSub child) {
                if (!GlobalValues.selectedPropertyType.equals(child.getPropertyTypeName())) {
                    GlobalValues.selectedPropertyType = child.getPropertyTypeName();
                    GlobalValues.selectedPropertyTypeID = child.getPropertyTypeID();
                    textBedRooms.setText(getResources().getString(R.string.select));
                    if (child.getPropertyTypeName().contains("Land") || child.getPropertyTypeName().contains("Lands")
                            || child.getPropertyTypeName().contains("Plot") || child.getPropertyTypeName().contains("Plots")) {
                        bedRoomList.clear();
                        bedroom_text.setVisibility(View.GONE);
                        bedroomRecycler.setVisibility(View.GONE);
                    } else {
                        getBedRoomNumbers();
                    }
                }
            }
        }));

    }

    public void setBedRoomRecycler() {
        GlobalValues.selectedBedrooms.clear();
        GlobalValues.selectedBedroomID.clear();

        BedRoomAdapter bedRoomAdapter = new BedRoomAdapter(bedRoomList);
        bedroomRecycler.setAdapter(bedRoomAdapter);
        bedroom_text.setVisibility(View.VISIBLE);
        bedroomRecycler.setVisibility(View.VISIBLE);
        GlobalValues.selectedBedroomsID = bedRoomList.get(0).getAttributeID();

    }


    //TODO Main functions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
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

}
