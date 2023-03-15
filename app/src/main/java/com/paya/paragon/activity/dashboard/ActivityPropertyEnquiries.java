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
import com.paya.paragon.adapter.AdapterMyPropertyEnquiries;
import com.paya.paragon.api.myPropertyEnquiry.MyPropertyEnquiryApi;
import com.paya.paragon.api.myPropertyEnquiry.MyPropertyEnquiryResponse;
import com.paya.paragon.api.myPropertyEnquiry.PropertyEnquiryInfo;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"RedundantCast", "HardCodedStringLiteral"})
public class ActivityPropertyEnquiries extends AppCompatActivity implements View.OnClickListener {

    ArrayList<PropertyEnquiryInfo> propertyEnquiryList = new ArrayList<>();
    AdapterMyPropertyEnquiries adapterPropertyEnquiries;
    RecyclerView recyclerPropertyEnquiries;
    DialogProgress mLoading;
    String propertyID = "";

    public DrawerLayout drawer;
    NavigationView navigationView;
    ImageView mDrawerProfileImage;
    TextView textSavedSearches, textShortlisted, textPostedRequirements, textAgents;
    TextView textPropertyEnquiry, textSettings, textLogout, textName, textEmail;
    TextView btnPostFreeAd, btnPostRequirement, textHome, noRecords, textSubscriptions;
    LinearLayout profileLayout;
    ImageView mProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_property_enquiries);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        mTitle.setText(R.string.properties_enquiries);
        mProfileImage = toolbar.findViewById(R.id.profile_image_my_account);

        propertyID = getIntent().getStringExtra("propertyID");

        declarations();

        if (!Utils.NoInternetConnection(ActivityPropertyEnquiries.this)) {
            mLoading.show();
            getPropertyEnquiries();
        } else {
            Utils.showAlertNoInternet(ActivityPropertyEnquiries.this);
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

    //TODO Set Data
    private void declarations() {
        recyclerPropertyEnquiries = (RecyclerView) findViewById(R.id.recycler_property_enquiries);
        recyclerPropertyEnquiries.setLayoutManager(new LinearLayoutManager(ActivityPropertyEnquiries.this));
        mLoading = new DialogProgress(ActivityPropertyEnquiries.this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_property_enquiries);
        navigationView = (NavigationView) findViewById(R.id.nav_view_property_enquiries);

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
        textSubscriptions = navigationView.findViewById(R.id.text_my_subscriptions_dashboard_drawer);
        noRecords = findViewById(R.id.text_no_records_found);
        noRecords.setVisibility(View.GONE);

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

        setProfileImage();

        textName.setText(SessionManager.getFullName(ActivityPropertyEnquiries.this));
        textEmail.setText(SessionManager.getEmail(ActivityPropertyEnquiries.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SessionManager.getAccessToken(this) != null
                && !SessionManager.getAccessToken(this).equals(""))
            setProfileImage();
        else finish();
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

    //TODO Click Listener
    @Override
    public void onClick(View view) {
        if (view == profileLayout) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityPropertyEnquiries.this, ProfileActivity.class);
            startActivity(intent);
            finish();

        } else if (view == btnPostFreeAd) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityPropertyEnquiries.this, PostPropertyPage01Activity.class);
            startActivity(intent);
            finish();

        } else if (view == btnPostRequirement) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityPropertyEnquiries.this, ActivityRequirementPurpose.class);
            startActivity(intent);
            finish();

        } else if (view == textSavedSearches) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityPropertyEnquiries.this, ActivitySavedSearchList.class);
            startActivity(intent);
            finish();

        } else if (view == textShortlisted) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityPropertyEnquiries.this, ActivityShortlisted.class);
            startActivity(intent);
            finish();

        } else if (view == textPostedRequirements) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityPropertyEnquiries.this, ActivityPostedRequirements.class);
            startActivity(intent);
            finish();

        } else if (view == textAgents) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityPropertyEnquiries.this, ActivityMyAgents.class);
            startActivity(intent);

        } else if (view == textPropertyEnquiry) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityPropertyEnquiries.this, ActivityMyProperties.class);
            startActivity(intent);
            finish();

        } else if (view == textSettings) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityPropertyEnquiries.this, ActivitySettings.class);
            startActivity(intent);
            finish();

        } else if (view == textSubscriptions) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityPropertyEnquiries.this, ActivityMySubscriptions.class);
            startActivity(intent);
            finish();

        } else if (view == textLogout) {
            drawer.closeDrawer(GravityCompat.END);
            alertLogout();

        } else if (view == textHome) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityPropertyEnquiries.this, ActivityHome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
    }

    Dialog alertDialog;

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
                Intent intent = new Intent(ActivityPropertyEnquiries.this, ActivityHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                SessionManager.logout(ActivityPropertyEnquiries.this);
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

    //TODO API Calls
    private void getPropertyEnquiries() {
        ApiLinks.getClient().create(MyPropertyEnquiryApi.class).post(
                propertyID,
                SessionManager.getAccessToken(ActivityPropertyEnquiries.this),
                "No")
                .enqueue(new Callback<MyPropertyEnquiryResponse>() {
                    @Override
                    public void onResponse(Call<MyPropertyEnquiryResponse> call,
                                           Response<MyPropertyEnquiryResponse> response) {
                        if (response.isSuccessful()) {
                            String status = response.body().getResponse();
                            // int code = response.body().getCode();
                            if (/*code == 4004 &&*/ status.equalsIgnoreCase("Success")) {
                                mLoading.dismiss();
                                propertyEnquiryList = response.body().getData().getEnquiryList();
                                adapterPropertyEnquiries = new AdapterMyPropertyEnquiries(ActivityPropertyEnquiries.this,
                                        propertyEnquiryList);
                                recyclerPropertyEnquiries.setAdapter(adapterPropertyEnquiries);
                            } else {
                                Toast.makeText(ActivityPropertyEnquiries.this, getString(R.string.no_enquiries),
                                        Toast.LENGTH_SHORT).show();
                                mLoading.dismiss();
                                noRecords.setVisibility(View.VISIBLE);
                            }
                        } else {
                            mLoading.dismiss();
                            noRecords.setVisibility(View.VISIBLE);
                            Toast.makeText(ActivityPropertyEnquiries.this, getString(R.string.no_response),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MyPropertyEnquiryResponse> call, Throwable t) {
                        mLoading.dismiss();
                        noRecords.setVisibility(View.VISIBLE);
                        Toast.makeText(ActivityPropertyEnquiries.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
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
