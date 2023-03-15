package com.paya.paragon.activity.login;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.google.protobuf.Api;
import com.paya.paragon.R;
import com.paya.paragon.api.checkForgotPassword.ForgotPasswordApi;
import com.paya.paragon.api.checkForgotPassword.ForgotPasswordResponse;
import com.paya.paragon.api.checkForgotPassword.requestOTPApi;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.CountryCode.CountryCodePicker;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"HardCodedStringLiteral", "RedundantCast"})
public class ActivityForgotPassword extends AppCompatActivity {

    ImageView close;
    TextView submitEmail, signIn;
    String strMobileNo = "";
    EditText etMobileNo;
    DialogProgress progress;
    CountryCodePicker mCountryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_forgot_password);
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.rl_forgot_password_parent_layout));
        declarations();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submitEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.NoInternetConnection(ActivityForgotPassword.this)) {
                    if (!etMobileNo.getText().toString().trim().equals("")) {
                        strMobileNo = etMobileNo.getText().toString();
                        if(Utils.isValidMobile(strMobileNo)){
                            progress.show();
                            setForgotPassword();
                        }else{
                            Toast.makeText(ActivityForgotPassword.this, getString(R.string.valid_phone_number), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ActivityForgotPassword.this, getString(R.string.your_phone), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityForgotPassword.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    //TODO API Calls
    private void setForgotPassword() {
      /*  ApiLinks.getClient().create(ForgotPasswordApi.class).post(
                strMobileNo,"1",SessionManager.getLanguageID(this))
                .enqueue(new Callback<ForgotPasswordResponse>() {
                    @Override
                    public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String Response = response.body().getResponse();
                            if (Response.equalsIgnoreCase("Success")) {
                                Toast.makeText(ActivityForgotPassword.this, message, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ActivityForgotPassword.this, message, Toast.LENGTH_SHORT).show();
                            }
                            progress.dismiss();
                        } else {
                            Toast.makeText(ActivityForgotPassword.this, "No Response", Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                        Toast.makeText(ActivityForgotPassword.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                });*/


        ApiLinks.getClient().create(requestOTPApi.class).post(strMobileNo,mCountryCodePicker.getSelectedCountryCodeWithPlus(),SessionManager.getLanguageID(this)).enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    String Response = response.body().getResponse();
                    if (Response.equalsIgnoreCase("Success")) {
                        Toast.makeText(ActivityForgotPassword.this, message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ActivityForgotPassword.this, ActivityOTP.class).putExtra("comingFrom","forgotpassword")
                                .putExtra("mobileno",strMobileNo).putExtra("country_code",mCountryCodePicker.getSelectedCountryCodeWithPlus()));
                    } else {
                        Toast.makeText(ActivityForgotPassword.this, message, Toast.LENGTH_SHORT).show();

                    }
                    progress.dismiss();
                } else {
                    Toast.makeText(ActivityForgotPassword.this, "No Response", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                Toast.makeText(ActivityForgotPassword.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });

//        startActivity(new Intent(ActivityForgotPassword.this, ActivityOTP.class).putExtra("comingFrom","forgotpassword"));

    }

    //TODO Set ShortListedCountData
    private void declarations() {
        progress = new DialogProgress(ActivityForgotPassword.this);
        mCountryCodePicker = findViewById(R.id.country_code_picker_resetpassword);

        submitEmail = (TextView) findViewById(R.id.button_submit_forgot_password);
        etMobileNo = (EditText) findViewById(R.id.edit_email_forgot_password);
        close = (ImageView) findViewById(R.id.close_forgot_password);
        signIn = (TextView) findViewById(R.id.sign_in_button_forgot_password);
        mCountryCodePicker.registerCarrierNumberEditText(etMobileNo);

    }


    //TODO Main Functions
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
}
