package com.paya.paragon.activity.dashboard;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.paya.paragon.R;
import com.paya.paragon.activity.ActivityHome;
import com.paya.paragon.activity.postProperty.PostPropertyPage01Activity;
import com.paya.paragon.activity.postRequirements.ActivityRequirementPurpose;
import com.paya.paragon.adapter.AdapterOpenHouseList;
import com.paya.paragon.adapter.AdapterPropertyNameList;
import com.paya.paragon.api.addOpenHouseSlot.AddOpenHouseSlotApi;
import com.paya.paragon.api.addOpenHouseSlot.AddOpenHouseSlotResponse;
import com.paya.paragon.api.myPropertyNameList.PropertyNamesApi;
import com.paya.paragon.api.myPropertyNameList.PropertyNamesData;
import com.paya.paragon.api.myPropertyNameList.PropertyNamesResponse;
import com.paya.paragon.api.openHouseSlots.OpenHouseModel;
import com.paya.paragon.api.openHouseSlots.OpenHouseSlotListApi;
import com.paya.paragon.api.openHouseSlots.OpenHouseSlotListResponse;
import com.paya.paragon.api.openHouseSlotsDeleted.DeleteOpenHouseSlotApi;
import com.paya.paragon.api.openHouseSlotsDeleted.DeleteOpenHouseSlotResponse;
import com.paya.paragon.classes.CustomSpinner;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("HardCodedStringLiteral")
@SuppressLint("StaticFieldLeak")
public class ActivityOpenHouse extends AppCompatActivity implements AdapterOpenHouseList.ItemClickAdapterListener, View.OnClickListener {

    private SimpleDateFormat showDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private SimpleDateFormat sendDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
    AdapterOpenHouseList adapterOpenHouseList;
    public static RecyclerView recyclerOpenHouse;
    public static ArrayList<OpenHouseModel> openHouseModels;
    ArrayList<PropertyNamesData> propertyNameList;
    PropertyNamesData selectedProperty;
    AdapterPropertyNameList adapterPropertyNameList;
    TextView selectorProperty, startDate, endDate;
    TextView addSlot;
    static DialogProgress mLoading;
    CustomSpinner spinnerPropertyName;
    boolean isStartDateSelected = false;
    String strStartDate = "", strEndDate = "";
    String strSendStartDate = "", strSendEndDate = "";

    LinearLayout llMain;
    TextView tvNoItem;
    private DialogProgress mLoadingDialog;
    ImageView mProfileImage;
    public DrawerLayout drawer;
    NavigationView navigationView;
    ImageView mDrawerProfileImage;
    TextView textSavedSearches, textShortlisted, textPostedRequirements, textAgents;
    TextView textEnquiriesOffers, textMyQuestions, textViewingRequest, textUnifiedTenancy, textOpenHouse;
    TextView textPropertyEnquiry, textSettings, textLogout, textName, textEmail;
    TextView btnPostFreeAd, btnPostRequirement, textHome, noRecords, textSubscriptions;
    LinearLayout profileLayout;
    public Dialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_house);
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.drawer_layout_open_house));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(16);
        mTitle.setText(R.string.open_house);
        mProfileImage = toolbar.findViewById(R.id.profile_image_my_account);
        declarations();

        if (!Utils.NoInternetConnection(ActivityOpenHouse.this)) {
            getMyPropertyListing();
        } else {
            Utils.NoInternetConnection(ActivityOpenHouse.this);
        }

        selectorProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerPropertyName.performClick();
            }
        });

        spinnerPropertyName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectorProperty.setText(propertyNameList.get(position).getPropertyName());
                selectedProperty = propertyNameList.get(position);
                getPropertySlotList(selectedProperty.getPropertyID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (propertyNameList.size() != 0) {
                    selectedProperty = propertyNameList.get(0);
                }
            }
        });

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDateSelected = true;
                showSingleDatePicker();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStartDateSelected) {
                    isStartDateSelected = false;
                    showSingleDatePicker();
                } else
                    Toast.makeText(ActivityOpenHouse.this, getString(R.string.select_start_date), Toast.LENGTH_SHORT).show();


            }
        });

        addSlot.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View v) {
                addSlot();
            }
        });
    }


    //TODO API Calls
    private void getMyPropertyListing() {
        mLoading.show();
        ApiLinks.getClient().create(PropertyNamesApi.class).post(
                "listProperty",
                SessionManager.getAccessToken(ActivityOpenHouse.this),
                SessionManager.getLanguageID(this))
                .enqueue(new Callback<PropertyNamesResponse>() {
                    @Override
                    public void onResponse(Call<PropertyNamesResponse> call, Response<PropertyNamesResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getResponse();
                            int code = response.body().getCode();
                            if (code == 4004 && message.equals("Success")) {
                                propertyNameList = new ArrayList<>();
                                propertyNameList = response.body().getData();
                                if (propertyNameList.size() > 0) {
                                    llMain.setVisibility(View.VISIBLE);
                                    mLoading.hide();
                                    tvNoItem.setVisibility(View.GONE);
                                    adapterPropertyNameList = new AdapterPropertyNameList(ActivityOpenHouse.this, propertyNameList);
                                    spinnerPropertyName.setAdapter(adapterPropertyNameList);
                                    getPropertySlotList(propertyNameList.get(0).getPropertyID());
                                } else {
                                    tvNoItem.setVisibility(View.VISIBLE);
                                    mLoading.hide();
                                }
                            } else {
                                tvNoItem.setVisibility(View.VISIBLE);
                                mLoading.hide();
                            }
                        } else {
                            tvNoItem.setVisibility(View.VISIBLE);
                            mLoading.hide();
                            Toast.makeText(ActivityOpenHouse.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PropertyNamesResponse> call, Throwable t) {
                        tvNoItem.setVisibility(View.VISIBLE);
                        mLoading.hide();
                        Toast.makeText(ActivityOpenHouse.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getPropertySlotList(String propertyID) {
        mLoadingDialog.show();
        ApiLinks.getClient().create(OpenHouseSlotListApi.class).post(
                "listSlot",
                propertyID,
                SessionManager.getLanguageID(this))
                .enqueue(new Callback<OpenHouseSlotListResponse>() {
                    @Override
                    public void onResponse(Call<OpenHouseSlotListResponse> call, Response<OpenHouseSlotListResponse> response) {
                        if (response.isSuccessful()) {
                            mLoadingDialog.dismiss();
                            String message = response.body().getResponse();
                            if (message.equals("Success")) {
                                tvNoItem.setVisibility(View.GONE);
                                openHouseModels = new ArrayList<>();
                                if (response.body().getData().getOpenHouseList() != null) {
                                    openHouseModels = response.body().getData().getOpenHouseList();
                                    adapterOpenHouseList = new AdapterOpenHouseList(ActivityOpenHouse.this, openHouseModels, ActivityOpenHouse.this);
                                    recyclerOpenHouse.setAdapter(adapterOpenHouseList);
                                } else {
                                    adapterOpenHouseList.notifyDataSetChanged();
                                }
                            } else {
                                openHouseModels = new ArrayList<>();
                                adapterOpenHouseList = new AdapterOpenHouseList(ActivityOpenHouse.this, openHouseModels, ActivityOpenHouse.this);
                                recyclerOpenHouse.setAdapter(adapterOpenHouseList);
                                tvNoItem.setVisibility(View.VISIBLE);
                                tvNoItem.setText(R.string.no_slots_listed);
                            }
                        } else {
                            mLoadingDialog.dismiss();
                            Toast.makeText(ActivityOpenHouse.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OpenHouseSlotListResponse> call, Throwable t) {
                        mLoadingDialog.dismiss();
                        Toast.makeText(ActivityOpenHouse.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addSlot() {
        try {
            Date selectedStartDate, selectedEndDate;
            if (!strStartDate.equals("") && !strEndDate.equals("")) {
                selectedStartDate = showDateFormat.parse(strStartDate);
                selectedEndDate = showDateFormat.parse(strEndDate);
                int compareStatus = selectedEndDate.compareTo(selectedStartDate);
                long diffInMillies = Math.abs(selectedEndDate.getTime() - selectedStartDate.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
//                assertEquals(diff, 6);
                if (diff == 1 || diff == 0) {
                    addSlotToProperty();
                } else {
                    Toast.makeText(ActivityOpenHouse.this, getString(R.string.date_valid), Toast.LENGTH_SHORT).show();
                }
            } else {
                if (strStartDate.equals("")) {
                    Toast.makeText(ActivityOpenHouse.this, getString(R.string.select_start_date), Toast.LENGTH_SHORT).show();
                } else if (strEndDate.equals("")) {
                    Toast.makeText(ActivityOpenHouse.this, getString(R.string.select_end_date), Toast.LENGTH_SHORT).show();
                }/* else {
                    selectedStartDate = showDateFormat.parse(strStartDate);
                    Date dates;
                    Calendar ca = Calendar.getInstance();
                    ca.set(Calendar.HOUR_OF_DAY, 0);
                    ca.set(Calendar.MINUTE, 0);
                    ca.set(Calendar.SECOND, 0);
                    dates = ca.getTime();
                    int compareStatus = selectedStartDate.compareTo(dates);
                    if (compareStatus < 0) {
                        Toast.makeText(ActivityOpenHouse.this, "Select a Valid Date", Toast.LENGTH_LONG).show();
                    } else {

                            selectedEndDate = showDateFormat.parse(strEndDate);
                            if (selectedEndDate.compareTo(dates) < 1) {
                                Toast.makeText(ActivityOpenHouse.this, "Invalid End Date", Toast.LENGTH_SHORT).show();
                        }
                    }
                }*/
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void addSlotToProperty() {
        mLoadingDialog.show();
        ApiLinks.getClient().create(AddOpenHouseSlotApi.class).post(
                "addSlot", strSendStartDate + " 0:00", strSendEndDate + " 0:00", selectedProperty.getPropertyID(), "0")
                .enqueue(new Callback<AddOpenHouseSlotResponse>() {
                    @Override
                    public void onResponse(Call<AddOpenHouseSlotResponse> call, Response<AddOpenHouseSlotResponse> response) {
                        if (response.isSuccessful()) {
                            mLoadingDialog.dismiss();
                            String status = response.body().getResponse();
                            String message = response.body().getMessage();
                            int code = response.body().getCode();
                            if (code == 4004 && status.equals("Success")) {
                                if (message.equals("slotExist")) {
                                    Toast.makeText(ActivityOpenHouse.this, getString(R.string.slot_already_exist), Toast.LENGTH_SHORT).show();
                                    strStartDate = "";
                                    strEndDate = "";
                                    endDate.setText("");
                                    startDate.setText("");
                                } else {
                                    Toast.makeText(ActivityOpenHouse.this, getString(R.string.slot_successfully_added), Toast.LENGTH_SHORT).show();
                                    strStartDate = "";
                                    strEndDate = "";
                                    endDate.setText("");
                                    startDate.setText("");
                                    getPropertySlotList(selectedProperty.getPropertyID());
                                }
                            } else {
                                Toast.makeText(ActivityOpenHouse.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            mLoadingDialog.dismiss();
                            Toast.makeText(ActivityOpenHouse.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddOpenHouseSlotResponse> call, Throwable t) {
                        mLoadingDialog.dismiss();
                        Toast.makeText(ActivityOpenHouse.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void deleteSlot(String propertyID, String slotID, final int position) {
        if (!Utils.NoInternetConnection(this)) {
            mLoadingDialog.show();
            ApiLinks.getClient().create(DeleteOpenHouseSlotApi.class).post(propertyID, slotID)
                    .enqueue(new Callback<DeleteOpenHouseSlotResponse>() {
                        @Override
                        public void onResponse(Call<DeleteOpenHouseSlotResponse> call, Response<DeleteOpenHouseSlotResponse> response) {
                            if (response.isSuccessful()) {
                                mLoadingDialog.dismiss();
                                String message = response.body().getResponse();
                                int code = response.body().getCode();
                                if (code == 4004 && message.equals("Success")) {
                                    Utils.collapse(recyclerOpenHouse.getChildAt(position), 400);
                             /*       new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            openHouseModels.remove(position);
                                        }
                                    }, 400);*/
                                } else {
                                    Toast.makeText(ActivityOpenHouse.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                mLoadingDialog.dismiss();
                                Toast.makeText(ActivityOpenHouse.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DeleteOpenHouseSlotResponse> call, Throwable t) {
                            mLoadingDialog.dismiss();
                            Toast.makeText(ActivityOpenHouse.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Utils.showAlertNoInternet(ActivityOpenHouse.this);
        }
    }


    //TODO Set Data
    private void declarations() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_open_house);
        navigationView = (NavigationView) findViewById(R.id.nav_view_my_properties);

        textShortlisted = navigationView.findViewById(R.id.text_shortlisted_dashboard_drawer);
        textName = navigationView.findViewById(R.id.user_name_dashboard_drawer);
        textEmail = navigationView.findViewById(R.id.user_email_dashboard_drawer);
        btnPostFreeAd = navigationView.findViewById(R.id.text_sell_post_ad_dashboard_drawer);
        btnPostRequirement = navigationView.findViewById(R.id.post_requirement_dashboard_drawer);
        textSavedSearches = navigationView.findViewById(R.id.text_saved_search_dashboard_drawer);
        textPostedRequirements = navigationView.findViewById(R.id.text_posted_requirements_dashboard_drawer);
        textAgents = navigationView.findViewById(R.id.text_agents_dashboard_drawer);
        textPropertyEnquiry = navigationView.findViewById(R.id.text_property_enquiry_dashboard_drawer);
        textSettings = navigationView.findViewById(R.id.text_settings_dashboard_drawer);
        textHome = navigationView.findViewById(R.id.text_home_dashboard_drawer);
        textLogout = navigationView.findViewById(R.id.text_logout_dashboard_drawer);
        mDrawerProfileImage = navigationView.findViewById(R.id.profile_image_dashboard_drawer);
        profileLayout = navigationView.findViewById(R.id.layout_view_profile_dashboard_drawer);
        textSubscriptions = navigationView.findViewById(R.id.text_my_subscriptions_dashboard_drawer);


        textMyQuestions = navigationView.findViewById(R.id.text_my_questions_dashboard_drawer);
        textViewingRequest = navigationView.findViewById(R.id.text_viewing_request_dashboard_drawer);
        textEnquiriesOffers = navigationView.findViewById(R.id.text_enquiries_offers_dashboard_drawer);
        textUnifiedTenancy = navigationView.findViewById(R.id.text_unified_dashboard_drawer);
        textOpenHouse = navigationView.findViewById(R.id.text_open_house_dashboard_drawer);

        textShortlisted.setOnClickListener(this);
        btnPostFreeAd.setOnClickListener(this);
        btnPostRequirement.setOnClickListener(this);
        textSavedSearches.setOnClickListener(this);
        textPostedRequirements.setOnClickListener(this);
        textAgents.setOnClickListener(this);
        textPropertyEnquiry.setOnClickListener(this);
        textSettings.setOnClickListener(this);
        textLogout.setOnClickListener(this);
        profileLayout.setOnClickListener(this);
        textHome.setOnClickListener(this);
        textSubscriptions.setOnClickListener(this);
        mProfileImage.setOnClickListener(this);

        textEnquiriesOffers.setOnClickListener(this);
        textMyQuestions.setOnClickListener(this);
        textViewingRequest.setOnClickListener(this);
        textUnifiedTenancy.setOnClickListener(this);
        textOpenHouse.setOnClickListener(this);
        mLoadingDialog = new DialogProgress(this);
        llMain = findViewById(R.id.layout_open_house_content);
        llMain.setVisibility(View.GONE);
        tvNoItem = findViewById(R.id.tv_no_item);
        tvNoItem.setVisibility(View.GONE);
        recyclerOpenHouse = (RecyclerView) findViewById(R.id.recycler_open_house_listing);
        selectorProperty = (TextView) findViewById(R.id.property_name_open_house);
        spinnerPropertyName = (CustomSpinner) findViewById(R.id.spinner_property_names_open_house);
        startDate = (TextView) findViewById(R.id.text_start_date_open_house);
        endDate = (TextView) findViewById(R.id.text_end_date_open_house);
        addSlot = (TextView) findViewById(R.id.add_slot_open_house);
        mLoading = new DialogProgress(this);
        selectedProperty = new PropertyNamesData();

        recyclerOpenHouse.setLayoutManager(new LinearLayoutManager(ActivityOpenHouse.this));
        openHouseModels = new ArrayList<>();
        adapterOpenHouseList = new AdapterOpenHouseList(ActivityOpenHouse.this, openHouseModels, this);
        recyclerOpenHouse.setAdapter(adapterOpenHouseList);
        setProfileImage();
    }

    public void setProfileImage() {
        if (mProfileImage != null && mDrawerProfileImage != null &&
                textName != null && textEmail != null) {
            textName.setText(SessionManager.getFullName(this));
            textEmail.setText(SessionManager.getEmail(this));

            String url = SessionManager.getProfileImage(this);
            if (url != null && !url.equals("")) {
                Utils.loadUrlImage(mProfileImage, url, R.drawable.icon_profile, true);
                Utils.loadUrlImage(mDrawerProfileImage, url, R.drawable.icon_profile, true);
            }
        }
    }

    private void alertLogout() {
        alertDialog = new Dialog(this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_exit_popup, null);
        TextView ok = alert_layout.findViewById(R.id.alert_ok_button);
        TextView cancel = alert_layout.findViewById(R.id.alert_cancel_button);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                SessionManager.logout(ActivityOpenHouse.this);
                Intent intent = new Intent(ActivityOpenHouse.this, ActivityHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    public void toggleDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(Gravity.START); //CLOSE Nav Drawer!
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(Gravity.END); //CLOSE Nav Drawer!
        }
    }

    private void showSingleDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(ActivityOpenHouse.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                if (isStartDateSelected) {
                    startDate.setText(showDateFormat.format(calendar.getTime()));
                    strStartDate = showDateFormat.format(calendar.getTime());
                    strSendStartDate = sendDateFormat.format(calendar.getTime());
                } else {
                    endDate.setText(showDateFormat.format(calendar.getTime()));
                    strEndDate = showDateFormat.format(calendar.getTime());
                    strSendEndDate = sendDateFormat.format(calendar.getTime());
                }
            }
        }, yy, mm, dd);
        if (!isStartDateSelected) {
            try {
                Date minDate = showDateFormat.parse(strStartDate);
                datePicker.getDatePicker().setMinDate(minDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        }
        datePicker.show();

    }

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
        Intent intent = new Intent(ActivityOpenHouse.this, ActivityHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void deleteClick(String propertyID, String slotID, int position) {
        deleteSlot(propertyID, slotID, position);

    }

    @Override
    public void onClick(View view) {
        if (view == mProfileImage) {
            drawer.openDrawer(Gravity.END);

        }
        if (view == profileLayout) {
            toggleDrawer();
            Intent in = new Intent(ActivityOpenHouse.this, ProfileActivity.class);
            startActivity(in);

        } else if (view == btnPostFreeAd) {
            toggleDrawer();
            Intent intent = new Intent(ActivityOpenHouse.this, PostPropertyPage01Activity.class);
            startActivity(intent);

        } else if (view == btnPostRequirement) {
            toggleDrawer();
            Intent intent = new Intent(ActivityOpenHouse.this, ActivityRequirementPurpose.class);
            startActivity(intent);

        } else if (view == textSavedSearches) {
            toggleDrawer();
            Intent intent = new Intent(ActivityOpenHouse.this, ActivitySavedSearchList.class);
            startActivity(intent);

        } else if (view == textShortlisted) {
            toggleDrawer();
            Intent intent = new Intent(ActivityOpenHouse.this, ActivityShortlisted.class);
            startActivity(intent);

        } else if (view == textPostedRequirements) {
            toggleDrawer();
            Intent intent = new Intent(ActivityOpenHouse.this, ActivityPostedRequirements.class);
            startActivity(intent);

        } else if (view == textAgents) {
            toggleDrawer();
            Intent intent = new Intent(ActivityOpenHouse.this, ActivityMyAgents.class);
            startActivity(intent);

        } else if (view == textPropertyEnquiry) {
            toggleDrawer();
            Intent intent = new Intent(ActivityOpenHouse.this, ActivityMyProperties.class);
            startActivity(intent);

        } else if (view == textSettings) {
            toggleDrawer();
            Intent intent = new Intent(ActivityOpenHouse.this, ActivitySettings.class);
            startActivity(intent);

        } else if (view == textSubscriptions) {
            toggleDrawer();
            Intent intent = new Intent(ActivityOpenHouse.this, ActivityMySubscriptions.class);
            startActivity(intent);

        } else if (view == textEnquiriesOffers) {
            toggleDrawer();
            Intent intent = new Intent(ActivityOpenHouse.this, ActivityEnquiriesOffers.class);
            startActivity(intent);

        } else if (view == textViewingRequest) {
            toggleDrawer();
            Intent intent = new Intent(ActivityOpenHouse.this, ActivityViewingRequests.class);
            startActivity(intent);

        } else if (view == textUnifiedTenancy) {
            toggleDrawer();
            Intent intent = new Intent(ActivityOpenHouse.this, ActivityUnifiedTenancyContract.class);
            startActivity(intent);

        } else if (view == textOpenHouse) {
            toggleDrawer();


        } else if (view == textMyQuestions) {
            toggleDrawer();
            Intent intent = new Intent(ActivityOpenHouse.this, ActivityMyQuestions.class);
            startActivity(intent);

        } else if (view == textLogout) {
            toggleDrawer();
            alertLogout();

        } else if (view == textHome) {
            toggleDrawer();
            Intent intent = new Intent(ActivityOpenHouse.this, ActivityHome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
    }
}
