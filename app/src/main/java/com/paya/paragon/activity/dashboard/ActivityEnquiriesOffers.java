package com.paya.paragon.activity.dashboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.paya.paragon.R;
import com.paya.paragon.activity.ActivityHome;
import com.paya.paragon.activity.postProperty.PostPropertyPage01Activity;
import com.paya.paragon.activity.postRequirements.ActivityRequirementPurpose;
import com.paya.paragon.adapter.AdapterEnquiryListing;
import com.paya.paragon.adapter.AdapterOfferListing;
import com.paya.paragon.api.enquiryList.EnquiryListApi;
import com.paya.paragon.api.enquiryList.EnquiryListItemData;
import com.paya.paragon.api.enquiryList.EnquiryListResponse;
import com.paya.paragon.api.offerList.OfferListApi;
import com.paya.paragon.api.offerList.OfferListItemData;
import com.paya.paragon.api.offerList.OfferListResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("HardCodedStringLiteral")
@SuppressLint("StaticFieldLeak")
public class ActivityEnquiriesOffers extends AppCompatActivity implements View.OnClickListener {

    static ArrayList<EnquiryListItemData> enquiryItemsList;
    static AdapterEnquiryListing adapterEnquiryListing;
    static RecyclerView recyclerEnquiriesListing, recyclerOfferListing;
    static ArrayList<OfferListItemData> offerItemsList;
    static AdapterOfferListing adapterOfferListing;
    //Side Menu
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

    public String langName = "";
    private String propID = "";
    private FrameLayout contentHolder;

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.drawer_layout_my_properties));
        if (!langName.equalsIgnoreCase(SessionManager.getLanguageName(this)))
            recreate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiries_offers);
        langName = SessionManager.getLanguageName(this);
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
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Intent intent = getIntent();
        propID = intent.getStringExtra("propID");
        if (propID == null)
            propID = "";
        declarations();

        Fragment enquiryFragment = EnquiryFragment.newInstance(propID);


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contentHolder, enquiryFragment, "enq")
                .disallowAddToBackStack()
                .commit();

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

    //TODO Set Data
    private void declarations() {

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_my_properties);
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

        contentHolder = findViewById(R.id.contentHolder);
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
        enquiryItemsList = new ArrayList<>();

        setProfileImage();
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
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view == mProfileImage) {
            drawer.openDrawer(GravityCompat.END);

        }
        if (view == profileLayout) {
            toggleDrawer();
            Intent in = new Intent(ActivityEnquiriesOffers.this, ProfileActivity.class);
            startActivity(in);

        } else if (view == btnPostFreeAd) {
            toggleDrawer();
            Intent intent = new Intent(ActivityEnquiriesOffers.this, PostPropertyPage01Activity.class);
            startActivity(intent);

        } else if (view == btnPostRequirement) {
            toggleDrawer();
            Intent intent = new Intent(ActivityEnquiriesOffers.this, ActivityRequirementPurpose.class);
            startActivity(intent);

        } else if (view == textSavedSearches) {
            toggleDrawer();
            Intent intent = new Intent(ActivityEnquiriesOffers.this, ActivitySavedSearchList.class);
            startActivity(intent);

        } else if (view == textShortlisted) {
            toggleDrawer();
            Intent intent = new Intent(ActivityEnquiriesOffers.this, ActivityShortlisted.class);
            startActivity(intent);

        } else if (view == textPostedRequirements) {
            toggleDrawer();
            Intent intent = new Intent(ActivityEnquiriesOffers.this, ActivityPostedRequirements.class);
            startActivity(intent);

        } else if (view == textAgents) {
            toggleDrawer();
            Intent intent = new Intent(ActivityEnquiriesOffers.this, ActivityMyAgents.class);
            startActivity(intent);

        } else if (view == textPropertyEnquiry) {
            toggleDrawer();
            Intent intent = new Intent(ActivityEnquiriesOffers.this, ActivityMyProperties.class);
            startActivity(intent);

        } else if (view == textSettings) {
            toggleDrawer();
            Intent intent = new Intent(ActivityEnquiriesOffers.this, ActivitySettings.class);
            startActivity(intent);

        } else if (view == textSubscriptions) {
            toggleDrawer();
            Intent intent = new Intent(ActivityEnquiriesOffers.this, ActivityMySubscriptions.class);
            startActivity(intent);

        } else if (view == textEnquiriesOffers) {
            toggleDrawer();


        } else if (view == textViewingRequest) {
            toggleDrawer();
            Intent intent = new Intent(ActivityEnquiriesOffers.this, ActivityViewingRequests.class);
            startActivity(intent);

        } else if (view == textUnifiedTenancy) {
            toggleDrawer();
            Intent intent = new Intent(ActivityEnquiriesOffers.this, ActivityUnifiedTenancyContract.class);
            startActivity(intent);

        } else if (view == textOpenHouse) {
            toggleDrawer();
            Intent intent = new Intent(ActivityEnquiriesOffers.this, ActivityOpenHouse.class);
            startActivity(intent);

        } else if (view == textMyQuestions) {
            toggleDrawer();
            Intent intent = new Intent(ActivityEnquiriesOffers.this, ActivityMyQuestions.class);
            startActivity(intent);

        } else if (view == textLogout) {
            toggleDrawer();
            alertLogout();

        } else if (view == textHome) {
            toggleDrawer();
            Intent intent = new Intent(ActivityEnquiriesOffers.this, ActivityHome.class);
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
                SessionManager.logout(ActivityEnquiriesOffers.this);
                Intent intent = new Intent(ActivityEnquiriesOffers.this, ActivityHome.class);
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
            drawer.closeDrawer(GravityCompat.START); //CLOSE Nav Drawer!
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END); //CLOSE Nav Drawer!
        }
    }

    //TODO Fragments
    @SuppressWarnings("HardCodedStringLiteral")
    public static class EnquiryFragment extends Fragment {
        private String propID = "";

        public EnquiryFragment() {
        }

        public static EnquiryFragment newInstance(String propID) {
            EnquiryFragment fragment = new EnquiryFragment();
            Bundle args = new Bundle();
            args.putString("propID", propID);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_listing, container, false);
            Bundle bundle = this.getArguments();
            propID = bundle.getString("propID");
            recyclerEnquiriesListing = rootView.findViewById(R.id.recycler_view_fragment_listing);
            final ProgressBar progress = rootView.findViewById(R.id.progressbar_property_fragment_listing);
            final SwipeRefreshLayout swipe = rootView.findViewById(R.id.swipe_refresh_fragment_listing);
            final TextView noData = rootView.findViewById(R.id.tv_no_item);
            noData.setVisibility(View.GONE);
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
            linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
            linearLayoutManager1.setAutoMeasureEnabled(true);
            recyclerEnquiriesListing.setLayoutManager(linearLayoutManager1);
            progress.setVisibility(View.VISIBLE);

            if (!Utils.NoInternetConnection(getActivity())) {
                getEnquiryListing(getActivity(), progress, noData);
            } else {
                progress.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
                noData.setText("No Internet Connection");
                Utils.showAlertNoInternet(getActivity());
            }

            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    int TIME_OUT = 500;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipe.setRefreshing(false);
                            progress.setVisibility(View.VISIBLE);
                            if (!Utils.NoInternetConnection(getActivity())) {
                                getEnquiryListing(getActivity(), progress, noData);
                            } else {
                                progress.setVisibility(View.GONE);
                                noData.setVisibility(View.GONE);
                                Utils.showAlertNoInternet(getActivity());
                            }
                        }
                    }, TIME_OUT);
                }
            });

            return rootView;
        }

        private void getEnquiryListing(final FragmentActivity activity, final ProgressBar progress, final TextView noData) {
            ApiLinks.getClient().create(EnquiryListApi.class).post("No", SessionManager.getAccessToken(activity), propID, SessionManager.getLanguageID(getActivity()))
                    .enqueue(new Callback<EnquiryListResponse>() {
                        @Override
                        public void onResponse(Call<EnquiryListResponse> call, Response<EnquiryListResponse> response) {
                            if (response.isSuccessful()) {
                                String message = response.body().getMessage();
                                if (response.body().getResponse().equals("Success")) {
                                    noData.setVisibility(View.GONE);
                                    enquiryItemsList = new ArrayList<>();
                                    enquiryItemsList.addAll(response.body().getData().getEnquiries());
                                    adapterEnquiryListing = new AdapterEnquiryListing(false, activity, enquiryItemsList);
                                    recyclerEnquiriesListing.setAdapter(adapterEnquiryListing);
                                    recyclerEnquiriesListing.setHasFixedSize(false);
                                    recyclerEnquiriesListing.clearFocus();
                                } else if (response.body().getCode() != null && response.body().getCode() == 409) {
                                    Utils.showAlertLogout(activity, message);
                                } else {
                                    noData.setVisibility(View.VISIBLE);
                                }
                                progress.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(activity, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                                progress.setVisibility(View.GONE);
                            }
                            progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<EnquiryListResponse> call, Throwable t) {
                            Toast.makeText(activity, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }
                    });

        }

    }

    @SuppressWarnings("HardCodedStringLiteral")
    public static class OfferFragment extends Fragment {
        private String propID = "";

        public OfferFragment() {
        }

        public static OfferFragment newInstance(String propID) {
            OfferFragment fragment = new OfferFragment();
            Bundle args = new Bundle();
            args.putString("propID", propID);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_listing, container, false);
            recyclerOfferListing = rootView.findViewById(R.id.recycler_view_fragment_listing);
            Bundle bundle = this.getArguments();
            propID = bundle.getString("propID");
            final ProgressBar progress = rootView.findViewById(R.id.progressbar_property_fragment_listing);
            final SwipeRefreshLayout swipe = rootView.findViewById(R.id.swipe_refresh_fragment_listing);
            final TextView noData = rootView.findViewById(R.id.tv_no_item);
            noData.setVisibility(View.GONE);

            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
            linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
            linearLayoutManager1.setAutoMeasureEnabled(true);
            recyclerOfferListing.setLayoutManager(linearLayoutManager1);
            progress.setVisibility(View.VISIBLE);

            if (!Utils.NoInternetConnection(getActivity())) {
                getOfferListing(getActivity(), progress, noData);
            } else {
                progress.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
                noData.setText("No Internet Connection");
                Utils.showAlertNoInternet(getActivity());
            }

            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    int TIME_OUT = 500;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipe.setRefreshing(false);
                            progress.setVisibility(View.VISIBLE);
                            if (!Utils.NoInternetConnection(getActivity())) {
                                getOfferListing(getActivity(), progress, noData);
                            } else {
                                progress.setVisibility(View.GONE);
                                noData.setVisibility(View.GONE);
                                Utils.showAlertNoInternet(getActivity());
                            }
                        }
                    }, TIME_OUT);
                }
            });

            return rootView;
        }

        private void getOfferListing(final FragmentActivity activity,
                                     final ProgressBar progress, final TextView noData) {
            ApiLinks.getClient().create(OfferListApi.class).post("Yes", SessionManager.getAccessToken(activity), propID, SessionManager.getLanguageID(getActivity()))
                    .enqueue(new Callback<OfferListResponse>() {
                        @Override
                        public void onResponse(Call<OfferListResponse> call, Response<OfferListResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getResponse().equals("Success")) {
                                    noData.setVisibility(View.GONE);
                                    offerItemsList = new ArrayList<>();
                                    offerItemsList.addAll(response.body().getData().getEnquiries());
                                    adapterOfferListing = new AdapterOfferListing(false, activity, offerItemsList);
                                    recyclerOfferListing.setAdapter(adapterOfferListing);
                                    recyclerOfferListing.setHasFixedSize(false);
                                    recyclerOfferListing.clearFocus();
                                } else {
                                    noData.setVisibility(View.VISIBLE);
                                }
                                progress.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(activity, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                                progress.setVisibility(View.GONE);
                            }
                            progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<OfferListResponse> call, Throwable t) {
                            Toast.makeText(activity, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }
                    });

        }

    }


}
