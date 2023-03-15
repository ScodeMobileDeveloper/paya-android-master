package com.paya.paragon.activity.postRequirements;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.paya.paragon.R;
import com.paya.paragon.api.postRequirement.requirementPost.PostRequirement1Api;
import com.paya.paragon.api.postRequirement.requirementPost.PostRequirementResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.model.RequirementModel;
import com.paya.paragon.utilities.CountryCode.CountryCodePicker;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"RedundantCast", "StringConcatenationInLoop", "HardCodedStringLiteral"})
public class ActivityRequirementContact extends AppCompatActivity {
    EditText editName, editEmail, editMobile;
    String strName = "", strEmail = "", strPhone = "", strAttributes = "", strUserID = "";
    String[] enquiryID;
    String strPropertyIds = "", strCountryCode = "", reqID = "";
    TextView btnContinue;
    DialogProgress mLoading;
    CountryCodePicker codePicker;
    private static RequirementModel requirementModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_requirement_contact);
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.rl_requirement_contact_parent_layout));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow_black);

        declarations();

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }


    //TODO Set Data
    private void declarations() {
        editName = findViewById(R.id.edit_name_requirement_contact);
        editEmail = findViewById(R.id.edit_email_requirement_contact);
        editMobile = findViewById(R.id.edit_phone_requirement_contact);
        codePicker = findViewById(R.id.country_code_requirement_contact);
        codePicker.registerCarrierNumberEditText(editMobile);
        btnContinue = findViewById(R.id.text_continue_requirement_contact);
        mLoading = new DialogProgress(ActivityRequirementContact.this);
        requirementModel = SessionManager.getPostRequirement(ActivityRequirementContact.this);

        codePicker.setCountryForNameCode(GlobalValues.countryCode);
        if (SessionManager.getLoginStatus(ActivityRequirementContact.this)) {
            strUserID = SessionManager.getAccessToken(ActivityRequirementContact.this);
            strName = SessionManager.getFullName(ActivityRequirementContact.this);
            strEmail = SessionManager.getEmail(ActivityRequirementContact.this);
            strPhone = SessionManager.getPhone(ActivityRequirementContact.this);
            strCountryCode = SessionManager.getCountryCode(ActivityRequirementContact.this);
            if (requirementModel.getRequirementAction().equalsIgnoreCase("post")) {
                editName.setText(strName);
                editEmail.setText(strEmail);
                editMobile.setText(strPhone);
                codePicker.setCountryForNameCode(strCountryCode.toUpperCase());
                requirementModel.setName(strName);
                requirementModel.setEmail(strEmail);
                requirementModel.setPhone(strPhone);
                requirementModel.setCountryCode(strCountryCode.toLowerCase());
            } else {
                editName.setText(requirementModel.getName());
                editEmail.setText(requirementModel.getEmail());
                editMobile.setText(requirementModel.getPhone());
                codePicker.setCountryForNameCode(requirementModel.getCountryCode());
            }
        }
    }

    private void checkValidation() {
        strName = editName.getText().toString().trim();
        strEmail = editEmail.getText().toString().trim();
        strPhone = editMobile.getText().toString().trim();
        if (strName != null && !strName.equals("")) {
            if (Utils.isValidName(strName)) {
                requirementModel.setName(strName);
            } else {
                Toast.makeText(ActivityRequirementContact.this, getString(R.string.please_enter_valid_name), Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(ActivityRequirementContact.this, getString(R.string.please_enter_name), Toast.LENGTH_SHORT).show();
            return;
        }
        if (strEmail != null && !strEmail.equals("")) {
            if (!TextUtils.isEmpty(strEmail) && Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                requirementModel.setEmail(strEmail);
            }
        }

        if (!validatePhone())
            return;
        requirementModel.setPhone(strPhone);
        requirementModel.setCountryCode(codePicker.getSelectedCountryNameCode().toLowerCase());
        postRequirement(requirementModel);
    }

    private boolean validatePhone() {
        if (strPhone.equals("")) {
            Toast.makeText(ActivityRequirementContact.this, getString(R.string.please_enter_phone_number), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!Utils.isValidMobile(strPhone)) {
                Toast.makeText(ActivityRequirementContact.this, getString(R.string.valid_mobile_number),
                        Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }

        }
    }

    boolean isValid;

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

    //TODO API Calls
    private void postRequirement(RequirementModel data) {
        if (requirementModel.getRequirementAction().equals("edit")) {
            reqID = requirementModel.getRequirementID();
        }
        mLoading.show();
        ApiLinks.getClient().create(PostRequirement1Api.class)
                .post(SessionManager.getLanguageID(this),
                        reqID,
                        data.getPurpose(),
                        data.getPropertyTypeParentID(),
                        data.getMinPrice(),
                        data.getMaxPrice(),
                        data.getReqBedroom(),
                        data.getLocationList(),
                        data.getName(),
                        data.getEmail(),
                        data.getPhone(),
                        strUserID,
                        data.getCountryCode(),
                        data.getMortgage()

                )
                .enqueue(new Callback<PostRequirementResponse>() {
                    @Override
                    public void onResponse(Call<PostRequirementResponse> call,
                                           Response<PostRequirementResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String status = response.body().getResponse();
                            int code = response.body().getCode();
                            if (code == 4004 && status.equals("Success")) {
                                if (message != null && !message.equals("")) {
                                    if (!message.equalsIgnoreCase("Success")) {
                                        enquiryID = message.split("~*~");
                                        requirementModel.setRequirementID(enquiryID[2]);
                                        SessionManager.setPostRequirement(ActivityRequirementContact.this, requirementModel);
                                        startActivity(new Intent(ActivityRequirementContact.this,
                                                ActivityRequirementOTP.class));
                                    } else {
                                        SessionManager.setPostRequirement(ActivityRequirementContact.this, requirementModel);
                                        startActivity(new Intent(ActivityRequirementContact.this,
                                                ActivityRequirementSubmitted.class));
                                        finish();
                                    }
                                } else {
                                    SessionManager.setPostRequirement(ActivityRequirementContact.this, requirementModel);
                                    startActivity(new Intent(ActivityRequirementContact.this,
                                            ActivityRequirementSubmitted.class));
                                }
                            } else {
                                Toast.makeText(ActivityRequirementContact.this, message, Toast.LENGTH_SHORT).show();
                            }
                            mLoading.dismiss();
                        } else {
                            mLoading.dismiss();
                            Toast.makeText(ActivityRequirementContact.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PostRequirementResponse> call, Throwable t) {
                        mLoading.dismiss();
                        Toast.makeText(ActivityRequirementContact.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
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
