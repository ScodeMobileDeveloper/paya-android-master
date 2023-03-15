package com.paya.paragon.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.paya.paragon.PropertyProjectListActivity;
import com.paya.paragon.R;
import com.paya.paragon.activity.dashboard.ActivityMyProperties;
import com.paya.paragon.activity.postProperty.PostPropertyPage01Activity;
import com.paya.paragon.api.SocialMeadiaLogin.SocialMediaData;
import com.paya.paragon.api.SocialMeadiaLogin.SocialMediaLoginApi;
import com.paya.paragon.api.SocialMeadiaLogin.SocialMediaResponse;
import com.paya.paragon.api.checkUserLogin.UserLoginApi;
import com.paya.paragon.api.checkUserLogin.UserLoginData;
import com.paya.paragon.api.checkUserLogin.UserLoginResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.AppConstant;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"HardCodedStringLiteral", "RedundantCast", "NullableProblems", "ConstantConditions"})
public class ActivityLoginEmail extends AppCompatActivity {

    ImageView loginFacebook, loginGoogle;
    ImageView close;
    TextView submit, forgotPassword;
    LinearLayout signUp;
    String strMobileNo = "", strPassword = "";
    EditText etMobileNo, etPassword;
    DialogProgress dialogProgress;
    LoginButton loginButton;

    UserLoginData userData;
    SocialMediaData socialUserData;

    GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 10;
    String sName, sEmail;
    CallbackManager callbackManager;
    public static Activity activityLogin;
    String comingFrom,strUserTypeId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_email);

        declarations();
        comingFrom = getIntent().getStringExtra("comingFrom");
        initiateFaceBookCallBack();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.NoInternetConnection(ActivityLoginEmail.this)) {
                    if (etMobileNo.getText().toString().trim().equals("")) {
                        Toast.makeText(ActivityLoginEmail.this, getString(R.string.your_phone), Toast.LENGTH_SHORT).show();

                    } else if (etPassword.getText().toString().trim().equals("")) {
                        Toast.makeText(ActivityLoginEmail.this, getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
                    } else {

                        strMobileNo = etMobileNo.getText().toString();
                        strPassword = etPassword.getText().toString();
                        if(!Utils.isValidMobile(strMobileNo)){
                            Toast.makeText(ActivityLoginEmail.this,
                                    getString(R.string.valid_phone_number), Toast.LENGTH_SHORT).show();

                        }else {
                            dialogProgress.show();
                            getUserEmailLogin();

                        }

                    }
                } else {
                    Toast.makeText(ActivityLoginEmail.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityLoginEmail.this, ActivityForgotPassword.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ActivityLoginEmail.this, ActivitySignUp.class);
                in.putExtra("email", sEmail);
                in.putExtra("name", sName);
                if (comingFrom != null)
                    if (comingFrom.equalsIgnoreCase("menu"))
                        in.putExtra("comingFrom", "menu");
                startActivity(in);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        loginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        loginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "email";
                String publicProfile = "public_profile";
                loginButton.setPermissions(Arrays.asList(email, publicProfile));
                loginButton.performClick();
                //LoginManager.getInstance().logInWithReadPermissions(ActivityLoginEmail.this, Arrays.asList(email, publicProfile));
            }
        });
    }

    private void initiateFaceBookCallBack() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        dialogProgress.show();
                        final AccessToken accessToken = loginResult.getAccessToken();
                        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(final JSONObject object, GraphResponse response) {
                                try {
                                    sName = object.getString("name");
                                    sEmail = object.optString("email", "");
                                    String id = object.optString("id", "");
                                    String lastName = sName.substring(sName.lastIndexOf(" ") + 1);
                                    String firstName = sName.substring(0, sName.length() - lastName.length());
                                    socialMediaLogin(sEmail, id, firstName, lastName, "facebook", "", "1");
                                } catch (JSONException e) {
                                    FirebaseCrashlytics.getInstance().recordException(e);
                                    e.printStackTrace();
                                    dismissLoaderDialog();
                                }
                            }

                        });

                        Bundle bundle = new Bundle();
                        bundle.putString("fields", "id, name, email, gender, birthday");
                        graphRequest.setParameters(bundle);
                        graphRequest.executeAsync();
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

    private void dismissLoaderDialog() {
        if (dialogProgress != null && dialogProgress.isShowing()) {
            dialogProgress.dismiss();
        }
    }

    //TODO API Calls
    public void getUserEmailLogin() {
        ApiLinks.getClient().create(UserLoginApi.class).post(
                strMobileNo, strPassword, SessionManager.getLanguageID(this), SessionManager.getDeviceTokenForFCM(this))
                .enqueue(new Callback<UserLoginResponse>() {
                    @Override
                    public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            if (response.body().getResponse().equalsIgnoreCase("Success")) {
                                userData = response.body().getData();
                                String imageURL = response.body().getImagePath();
                                strUserTypeId=userData.getUserTypeID();
                                if(strUserTypeId.equalsIgnoreCase(AppConstant.INDIVIDUAL_ID)){
                                    userData.setUserProfilePicture(imageURL + userData.getUserProfilePicture());

                                }else {


                                }
                                saveUserLogin();

                            } else {
                                Toast.makeText(ActivityLoginEmail.this, getString(R.string.invalid_username_password), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ActivityLoginEmail.this, getString(R.string.invalid_username_password), Toast.LENGTH_SHORT).show();
                        }
                        dialogProgress.dismiss();
                    }

                    @Override
                    public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                        Toast.makeText(ActivityLoginEmail.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        dialogProgress.dismiss();
                    }
                });
    }


    //TODO Set Data
    private void saveUserLogin() {
        if(strUserTypeId.equalsIgnoreCase(AppConstant.AGENCY_ID)){
            SessionManager.saveAgentLogin(ActivityLoginEmail.this, "", userData.getUserFirstname(),
                    userData.getUserID(), userData.getAcessToken(),
                    userData.getUserPhone(), userData.getCountryCode(), strUserTypeId, "", true);

        }
        else {
            SessionManager.saveLogin(ActivityLoginEmail.this, userData.getUserFirstname(), userData.getUserLastname(),
                    userData.getUserID(), userData.getAcessToken(), userData.getUserEmail(), userData.getUserPhone(),
                    userData.getCountryCode(), userData.getUserTypeID(), userData.getUserProfilePicture(), true);
        }

        SessionManager.setNewsLetterStatus(ActivityLoginEmail.this, "Yes");
        SessionManager.setSavedSearchStatus(ActivityLoginEmail.this, "Yes");
        Toast.makeText(ActivityLoginEmail.this, getString(R.string.welcome)+" "+SessionManager.getFullName(this), Toast.LENGTH_SHORT).show();

        if (comingFrom != null)
            startActivity(new Intent(ActivityLoginEmail.this, AppConstant.FROM_POST_PROPERTY.equalsIgnoreCase(comingFrom) ? PostPropertyPage01Activity.class : ActivityMyProperties.class));
        finish();
    }

    private void declarations() {
        activityLogin = ActivityLoginEmail.this;
        sName = "";
        sEmail = "";
        loginButton = findViewById(R.id.login_button);
        loginFacebook = (ImageView) findViewById(R.id.facebook_sign_up_login_email);
        loginGoogle = (ImageView) findViewById(R.id.google_sign_up_login_email);
        dialogProgress = new DialogProgress(ActivityLoginEmail.this);
        etMobileNo = (EditText) findViewById(R.id.edit_login_mobile_no);
        etPassword = (EditText) findViewById(R.id.edit_login_password);
        userData = new UserLoginData();
        submit = (TextView) findViewById(R.id.button_submit_email_login);
        forgotPassword = (TextView) findViewById(R.id.button_forgot_password_login);
        signUp = (LinearLayout) findViewById(R.id.button_sign_up_login_email);
        close = (ImageView) findViewById(R.id.close_login_email);

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
        if(comingFrom!=null &&comingFrom.equalsIgnoreCase("menu")){
            startActivity(new Intent(this, PropertyProjectListActivity.class));
            finish();
        }
       else
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.rl_login_parent_layout));
        LoginManager.getInstance().logOut();
        sName = "";
        sEmail = "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            dialogProgress.show();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String id = account.getId();
            sName = account.getDisplayName();
            sEmail = account.getEmail();
            Log.e("Gmail Details", id + " " + account.getPhotoUrl());
            String lastName = sName.substring(sName.lastIndexOf(" ") + 1);
            String firstName = sName.substring(0, sName.length() - lastName.length());
            socialMediaLogin(sEmail, id, firstName, lastName, "gplus", "", "1");
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            Toast.makeText(ActivityLoginEmail.this, e + "", Toast.LENGTH_SHORT).show();
            Log.e("Exception", e + "");
        }
    }

    public void socialMediaLogin(String email, String id, String firstName, String lastName, String loginType, String gender, String languageID) {
        ApiLinks.getClient().create(SocialMediaLoginApi.class).post(email, id, firstName, lastName, loginType, gender, languageID)
                .enqueue(new Callback<SocialMediaResponse>() {
                    @Override
                    public void onResponse(Call<SocialMediaResponse> call, Response<SocialMediaResponse> response) {
                        if (response.isSuccessful()) {
                            dialogProgress.dismiss();
                            String message = response.body().getMessage();
                            int code = response.body().getCode();
                            if (code == 100) {
                                socialUserData = response.body().getData();
                                String imageURL = response.body().getImagePath();
                                socialUserData.setUserProfilePicture(imageURL + socialUserData.getUserProfilePicture());
                                saveUserLoginSocial();
                            } else {
                                Toast.makeText(ActivityLoginEmail.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            dialogProgress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<SocialMediaResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                    }
                });
    }

    private void saveUserLoginSocial() {
        SessionManager.saveLogin(ActivityLoginEmail.this, socialUserData.getUserFirstname(), socialUserData.getUserLastname(),
                socialUserData.getUserID(), socialUserData.getAcessToken(), socialUserData.getUserEmail(), socialUserData.getUserPhone(),
                socialUserData.getCountryCode(), socialUserData.getUserTypeID(), socialUserData.getUserProfilePicture(), true);
        SessionManager.setNewsLetterStatus(ActivityLoginEmail.this, "Yes");
        SessionManager.setSavedSearchStatus(ActivityLoginEmail.this, "Yes");
        if (comingFrom != null)
            startActivity(new Intent(ActivityLoginEmail.this, AppConstant.FROM_POST_PROPERTY.equalsIgnoreCase(comingFrom) ? PostPropertyPage01Activity.class : ActivityMyProperties.class));
        finish();
    }
}
