package com.paya.paragon.activity.localExpert;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.adapter.AdapterCityLocalExpertListing;
import com.paya.paragon.adapter.AdapterLocalExpertListing;
import com.paya.paragon.api.cityList.CityListingApi;
import com.paya.paragon.api.cityList.CityListingResponse;
import com.paya.paragon.api.index.LocationInfo;
import com.paya.paragon.api.localExpertList.LocalExpertListApi;
import com.paya.paragon.api.localExpertList.LocalExpertListData;
import com.paya.paragon.api.localExpertList.LocalExpertListResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.ListBusinessDialogeBox;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("HardCodedStringLiteral")
public class ActivityLocalExpertListing extends AppCompatActivity {

    String categoryName = "", categoryId = "";
    RecyclerView recyclerLocalExperts;
    AdapterLocalExpertListing adapterExpertListing;
    ArrayList<LocalExpertListData> expertModels;
    DialogProgress progress;

    private ArrayList<LocationInfo> locationInfo;
    String selectedCityID = "", selectedCityName = "";
    RelativeLayout rlHead;
    TextView tvLocation;
    DialogProgress dialogProgress;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_local_expert_listing);
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.rl_local_expert_list_parent_layout));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        mTitle.setTypeface(mTitle.getTypeface(), Typeface.BOLD);
        dialogProgress = new DialogProgress(ActivityLocalExpertListing.this);
        declarations();

        categoryName = getIntent().getStringExtra("categoryName");
        categoryId = getIntent().getStringExtra("categoryId");
        mTitle.setText(categoryName);

        if (!Utils.NoInternetConnection(ActivityLocalExpertListing.this)) {
            getCityList();
            getLocalExpertList();
        } else {
            progress.dismiss();
            Toast.makeText(ActivityLocalExpertListing.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
        rlHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }

    private void getLocalExpertList() {
        progress.show();
        ApiLinks.getClient().create(LocalExpertListApi.class).post(
                categoryId, "0", selectedCityID, SessionManager.getLanguageID(this))
                .enqueue(new Callback<LocalExpertListResponse>() {
                    @Override
                    public void onResponse(Call<LocalExpertListResponse> call, Response<LocalExpertListResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            int code = response.body().getCode();
                            if (code == 100) {
                                if (response.body().getResponse().equals("Failure")) {
                                    finish();
                                    Toast.makeText(ActivityLocalExpertListing.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    expertModels = response.body().getData();
                                    String imgUrl = response.body().getImgUrl();
                                    adapterExpertListing = new AdapterLocalExpertListing(ActivityLocalExpertListing.this, expertModels, imgUrl, categoryId);
                                    recyclerLocalExperts.setAdapter(adapterExpertListing);
                                }
                            } else {
                                Toast.makeText(ActivityLocalExpertListing.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ActivityLocalExpertListing.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                        progress.dismiss();
                    }

                    @Override
                    public void onFailure(Call<LocalExpertListResponse> call, Throwable t) {
                        Toast.makeText(ActivityLocalExpertListing.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                });
    }

    private void declarations() {
        expertModels = new ArrayList<>();
        recyclerLocalExperts = (RecyclerView) findViewById(R.id.recycler_local_experts_listing);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ActivityLocalExpertListing.this);
        recyclerLocalExperts.setLayoutManager(mLayoutManager);
        progress = new DialogProgress(this);
        rlHead = findViewById(R.id.rl_head);
        rlHead.setVisibility(View.GONE);
        tvLocation = findViewById(R.id.tv_local);
        TextView listBusiness = (TextView) findViewById(R.id.list_your_business_local_expert_details);


        listBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListBusinessDialogeBox dialogBox = new ListBusinessDialogeBox(ActivityLocalExpertListing.this, categoryId);
                dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogBox.show();
                dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {

                    }
                });
            }
        });
    }

    public void getCityList() {
        ApiLinks.getClient().create(CityListingApi.class).post("" + SessionManager.getLanguageID(this))
                .enqueue(new Callback<CityListingResponse>() {
                    @Override
                    public void onResponse(Call<CityListingResponse> call, Response<CityListingResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getResponse();
                            if (message != null && message.equalsIgnoreCase("Success")) {
                                rlHead.setVisibility(View.VISIBLE);
                                locationInfo = response.body().getData().getCityList();
                                showCityListSpinner();
                            }

                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<CityListingResponse> call, Throwable t) {

                    }
                });
    }

    private void showCityListSpinner() {
        if (SessionManager.getLocationId(this).equals("-1")) {
            String address = GlobalValues.address;
            address = address.toLowerCase();
            for (int i = 0; i < locationInfo.size(); i++) {
                String cityName = locationInfo.get(i).getCityName();
                if (address.contains(cityName.toLowerCase())) {
                    selectedCityID = locationInfo.get(i).getCityID();
                    selectedCityName = locationInfo.get(i).getCityName();

                }
            }

        }
        dialog = new Dialog(this);
        @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.show_price_list_selector, null);
        final RecyclerView recyclerMinPrice = (RecyclerView) view.findViewById(R.id.recycler_price_list);
        recyclerMinPrice.setLayoutManager(new LinearLayoutManager(ActivityLocalExpertListing.this));
        AdapterCityLocalExpertListing adapter = new AdapterCityLocalExpertListing(locationInfo, ActivityLocalExpertListing.this);
        recyclerMinPrice.setAdapter(adapter);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        adapter.setMinPriceInterface(new AdapterCityLocalExpertListing.SelectCityInterface() {
            @Override
            public void onPriceSelected(LocationInfo locationInfo) {
                tvLocation.setText(locationInfo.getCityName());
                selectedCityID = locationInfo.getCityID();
                selectedCityName = locationInfo.getCityName();
                dialogProgress.show();
                getLocalExpertList();
                dialog.dismiss();
            }
        });
     /*   if (SessionManager.getLocationId(this).equals("-1")) {
            Toast.makeText(ActivityLocalExpertListing.this, "Please select your City", Toast.LENGTH_LONG).show();
            dialog.show();
        } else {

        }*/
    }

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
