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
import com.paya.paragon.activity.AgentDetailsActivity;
import com.paya.paragon.activity.postProperty.PostPropertyPage01Activity;
import com.paya.paragon.activity.postRequirements.ActivityRequirementPurpose;
import com.paya.paragon.adapter.AdapterFollowingAgents;
import com.paya.paragon.api.AgentDetail.AgentFollowResponse;
import com.paya.paragon.api.AgentDetail.AgentsFollowApi;
import com.paya.paragon.api.agentList.AgentList;
import com.paya.paragon.api.agentsFolloing.AgentsFollowingApi;
import com.paya.paragon.api.agentsFolloing.AgentsFollowingResponse;
import com.paya.paragon.api.profileInfo.ProfileInfoApi;
import com.paya.paragon.api.profileInfo.ProfileInfoData;
import com.paya.paragon.api.profileInfo.ProfileInfoResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"RedundantCast", "HardCodedStringLiteral"})
public class ActivityMyAgents extends AppCompatActivity implements View.OnClickListener {

    ArrayList<AgentList> followingAgentsList = new ArrayList<>();
    AdapterFollowingAgents adapterFollowingAgents;
    RecyclerView recyclerMyAgents;
    DialogProgress dialogProgress;
    public Dialog alertDialog;

    //Side Menu
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
        setContentView(R.layout.layout_my_agents);
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.drawer_layout_my_agents));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        mTitle.setText(R.string.i_am_following_agents);
        mProfileImage = toolbar.findViewById(R.id.profile_image_my_account);

        declarations();

        if (!Utils.NoInternetConnection(ActivityMyAgents.this)) {
            dialogProgress.show();
            getAgentsFollowing();
            getProfileInfo();
        } else {
            Utils.NoInternetConnection(ActivityMyAgents.this);
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

    //TODO API Calls
    public void getAgentsFollowing() {
        ApiLinks.getClient().create(AgentsFollowingApi.class).post
                (SessionManager.getAccessToken(ActivityMyAgents.this),
                        SessionManager.getLanguageID(this), "0")
                .enqueue(new Callback<AgentsFollowingResponse>() {
                    @Override
                    public void onResponse(Call<AgentsFollowingResponse> call,
                                           Response<AgentsFollowingResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getResponse();
                            int code = response.body().getCode();
                            String imagePath = response.body().getImagePath();
                            if (code == 1 && message.equalsIgnoreCase("Success")) {
                                followingAgentsList = response.body().getData().getAgencyLists();
                                for (int i = 0; i < followingAgentsList.size(); i++) {
                                    followingAgentsList.get(i).setUserCompanyLogo(imagePath +
                                            followingAgentsList.get(i).getUserCompanyLogo());
                                }
                                adapterFollowingAgents = new AdapterFollowingAgents(ActivityMyAgents.this,
                                        followingAgentsList, new AdapterFollowingAgents.ItemClickAdapterListener() {
                                    @Override
                                    public void deleteClick(int position) {
                                        alertDialogUnFollowAgency(position);
                                    }

                                    @Override
                                    public void detailsClick(int position) {
                                        Intent intent = new Intent(ActivityMyAgents.this,
                                                AgentDetailsActivity.class);
                                        intent.putExtra("agentDetail", followingAgentsList.get(position));
                                        startActivity(intent);
                                    }
                                });
                                recyclerMyAgents.setAdapter(adapterFollowingAgents);
                                dialogProgress.dismiss();
                            } else {
                                Toast.makeText(ActivityMyAgents.this, "No agents Followed",
                                        Toast.LENGTH_SHORT).show();
                                dialogProgress.dismiss();
                                noRecords.setVisibility(View.VISIBLE);
                            }
                        } else {
                            dialogProgress.dismiss();
                            noRecords.setVisibility(View.VISIBLE);
                            Toast.makeText(ActivityMyAgents.this, "No Response",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AgentsFollowingResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                        noRecords.setVisibility(View.VISIBLE);
                        Toast.makeText(ActivityMyAgents.this, "No Response", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void unFollowAgency(String userID, final int position) {
        dialogProgress.show();
        ApiLinks.getClient().create(AgentsFollowApi.class).post(
                SessionManager.getAccessToken(ActivityMyAgents.this),
                userID)
                .enqueue(new Callback<AgentFollowResponse>() {
                    @Override
                    public void onResponse(Call<AgentFollowResponse> call,
                                           Response<AgentFollowResponse> response) {
                        String message = response.body().getMessage();
                        if (response.body().getResponse().equalsIgnoreCase("Success")) {
                            followingAgentsList.remove(position);
                            adapterFollowingAgents.notifyDataSetChanged();
                            if (followingAgentsList.size() == 0) {
                                noRecords.setVisibility(View.VISIBLE);
                            }
                        }
                        Toast.makeText(ActivityMyAgents.this, message, Toast.LENGTH_SHORT).show();
                        dialogProgress.dismiss();
                    }

                    @Override
                    public void onFailure(Call<AgentFollowResponse> call, Throwable t) {
                        Toast.makeText(ActivityMyAgents.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        dialogProgress.dismiss();
                    }
                });
    }

    public void getProfileInfo() {
        ApiLinks.getClient().create(ProfileInfoApi.class)
                .post(SessionManager.getAccessToken(ActivityMyAgents.this))
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
                                    SessionManager.saveLogin(ActivityMyAgents.this, data.getUserFirstName(), data.getUserLastName(),
                                            data.getUserID(), SessionManager.getAccessToken(ActivityMyAgents.this),
                                            data.getUserEmail(), data.getUserPhone(), data.getCountryCode(), data.getUserTypeID(),
                                            imagePath + data.getUserProfilePicture(), true);
                                } else if (response.body().getCode() != null && response.body().getCode() == 409) {
                                    Utils.showAlertLogout(ActivityMyAgents.this, message);
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
    private void alertDialogUnFollowAgency(final int position) {
        alertDialog = new Dialog(ActivityMyAgents.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_popup, null);
        LinearLayout cancel = alert_layout.findViewById(R.id.CancelPopUp);
        LinearLayout ok = alert_layout.findViewById(R.id.ApplyPopUP);
        TextView title = alert_layout.findViewById(R.id.alertTitle);
        title.setText(getString(R.string.are_you_sure_to_unfollow_this_agency));

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
                unFollowAgency(followingAgentsList.get(position).getUserID(), position);
            }

        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }


    ///TODO Set Data
    private void declarations() {
        dialogProgress = new DialogProgress(ActivityMyAgents.this);
        recyclerMyAgents = findViewById(R.id.recycler_my_agents);
        recyclerMyAgents.setLayoutManager(new LinearLayoutManager(ActivityMyAgents.this));

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_my_agents);
        navigationView = (NavigationView) findViewById(R.id.nav_view_my_agents);

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
        textSubscriptions = navigationView.findViewById(R.id.text_my_subscriptions_dashboard_drawer);
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

        setProfileImage();

        textName.setText(SessionManager.getFullName(ActivityMyAgents.this));
        textEmail.setText(SessionManager.getEmail(ActivityMyAgents.this));

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

    //TODO Click Listener
    @Override
    public void onClick(View view) {
        if (view == profileLayout) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMyAgents.this, ProfileActivity.class);
            startActivity(intent);

        } else if (view == btnPostFreeAd) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMyAgents.this, PostPropertyPage01Activity.class);
            startActivity(intent);

        } else if (view == btnPostRequirement) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMyAgents.this, ActivityRequirementPurpose.class);
            startActivity(intent);

        } else if (view == textSavedSearches) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMyAgents.this, ActivitySavedSearchList.class);
            startActivity(intent);

        } else if (view == textShortlisted) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMyAgents.this, ActivityShortlisted.class);
            startActivity(intent);

        } else if (view == textPostedRequirements) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMyAgents.this, ActivityPostedRequirements.class);
            startActivity(intent);

        } else if (view == textAgents) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMyAgents.this, ActivityMyAgents.class);
            startActivity(intent);
            finish();

        } else if (view == textPropertyEnquiry) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMyAgents.this, ActivityMyProperties.class);
            startActivity(intent);

        } else if (view == textSettings) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMyAgents.this, ActivitySettings.class);
            startActivity(intent);

        } else if (view == textSubscriptions) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMyAgents.this, ActivityMySubscriptions.class);
            startActivity(intent);

        } else if (view == textLogout) {
            drawer.closeDrawer(GravityCompat.END);
            alertLogout();

        } else if (view == textHome) {
            drawer.closeDrawer(GravityCompat.END);
            Intent intent = new Intent(ActivityMyAgents.this, ActivityHome.class);
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
                Intent intent = new Intent(ActivityMyAgents.this, ActivityHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                SessionManager.logout(ActivityMyAgents.this);
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
        Intent intent = new Intent(ActivityMyAgents.this, ActivityHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
