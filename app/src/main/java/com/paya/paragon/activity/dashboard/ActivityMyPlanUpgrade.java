package com.paya.paragon.activity.dashboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paya.paragon.R;
import com.paya.paragon.adapter.AdapterPlanInfo;
import com.paya.paragon.api.upgradePlanListing.UpgradePlanListApi;
import com.paya.paragon.api.upgradePlanListing.UpgradePlanListData;
import com.paya.paragon.api.upgradePlanListing.UpgradePlanListResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"HardCodedStringLiteral", "RedundantCast"})
@SuppressLint("StaticFieldLeak")
public class ActivityMyPlanUpgrade extends AppCompatActivity {

    RecyclerView recyclerPlan;
    private ArrayList<UpgradePlanListData> upgradePlanList;
    AdapterPlanInfo adapterPlanInfo;
    DialogProgress mLoading;
    private TextView planDisplayTime, featuredPlanDisplayTime, planPhotoUploadCount, planVideoUploadingCount;
    private TextView all_platinum_text, features_text, planPropertyDetails, upgradePlan;
    private LinearLayout container;
    private Dialog alertDialog;

    UpgradePlanListData selectedData;
    String propID = "", planID = "", selectedPlanID = "";
    private TextView plan_persional_community,
            plan_profesional_desc, plan_featured_list,
            plan_virtual_tour, plan_photography, plan_free_home, plan_duration, plan_other_portals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_upgrade_plan);
/*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(16);
        mTitle.setText(R.string.plan_upgrade);
*/
        propID = getIntent().getStringExtra("propID");
        if (getIntent() != null && getIntent().hasExtra("planID")) {
            planID = getIntent().getStringExtra("planID");
        }
        declarations();

        if (!Utils.NoInternetConnection(ActivityMyPlanUpgrade.this)) {
            mLoading.show();
            getMyPlanUpgradeListing();
        } else {
            Utils.NoInternetConnection(ActivityMyPlanUpgrade.this);
        }

        upgradePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.equals(selectedPlanID, planID)) {
                    alertSimilarPlan();

                } else {


                    Intent in = new Intent(ActivityMyPlanUpgrade.this, ActivityUpgradePayment.class);
                    in.putExtra("selectedData", selectedData);
                    in.putExtra("propID", propID
                    );
                    startActivity(in);
                    finish();
                }
            }
        });

    }


    //TODO Set Data
    private void declarations() {
        recyclerPlan = (RecyclerView) findViewById(R.id.recycler_my_plan_upgrade_plan_info);
        mLoading = new DialogProgress(ActivityMyPlanUpgrade.this);
        upgradePlan = (TextView) findViewById(R.id.button_upgrade_plan_my_plan);

        plan_persional_community = (TextView) findViewById(R.id.plan_persional_community);
        plan_profesional_desc = (TextView) findViewById(R.id.plan_profesional_desc);
        plan_featured_list = (TextView) findViewById(R.id.plan_featured_list);
        plan_virtual_tour = (TextView) findViewById(R.id.plan_virtual_tour);
        plan_photography = (TextView) findViewById(R.id.plan_photography);
        plan_free_home = (TextView) findViewById(R.id.plan_free_home);
        plan_duration = (TextView) findViewById(R.id.plan_duration);
        plan_other_portals = (TextView) findViewById(R.id.plan_other_portals);


        planDisplayTime = (TextView) findViewById(R.id.planDisplayTime);
        featuredPlanDisplayTime = (TextView) findViewById(R.id.featuredPlanDisplayTime);
        planPhotoUploadCount = (TextView) findViewById(R.id.planphotouploadCount);
        planVideoUploadingCount = (TextView) findViewById(R.id.planVideoUploadingCount);
        planPropertyDetails = (TextView) findViewById(R.id.planPropertyDetails);
        all_platinum_text = (TextView) findViewById(R.id.all_platinum_text);
        features_text = (TextView) findViewById(R.id.features_text);
        container = (LinearLayout) findViewById(R.id.container);
        ImageView close = (ImageView) findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerPlan.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    public void SetPlanFeaturesData(int position) {
        selectedData = upgradePlanList.get(position);
        if (selectedData.getPlanTitle().equalsIgnoreCase("Assisted service")) {
            container.setVisibility(View.GONE);
            features_text.setVisibility(View.GONE);
            all_platinum_text.setVisibility(View.VISIBLE);
        } else {
            container.setVisibility(View.VISIBLE);
            features_text.setVisibility(View.VISIBLE);
            all_platinum_text.setVisibility(View.GONE);
            planDisplayTime.setText(selectedData.getPlanDisplayTime() + " " + getString(R.string.days));
            featuredPlanDisplayTime.setText(selectedData.getFeaturedPlanDisplayTime() + " " + getString(R.string.days));
            planPhotoUploadCount.setText(selectedData.getPlanphotouploadCount() + " " + getString(R.string.nos));
            planVideoUploadingCount.setText(selectedData.getPlanVideoUploadingCount() + " " + getString(R.string.nos));


            // planDisplayTime.setText(selectedData.getPlanDisplayTime());


            plan_persional_community.setText(selectedData.getPlanCommManager());
            plan_profesional_desc.setText(selectedData.getPlanPropertyDetails());
            plan_featured_list.setText(selectedData.getFeaturedPlanDisplayTime());
            plan_virtual_tour.setText(selectedData.getPlanVirtualTour());
            plan_photography.setText(selectedData.getPlanPhotography());
            plan_free_home.setText(selectedData.getPlanFreeHwe());
            planPropertyDetails.setText(selectedData.getPlanPropertyDetails());
            plan_duration.setText(selectedData.getPlanDisplayTime() + " " + getString(R.string.days));
            plan_other_portals.setText(selectedData.getPlanListOtherPortals());
            selectedData.getPlanListOtherPortals();
        }

    }


    //TODO API Calls
    private void getMyPlanUpgradeListing() {
        ApiLinks.getClient().create(UpgradePlanListApi.class).post(SessionManager.getAccessToken(ActivityMyPlanUpgrade.this))
                .enqueue(new Callback<UpgradePlanListResponse>() {
                    @Override
                    public void onResponse(Call<UpgradePlanListResponse> call,
                                           Response<UpgradePlanListResponse> response) {
                        if (response.isSuccessful()) {
                            String status = response.body().getResponse();
                            String message = response.body().getMessage();
                            if (status.equals("Success")) {
                                mLoading.dismiss();
                                upgradePlanList = new ArrayList<>();
                                upgradePlanList = response.body().getData().getResPlans();
                                String selectedId = upgradePlanList.get(0).getPlanID();
                                selectedPlanID = selectedId;
                                adapterPlanInfo = new AdapterPlanInfo(selectedId, ActivityMyPlanUpgrade.this, upgradePlanList, new AdapterPlanInfo.ItemClickAdapterListener() {
                                    @Override
                                    public void menuClick(int position,String planID) {
                                       selectedPlanID=planID;
                                        SetPlanFeaturesData(position);
                                    }
                                });
                                recyclerPlan.setAdapter(adapterPlanInfo);
                                recyclerPlan.scrollToPosition(0);
                                SetPlanFeaturesData(0);
                            } else {
                                Toast.makeText(ActivityMyPlanUpgrade.this, message, Toast.LENGTH_SHORT).show();
                                mLoading.dismiss();
                            }
                        } else {
                            mLoading.dismiss();
                            Toast.makeText(ActivityMyPlanUpgrade.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpgradePlanListResponse> call, Throwable t) {
                        mLoading.dismiss();
                        Toast.makeText(ActivityMyPlanUpgrade.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
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

    private void alertSimilarPlan() {
        alertDialog = new Dialog(ActivityMyPlanUpgrade.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.dialog_same_plan, null);


        alertDialog.setContentView(alert_layout);
        TextView tv_ok_button = alert_layout.findViewById(R.id.tv_ok_button);
        tv_ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }
}
