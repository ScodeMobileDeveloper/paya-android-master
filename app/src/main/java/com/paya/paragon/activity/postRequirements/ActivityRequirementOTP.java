package com.paya.paragon.activity.postRequirements;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.paya.paragon.R;
import com.paya.paragon.api.postRequirement.resendOTP.ReSendOTPApi;
import com.paya.paragon.api.postRequirement.resendOTP.ReSendOTPResponse;
import com.paya.paragon.api.postRequirement.verifyOTP.VerifyOTPApi;
import com.paya.paragon.api.postRequirement.verifyOTP.VerifyOTPResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.model.RequirementModel;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"RedundantCast", "HardCodedStringLiteral"})
public class ActivityRequirementOTP extends AppCompatActivity {
    EditText editOTP;
    TextView verifyOTP, resendOTP;
    DialogProgress mLoading;
    String strOTP;
    private static RequirementModel requirementModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_requirement_otp);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow_black);

        declarations();

        verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editOTP.getText().toString().isEmpty()) {
                    Toast.makeText(ActivityRequirementOTP.this, getString(R.string.please_enter_otp), Toast.LENGTH_SHORT).show();
                } else {
                    strOTP = editOTP.getText().toString();
                    getOTPVerify();
                }
            }
        });

        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOTPVerify();
            }
        });
    }

    //TODO Set data
    private void declarations() {
        editOTP = findViewById(R.id.edit_otp_requirement_otp);
        verifyOTP = findViewById(R.id.text_verify_requirement_otp);
        resendOTP = findViewById(R.id.text_resend_otp_requirement_otp);
        mLoading = new DialogProgress(ActivityRequirementOTP.this);
        requirementModel = SessionManager.getPostRequirement(ActivityRequirementOTP.this);
    }


    //TODO API Calls
    private void getOTPVerify() {
        mLoading.show();
        ApiLinks.getClient().create(VerifyOTPApi.class)
                .post(requirementModel.getRequirementID(),
                        strOTP, "postReq").enqueue(new Callback<VerifyOTPResponse>() {
            @Override
            public void onResponse(Call<VerifyOTPResponse> call,
                                   Response<VerifyOTPResponse> response) {

                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    String status = response.body().getResponse();
                    int code = response.body().getCode();
                    if (code == 4004 && status.equals("Success")) {
                        SessionManager.setPostRequirement(ActivityRequirementOTP.this, requirementModel);
                        startActivity(new Intent(ActivityRequirementOTP.this,
                                ActivityRequirementSubmitted.class));
                        finish();
                    } else {
                        Toast.makeText(ActivityRequirementOTP.this, message, Toast.LENGTH_SHORT).show();
                    }
                    mLoading.dismiss();
                } else {
                    mLoading.dismiss();
                    Toast.makeText(ActivityRequirementOTP.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VerifyOTPResponse> call, Throwable t) {
                mLoading.dismiss();
                Toast.makeText(ActivityRequirementOTP.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resendOTPVerify() {
        mLoading.show();
        ApiLinks.getClient().create(ReSendOTPApi.class)
                .post(requirementModel.getRequirementID(),
                        "postReq",
                        requirementModel.getCountryCode()).enqueue(new Callback<ReSendOTPResponse>() {
            @Override
            public void onResponse(Call<ReSendOTPResponse> call,
                                   Response<ReSendOTPResponse> response) {

                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    String status = response.body().getResponse();
                    int code = response.body().getCode();
                    if (code == 4004 && status.equals("Success")) {
                        Toast.makeText(ActivityRequirementOTP.this, message,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ActivityRequirementOTP.this, message, Toast.LENGTH_SHORT).show();
                    }
                    mLoading.dismiss();
                } else {
                    mLoading.dismiss();
                    Toast.makeText(ActivityRequirementOTP.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReSendOTPResponse> call, Throwable t) {
                mLoading.dismiss();
                Toast.makeText(ActivityRequirementOTP.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
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

}
