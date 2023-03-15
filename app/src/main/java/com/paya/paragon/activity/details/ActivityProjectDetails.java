package com.paya.paragon.activity.details;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.paya.paragon.R;
import com.paya.paragon.activity.GalleryListActivity;
import com.paya.paragon.activity.buy.ProjectListFragment;
import com.paya.paragon.activity.login.ActivityLoginEmail;
import com.paya.paragon.activity.postProperty.PostPropertyPage01Activity;
import com.paya.paragon.adapter.AdapterAmenities;
import com.paya.paragon.adapter.AdapterBankPartners;
import com.paya.paragon.adapter.AdapterPropertyListing;
import com.paya.paragon.adapter.AdapterPropertySpecification;
import com.paya.paragon.adapter.AdapterSlider;
import com.paya.paragon.adapter.ProjectSimilarAdapter;
import com.paya.paragon.api.FetchLocalInformationData.LocalInformationData;
import com.paya.paragon.api.FetchLocalInformationData.LocalInformationDataApi;
import com.paya.paragon.api.FetchLocalInformationData.LocalInformationDataResponse;
import com.paya.paragon.api.addFavProject.AddFavProjectApi;
import com.paya.paragon.api.addFavProject.AddFavProjectResponse;
import com.paya.paragon.api.contactAgent.ContactAgentResponse;
import com.paya.paragon.api.contactAgent.ContactProjectApi;
import com.paya.paragon.api.localInformationList.LocalInformationListApi;
import com.paya.paragon.api.localInformationList.LocalInformationListData;
import com.paya.paragon.api.localInformationList.LocalInformationListResponse;
import com.paya.paragon.api.projectDetails.FloorPlans;
import com.paya.paragon.api.projectDetails.ImageCategoryData;
import com.paya.paragon.api.projectDetails.ProjectDetails1Api;
import com.paya.paragon.api.projectDetails.ProjectDetails1Model;
import com.paya.paragon.api.projectDetails.ProjectDetails1Response;
import com.paya.paragon.api.projectDetails.ProjectImageInfo;
import com.paya.paragon.api.projectDetails.ProjectVideoInfo;
import com.paya.paragon.api.projectDetails.Units;
import com.paya.paragon.api.propertyDetails.AmenitiesModel;
import com.paya.paragon.api.propertyDetails.CallPropertyOwnerApi;
import com.paya.paragon.api.propertyDetails.CallPropertyOwnerResponse;
import com.paya.paragon.api.propertyDetails.SpecificationModel;
import com.paya.paragon.api.propertyDetails.SuggestedPropertyDetails;
import com.paya.paragon.classes.ReadMoreTextView;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.fragments.FragmentUnitDetails;
import com.paya.paragon.model.ProjectModel;
import com.paya.paragon.utilities.CountryCode.CountryCodePicker;
import com.paya.paragon.utilities.CustomPager;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.ExtendedFragment;
import com.paya.paragon.utilities.ExtendedViewPager;
import com.paya.paragon.utilities.FragmentImageView;
import com.paya.paragon.utilities.ListDialogBox;
import com.paya.paragon.utilities.PropertyProjectType;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;
import com.paya.paragon.utilities.WorkaroundMapFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"HardCodedStringLiteral", "RedundantCast", "deprecation"})
public class ActivityProjectDetails extends AppCompatActivity implements OnMapReadyCallback {

    MenuItem share;
    boolean isReadMoreClicked = false, isSpecificationExpanded = false;
    private NestedScrollView nestedScrollView;
    TextView readMore, moreSpecification, contactOwner,call,calluswithteam;
    TextView propertyName, propertyAddress, propertyPrice, propertyKey, propertyAddedDate, propertyType, projectOffer;
    TextView propertyStatus, propertyPossession, averagePrice, /*propertyDescription,*/
            button_sell_post;
    private boolean isBusFav = false;
    private ReadMoreTextView propertyDescription;
    private TextView noData;
    LinearLayout progressLayout, amenitiesLayout, specificationLayout, similarPropertyLayout, projectOffer_lay, galleryLay;
    LinearLayout bankPartnersLay;
    FloatingActionButton favButton, locButton, videoButton;
    RecyclerView recyclerSpecification, recyclerSimilarProperties, recyclerAmenities, recyclerBankPartners;

    ArrayList<SpecificationModel> specificationList, specList1, specList2;
    ArrayList<SuggestedPropertyDetails> similarPropertiesData;
    ArrayList<AmenitiesModel> amenitiesData;
    ArrayList<AmenitiesModel> bankPartners;
    private CardView unitTab;
    AdapterPropertySpecification specificationAdapter;
    AdapterPropertyListing similarPropertyAdapter;
    AdapterAmenities amenitiesAdapter;
    AdapterBankPartners adapterBankPartners;
    private ImageView projectBannerImage;
    private GoogleMap googleMap;
    public Dialog alertDialog;
    DialogProgress progress;
    int position = 0;
    String projectID = "", projectLink = "", imageUrl = "", imagePath = "", unitimagePath = "", imageURL = "";
    private ProjectDetails1Model projects;
    ArrayList<ImageCategoryData> imageCategoryData;
    ArrayList<ProjectVideoInfo> savedVideoInfos;
    LinearLayoutManager amenitiesLayoutManager;

    private ExtendedViewPager productImageViewPager;
    private TextView tvLocal;


    List<String> nameList;
    public static int selectedPosition = 100;


    List<LocalInformationData> localInformationList;
    List<LocalInformationListData> localList;
    Marker markerMain = null;
    Marker markerSub = null;
    String selectedTypeId = "";
    String selectedTypeName = "";
    String checkBoxText = "", langName = "";
    LatLng views;
    private TextView mTitle;
    ArrayList<String> imagesArray = new ArrayList<>();
    ArrayList<String> floorImagesArray = new ArrayList<>();
    ArrayList<String> videoArray = new ArrayList<>();
    ArrayList<String> arrayListVideoIcons = new ArrayList<>();
    // NestedScrollView nestedScroll;
    boolean isValid;
    String purpose = "";


    final int duration = 10;
    final int pixelsToMove = 30;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final Runnable SCROLLING_RUNNABLE = new Runnable() {

        @Override
        public void run() {
            recyclerAmenities.smoothScrollBy(pixelsToMove, 0);
            mHandler.postDelayed(this, duration);
        }
    };

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000; // time in milliseconds between successive task executions.
    private PropertyProjectType type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_prject1_details);


        initializeMap();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);


        projectID = getIntent().getStringExtra("projectID");
        position = getIntent().getIntExtra("position", 0);

        if (getIntent().hasExtra("purpose")) {
            purpose = getIntent().getStringExtra("purpose");
        }

        type = (PropertyProjectType) getIntent().getSerializableExtra(Utils.TYPE);
        declarations();

        setUpMap();

        langName = SessionManager.getLanguageName(this);
        checkBoxText = "I agree to the <a href='" + ApiLinks.BASE_URL_TERMS_AND_CONDITIONS +/*"terms-conditions.html*/"' > Terms and Conditions</a> of Paya";

        if (langName != null && langName.contains("Arabic"))


            checkBoxText = "أوافق على <a href='" + ApiLinks.BASE_URL_TERMS_AND_CONDITIONS + "' > الأحكام والشروط</a> of Paya";

        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReadMoreClicked) {
                    propertyDescription.setMaxLines(2);
                    isReadMoreClicked = false;
                    readMore.setText(R.string.read_more);
                } else {
                    propertyDescription.setMaxLines(250);
                    isReadMoreClicked = true;
                    readMore.setText(R.string.read_less);
                }
            }
        });

        moreSpecification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSpecificationExpanded) {
                    specificationList.clear();
                    specificationList.addAll(specList1);
                    isSpecificationExpanded = false;
                    specificationAdapter.notifyDataSetChanged();
                    moreSpecification.requestFocus();
                    moreSpecification.setText(R.string.more_specifications);
                } else {
                    specificationList.addAll(specList2);
                    isSpecificationExpanded = true;
                    specificationAdapter.notifyDataSetChanged();
                    moreSpecification.requestFocus();
                    moreSpecification.setText(R.string.less_specifications);
                }
            }
        });
        contactOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.NoInternetConnection(ActivityProjectDetails.this)) {

                   /* if(!Utils.appInstalledOrNot(ActivityProjectDetails.this,"com.whatsapp")){
                        Toast.makeText(ActivityProjectDetails.this, "WhatsApp Not Installed", Toast.LENGTH_SHORT).show();

                   }else {
                        String mobileno ="";
                        if(!projects.getUserPhone().isEmpty()){
                             mobileno ="964"+projects.getUserPhone();

                        }

                        try {
                            String msg = "https://www.paya-realestate.com/projects?projectID=" +projectID;
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + mobileno + "&text=" + msg)));
                        }catch (Exception e){
                            //whatsapp app not install
                        }
                    }*/
                    if(!SessionManager.getEmail(ActivityProjectDetails.this).isEmpty())
                       alertContactOwner();
                    else {
                        Toast.makeText(ActivityProjectDetails.this, R.string.email_not_found, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityProjectDetails.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (!Utils.NoInternetConnection(ActivityProjectDetails.this)) {

            getPropertyDetails();
            getLocalList();
        } else {
            Toast.makeText(ActivityProjectDetails.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeMap() {
        MapsInitializer.initialize(getApplicationContext());
    }


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private int mCurrentPosition = -1;

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<ExtendedFragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final List<Integer> mFragmentBadges = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            if (position != mCurrentPosition) {
                Fragment fragment = (Fragment) object;
                CustomPager pager = (CustomPager) container;
                if (fragment != null && fragment.getView() != null) {
                    mCurrentPosition = position;
                    pager.measureCurrentView(fragment.getView());
                }
            }
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(ExtendedFragment fragment, String title, int badage) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            mFragmentBadges.add(badage);
            notifyDataSetChanged();
        }

        public void resetFragment(ExtendedFragment fragment, String title, int badage, int position) {
            mFragmentList.set(position, fragment);
            mFragmentTitleList.set(position, title);
            mFragmentBadges.set(position, badage);
            notifyDataSetChanged();
        }

        public int getBadge(int position) {
            return mFragmentBadges.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    //TODO API Calls
    private void getPropertyDetails() {
        progress.show();
        Log.d("projectId", projectID + " ");
        ApiLinks.getClient().create(ProjectDetails1Api.class).post(
                        "" + projectID,
                        "" + SessionManager.getLanguageID(this),
                        "" + SessionManager.getAccessToken(ActivityProjectDetails.this))
                .enqueue(new Callback<ProjectDetails1Response>() {
                    @Override
                    public void onResponse(Call<ProjectDetails1Response> call, Response<ProjectDetails1Response> response) {

                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            Log.e("getPropertyDetails", message);
                            int code = response.body().getCode();
                            if (response.body().getResponse().equalsIgnoreCase("Success")) {
                                //Toast.makeText(ActivityPropertyDetails.this, message, Toast.LENGTH_SHORT).show();
                                noData.setVisibility(View.GONE);
                                nestedScrollView.setVisibility(View.VISIBLE);
                                imageURL = response.body().getData().getImageURL();
                                projects = response.body().getData().getProjects();

                                projectLink = projects.getLink();
                                imageCategoryData = projects.getImageCategories();
                                savedVideoInfos = projects.getProjectVideos();
                                amenitiesData = new ArrayList<>();
                                bankPartners = new ArrayList<>();
                                amenitiesData.addAll(projects.getListAmenties());
                                bankPartners.addAll(projects.getBankPartners());
                                amenitiesAdapter = new AdapterAmenities(ActivityProjectDetails.this, amenitiesData, projects.getAmenitiesImgPath());
                                recyclerAmenities.setAdapter(amenitiesAdapter);
                                recyclerAmenities.scrollToPosition(0);
                                amenitiesAdapter.notifyDataSetChanged();
                                recyclerAmenities.clearFocus();

                                adapterBankPartners = new AdapterBankPartners(ActivityProjectDetails.this, bankPartners, projects.getAmenitiesImgPath());
                                recyclerBankPartners.setAdapter(adapterBankPartners);
                                recyclerBankPartners.scrollToPosition(0);
                                adapterBankPartners.notifyDataSetChanged();
                                recyclerBankPartners.clearFocus();
                                specificationList = new ArrayList<>();
                                specificationList.addAll(projects.getSpecifications());
                                specificationAdapter = new AdapterPropertySpecification(specificationList);
                                recyclerSpecification.setAdapter(specificationAdapter);
                                recyclerSpecification.scrollToPosition(0);
                                specificationAdapter.notifyDataSetChanged();
                                recyclerSpecification.clearFocus();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        recyclerSpecification.scrollToPosition(0);
                                    }
                                }, 200);
                                setDataToUI();
                                initiateAmenitiesAutoScroll();
                            } else {
                                noData.setVisibility(View.VISIBLE);
                                nestedScrollView.setVisibility(View.GONE);
                                Toast.makeText(ActivityProjectDetails.this, message, Toast.LENGTH_SHORT).show();
                            }
                            dismissProgress();
                        } else {
                            noData.setVisibility(View.VISIBLE);
                            nestedScrollView.setVisibility(View.GONE);
                            Toast.makeText(ActivityProjectDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                            dismissProgress();
                        }
                        progressLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ProjectDetails1Response> call, Throwable t) {
                        Toast.makeText(ActivityProjectDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        dismissProgress();
                        noData.setVisibility(View.VISIBLE);
                        nestedScrollView.setVisibility(View.GONE);
                        progressLayout.setVisibility(View.GONE);
                    }
                });


    }

    private void dismissProgress() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

    public void apiFavProject() {
        progress.show();
        ApiLinks.getClient().create(AddFavProjectApi.class).post(
                projectID,
                SessionManager.getAccessToken(ActivityProjectDetails.this)).enqueue(new Callback<AddFavProjectResponse>() {
            @Override
            public void onResponse(Call<AddFavProjectResponse> call, Response<AddFavProjectResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getResponse();
                    String message = response.body().getMessage();
                    Log.e("markPropertyFavourite", message);
                    int code = response.body().getCode();
                    if (status.equalsIgnoreCase("Success")) {
                        isBusFav = !isBusFav;
                        if (isBusFav) {
                            favButton.setImageResource(R.drawable.favorite_active);
                            ProjectListFragment.projectLists.get(position).setProjectFavourite("1");
                            ProjectListFragment.buyProjectListAdapter.notifyItemChanged(position);
                        } else {
                            favButton.setImageResource(R.drawable.favorite);
                            ProjectListFragment.projectLists.get(position).setProjectFavourite("0");
                            ProjectListFragment.buyProjectListAdapter.notifyItemChanged(position);
                        }
                    }
                    dismissProgress();
                    Toast.makeText(ActivityProjectDetails.this, message, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ActivityProjectDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    dismissProgress();
                    Log.e("Status", "Failed");
                }
            }

            @Override
            public void onFailure(Call<AddFavProjectResponse> call, Throwable t) {
                Toast.makeText(ActivityProjectDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                dismissProgress();
            }
        });
    }

    private void postContactOwner(final Dialog alertDialog, String strName, String strEmail, String strPhone, String message,
                                  String strCountryCode) {
        progress.show();
        alertDialog.dismiss();
        ApiLinks.getClient().create(ContactProjectApi.class).post(
                        SessionManager.getAccessToken(ActivityProjectDetails.this),
                        strName, strEmail, strPhone,
                        projectID, "Project",
                        strCountryCode, message, "Details")
                .enqueue(new Callback<ContactAgentResponse>() {
                    @Override
                    public void onResponse(Call<ContactAgentResponse> call, Response<ContactAgentResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getResponse();
                            if (response.body().getResponse().equalsIgnoreCase("Success")) {
                                alertDialog.dismiss();
                                alertSuccess(getResources().getString(R.string.thank_you_for_contact),
                                        getResources().getString(R.string.contact_owner_success_message));
                                //Toast.makeText(ActivityPropertyDetails.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                alertDialog.dismiss();
                                Toast.makeText(ActivityProjectDetails.this, message, Toast.LENGTH_SHORT).show();
                            }
                            dismissProgress();
                        } else {
                            alertDialog.dismiss();
                            Toast.makeText(ActivityProjectDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                            dismissProgress();
                        }
                    }

                    @Override
                    public void onFailure(Call<ContactAgentResponse> call, Throwable t) {
                        alertDialog.dismiss();
                        dismissProgress();
                        alertDialog.dismiss();
                    }
                });
    }


    //TODO Set Data
    private void setDataToUI() {
        propertyName.setText(projects.getProjectName());
        mTitle.setText(projects.getProjectName());
        if (projects.getProjectLatitude() != null && !projects.getProjectLatitude().equals("") &&
                projects.getProjectLongitude() != null && !projects.getProjectLongitude().equals("")) {
            setMap();
        }
        String loc2 = "", location = "";

        if (projects.getProjectUserCompanyName() != null && !projects.getProjectUserCompanyName().equals("")) {
            if (projects.getUserCompanyName() != null && !projects.getUserCompanyName().equals("")) {
                loc2 = "By " + projects.getUserCompanyName() + " ";
            }
        } else if (projects.getProjectBuilderName() != null && !projects.getProjectBuilderName().equals("")) {
            loc2 = "By " + projects.getProjectBuilderName() + " ";
        }

        if (projects.getCityLocName() != null && !projects.getCityLocName().equals("")) {
            ((TextView) findViewById(R.id.tv_location)).setText(projects.getCityLocName());
        } else ((TextView) findViewById(R.id.tv_location)).setVisibility(View.GONE);


        propertyAddress.setText(loc2);


        String price = projects.getPropertyUnitPriceRange();
        if (price == null || price.equalsIgnoreCase("null")
                || price.equalsIgnoreCase("") || price.equalsIgnoreCase("0")) {
            price = getString(R.string.price_on_request);
        } else price = getString(R.string.currency_symbol) + " " + price;

        propertyPrice.setText(price);

        propertyAddedDate.setText(projects.getProjectAddedDate());
        propertyKey.setText(projects.getProjectKey());

        if (projects.getProjectStatus() != null && !projects.getProjectStatus().equals("")) {
            propertyStatus.setText(projects.getProjectStatus());
        } else {
            propertyStatus.setVisibility(View.INVISIBLE);
        }

        if (projects.getPossessionDate() != null && !projects.getPossessionDate().equals("")) {
            ((TextView) findViewById(R.id.possessionDate)).setText(projects.getPossessionDate());
            findViewById(R.id.possessionDateLay).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.possessionDateLay).setVisibility(View.GONE);
        }

        if (projects.getProjectOffer() != null && !projects.getProjectOffer().equals("")) {
            projectOffer.setText(projects.getProjectOffer());
            projectOffer_lay.setVisibility(View.VISIBLE);
        } else {
            projectOffer_lay.setVisibility(View.GONE);
        }

       /* if (projects.getPricePerSqft() != null && !projects.getPricePerSqft().equals("")) {
            propertyType.setText(projects.getPricePerSqft());
        } else {
            findViewById(R.id.area_lay).setVisibility(View.GONE);
        }*/
        findViewById(R.id.area_lay).setVisibility(View.GONE);

        if (projects.getProjectCurrentStatus() != null && !projects.getProjectCurrentStatus().equals("")) {
            propertyPossession.setText(projects.getProjectCurrentStatus());
        } else {
            propertyPossession.setVisibility(View.INVISIBLE);
        }
        int size = projects.getProjectVideos().size();
        if (size > 0) {
            videoButton.hide();
        } else {
            videoButton.hide();
        }

        if (projects.getPlanEnquiryCountBalance() != null &&
                !projects.getPlanEnquiryCountBalance().equals("") &&
                Integer.parseInt(projects.getPlanEnquiryCountBalance()) > 0) {
            findViewById(R.id.layout_bottom_property_details).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.layout_bottom_property_details).setVisibility(View.GONE);
        }

        String propertyUnitPricePerSqftS = "";
        propertyUnitPricePerSqftS = projects.getPropertyUnitSqftRange();
        if (propertyUnitPricePerSqftS != null && !propertyUnitPricePerSqftS.equals("") && !propertyUnitPricePerSqftS.equals("null") && !propertyUnitPricePerSqftS.equals("0")) {
            propertyUnitPricePerSqftS = propertyUnitPricePerSqftS + "  " + getString(R.string.meter_square);
            averagePrice.setVisibility(View.VISIBLE);

        } else averagePrice.setVisibility(View.GONE);
        String propertyUnitPricePerSqft = "";
        propertyUnitPricePerSqft = projects.getPropertyUnitPricePerSqft();
        if (propertyUnitPricePerSqft != null && !propertyUnitPricePerSqft.equals("") && !propertyUnitPricePerSqft.equals("null") && !propertyUnitPricePerSqft.equals("0")) {

            if (getResources().getConfiguration().locale.getLanguage().equalsIgnoreCase("en")) {
                propertyUnitPricePerSqft = getString(R.string.currency_symbol) + " " + propertyUnitPricePerSqft + " " + getString(R.string.per) + " " + getString(R.string.meter_square);

            } else {
                propertyUnitPricePerSqft = getString(R.string.currency_symbol) + " " + propertyUnitPricePerSqft + " " + getString(R.string.per);

            }
            averagePrice.setVisibility(View.VISIBLE);

        } else averagePrice.setVisibility(View.GONE);

        String area_price_str = getResources().getString(R.string.area) + ": " + propertyUnitPricePerSqftS + "\n" + getResources().getString(R.string.price) + ": " + propertyUnitPricePerSqft;

        //averagePrice.setText(propertyUnitPricePerSqftS + "\n" + propertyUnitPricePerSqft);
        averagePrice.setText(area_price_str);


        if (projects.getProjectDescription() != null && !projects.getProjectDescription().equals("")) {
            propertyDescription.setText(projects.getProjectDescription());

        } else {
            propertyDescription.setVisibility(View.GONE);
            readMore.setVisibility(View.GONE);
        }

        propertyDescription.setOnLayoutListener(new ReadMoreTextView.OnLayoutListener() {
            @Override
            public void onLayouted(TextView view) {
                int lineCount = view.getLineCount();


                if (lineCount < 3) {
                    readMore.setVisibility(View.GONE);
                } else {
                    readMore.setVisibility(View.VISIBLE);
                }

            }
        });

        if (amenitiesData.size() <= 0) {
            amenitiesLayout.setVisibility(View.GONE);
        }
        if (specificationList.size() <= 0) {
            specificationLayout.setVisibility(View.GONE);
        }

        if (bankPartners.size() <= 0) {
            bankPartnersLay.setVisibility(View.GONE);
        }

        imagePath = projects.getImagePath();

        if (projects.getProjectFavourite() == 1) {
            isBusFav = true;
            favButton.setImageResource(R.drawable.favorite_active);
        } else {
            isBusFav = false;
            favButton.setImageResource(R.drawable.favorite);
        }
        ArrayList<ProjectDetails1Model.ResUnits> resUnits = projects.getResUnits();
        if (resUnits != null && resUnits.size() > 0) {
            unitTab.setVisibility(View.VISIBLE);
            setUnitDetails(resUnits);
        } else unitTab.setVisibility(View.GONE);

        if(projects.getUserPhone()!=null && !projects.getUserPhone().isEmpty()){
            call.setVisibility(View.VISIBLE);
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initiateCallProjectOwnerAPI();
                }
            });
        }
        if(projects.getUserEmail() != null && !projects.getUserEmail().isEmpty()){
            contactOwner.setVisibility(View.VISIBLE);
        }
        if(call.getVisibility()==View.GONE && contactOwner.getVisibility() == View.GONE){
            calluswithteam.setVisibility(View.VISIBLE);
            calluswithteam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" + "+964 7501297777"));
                    startActivity(callIntent);
                }
            });
        }




//Commented by lavanya
       /* if (projects.getUserPhone() != null && !projects.getUserPhone().equals("")) {
            ((TextView) findViewById(R.id.tv_call_property_owner)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        } else
            Toast.makeText(ActivityProjectDetails.this, "Number not found", Toast.LENGTH_SHORT).show();

        if (projects.getUserPhone() != null && !projects.getUserPhone().equals("")) {
            ((ImageView) findViewById(R.id.btn_call)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initiateCallProjectOwnerAPI();
                }
            });
        } else
            Toast.makeText(ActivityProjectDetails.this, "Number not found", Toast.LENGTH_SHORT).show();*/


        ArrayList<ProjectModel> similarProjects = projects.getSimilarProjects();
        if (similarProjects != null && similarProjects.size() > 0) {
            setSimilarProjects(similarProjects);
        }
        similarPropertyLayout.setVisibility(View.GONE);

        ArrayList<Fragment> arrayList = new ArrayList<>();
        imagesArray.clear();
        videoArray.clear();
        arrayListVideoIcons.clear();
        int gallerySize = imageCategoryData.size();
        int videoSize = savedVideoInfos.size();
        if (gallerySize > 0) {
            imageUrl = imageCategoryData.get(0).getImgpath();
            final ArrayList<ProjectImageInfo> images = imageCategoryData.get(0).getImages();
            for (int i = 0; i < images.size(); i++) {
                FragmentImageView imF = new FragmentImageView();
                ProjectImageInfo images1 = images.get(i);
                imF.setData(imageUrl + images1.getProjectImageName());
                imF.setId(Integer.parseInt(images1.getProjectImageID()));
                imF.setPreviewPath(imageUrl);
                imF.setLargePath(imageUrl);
                imF.setFileName(images1.getProjectImageName());
                imagesArray.add(imageUrl + images1.getProjectImageName());
                arrayList.add(imF);
            }
            if (videoSize > 0) {
                for (int i = 0; i < videoSize; i++) {
                    if (savedVideoInfos.get(i).getProjectVideoType().equals("youtube")) {
                        videoArray.add(savedVideoInfos.get(i).getProjectVideoYoutubeID());
                        arrayListVideoIcons.add("https://img.youtube.com/vi/" + savedVideoInfos.get(i).getProjectVideoYoutubeID() + "/hqdefault.jpg");
                    }
                }
            }
            AdapterSlider adapterSlider = new AdapterSlider(getSupportFragmentManager(), arrayList);
            productImageViewPager.setAdapter(adapterSlider);

            /*After setting the adapter use the timer */
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (productImageViewPager.getCurrentItem() == adapterSlider.getCount() - 1) { //adapter is your custom ViewPager's adapter
                        productImageViewPager.setCurrentItem(0);
                    } else {
                        productImageViewPager.setCurrentItem(productImageViewPager.getCurrentItem() + 1, true);
                    }
                }
            };

            timer = new Timer(); // This will create a new Thread
            timer.schedule(new TimerTask() { // task to be scheduled
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, DELAY_MS, PERIOD_MS);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            tabLayout.setupWithViewPager(productImageViewPager, true);
            productImageViewPager.setOnItemClickListener(new ExtendedViewPager.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(ActivityProjectDetails.this, GalleryListActivity.class);
                    intent.putExtra("images", imagesArray);
                    intent.putExtra("title", projects.getProjectName());
                    intent.putExtra("videos", videoArray);
                    intent.putExtra("videoIcons", arrayListVideoIcons);
                    startActivity(intent);
                }
            });

            ((findViewById(R.id.buttonBackImage))).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productImageViewPager.previous();
                }
            });
            ((findViewById(R.id.buttonNextImage))).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productImageViewPager.next();
                }
            });
            if (adapterSlider.getCount() > 1)
                tabLayout.setVisibility(View.VISIBLE);
            else
                tabLayout.setVisibility(View.GONE);
        }
    }

    private void initiateCallProjectOwnerAPI() {
        progress.show();
        ApiLinks.getClient().create(CallPropertyOwnerApi.class).post(projectID, SessionManager.getLanguageID(this))
                .enqueue(new Callback<CallPropertyOwnerResponse>() {
                    @Override
                    public void onResponse(Call<CallPropertyOwnerResponse> call, Response<CallPropertyOwnerResponse> response) {
                        initiateCallIntent();
                        progress.dismiss();
                    }

                    @Override
                    public void onFailure(Call<CallPropertyOwnerResponse> call, Throwable t) {
                        initiateCallIntent();
                        progress.dismiss();
                    }
                });
    }

    private void initiateCallIntent() {
        Intent callIntent = new Intent(Intent.ACTION_VIEW);
        Log.d("cc",projects.getUserPhone()+" "+projectID);
        callIntent.setData(Uri.parse("tel:" + projects.getUserPhone()));
        startActivity(callIntent);
    }

    RecyclerView similarRecyclerView;

    public void setSimilarProjects(ArrayList<ProjectModel> similarProjects) {
        similarRecyclerView = findViewById(R.id.project_gallery_horizontel);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(R.string.similar_projects);
        title.setVisibility(View.VISIBLE);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ActivityProjectDetails.this, LinearLayoutManager.HORIZONTAL, false);

        ProjectSimilarAdapter projectSimilarAdapter = new ProjectSimilarAdapter(ActivityProjectDetails.this, imagePath, similarProjects, new ProjectSimilarAdapter.OnItemClickListener() {
            @Override
            public void onContactClick(ProjectModel item) {
                alertContactOwner();
            }

            @Override
            public void onItemClick(ProjectModel item) {
                Intent intent = new Intent(ActivityProjectDetails.this,
                        ActivityProjectDetails.class);
                intent.putExtra("projectID", item.getProjectID());
                intent.putExtra("position", "0");
                startActivity(intent);
                finish();

            }
        });
        similarRecyclerView.setLayoutManager(mLayoutManager);
        similarRecyclerView.setAdapter(projectSimilarAdapter);
    }

    private FragmentUnitDetails fragmentUnitDetails;

    public void setUnitDetails(ArrayList<ProjectDetails1Model.ResUnits> resUnits) {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        // setFragmentOverView();
        unitimagePath = projects.getUnitimagePath();
        for (ProjectDetails1Model.ResUnits resUnits1 : resUnits) {
            fragmentUnitDetails = new FragmentUnitDetails();
            String bedrooms = getString(R.string.bedroom_s);
            if (resUnits.get(0).getTitle().equals(resUnits1.getTitle())) {
                bedrooms = "";
            }
            fragmentUnitDetails.setData(resUnits1.getUnits());
            adapter.addFragment(fragmentUnitDetails, resUnits1.getTitle() + " " + bedrooms, 0);
            fragmentUnitDetails.setOnFragmentChangeListener(new ExtendedFragment.OnFragmentChangeListener() {
                @Override
                public void onBack() {
                }

                @Override
                public void onCompleted() {
                }

                @Override
                public void onError() {
                }

                @Override
                public void onDataArrive(int requestCode, Object data) {
                    if (requestCode == FragmentUnitDetails.CONTACT_CLICK)
                        alertContactOwner();
                    if (requestCode == FragmentUnitDetails.FLOR_CLICK) {
                        floorImagesArray.clear();
                        Units units = (Units) data;
                        ArrayList<FloorPlans> floorPlans = units.getFloorPlans();
                        int floorPlanSize = floorPlans.size();
                        if (floorPlanSize > 0) {
                            for (int i = 0; i < floorPlanSize; i++) {
                                floorImagesArray.add(unitimagePath + floorPlans.get(i).getPropertyImageName());
                            }
                            Intent intent = new Intent(ActivityProjectDetails.this, GalleryListActivity.class);
                            intent.putExtra("images", floorImagesArray);
                            intent.putExtra("videos", new ArrayList<String>());
                            intent.putExtra("videoIcons", new ArrayList<String>());
                            intent.putExtra("floor", "yes");
                            startActivity(intent);
                        }
                    }
                }
            });
        }

        viewPager.setAdapter(adapter);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            View view = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tab_item_units, null, false);
            ((TextView) view.findViewById(R.id.textView1)).setText(adapter.getPageTitle(i));
            if (adapter.getBadge(i) > 0) {
                view.findViewById(R.id.textView2).setVisibility(View.VISIBLE);
            } else {
                view.findViewById(R.id.textView2).setVisibility(View.GONE);
            }
            ((TextView) view.findViewById(R.id.textView2)).setText(adapter.getBadge(i) + "");
            tab.setCustomView(view);
        }

    }

    private void declarations() {
        projects = new ProjectDetails1Model();

        noData = findViewById(R.id.no_data_available);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        progressLayout = (LinearLayout) findViewById(R.id.progress_layout_property_details);
        favButton = (FloatingActionButton) findViewById(R.id.fab_fav);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SessionManager.getLoginStatus(ActivityProjectDetails.this)) {
                    apiFavProject();
                } else {
                    startActivity(new Intent(ActivityProjectDetails.this, ActivityLoginEmail.class));
                }

            }
        });
        // nestedScroll = findViewById(R.id.nestedScrollView);
        locButton = (FloatingActionButton) findViewById(R.id.fab_loc);
        videoButton = (FloatingActionButton) findViewById(R.id.fab_video);
        amenitiesLayout = (LinearLayout) findViewById(R.id.amenities_lay);
        specificationLayout = (LinearLayout) findViewById(R.id.specification_lay);
        bankPartnersLay = (LinearLayout) findViewById(R.id.bankPartnersLay);
        // projectBannerImage = (ImageView) findViewById(R.id.projectBannerImage);


        projectOffer_lay = (LinearLayout) findViewById(R.id.projectOffer_lay);
        //galleryLay = (LinearLayout) findViewById(R.id.galleryLay);
        unitTab = (CardView) findViewById(R.id.unitTab);
        projectOffer = (TextView) findViewById(R.id.projectOffer);
        similarPropertyLayout = (LinearLayout) findViewById(R.id.layout_similar_properties_content_property_details);
        propertyName = (TextView) findViewById(R.id.property_name_content_property_details);
        propertyAddress = (TextView) findViewById(R.id.property_address_content_property_details);
        propertyKey = (TextView) findViewById(R.id.propertyKey);
        propertyAddedDate = (TextView) findViewById(R.id.propertyAddedDate);
        propertyPrice = (TextView) findViewById(R.id.price_content_property_details);
        propertyType = (TextView) findViewById(R.id.property_type_content_property_details);
        progress = new DialogProgress(ActivityProjectDetails.this);
        propertyStatus = (TextView) findViewById(R.id.property_status_content_property_details);
        propertyPossession = (TextView) findViewById(R.id.possession_date_content_property_details);
        averagePrice = (TextView) findViewById(R.id.avg_price_content_property_details);
        propertyDescription = (ReadMoreTextView) findViewById(R.id.description_content_property_details);
        recyclerAmenities = (RecyclerView) findViewById(R.id.recycler_property_amenities);
        recyclerBankPartners = (RecyclerView) findViewById(R.id.recycler_property_bankPartners);
        contactOwner = (TextView) findViewById(R.id.contactButton);
        call = findViewById(R.id.tv_call_property_owner);
        calluswithteam = findViewById(R.id.call_us_paya_team);
        productImageViewPager = (ExtendedViewPager) findViewById(R.id.productImageViewPager);
        productImageViewPager.setOffscreenPageLimit(10);
        specificationList = new ArrayList<>();
        specList1 = new ArrayList<>();
        specList2 = new ArrayList<>();
        readMore = (TextView) findViewById(R.id.read_more_less_content_property_details);
        recyclerSpecification = (RecyclerView) findViewById(R.id.recycler_property_specification);
        moreSpecification = (TextView) findViewById(R.id.more_specification_property_details);
        recyclerSimilarProperties = (RecyclerView) findViewById(R.id.recycler_similar_properties);
        button_sell_post = (TextView) findViewById(R.id.button_sell_post);
        button_sell_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SessionManager.getLoginStatus(ActivityProjectDetails.this)) {
                    startActivity(new Intent(ActivityProjectDetails.this, PostPropertyPage01Activity.class));
                } else {
                    startActivity(new Intent(ActivityProjectDetails.this, ActivityLoginEmail.class));
                }
            }
        });
        locButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final View targetView = findViewById(R.id.map_property_lay);
                nestedScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        nestedScrollView.smoothScrollTo(0, (int) targetView.getY());
                    }
                });

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityProjectDetails.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setAutoMeasureEnabled(true);
        specificationAdapter = new AdapterPropertySpecification(specificationList);
        // recyclerSpecification.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerSpecification.setLayoutManager(linearLayoutManager);
        recyclerSpecification.setNestedScrollingEnabled(false);
        recyclerSpecification.setHasFixedSize(false);
        recyclerSpecification.setAdapter(specificationAdapter);
        specificationList.addAll(specList1);
        specificationAdapter.notifyDataSetChanged();
        isSpecificationExpanded = false;
        recyclerSpecification.clearFocus();

        similarPropertiesData = new ArrayList<>();
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(ActivityProjectDetails.this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager1.setAutoMeasureEnabled(true);
        similarPropertyAdapter = new AdapterPropertyListing(ActivityProjectDetails.this, similarPropertiesData);
        recyclerSimilarProperties.setLayoutManager(linearLayoutManager1);
        recyclerSimilarProperties.setNestedScrollingEnabled(false);
        recyclerSimilarProperties.setHasFixedSize(false);
        recyclerSimilarProperties.setAdapter(similarPropertyAdapter);
        similarPropertyAdapter.notifyDataSetChanged();
        recyclerSimilarProperties.clearFocus();

        amenitiesData = new ArrayList<>();
        amenitiesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recyclerAmenities.setLayoutManager(amenitiesLayoutManager);
        recyclerAmenities.setNestedScrollingEnabled(false);
        recyclerAmenities.setHasFixedSize(true);
        recyclerAmenities.clearFocus();
        recyclerBankPartners.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerBankPartners.setNestedScrollingEnabled(false);
    }


    private void initiateAmenitiesAutoScroll() {
        recyclerAmenities.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastItem = amenitiesLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastItem == amenitiesLayoutManager.getItemCount() - 1) {
                    mHandler.removeCallbacks(SCROLLING_RUNNABLE);
                    Handler postHandler = new Handler();
                    postHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerAmenities.setAdapter(null);
                            recyclerAmenities.setAdapter(amenitiesAdapter);
                            mHandler.postDelayed(SCROLLING_RUNNABLE, 2000);
                        }
                    }, 2000);
                }
            }
        });
        mHandler.postDelayed(SCROLLING_RUNNABLE, 2000);
    }

    private void sharePropertyLink() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        try {
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, projects.getProjectName());
            sendIntent.putExtra(Intent.EXTRA_TEXT, projectLink);

        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }
        startActivity(sendIntent);
    }

    private void getLocalList() {
        ApiLinks.getClient().create(LocalInformationListApi.class).post("0")
                .enqueue(new Callback<LocalInformationListResponse>() {
                    @Override
                    public void onResponse(Call<LocalInformationListResponse> call, Response<LocalInformationListResponse> response) {
                        if (response.body() != null && response.body().getResponse().equalsIgnoreCase("Success")) {
                            localList = response.body().getLocalList();
                            nameList = new ArrayList<>();
                            for (int i = 0; i < localList.size(); i++) {
                                nameList.add(localList.get(i).getLocalinfoTypeTitle());
                            }
                        } else {
                            Toast.makeText(ActivityProjectDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LocalInformationListResponse> call, Throwable t) {
                        Toast.makeText(ActivityProjectDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    //TODO Google Map
    private void setUpMap() {

        //parent scrollview in xml, give your scrollview id value
        ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_property_details))
                .setListener(new WorkaroundMapFragment.OnTouchListener() {
                    @Override
                    public void onTouch() {
                        nestedScrollView.requestDisallowInterceptTouchEvent(true);
                    }
                });

        tvLocal = findViewById(R.id.tv_local);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_property_details);
        mapFragment.getMapAsync(this);
        tvLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListDialogBox dialogBox = new ListDialogBox(ActivityProjectDetails.this, nameList, "Local Information", "project");
                dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogBox.show();
                dialogBox.setCanceledOnTouchOutside(true);
                dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (selectedPosition != 100) {
                            if (googleMap != null) {
                                googleMap.clear();
                            }
                            tvLocal.setText(localList.get(selectedPosition).getLocalinfoTypeTitle());
                            selectedTypeId = localList.get(selectedPosition).getLocalinfoTypeID();
                            selectedTypeName = localList.get(selectedPosition).getLocalinfoTypeTitle();
                            getLocalData(selectedTypeId);
                        }
                    }
                });
            }
        });
    }

    public void setLocalInformation() {
        try {
            int height = 40;
            int width = 40;
            List<LatLng> views = new ArrayList<LatLng>();
            for (int i = 0; i < localInformationList.size(); i++) {
                BitmapDrawable bitmapdraw = getBitmapDrawableLocal(selectedTypeName, i);
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                Double lat = Double.parseDouble(localInformationList.get(i).getInfoLat());
                Double lon = Double.parseDouble(localInformationList.get(i).getInfoLng());
                views.add(new LatLng(lat, lon));
                markerSub = googleMap.addMarker(new MarkerOptions()
                        .position(views.get(i))
                        .title(localInformationList.get(i).getInfoName())
                        .snippet(localInformationList.get(i).getInfoAddress())
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                );
                markerSub.showInfoWindow();
                setMap();
            }
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
        }
    }

    private void getLocalData(String selectedTypeId) {
        progress.show();
        ApiLinks.getClient().create(LocalInformationDataApi.class).post(selectedTypeId, projects.getProjectLatitude(), projects.getProjectLongitude())
                .enqueue(new Callback<LocalInformationDataResponse>() {
                    @Override
                    public void onResponse(Call<LocalInformationDataResponse> call, Response<LocalInformationDataResponse> response) {
                        if (response.body().getResponse().equalsIgnoreCase("Success")) {
                            dismissProgress();
                            localInformationList = response.body().getLocalInformation();
                            setLocalInformation();
                        } else {
                            dismissProgress();
                            Toast.makeText(ActivityProjectDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LocalInformationDataResponse> call, Throwable t) {
                        dismissProgress();
                        Toast.makeText(ActivityProjectDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void setMap() {
        try {
            int height = 35;
            int width = 35;
            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(getMarker());
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
            Double lat = Double.parseDouble(projects.getProjectLatitude());
            Double lon = Double.parseDouble(projects.getProjectLongitude());
            views = new LatLng(lat, lon);
            markerMain = googleMap.addMarker(new MarkerOptions()
                    .position(views)
                    .title(projects.getProjectName())
                    .snippet(projects.getCityLocName())
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
            );
            markerMain.showInfoWindow();
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(views, 12.0F));
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
        }
    }

    private int getMarker() {
        if (PropertyProjectType.BUY == type) {
            return R.drawable.ic_red_circle;
        } else if (PropertyProjectType.RENT == type) {
            return R.drawable.ic_green_circle;
        } else {
            return R.drawable.ic_yellow_circle;
        }
    }

    @Override
    public void onMapReady(GoogleMap mGoogleMap) {
        if (googleMap != null) {
            return;
        }
        googleMap = mGoogleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        if (projects.getProjectLatitude() != null && !projects.getProjectLatitude().equals("") &&
                projects.getProjectLongitude() != null && !projects.getProjectLongitude().equals("")) {
            setMap();
        }

    }

    public BitmapDrawable getBitmapDrawable(String value) {
        BitmapDrawable bitmapdraw = null;
        switch (value) {
            case "Apartment":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_apartment);
                break;
            case "Villa":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_villa);
                break;
            case "Flat":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_apartment);
                break;
            case "House":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_house);
                break;
            case "Agricultural Land":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_agricultural_land);
                break;
            case "Commercial Office Space":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_commercial_office_space);
                break;
            case "Office":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_office_it_park);
                break;
            case "Commercial Shop":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_commercial_shop);
                break;
            case "Commercial Showroom":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_locationcommercial_showroom);
                break;
            case "Co-Working Space":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_co_working_space);
                break;
            case "Farm House":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_locationfarm_house);
                break;
            case "Industrial Land":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_industrial_land);
                break;
            case "Industrial Shed":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_industrial_shed);
                break;
            case "Office IT-park":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_office_it_park);
                break;
            case "Penthouse":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_penthouse);
                break;
            case "Residential House":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_residential_house);
                break;
            case "Residential Land":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_residential_land);
                break;
            case "Shop":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_shop);
                break;
            case "Studio Apartment":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_studio_apartment);
                break;
            case "Warehouse":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_warehouse);
                break;
            default:
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_apartment);
                break;
        }
        return bitmapdraw;

    }

    public BitmapDrawable getBitmapDrawableLocal(String value, int i) {
        BitmapDrawable bitmapdraw = null;
        switch (value) {
            case "ATM":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_atm);
                break;
            case "Bank":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_bank);
                break;
            case "Bus Station":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_bus_station);
                break;
            case "Food":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_food);
                break;
            case "Gym":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_gym);
                break;
            case "Hospital":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_hospital);
                break;
            case "Pharmacy":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_pharmacy);
                break;
            case "Restaurant":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_food);
                break;
            case "School":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_school);
                break;
            case "Temple":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_temple);
                break;
            case "Train":
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_train);
                break;
            default:
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_bank);
                break;
        }
        return bitmapdraw;

    }


    //TODO Alert
    private void alertContactOwner() {
        alertDialog = new Dialog(ActivityProjectDetails.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_contact_agent_popup, null);
        ImageView close = alert_layout.findViewById(R.id.close_contact_owner);
        final EditText fullName = alert_layout.findViewById(R.id.editText_user_full_name_contact_owner);
        final EditText email = alert_layout.findViewById(R.id.editText_user_email_address_contact_owner);
        final EditText phone = alert_layout.findViewById(R.id.editText_phone_contact_owner);
        final CountryCodePicker codePicker = alert_layout.findViewById(R.id.country_code_contact_owner);
        codePicker.registerCarrierNumberEditText(phone);
        final EditText message = alert_layout.findViewById(R.id.editText_user_message_contact_owner);
        final CheckBox cbTerms = alert_layout.findViewById(R.id.checkBox_terms_conditions_contact_owner);
        final TextView title = alert_layout.findViewById(R.id.title);
        title.setText(R.string.email);
        cbTerms.setText(Html.fromHtml(checkBoxText));
        cbTerms.setMovementMethod(LinkMovementMethod.getInstance());

        TextView submit = alert_layout.findViewById(R.id.button_submit_contact_owner);


        final CheckBox cbMortgage = alert_layout.findViewById(R.id.checkBox_info_mortgage_contact_owner);

      /*  if (purpose.compareToIgnoreCase("Rent") == 0) {
            cbMortgage.setVisibility(View.GONE);
        }*/
        // message.setVisibility(View.GONE);
        codePicker.setCountryForNameCode(GlobalValues.countryCode);
        if (SessionManager.getLoginStatus(ActivityProjectDetails.this)) {
            fullName.setText(SessionManager.getFullName(ActivityProjectDetails.this));
            email.setText(SessionManager.getEmail(ActivityProjectDetails.this));
            phone.setText(SessionManager.getPhone(ActivityProjectDetails.this));
            codePicker.setCountryForNameCode(SessionManager.getCountryCode(ActivityProjectDetails.this));
            message.clearFocus();
            message.requestFocus();
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strCountryCode = codePicker.getSelectedCountryCode().toLowerCase();
                String strName;
                String strEmail = "";
                String strPhone;

                if (fullName.getText().toString().trim().equals("")) {
                    fullName.clearFocus();
                    fullName.requestFocus();
                    Toast.makeText(ActivityProjectDetails.this, getString(R.string.please_enter_name), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    strName = fullName.getText().toString();
                    if (!Utils.isValidName(strName)) {
                        Toast.makeText(ActivityProjectDetails.this, getString(R.string.please_enter_valid_name),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (!email.getText().toString().equals("")) {
                /*    email.clearFocus();
                    email.requestFocus();
                    Toast.makeText(ActivityProjectDetails.this, getString(R.string.please_enter_email_address), Toast.LENGTH_SHORT).show();
                    return;
                } else {*/
                    strEmail = email.getText().toString();
                    if (TextUtils.isEmpty(strEmail) || !Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                        Toast.makeText(ActivityProjectDetails.this, getString(R.string.please_enter_valid_email_address), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (!phone.getText().toString().equals("")) {
//                    phone.clearFocus();
//                    phone.requestFocus();
//                    Toast.makeText(ActivityProjectDetails.this, getString(R.string.please_enter_phone_number), Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
                    strPhone = phone.getText().toString();
                    if (!Utils.isValidMobile(strPhone)) {
                        Toast.makeText(ActivityProjectDetails.this,
                                getString(R.string.valid_mobile_number), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
               /* if (!validatePhone(phone.getText().toString(), codePicker)) {
                    return;
                }*/

                if (!cbTerms.isChecked()) {
                    Toast.makeText(ActivityProjectDetails.this, getString(R.string.accept_terms_conditions), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utils.NoInternetConnection(ActivityProjectDetails.this)) {
                    postContactOwner(alertDialog, strName, strEmail, phone.getText().toString(), message.getText().toString(), strCountryCode);
                } else {
                    Toast.makeText(ActivityProjectDetails.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void alertSuccess(String successTitle, String successMessage) {
        alertDialog = new Dialog(ActivityProjectDetails.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) ActivityProjectDetails.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_success_popup, null);
        ImageView close = alert_layout.findViewById(R.id.close_success_dialog);
        TextView title = alert_layout.findViewById(R.id.title_alert_success_popup);
        TextView message = alert_layout.findViewById(R.id.message_alert_success_popup);
        title.setText(successTitle);
        message.setText(successMessage);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setContentView(alert_layout);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }


    //TODO Main Functions
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details_menu, menu);
        //favourite = menu.findItem(R.id.detail_menu_favourite);
        share = menu.findItem(R.id.detail_menu_share);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.detail_menu_share:
                sharePropertyLink();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean validatePhone(String mobile, final CountryCodePicker codePicker) {
        if (mobile.equals("")) {
            Toast.makeText(ActivityProjectDetails.this, getString(R.string.valid_mobile_number), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!validateMobile(codePicker, mobile)) {
                Toast.makeText(ActivityProjectDetails.this, getString(R.string.valid_mobile_number),
                        Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }

        }
    }

    public boolean validateMobile(CountryCodePicker codePicker, String mobile) {

        if (!codePicker.getSelectedCountryCode().equals("964")) {
            codePicker.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
                @Override
                public void onValidityChanged(boolean isValidNumber) {
                    isValid = isValidNumber;
                }
            });
        } else isValid = !Utils.isValidMobile(mobile);

        return isValid;

    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.rl_project_details_parent_layout));
    }


}
