package com.paya.paragon.activity.dashboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.paya.paragon.R;
import com.paya.paragon.activity.ActivityHome;
import com.paya.paragon.activity.postProperty.PostPropertyPage01Activity;
import com.paya.paragon.activity.postRequirements.ActivityRequirementPurpose;
import com.paya.paragon.adapter.AdapterMyQuestions;
import com.paya.paragon.adapter.AdapterQuestionCategoryList;
import com.paya.paragon.api.askQuestion.AskQuestionApi;
import com.paya.paragon.api.askQuestion.AskQuestionResponse;
import com.paya.paragon.api.myQuestions.MyQuestionsApi;
import com.paya.paragon.api.myQuestions.MyQuestionsData;
import com.paya.paragon.api.myQuestions.MyQuestionsResponse;
import com.paya.paragon.api.questionCategoryList.CategoriesList;
import com.paya.paragon.api.questionCategoryList.QuestionCategoryApi;
import com.paya.paragon.api.questionCategoryList.QuestionCategoryResponse;
import com.paya.paragon.classes.CustomSpinner;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("HardCodedStringLiteral")
public class ActivityMyQuestions extends AppCompatActivity implements View.OnClickListener {

    static ArrayList<MyQuestionsData> myQuestionsList;
    AdapterMyQuestions adapterMyQuestions;
    RecyclerView recyclerMyQuestions;
    DialogProgress mLoading;

    EditText editAskQuestion;
    TextView submitQuestion;
    CustomSpinner spinnerCategory;
    LinearLayout layoutSubmit;
    String myQuestion;

    ImageView mProfileImage;
    public DrawerLayout drawer;
    NavigationView navigationView;
    ImageView mDrawerProfileImage;
    TextView textSavedSearches, textShortlisted, textPostedRequirements, textAgents;
    TextView textEnquiriesOffers, textMyQuestions, textViewingRequest, textUnifiedTenancy, textOpenHouse;
    TextView textPropertyEnquiry, textSettings, textLogout, textName, textEmail;
    TextView btnPostFreeAd, btnPostRequirement, textHome, noRecords, textSubscriptions;
    LinearLayout profileLayout;
    public Dialog alertDialog;

    CategoriesList categoriesList;
    private ArrayList<CategoriesList> mNameList;
    AdapterQuestionCategoryList adapterQuestionCategoryList;
    TextView tvNoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_questions);
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.drawer_layout_my_questions));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(16);
        mTitle.setText(R.string.my_questions);
        mProfileImage = toolbar.findViewById(R.id.profile_image_my_account);
        declarations();

        if (!Utils.NoInternetConnection(ActivityMyQuestions.this)) {
            mLoading.show();
            getMyQuestionsListing();
        } else {
            Utils.NoInternetConnection(ActivityMyQuestions.this);
        }

        editAskQuestion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    layoutSubmit.setVisibility(View.VISIBLE);
                    Utils.expand(layoutSubmit, 200);
                    requestFocus(editAskQuestion);
                } else {
                    InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext()
                            .getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(editAskQuestion.getWindowToken(), 0);
                    Utils.collapse(layoutSubmit, 200);
                    editAskQuestion.clearFocus();
                }
            }
        });

        submitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myQuestion = editAskQuestion.getText().toString().trim();
                if (!myQuestion.isEmpty()) {
                    if (spinnerCategory.getSelectedItemPosition() == 0 && categoriesList == null) {
                        Toast.makeText(ActivityMyQuestions.this, getString(R.string.select_category), Toast.LENGTH_SHORT).show();
                    } else {
                        closeKeyboard();
                        mLoading.show();
                        Utils.collapse(layoutSubmit, 200);
                        editAskQuestion.setText("");
                        editAskQuestion.setFocusableInTouchMode(false);
                        editAskQuestion.setFocusable(false);
                        editAskQuestion.setFocusableInTouchMode(true);
                        editAskQuestion.setFocusable(true);
                        postQuestion();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.please_write_your_question), Toast.LENGTH_LONG).show();
                }
            }
        });

        recyclerMyQuestions.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (editAskQuestion.hasFocus()) {
                    editAskQuestion.clearFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                }
                return false;
            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    categoriesList = mNameList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    //TODO API Calls
    private void getMyQuestionsListing() {
        ApiLinks.getClient().create(MyQuestionsApi.class).post(
                SessionManager.getAccessToken(ActivityMyQuestions.this),
                SessionManager.getLanguageID(this))
                .enqueue(new Callback<MyQuestionsResponse>() {
                    @Override
                    public void onResponse(Call<MyQuestionsResponse> call, Response<MyQuestionsResponse> response) {
                        if (response.isSuccessful()) {
                            String responseText = response.body().getResponse();
                            String message = response.body().getMessage();
                            int code = response.body().getCode();
                            if (code == 4004 && responseText.equals("Success")) {
                                tvNoItem.setVisibility(View.GONE);
                                myQuestionsList = new ArrayList<>();
                                myQuestionsList = response.body().getData();
                                String myQuestionProfileImageUrl = response.body().getImgUrl();
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityMyQuestions.this);
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                linearLayoutManager.setAutoMeasureEnabled(true);
                                linearLayoutManager.setStackFromEnd(false);
                                recyclerMyQuestions.setLayoutManager(linearLayoutManager);
                                adapterMyQuestions = new AdapterMyQuestions(ActivityMyQuestions.this, myQuestionsList, myQuestionProfileImageUrl);
                                recyclerMyQuestions.setAdapter(adapterMyQuestions);
                                mLoading.hide();
                            } else if (response.body().getCode() != null && response.body().getCode() == 409) {
                                Utils.showAlertLogout(ActivityMyQuestions.this, message);
                            } else {
                                mLoading.hide();
                                tvNoItem.setVisibility(View.VISIBLE);
                            }
                        } else {
                            mLoading.hide();
                            Toast.makeText(ActivityMyQuestions.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                        recyclerMyQuestions.scrollToPosition(0);
                    }

                    @Override
                    public void onFailure(Call<MyQuestionsResponse> call, Throwable t) {
                        mLoading.hide();
                        Toast.makeText(ActivityMyQuestions.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
        getQuestionCategoryList();
    }

    private void getQuestionCategoryList() {
        mLoading.show();
        ApiLinks.getClient().create(QuestionCategoryApi.class).post()
                .enqueue(new Callback<QuestionCategoryResponse>() {
                    @Override
                    public void onResponse(Call<QuestionCategoryResponse> call, Response<QuestionCategoryResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getResponse();
                            if (message.equalsIgnoreCase("Success")) {
                                mNameList = new ArrayList<>();
                                CategoriesList categoriesList = new CategoriesList();
                                categoriesList.setCategoryName(getString(R.string.select_a_category));
                                mNameList.add(categoriesList);
                                mNameList.addAll(response.body().getData().getCategoriesList());
                                if (mNameList.size() > 0) {

                                    adapterQuestionCategoryList = new AdapterQuestionCategoryList(ActivityMyQuestions.this, mNameList);
                                    spinnerCategory.setAdapter(adapterQuestionCategoryList);
                                }
                            } else
                                Toast.makeText(ActivityMyQuestions.this, message, Toast.LENGTH_SHORT).show();


                        }
                        mLoading.hide();
                    }

                    @Override
                    public void onFailure(Call<QuestionCategoryResponse> call, Throwable t) {
                        mLoading.hide();
                        Toast.makeText(ActivityMyQuestions.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void postQuestion() {
        ApiLinks.getClient().create(AskQuestionApi.class).post(
                GlobalValues.countryID, "dashbrd", SessionManager.getAccessToken(ActivityMyQuestions.this),
                categoriesList.getCategoryID(), myQuestion)
                .enqueue(new Callback<AskQuestionResponse>() {
                    @Override
                    public void onResponse(Call<AskQuestionResponse> call, Response<AskQuestionResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getResponse();
                            int code = response.body().getCode();
                            if (code == 4004) {
                                //Toast.makeText(ActivityMyQuestions.this, message, Toast.LENGTH_SHORT).show();
                                getMyQuestionsListing();
                            } else {
                                Toast.makeText(ActivityMyQuestions.this, message, Toast.LENGTH_SHORT).show();
                                mLoading.hide();
                            }
                        } else {
                            mLoading.hide();
                            Toast.makeText(ActivityMyQuestions.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                        recyclerMyQuestions.scrollToPosition(0);
                    }

                    @Override
                    public void onFailure(Call<AskQuestionResponse> call, Throwable t) {
                        mLoading.hide();
                        Toast.makeText(ActivityMyQuestions.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //TODO Set Data
    private void declarations() {


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_my_questions);
        navigationView = (NavigationView) findViewById(R.id.nav_view_my_properties);
        tvNoItem = findViewById(R.id.tv_no_item);
        tvNoItem.setVisibility(View.GONE);

        textShortlisted = navigationView.findViewById(R.id.text_shortlisted_dashboard_drawer);
        textName = navigationView.findViewById(R.id.user_name_dashboard_drawer);
        textEmail = navigationView.findViewById(R.id.user_email_dashboard_drawer);
        btnPostFreeAd = navigationView.findViewById(R.id.text_sell_post_ad_dashboard_drawer);
        btnPostRequirement = navigationView.findViewById(R.id.post_requirement_dashboard_drawer);
        textSavedSearches = navigationView.findViewById(R.id.text_saved_search_dashboard_drawer);
        textPostedRequirements = navigationView.findViewById(R.id.text_posted_requirements_dashboard_drawer);
        textAgents = navigationView.findViewById(R.id.text_agents_dashboard_drawer);
        textPropertyEnquiry = navigationView.findViewById(R.id.text_property_enquiry_dashboard_drawer);
        textSettings = navigationView.findViewById(R.id.text_settings_dashboard_drawer);
        textHome = navigationView.findViewById(R.id.text_home_dashboard_drawer);
        textLogout = navigationView.findViewById(R.id.text_logout_dashboard_drawer);
        mDrawerProfileImage = navigationView.findViewById(R.id.profile_image_dashboard_drawer);
        profileLayout = navigationView.findViewById(R.id.layout_view_profile_dashboard_drawer);
        textSubscriptions = navigationView.findViewById(R.id.text_my_subscriptions_dashboard_drawer);


        textMyQuestions = navigationView.findViewById(R.id.text_my_questions_dashboard_drawer);
        textViewingRequest = navigationView.findViewById(R.id.text_viewing_request_dashboard_drawer);
        textEnquiriesOffers = navigationView.findViewById(R.id.text_enquiries_offers_dashboard_drawer);
        textUnifiedTenancy = navigationView.findViewById(R.id.text_unified_dashboard_drawer);
        textOpenHouse = navigationView.findViewById(R.id.text_open_house_dashboard_drawer);

        textShortlisted.setOnClickListener(this);
        btnPostFreeAd.setOnClickListener(this);
        btnPostRequirement.setOnClickListener(this);
        textSavedSearches.setOnClickListener(this);
        textPostedRequirements.setOnClickListener(this);
        textAgents.setOnClickListener(this);
        textPropertyEnquiry.setOnClickListener(this);
        textSettings.setOnClickListener(this);
        textLogout.setOnClickListener(this);
        profileLayout.setOnClickListener(this);
        textHome.setOnClickListener(this);
        textSubscriptions.setOnClickListener(this);
        mProfileImage.setOnClickListener(this);

        textEnquiriesOffers.setOnClickListener(this);
        textMyQuestions.setOnClickListener(this);
        textViewingRequest.setOnClickListener(this);
        textUnifiedTenancy.setOnClickListener(this);
        textOpenHouse.setOnClickListener(this);
        recyclerMyQuestions = (RecyclerView) findViewById(R.id.recycler_my_questions);
        mLoading = new DialogProgress(this);
        editAskQuestion = (EditText) findViewById(R.id.editText_ask_question_my_questions);
        spinnerCategory = (CustomSpinner) findViewById(R.id.spinner_category_my_questions);
        submitQuestion = (TextView) findViewById(R.id.text_submit_my_questions);
        layoutSubmit = (LinearLayout) findViewById(R.id.layout_submit_question_my_questions);
        mLoading.hide();
        layoutSubmit.setVisibility(View.GONE);
        myQuestionsList = new ArrayList<>();

        setProfileImage();
    }

    public void setProfileImage() {
        if (mProfileImage != null && mDrawerProfileImage != null &&
                textName != null && textEmail != null) {
            textName.setText(SessionManager.getFullName(this));
            textEmail.setText(SessionManager.getEmail(this));

            String url = SessionManager.getProfileImage(this);
            if (url != null && !url.equals("")) {
                Utils.loadUrlImage(mProfileImage, url, R.drawable.icon_profile, true);
                Utils.loadUrlImage(mDrawerProfileImage, url, R.drawable.icon_profile, true);
            }
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    public void onClick(View view) {
        if (view == mProfileImage) {
            drawer.openDrawer(GravityCompat.END);

        }
        if (view == profileLayout) {
            toggleDrawer();
            Intent in = new Intent(ActivityMyQuestions.this, ProfileActivity.class);
            startActivity(in);

        } else if (view == btnPostFreeAd) {
            toggleDrawer();
            Intent intent = new Intent(ActivityMyQuestions.this, PostPropertyPage01Activity.class);
            startActivity(intent);

        } else if (view == btnPostRequirement) {
            toggleDrawer();
            Intent intent = new Intent(ActivityMyQuestions.this, ActivityRequirementPurpose.class);
            startActivity(intent);

        } else if (view == textSavedSearches) {
            toggleDrawer();
            Intent intent = new Intent(ActivityMyQuestions.this, ActivitySavedSearchList.class);
            startActivity(intent);

        } else if (view == textShortlisted) {
            toggleDrawer();
            Intent intent = new Intent(ActivityMyQuestions.this, ActivityShortlisted.class);
            startActivity(intent);

        } else if (view == textPostedRequirements) {
            toggleDrawer();
            Intent intent = new Intent(ActivityMyQuestions.this, ActivityPostedRequirements.class);
            startActivity(intent);

        } else if (view == textAgents) {
            toggleDrawer();
            Intent intent = new Intent(ActivityMyQuestions.this, ActivityMyAgents.class);
            startActivity(intent);

        } else if (view == textPropertyEnquiry) {
            toggleDrawer();
            Intent intent = new Intent(ActivityMyQuestions.this, ActivityMyProperties.class);
            startActivity(intent);

        } else if (view == textSettings) {
            toggleDrawer();
            Intent intent = new Intent(ActivityMyQuestions.this, ActivitySettings.class);
            startActivity(intent);

        } else if (view == textSubscriptions) {
            toggleDrawer();
            Intent intent = new Intent(ActivityMyQuestions.this, ActivityMySubscriptions.class);
            startActivity(intent);

        } else if (view == textEnquiriesOffers) {
            toggleDrawer();
            Intent intent = new Intent(ActivityMyQuestions.this, ActivityEnquiriesOffers.class);
            startActivity(intent);

        } else if (view == textViewingRequest) {
            toggleDrawer();
            Intent intent = new Intent(ActivityMyQuestions.this, ActivityViewingRequests.class);
            startActivity(intent);

        } else if (view == textUnifiedTenancy) {
            toggleDrawer();
            Intent intent = new Intent(ActivityMyQuestions.this, ActivityUnifiedTenancyContract.class);
            startActivity(intent);

        } else if (view == textOpenHouse) {
            toggleDrawer();
            Intent intent = new Intent(ActivityMyQuestions.this, ActivityOpenHouse.class);
            startActivity(intent);

        } else if (view == textMyQuestions) {
            toggleDrawer();


        } else if (view == textLogout) {
            toggleDrawer();
            alertLogout();

        } else if (view == textHome) {
            toggleDrawer();
            Intent intent = new Intent(ActivityMyQuestions.this, ActivityHome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }

    }

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
        Intent intent = new Intent(ActivityMyQuestions.this, ActivityHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void alertLogout() {
        alertDialog = new Dialog(this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_exit_popup, null);
        TextView ok = alert_layout.findViewById(R.id.alert_ok_button);
        TextView cancel = alert_layout.findViewById(R.id.alert_cancel_button);

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
                SessionManager.logout(ActivityMyQuestions.this);
                Intent intent = new Intent(ActivityMyQuestions.this, ActivityHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    public void toggleDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START); //CLOSE Nav Drawer!
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END); //CLOSE Nav Drawer!
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SessionManager.getAccessToken(this) == null
                || SessionManager.getAccessToken(this).equals(""))
            finish();
    }
}
