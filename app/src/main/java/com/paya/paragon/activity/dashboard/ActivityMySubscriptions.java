package com.paya.paragon.activity.dashboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.paya.paragon.adapter.AdapterMySubscriptions;
import com.paya.paragon.api.mySubscriptions.MySubscriptionsApi;
import com.paya.paragon.api.mySubscriptions.MySubscriptionsData;
import com.paya.paragon.api.mySubscriptions.MySubscriptionsResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"RedundantCast", "HardCodedStringLiteral"})
public class ActivityMySubscriptions extends AppCompatActivity implements View.OnClickListener {

    DialogProgress mLoading;
    public ArrayList<MySubscriptionsData> mySubscriptions = new ArrayList<>();
    AdapterMySubscriptions adapterMySubscriptions;
    RecyclerView recyclerMySubscriptions;
    public Dialog alertDialog;

    //Side Menu
    public DrawerLayout drawer;
    NavigationView navigationView;
    ImageView mDrawerProfileImage;
    TextView textSavedSearches, textShortlisted, textPostedRequirements, textAgents;
    TextView textPropertyEnquiry, textSettings, textLogout, textName, textEmail;
    TextView textEnquiriesOffers, textMyQuestions, textViewingRequest, textUnifiedTenancy, textOpenHouse;
    TextView btnPostFreeAd, btnPostRequirement, textHome, noRecords;
    LinearLayout profileLayout;
    ImageView mProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_my_subscriptions);
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.drawer_layout_my_subscriptions));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        mTitle.setText(R.string.my_subscriptions);
        mProfileImage = toolbar.findViewById(R.id.profile_image_my_account);

        declarations();

        if (!Utils.NoInternetConnection(ActivityMySubscriptions.this)) {
            getMySubscriptions();
        } else {
            Utils.NoInternetConnection(ActivityMySubscriptions.this);
            noRecords.setVisibility(View.VISIBLE);
        }
        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

    }


    //TODO Set Data.
    private void declarations() {
        recyclerMySubscriptions = (RecyclerView) findViewById(R.id.recycler_my_subscriptions);
        mLoading = new DialogProgress(ActivityMySubscriptions.this);
        recyclerMySubscriptions.setLayoutManager(new LinearLayoutManager(ActivityMySubscriptions.this));
        mLoading = new DialogProgress(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_my_subscriptions);
        navigationView = (NavigationView) findViewById(R.id.nav_view_my_subscriptions);

        textShortlisted = navigationView.findViewById(R.id.text_shortlisted_dashboard_drawer);
        textHome = navigationView.findViewById(R.id.text_home_dashboard_drawer);
        textName = navigationView.findViewById(R.id.user_name_dashboard_drawer);
        textEmail = navigationView.findViewById(R.id.user_email_dashboard_drawer);
        btnPostFreeAd = navigationView.findViewById(R.id.text_sell_post_ad_dashboard_drawer);
        btnPostRequirement = navigationView.findViewById(R.id.post_requirement_dashboard_drawer);
        textSavedSearches = navigationView.findViewById(R.id.text_saved_search_dashboard_drawer);
        textPostedRequirements = navigationView.findViewById(R.id.text_posted_requirements_dashboard_drawer);
        textAgents = navigationView.findViewById(R.id.text_agents_dashboard_drawer);
        textPropertyEnquiry = navigationView.findViewById(R.id.text_property_enquiry_dashboard_drawer);
        textSettings = navigationView.findViewById(R.id.text_settings_dashboard_drawer);
        textLogout = navigationView.findViewById(R.id.text_logout_dashboard_drawer);
        mDrawerProfileImage = navigationView.findViewById(R.id.profile_image_dashboard_drawer);
        profileLayout = navigationView.findViewById(R.id.layout_view_profile_dashboard_drawer);
        noRecords = findViewById(R.id.text_no_records_found);
        noRecords.setVisibility(View.GONE);
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
        textEnquiriesOffers.setOnClickListener(this);
        textMyQuestions.setOnClickListener(this);
        textViewingRequest.setOnClickListener(this);
        textUnifiedTenancy.setOnClickListener(this);
        textOpenHouse.setOnClickListener(this);

        setProfileImage();

        textName.setText(SessionManager.getFullName(ActivityMySubscriptions.this));
        textEmail.setText(SessionManager.getEmail(ActivityMySubscriptions.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    //TODO API Calls
    private void getMySubscriptions() {
        mLoading.show();
        ApiLinks.getClient().create(MySubscriptionsApi.class)
                .post("" + SessionManager.getLanguageID(ActivityMySubscriptions.this),
                        SessionManager.getAccessToken(ActivityMySubscriptions.this))
                .enqueue(new Callback<MySubscriptionsResponse>() {
                    @Override
                    public void onResponse(Call<MySubscriptionsResponse> call,
                                           Response<MySubscriptionsResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            if (response.body().getResponse().equalsIgnoreCase("Success")) {
                                if (response.body().getData() != null) {
                                    mySubscriptions = response.body().getData();
                                    adapterMySubscriptions = new AdapterMySubscriptions(ActivityMySubscriptions.this,
                                            mySubscriptions);
                                    recyclerMySubscriptions.setAdapter(adapterMySubscriptions);
                                    adapterMySubscriptions.notifyDataSetChanged();
                                } else {
                                    noRecords.setVisibility(View.VISIBLE);
                                }
                                mLoading.dismiss();
                            } else if (response.body().getCode() != null && response.body().getCode() == 409) {
                                Utils.showAlertLogout(ActivityMySubscriptions.this, message);
                            } else {
                                mLoading.dismiss();
                                noRecords.setVisibility(View.VISIBLE);
                            }
                        } else {
                            mLoading.dismiss();
                            noRecords.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<MySubscriptionsResponse> call, Throwable t) {
                        mLoading.dismiss();
                        noRecords.setVisibility(View.VISIBLE);
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
        Intent intent = new Intent(ActivityMySubscriptions.this, ActivityHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }


    //TODO Click Listener
    @Override
    public void onClick(View view) {
        if (view == profileLayout) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMySubscriptions.this, ProfileActivity.class);
            startActivity(intent);

        } else if (view == btnPostFreeAd) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMySubscriptions.this, PostPropertyPage01Activity.class);
            startActivity(intent);

        } else if (view == btnPostRequirement) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMySubscriptions.this, ActivityRequirementPurpose.class);
            startActivity(intent);

        } else if (view == textSavedSearches) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMySubscriptions.this, ActivitySavedSearchList.class);
            startActivity(intent);
            finish();

        } else if (view == textShortlisted) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMySubscriptions.this, ActivityShortlisted.class);
            startActivity(intent);

        } else if (view == textPostedRequirements) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMySubscriptions.this, ActivityPostedRequirements.class);
            startActivity(intent);

        } else if (view == textAgents) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMySubscriptions.this, ActivityMyAgents.class);
            startActivity(intent);

        } else if (view == textPropertyEnquiry) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMySubscriptions.this, ActivityMyProperties.class);
            startActivity(intent);

        } else if (view == textSettings) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMySubscriptions.this, ActivitySettings.class);
            startActivity(intent);

        } else if (view == textEnquiriesOffers) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMySubscriptions.this, ActivityEnquiriesOffers.class);
            startActivity(intent);

        } else if (view == textViewingRequest) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMySubscriptions.this, ActivityViewingRequests.class);
            startActivity(intent);

        } else if (view == textUnifiedTenancy) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMySubscriptions.this, ActivityUnifiedTenancyContract.class);
            startActivity(intent);

        } else if (view == textOpenHouse) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMySubscriptions.this, ActivityOpenHouse.class);
            startActivity(intent);

        } else if (view == textMyQuestions) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMySubscriptions.this, ActivityMyQuestions.class);
            startActivity(intent);

        } else if (view == textLogout) {
            drawer.closeDrawer(GravityCompat.END);
            alertLogout();

        } else if (view == textHome) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMySubscriptions.this, ActivityHome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
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
                Intent intent = new Intent(ActivityMySubscriptions.this, ActivityHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                SessionManager.logout(ActivityMySubscriptions.this);
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

}
