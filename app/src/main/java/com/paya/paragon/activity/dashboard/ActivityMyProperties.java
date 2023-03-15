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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.PropertyProjectListActivity;
import com.paya.paragon.R;
import com.paya.paragon.activity.details.ActivityPropertyDetails;
import com.paya.paragon.activity.postProperty.PostPropertyPage01Activity;
import com.paya.paragon.adapter.AdapterMyProperties;
import com.paya.paragon.api.myProperties.MyPropertiesApi;
import com.paya.paragon.api.myProperties.MyPropertiesItem;
import com.paya.paragon.api.myProperties.MyPropertiesResponse;
import com.paya.paragon.api.myPropertiesDelete.DeleteMyPropertiesApi;
import com.paya.paragon.api.myPropertiesDelete.DeleteMyPropertiesResponse;
import com.paya.paragon.classes.ChangePlanPopUp;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.PropertyProjectType;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"HardCodedStringLiteral", "RedundantCast"})
@SuppressLint("StaticFieldLeak")
public class ActivityMyProperties extends AppCompatActivity {

    ArrayList<MyPropertiesItem> myPropertyListData = new ArrayList<>();
    AdapterMyProperties adapterMyProperties;
    RecyclerView recyclerMyProperties;
    DialogProgress dialogProgress;
    public Dialog alertDialog;
    ChangePlanPopUp changePlanPopUp;

    TextView noRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_my_properties);
        setToolBar();

        if (getIntent().getStringExtra("confirm") != null) {
            String confirm = getIntent().getStringExtra("confirm");
            if (confirm.equals("true")) {
                showPropertyPostedAlert();
            }
        }
        declarations();

        if (!Utils.NoInternetConnection(ActivityMyProperties.this)) {
            getMyProperties();

        } else {
            Utils.showAlertNoInternetFinish(ActivityMyProperties.this);
        }
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        mTitle.setText(R.string.my_properties);
    }

    //TODO API Calls
    private void getMyProperties() {
        dialogProgress.show();
        Log.d("myproperties",SessionManager.getAccessToken(ActivityMyProperties.this)+" "+SessionManager.getLanguageID(this));

        ApiLinks.getClient().create(MyPropertiesApi.class).post(
                SessionManager.getAccessToken(ActivityMyProperties.this),
                SessionManager.getLanguageID(this))
                .enqueue(new Callback<MyPropertiesResponse>() {
                    @Override
                    public void onResponse(Call<MyPropertiesResponse> call,
                                           Response<MyPropertiesResponse> response) {
                        if (response.isSuccessful()) {
                            String status = response.body().getResponse();
                            int code = response.body().getCode();
                            String message = response.body().getMessage();
                            if (code == 4004 && status.equalsIgnoreCase("Success")) {
                                GlobalValues.myPropertiesImageUrl = response.body().getData().getImgPath();
                                myPropertyListData = response.body().getData().getProprtyList();
                                adapterMyProperties = new AdapterMyProperties(ActivityMyProperties.this,
                                        myPropertyListData, new AdapterMyProperties.ItemClickAdapterListener() {
                                    @Override
                                    public void deleteClick(int position) {
                                        alertDialogDeleteProperty(position);
                                    }

                                    @Override
                                    public void editClick(int position) {
                                        Intent intentEdit = new Intent(ActivityMyProperties.this, PostPropertyPage01Activity.class);
                                        intentEdit.putExtra("action", "edit");
                                        intentEdit.putExtra("propertyId", myPropertyListData.get(position).getPropertyID());
                                        startActivity(intentEdit);
                                    }

                                    @Override
                                    public void enquiryClick(int position) {
                                        Intent intent = new Intent(ActivityMyProperties.this, ActivityEnquiriesOffers.class);
                                        intent.putExtra("propID", myPropertyListData.get(position).getPropertyID());
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void itemClick(int position) {
                                        Intent intent = new Intent(ActivityMyProperties.this, ActivityPropertyDetails.class);
                                        intent.putExtra("propertyID", myPropertyListData.get(position).getPropertyID());
                                        intent.putExtra(Utils.TYPE, myPropertyListData.get(position).getPropertyPurpose().equalsIgnoreCase("Sell") ? PropertyProjectType.BUY : PropertyProjectType.RENT);
                                        intent.putExtra("position", "0");
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void changePlanClick(int position) {
                                        alertChangePlan(position);
                                    }

                                    @Override
                                    public void upgradePlanClick(int position) {
                                        if (!Utils.NoInternetConnection(ActivityMyProperties.this)) {
                                            Intent intent = new Intent(ActivityMyProperties.this, ActivityMyPlanUpgrade.class);
                                            intent.putExtra("propID", myPropertyListData.get(position).getPropertyID());
                                            intent.putExtra("planID", myPropertyListData.get(position).getPlanID());
                                            startActivity(intent);
                                        } else {
                                            Utils.NoInternetConnection(ActivityMyProperties.this);
                                        }
                                    }
                                });
                                recyclerMyProperties.setAdapter(adapterMyProperties);
                                dialogProgress.dismiss();
                            } else {
                                dialogProgress.dismiss();
                                noRecords.setVisibility(View.VISIBLE);
                            }
                        } else {
                            dialogProgress.dismiss();
                            noRecords.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<MyPropertiesResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                        noRecords.setVisibility(View.VISIBLE);
                    }
                });
    }

    public void deleteMyProperty(String propertyID, final int position) {
        if (!Utils.NoInternetConnection(ActivityMyProperties.this)) {
            dialogProgress.show();
            ApiLinks.getClient().create(DeleteMyPropertiesApi.class).post("Deleted", propertyID)
                    .enqueue(new Callback<DeleteMyPropertiesResponse>() {
                        @Override
                        public void onResponse(Call<DeleteMyPropertiesResponse> call,
                                               Response<DeleteMyPropertiesResponse> response) {
                            if (response.isSuccessful()) {
                                int code = response.body().getCode();
                                String status = response.body().getResponse();
                                if (code == 4004 && status.equalsIgnoreCase("success")) {
                                    dialogProgress.dismiss();
                                    myPropertyListData.remove(position);
                                    adapterMyProperties.notifyDataSetChanged();
                                    Toast.makeText(ActivityMyProperties.this, getString(R.string.property_deleted),
                                            Toast.LENGTH_SHORT).show();
                                    if (myPropertyListData.size() == 0) {
                                        noRecords.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    Toast.makeText(ActivityMyProperties.this, getString(R.string.no_response),
                                            Toast.LENGTH_SHORT).show();
                                    dialogProgress.dismiss();
                                }
                            } else {
                                dialogProgress.dismiss();
                                Toast.makeText(ActivityMyProperties.this, getString(R.string.no_response),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DeleteMyPropertiesResponse> call, Throwable t) {
                            dialogProgress.dismiss();
                            dialogProgress.dismiss();
                            Toast.makeText(ActivityMyProperties.this, getString(R.string.no_response),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Utils.showAlertNoInternet(ActivityMyProperties.this);
        }
    }

    //TODO Alert Delete
    @SuppressLint("SetTextI18n")
    private void alertDialogDeleteProperty(final int position) {
        alertDialog = new Dialog(ActivityMyProperties.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_popup, null);
        LinearLayout cancel = alert_layout.findViewById(R.id.CancelPopUp);
        LinearLayout ok = alert_layout.findViewById(R.id.ApplyPopUP);
        TextView title = alert_layout.findViewById(R.id.alertTitle);
        //You are not able to reuse the credit for this property if it is deleted.
        title.setText(getString(R.string.are_you_sure_want_to_delete_it));

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
                deleteMyProperty(myPropertyListData.get(position).getPropertyID(), position);
            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void showPropertyPostedAlert() {
        alertDialog = new Dialog(ActivityMyProperties.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_ad_posted_popup,
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

    @SuppressLint("SetTextI18n")
    private void alertChangePlan(final int position) {
        changePlanPopUp = new ChangePlanPopUp(ActivityMyProperties.this,
                R.layout.alert_change_plan, myPropertyListData.get(position).getActivePlanList());
        changePlanPopUp.show();
    }

    //TODO Set Data
    private void declarations() {
        recyclerMyProperties = (RecyclerView) findViewById(R.id.recycler_my_properties);
        dialogProgress = new DialogProgress(ActivityMyProperties.this);
        recyclerMyProperties.setNestedScrollingEnabled(false);
        recyclerMyProperties.setLayoutManager(new LinearLayoutManager(ActivityMyProperties.this));
        noRecords = findViewById(R.id.text_no_records_found);
        noRecords.setVisibility(View.GONE);
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
        Intent intent = new Intent(this, PropertyProjectListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("searchPropertyPurpose", "Sell");
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.rl_my_properties_parent));
    }


}
