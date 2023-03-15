package com.paya.paragon.activity.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.paya.paragon.R;
import com.paya.paragon.activity.dashboard.ActivityMyProperties;
import com.paya.paragon.activity.dashboard.ProfileImageBrowseActivity;
import com.paya.paragon.activity.dashboard.ProfileImageCropActivity;
import com.paya.paragon.adapter.AdapterCityListing;
import com.paya.paragon.api.SocialMeadiaLogin.SocialMediaData;
import com.paya.paragon.api.SocialMeadiaLogin.SocialMediaLoginApi;
import com.paya.paragon.api.SocialMeadiaLogin.SocialMediaResponse;
import com.paya.paragon.api.agentSignUp.AgentSignUpApi;
import com.paya.paragon.api.cityList.CityListingApi;
import com.paya.paragon.api.cityList.CityListingResponse;
import com.paya.paragon.api.index.LocationInfo;
import com.paya.paragon.api.userSignUp.UserSignUpApi;
import com.paya.paragon.api.userSignUp.UserSignUpData;
import com.paya.paragon.api.userSignUp.UserSignUpResponse;
import com.paya.paragon.classes.CustomSpinner;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.CircularImageView;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.AppConstant;
import com.paya.paragon.utilities.CountryCode.CountryCodePicker;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.PasswordValidation;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings({"HardCodedStringLiteral", "RedundantCast"})
public class ActivitySignUp extends AppCompatActivity {

    public int RC_SIGN_IN = 10, OTP_VERIFY = 1001;
    TextView selectorUserType, submitSignUp, login,aglogin,tvLocation,submitAgentSignup;
    ImageView facebookLogin, googleLogin;
    CircularImageView ivCompanyLogo;
    EditText etFirstName, etLastName, etPhone, etEmail, etPassword, etConfirmPassword,etCompanyName,etAgentFullName,etMainPhoneNo,etSecondaryPhoneNo,etAgPassword,etAgConfirmPassword;
    CheckBox cbNewsletter, cbTermsCondition,cbAgNewsletter,cbAgTermsCondition;
    ImageView btnAddLogo;
    String strUserTypeId = "",strFirstName = "", strLastName = "", selectedUserType = "", strEmail = "", strCountryCode = "",strCompanyName="",strFullName="",strMPNo="",strSPNo="",strMPCC="",strSPCC="",strCountryCodewithPlus="",strMPCCP="";
    String strPhone = "", strPassword = "", strCfPassword = "", strNewsletterStatus = "",strAgPassword=" ",strAgCfPassword= " ",strAgNewsLetterStatus=" ",strLocation="";
    DialogProgress progress;
    CountryCodePicker countryCodePicker,MPCountryCodePicker,SPCountryCodePicker;
    String sName, sEmail;
    GoogleSignInClient mGoogleSignInClient;
    NestedScrollView nestedScroll,nestedAgentScroll;
    CallbackManager callbackManager;
    LoginButton loginButton;
    SocialMediaData socialUserData;
    UserSignUpData userSignUpData;
    CustomSpinner spinnerUserType;
    ArrayList<String> userTypeList;
    ArrayAdapter<String> adapterUserType;
    String comingFrom;
    Toolbar toolbar;
    boolean isValid;
    DialogProgress mLoading;

    LinearLayout selectUserTypeLay;
    RadioGroup rgSelectUserType;
    RelativeLayout parentLay;
    LinearLayout individualLay,agencyLay;
    private ArrayList<LocationInfo> locationInfo;
    private ArrayList<LocationInfo> searchedLocatinInfo;

    AdapterCityListing adapter;

    static final int ID_GALLERY = 1;
    static final int ID_CROP = 2;


    public static String selectedImageUrl = "";
    public static String selectedFinalImageUrl = "";
    public static Bitmap bt;
    String checkBoxText = "";
    SpannableString text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sign_up);
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.rl_sign_up_parent_layout));

        /*Toolbar Setup*/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        mTitle.setText(R.string.register);

        comingFrom = getIntent().getStringExtra("comingFrom");
        layoutdecider();
        checkBoxText = getString(R.string.i_agree_to_the) + " "+"<a href='" + ApiLinks.BASE_URL_TERMS_AND_CONDITIONS + "' >"
                + getString(R.string.terms_and_conditions) + "</a>"+" & "+"<a href='" + ApiLinks.BASE_URL_PRIVACY_POLICY + "' >"
                + getString(R.string.privacy_policy) + "</a>" + getString(R.string.text_of) + getString(R.string.app_name);


        text = new SpannableString(getString(R.string.already_have_an_account_log_in));
        int length = text.length();
        ClickableSpan resendClick = new ClickableSpan() {
            @Override

            public void onClick(View view) {
                finish();
            }

        };

        text.setSpan(resendClick, length - 5, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_color_search)), length - 5, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        mLoading = new DialogProgress(ActivitySignUp.this);
        locationInfo = new ArrayList<>();
        getCityList();




    }

    public void layoutdecider() {
        parentLay = findViewById(R.id.rl_sign_up_parent_layout);
        selectUserTypeLay = findViewById(R.id.select_user_type_lay);
        rgSelectUserType = findViewById(R.id.radio_group_select_user_type);
        individualLay = findViewById(R.id.individual_lay);
        agencyLay = findViewById(R.id.agency_lay);
        rgSelectUserType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.radio_button_individual:
                        strUserTypeId = AppConstant.INDIVIDUAL_ID;

                        individualLay.setVisibility(View.VISIBLE);
                        agencyLay.setVisibility(View.GONE);
                        individualuserlayoutcall();

                        break;
                    case R.id.radio_button_agency:
                        strUserTypeId = AppConstant.AGENCY_ID;

                        agencyLay.setVisibility(View.VISIBLE);
                        individualLay.setVisibility(View.GONE);
                        agencylayoutcall();


                        break;

                }

            }
        });
    }
    public void getCityList() {

        mLoading.show();
        ApiLinks.getClient().create(CityListingApi.class).post("" + SessionManager.getLanguageID(this))
                .enqueue(new Callback<CityListingResponse>() {
                    @Override
                    public void onResponse(Call<CityListingResponse> call, Response<CityListingResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getResponse();
                            if (message != null && message.equalsIgnoreCase("Success")) {
                                locationInfo.addAll(response.body().getData().getCityList());

                            } else
                                Toast.makeText(ActivitySignUp.this, message, Toast.LENGTH_SHORT).show();


                            mLoading.dismiss();
                        } else {
                            Toast.makeText(ActivitySignUp.this, "No Response", Toast.LENGTH_SHORT).show();
                            mLoading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<CityListingResponse> call, Throwable t) {
                        Toast.makeText(ActivitySignUp.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        mLoading.dismiss();
                    }
                });
    }
    public void individualuserlayoutcall() {
        declarations();

        sName = getIntent().getStringExtra("name");
        sEmail = getIntent().getStringExtra("email");
        etFirstName.setText(sName);
        etEmail.setText(sEmail);
        if (!sName.equals("")) {
            String lastName = sName.substring(sName.lastIndexOf(" ") + 1);
            String firstName = sName.substring(0, sName.length() - lastName.length());
            etFirstName.setText(firstName);
            etLastName.setText(lastName);
        }

        /*Login Button*/
        submitSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strCountryCode = countryCodePicker.getSelectedCountryNameCode().toLowerCase();

                strCountryCodewithPlus = countryCodePicker.getSelectedCountryCodeWithPlus();
                if (checkValidation()) {
                    getUserSignUp();

                }
            }
        });

        selectorUserType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerUserType.performClick();
            }
        });

        spinnerUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedUserType = spinnerUserType.getAdapter().getItem(position).toString();
                selectorUserType.setText(selectedUserType);
              /*  if (position != 0
              ) {

                } else {
                    selectorUserType.setText("");
                    selectedUserType = "";
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectorUserType.setText(R.string.select_user_type);
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


        login.setText(text, TextView.BufferType.SPANNABLE);
        login.setMovementMethod(LinkMovementMethod.getInstance());

        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.performClick();
            }
        });
        //fb login
        callbackManager = CallbackManager.Factory.create();
        /*  List<String> permissionNeeds = Arrays.asList("user_photos", "email",
                "user_birthday", "public_profile");*/
        List<String> permissionNeeds = Arrays.asList("email",
                "public_profile");
        loginButton.setReadPermissions(permissionNeeds);
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            sName = object.getString("name");
                                            sEmail = object.getString("email");
                                            String id = object.getString("id");
                                            String lastName = sName.substring(sName.lastIndexOf(" ") + 1);
                                            String firstName = sName.substring(0, sName.length() - lastName.length());
                                            socialMediaLogin(sEmail, id, firstName, lastName, "facebook", "", "1");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.e("Exception", e + "");
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("onError");
                    }
                });
    }
    public void agencylayoutcall() {

        agencyDeclarations();



    }

    private void agencyDeclarations() {
        progress = new DialogProgress(ActivitySignUp.this);

        etCompanyName = findViewById(R.id.et_agency_company_name);
        etAgentFullName = findViewById(R.id.et_agency_full_name);
        etMainPhoneNo = findViewById(R.id.et_phone_agency_sign_up);
        etSecondaryPhoneNo = findViewById(R.id.et_phone_agency_sign_up_spn);
        etAgPassword = findViewById(R.id.et_password_agency_sign_up);
        etAgConfirmPassword = findViewById(R.id.et_confirm_password_agency_sign_up);
        tvLocation = findViewById(R.id.text_location_list);
        MPCountryCodePicker = findViewById(R.id.country_code_agency_sign_up);
        SPCountryCodePicker = findViewById(R.id.country_code_agency_sign_up_spn);

//        MPCountryCodePicker.setCountryForNameCode(GlobalValues.countryCode);
        MPCountryCodePicker.registerCarrierNumberEditText(etMainPhoneNo);

//        SPCountryCodePicker.setCountryForNameCode(GlobalValues.countryCode);
        SPCountryCodePicker.registerCarrierNumberEditText(etSecondaryPhoneNo);

        cbAgNewsletter = findViewById(R.id.checkBox_subscribe_newsletter_agency_sign_up);
        cbAgTermsCondition = findViewById(R.id.checkBox_subscribe_privacy_agency_sign_up);

        aglogin = findViewById(R.id.button_login_agency_sign_up);

        submitAgentSignup = findViewById(R.id.button_submit_agency_sign_up);
        nestedAgentScroll = findViewById(R.id.nested_scroll_agent);

        final Dialog dialog = new Dialog(this);
        @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.show_price_list_selector, null);
        final SearchView searchView = view.findViewById(R.id.sv_list_search);
        searchView.setVisibility(View.VISIBLE);
        final RecyclerView recyclerMinPrice = (RecyclerView) view.findViewById(R.id.recycler_price_list);
        recyclerMinPrice.setLayoutManager(new LinearLayoutManager(ActivitySignUp.this));
        adapter = new AdapterCityListing(locationInfo, ActivitySignUp.this, SessionManager.getLocationId(this));
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
                strLocation = locationInfo.getCityID();

                dialog.dismiss();
            }
        });

        ivCompanyLogo = findViewById(R.id.iv_company_logo);
        ivCompanyLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryIntent();

            }
        });

        btnAddLogo = findViewById(R.id.iv_add_logo);
        btnAddLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                galleryIntent();
            }
        });



        cbAgTermsCondition.setText(Html.fromHtml(checkBoxText));
        cbAgTermsCondition.setMovementMethod(LinkMovementMethod.getInstance());

        aglogin.setText(text, TextView.BufferType.SPANNABLE);
        aglogin.setMovementMethod(LinkMovementMethod.getInstance());

        submitAgentSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strMPCC = MPCountryCodePicker.getSelectedCountryNameCode().toLowerCase();
                strSPCC = SPCountryCodePicker.getSelectedCountryNameCode().toLowerCase();
                strMPCCP = MPCountryCodePicker.getSelectedCountryCodeWithPlus();

                if(checkAgentValidation()){
                    getAgentSignup();
                }
            }
        });
    }

    private void galleryIntent() {
                if (ContextCompat.checkSelfPermission(ActivitySignUp.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ActivitySignUp.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                } else {
                    selectedImageUrl = "";

                    Intent in = new Intent(ActivitySignUp.this, ProfileImageBrowseActivity.class);
                    in.putExtra("whichScreen","signup");
                    startActivityForResult(in, ID_GALLERY);
                }
    }

    private void getAgentSignup() {

        progress.show();
        MultipartBody.Part body = null;
        if (!selectedFinalImageUrl.equals("")) {
            File file = new File(selectedFinalImageUrl);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("userCompanyLogo", file.getName(), requestFile);
        }
        RequestBody requestBodyUserTypeID = RequestBody.create(MediaType.parse("text/plain"), strUserTypeId);
        RequestBody requestBodyUserStatus = RequestBody.create(MediaType.parse("text/plain"), "Active");
        RequestBody requestBodyCompanyName = RequestBody.create(MediaType.parse("text/plain"), strCompanyName);
        RequestBody requestBodyFullName = RequestBody.create(MediaType.parse("text/plain"), strFullName);
        RequestBody requestBodyMainPhone = RequestBody.create(MediaType.parse("text/plain"), strMPNo);
        RequestBody requestBodySecondaryPhone = RequestBody.create(MediaType.parse("text/plain"), strSPNo);
        RequestBody requestBodyCCMainPhone = RequestBody.create(MediaType.parse("text/plain"), strMPCC);
        RequestBody requestBodyPassword = RequestBody.create(MediaType.parse("text/plain"), strAgPassword);
        RequestBody requestBodyLocation = RequestBody.create(MediaType.parse("text/plain"), strLocation);
        RequestBody requestBodyNewsLetterSubscribe = RequestBody.create(MediaType.parse("text/plain"), strAgNewsLetterStatus);
        RequestBody requestBodyLanguageId = RequestBody.create(MediaType.parse("text/plain"), SessionManager.getLanguageID(this));
        RequestBody requestBodyFcmToken = RequestBody.create(MediaType.parse("text/plain"), SessionManager.getDeviceTokenForFCM(this));
        RequestBody requestBodyEnableOTP = RequestBody.create(MediaType.parse("text/plain"),"Yes");
        RequestBody requestBodyCountryCode = RequestBody.create(MediaType.parse("text/plain"),strMPCCP);



        Log.d("Agency","UserType: "+strUserTypeId+" CompanyName: "+strCompanyName+" Name: "+strFullName+" Main Phone: "+strMPNo+" MainCC: "+strMPCC+" SecondPhone: "+strSPNo
                +" strLocation:  "+strLocation+" CompanyLogo: "+selectedFinalImageUrl);



        ApiLinks.getClient().create(AgentSignUpApi.class).post(
                requestBodyUserTypeID,
                requestBodyUserStatus,
                requestBodyCompanyName,
                requestBodyFullName,
                requestBodyMainPhone,
                requestBodySecondaryPhone,
                requestBodyCCMainPhone,
                requestBodyPassword,
                requestBodyLocation,
                requestBodyNewsLetterSubscribe,
                requestBodyLanguageId,
                requestBodyFcmToken,
                requestBodyEnableOTP,
                requestBodyCountryCode,
                body).enqueue(new Callback<UserSignUpResponse>() {
            @Override
            public void onResponse(Call<UserSignUpResponse> call, Response<UserSignUpResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = response.body().getMessage();
                    String status = response.body().getResponse();
                    if (status.equalsIgnoreCase("Success")) {
                        userSignUpData = response.body().getData();
                        String OTP = userSignUpData.getOTP();
                        if (OTP.equalsIgnoreCase("true")) {
                            Toast.makeText(ActivitySignUp.this, getResources().getString(R.string.otp_success),
                                    Toast.LENGTH_SHORT).show();
                            saveUserLogin();

                            startActivity(new Intent(ActivitySignUp.this, ActivityOTP.class).putExtra("comingFrom",comingFrom));


                        } else {
                            Toast.makeText(ActivitySignUp.this, message, Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(ActivitySignUp.this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivitySignUp.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<UserSignUpResponse> call, Throwable t) {
                Toast.makeText(ActivitySignUp.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });


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

    //TODO API Calls
    public void socialMediaLogin(String email, String id, String firstName, String lastName,
                                 String loginType, String gender, String languageID) {
        progress.show();
        ApiLinks.getClient().create(SocialMediaLoginApi.class).post(email, id, firstName, lastName, loginType, gender, languageID)
                .enqueue(new Callback<SocialMediaResponse>() {
                    @Override
                    public void onResponse(Call<SocialMediaResponse> call, Response<SocialMediaResponse> response) {
                        if (response.isSuccessful()) {
                            progress.dismiss();
                            if (response.body() != null) {
                                String message = response.body().getMessage();
                                int code = response.body().getCode();
                                if (code == 100) {
                                    socialUserData = response.body().getData();
                                    String imageURL = response.body().getImagePath();
                                    socialUserData.setUserProfilePicture(imageURL + socialUserData.getUserProfilePicture());
                                    saveUserLoginSocial();
                                } else {
                                    Toast.makeText(ActivitySignUp.this, message, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            progress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<SocialMediaResponse> call, Throwable t) {
                        progress.dismiss();
                    }
                });
    }

    private void getUserSignUp() {


        progress.show();
        ApiLinks.getClient().create(UserSignUpApi.class).post(
                strUserTypeId,
                "Active",
                selectedUserType,
                strFirstName,
                strLastName,
                strPhone,
                countryCodePicker.getSelectedCountryNameCode().toLowerCase(),
                strEmail,
                strPassword,
                GlobalValues.countryID,
                strNewsletterStatus,
                "state", SessionManager.getLanguageID(this),
                SessionManager.getDeviceTokenForFCM(this),"Yes",countryCodePicker.getSelectedCountryCodeWithPlus())
                .enqueue(new Callback<UserSignUpResponse>() {
                    @Override
                    public void onResponse(Call<UserSignUpResponse> call, Response<UserSignUpResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            String message = response.body().getMessage();
                            String status = response.body().getResponse();
                            if (status.equalsIgnoreCase("Success")) {
                                userSignUpData = response.body().getData();
                                String OTP = userSignUpData.getOTP();
                                if (OTP.equalsIgnoreCase("true")) {


                                    Toast.makeText(ActivitySignUp.this, getResources().getString(R.string.otp_success),
                                            Toast.LENGTH_SHORT).show();
                                    saveUserLogin();

                                    startActivity(new Intent(ActivitySignUp.this, ActivityOTP.class).putExtra("comingFrom",comingFrom));

                                } else {
                                    Toast.makeText(ActivitySignUp.this, message, Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(ActivitySignUp.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ActivitySignUp.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                        progress.dismiss();
                    }

                    @Override
                    public void onFailure(Call<UserSignUpResponse> call, Throwable t) {
                        Toast.makeText(ActivitySignUp.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                });
    }


    //TODO Set Data
    private void declarations() {
        sName = "";
        sEmail = "";
        loginButton = findViewById(R.id.login_button);
        nestedScroll = findViewById(R.id.nested_scroll);
        selectorUserType = (TextView) findViewById(R.id.text_selector_user_type);
        etFirstName = (EditText) findViewById(R.id.editText_user_first_name);
        etLastName = (EditText) findViewById(R.id.editText_user_last_name);
        etPhone = (EditText) findViewById(R.id.editText_phone_user_sign_up);
        etEmail = (EditText) findViewById(R.id.editText_user_email_address);
        etPassword = (EditText) findViewById(R.id.editText_password_sign_up);
        etConfirmPassword = (EditText) findViewById(R.id.editText_confirm_password_sign_up);
        submitSignUp = (TextView) findViewById(R.id.button_submit_sign_up);
        login = (TextView) findViewById(R.id.button_login_sign_up);
        facebookLogin = (ImageView) findViewById(R.id.facebook_login_sign_up);
        googleLogin = (ImageView) findViewById(R.id.google_login_sign_up);
        cbNewsletter = (CheckBox) findViewById(R.id.checkBox_subscribe_newsletter_sign_up);
        cbTermsCondition = (CheckBox) findViewById(R.id.checkBox_subscribe_privacy_sign_up);
        progress = new DialogProgress(ActivitySignUp.this);
        spinnerUserType = (CustomSpinner) findViewById(R.id.spinner_user_type_layout_sing_up);
        countryCodePicker = (CountryCodePicker) findViewById(R.id.country_code_user_sign_up);
//        countryCodePicker.setCountryForNameCode(GlobalValues.countryCode);
        countryCodePicker.registerCarrierNumberEditText(etPhone);


        userTypeList = new ArrayList<>();
        userTypeList.addAll(Arrays.asList(getResources().getStringArray(R.array.userTypeList)));
        adapterUserType = new ArrayAdapter<>(ActivitySignUp.this, android.R.layout.simple_list_item_1,
                userTypeList);
        spinnerUserType.setAdapter(adapterUserType);
        String langName = SessionManager.getLanguageName(this);



        cbTermsCondition.setText(Html.fromHtml(checkBoxText));
        cbTermsCondition.setMovementMethod(LinkMovementMethod.getInstance());


    }

    private void saveUserLoginSocial() {
        SessionManager.saveLogin(ActivitySignUp.this, socialUserData.getUserFirstname(), socialUserData.getUserLastname(),
                socialUserData.getUserID(), socialUserData.getAcessToken(), socialUserData.getUserEmail(), socialUserData.getUserPhone(),
                socialUserData.getCountryCode(), socialUserData.getUserTypeID(), socialUserData.getUserProfilePicture(), true);
        SessionManager.setNewsLetterStatus(ActivitySignUp.this, "Yes");
        SessionManager.setSavedSearchStatus(ActivitySignUp.this, "Yes");
        if (ActivityLoginEmail.activityLogin != null) {
            ActivityLoginEmail.activityLogin.finish();
        }

        if (comingFrom != null)
            if (comingFrom.equalsIgnoreCase("menu"))
                startActivity(new Intent(ActivitySignUp.this, ActivityMyProperties.class));
                finish();
    }

    private void saveUserLogin() {
        if(strUserTypeId.equalsIgnoreCase(AppConstant.AGENCY_ID)){
            SessionManager.saveAgentLogin(ActivitySignUp.this, strCompanyName, strFullName,
                    userSignUpData.getUserID(), userSignUpData.getAcessToken(),
                    strMPNo, strCountryCode, strUserTypeId, selectedFinalImageUrl, false);

            SessionManager.setCountryCodeWithPlus(ActivitySignUp.this,strMPCCP);
        }
        else {
            SessionManager.saveLogin(ActivitySignUp.this, strFirstName, strLastName,
                    userSignUpData.getUserID(), userSignUpData.getAcessToken(),
                    strEmail, strPhone, strCountryCode, strUserTypeId, "", false);

            SessionManager.setCountryCodeWithPlus(ActivitySignUp.this,strCountryCodewithPlus);

        }


        SessionManager.setNewsLetterStatus(ActivitySignUp.this, strNewsletterStatus);
        SessionManager.setSavedSearchStatus(ActivitySignUp.this, strNewsletterStatus);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ID_GALLERY){
            if (!selectedImageUrl.equals("")) {
                bt = BitmapFactory.decodeFile(selectedImageUrl);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bt.compress(Bitmap.CompressFormat.PNG, 100, stream);
                selectedFinalImageUrl = "";
                Intent in = new Intent(ActivitySignUp.this, ProfileImageCropActivity.class);
                in.putExtra("whichScreen","signup");
                startActivityForResult(in, ID_CROP);

            }
        }
        else if (requestCode == ID_CROP) {
            if (!selectedFinalImageUrl.equals("")) {
                Utils.loadUrlImage(ivCompanyLogo, selectedFinalImageUrl, R.drawable.icon_profile, true);
            }
        }
        else if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
       /*  else if (requestCode == OTP_VERIFY) {


             if(resultCode == RESULT_OK){
                 redirectHomePage();
             }else if(requestCode == RESULT_CANCELED){


             }
        }*/

    }



    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            sName = account.getDisplayName();
            sEmail = account.getEmail();
            String id = account.getId();
            String lastName = sName.substring(sName.lastIndexOf(" ") + 1);
            String firstName = sName.substring(0, sName.length() - lastName.length());
            socialMediaLogin(sEmail, id, firstName, lastName, "gplus", "", "1");
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            Toast.makeText(ActivitySignUp.this, e + "", Toast.LENGTH_SHORT).show();
            Log.e("Exception", e + "");
        }
    }

    private boolean checkValidation() {
        if (etFirstName.getText().toString().trim().equals("")) {
            etFirstName.clearFocus();
            etFirstName.requestFocus();
            nestedScroll.scrollTo(0, (int) toolbar.getY());
            Toast.makeText(ActivitySignUp.this, getString(R.string.please_enter_first_name), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            strFirstName = etFirstName.getText().toString();
            if (!Utils.isValidName(strFirstName)) {
                etFirstName.clearFocus();
                etFirstName.requestFocus();
                nestedScroll.scrollTo(0, (int) toolbar.getY());
                Toast.makeText(ActivitySignUp.this, getString(R.string.enter_valid_first_name),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (etLastName.getText().toString().trim().equals("")) {
            etLastName.clearFocus();
            etLastName.requestFocus();
            nestedScroll.scrollTo(0, (int) etFirstName.getY());
            Toast.makeText(ActivitySignUp.this, getString(R.string.please_enter_last_name), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            strLastName = etLastName.getText().toString();
            if (!Utils.isValidName(strLastName)) {
                etLastName.clearFocus();
                etLastName.requestFocus();
                nestedScroll.scrollTo(0, (int) etFirstName.getY());
                Toast.makeText(ActivitySignUp.this, getString(R.string.enter_valid_last_name),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (etEmail.getText() != null && etEmail.getText().toString().trim().length() > 0) {
            strEmail = etEmail.getText().toString();
        }
        /*if (etEmail.getText().toString().equals("")) {
            etEmail.clearFocus();
            etEmail.requestFocus();
            nestedScroll.scrollTo(0, (int) etLastName.getY());
            Toast.makeText(ActivitySignUp.this, getString(R.string.please_enter_email_address), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            strEmail = etEmail.getText().toString();
            if (TextUtils.isEmpty(strEmail) || !Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                etEmail.clearFocus();
                etEmail.requestFocus();
                nestedScroll.scrollTo(0, (int) etLastName.getY());
                Toast.makeText(ActivitySignUp.this, getString(R.string.please_enter_valid_email_address), Toast.LENGTH_SHORT).show();
                return false;
            }
        }*/
        strPhone = etPhone.getText().toString().trim();
        if (etPhone.getText().toString().equals("")) {
            etPhone.clearFocus();
            etPhone.requestFocus();
            nestedScroll.scrollTo(0, (int) etEmail.getY());
            Toast.makeText(ActivitySignUp.this, getString(R.string.please_enter_phone_number), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            strPhone = etPhone.getText().toString().trim();
            if (!Utils.isValidMobile(strPhone)) {
                etPhone.clearFocus();
                etPhone.requestFocus();
                nestedScroll.scrollTo(0, (int) etEmail.getY());
                Toast.makeText(ActivitySignUp.this,
                        getString(R.string.valid_phone_number), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (etPassword.getText().toString().trim().equals("")) {
            etPassword.clearFocus();
            etPassword.requestFocus();
            Toast.makeText(ActivitySignUp.this, getString(R.string.enter_your_password), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            strPassword = etPassword.getText().toString();
            ArrayList<PasswordValidation> arrayList = Utils.validatePassword(strPassword);
            if (strPassword.contains(" ")) {
                Toast.makeText(ActivitySignUp.this,
                        getString(R.string.password_without_space), Toast.LENGTH_SHORT).show();
                return false;
            }
            if (arrayList.get(0) != PasswordValidation.PASSWORD_OK) {
                String msg = getResources().getString(R.string.password_validation_characters);
                Toast.makeText(ActivitySignUp.this, msg, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (etConfirmPassword.getText().toString().trim().equals("")) {
            etConfirmPassword.clearFocus();
            etConfirmPassword.requestFocus();
            Toast.makeText(ActivitySignUp.this, getString(R.string.confirm_your_password), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            strCfPassword = etConfirmPassword.getText().toString();
        }
        if (!strPassword.equals(strCfPassword)) {
            Toast.makeText(ActivitySignUp.this, getString(R.string.password_mismatch), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!cbTermsCondition.isChecked()) {
            Toast.makeText(ActivitySignUp.this, getString(R.string.You_must_agree_terms), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (cbNewsletter.isChecked()) {
            strNewsletterStatus = "Yes";
        } else {
            strNewsletterStatus = "No";
        }
        return true;
    }

    private boolean checkAgentValidation() {
        if (etCompanyName.getText().toString().trim().equals("")) {
            etCompanyName.clearFocus();
            etCompanyName.requestFocus();
            nestedAgentScroll.scrollTo(0, (int) toolbar.getY());
            Toast.makeText(ActivitySignUp.this, getString(R.string.please_enter_company_name), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            strCompanyName = etCompanyName.getText().toString();
            if (!Utils.isValidName(strCompanyName)) {
                etCompanyName.clearFocus();
                etCompanyName.requestFocus();
                nestedAgentScroll.scrollTo(0, (int) toolbar.getY());
                Toast.makeText(ActivitySignUp.this, getString(R.string.validate_company_name),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (etAgentFullName.getText().toString().trim().equals("")) {
            etAgentFullName.clearFocus();
            etAgentFullName.requestFocus();
            nestedAgentScroll.scrollTo(0, (int) etCompanyName.getY());
            Toast.makeText(ActivitySignUp.this, getString(R.string.please_enter_fullname), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            strFullName = etAgentFullName.getText().toString();
            if (!Utils.isValidName(strFullName)) {
                etAgentFullName.clearFocus();
                etAgentFullName.requestFocus();
                nestedAgentScroll.scrollTo(0, (int) etCompanyName.getY());
                Toast.makeText(ActivitySignUp.this, getString(R.string.validate_name),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        strMPNo = etMainPhoneNo.getText().toString().trim();
        if (etMainPhoneNo.getText().toString().equals("")) {
            etMainPhoneNo.clearFocus();
            etMainPhoneNo.requestFocus();
            nestedAgentScroll.scrollTo(0, (int) etAgentFullName.getY());
            Toast.makeText(ActivitySignUp.this, getString(R.string.please_enter_phone_number), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            strMPNo = etMainPhoneNo.getText().toString().trim();
            if (!Utils.isValidMobile(strMPNo)) {
                etMainPhoneNo.clearFocus();
                etMainPhoneNo.requestFocus();
                nestedAgentScroll.scrollTo(0, (int) etAgentFullName.getY());
                Toast.makeText(ActivitySignUp.this,
                        getString(R.string.valid_phone_number), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        strSPNo = etSecondaryPhoneNo.getText().toString().trim();
        if(!strSPNo.isEmpty()){
            if (!Utils.isValidMobile(strSPNo)) {
                etSecondaryPhoneNo.clearFocus();
                etSecondaryPhoneNo.requestFocus();
                nestedAgentScroll.scrollTo(0, (int) etMainPhoneNo.getY());
                Toast.makeText(ActivitySignUp.this,
                        getString(R.string.validate_secondary_no), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (etAgPassword.getText().toString().trim().equals("")) {
            etAgPassword.clearFocus();
            etAgPassword.requestFocus();
            Toast.makeText(ActivitySignUp.this, getString(R.string.enter_your_password), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            strAgPassword = etAgPassword.getText().toString();
            ArrayList<PasswordValidation> arrayList = Utils.validatePassword(strAgPassword);
            if (strAgPassword.contains(" ")) {
                Toast.makeText(ActivitySignUp.this,
                        getString(R.string.password_without_space), Toast.LENGTH_SHORT).show();
                return false;
            }
            if (arrayList.get(0) != PasswordValidation.PASSWORD_OK) {
                String msg = getResources().getString(R.string.password_validation_characters);
                Toast.makeText(ActivitySignUp.this, msg, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (etAgConfirmPassword.getText().toString().trim().equals("")) {
            etAgConfirmPassword.clearFocus();
            etAgConfirmPassword.requestFocus();
            Toast.makeText(ActivitySignUp.this, getString(R.string.confirm_your_password), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            strAgCfPassword = etAgConfirmPassword.getText().toString();
        }
        if (!strAgPassword.equals(strAgCfPassword)) {
            Toast.makeText(ActivitySignUp.this, getString(R.string.password_mismatch), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!cbAgTermsCondition.isChecked()) {
            Toast.makeText(ActivitySignUp.this, getString(R.string.You_must_agree_terms), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (cbAgNewsletter.isChecked()) {
            strAgNewsLetterStatus = "Yes";
        } else {
            strAgNewsLetterStatus = "No";
        }

        if(tvLocation.getText().toString().trim().equals(getString(R.string.select))){

            Toast.makeText(ActivitySignUp.this,
                    "Please select a location", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.rl_filter_parent_layout));
        LoginManager.getInstance().logOut();
        sName = "";
        sEmail = "";
    }


    private boolean validatePhone() {
        if (etPhone.getText().toString().trim().equals("")) {
            Toast.makeText(ActivitySignUp.this, getString(R.string.please_enter_phone_number), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!validateMobile()) {
                Toast.makeText(ActivitySignUp.this, getString(R.string.valid_mobile_number),
                        Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }

        }
    }

    public boolean validateMobile() {
        if (!countryCodePicker.getSelectedCountryCode().equals("964")) {
            countryCodePicker.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
                @Override
                public void onValidityChanged(boolean isValidNumber) {
                    isValid = isValidNumber;
                }
            });
        } else isValid = !Utils.isValidMobile(strPhone);

        return isValid;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 2){
            if (grantResults[0] == 0) {

                selectedImageUrl = "";

                Intent in = new Intent(ActivitySignUp.this, ProfileImageBrowseActivity.class);
                in.putExtra("whichScreen","signup");
                startActivityForResult(in, ID_GALLERY);
            }
            else {

                Toast.makeText(ActivitySignUp.this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();

            }

        }
    }


}
