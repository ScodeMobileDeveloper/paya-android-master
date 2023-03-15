package com.paya.paragon.activity.dashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.paya.paragon.R;
import com.paya.paragon.activity.login.ActivityOTP;
import com.paya.paragon.adapter.AdapterCityListing;
import com.paya.paragon.api.cityList.CityListingApi;
import com.paya.paragon.api.cityList.CityListingResponse;
import com.paya.paragon.api.getProfileDetails.GetProfileApi;
import com.paya.paragon.api.getProfileDetails.GetProfileData;
import com.paya.paragon.api.getProfileDetails.GetProfileDetailsData;
import com.paya.paragon.api.getProfileDetails.GetProfileResponse;
import com.paya.paragon.api.index.LocationInfo;
import com.paya.paragon.api.postRequirement.verifyOTP.VerifyOTPResponse;
import com.paya.paragon.api.updateProfile.UpdateAgentProfileApi;
import com.paya.paragon.api.updateProfile.UpdateProfileApi;
import com.paya.paragon.api.updateProfile.UpdateProfileResponse;
import com.paya.paragon.api.userSignUp.verifyOTP.ResendLoginOTPApi;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.CircularImageView;
import com.paya.paragon.utilities.AppConstant;
import com.paya.paragon.utilities.CountryCode.CountryCodePicker;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"HardCodedStringLiteral", "RedundantCast", "RegExpRedundantEscape"})
public class ProfileActivity extends AppCompatActivity {

    CircularImageView ivProfile;
    ImageView ivPencil;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetBehavior bottomSheetBehavior;
    View bottomSheetView;
    LinearLayout ll_gallery, ll_remove;
    String userProfileImage = "hi", userID, imagePath,imageCompanyLogoPath;
    GetProfileDetailsData profileDetails;
    Geocoder geocoder;
    ScrollView scrollView;
    EditText edtName, edtLastName, edtEmail, edtAddress1, edtAddress2,edtCompanyName,edtFullName,edtSecPhone;
    TextView edtPhone,edtMainPhone;
    RadioButton rbMale, rbFemale;
    TextView tvLocalityInfo, btnSubmit,tvLocation;
    EditText edtLocality, edtLocalityInfo, edtCountry, edtState, edtDistrict, edtPinCode;
    LinearLayout individualLay,agencyLay;
    private ArrayList<LocationInfo> locationInfo;
    private ArrayList<LocationInfo> searchedLocatinInfo;
    AdapterCityListing adapter;
    LinearLayout phoneLay,agentPhoneLay;
    public static int PLACE_AUTOCOMPLETE_REQUEST_CODE = 300;
    String userEmail, userCompanyName,userFirstName, userLastName, userPhone,userPhone2, userGender, countryCode, userAddress,userLocation,userCity;
    String userAddress1, userAddress2;
    String googleStateName, googleCityName, googleCountryName, googleLocality, userZip;
    public static String selectedImageUrl = "";
    public static String selectedFinalImageUrl = "";
    DialogProgress dialogProgress;
    static final int ID_GALLERY = 1;
    static final int ID_CROP = 2;
    public static Bitmap bt;
    CountryCodePicker countryCodePicker,agentMainCCP,agentSecondaryCCP;
    boolean isUpdated = false;
    String successMessage = "";
    public static Bitmap bitmap;
    public Dialog alertDialog;
    public boolean isImageRemove = false;
    boolean isValid;
    String userTypeId="";

    @SuppressLint("InflateParams")
    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        mTitle.setText(R.string.edit_profile);

        if (SessionManager.getLoginStatus(ProfileActivity.this)) {
            userID = SessionManager.getAccessToken(ProfileActivity.this);
        } else {
            userID = "";
        }
        dialogProgress = new DialogProgress(ProfileActivity.this);
        geocoder = new Geocoder(ProfileActivity.this, Locale.getDefault());
        locationInfo = new ArrayList<>();

        //permission
        if (ContextCompat.checkSelfPermission(ProfileActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(ProfileActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }
        scrollView = findViewById(R.id.scrollView);
        edtName = findViewById(R.id.edt_name);
        edtLastName = findViewById(R.id.edt_last_name);
        edtEmail = findViewById(R.id.edt_email);
        phoneLay = findViewById(R.id.phone_lay);
        edtPhone = findViewById(R.id.edt_phone);
        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);
        edtAddress1 = findViewById(R.id.edt_address1);
        edtAddress2 = findViewById(R.id.edt_address2);

        edtCompanyName = findViewById(R.id.edt_company_name);
        edtFullName = findViewById(R.id.edt_full_name);
        agentPhoneLay = findViewById(R.id.agent_phone_lay);
        edtMainPhone = findViewById(R.id.edt_main_phone);
        edtSecPhone = findViewById(R.id.edt_sec_phone);
        tvLocation = findViewById(R.id.text_location_list);

        agentMainCCP = findViewById(R.id.cc_agent_profile);
        agentMainCCP.setCcpClickable(false);
        agentSecondaryCCP = findViewById(R.id.spcc_agent_profile);
        agentSecondaryCCP.registerCarrierNumberEditText(edtSecPhone);

        edtPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert();
            }
        });

        edtMainPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert();

            }
        });


        final Dialog dialog = new Dialog(this);
        @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.show_price_list_selector, null);
        final SearchView searchView = view.findViewById(R.id.sv_list_search);
        searchView.setVisibility(View.VISIBLE);
        final RecyclerView recyclerMinPrice = (RecyclerView) view.findViewById(R.id.recycler_price_list);
        recyclerMinPrice.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));
        adapter = new AdapterCityListing(locationInfo, ProfileActivity.this, SessionManager.getLocationId(this));
        recyclerMinPrice.setAdapter(adapter);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return false;
            }
        });
        adapter.setMinPriceInterface(new AdapterCityListing.SelectCityInterface() {
            @Override
            public void onPriceSelected(LocationInfo locationInfo) {
                tvLocation.setText(locationInfo.getCityName());
                userLocation = locationInfo.getCityID();
                Log.d("id",userLocation);

                dialog.dismiss();
            }
        });


        tvLocalityInfo = findViewById(R.id.tv_locality_info);
        edtLocalityInfo = findViewById(R.id.edt_locality_info);
        edtLocalityInfo.setVisibility(View.GONE);
        edtLocality = findViewById(R.id.edt_locality);
        edtCountry = findViewById(R.id.edt_country);
        edtState = findViewById(R.id.edt_state);
        edtDistrict = findViewById(R.id.edt_district);
        edtPinCode = findViewById(R.id.edt_pin_code);
        btnSubmit = findViewById(R.id.btn_submit);

        edtLocality.setEnabled(false);
        edtCountry.setEnabled(false);
        edtState.setEnabled(false);
//        edtDistrict.setEnabled(false);
        //edtPinCode.setEnabled(false);

        bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_profile_image, null);
        ll_gallery = (LinearLayout) bottomSheetView.findViewById(R.id.ll_gallery);
        ll_remove = (LinearLayout) bottomSheetView.findViewById(R.id.ll_remove);
        bottomSheetDialog = new BottomSheetDialog(ProfileActivity.this);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
        countryCodePicker = (CountryCodePicker) findViewById(R.id.country_code_profile);
        countryCodePicker.setCcpClickable(false);

        individualLay = findViewById(R.id.individual_lay);
        agencyLay = findViewById(R.id.agency_lay);
        ivProfile = findViewById(R.id.iv_profile);
        ivPencil = findViewById(R.id.iv_pencil);
        findViewById(R.id.btn_change_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        ivPencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ProfileActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    bottomSheetDialog.show();
                    if (userProfileImage == null || userProfileImage.equals("")) {
                        ll_remove.setVisibility(View.GONE);
                    } else {
                        ll_remove.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        ll_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isImageRemove = true;
                bottomSheetDialog.dismiss();
                //updateProfileAction();
                alertDeleteProfileImage();
            }
        });
        ll_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                if (ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                } else {
                    selectedImageUrl = "";
                    Intent in = new Intent(ProfileActivity.this, ProfileImageBrowseActivity.class);
                    in.putExtra("whichScreen","profile");
                    startActivityForResult(in, ID_GALLERY);
                }
            }
        });
        tvLocalityInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocationIntent();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isImageRemove = false;
                if(userTypeId.equals(AppConstant.INDIVIDUAL_ID)){
                    updateProfileAction();
                }
                else {
                    updateAgentProfileAction();
                }
            }
        });

        if (!Utils.NoInternetConnection(ProfileActivity.this)) {
            getLocationList();

        } else {
            Utils.showAlertNoInternet(ProfileActivity.this);
        }

    }

    private void showAlert() {
        Dialog alertDialog = new Dialog(this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_phoneno_update, null);
        com.rilixtech.widget.countrycodepicker.CountryCodePicker codePicker = alert_layout.findViewById(R.id.country_code_picker);
        EditText phone = alert_layout.findViewById(R.id.edt_phone);
        TextView reqOtp = alert_layout.findViewById(R.id.alert_req_otp);
        TextView cancel = alert_layout.findViewById(R.id.alert_cancel_button);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        reqOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phone.getText().toString().equalsIgnoreCase(userPhone)){
                    Toast.makeText(ProfileActivity.this, getResources().getString(R.string.enter_the_new_number), Toast.LENGTH_SHORT).show();

                }else {
                    if(Utils.isValidMobile(phone.getText().toString().trim())){
                        requestOTP(phone.getText().toString().trim(),codePicker.getSelectedCountryCodeWithPlus());
                    }
                    else {
                        Toast.makeText(ProfileActivity.this,
                                getString(R.string.valid_phone_number), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




        alertDialog.setContentView(alert_layout);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void requestOTP(String phone, String countryCode) {
        dialogProgress.show();
        ApiLinks.getClient().create(ResendLoginOTPApi.class)
                .post(SessionManager.getUserId(ProfileActivity.this),phone,countryCode).enqueue(new Callback<VerifyOTPResponse>() {
            @Override
            public void onResponse(Call<VerifyOTPResponse> call, Response<VerifyOTPResponse> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    String status = response.body().getResponse();
                    int code = response.body().getCode();
                    if (status.equals("Success")) {
                        startActivity(new Intent(ProfileActivity.this,ActivityOTP.class).putExtra("comingFrom","profile")
                        .putExtra("countryCode",countryCode)
                        .putExtra("phone",phone));

                    }
                    Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();

                    dialogProgress.dismiss();
                } else {
                    dialogProgress.dismiss();
                    Toast.makeText(ProfileActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VerifyOTPResponse> call, Throwable t) {
                dialogProgress.dismiss();
                Toast.makeText(ProfileActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateAgentProfileAction() {
        userCompanyName = edtCompanyName.getText().toString().trim();
        userFirstName = edtFullName.getText().toString().trim();
        userPhone = edtMainPhone.getText().toString().trim();
        userPhone2 = edtSecPhone.getText().toString().trim();
        countryCode = agentMainCCP.getSelectedCountryNameCode().toLowerCase();

        if (validateCompanyName()) {
            if (validateFullName()) {
                    if (validatePhone()) {
                        if (validateSecPhone()) {
                            updateAgentProfileDetails();
                        }
                        }


                    }
            }
        }


    private void searchList(String newText) {
        searchedLocatinInfo = new ArrayList<>();

        for (LocationInfo info : locationInfo){
            if(info.getCityName().toLowerCase().contains(newText.toLowerCase())){
                searchedLocatinInfo.add(info);
            }
        }
        if(searchedLocatinInfo.isEmpty()){

        }else {
            adapter.searchedLocationList(searchedLocatinInfo);
        }
    }
    private void getLocationList() {
        dialogProgress.show();
        ApiLinks.getClient().create(CityListingApi.class).post("" + SessionManager.getLanguageID(this))
                .enqueue(new Callback<CityListingResponse>() {
                    @Override
                    public void onResponse(Call<CityListingResponse> call, Response<CityListingResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getResponse();
                            if (message != null && message.equalsIgnoreCase("Success")) {
                                locationInfo.addAll(response.body().getData().getCityList());

                            } else
                                Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();


                            dialogProgress.dismiss();
                        } else {
                            Toast.makeText(ProfileActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                            dialogProgress.dismiss();
                        }
                        apiGetProfile();

                    }

                    @Override
                    public void onFailure(Call<CityListingResponse> call, Throwable t) {
                        Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        dialogProgress.dismiss();
                    }
                });
    }

    private void updateProfileAction() {
        userFirstName = edtName.getText().toString().trim();
        userLastName = edtLastName.getText().toString().trim();
        userEmail = edtEmail.getText().toString().trim();
        userPhone = edtPhone.getText().toString().trim();
        googleLocality = edtLocality.getText().toString().trim();
        googleCountryName = edtCountry.getText().toString().trim();
        googleStateName = edtState.getText().toString().trim();
        googleCityName = edtDistrict.getText().toString().trim();
        //Lav
        userCity = edtDistrict.getText().toString().trim();

        userZip = edtPinCode.getText().toString().trim();
        userAddress1 = edtAddress1.getText().toString().trim();
        userAddress2 = edtAddress2.getText().toString().trim();
        countryCode = countryCodePicker.getSelectedCountryNameCode().toLowerCase();

        if (rbMale.isChecked()) {
            userGender = "Male";
        } else if (rbFemale.isChecked()) {
            userGender = "Female";
        }

        if (validateFirstName()) {
            if (validateSecondName()) {
                if (isEmailValid()) {
                    if (validatePhone()) {
//                        if (validateAddress()) {
//                            if (selectLocality()) {
                               /* if (isImageRemove) {
                                    alertDeleteProfileImage();
                                } else {*/



                        updateProfileDetails();
                                // }
//                            }
//                        }
                    }
                }
            }
        }

    }


    //TODO Validation
    private boolean validateFirstName() {


        if (edtName.getText().toString().trim().equals("")) {
            edtName.clearFocus();
            edtName.requestFocus();
            Toast.makeText(ProfileActivity.this, getString(R.string.please_enter_first_name), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Utils.isValidName(userFirstName)) {
            edtName.clearFocus();
            edtName.requestFocus();
            Toast.makeText(ProfileActivity.this, getString(R.string.enter_valid_first_name),
                    Toast.LENGTH_SHORT).show();
            return false;

        } else return true;
    }

    private boolean validateSecondName() {

        if (edtLastName.getText().toString().trim().equals("")) {
            edtLastName.clearFocus();
            edtLastName.requestFocus();
            Toast.makeText(ProfileActivity.this, getString(R.string.please_enter_last_name), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Utils.isValidName(userLastName)) {
            edtLastName.clearFocus();
            edtLastName.requestFocus();
            Toast.makeText(ProfileActivity.this, getString(R.string.enter_valid_last_name),
                    Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }

    public boolean isEmailValid() {
        if (userEmail.equals("")) {
            /*Toast.makeText(ProfileActivity.this, getString(R.string.please_enter_email_address), Toast.LENGTH_SHORT).show();
            edtEmail.clearFocus();
            edtEmail.requestFocus();*/
            return true;
        } else {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = userEmail;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                return true;
            } else {
                Toast.makeText(ProfileActivity.this, getString(R.string.please_enter_valid_email_address), Toast.LENGTH_SHORT).show();
                edtEmail.clearFocus();
                edtEmail.clearFocus();
                edtEmail.requestFocus();
                return false;
               }
        }
    }

    private boolean validatePhone() {
        if (userPhone.equals("")) {
            Toast.makeText(ProfileActivity.this, (getString(R.string.please_enter_phone_number)), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!Utils.isValidMobile(userPhone)) {
                Toast.makeText(ProfileActivity.this, (getString(R.string.valid_phone_number)),
                        Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }

        }
    }
    public boolean validateSecPhone(){
        if(!userPhone2.isEmpty()){
            if (!Utils.isValidMobile(userPhone2)) {
                edtSecPhone.clearFocus();
                edtSecPhone.requestFocus();
                Toast.makeText(ProfileActivity.this,
                        getString(R.string.validate_secondary_no), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    public boolean validateMobile() {
        if (!countryCodePicker.getSelectedCountryCode().equals("964")) {
            countryCodePicker.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
                @Override
                public void onValidityChanged(boolean isValidNumber) {
                    isValid = isValidNumber;
                }
            });
        } else isValid = !Utils.isValidMobile(userPhone);

        return isValid;
    }

    private boolean validateAddress() {
        if (userAddress1.equalsIgnoreCase("") || userAddress1 == null) {
            Toast.makeText(ProfileActivity.this, getString(R.string.please_enter_address), Toast.LENGTH_SHORT).show();
            edtAddress1.clearFocus();
            edtAddress1.requestFocus();
            return false;
        } else {
            return true;
        }
    }
    private boolean validateCompanyName() {


        if (edtCompanyName.getText().toString().trim().equals("")) {
            edtCompanyName.clearFocus();
            edtCompanyName.requestFocus();
            Toast.makeText(ProfileActivity.this, getString(R.string.please_enter_company_name), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Utils.isValidName(userCompanyName)) {
            edtCompanyName.clearFocus();
            edtCompanyName.requestFocus();
            Toast.makeText(ProfileActivity.this, getString(R.string.validate_company_name),
                    Toast.LENGTH_SHORT).show();
            return false;

        } else return true;
    }
    private boolean validateFullName() {


        if (edtFullName.getText().toString().trim().equals("")) {
            edtFullName.clearFocus();
            edtFullName.requestFocus();
            Toast.makeText(ProfileActivity.this, getString(R.string.please_enter_fullname), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Utils.isValidName(userFirstName)) {
            edtFullName.clearFocus();
            edtFullName.requestFocus();
            Toast.makeText(ProfileActivity.this, getString(R.string.validate_name),
                    Toast.LENGTH_SHORT).show();
            return false;

        } else return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2) {
            if (grantResults[0] == 0) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                bottomSheetDialog.show();
                if (userProfileImage == null || userProfileImage.equals("")) {
                    ll_remove.setVisibility(View.GONE);
                } else {
                    ll_remove.setVisibility(View.VISIBLE);
                }
            } else {
                Toast.makeText(ProfileActivity.this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
            }

        }
    }

    //TODO Set Data
    private boolean selectLocality() {
        if (googleLocality.equals("")) {
            Toast.makeText(ProfileActivity.this, getString(R.string.select_location_details), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    //TODO API Calls
    public void apiGetProfile() {
        if (!isUpdated) {
            dialogProgress.show();
        }
        Log.d("userId",userID);
        ApiLinks.getClient().create(GetProfileApi.class).post(
                userID,
                "" + SessionManager.getLanguageID(this))
                .enqueue(new Callback<GetProfileResponse>() {
                    @Override
                    public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {

                        String message = response.body().getMessage();
                        if (response.body().getResponse().equalsIgnoreCase("Success")) {
                            GetProfileData data = response.body().getData();
                            imagePath = data.getImgPath();
                            imageCompanyLogoPath = data.getImgCompanyLogoPath();
                            profileDetails = data.getProfileDetails();
                            userTypeId = profileDetails.getUserTypeID();

                            if(userTypeId.equals(AppConstant.INDIVIDUAL_ID)){
                                individualLay.setVisibility(View.VISIBLE);
                                btnSubmit.setVisibility(View.VISIBLE);

                            }else {
                                agencyLay.setVisibility(View.VISIBLE);
                                btnSubmit.setVisibility(View.VISIBLE);


                            }
                            setProfileDetails();
                            if (isUpdated) {
                                Toast.makeText(ProfileActivity.this, successMessage, Toast.LENGTH_SHORT).show();
                                isUpdated = false;
                                successMessage = "";
                            }
                            dialogProgress.dismiss();
                        } else if (response.body().getCode() != null && response.body().getCode() == 409) {
                            Utils.showAlertLogout(ProfileActivity.this, message);
                        } else {
                            Log.e("Status", "Failed");
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }

    private void setProfileDetails() {
        userProfileImage = profileDetails.getUserProfilePicture();
        userEmail = profileDetails.getUserEmail();
        userCompanyName = profileDetails.getUserCompanyName();
        userFirstName = profileDetails.getUserFirstName();
        userLastName = profileDetails.getUserLastName();
        userPhone = profileDetails.getUserPhone();
        userPhone2 = profileDetails.getUserPhone2();
        userGender = profileDetails.getUserGender();
        userAddress1 = profileDetails.getUserAddress1();
        userAddress2 = profileDetails.getUserAddress2();
        googleStateName = profileDetails.getGoogleStateName();
        googleCityName = profileDetails.getGoogleCityName();
        userCity = profileDetails.getGoogleStateName();
        googleCountryName = profileDetails.getGoogleCountryName();
        googleLocality = profileDetails.getGoogleLocality();
        userZip = profileDetails.getUserZip();

        if(userTypeId.equals(AppConstant.INDIVIDUAL_ID)){

            edtLocality.setText(googleLocality);
            edtCountry.setText(googleCountryName);
            edtState.setText(googleStateName);
            edtDistrict.setText(userCity);
            edtPinCode.setText(userZip);
            edtName.setText(userFirstName);
            edtLastName.setText(userLastName);
            edtEmail.setText(userEmail);
            edtPhone.setText(userPhone);
            rbMale.setChecked("Male".equalsIgnoreCase(userGender));
            rbFemale.setChecked("Female".equals(userGender));
            edtAddress1.setText(userAddress1);
            edtAddress2.setText(userAddress2);
            Utils.loadUrlImage(ivProfile, imagePath + userProfileImage, R.drawable.icon_profile, true);
            SessionManager.setProfileImage(ProfileActivity.this, imagePath + userProfileImage);
            countryCodePicker.setCountryForNameCode(profileDetails.getCountryCode());
        }else {
            edtCompanyName.setText(userCompanyName);
            edtFullName.setText(userFirstName);
            edtMainPhone.setText(userPhone);
            edtSecPhone.setText(userPhone2);
            agentMainCCP.setCountryForNameCode(profileDetails.getCountryCode());
            agentSecondaryCCP.setCountryForNameCode(profileDetails.getCountryCode());
            userLocation = profileDetails.getUserLocation();

            Utils.loadUrlImage(ivProfile, imageCompanyLogoPath + profileDetails.getUserCompanyLogo(), R.drawable.icon_profile, true);
            SessionManager.setProfileImage(ProfileActivity.this, imageCompanyLogoPath + userProfileImage);
            for(LocationInfo locationInfo : locationInfo){
                if(profileDetails.getUserLocation().equalsIgnoreCase(locationInfo.getCityID())){
                    tvLocation.setText(locationInfo.getCityName());
                    break;
                }

            }

        }



    }

    public void updateProfileDetails() {
        dialogProgress.show();
        MultipartBody.Part body = null;
        if (!selectedFinalImageUrl.equals("")) {
            File file = new File(selectedFinalImageUrl);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("userProfilePicture", file.getName(), requestFile);
        }
        RequestBody requestBodyUserID = RequestBody.create(MediaType.parse("text/plain"), userID);
        RequestBody requestBodyUserFirstName = RequestBody.create(MediaType.parse("text/plain"), userFirstName);
        RequestBody requestBodyUserLastName = RequestBody.create(MediaType.parse("text/plain"), userLastName);
        RequestBody requestBodyUserGender = RequestBody.create(MediaType.parse("text/plain"), userGender);
        RequestBody requestBodyUserPhone = RequestBody.create(MediaType.parse("text/plain"), userPhone);
        RequestBody requestBodyGoogleCountryName = RequestBody.create(MediaType.parse("text/plain"), googleCountryName);
        RequestBody requestBodyGoogleStateName = RequestBody.create(MediaType.parse("text/plain"), userCity);
        RequestBody requestBodyGoogleCityName = RequestBody.create(MediaType.parse("text/plain"), googleCityName);
        RequestBody requestBodyGoogleLocality = RequestBody.create(MediaType.parse("text/plain"), googleLocality);
        RequestBody requestBodyCountryCode = RequestBody.create(MediaType.parse("text/plain"), countryCode);
        RequestBody requestBodyUserAddress1 = RequestBody.create(MediaType.parse("text/plain"), userAddress1);
        RequestBody requestBodyUserAddress2 = RequestBody.create(MediaType.parse("text/plain"), userAddress2);
        RequestBody requestBodyUserEmail = RequestBody.create(MediaType.parse("text/plain"), userEmail);
        RequestBody requestBodyUserZip = RequestBody.create(MediaType.parse("text/plain"), userZip);
        ApiLinks.getClient().create(UpdateProfileApi.class)
                .post(requestBodyUserID,
                        requestBodyUserFirstName,
                        requestBodyUserLastName,
                        requestBodyUserGender,
                        requestBodyUserPhone,
                        requestBodyGoogleCountryName,
                        requestBodyGoogleStateName,
                        requestBodyGoogleCityName,
                        requestBodyGoogleLocality,
                        requestBodyCountryCode,
                        requestBodyUserAddress1,
                        requestBodyUserAddress2,
                        requestBodyUserEmail,
                        requestBodyUserZip,
                        body, SessionManager.getLanguageID(this)).enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                if (response.isSuccessful()) {
                    dialogProgress.dismiss();
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        //TODO update profile picture
                        String userProfile = response.body().getUserProfileImagePath();
                        if (!isImageRemove) {
                            userProfileImage = response.body().getUserProfileImagePath();
                        } else {
                            isImageRemove = false;
                        }
                        SessionManager.setProfileImage(ProfileActivity.this, userProfile);
                        SessionManager.setFullName(ProfileActivity.this,userFirstName,userLastName);
                        SessionManager.setMobileNumber(ProfileActivity.this, userPhone);
                        SessionManager.setCountryCode(ProfileActivity.this, countryCode);
                        Log.e("countryCode", countryCode);


                        alertSuccess(true, getString(R.string.your_profile_updated));
                    } else {
                        alertSuccess(false, getString(R.string.failed_update_profile));
                    }
                    scrollView.scrollTo(0, 0);
                    scrollView.pageScroll(View.FOCUS_UP);
                    scrollView.smoothScrollTo(0, 0);
                } else {
                    dialogProgress.dismiss();
                    Toast.makeText(ProfileActivity.this, getString(R.string.failed_update), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                dialogProgress.dismiss();
                Toast.makeText(ProfileActivity.this, getString(R.string.failed_update), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updateAgentProfileDetails(){

        dialogProgress.show();
        MultipartBody.Part body = null;
        if (!selectedFinalImageUrl.equals("")) {
            File file = new File(selectedFinalImageUrl);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("userCompanyLogo", file.getName(), requestFile);
        }
        RequestBody requestBodyUserID = RequestBody.create(MediaType.parse("text/plain"), userID);
        RequestBody requestBodyCompanyName = RequestBody.create(MediaType.parse("text/plain"), userCompanyName);
        RequestBody requestBodyUserFirstName = RequestBody.create(MediaType.parse("text/plain"), userFirstName);
        RequestBody requestBodyUserPhone = RequestBody.create(MediaType.parse("text/plain"), userPhone);
        RequestBody requestBodyUserPhone2 = RequestBody.create(MediaType.parse("text/plain"), userPhone2);
        RequestBody requestBodyCountryCode = RequestBody.create(MediaType.parse("text/plain"),countryCode);
        RequestBody requestBodyUserLocation = RequestBody.create(MediaType.parse("text/plain"), userLocation);

        ApiLinks.getClient().create(UpdateAgentProfileApi.class)
                .post(requestBodyUserID,
                        requestBodyCompanyName,
                        requestBodyUserFirstName,
                        requestBodyUserPhone,
                        requestBodyUserPhone2,
                        requestBodyUserLocation,
                        requestBodyCountryCode,
                        body, SessionManager.getLanguageID(this)).enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                if (response.isSuccessful()) {
                    dialogProgress.dismiss();
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        //TODO update profile picture
                        String userProfile = response.body().getUserProfileImagePath();
                        if (!isImageRemove) {
                            userProfileImage = response.body().getUserProfileImagePath();
                        } else {
                            isImageRemove = false;
                        }
                        SessionManager.setProfileImage(ProfileActivity.this, userProfile);
                        SessionManager.setFullName(ProfileActivity.this,userFirstName,"");
                        SessionManager.setMobileNumber(ProfileActivity.this, userPhone);
                        SessionManager.setCountryCode(ProfileActivity.this, countryCode);
                        Log.e("countryCode", countryCode);


                        alertSuccess(true, getString(R.string.your_profile_updated));
                    } else {
                        alertSuccess(false, getString(R.string.failed_update_profile));
                    }
                    scrollView.scrollTo(0, 0);
                    scrollView.pageScroll(View.FOCUS_UP);
                    scrollView.smoothScrollTo(0, 0);
                } else {
                    dialogProgress.dismiss();
                    Toast.makeText(ProfileActivity.this, getString(R.string.failed_update), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                dialogProgress.dismiss();
                Toast.makeText(ProfileActivity.this, getString(R.string.failed_update), Toast.LENGTH_SHORT).show();
            }
        });

    }

    //TODO Alert Message
    private void alertSuccess(boolean isSuccess, String successMessage) {
        alertDialog = new Dialog(ProfileActivity.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_success_popup_profile, null, false);
        ImageView close = alert_layout.findViewById(R.id.close_success_dialog);
        ImageView icon = alert_layout.findViewById(R.id.alertTitle);
        TextView title = alert_layout.findViewById(R.id.title_alert_success_popup);
        title.setVisibility(View.GONE);
        TextView message = alert_layout.findViewById(R.id.message_alert_success_popup);
        if (isSuccess) {
            icon.setImageResource(R.drawable.icon_success);
        } else {
            icon.setImageResource(R.drawable.icon_warning);
        }
        message.setText(successMessage);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtPhone.setCursorVisible(false);
                alertDialog.dismiss();
                finish();
            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void alertDeleteProfileImage() {
        alertDialog = new Dialog(ProfileActivity.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") final View alert_layout =
                factory.inflate(R.layout.alert_delete_video_popup, null);
        LinearLayout cancel = alert_layout.findViewById(R.id.CancelPopUp);
        TextView title = alert_layout.findViewById(R.id.alertTitle);
        TextView ok = alert_layout.findViewById(R.id.alert_ok_text);
        title.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_alert, 0, 0);
        title.setText(getString(R.string.delete_this_image));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                alertDialog.dismiss();
                isImageRemove = false;
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFinalImageUrl = "";
                userProfileImage = "";
                selectedImageUrl = "";
                ivProfile.setImageResource(R.drawable.icon_profile);
                alertDialog.dismiss();
                //updateProfileDetails();
            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

    }

    @SuppressLint("SetTextI18n")
    private void alertExit() {
        final Dialog alertDialog = new Dialog(ProfileActivity.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") final View alert_layout =
                factory.inflate(R.layout.alert_delete_video_popup, null);
        LinearLayout cancel = alert_layout.findViewById(R.id.CancelPopUp);
        TextView title = alert_layout.findViewById(R.id.alertTitle);
        TextView ok = alert_layout.findViewById(R.id.alert_ok_text);
        title.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_alert, 0, 0);
        title.setText(R.string.exit_without_update);

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
                finish();
                //updateProfileDetails();
            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

    }


    //TODO Main Function
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
        //  super.onBackPressed();
        if (isImageRemove)
            alertExit();
        else finish();
    }


    //TODO Set Data
    private void getLocationIntent() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                edtLocalityInfo.setVisibility(View.GONE);
                tvLocalityInfo.setVisibility(View.VISIBLE);
                Place place = Autocomplete.getPlaceFromIntent(data);
                getDataFromPlacesApi(place);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                edtLocality.setEnabled(true);
                edtCountry.setEnabled(true);
                edtState.setEnabled(true);
                edtDistrict.setEnabled(true);
                edtPinCode.setEnabled(true);
                edtLocalityInfo.setVisibility(View.VISIBLE);
                tvLocalityInfo.setVisibility(View.GONE);
                edtLocalityInfo.setText("");
                edtLocality.setText("");
                edtState.setText("");
                edtDistrict.setText("");
                edtPinCode.setText("");
                Toast.makeText(ProfileActivity.this, getString(R.string.find_location_details), Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Log.e("onActivityResult", "Result cancelled");
            }
        } else if (requestCode == ID_GALLERY) {
            if (!selectedImageUrl.equals("")) {
                bt = BitmapFactory.decodeFile(selectedImageUrl);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bt.compress(Bitmap.CompressFormat.PNG, 100, stream);
                selectedFinalImageUrl = "";
                Intent in = new Intent(ProfileActivity.this, ProfileImageCropActivity.class);
                in.putExtra("whichScreen","profile");
                startActivityForResult(in, ID_CROP);

            }
        } else if (requestCode == ID_CROP) {
            if (!selectedFinalImageUrl.equals("")) {
                Utils.loadUrlImage(ivProfile, selectedFinalImageUrl, R.drawable.icon_profile, true);
            }
        }
    }

    public void getDataFromPlacesApi(Place place) {
        String locality = place.getName();
        Double latitude = place.getLatLng().latitude;
        Double longitude = place.getLatLng().longitude;
        tvLocalityInfo.setText(locality);
        try {
            ArrayList<Address> addressList = (ArrayList<Address>) geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList.size() > 0) {
                Address address = addressList.get(0);
                edtLocality.setText(address.getLocality());
                edtCountry.setText(address.getCountryName());
                //  edtState.setText(address.getAdminArea());
                edtDistrict.setText(address.getSubAdminArea());
                edtState.setText(address.getSubAdminArea());
                edtPinCode.setText(address.getPostalCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.col_profile_parent_layout));
        if (SessionManager.getAccessToken(this) == null
                || SessionManager.getAccessToken(this).equals(""))
            finish();
    }
}
