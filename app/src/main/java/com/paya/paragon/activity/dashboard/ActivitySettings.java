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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.paya.paragon.R;
import com.paya.paragon.activity.postProperty.PostPropertyPage01Activity;
import com.paya.paragon.activity.postRequirements.ActivityRequirementPurpose;
import com.paya.paragon.api.listSettings.ListSettingsApi;
import com.paya.paragon.api.listSettings.ListSettingsData;
import com.paya.paragon.api.listSettings.ListSettingsResponse;
import com.paya.paragon.api.profileInfo.ProfileInfoApi;
import com.paya.paragon.api.profileInfo.ProfileInfoData;
import com.paya.paragon.api.profileInfo.ProfileInfoResponse;
import com.paya.paragon.api.updateSettings.UpdateSettingsApi;
import com.paya.paragon.api.updateSettings.UpdateSettingsResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"HardCodedStringLiteral", "RedundantCast"})
public class ActivitySettings extends AppCompatActivity implements View.OnClickListener {

    public DrawerLayout drawer;
    RadioGroup radioGroupPropertyAlert;
    RadioButton radioButtonAlertDaily, radioButtonAlertWeekly;
    SwitchCompat newsletters, saveSearch, smsAlerts;
    DialogProgress dialogProgress;
    TextView saveSettings;
    LinearLayout layoutProgress;
    String strNewsLetterStatus = "", strSavedSearchStatus = "";
    String checkPreferences = "", strAlertTime = "Daily", strSmsStatus;

    //Side Menu
    NavigationView navigationView;
    ImageView mDrawerProfileImage;
    TextView textSavedSearches, textShortlisted, textPostedRequirements, textAgents;
    TextView textPropertyEnquiry, textSettings, textLogout, textName, textEmail;
    TextView btnPostFreeAd, btnPostRequirement, textHome, textSubscriptions;
    TextView textEnquiriesOffers, textMyQuestions, textViewingRequest, textUnifiedTenancy, textOpenHouse;

    LinearLayout profileLayout;
    ImageView mProfileImage;

     Button shortListed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.drawer_layout_settings));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        mTitle.setText(R.string.settings);
        mProfileImage = toolbar.findViewById(R.id.profile_image_my_account);


        declarations();

        if (!Utils.NoInternetConnection(ActivitySettings.this)) {
            getSettingsStatus();
            //getProfileInfo();
        } else {
            setStatus();
        }

        newsletters.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strNewsLetterStatus = "Yes";
                } else {
                    strNewsLetterStatus = "No";
                }
            }
        });

        saveSearch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strSavedSearchStatus = "Yes";
                    Utils.expand(radioGroupPropertyAlert, 300);
                } else {
                    strSavedSearchStatus = "No";
                    Utils.collapse(radioGroupPropertyAlert, 300);
                }
            }
        });

        smsAlerts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strSmsStatus = "Yes";
                } else {
                    strSmsStatus = "No";
                }
            }
        });

        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.NoInternetConnection(ActivitySettings.this)) {
                    saveSettingsToServer();
                } else {
                    Utils.showAlertNoInternet(ActivitySettings.this);
                }
            }
        });

        radioGroupPropertyAlert.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radio_button_save_search_daily:
                        strAlertTime = "Daily";
                        break;
                    case R.id.radio_button_save_search_weekly:
                        strAlertTime = "Weekly";
                        break;
                }
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


    //TODO API Calls
    private void getSettingsStatus() {
        dialogProgress.show();
        ApiLinks.getClient().create(ListSettingsApi.class).post(SessionManager.getAccessToken(ActivitySettings.this))
                .enqueue(new Callback<ListSettingsResponse>() {
                    @Override
                    public void onResponse(Call<ListSettingsResponse> call, Response<ListSettingsResponse> response) {
                        if (response.isSuccessful()) {
                            String status = response.body().getResponse();
                            String message = response.body().getMessage();
                            int code = response.body().getCode();
                            if (code == 100 && status.equals("Success")) {
                                dialogProgress.dismiss();
                                if (response.body().getData() != null && response.body().getData().size() > 0) {
                                    ListSettingsData data = response.body().getData().get(0);
                                    strNewsLetterStatus = data.getUserNewsletterSubscribeStatus();
                                    strSavedSearchStatus = data.getUserPropertyAlerts();
                                    strAlertTime = data.getUserPropertyAlertType();
                                    strSmsStatus = data.getUserSmsAlerts();
                                    SessionManager.setSavedSearchStatus(ActivitySettings.this, strSavedSearchStatus);
                                    SessionManager.setNewsLetterStatus(ActivitySettings.this, strNewsLetterStatus);
                                    SessionManager.setSavedSearchTypeStatus(ActivitySettings.this, strAlertTime);
                                    SessionManager.setSavedSMSStatus(ActivitySettings.this, strSmsStatus);
                                    setPreferencesStatus();
                                } else {
                                    setStatus();
                                }
                            } else {
                                Toast.makeText(ActivitySettings.this, message, Toast.LENGTH_SHORT).show();
                                dialogProgress.dismiss();
                                statusNotUpdated();
                            }
                            dialogProgress.dismiss();
                        } else {
                            dialogProgress.dismiss();
                            Toast.makeText(ActivitySettings.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                            statusNotUpdated();
                        }
                    }

                    @Override
                    public void onFailure(Call<ListSettingsResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                        Toast.makeText(ActivitySettings.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        statusNotUpdated();
                    }
                });
    }

    private void saveSettingsToServer() {
        dialogProgress.show();
        ApiLinks.getClient().create(UpdateSettingsApi.class).post(SessionManager.getLanguageID(this),
                SessionManager.getAccessToken(ActivitySettings.this),
                SessionManager.getEmail(ActivitySettings.this),
                strNewsLetterStatus,
                strSmsStatus,
                strSavedSearchStatus,
                strAlertTime)
                .enqueue(new Callback<UpdateSettingsResponse>() {
                    @Override
                    public void onResponse(Call<UpdateSettingsResponse> call, Response<UpdateSettingsResponse> response) {
                        if (response.isSuccessful()) {
                            String status = response.body().getResponse();
                            String message = response.body().getMessage();
                            int code = response.body().getCode();
                            if (code == 100 && status.equalsIgnoreCase("Success")) {
                                Toast.makeText(ActivitySettings.this, message, Toast.LENGTH_SHORT).show();
                                dialogProgress.dismiss();
                                SessionManager.setSavedSearchStatus(ActivitySettings.this, strSavedSearchStatus);
                                SessionManager.setNewsLetterStatus(ActivitySettings.this, strNewsLetterStatus);
                                SessionManager.setSavedSearchTypeStatus(ActivitySettings.this, strAlertTime);
                                SessionManager.setSavedSMSStatus(ActivitySettings.this, strSmsStatus);
                            } else {
                                Toast.makeText(ActivitySettings.this, message, Toast.LENGTH_SHORT).show();
                                dialogProgress.dismiss();
                                statusNotUpdated();
                            }
                            dialogProgress.dismiss();
                        } else {
                            dialogProgress.dismiss();
                            Toast.makeText(ActivitySettings.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                            statusNotUpdated();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateSettingsResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                        Toast.makeText(ActivitySettings.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        statusNotUpdated();
                    }
                });
    }

    public void getProfileInfo() {
        ApiLinks.getClient().create(ProfileInfoApi.class)
                .post(SessionManager.getAccessToken(ActivitySettings.this))
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
                                    SessionManager.saveLogin(ActivitySettings.this, data.getUserFirstName(),
                                            data.getUserLastName(), data.getUserID(),
                                            SessionManager.getAccessToken(ActivitySettings.this),
                                            data.getUserEmail(), data.getUserPhone(), data.getCountryCode(),
                                            data.getUserTypeID(),
                                            imagePath + data.getUserProfilePicture(), true);

                                    getSettingsStatus();
                                } else if (response.body().getCode() != null && response.body().getCode() == 409) {
                                    Utils.showAlertLogout(ActivitySettings.this, message);
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


    //TODO Set Data
    private void declarations() {
        radioGroupPropertyAlert = (RadioGroup) findViewById(R.id.radio_group_save_search_time);
        radioButtonAlertDaily = (RadioButton) findViewById(R.id.radio_button_save_search_daily);
        radioButtonAlertWeekly = (RadioButton) findViewById(R.id.radio_button_save_search_weekly);
        newsletters = (SwitchCompat) findViewById(R.id.preferences_switch_newsletters);
        saveSearch = (SwitchCompat) findViewById(R.id.preferences_switch_save_search_alert);
        smsAlerts = (SwitchCompat) findViewById(R.id.preferences_switch_sms);
        layoutProgress = (LinearLayout) findViewById(R.id.layout_progress_my_preferences);
        saveSettings = (TextView) findViewById(R.id.button_save_settings);
        layoutProgress.setVisibility(View.VISIBLE);
        dialogProgress = new DialogProgress(ActivitySettings.this);
        Utils.collapse(radioGroupPropertyAlert, 300);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_settings);
        navigationView = (NavigationView) findViewById(R.id.nav_view_settings);

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

        textName.setText(SessionManager.getFullName(ActivitySettings.this));
        textEmail.setText(SessionManager.getEmail(ActivitySettings.this));
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

    private void setPreferencesStatus() {
        if (strNewsLetterStatus.equalsIgnoreCase("Yes")) {
            newsletters.setChecked(true);
        } else {
            newsletters.setChecked(false);
        }
        if (strSavedSearchStatus.equalsIgnoreCase("Yes")) {
            saveSearch.setChecked(true);
        } else {
            saveSearch.setChecked(false);
        }
        if (strAlertTime.equals("Daily")) {
            radioButtonAlertDaily.setChecked(true);
            radioButtonAlertWeekly.setChecked(false);
        } else {
            radioButtonAlertDaily.setChecked(false);
            radioButtonAlertWeekly.setChecked(true);
        }
        if (strSmsStatus.equalsIgnoreCase("Yes")) {
            smsAlerts.setChecked(true);
        } else {
            smsAlerts.setChecked(false);
        }
        layoutProgress.setVisibility(View.GONE);
    }

    private void setStatus() {
        strNewsLetterStatus = SessionManager.getNewsLetterStatus(ActivitySettings.this);
        strSavedSearchStatus = SessionManager.getSavedSearchStatus(ActivitySettings.this);
        strAlertTime = SessionManager.getSavedSearchTypeStatus(ActivitySettings.this);
        strSmsStatus = SessionManager.getSavedSMSStatus(ActivitySettings.this);
        if (strNewsLetterStatus != null && !strNewsLetterStatus.equals("")) {
            if (strNewsLetterStatus.equalsIgnoreCase("Yes")) {
                newsletters.setChecked(true);
            } else {
                newsletters.setChecked(false);
            }
        } else {
            newsletters.setChecked(false);
            strNewsLetterStatus = "No";
        }
        if (strSavedSearchStatus != null && !strSavedSearchStatus.equals("")) {
            if (strSavedSearchStatus.equalsIgnoreCase("Yes")) {
                saveSearch.setChecked(true);
                Utils.expand(radioGroupPropertyAlert, 300);
            } else {
                saveSearch.setChecked(false);
                Utils.collapse(radioGroupPropertyAlert, 300);
            }
        } else {
            Utils.collapse(radioGroupPropertyAlert, 300);
            saveSearch.setChecked(false);
            strSavedSearchStatus = "No";
        }
        if (strAlertTime != null && !strAlertTime.equals("")) {
            Utils.expand(radioGroupPropertyAlert, 300);
            if (strAlertTime.equalsIgnoreCase("Daily")) {
                radioButtonAlertDaily.setChecked(true);
                radioButtonAlertWeekly.setChecked(false);
            } else {
                radioButtonAlertDaily.setChecked(false);
                radioButtonAlertWeekly.setChecked(true);
            }
        } else {
            radioButtonAlertDaily.setChecked(true);
            radioButtonAlertWeekly.setChecked(false);
            Utils.collapse(radioGroupPropertyAlert, 300);
        }
        layoutProgress.setVisibility(View.GONE);
    }

    private void statusNotUpdated() {
        if (checkPreferences.equals("NewsLetter")) {
            if (strNewsLetterStatus.equalsIgnoreCase("Yes")) {
                newsletters.setChecked(false);
            } else {
                newsletters.setChecked(true);
            }
        }
        if (checkPreferences.equals("SavedSearch")) {
            if (strSavedSearchStatus.equalsIgnoreCase("Yes")) {
                saveSearch.setChecked(false);
            } else {
                saveSearch.setChecked(true);
            }
        }
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


    //TODO Click Listener
    @Override
    public void onClick(View view) {
        if (view == profileLayout) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySettings.this, ProfileActivity.class);
            startActivity(intent);

        } else if (view == btnPostFreeAd) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySettings.this, PostPropertyPage01Activity.class);
            startActivity(intent);

        } else if (view == btnPostRequirement) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySettings.this, ActivityRequirementPurpose.class);
            startActivity(intent);

        } else if (view == textSavedSearches) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySettings.this, ActivitySavedSearchList.class);
            startActivity(intent);

        } else if (view == textShortlisted) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySettings.this, ActivityShortlisted.class);
            startActivity(intent);

        } else if (view == textPostedRequirements) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySettings.this, ActivityPostedRequirements.class);
            startActivity(intent);

        } else if (view == textAgents) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySettings.this, ActivityMyAgents.class);
            startActivity(intent);

        } else if (view == textPropertyEnquiry) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySettings.this, ActivityMyProperties.class);
            startActivity(intent);

        } else if (view == textSettings) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySettings.this, ActivitySettings.class);
            startActivity(intent);
            finish();

        } else if (view == textSubscriptions) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySettings.this, ActivityMySubscriptions.class);
            startActivity(intent);

        } else if (view == textEnquiriesOffers) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySettings.this, ActivityEnquiriesOffers.class);
            startActivity(intent);

        } else if (view == textViewingRequest) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySettings.this, ActivityViewingRequests.class);
            startActivity(intent);

        } else if (view == textUnifiedTenancy) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySettings.this, ActivityUnifiedTenancyContract.class);
            startActivity(intent);

        } else if (view == textOpenHouse) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySettings.this, ActivityOpenHouse.class);
            startActivity(intent);

        } else if (view == textMyQuestions) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivitySettings.this, ActivityMyQuestions.class);
            startActivity(intent);

        } else if (view == textLogout) {
            drawer.closeDrawer(GravityCompat.END);
            alertLogout();

        } else if (view == textHome) {
            drawer.closeDrawer(GravityCompat.END);
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
                SessionManager.logout(ActivitySettings.this);
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
