package com.paya.paragon.activity.dashboard;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paya.paragon.R;
import com.paya.paragon.api.myPlan.MyPlanApi;
import com.paya.paragon.api.myPlan.MyPlanData;
import com.paya.paragon.api.myPlan.MyPlanResponse;
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

import java.util.concurrent.TimeUnit;

@SuppressWarnings({"HardCodedStringLiteral", "RedundantCast"})
public class ActivityMyPlan extends AppCompatActivity {

    TextView planUpgrade;
    DialogProgress mLoading;
    LinearLayout mLoadingLayout;
    TextView planName, planPrice, planStartDate, planEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_my_plan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(16);
        mTitle.setText(R.string.my_plan);

        declarations();


        if (!Utils.NoInternetConnection(ActivityMyPlan.this)) {
            mLoading.show();
            mLoadingLayout.setVisibility(View.VISIBLE);
            getMyPlan();
        } else {
            Utils.NoInternetConnection(ActivityMyPlan.this);
        }

        planUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityMyPlan.this, ActivityMyPlanUpgrade.class));
            }
        });


    }


    //TODO API Calls
    private void getMyPlan() {
        ApiLinks.getClient().create(MyPlanApi.class).post(SessionManager.getAccessToken(ActivityMyPlan.this))
                .enqueue(new Callback<MyPlanResponse>() {
                    @Override
                    public void onResponse(Call<MyPlanResponse> call, Response<MyPlanResponse> response) {
                        if (response.isSuccessful()) {
                            String status = response.body().getResponse();
                            String message = response.body().getMessage();
                            int code = response.body().getCode();
                            if (code == 100 && status.equals("Success")) {
                                mLoading.hide();
                                if (response.body().getData() != null) {
                                    mLoadingLayout.setVisibility(View.GONE);
                                    MyPlanData data;
                                    data = response.body().getData();
                                    setDataToUI(data);
                                }
                            }else if (response.body().getCode() !=null && response.body().getCode() ==409)
                            {
                                Utils.showAlertLogout(ActivityMyPlan.this,message);
                            } else {
                                mLoading.hide();
                                Toast.makeText(ActivityMyPlan.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            mLoading.hide();
                            Toast.makeText(ActivityMyPlan.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MyPlanResponse> call, Throwable t) {
                        mLoading.hide();
                        Toast.makeText(ActivityMyPlan.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    //TODO Set Data
    private void declarations() {
        planUpgrade = (TextView) findViewById(R.id.button_upgrade_plan_my_plan);
        mLoading = new DialogProgress(this);
        mLoadingLayout = (LinearLayout) findViewById(R.id.progressbar_layout_my_plan_listing);
        planName = (TextView) findViewById(R.id.current_plan_name_my_plan);
        planPrice = (TextView) findViewById(R.id.current_plan_price_my_plan);
        planStartDate = (TextView) findViewById(R.id.current_plan_purchased_date_my_plan);
        planEndDate = (TextView) findViewById(R.id.current_plan_expiry_date_my_plan);
        mLoading.hide();

    }

    private void setDataToUI(MyPlanData data) {
        if (data.getPlanTitle() != null && !data.getPlanTitle().equals("")) {
            planName.setText(data.getPlanTitle());
        } else {
            planName.setText("");
        }

        if (data.getPlanPrice() != null && !data.getPlanPrice().equals("")) {
            String price = "";
            if (data.getCurrencySymbolLeft() != null && !data.getCurrencySymbolLeft().equals("")) {
                price = price + data.getCurrencySymbolLeft();
            }
            if (data.getPlanPrice() != null && !data.getPlanPrice().equals("")) {
                price = price + " " + data.getPlanPrice();
            }
            if (data.getCurrencySymbolRight() != null && !data.getCurrencySymbolRight().equals("")) {
                price = price + data.getCurrencySymbolRight();
            }
            planPrice.setText(price);
        } else {
            planPrice.setText("");
        }
        if (data.getPlanPurchaseDate() != null && !data.getPlanPurchaseDate().equals("")) {
            planStartDate.setText(Utils.convertToDateOnlyFormat(data.getPlanPurchaseDate()));
        } else {
            planStartDate.setText("");
        }
        if (data.getPlanExpiryDate() != null && !data.getPlanExpiryDate().equals("")) {
            planEndDate.setText(Utils.convertToDateOnlyFormat(data.getPlanExpiryDate()));
        } else {
            planEndDate.setText("");
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
}
