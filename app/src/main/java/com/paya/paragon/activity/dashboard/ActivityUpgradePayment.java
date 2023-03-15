package com.paya.paragon.activity.dashboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.*;

import com.paya.paragon.BuildConfig;
import com.paya.paragon.R;
import com.paya.paragon.activity.PaymentActivity;
import com.paya.paragon.activity.postRequirements.ActivityRequirementSubmitted;
import com.paya.paragon.api.getCityListApi.GetCityApi;
import com.paya.paragon.api.getCityListApi.GetCityData;
import com.paya.paragon.api.getCityListApi.GetCityResponse;
import com.paya.paragon.api.getStateApi.GetStateApi;
import com.paya.paragon.api.getStateApi.GetStateList;
import com.paya.paragon.api.getStateApi.GetStateResponse;
import com.paya.paragon.api.planPayment.PlanPaymentApi;
import com.paya.paragon.api.planPayment.PlanPaymentResponse;
import com.paya.paragon.api.upgradePlanApi.*;
import com.paya.paragon.api.upgradePlanListing.UpgradePlanListData;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.CountryCode.CountryCodePicker;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.ListDialogBox;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"HardCodedStringLiteral", "RedundantCast", "ConstantConditions"})
public class ActivityUpgradePayment extends AppCompatActivity {

    boolean isValid;

    TextView tvPaymentMethods;
    TextView tvPlanAmount, tvPlanName;
    TextView tvSubTotalAmount, tvTotalAmount;
    EditText edtFirstName, edtLastName, edtEmail, edtPhone, billingAddress1,/* billingAddress2,*/ userZip;
    TextView tvCountry, tvState, tvCity, taxable_amount, reward_point_amount, add_reward_point, upgradePlan;
    LinearLayout add_reward_point_lay;
    LinearLayout taxable_amount_lay, reward_point_lay;
    UpgradePlanListData selectedData;
    String planTitle, planAmount, email, phone, userId, lastName, firstName;
    private CheckBox reward_point_ch;

    public static int selectedPosition = 100;
    String selectedCountryName, selectedCountryId;
    String selectedStateName, selectedStateId;
    String selectedPaymentName, selectedPaymentId;
    String selectedCityName, selectedCityId;
    private UpgradePlanData upgradePlanData;
    ArrayList<UpgradePlanCountryData> countryList;
    private ArrayList<PaymentSettings> paymentSettings;
    ArrayList<GetStateList> stateList;
    ArrayList<GetCityData> cityList;
    List<String> countryNameList;
    List<String> stateNameList;
    List<String> cityNameList;
    List<String> paymentNameList;
    private CountryCodePicker codePicker;
    DialogProgress mLoadingDialog;
    String strFirstName = "", strLastName = "", strEmail = "", strCountryCode = "";
    String strPhone = "", strBillingAddress1 = "", addRewardPoint = "";
    public Dialog alertDialog;
    String propID = "";
    private EditText edt_phone;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_payment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);

        selectedPosition = 100;
        mLoadingDialog = new DialogProgress(ActivityUpgradePayment.this);
        userId = SessionManager.getAccessToken(ActivityUpgradePayment.this);
        tvPaymentMethods = findViewById(R.id.tv_payment_methods);
        selectedData = (UpgradePlanListData) getIntent().getSerializableExtra("selectedData");
        propID = getIntent().getStringExtra("propID");
        planAmount = selectedData.getPlanPrice();
        planTitle = selectedData.getPlanTitle();
        mTitle.setText(planTitle);

        tvPlanAmount = findViewById(R.id.tv_plan_amount);
        tvPlanAmount.setText("AED " + planAmount);
        tvPlanName = findViewById(R.id.tv_plan_name);
        tvPlanName.setText(planTitle);
        tvSubTotalAmount = findViewById(R.id.tv_sub_total);

        tvTotalAmount = findViewById(R.id.tv_total);
        reward_point_ch = findViewById(R.id.reward_point_ch);

        edtFirstName = findViewById(R.id.edt_first_name);
        edtLastName = findViewById(R.id.edt_last_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPhone = findViewById(R.id.edt_phone);

        billingAddress1 = findViewById(R.id.billingAddrs1);
        // billingAddress2 = findViewById(R.id.billingAddrs2);
        userZip = findViewById(R.id.userZip);
        upgradePlan = (TextView) findViewById(R.id.button_upgrade_plan_my_plan);

        tvCountry = findViewById(R.id.tv_country);
        tvState = findViewById(R.id.tv_emirate);
        tvCity = findViewById(R.id.tv_city);
        taxable_amount = findViewById(R.id.taxable_amount);
        reward_point_amount = findViewById(R.id.reward_point_amount);
        add_reward_point = findViewById(R.id.add_reward_point);

        add_reward_point_lay = findViewById(R.id.add_reward_point_lay);
        taxable_amount_lay = findViewById(R.id.taxable_amount_lay);
        reward_point_lay = findViewById(R.id.reward_point_lay);
        edt_phone=findViewById(R.id.edt_phone);
        codePicker = findViewById(R.id.country_code);
        codePicker.setCountryForPhoneCode(971);
        codePicker.setCountryForNameCode(GlobalValues.countryCode);
        codePicker.registerCarrierNumberEditText(edt_phone);


        getData();


        //payment methods

        tvPaymentMethods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // spinnerPaymentMethods.performClick();
                selectedPosition = 100;
                ListDialogBox dialogBox = new ListDialogBox(ActivityUpgradePayment.this, paymentNameList,
                        "Please choose any payment method", "payment");
                dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogBox.show();
                dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (selectedPosition != 100) {
                            selectedPaymentName = paymentSettings.get(selectedPosition).getPaySetGroupLabel();
                            selectedPaymentId = paymentSettings.get(selectedPosition).getPaySetGroupID();
                            tvPaymentMethods.setText(selectedPaymentName);
                        }
                    }
                });
            }
        });

        tvCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = 100;
                ListDialogBox dialogBox = new ListDialogBox(ActivityUpgradePayment.this, countryNameList,
                        "Select A Country", "payment");
                dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogBox.show();
                dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (selectedPosition != 100) {
                            selectedCountryName = countryList.get(selectedPosition).getCountryName();
                            selectedCountryId = countryList.get(selectedPosition).getCountryID();
                            tvCountry.setText(selectedCountryName);
                            tvState.setHint("Select A State");
                            tvCity.setHint("Select A District");
                            tvState.setText("");
                            tvCity.setText("");
                            selectedStateId = null;
                            selectedStateName = "";
                            selectedCityId = null;
                            selectedCityName = "";
                            getState(false);
                        }
                    }
                });
            }
        });

        tvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = 100;
                ListDialogBox dialogBox = new ListDialogBox(ActivityUpgradePayment.this, stateNameList,
                        "Select A State", "payment");
                dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogBox.show();
                dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (selectedPosition != 100) {
                            selectedStateName = stateList.get(selectedPosition).getStateName();
                            selectedStateId = stateList.get(selectedPosition).getStateID();
                            tvState.setText(selectedStateName);
                            tvCity.setHint("Select A District");
                            tvCity.setText("");
                            selectedCityId = null;
                            selectedCityName = "";
                            getCity(false);
                        }
                    }
                });
            }
        });

        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedStateId!=null&&!selectedStateId.equals("")) {
                    selectedPosition = 100;
                    ListDialogBox dialogBox = new ListDialogBox(ActivityUpgradePayment.this, cityNameList,
                            "Select A District", "payment");
                    dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogBox.show();
                    dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            if (selectedPosition != 100) {
                                selectedCityName = cityList.get(selectedPosition).getCityName();
                                selectedCityId = cityList.get(selectedPosition).getCityID();
                                tvCity.setText(selectedCityName);
                            }
                        }
                    });
                } else {
                    Toast.makeText(ActivityUpgradePayment.this, getString(R.string.please_select_state),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean checkValidation() {
        if (edtFirstName.getText().toString().trim().isEmpty()) {
            edtFirstName.clearFocus();
            edtFirstName.requestFocus();
            Toast.makeText(ActivityUpgradePayment.this, getString(R.string.please_enter_first_name), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            strFirstName = edtFirstName.getText().toString();
        }
        if (edtLastName.getText().toString().trim().isEmpty()) {
            edtLastName.clearFocus();
            edtLastName.requestFocus();
            Toast.makeText(ActivityUpgradePayment.this, getString(R.string.please_enter_last_name), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            strLastName = edtLastName.getText().toString();
        }

        if (selectedCountryId == null) {
            tvCountry.clearFocus();
            tvCountry.requestFocus();
            Toast.makeText(ActivityUpgradePayment.this, getString(R.string.please_select_country), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (selectedStateId == null) {
            tvState.clearFocus();
            tvState.requestFocus();
            Toast.makeText(ActivityUpgradePayment.this, getString(R.string.please_select_state), Toast.LENGTH_SHORT).show();
            return false;
        }


        if (selectedCityId == null) {
            tvCity.clearFocus();
            tvCity.requestFocus();
            Toast.makeText(ActivityUpgradePayment.this, getString(R.string.please_select_district), Toast.LENGTH_SHORT).show();
            return false;
        }





        if (billingAddress1.getText().toString().trim().isEmpty()) {
            billingAddress1.clearFocus();
            billingAddress1.requestFocus();
            Toast.makeText(ActivityUpgradePayment.this,  getString(R.string.enter_billing_address), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            strBillingAddress1 = billingAddress1.getText().toString();
        }
        strPhone = edtPhone.getText().toString().trim();
        if (edtPhone.getText().toString().trim().isEmpty()) {
            edtPhone.clearFocus();
            edtPhone.requestFocus();
            Toast.makeText(ActivityUpgradePayment.this, getString(R.string.please_enter_phone_number), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!Utils.isValidMobile(strPhone)) {
                Toast.makeText(ActivityUpgradePayment.this,getString(R.string.valid_mobile_number),
                        Toast.LENGTH_SHORT).show();
                return false;
            }

        }











        if (edtEmail.getText().toString().trim().isEmpty()) {
            edtEmail.clearFocus();
            edtEmail.requestFocus();
            Toast.makeText(ActivityUpgradePayment.this, getString(R.string.please_enter_email_address), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            strEmail = edtEmail.getText().toString();
            if (TextUtils.isEmpty(strEmail) || !Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                Toast.makeText(ActivityUpgradePayment.this, getString(R.string.please_enter_valid_email_address), Toast.LENGTH_SHORT).show();
                return false;
            }
        }




        if (selectedPaymentId == null) {
            tvPaymentMethods.clearFocus();
            tvPaymentMethods.requestFocus();
            Toast.makeText(ActivityUpgradePayment.this, getString(R.string.payment_method_validation), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (reward_point_ch.isChecked()) {
            addRewardPoint = "Yes";
        } else {
            addRewardPoint = "No";
        }
        strCountryCode = codePicker.getDefaultCountryCode().toLowerCase();
        return true;
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

    private void getData() {
        mLoadingDialog.show();
        ApiLinks.getClient().create(UpgradePlanApi.class).post(userId,
                selectedData.getPlanID(),
                "" + SessionManager.getLanguageID(this))
                .enqueue(new Callback<UpgradePlanResponse>() {
                    @Override
                    public void onResponse(Call<UpgradePlanResponse> call, Response<UpgradePlanResponse> response) {
                        if (response.isSuccessful()) {
                            String status = response.body().getResponse();
                            String message = response.body().getMessage();
                            if (status.equals("Success")) {
                                countryList = response.body().getData().getResCountry();
                                paymentSettings = response.body().getData().getPaymentSettings();
                                upgradePlanData = response.body().getData();
                                setDetails();
                            } else {
                                Toast.makeText(ActivityUpgradePayment.this, message, Toast.LENGTH_SHORT).show();

                            }
                        } else {

                            Toast.makeText(ActivityUpgradePayment.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                        mLoadingDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<UpgradePlanResponse> call, Throwable t) {
                        mLoadingDialog.dismiss();
                        Toast.makeText(ActivityUpgradePayment.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();

                    }
                });


    }

    private void getState(final boolean isFirstTime) {
        mLoadingDialog.show();
        ApiLinks.getClient().create(GetStateApi.class).post(selectedCountryId,
                "" + SessionManager.getLanguageID(this))
                .enqueue(new Callback<GetStateResponse>() {
                    @Override
                    public void onResponse(Call<GetStateResponse> call, Response<GetStateResponse> response) {
                        if (response.isSuccessful()) {
                            String status = response.body().getResponse();
                            String message = response.body().getMessage();
                            if (status.equals("Success")) {

                                mLoadingDialog.dismiss();
                                stateList = response.body().getData().getStateList();
                                stateNameList = new ArrayList<>();
                                for (int i = 0; i < stateList.size(); i++) {
                                    stateNameList.add(stateList.get(i).getStateName());
                                }
                                if (isFirstTime) {
                                    if (upgradePlanData.getStateID() != null && !upgradePlanData.getStateID().equals("")) {
                                        String stateName = "";
                                        selectedStateId = upgradePlanData.getStateID();
                                        for (int i = 0; i < stateList.size(); i++) {

                                            if (selectedStateId.equalsIgnoreCase(stateList.get(i).getStateID()))
                                                stateName = stateList.get(i).getStateName();
                                        }
                                        if (!stateName.equals("")) {
                                            tvState.setText(stateName);
                                            getCity(true);
                                        } else selectedStateId = null;

                                    }
                                }


                            } else {
                                Toast.makeText(ActivityUpgradePayment.this, message, Toast.LENGTH_SHORT).show();

                            }
                        } else {

                            Toast.makeText(ActivityUpgradePayment.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                        mLoadingDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<GetStateResponse> call, Throwable t) {
                        Toast.makeText(ActivityUpgradePayment.this, getString(R.string.no_response) + t.getMessage(), Toast.LENGTH_SHORT).show();
                        mLoadingDialog.dismiss();
                    }
                });


    }

    private void getCity(final boolean isFirstTime) {
        mLoadingDialog.show();
        ApiLinks.getClient().create(GetCityApi.class).post(selectedStateId)
                .enqueue(new Callback<GetCityResponse>() {
                    @Override
                    public void onResponse(Call<GetCityResponse> call, Response<GetCityResponse> response) {
                        if (response.isSuccessful()) {
                            String status = response.body().getResponse();
                            String message = response.body().getMessage();
                            if (status.equals("Success")) {

                                mLoadingDialog.dismiss();
                                cityList = response.body().getCityList();
                                cityNameList = new ArrayList<>();
                                for (int i = 0; i < cityList.size(); i++) {
                                    cityNameList.add(cityList.get(i).getCityName());
                                }
                                if (isFirstTime) {
                                    if (upgradePlanData.getCityID() != null && !upgradePlanData.getCityID().equals("")) {
                                        String cityName = "";
                                        selectedCityId = upgradePlanData.getCityID();
                                        for (int i = 0; i < cityList.size(); i++) {

                                            if (selectedCityId.equalsIgnoreCase(cityList.get(i).getCityID()))
                                                cityName = cityList.get(i).getCityName();
                                        }
                                        if (!cityName.equals("")) {
                                            tvCity.setText(cityName);
                                        } else selectedCityId = null;

                                    }
                                }

                            } else {
                                Toast.makeText(ActivityUpgradePayment.this, message, Toast.LENGTH_SHORT).show();

                            }
                        } else {

                            Toast.makeText(ActivityUpgradePayment.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                        mLoadingDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<GetCityResponse> call, Throwable t) {
                        Toast.makeText(ActivityUpgradePayment.this, getString(R.string.no_response) + t.getMessage(), Toast.LENGTH_SHORT).show();
                        mLoadingDialog.dismiss();
                    }
                });


    }

    private void alertSuccess(String successTitle) {
        alertDialog = new Dialog(ActivityUpgradePayment.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_success_popup, null);
        ImageView close = alert_layout.findViewById(R.id.close_success_dialog);
        TextView title = alert_layout.findViewById(R.id.title_alert_success_popup);
        TextView message = alert_layout.findViewById(R.id.message_alert_success_popup);
        title.setText(successTitle);
        message.setText("");
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
      /*  alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Intent intentLogin = new Intent(ActivityUpgradePayment.this, ActivityMyProperties.class);
                startActivity(intentLogin);
                finish();
            }
        });*/
        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void submitData() {
        mLoadingDialog.show();
        ApiLinks.getClient().create(PlanPaymentApi.class).post(userId,
                selectedData.getPlanID(),
                "" + SessionManager.getLanguageID(this),
                selectedCityId, selectedStateId, selectedCountryId,
                strPhone, userZip.getText().toString(), firstName,
                lastName, strBillingAddress1,""/* billingAddress2.getText().toString()*/,
                strEmail, addRewardPoint, upgradePlanData.getPointUsable(),
                selectedPaymentId, propID).enqueue(new Callback<PlanPaymentResponse>() {
            @Override
            public void onResponse(Call<PlanPaymentResponse> call, Response<PlanPaymentResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getResponse();
                    String message = response.body().getMessage();
                    if (status.equals("Success")) {
                        alertSuccess(message);
                    } else {
                        Toast.makeText(ActivityUpgradePayment.this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityUpgradePayment.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                }
                mLoadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<PlanPaymentResponse> call, Throwable t) {
                mLoadingDialog.dismiss();
                Toast.makeText(ActivityUpgradePayment.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
            }
        });
    }

    int totalAmount;

    @SuppressLint("SetTextI18n")
    private void setDetails() {
        firstName = upgradePlanData.getBillingFirstName();
        lastName = upgradePlanData.getBillingLastName();
        email = upgradePlanData.getUserEmail();
        phone = upgradePlanData.getUserPhone();

        edtFirstName.setText(firstName);
        edtLastName.setText(lastName);
        edtEmail.setText(email);
        edtPhone.setText(phone);

        if (upgradePlanData.getCountryCode() != null && !upgradePlanData.getCountryCode().equals(""))
            codePicker.setDefaultCountryUsingNameCode(upgradePlanData.getCountryCode());


        billingAddress1.setText(upgradePlanData.getBillingAddrs1());
        //billingAddress2.setText(upgradePlanData.getBillingAddrs2());
        userZip.setText(upgradePlanData.getUserZip());
        tvSubTotalAmount.setText(getString(R.string.currency_symbol) + " " + upgradePlanData.getOrderTotalAmount());
        if (upgradePlanData.getTax_applicable().equalsIgnoreCase("Yes")) {
            taxable_amount_lay.setVisibility(View.VISIBLE);
            tvTotalAmount.setText(getString(R.string.currency_symbol) + " " + upgradePlanData.getResult_Tax_calc());
            taxable_amount.setText(getString(R.string.currency_symbol) + " " + upgradePlanData.getTax_calc());
            totalAmount = Integer.parseInt(upgradePlanData.getResult_Tax_calc());
        } else {
            taxable_amount_lay.setVisibility(View.GONE);
            tvTotalAmount.setText(getString(R.string.currency_symbol) + " " + upgradePlanData.getOrderTotalAmount());
            totalAmount = Integer.parseInt(upgradePlanData.getOrderTotalAmount());
        }

        if (upgradePlanData.getPointUsable() != null && !upgradePlanData.getPointUsable().equalsIgnoreCase("") &&
                upgradePlanData.getAvailRewardPoint() != null && !upgradePlanData.getAvailRewardPoint().equalsIgnoreCase("")) {
            add_reward_point_lay.setVisibility(View.VISIBLE);
            String add_reward_pointText = "( You have " + upgradePlanData.getAvailRewardPoint() + " points for redemption. Out of this you can use "
                    + upgradePlanData.getPointUsable() + " points for this transaction )";
            add_reward_point.setText(add_reward_pointText);
        } else add_reward_point_lay.setVisibility(View.GONE);

        reward_point_ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    reward_point_lay.setVisibility(View.VISIBLE);
                    reward_point_amount.setText(upgradePlanData.getPointUsable());
                    int calcAmount = totalAmount - Integer.parseInt(upgradePlanData.getPointUsable());
                    //upgradePlanData.setOrderTotalAmount(String.valueOf(calcAmount));
                    tvTotalAmount.setText(getString(R.string.currency_symbol) + " " + calcAmount);

                } else {
                    reward_point_lay.setVisibility(View.GONE);
                    tvTotalAmount.setText(getString(R.string.currency_symbol) + " " + totalAmount);
                }
            }
        });
        countryNameList = new ArrayList<>();
        for (int i = 0; i < countryList.size(); i++) {
            countryNameList.add(countryList.get(i).getCountryName());
        }
        paymentNameList = new ArrayList<>();
        for (int i = 0; i < paymentSettings.size(); i++) {
            paymentNameList.add(paymentSettings.get(i).getPaySetGroupLabel());
        }

        if (upgradePlanData.getCountryID() != null && !upgradePlanData.getCountryID().equals("")) {
            String countryName = "";
            selectedCountryId = upgradePlanData.getCountryID();
            for (int i = 0; i < countryList.size(); i++) {

                if (selectedCountryId.equalsIgnoreCase(countryList.get(i).getCountryID()))
                    countryName = countryList.get(i).getCountryName();
            }
            if (!countryName.equals("")) {
                tvCountry.setText(countryName);
                getState(true);
            } else selectedCountryId = null;

        }

        upgradePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()) {
                    //  submitData();
                    String url = BuildConfig.APP_BASE_URL + "user/upgradePayment",
                            postData = "",
                            sUrl = BuildConfig.APP_BASE_URL + "user/paymentSuccessMob", lUrl = BuildConfig.APP_BASE_URL + "user/paymentFailedMob";
                    payThroughUrl(url, sUrl, lUrl);
                }
            }
        });
    }

    public void payThroughUrl(String url, String sUrl, String lUrl) {
        Intent intent = new Intent(this, PaymentActivity.class);


        String postData = "";

        postData = "userID=" +  SessionManager.getUserId(ActivityUpgradePayment.this) + "" +
                "&planID=" + selectedData.getPlanID() +
                "&languageID=" + SessionManager.getLanguageID(this) +
                "&billingCity=" + selectedCityId +

                "&billingState=" + selectedStateId +
                "&billingCountry=" + selectedCountryId +
                "&billingPhone=" + strPhone +
                "&billingZip=" + userZip.getText().toString() +

                "&billingFirstName=" + firstName +
                "&billingLastName=" + lastName +
                "&billingAddress=" + strBillingAddress1 +
                "&billingAddress1=" +" " /*billingAddress2.getText().toString()*/ +

                "&billingEmail=" + strEmail +
                "&selectedCityID=" + "" +
                "&purchasedCurrency=" + "6" +
                "&orderUserCreditAmount=" + "0" +

                "&orderTotalAmount=" + upgradePlanData.getOrderTotalAmount() +
                "&paymentSettingsGroupID=" + selectedPaymentId +
                "&propertyID=" + propID+
 "&addRewardPoint=" + addRewardPoint ;
//                "&rewardPoint=" + upgradePlanData.getPointUsable() +
        intent.putExtra("payUrl", url);
        intent.putExtra("post", postData);
        intent.putExtra("sUrl", sUrl);
        intent.putExtra("lUrl", lUrl);
        Log.d("PayURLTEST", url+postData);
        startActivityForResult(intent, 1148);
    }
   /* private boolean validatePhone() {
        if (editText_phone_user_sign_up.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.please_enter_phone_number), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!validateMobile()) {
                Toast.makeText(this,getString(R.string.valid_mobile_number),
                        Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }

        }
    }*/

    public boolean validateMobile() {
        if (!codePicker.getSelectedCountryCode().equals("964")) {
            codePicker.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
                @Override
                public void onValidityChanged(boolean isValidNumber) {
                    isValid = isValidNumber;
                }
            });
        } else isValid = !Utils.isValidMobile(strPhone);

        return isValid;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1148) {
            if (resultCode == Activity.RESULT_OK) {
                startActivity(new Intent(this, ActivityRequirementSubmitted.class).putExtra("payment","payment"));
                finish();
            } else {
                alertSuccess("Payment Error");
            }
        }
    }
}
