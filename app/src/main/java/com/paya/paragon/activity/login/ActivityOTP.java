package com.paya.paragon.activity.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chaos.view.PinView;
import com.paya.paragon.PropertyProjectListActivity;
import com.paya.paragon.R;
import com.paya.paragon.activity.dashboard.ActivityMyProperties;
import com.paya.paragon.activity.dashboard.ChangePasswordActivity;
import com.paya.paragon.activity.dashboard.ProfileActivity;
import com.paya.paragon.api.checkForgotPassword.resetPasswordVerifyOTp;
import com.paya.paragon.api.postRequirement.verifyOTP.VerifyOTPResponse;
import com.paya.paragon.api.profilePhoneUpdate.ProfilePhoneNoUpdate;
import com.paya.paragon.api.userSignUp.verifyOTP.ResendLoginOTPApi;
import com.paya.paragon.api.userSignUp.verifyOTP.VerifyLoginOTPApi;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"RedundantCast", "HardCodedStringLiteral"})
public class ActivityOTP extends AppCompatActivity {
    EditText editOTP;
    PinView OTP;
    TextView verifyOTP, resendOTP, skipText;
    DialogProgress mLoading;
    String strOTP;
    String comingFrom="";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_otp);
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.rl_login_otp_parent_layout));

        comingFrom = getIntent().getStringExtra("comingFrom");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        mTitle.setText(R.string.verify);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow_black);

        declarations();
        if(comingFrom!=null){
            if(comingFrom.equalsIgnoreCase("signin")){
                builder = new AlertDialog.Builder(this,R.style.AlertDialogCustom);
                builder.setMessage(getResources().getString(R.string.phone_no_verity))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.req_otp), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                resendOTPVerify();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.login), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                startActivity(new Intent(ActivityOTP.this,ActivityLoginEmail.class).putExtra("comingFrom","menu"));

                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle(getResources().getString(R.string.req_otp));
                alert.show();



            }
        }

        SpannableString text = new SpannableString(getString(R.string.resend_text));
        int length = text.length();
        ClickableSpan resendClick = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                resendOTPVerify();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        };

        text.setSpan(resendClick, length - 10, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.menu_email)), length - 10, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        resendOTP.setText(text, TextView.BufferType.SPANNABLE);
        resendOTP.setMovementMethod(LinkMovementMethod.getInstance());
        resendOTP.setTextSize(17);
        verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  if (editOTP.getText().toString().isEmpty()) {
                    Toast.makeText(ActivityOTP.this, getString(R.string.please_enter_otp), Toast.LENGTH_SHORT).show();
                } else {
                    strOTP = editOTP.getText().toString();
                    getOTPVerify();
                }*/
                if(OTP.getText().toString().isEmpty()){
                    Toast.makeText(ActivityOTP.this, getString(R.string.please_enter_otp), Toast.LENGTH_SHORT).show();

                }else {
                    strOTP = OTP.getText().toString();
                    if(strOTP.length()==6){
                        if(comingFrom.equalsIgnoreCase("profile")){
                            String phone  = getIntent().getStringExtra("phone");
                            String countryCode  = getIntent().getStringExtra("countryCode");
                            updateMobileNumber(phone,countryCode);
                        }else if(comingFrom.equalsIgnoreCase("forgotpassword")){

                            OtpVerifyforgotPassword();

                        }else{
                            getOTPVerify();

                        }

                    }else {
                        Toast.makeText(ActivityOTP.this, "Please enter valid otp", Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });
        skipText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void OtpVerifyforgotPassword() {
        mLoading.show();

        String mobileNo = getIntent().getStringExtra("mobileno");
        String country_code = getIntent().getStringExtra("country_code");

        ApiLinks.getClient().create(resetPasswordVerifyOTp.class).post(strOTP,mobileNo,country_code).enqueue(new Callback<VerifyOTPResponse>() {
            @Override
            public void onResponse(Call<VerifyOTPResponse> call, Response<VerifyOTPResponse> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    String status = response.body().getResponse();
                    int code = response.body().getCode();
                    if (status.equals("Success")) {
                        startActivity(new Intent(ActivityOTP.this, ChangePasswordActivity.class).putExtra("comingFrom",comingFrom).putExtra("mobileno",mobileNo));
                    }
                    else {
                        Toast.makeText(ActivityOTP.this, message, Toast.LENGTH_SHORT).show();
                    }

                    mLoading.dismiss();
                } else {
                    mLoading.dismiss();
                    Toast.makeText(ActivityOTP.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VerifyOTPResponse> call, Throwable t) {

                mLoading.dismiss();
            }
        });

    }

    private void updateMobileNumber(String phone, String countryCode) {

        mLoading.show();
        ApiLinks.getClient().create(ProfilePhoneNoUpdate.class).post(strOTP,SessionManager.getUserId(this),countryCode,phone).enqueue(new Callback<VerifyOTPResponse>() {
            @Override
            public void onResponse(Call<VerifyOTPResponse> call, Response<VerifyOTPResponse> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    String status = response.body().getResponse();
                    int code = response.body().getCode();
                    if (status.equals("Success")) {

                        redirectHomePage();
                    }
                    else {

                        Toast.makeText(ActivityOTP.this, message, Toast.LENGTH_SHORT).show();

                    }


                    mLoading.dismiss();
                } else {
                    mLoading.dismiss();
                    Toast.makeText(ActivityOTP.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<VerifyOTPResponse> call, Throwable t) {

                mLoading.dismiss();
            }
        });
    }

    //TODO Set data
    private void declarations() {
//        editOTP = findViewById(R.id.edit_otp_requirement_otp);
        OTP = findViewById(R.id.otp_pin_view);
        verifyOTP = findViewById(R.id.text_verify_requirement_otp);
        skipText = findViewById(R.id.skip_text);
        resendOTP = findViewById(R.id.text_resend_otp_requirement_otp);
        mLoading = new DialogProgress(ActivityOTP.this);
    }


    //TODO API Calls
    private void getOTPVerify() {
        mLoading.show();
        ApiLinks.getClient().create(VerifyLoginOTPApi.class)
                .post(strOTP, SessionManager.getUserId(this),
                        SessionManager.getLanguageID(this))
                .enqueue(new Callback<VerifyOTPResponse>() {
                    @Override
                    public void onResponse(Call<VerifyOTPResponse> call,
                                           Response<VerifyOTPResponse> response) {

                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String status = response.body().getResponse();
                            int code = response.body().getCode();
                            if (status.equals("Success")) {



                                redirectHomePage();
                            }
                            else {
                                SessionManager.setOTPverified(ActivityOTP.this,false);
                                Toast.makeText(ActivityOTP.this, getResources().getString(R.string.invalid_otp), Toast.LENGTH_SHORT).show();


                            }


                            mLoading.dismiss();
                        } else {
                            mLoading.dismiss();
                            Toast.makeText(ActivityOTP.this, getString(R.string.failed)+" "+"API call Failure", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<VerifyOTPResponse> call, Throwable t) {
                        mLoading.dismiss();
                        Toast.makeText(ActivityOTP.this, getString(R.string.failed)+" "+t, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void resendOTPVerify() {
        mLoading.show();
        ApiLinks.getClient().create(ResendLoginOTPApi.class)
                .post(SessionManager.getUserId(ActivityOTP.this),SessionManager.getPhone(ActivityOTP.this),SessionManager.getCountryCodePlus(ActivityOTP.this)).enqueue(new Callback<VerifyOTPResponse>() {
            @Override
            public void onResponse(Call<VerifyOTPResponse> call, Response<VerifyOTPResponse> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    String status = response.body().getResponse();
                    int code = response.body().getCode();
                    if (status.equals("Success")) {

                    }
                    Toast.makeText(ActivityOTP.this, message, Toast.LENGTH_SHORT).show();

                    mLoading.dismiss();
                } else {
                    mLoading.dismiss();
                    Toast.makeText(ActivityOTP.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VerifyOTPResponse> call, Throwable t) {
                mLoading.dismiss();
                Toast.makeText(ActivityOTP.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
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

    public void redirectHomePage(){
        SessionManager.setisLogin(this,true);
        SessionManager.setOTPverified(this,true);

        if (comingFrom != null)
            if (comingFrom.equalsIgnoreCase("menu"))
                startActivity(new Intent(ActivityOTP.this, ActivityMyProperties.class));
            else if(comingFrom.equalsIgnoreCase("profile"))
                startActivity(new Intent(ActivityOTP.this, ProfileActivity.class));
            else
                startActivity(new Intent(ActivityOTP.this, ActivityMyProperties.class));

        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SessionManager.setOTPverified(ActivityOTP.this,false);

        startActivity(new Intent(ActivityOTP.this, PropertyProjectListActivity.class));
        finish();
    }

}
