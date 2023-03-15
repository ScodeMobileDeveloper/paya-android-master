package com.paya.paragon.activity.dashboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.paya.paragon.R;
import com.paya.paragon.activity.ActivityHome;
import com.paya.paragon.PropertyProjectListActivity;
import com.paya.paragon.activity.postProperty.PostPropertyPage01Activity;
import com.paya.paragon.activity.postRequirements.ActivityRequirementPurpose;
import com.paya.paragon.adapter.AdapterMySavedSearches;
import com.paya.paragon.api.mySavedSearchDelete.SavedSearchDeleteApi;
import com.paya.paragon.api.mySavedSearchDelete.SavedSearchDeleteResponse;
import com.paya.paragon.api.mySavedSearchStatusChange.SavedSearchStatusChangeApi;
import com.paya.paragon.api.mySavedSearchStatusChange.SavedSearchStatusChangeResponse;
import com.paya.paragon.api.mySavedSearches.MySavedSearchesApi;
import com.paya.paragon.api.mySavedSearches.MySavedSearchesResponse;
import com.paya.paragon.api.mySavedSearches.SavedSearchInfo;
import com.paya.paragon.api.profileInfo.ProfileInfoApi;
import com.paya.paragon.api.profileInfo.ProfileInfoData;
import com.paya.paragon.api.profileInfo.ProfileInfoResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"HardCodedStringLiteral", "RedundantCast"})
@SuppressLint("StaticFieldLeak")
public class ActivitySavedSearchList extends AppCompatActivity implements View.OnClickListener {
    DialogProgress dialogProgress;
    public ArrayList<SavedSearchInfo> mySavedSearchesData = new ArrayList<>();
    AdapterMySavedSearches adapterMySavedSearches;
    RecyclerView recyclerMySavedSearches;
    public int scrolledPageCount = 0, totalSavedSearchCount = 0;
    public int paginationLimit = 10, pageCount = 1;
    public Dialog alertDialog;

    //Side Menu
    public DrawerLayout drawer;
    NavigationView navigationView;
    ImageView mDrawerProfileImage;
    TextView textSavedSearches, textShortlisted, textPostedRequirements, textAgents;
    TextView textEnquiriesOffers, textMyQuestions, textViewingRequest, textUnifiedTenancy, textOpenHouse;
    TextView textPropertyEnquiry, textSettings, textLogout, textName, textEmail;
    TextView btnPostFreeAd, btnPostRequirement, textHome, noRecords, textSubscriptions;
    LinearLayout profileLayout;
    ImageView mProfileImage;
    TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_saved_searches);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        mTitle.setText(R.string.saved_searches);
        mProfileImage = toolbar.findViewById(R.id.profile_image_my_account);

        declarations();

        if (!Utils.NoInternetConnection(ActivitySavedSearchList.this)) {
            getMySavedSearches();
            getProfileInfo();
        } else {
            Utils.NoInternetConnection(ActivitySavedSearchList.this);
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

        recyclerMySavedSearches.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (totalSavedSearchCount > paginationLimit) {
                    if (dy > 10) {
                        scrolledPageCount = scrolledPageCount + 1;
                        if (scrolledPageCount < pageCount && !(totalSavedSearchCount == mySavedSearchesData.size())) {
                            if (!Utils.NoInternetConnection(ActivitySavedSearchList.this)) {
                                getMySavedSearches();
                            } else {
                                Utils.showAlertNoInternet(ActivitySavedSearchList.this);
                            }
                        }
                    }
                }
            }
        });
    }

    //TODO Set Data.
    private void declarations() {
        recyclerMySavedSearches = (RecyclerView) findViewById(R.id.recycler_my_saved_searches);
        dialogProgress = new DialogProgress(ActivitySavedSearchList.this);
        recyclerMySavedSearches.setLayoutManager(new LinearLayoutManager(ActivitySavedSearchList.this));

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_saved_searches);
        navigationView = (NavigationView) findViewById(R.id.nav_view_saved_searches);

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
        textSubscriptions = navigationView.findViewById(R.id.text_my_subscriptions_dashboard_drawer);
        textSettings = navigationView.findViewById(R.id.text_settings_dashboard_drawer);
        textLogout = navigationView.findViewById(R.id.text_logout_dashboard_drawer);
        mDrawerProfileImage = navigationView.findViewById(R.id.profile_image_dashboard_drawer);
        profileLayout = navigationView.findViewById(R.id.layout_view_profile_dashboard_drawer);
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

        textName.setText(SessionManager.getFullName(ActivitySavedSearchList.this));
        textEmail.setText(SessionManager.getEmail(ActivitySavedSearchList.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.drawer_layout_saved_searches));
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

    //TODO API Calls
    private void getMySavedSearches() {
        ApiLinks.getClient().create(MySavedSearchesApi.class)
                .post("" + SessionManager.getLanguageID(ActivitySavedSearchList.this),
                        SessionManager.getAccessToken(ActivitySavedSearchList.this),
                        String.valueOf(scrolledPageCount))
                .enqueue(new Callback<MySavedSearchesResponse>() {
                    @Override
                    public void onResponse(Call<MySavedSearchesResponse> call,
                                           Response<MySavedSearchesResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            if (response.body().getResponse().equalsIgnoreCase("Success")) {
                                if (response.body().getData() != null) {
                                    if (scrolledPageCount == 0) {
                                        mySavedSearchesData = response.body().getData().getSavedSearchLists();
                                        totalSavedSearchCount = response.body().getData().getTotalCount();
                                        mTitle.setText(getString(R.string.saved_searches) + " (" + totalSavedSearchCount + ") ");
                                        if (totalSavedSearchCount % paginationLimit > 0) {
                                            pageCount = (totalSavedSearchCount / paginationLimit) + 1;
                                        } else {
                                            pageCount = (totalSavedSearchCount / paginationLimit);
                                        }
                                        adapterMySavedSearches = new AdapterMySavedSearches(ActivitySavedSearchList.this,
                                                mySavedSearchesData,
                                                new AdapterMySavedSearches.ItemClickAdapterListener() {
                                                    @Override
                                                    public void deleteClick(int position) {
                                                        alertDialogDeleteSearch(position);
                                                    }

                                                    @Override
                                                    public void playPauseClick(int position, String status) {
                                                        changeSearchStatus(position, status);
                                                    }

                                                    @Override
                                                    public void itemClick(int position, String purpose) {
                                                        GlobalValues.setValuesForSearch(mySavedSearchesData.get(position));
                                                        Intent in = new Intent(ActivitySavedSearchList.this,
                                                                PropertyProjectListActivity.class);
                                                        in.putExtra("searchPropertyPurpose", purpose);
                                                        startActivity(in);
                                                    }
                                                }, totalSavedSearchCount);
                                        recyclerMySavedSearches.setAdapter(adapterMySavedSearches);
                                    } else {
                                        mySavedSearchesData.addAll(response.body().getData().getSavedSearchLists());
                                        adapterMySavedSearches.notifyDataSetChanged();
                                    }
                                } else {
                                    Toast.makeText(ActivitySavedSearchList.this, message, Toast.LENGTH_SHORT).show();
                                }
                                dialogProgress.dismiss();
                            } else {
                                dialogProgress.dismiss();
                                noRecords.setVisibility(View.VISIBLE);
                                noRecords.setText(R.string.no_saved_searches);
                            }
                        } else {
                            dialogProgress.dismiss();
                            Toast.makeText(ActivitySavedSearchList.this, getString(R.string.no_response),
                                    Toast.LENGTH_SHORT).show();
                            noRecords.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<MySavedSearchesResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                        Toast.makeText(ActivitySavedSearchList.this, getString(R.string.no_response),
                                Toast.LENGTH_SHORT).show();
                        noRecords.setVisibility(View.VISIBLE);
                    }
                });
    }

    public void changeSearchStatus(final int position, final String status) {
        dialogProgress.show();
        ApiLinks.getClient().create(SavedSearchStatusChangeApi.class)
                .post(mySavedSearchesData.get(position).getSearchID(),
                        status,
                        SessionManager.getAccessToken(ActivitySavedSearchList.this))
                .enqueue(new Callback<SavedSearchStatusChangeResponse>() {
                    @Override
                    public void onResponse(Call<SavedSearchStatusChangeResponse> call,
                                           Response<SavedSearchStatusChangeResponse> response) {
                        if (response.isSuccessful()) {
                            int code = response.body().getCode();
                            if (code == 1 && response.body().getResponse().equalsIgnoreCase("Success")) {
                                dialogProgress.dismiss();
                                mySavedSearchesData.get(position).setSearchStatus(status);
                                adapterMySavedSearches.notifyDataSetChanged();
                                Toast.makeText(ActivitySavedSearchList.this, getString(R.string.status_changed), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivitySavedSearchList.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                                dialogProgress.dismiss();
                            }
                        } else {
                            dialogProgress.dismiss();
                            Toast.makeText(ActivitySavedSearchList.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SavedSearchStatusChangeResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                        Toast.makeText(ActivitySavedSearchList.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void deleteSavedSearch(final int position) {
        dialogProgress.show();
        ApiLinks.getClient().create(SavedSearchDeleteApi.class)
                .post(mySavedSearchesData.get(position).getSearchID(),
                        SessionManager.getAccessToken(ActivitySavedSearchList.this))
                .enqueue(new Callback<SavedSearchDeleteResponse>() {
                    @Override
                    public void onResponse(Call<SavedSearchDeleteResponse> call,
                                           Response<SavedSearchDeleteResponse> response) {
                        if (response.isSuccessful()) {
                            int code = response.body().getCode();
                            String status = response.body().getResponse();
                            if (code == 1 && status.equalsIgnoreCase("Success")) {
                                dialogProgress.dismiss();
                                mySavedSearchesData.remove(position);
                                adapterMySavedSearches.notifyDataSetChanged();
                                showPropertyPostedAlert();
                                mTitle.setText(getString(R.string.saved_searches) + " (" + mySavedSearchesData.size() + ") ");

                                if (mySavedSearchesData.size() == 0) {
                                    noRecords.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(ActivitySavedSearchList.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                                dialogProgress.dismiss();
                            }
                        } else {
                            dialogProgress.dismiss();
                            Toast.makeText(ActivitySavedSearchList.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SavedSearchDeleteResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                        Toast.makeText(ActivitySavedSearchList.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getProfileInfo() {
        ApiLinks.getClient().create(ProfileInfoApi.class)
                .post(SessionManager.getAccessToken(ActivitySavedSearchList.this))
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
                                    SessionManager.saveLogin(ActivitySavedSearchList.this, data.getUserFirstName(),
                                            data.getUserLastName(), data.getUserID(),
                                            SessionManager.getAccessToken(ActivitySavedSearchList.this),
                                            data.getUserEmail(), data.getUserPhone(), data.getCountryCode(),
                                            data.getUserTypeID(),
                                            imagePath + data.getUserProfilePicture(), true);
                                } else if (response.body().getCode() != null && response.body().getCode() == 409) {
                                    Utils.showAlertLogout(ActivitySavedSearchList.this, message);
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
                        Log.d("getProfileInfo", t.getMessage());
                    }
                });
    }


    //TODO Alert Delete
    @SuppressLint("SetTextI18n")
    private void alertDialogDeleteSearch(final int position) {
        alertDialog = new Dialog(ActivitySavedSearchList.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_popup, null);
        LinearLayout cancel = alert_layout.findViewById(R.id.CancelPopUp);
        LinearLayout ok = alert_layout.findViewById(R.id.ApplyPopUP);
        TextView title = alert_layout.findViewById(R.id.alertTitle);
        title.setText(getString(R.string.are_you_sure_to_delete_this_search));

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
                deleteSavedSearch(position);
            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void showPropertyPostedAlert() {
        alertDialog = new Dialog(ActivitySavedSearchList.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_delete_save_search_popup,
                null);
        ImageView close = alert_layout.findViewById(R.id.close_alert);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }


    //TODO Click Listener
    @Override
    public void onClick(View view) {
        if (view == profileLayout) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySavedSearchList.this, ProfileActivity.class);
            startActivity(intent);

        } else if (view == btnPostFreeAd) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySavedSearchList.this, PostPropertyPage01Activity.class);
            startActivity(intent);

        } else if (view == btnPostRequirement) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySavedSearchList.this, ActivityRequirementPurpose.class);
            startActivity(intent);

        } else if (view == textSavedSearches) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySavedSearchList.this, ActivitySavedSearchList.class);
            startActivity(intent);
            finish();

        } else if (view == textShortlisted) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySavedSearchList.this, ActivityShortlisted.class);
            startActivity(intent);

        } else if (view == textPostedRequirements) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySavedSearchList.this, ActivityPostedRequirements.class);
            startActivity(intent);

        } else if (view == textAgents) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySavedSearchList.this, ActivityMyAgents.class);
            startActivity(intent);

        } else if (view == textPropertyEnquiry) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySavedSearchList.this, ActivityMyProperties.class);
            startActivity(intent);

        } else if (view == textSubscriptions) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySavedSearchList.this, ActivityMySubscriptions.class);
            startActivity(intent);

        } else if (view == textSettings) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySavedSearchList.this, ActivitySettings.class);
            startActivity(intent);

        } else if (view == textEnquiriesOffers) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySavedSearchList.this, ActivityEnquiriesOffers.class);
            startActivity(intent);

        } else if (view == textViewingRequest) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySavedSearchList.this, ActivityViewingRequests.class);
            startActivity(intent);

        } else if (view == textUnifiedTenancy) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySavedSearchList.this, ActivityUnifiedTenancyContract.class);
            startActivity(intent);

        } else if (view == textOpenHouse) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySavedSearchList.this, ActivityOpenHouse.class);
            startActivity(intent);

        } else if (view == textMyQuestions) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySavedSearchList.this, ActivityMyQuestions.class);
            startActivity(intent);

        } else if (view == textLogout) {
            drawer.closeDrawer(GravityCompat.END);
            alertLogout();

        } else if (view == textHome) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySavedSearchList.this, ActivityHome.class);
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
                Intent intent = new Intent(ActivitySavedSearchList.this, ActivityHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                SessionManager.logout(ActivitySavedSearchList.this);
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
        Intent intent = new Intent(ActivitySavedSearchList.this, ActivityHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

}
