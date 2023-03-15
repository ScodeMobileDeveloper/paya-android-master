package com.paya.paragon.activity.dashboard;

import android.annotation.SuppressLint;
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
import com.paya.paragon.adapter.AdapterViewRequest;
import com.paya.paragon.api.viewingRequests.ViewingRequestApi;
import com.paya.paragon.api.viewingRequests.ViewingRequestData;
import com.paya.paragon.api.viewingRequests.ViewingRequestResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("HardCodedStringLiteral")
public class ActivityViewingRequests extends AppCompatActivity implements View.OnClickListener {


    static ArrayList<ViewingRequestData> viewRequestModels;
    AdapterViewRequest adapterViewRequest;
    RecyclerView recyclerViewRequests;
    DialogProgress mLoading;

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
    TextView tvNoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing_request);
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.drawer_layout_viewing_request));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(16);
        mTitle.setText(R.string.viewing_request);
        mProfileImage = toolbar.findViewById(R.id.profile_image_my_account);
        declarations();

        if (!Utils.NoInternetConnection(ActivityViewingRequests.this)) {
            mLoading.show();
            getViewingRequests();
        } else {
            Utils.NoInternetConnection(ActivityViewingRequests.this);
        }
    }

    //TODO API Calls
    private void getViewingRequests() {
        ApiLinks.getClient().create(ViewingRequestApi.class).post(
                SessionManager.getAccessToken(this),
                SessionManager.getLanguageID(this))
                .enqueue(new Callback<ViewingRequestResponse>() {
                    @Override
                    public void onResponse(Call<ViewingRequestResponse> call, Response<ViewingRequestResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String status = response.body().getResponse();
                            int code = response.body().getCode();
                            if (code == 4004 && status.equalsIgnoreCase("Success")) {
                                //Toast.makeText(ActivityViewingRequests.this, message, Toast.LENGTH_SHORT).show();
                                if (response.body().getData() != null && response.body().getData().size() != 0) {
                                    tvNoItem.setVisibility(View.GONE);
                                    viewRequestModels = response.body().getData();
                                    adapterViewRequest = new AdapterViewRequest(ActivityViewingRequests.this,
                                            viewRequestModels);
                                    recyclerViewRequests.setAdapter(adapterViewRequest);
                                } else {
                                    tvNoItem.setVisibility(View.VISIBLE);
                                }
                                mLoading.hide();
                            } else {
                                tvNoItem.setVisibility(View.VISIBLE);
                                mLoading.hide();
                            }
                        } else {
                            mLoading.hide();
                            Toast.makeText(ActivityViewingRequests.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ViewingRequestResponse> call, Throwable t) {
                        mLoading.hide();
                        Toast.makeText(ActivityViewingRequests.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    //TODO Set Data
    private void declarations() {

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_viewing_request);
        navigationView = (NavigationView) findViewById(R.id.nav_view_my_properties);
        tvNoItem = findViewById(R.id.tv_no_item);
        tvNoItem.setVisibility(View.GONE);

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
        recyclerViewRequests = (RecyclerView) findViewById(R.id.recycler_view_request);
        recyclerViewRequests.setLayoutManager(new LinearLayoutManager(ActivityViewingRequests.this));
        viewRequestModels = new ArrayList<>();
        adapterViewRequest = new AdapterViewRequest(ActivityViewingRequests.this, viewRequestModels);
        recyclerViewRequests.setAdapter(adapterViewRequest);
        mLoading = new DialogProgress(this);
        mLoading.hide();

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
                SessionManager.logout(ActivityViewingRequests.this);
                Intent intent = new Intent(ActivityViewingRequests.this, ActivityHome.class);
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
        Intent intent = new Intent(ActivityViewingRequests.this, ActivityHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view == mProfileImage) {
            drawer.openDrawer(Gravity.END);

        }
        if (view == profileLayout) {
            toggleDrawer();
            Intent in = new Intent(ActivityViewingRequests.this, ProfileActivity.class);
            startActivity(in);

        } else if (view == btnPostFreeAd) {
            toggleDrawer();
            Intent intent = new Intent(ActivityViewingRequests.this, PostPropertyPage01Activity.class);
            startActivity(intent);

        } else if (view == btnPostRequirement) {
            toggleDrawer();
            Intent intent = new Intent(ActivityViewingRequests.this, ActivityRequirementPurpose.class);
            startActivity(intent);

        } else if (view == textSavedSearches) {
            toggleDrawer();
            Intent intent = new Intent(ActivityViewingRequests.this, ActivitySavedSearchList.class);
            startActivity(intent);

        } else if (view == textShortlisted) {
            toggleDrawer();
            Intent intent = new Intent(ActivityViewingRequests.this, ActivityShortlisted.class);
            startActivity(intent);

        } else if (view == textPostedRequirements) {
            toggleDrawer();
            Intent intent = new Intent(ActivityViewingRequests.this, ActivityPostedRequirements.class);
            startActivity(intent);

        } else if (view == textAgents) {
            toggleDrawer();
            Intent intent = new Intent(ActivityViewingRequests.this, ActivityMyAgents.class);
            startActivity(intent);

        } else if (view == textPropertyEnquiry) {
            toggleDrawer();
            Intent intent = new Intent(ActivityViewingRequests.this, ActivityMyProperties.class);
            startActivity(intent);

        } else if (view == textSettings) {
            toggleDrawer();
            Intent intent = new Intent(ActivityViewingRequests.this, ActivitySettings.class);
            startActivity(intent);

        } else if (view == textSubscriptions) {
            toggleDrawer();
            Intent intent = new Intent(ActivityViewingRequests.this, ActivityMySubscriptions.class);
            startActivity(intent);

        } else if (view == textEnquiriesOffers) {
            toggleDrawer();
            Intent intent = new Intent(ActivityViewingRequests.this, ActivityEnquiriesOffers.class);
            startActivity(intent);

        } else if (view == textViewingRequest) {
            toggleDrawer();


        } else if (view == textUnifiedTenancy) {
            toggleDrawer();
            Intent intent = new Intent(ActivityViewingRequests.this, ActivityUnifiedTenancyContract.class);
            startActivity(intent);

        } else if (view == textOpenHouse) {
            toggleDrawer();
            Intent intent = new Intent(ActivityViewingRequests.this, ActivityOpenHouse.class);
            startActivity(intent);

        } else if (view == textMyQuestions) {
            toggleDrawer();
            Intent intent = new Intent(ActivityViewingRequests.this, ActivityMyQuestions.class);
            startActivity(intent);

        } else if (view == textLogout) {
            toggleDrawer();
            alertLogout();

        } else if (view == textHome) {
            toggleDrawer();
            Intent intent = new Intent(ActivityViewingRequests.this, ActivityHome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
    }
}
