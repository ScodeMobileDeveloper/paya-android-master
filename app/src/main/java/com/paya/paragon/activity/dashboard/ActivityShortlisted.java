package com.paya.paragon.activity.dashboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.paya.paragon.R;
import com.paya.paragon.activity.ActivityHome;
import com.paya.paragon.activity.postProperty.PostPropertyPage01Activity;
import com.paya.paragon.activity.postRequirements.ActivityRequirementPurpose;
import com.paya.paragon.api.profileInfo.ProfileInfoApi;
import com.paya.paragon.api.profileInfo.ProfileInfoData;
import com.paya.paragon.api.profileInfo.ProfileInfoResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.fragments.shortlisted.FragmentShortListProject;
import com.paya.paragon.fragments.shortlisted.FragmentShortListProperty;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings({"HardCodedStringLiteral", "RedundantCast"})
@SuppressLint("SetTextI18n,StaticFieldLeak")
public class ActivityShortlisted extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;
    FragmentShortListProperty tab1;
    FragmentShortListProject tab2;
    int propertyCount = 0, projectCount = 0;
    public static LinearLayout tabView1, tabView2;
    TextView mTitle;

    public DrawerLayout drawer;
    NavigationView navigationView;
    ImageView mDrawerProfileImage;
    TextView textSavedSearches, textShortlisted, textPostedRequirements, textAgents;
    TextView textPropertyEnquiry, textSettings, textLogout, textName, textEmail;
    TextView textEnquiriesOffers, textMyQuestions, textViewingRequest, textUnifiedTenancy, textOpenHouse;

    TextView btnPostFreeAd, btnPostRequirement, textHome, textSubscriptions;
    LinearLayout profileLayout;
    ImageView mProfileImage;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shortlisted);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        mTitle.setText(R.string.shortlisted);
        mProfileImage = toolbar.findViewById(R.id.profile_image_my_account);


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        declarations();

        if (Utils.NoInternetConnection(ActivityShortlisted.this)) {
            Utils.showAlertNoInternet(ActivityShortlisted.this);
        } else {
            getProfileInfo();
        }

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.content_tab_buy_shortlisted);
        viewPager.setOffscreenPageLimit(1);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_shortlisted);
        tabLayout.setupWithViewPager(viewPager);

        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        tabView1 = (LinearLayout) LayoutInflater.from(ActivityShortlisted.this)
                .inflate(R.layout.custom_tab, null, false);
        final TextView title1 = tabView1.findViewById(R.id.custom_tab_inbox_title);
        title1.setTextColor(getResources().getColor(R.color.black));
        title1.setTypeface(title1.getTypeface(), Typeface.BOLD);
        title1.setText(R.string.properties);
        final TextView count1 = tabView1.findViewById(R.id.custom_tab_inbox_count);
        count1.setTextColor(getResources().getColor(R.color.black));
        Objects.requireNonNull(tabLayout.getTabAt(0)).setCustomView(tabView1);

        tabView2 = (LinearLayout) LayoutInflater.from(ActivityShortlisted.this)
                .inflate(R.layout.custom_tab, null, false);
        final TextView title2 = tabView2.findViewById(R.id.custom_tab_inbox_title);
        title2.setTextColor(getResources().getColor(R.color.white));
        title2.setTypeface(title2.getTypeface(), Typeface.BOLD);
        title2.setText(R.string.projects);
        final TextView count2 = tabView2.findViewById(R.id.custom_tab_inbox_count);
        count2.setTextColor(getResources().getColor(R.color.white));
        Objects.requireNonNull(tabLayout.getTabAt(1)).setCustomView(tabView2);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tabLayout.getSelectedTabPosition()) {
                    case 0:
                        title1.setTextColor(getResources().getColor(R.color.black));
                        count1.setTextColor(getResources().getColor(R.color.black));
                        title2.setTextColor(getResources().getColor(R.color.white));
                        count2.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case 1:
                        title1.setTextColor(getResources().getColor(R.color.white));
                        count1.setTextColor(getResources().getColor(R.color.white));
                        title2.setTextColor(getResources().getColor(R.color.black));
                        count2.setTextColor(getResources().getColor(R.color.black));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

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
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_shortlisted);
        navigationView = (NavigationView) findViewById(R.id.nav_view_shortlisted);

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
        textMyQuestions = navigationView.findViewById(R.id.text_my_questions_dashboard_drawer);
        textViewingRequest = navigationView.findViewById(R.id.text_viewing_request_dashboard_drawer);
        textEnquiriesOffers = navigationView.findViewById(R.id.text_enquiries_offers_dashboard_drawer);
        textUnifiedTenancy = navigationView.findViewById(R.id.text_unified_dashboard_drawer);
        textOpenHouse = navigationView.findViewById(R.id.text_open_house_dashboard_drawer);

        textEnquiriesOffers.setOnClickListener(this);
        textMyQuestions.setOnClickListener(this);
        textViewingRequest.setOnClickListener(this);
        textUnifiedTenancy.setOnClickListener(this);
        textOpenHouse.setOnClickListener(this);

        setProfileImage();

        textName.setText(SessionManager.getFullName(ActivityShortlisted.this));
        textEmail.setText(SessionManager.getEmail(ActivityShortlisted.this));
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

    //TODO Tab Layout
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        tab1 = new FragmentShortListProperty(new FragmentShortListProperty.CountUpdateListener() {
            @Override
            public void updateCount(int count) {
                TextView countText = tabView1.findViewById(R.id.custom_tab_inbox_count);
                String countValue = " (" + count + ")";
                countText.setText(countValue);
                propertyCount = count;
                int total = projectCount + propertyCount;
                mTitle.setText(getResources().getString(R.string.shortlisted) + " (" + total + ")");
            }
        });
        tab2 = new FragmentShortListProject(new FragmentShortListProject.CountUpdateListener() {
            @Override
            public void updateCount(int count) {
                TextView countText = tabView2.findViewById(R.id.custom_tab_inbox_count);
                String countValue = " (" + count + ")";
                countText.setText(countValue);
                projectCount = count;
                int total = projectCount + propertyCount;
                mTitle.setText(getResources().getString(R.string.shortlisted) + " (" + total + ")");
            }
        });
        adapter.addFragment(tab1, "Properties");
        adapter.addFragment(tab2, "Projects");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }


    //TODO Click Listener
    @Override
    public void onClick(View view) {
        if (view == profileLayout) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityShortlisted.this, ProfileActivity.class);
            startActivity(intent);

        } else if (view == btnPostFreeAd) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityShortlisted.this, PostPropertyPage01Activity.class);
            startActivity(intent);

        } else if (view == btnPostRequirement) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityShortlisted.this, ActivityRequirementPurpose.class);
            startActivity(intent);

        } else if (view == textSavedSearches) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityShortlisted.this, ActivitySavedSearchList.class);
            startActivity(intent);

        } else if (view == textShortlisted) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityShortlisted.this, ActivityShortlisted.class);
            startActivity(intent);
            finish();

        } else if (view == textPostedRequirements) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityShortlisted.this, ActivityPostedRequirements.class);
            startActivity(intent);

        } else if (view == textAgents) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityShortlisted.this, ActivityMyAgents.class);
            startActivity(intent);

        } else if (view == textPropertyEnquiry) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityShortlisted.this, ActivityMyProperties.class);
            startActivity(intent);

        } else if (view == textSettings) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityShortlisted.this, ActivitySettings.class);
            startActivity(intent);

        } else if (view == textSubscriptions) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityShortlisted.this, ActivityMySubscriptions.class);
            startActivity(intent);

        } else if (view == textEnquiriesOffers) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityShortlisted.this, ActivityEnquiriesOffers.class);
            startActivity(intent);

        } else if (view == textViewingRequest) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityShortlisted.this, ActivityViewingRequests.class);
            startActivity(intent);

        } else if (view == textUnifiedTenancy) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityShortlisted.this, ActivityUnifiedTenancyContract.class);
            startActivity(intent);

        } else if (view == textOpenHouse) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityShortlisted.this, ActivityOpenHouse.class);
            startActivity(intent);

        } else if (view == textMyQuestions) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityShortlisted.this, ActivityMyQuestions.class);
            startActivity(intent);

        } else if (view == textLogout) {
            drawer.closeDrawer(GravityCompat.END);
            alertLogout();

        } else if (view == textHome) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityShortlisted.this, ActivityHome.class);
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
                Intent intent = new Intent(ActivityShortlisted.this, ActivityHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                SessionManager.logout(ActivityShortlisted.this);
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
    public void getProfileInfo() {
        ApiLinks.getClient().create(ProfileInfoApi.class)
                .post(SessionManager.getAccessToken(ActivityShortlisted.this))
                .enqueue(new Callback<ProfileInfoResponse>() {
                    @Override
                    public void onResponse(Call<ProfileInfoResponse> call,
                                           Response<ProfileInfoResponse> response) {
                        try {
                            if (response.isSuccessful()) {
                                String message = response.body().getMessage();
                                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                                    Log.d("getProfileInfo", response.body().getMessage());
                                    ProfileInfoData data = response.body().getData();
                                    String imagePath = response.body().getImagePath();
                                    SessionManager.saveLogin(ActivityShortlisted.this, data.getUserFirstName(),
                                            data.getUserLastName(), data.getUserID(),
                                            SessionManager.getAccessToken(ActivityShortlisted.this),
                                            data.getUserEmail(), data.getUserPhone(), data.getCountryCode(),
                                            data.getUserTypeID(),
                                            imagePath + data.getUserProfilePicture(), true);
                                } else if (response.body().getCode() != null && response.body().getCode() == 409) {
                                    Utils.showAlertLogout(ActivityShortlisted.this, message);
                                } else {
                                    Log.d("getProfileInfo", response.body().getMessage());
                                }
                            } else {
                                Log.d("getProfileInfo", response.body().getMessage());
                            }
                        } catch (Exception e) {
                            FirebaseCrashlytics.getInstance().recordException(e);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProfileInfoResponse> call, Throwable t) {
                        Log.v("getProfileInfo", t.getMessage());
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
        //commented by lavanya
       /* Intent intent = new Intent(ActivityShortlisted.this, ActivityHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.drawer_layout_shortlisted));
        if (SessionManager.getAccessToken(this) != null
                && !SessionManager.getAccessToken(this).equals("")) {
            setProfileImage();
            tab1.getShortlistedPropertyListing();
            tab2.getShortlistedProjectListing();
        } else finish();
    }
}
