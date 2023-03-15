package com.paya.paragon.activity.dashboard;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.protobuf.Api;
import com.paya.paragon.R;
import com.paya.paragon.activity.login.ActivityForgotPassword;
import com.paya.paragon.activity.login.ActivityLoginEmail;
import com.paya.paragon.activity.login.ActivityOTP;
import com.paya.paragon.api.checkForgotPassword.changePasswordApi;
import com.paya.paragon.api.getProfileDetails.BaseResponse;
import com.paya.paragon.api.getProfileDetails.ChangePasswordAPI;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.PasswordValidation;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText edt_confirm_password, edt_new_password, current_password;
    private TextView mTvChangePassword;
    private Button btn_change_password;
    private DialogProgress dialogProgress;
    private  String  strPassword,strCfPassword,comingFrom;

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.col_change_password_parent_layout));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initialise_toolbar();
        initialise();
    }

    private void initialise() {
        edt_confirm_password = (EditText) findViewById(R.id.edt_confirm_password);
        current_password = (EditText) findViewById(R.id.current_password);
        edt_new_password = (EditText) findViewById(R.id.edt_new_password);
        btn_change_password = (Button) findViewById(R.id.btn_change_password);
        mTvChangePassword = findViewById(R.id.tv_cp);

        comingFrom = getIntent().getStringExtra("comingFrom");

        if(comingFrom!=null && comingFrom.equalsIgnoreCase("forgotpassword")){
            current_password.setVisibility(View.GONE);
            mTvChangePassword.setVisibility(View.GONE);
        }
        else{
            current_password.setVisibility(View.VISIBLE);
            mTvChangePassword.setVisibility(View.VISIBLE);

        }

        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comingFrom.equalsIgnoreCase("profile")){

                    if (!checkEmptyValues("Current Password", current_password.getText().toString().trim()) && validatePassword())
                {
                    showProgress();
                        ApiLinks.getClient().create(ChangePasswordAPI.class).postPassword(
                                SessionManager.getAccessToken(ChangePasswordActivity.this),
                                SessionManager.getLanguageID(ChangePasswordActivity.this),
                                current_password.getText().toString(),
                                edt_new_password.getText().toString(),
                                edt_confirm_password.getText().toString()).
                                enqueue(new Callback<BaseResponse>() {
                                    @Override
                                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                        hideProgress();
                                        if (response.body().getResponse() != null && response.body().getResponse().equalsIgnoreCase("Success")) {

                                            showToastMessage(getString(R.string.password_changed_successfully));
                                            finish();
                                        } else {
                                            showToastMessage(getString(R.string.no_response));
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                                        hideProgress();
                                        showToastMessage(t.toString());
                                    }
                                });
                    }



                }
                else {
                    if(validatePassword()){
                        String mobileNo = getIntent().getStringExtra("mobileno");
                        showProgress();


                        ApiLinks.getClient().create(changePasswordApi.class).postPassword(
                               mobileNo,edt_new_password.getText().toString().trim(),edt_confirm_password.getText().toString().trim(),SessionManager.getLanguageID(ChangePasswordActivity.this)).enqueue(new Callback<BaseResponse>() {
                            @Override
                            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                hideProgress();

                                if(response.isSuccessful()){
                                    String message = response.body().getMessage();
                                    String Response = response.body().getResponse();
                                    if (Response.equalsIgnoreCase("Success")) {
                                        Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ChangePasswordActivity.this, ActivityLoginEmail.class).putExtra("comingFrom","menu"));
                                        finish();
                                    } else {
                                        Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(ChangePasswordActivity.this, "No Response", Toast.LENGTH_SHORT).show();

                                }

                            }

                            @Override
                            public void onFailure(Call<BaseResponse> call, Throwable t) {
                                hideProgress();

                            }
                        });

                    }

                }

            }
        });
    }

    private void initialise_toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        mTitle.setText(R.string.change_password);
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

    private void showProgress() {
        dialogProgress = new DialogProgress(this);
        dialogProgress.show();
    }

    private void hideProgress() {
        if (dialogProgress != null) {
            dialogProgress.dismiss();
        }

    }

    private boolean validatePassword() {
        if (edt_new_password.getText().toString().trim().equals("")) {
            edt_new_password.clearFocus();
            edt_new_password.requestFocus();
            Toast.makeText(ChangePasswordActivity.this, getString(R.string.enter_your_password), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            strPassword = edt_new_password.getText().toString();
            ArrayList<PasswordValidation> arrayList = Utils.validatePassword(strPassword);
            if (strPassword.contains(" ")) {
                Toast.makeText(ChangePasswordActivity.this,
                        getString(R.string.password_without_space), Toast.LENGTH_SHORT).show();
                return false;
            }
            if (arrayList.get(0) != PasswordValidation.PASSWORD_OK) {
                String msg = getResources().getString(R.string.password_validation_characters);
                Toast.makeText(ChangePasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (edt_confirm_password.getText().toString().trim().equals("")) {
            edt_confirm_password.clearFocus();
            edt_confirm_password.requestFocus();
            Toast.makeText(ChangePasswordActivity.this, getString(R.string.confirm_your_password), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            strCfPassword = edt_confirm_password.getText().toString();
        }
        if (!strPassword.equals(strCfPassword)) {
            Toast.makeText(ChangePasswordActivity.this, getString(R.string.password_mismatch), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showToastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public Boolean checkEmptyValues(String label, String value) {


        if (TextUtils.isEmpty(value) || value.compareTo("") == 0) {
            showToastMessage("Enter " + label);
            return true;
        } else {
            return false;
        }
    }

    public Boolean isSame(String pswd1, String pswd2) {
        if (pswd1.compareTo(pswd2) == 0) {
            return true;
        } else {
            showToastMessage("Password mismatch");
            return false;
        }
    }
}
