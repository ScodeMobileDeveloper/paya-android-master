package com.paya.paragon.activity.details;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.paya.paragon.R;
import com.paya.paragon.activity.GalleryListActivity;
import com.paya.paragon.activity.buy.PropertyListFragment;
import com.paya.paragon.activity.login.ActivityLoginEmail;
import com.paya.paragon.activity.postProperty.PostPropertyPage01Activity;
import com.paya.paragon.adapter.AdapterAmenities;
import com.paya.paragon.adapter.AdapterPropertyListing;
import com.paya.paragon.adapter.AdapterPropertySpecification;
import com.paya.paragon.adapter.AdapterSlider;
import com.paya.paragon.adapter.PropertySimilarAdapter;
import com.paya.paragon.api.buy_properties.BuyPropertiesModel;
import com.paya.paragon.api.FetchLocalInformationData.LocalInformationData;
import com.paya.paragon.api.FetchLocalInformationData.LocalInformationDataApi;
import com.paya.paragon.api.FetchLocalInformationData.LocalInformationDataResponse;
import com.paya.paragon.api.StandardResponse;
import com.paya.paragon.api.addFavProperty.AddFavPropertyApi;
import com.paya.paragon.api.addFavProperty.AddFavPropertyResponse;
import com.paya.paragon.api.contactOwner.ContactOwnerData;
import com.paya.paragon.api.contactOwner.ContactOwnerPropertyApi;
import com.paya.paragon.api.contactOwner.ContactOwnerResponse;
import com.paya.paragon.api.localInformationList.LocalInformationListApi;
import com.paya.paragon.api.localInformationList.LocalInformationListData;
import com.paya.paragon.api.localInformationList.LocalInformationListResponse;
import com.paya.paragon.api.makeAnOffer.MakeAnOfferApi;
import com.paya.paragon.api.makeAnOffer.MakeAnOfferResponse;
import com.paya.paragon.api.postProperty.loadVideo.SavedVideoInfo;
import com.paya.paragon.api.propertyDetails.AmenitiesModel;
import com.paya.paragon.api.propertyDetails.CallPropertyOwnerApi;
import com.paya.paragon.api.propertyDetails.CallPropertyOwnerResponse;
import com.paya.paragon.api.propertyDetails.PropertyDetailsLoginedApi;
import com.paya.paragon.api.propertyDetails.PropertyDetailsModel;
import com.paya.paragon.api.propertyDetails.PropertyDetailsResponse;
import com.paya.paragon.api.propertyDetails.SpecificationModel;
import com.paya.paragon.api.propertyDetails.SuggestedPropertyDetails;
import com.paya.paragon.api.propertyDetails.WhatsappClickApi;
import com.paya.paragon.classes.ReadMoreTextView;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.AppConstant;
import com.paya.paragon.utilities.CountryCode.CountryCodePicker;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.ExtendedViewPager;
import com.paya.paragon.utilities.FragmentImageView;
import com.paya.paragon.utilities.ListDialogBox;
import com.paya.paragon.utilities.NumberTextWatcherForThousand;
import com.paya.paragon.utilities.PopupImageViewer;
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
import timber.log.Timber;

@SuppressWarnings({"HardCodedStringLiteral", "RedundantCast", "deprecation", "unused"})
public class ActivityPropertyDetails extends AppCompatActivity implements OnMapReadyCallback, PopupImageViewer.PreviewCallBack {

    MenuItem share;
    boolean isReadMoreClicked = false, isSpecificationExpanded = false;
    private NestedScrollView nestedScrollView;
    TextView readMore, moreSpecification, tvCallPropertyOwner, contactOwnerByMail, tvAgentName;
    TextView propertyName, propertyAddress, offerValue, soldOutDate, propertyOfferEndTime, propertyPrice, propertyKey, propertyAddedDate, propertyType, priceperm2;
    TextView propertyStatus, propertyPossession, averagePrice, /*propertyDescription,*/
            button_sell_post, similarProperties;
    ImageView btnCall, ivAgentLogo;
    ReadMoreTextView propertyDescription;
    private boolean isBusFav = false;
    private TextView noData;
    LinearLayout progressLayout, amenitiesLayout, specificationLayout, similarPropertyLayout, layAgentDetails;
    FloatingActionButton favButton, locButton, videoButton, fabBlueprint;
    RecyclerView recyclerSpecification, recyclerSimilarProperties, recyclerAmenities;
    ArrayList<SavedVideoInfo> savedVideoInfos;
    ArrayList<SpecificationModel> specificationList, specList1, specList2;
    ArrayList<SuggestedPropertyDetails> similarPropertiesData;
    ArrayList<AmenitiesModel> amenitiesData;

    AdapterPropertySpecification specificationAdapter;
    AdapterPropertyListing similarPropertyAdapter;
    AdapterAmenities amenitiesAdapter;
    LinearLayoutManager amenitiesLayoutManager;

    private GoogleMap googleMap;
    public Dialog alertDialog;
    DialogProgress progress;
    int position = 0;
    String propertyID = "", propertyLink = "", imageUrl = "", agentId = "";
    PropertyDetailsModel propertyDetails;
    PropertyDetailsModel.PropertyImages propertyImages;

    private ExtendedViewPager productImageViewPager;
    private AdapterSlider adapterSlider;
    private TextView tvLocal;
    List<String> nameList;
    public static int selectedPosition = 100;
    List<LocalInformationData> localInformationList;
    List<LocalInformationListData> localList;
    Marker markerMain = null;
    Marker markerSub = null;
    String selectedTypeId = "";
    String selectedTypeName = "";
    LatLng views;
    private TextView mTitle;
    private TextView tvTotalViews;
    ArrayList<String> imagesArray = new ArrayList<>();
    ArrayList<String> videoArray = new ArrayList<>();
    ArrayList<String> arrayListVideoIcons = new ArrayList<>();
    RecyclerView similarRecyclerView;
    boolean isValid;
    String checkBoxText = "", langName = "";
    String purpose = "";
    private String isMortgaged = "No", isOwnerMortgage = "No";
    ContactOwnerData contactOwnerData;
    private String callingPhoneNo = "";


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
    Timer viewPagerTimer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000; // time in milliseconds between successive task executions.
    private PropertyProjectType type;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_property_details);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        initializeMap();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        mTitle.setTypeface(mTitle.getTypeface(), Typeface.BOLD);


        propertyID = getIntent().getStringExtra("propertyID");
        position = getIntent().getIntExtra("position", 0);
        if (getIntent().hasExtra("purpose")) {
            purpose = getIntent().getStringExtra("purpose");
        }
        type = (PropertyProjectType) getIntent().getSerializableExtra(Utils.TYPE);

        Log.d("id",propertyID);

        declarations();


        setUpMap();
        langName = SessionManager.getLanguageName(this);

        checkBoxText = "I agree to the <a href='" + ApiLinks.BASE_URL_TERMS_AND_CONDITIONS + "' > Terms and Conditions</a>" + " of Paya";
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
                    propertyDescription.setMaxLines(100);
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


        contactOwnerByMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("WhatsApp", "WhatsApp_Clicked");
                mFirebaseAnalytics.logEvent("WhatsApp_Button", bundle);

                if (!Utils.NoInternetConnection(ActivityPropertyDetails.this)) {

                    if (!Utils.appInstalledOrNot(ActivityPropertyDetails.this, "com.whatsapp")) {
                        Toast.makeText(ActivityPropertyDetails.this, "WhatsApp Not Installed", Toast.LENGTH_SHORT).show();
                    } else {
                        initiateWhatsappClick();

                       /* String mobileno ="";
                        if(!propertyDetails.getAdditionalPhone_2().isEmpty()){
                             mobileno ="964"+propertyDetails.getAdditionalPhone_2();

                        }else if(!propertyDetails.getAdditionalPhone_1().isEmpty()) {
                            mobileno = "964"+propertyDetails.getAdditionalPhone_1();
                        }else {
                            mobileno = propertyDetails.getOwnerPhone();
                        }


                        try {
                            String msg = "https://www.paya-realestate.com/properties?propertyID=" +propertyID;
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + mobileno + "&text=" + msg)));
                        }catch (Exception e){
                            //whatsapp app not install
                        }*/
                    }
//                     alertContactOwner();
                } else {
                    Toast.makeText(ActivityPropertyDetails.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void initializeMap() {
        MapsInitializer.initialize(getApplicationContext());
    }

    //TODO API Calls
    private void getPropertyDetails() {
        progress.show();
        Timber.tag("property").d(propertyID + " " + SessionManager.getLanguageID(this) + " " + SessionManager.getAccessToken(ActivityPropertyDetails.this));

        ApiLinks.getClient().create(PropertyDetailsLoginedApi.class).post(propertyID,
                        "" + SessionManager.getLanguageID(this),
                        SessionManager.getAccessToken(ActivityPropertyDetails.this))
                .enqueue(new Callback<PropertyDetailsResponse>() {
                    @Override
                    public void onResponse(Call<PropertyDetailsResponse> call, Response<PropertyDetailsResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            //     Log.e("getPropertyDetails", message);
                            int code = response.body().getCode();
                            if (response.body().getResponse().equalsIgnoreCase("Success")) {
                                //Toast.makeText(ActivityPropertyDetails.this, message, Toast.LENGTH_SHORT).show();
                                noData.setVisibility(View.GONE);
                                nestedScrollView.setVisibility(View.VISIBLE);
                                if (response.body() != null && response.body().getData() != null &&
                                        response.body().getData().getTotalViews() != null && !response.body().getData().getTotalViews().isEmpty()) {
                                    tvTotalViews.setText(response.body().getData().getTotalViews());
                                } else {
                                    tvTotalViews.setText("0");
                                }
                                propertyDetails = response.body().getData().getProperty();
                                imageUrl = propertyDetails.getPropertyImages().getImageUrl();
                                propertyLink = propertyDetails.getPropertyLink();
                                propertyImages = propertyDetails.getPropertyImages();
                                savedVideoInfos = propertyDetails.getPropertyVideos();
                                amenitiesData = new ArrayList<>();
                                amenitiesData.addAll(propertyDetails.getLstAmenities());
                                amenitiesAdapter = new AdapterAmenities(ActivityPropertyDetails.this, amenitiesData, propertyDetails.getAmenitiesImgPath());
                                recyclerAmenities.setAdapter(amenitiesAdapter);
                                recyclerAmenities.scrollToPosition(0);
                                amenitiesAdapter.notifyDataSetChanged();
                                recyclerAmenities.clearFocus();
                                specificationList = new ArrayList<>();
                                specificationList.addAll(response.body().getData().getProperty().getPropertyAtrributes());
                                specificationAdapter = new AdapterPropertySpecification(specificationList);
                                recyclerSpecification.setAdapter(specificationAdapter);
                                recyclerSpecification.scrollToPosition(0);
                                specificationAdapter.notifyDataSetChanged();
                                recyclerSpecification.clearFocus();


                                similarPropertiesData = new ArrayList<>();
                                if (response.body().getSuggestedProperties().getProperties() != null) {
                                    similarProperties.setText(String.format("%s (%s)", getString(R.string.similar_properties), response.body().getSuggestedProperties().getCountProperties()));

                                    similarPropertiesData.addAll(response.body().getSuggestedProperties().getProperties());
                                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(ActivityPropertyDetails.this);
                                    linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
                                    linearLayoutManager1.setAutoMeasureEnabled(true);
                                    similarPropertyAdapter = new AdapterPropertyListing(ActivityPropertyDetails.this, similarPropertiesData);
                                    recyclerSimilarProperties.setLayoutManager(linearLayoutManager1);
                                    recyclerSimilarProperties.setNestedScrollingEnabled(false);
                                    recyclerSimilarProperties.setHasFixedSize(false);
                                    recyclerSimilarProperties.setAdapter(similarPropertyAdapter);
                                    similarPropertyAdapter.notifyDataSetChanged();
                                }

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
                                Toast.makeText(ActivityPropertyDetails.this, message, Toast.LENGTH_SHORT).show();
                            }
                            dismissProgress();
                        } else {

                            noData.setVisibility(View.VISIBLE);
                            nestedScrollView.setVisibility(View.GONE);
                            Toast.makeText(ActivityPropertyDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                            dismissProgress();
                        }
                        progressLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<PropertyDetailsResponse> call, Throwable t) {
                        noData.setVisibility(View.VISIBLE);
                        nestedScrollView.setVisibility(View.GONE);
                        dismissProgress();
                        progressLayout.setVisibility(View.GONE);
                    }
                });


    }

    private void dismissProgress() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

    public void apiFavProperty() {
        progress.show();

        ApiLinks.getClient().create(AddFavPropertyApi.class).post(
                propertyID,
                SessionManager.getAccessToken(this),
                SessionManager.getLanguageID(this)
        ).enqueue(new Callback<AddFavPropertyResponse>() {
            @Override
            public void onResponse(Call<AddFavPropertyResponse> call, Response<AddFavPropertyResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getResponse();
                    String message = response.body().getMessage();
                    int code = response.body().getCode();
                    if (status.equalsIgnoreCase("Success")) {
                        isBusFav = !isBusFav;
                        if (isBusFav) {
                            favButton.setImageResource(R.drawable.favorite_active);
                            PropertyListFragment.propertyLists.get(position).setPropertyFavourite("1");
                            PropertyListFragment.mPropertyListAdapter.notifyItemChanged(position);
                        } else {
                            favButton.setImageResource(R.drawable.favorite);
                            PropertyListFragment.propertyLists.get(position).setPropertyFavourite("0");
                            PropertyListFragment.mPropertyListAdapter.notifyItemChanged(position);
                        }

                    }
                    dismissProgress();
                    Toast.makeText(ActivityPropertyDetails.this, message + " ", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ActivityPropertyDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    dismissProgress();
                    Log.e("Status", "Failed");
                }
            }

            @Override
            public void onFailure(Call<AddFavPropertyResponse> call, Throwable t) {
                Toast.makeText(ActivityPropertyDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                dismissProgress();
            }
        });
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
                            Toast.makeText(ActivityPropertyDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LocalInformationListResponse> call, Throwable t) {
                        Toast.makeText(ActivityPropertyDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void postContactOwner(final Dialog alertDialog, String strName, String strEmail, String strPhone, String message,
                                  String strCountryCode) {
        progress.show();
        alertDialog.dismiss();
        ApiLinks.getClient().create(ContactOwnerPropertyApi.class)
                .post(SessionManager.getAccessToken(ActivityPropertyDetails.this),
                        propertyID,
                        "Property",
                        "Listing",
                        strEmail,
                        strName,
                        strPhone,
                        message,
                        strCountryCode, isMortgaged)
                .enqueue(new Callback<ContactOwnerResponse>() {
                    @Override
                    public void onResponse(Call<ContactOwnerResponse> call, Response<ContactOwnerResponse> response) {
                        try {
                            if (response.isSuccessful()) {
                                String message = response.body().getResponse();
                                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                                    //alertDialog.dismiss();
                                    contactOwnerData = response.body().getContactOwnerData();
                                    alertSuccess(getResources().getString(R.string.thank_you_for_contact),
                                            getResources().getString(R.string.contact_owner_success_message));
                                } else {
                                    Toast.makeText(ActivityPropertyDetails.this, message, Toast.LENGTH_SHORT).show();
                                }

                            } else {

                                Toast.makeText(ActivityPropertyDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                            }
                            alertDialog.dismiss();
                            dismissProgress();
                        } catch (Exception e) {
                            FirebaseCrashlytics.getInstance().recordException(e);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ContactOwnerResponse> call, Throwable t) {
                        dismissProgress();
                        alertDialog.dismiss();
                    }
                });
    }


    //TODO Set Data
    private void setDataToUI() {

        propertyName.setText(propertyDetails.getPropertyName());
        mTitle.setText(propertyDetails.getPropertyName());
        agentId = propertyDetails.getUserID();


        if (propertyDetails.getUserTypeID() != null && !propertyDetails.getUserTypeID().equals("")) {
            if (!propertyDetails.getUserTypeID().equals("1") && propertyDetails.getUserCompanyName() != null
                    && !propertyDetails.getUserCompanyName().equals("")) {
                layAgentDetails.setVisibility(View.VISIBLE);
                tvAgentName.setText(propertyDetails.getUserCompanyName());
                Utils.loadUrlImage(((ImageView) findViewById(R.id.img_agent_logo)), propertyDetails.getUserCompanyLogo(), R.drawable.no_image, false);
            } else
                layAgentDetails.setVisibility(View.GONE);
        }
        if (propertyDetails.getPropertyLat() != null && !propertyDetails.getPropertyLat().equals("") &&
                propertyDetails.getPropertyLong() != null && !propertyDetails.getPropertyLong().equals("")) {
            setMap();
        }
        String loc1 = "", loc2 = "", location = "";
        if (propertyDetails.getProjectName() != null && !propertyDetails.getProjectName().equals("")) {
            loc1 = "in " + propertyDetails.getProjectName() + " ";
        }
        if (propertyDetails.getProjectUserCompanyName() != null && !propertyDetails.getProjectUserCompanyName().equals("")) {
            loc2 = "By " + propertyDetails.getProjectUserCompanyName() + " ";
        }

        if (propertyDetails.getPropertyLocationName() != null && !propertyDetails.getPropertyLocationName().equals("")) {
            location = propertyDetails.getPropertyLocationName();
        } else if (propertyDetails.getPropertyLocality() != null && !propertyDetails.getPropertyLocality().equals("")) {
            location = propertyDetails.getPropertyLocality();
        } else ((TextView) findViewById(R.id.tv_location)).setVisibility(View.GONE);
        loc1 = loc1 + loc2;
        propertyAddress.setText(loc1);
        ((TextView) findViewById(R.id.tv_location)).setText(location);
        String price = "";
        if (propertyDetails.getCurrencyID_5() != null && !propertyDetails.getCurrencyID_5().equalsIgnoreCase("0.00")) {
            price = String.format("%s %s", getString(R.string.currency_symbol), propertyDetails.getPropertyPrice());

            propertyPrice.setText(String.format("%s %s", getString(R.string.currency_symbol), propertyDetails.getPropertyPrice()));

        } else if (propertyDetails.getCurrencyID_1() != null && !propertyDetails.getCurrencyID_1().equalsIgnoreCase("0.00")) {
            price = String.format("%s %s", getString(R.string.iqd_currency_symbol), propertyDetails.getPropertyPrice());

            propertyPrice.setText(String.format("%s %s", getString(R.string.iqd_currency_symbol), propertyDetails.getPropertyPrice()));

        }

        if (propertyDetails.getPropertyPricePerM2() != null && !propertyDetails.getPropertyPricePerM2().isEmpty() && !propertyDetails.getPropertyPricePerM2().equals("0")) {
            if (propertyDetails.getTotal_price().isEmpty() || propertyDetails.getTotal_price() == null || propertyDetails.getTotal_price().equals("0")) {
                priceperm2.setText(getString(R.string.priceinM));

            }

        } else if (propertyDetails.getTotal_price() != null && !propertyDetails.getTotal_price().isEmpty() && !propertyDetails.getTotal_price().equals("0")) {
            if (propertyDetails.getPropertyPricePerM2().isEmpty() || propertyDetails.getPropertyPricePerM2() != null || propertyDetails.getPropertyPricePerM2().equals("0")) {
                priceperm2.setText(getString(R.string.total_price));
            }
        } else {
            priceperm2.setText("");
        }

        if (propertyDetails.getIsOffer() != null && !propertyDetails.getIsOffer().equals("")
                && propertyDetails.getIsOffer().equalsIgnoreCase("Yes")) {
            if (propertyDetails.getPropertyOfferDiscount() != null && !propertyDetails.getPropertyOfferDiscount().equals("")) {
                offerValue.setVisibility(View.VISIBLE);
                offerValue.setText(propertyDetails.getPropertyOfferDiscount() + "% Off");
            }
            if (propertyDetails.getPropertyOfferDiscount() != null && !propertyDetails.getPropertyOfferDiscount().equals("")) {
                String propertyOfferEndTimeString = getString(R.string.offer_ends) + " " +
                        Utils.convertToDateOnlyFormat(propertyDetails.getPropertyOfferEndTime(), true);
                propertyOfferEndTime.setText(propertyOfferEndTimeString);
                propertyOfferEndTime.setVisibility(View.VISIBLE);
            }
            if (propertyDetails.getPropertyOfferPrice() != null && !propertyDetails.getPropertyOfferPrice().equals("")) {
                String propertyOfferPrice = getString(R.string.currency_symbol) + " " + propertyDetails.getPropertyOfferPrice();
                propertyPrice.setText(price + " " + "\n" + propertyOfferPrice, TextView.BufferType.SPANNABLE);

                Spannable spannable = (Spannable) propertyPrice.getText();
                spannable.setSpan(new StrikethroughSpan(), 0, price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(
                        this,
                        R.color.gray
                )), 0, price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } else {
            offerValue.setVisibility(View.GONE);
        }
        String propType = "";
        if (propertyDetails.getPropertyBedRoom() != null && !propertyDetails.getPropertyBedRoom().equals("")) {
            propType = propertyDetails.getPropertyBedRoom() + " " + getString(R.string.bed);
            ((TextView) findViewById(R.id.bed_textView)).setText(propType);
        } else findViewById(R.id.bed_ic_lay).setVisibility(View.GONE);


        if (propertyDetails.getPropertyBathRoom() != null && !propertyDetails.getPropertyBathRoom().equals("")) {
            ((TextView) findViewById(R.id.bath_textView)).setText(propertyDetails.getPropertyBathRoom() + " " + getString(R.string.bath));
        } else findViewById(R.id.bath_ic_lay).setVisibility(View.GONE);


        if (propertyDetails.getPropertyTypeName() != null && !propertyDetails.getPropertyTypeName().equals("")) {
            if (propType.equals("")) {
                propType = propertyDetails.getPropertyTypeName();
            } else {
                propType = propType + ", " + propertyDetails.getPropertyTypeName();
            }

            ((TextView) findViewById(R.id.apartment_textView)).setText(propertyDetails.getPropertyTypeName());
        } else findViewById(R.id.apartment_lay).setVisibility(View.GONE);

        propertyType.setText(propType);
        if (propertyDetails.getPropertyTypeIcon() != null && !propertyDetails.getPropertyTypeIcon().equals(""))
            Utils.loadUrlImage(((ImageView) findViewById(R.id.apartment_image)), propertyDetails.getPropertyTypeIcon(), R.drawable.no_image, false);

        propertyAddedDate.setText(propertyDetails.getPropertyAddedDate());
        propertyKey.setText(propertyDetails.getPropertyKey());

        if (propertyDetails.getPropertyStatus() != null && !propertyDetails.getPropertyStatus().equals("")) {
            propertyStatus.setText(propertyDetails.getPropertyStatus());
        } else {
            propertyStatus.setVisibility(View.INVISIBLE);
        }

        if (propertyDetails.getPropertyCurrentStatus() != null && !propertyDetails.getPropertyCurrentStatus().equals("")) {
            ((TextView) findViewById(R.id.propertyCurrentStatus)).setText(propertyDetails.getPropertyCurrentStatus());
        } else {
            findViewById(R.id.propertyCurrentStatusLay).setVisibility(View.GONE);
        }

        tvCallPropertyOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateCallPropertyOwner();
            }
        });


        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateCallPropertyOwner();

            }
        });


        if (propertyDetails.getBluePrintimageURL() != null && !propertyDetails.getBluePrintimageURL().equals("")) {
            fabBlueprint.show();
            fabBlueprint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<String> bluePrint = new ArrayList<>();
                    bluePrint.add(propertyDetails.getBluePrintimageURL());
                    Intent intent = new Intent(ActivityPropertyDetails.this, GalleryListActivity.class);
                    intent.putExtra("images", bluePrint);
                    intent.putExtra("videos", new ArrayList<String>());
                    intent.putExtra("videoIcons", new ArrayList<String>());
                    intent.putExtra("floor", "yes");
                    startActivity(intent);
                }
            });

        } else {
            fabBlueprint.hide();
        }

        String propertyShowPhone = "";
        if (propertyDetails.getPropertyShowPhone() != null && !propertyDetails.getPropertyShowPhone().equals("")) {
            propertyShowPhone = propertyDetails.getPropertyShowPhone();
            findViewById(R.id.layout_bottom_property_details).setVisibility(View.VISIBLE);
            if (propertyShowPhone.equalsIgnoreCase("Yes")) {
                tvCallPropertyOwner.setVisibility(View.VISIBLE);
                btnCall.setVisibility(View.VISIBLE);
            } else if (propertyShowPhone.equalsIgnoreCase("No"))
                contactOwnerByMail.setVisibility(View.VISIBLE);
            else if (propertyShowPhone.equalsIgnoreCase("Both")) {
                findViewById(R.id.layout_bottom_property_details).setVisibility(View.VISIBLE);
                tvCallPropertyOwner.setVisibility(View.VISIBLE);
                btnCall.setVisibility(View.VISIBLE);
                contactOwnerByMail.setVisibility(View.VISIBLE);
            }
        } else
            findViewById(R.id.layout_bottom_property_details).setVisibility(View.GONE);


        if (propertyDetails.getPropertySoldOutStatus() != null && !propertyDetails.getPropertySoldOutStatus().equals("")
                && propertyDetails.getPropertySoldOutStatus().equals("Yes")) {
            String soldOutTimeString = "Sold Out On " +
                    Utils.convertToDateOnlyFormat(propertyDetails.getPropertySoldOutDate(), true);
            soldOutDate.setText(soldOutTimeString);
            soldOutDate.setVisibility(View.VISIBLE);
            contactOwnerByMail.setVisibility(View.GONE);
            tvCallPropertyOwner.setVisibility(View.GONE);
            btnCall.setVisibility(View.GONE);
        } else
            soldOutDate.setVisibility(View.GONE);


        String possessionStatus = "";
        if (propertyDetails.getPossessionStatus() != null && !propertyDetails.getPossessionStatus().equals("")) {
            possessionStatus = propertyDetails.getPossessionStatus();
        } else {
            propertyPossession.setVisibility(View.INVISIBLE);
        }
        if (possessionStatus.equalsIgnoreCase("Under Construction")) {
            if (propertyDetails.getPossessionDate() != null && !propertyDetails.getPossessionDate().equals("")) {
                possessionStatus = possessionStatus + "\nCompletion Date : " + propertyDetails.getPossessionDate();
            }
        }
        propertyPossession.setText(possessionStatus);


        String propertyBuiltUpArea = "";
        if (propertyDetails.getPropertyBuiltUpArea() != null && !propertyDetails.getPropertyBuiltUpArea().equals("")) {
            propertyBuiltUpArea = propertyDetails.getPropertyBuiltUpArea();
           /* if (propertyDetails.getAreaType() != null && !propertyDetails.getAreaType().equals("")) {
                propertyBuiltUpArea = propertyBuiltUpArea + " " + propertyDetails.getAreaType();
            }*/
        } else if (propertyDetails.getPropertyPlotArea() != null && !propertyDetails.getPropertyPlotArea().equals("")) {
            propertyBuiltUpArea = propertyDetails.getPropertyPlotArea();
           /* if (propertyDetails.getAreaType() != null && !propertyDetails.getAreaType().equals("")) {
                propertyBuiltUpArea = propertyBuiltUpArea + " " + propertyDetails.getAreaType();
            }*/
        } else {
            findViewById(R.id.area_lay).setVisibility(View.GONE);
            findViewById(R.id.area_ic_lay).setVisibility(View.GONE);
        }


        ((TextView) findViewById(R.id.propertyBuiltUpArea)).setText(propertyBuiltUpArea);
        ((TextView) findViewById(R.id.area_textView)).setText(propertyBuiltUpArea + " " + getString(R.string.meter_square));


        int videoSize = savedVideoInfos.size();
        if (videoSize > 0) {
            videoButton.show();
        } else {
            videoButton.hide();
        }
        videoArray.clear();
        arrayListVideoIcons.clear();
        if (videoSize > 0) {

            for (int i = 0; i < videoSize; i++) {
                if (savedVideoInfos.get(i).getPropertyVideoType().equalsIgnoreCase("youtube")) {
                    videoArray.add(savedVideoInfos.get(i).getPropertyVideoYoutubeID());
                    arrayListVideoIcons.add("https://img.youtube.com/vi/" + savedVideoInfos.get(i).getPropertyVideoYoutubeID() + "/hqdefault.jpg");
                }


            }
        }

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityPropertyDetails.this, GalleryListActivity.class);
                intent.putExtra("images", new ArrayList<String>());
                intent.putExtra("videos", videoArray);
                intent.putExtra("videoIcons", arrayListVideoIcons);
                startActivity(intent);
            }
        });
        String propertyUnitPricePerSqftS = propertyDetails.getPricePerUnit();
        if (propertyUnitPricePerSqftS != null && !propertyUnitPricePerSqftS.equals("") && !propertyUnitPricePerSqftS.equals("0") &&
                propertyDetails.getPricePerUnitValue() != null && !propertyDetails.getPricePerUnitValue().equals("") && !propertyDetails.getPricePerUnitValue().equals("0")) {
            propertyUnitPricePerSqftS = getString(R.string.currency_symbol) + " " + propertyDetails.getPricePerUnit() + " per " + propertyDetails.getPricePerUnitValue();
            averagePrice.setText(propertyUnitPricePerSqftS);

        } else averagePrice.setVisibility(View.GONE);
        //propertyUnitPricePerSqftS="";
        // averagePrice.setVisibility(View.INVISIBLE); //TODO

        if (propertyDetails.getPropertyDescription() != null && !propertyDetails.getPropertyDescription().equals("")) {
            propertyDescription.setText(propertyDetails.getPropertyDescription());

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
        /*propertyDescription.post(new Runnable() {
            @Override
            public void run() {
                int lineCount = propertyDescription.getLineCount();
                if (lineCount < 3) {
                    readMore.setVisibility(View.GONE);
                } else {
                    readMore.setVisibility(View.VISIBLE);
                }
            }
        });*/

        if (amenitiesData.size() <= 0) {
            amenitiesLayout.setVisibility(View.GONE);
        }
        if (specificationList.size() <= 0) {
            specificationLayout.setVisibility(View.GONE);
        }

        if (propertyDetails.getPropertyFavourite() == 1) {
            isBusFav = true;
            favButton.setImageResource(R.drawable.favorite_active);
        } else {
            isBusFav = false;
            favButton.setImageResource(R.drawable.favorite);
        }

        ArrayList<BuyPropertiesModel> similarProjects = propertyDetails.getSimilarProperties();
        if (similarProjects != null && similarProjects.size() > 0) {
            // unitTab.setVisibility(View.VISIBLE);
            // setUnitDetails(resUnits);
            setSimilarProjects(similarProjects);
        }// else unitTab.setVisibility(View.GONE);
        /**
         * Done By Lavanya
         */
//        similarPropertyLayout.setVisibility(View.GONE);
        if (imageUrl != null && !imageUrl.equals("")) {
            if (propertyDetails.getPropertyImages().getPropCoverImage() != null && !propertyDetails.getPropertyImages().getPropCoverImage().equals("")) {

            }
        }


        ArrayList<Fragment> arrayList = new ArrayList<>();
        imagesArray.clear();
        ArrayList<PropertyDetailsModel.Images> images = propertyImages.getImages();
        for (int i = 0; i < images.size(); i++) {
            FragmentImageView imF = new FragmentImageView();
            PropertyDetailsModel.Images images1 = images.get(i);
            if (images1.getPropertyImageStatus() != null && !AppConstant.DELETED.equalsIgnoreCase(images1.getPropertyImageStatus())) {
                imF.setData(imageUrl + images1.getPropertyImageName());
                imF.setId(Integer.parseInt(images1.getPropertyImageID()));
                imF.setPreviewPath(imageUrl);
                imF.setLargePath(imageUrl);
                imF.setFileName(images1.getPropertyImageName());
                imagesArray.add(imageUrl + images1.getPropertyImageName());
                arrayList.add(imF);
            }
        }
        if (images.size() == 0) {
            if (propertyImages.getPropCoverImage() != null
                    && !propertyImages.getPropCoverImage().equals("")) {
                FragmentImageView imF = new FragmentImageView();
                imF.setData(imageUrl + propertyImages.getPropCoverImage());
                imF.setPreviewPath(imageUrl);
                imF.setLargePath(imageUrl);
                imF.setFileName(propertyImages.getPropCoverImage());
                imagesArray.add(imageUrl + propertyImages.getPropCoverImage());
                arrayList.add(imF);
            }
        }
        setViewPagerProperties(arrayList);
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


    }

    private void initiateWhatsAppIntent() {
        try {
            String phoneNumber = callingPhoneNo;

            String msg = propertyLink;
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + msg)));
        } catch (Exception e) {
            //whatsapp app not install
        }
    }

    private void initiateCallPropertyOwner() {
        progress.show();
        ApiLinks.getClient().create(CallPropertyOwnerApi.class).post(
                        propertyID, SessionManager.getLanguageID(this))
                .enqueue(new Callback<CallPropertyOwnerResponse>() {
                    @Override
                    public void onResponse(Call<CallPropertyOwnerResponse> call, Response<CallPropertyOwnerResponse> response) {

                        initiateContactOwnerPhoneDialog("Contact");
                        dismissProgress();
                    }

                    @Override
                    public void onFailure(Call<CallPropertyOwnerResponse> call, Throwable t) {
                        initiateContactOwnerPhoneDialog("Contact");
                        dismissProgress();
                    }
                });
    }

    private void initiateContactOwnerPhoneDialog(String value) {
        if (!propertyDetails.getAdditionalPhone_1().isEmpty() && !propertyDetails.getAdditionalPhone_2().isEmpty()) {
            alertDialog = new Dialog(ActivityPropertyDetails.this);
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert factory != null;
            @SuppressLint("InflateParams") View phoneDialogView = factory.inflate(R.layout.alert_contact_owners_phone, null);
            TextView title = phoneDialogView.findViewById(R.id.title);
            TextView tvContact1 = phoneDialogView.findViewById(R.id.tv_contact_1);
            TextView tvContact2 = phoneDialogView.findViewById(R.id.tv_contact_2);
            TextView tvContact3 = phoneDialogView.findViewById(R.id.tv_contact_3);

            ImageView closeContactOwner = phoneDialogView.findViewById(R.id.close_contact_owner);

            if (value.equalsIgnoreCase("WhatsApp")) {

                title.setText(getString(R.string.whatsapp));
                tvContact1.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.whatsapp_icon), null, null, null);
                tvContact2.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.whatsapp_icon), null, null, null);
                tvContact3.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.whatsapp_icon), null, null, null);

            }

            if (!propertyDetails.getAdditionalPhone_1().isEmpty() ) {
                tvContact1.setVisibility(View.VISIBLE);
                tvContact1.setText(propertyDetails.getAdditionalPhone_1());

                if(propertyDetails.getCountryCodeone()!=null && !propertyDetails.getCountryCodeone().isEmpty()){
                    tvContact1.setText(String.format("%s %s", propertyDetails.getCountryCodeone(), propertyDetails.getAdditionalPhone_1()));
                }
            }
            if (!propertyDetails.getAdditionalPhone_2().isEmpty()) {
                tvContact2.setVisibility(View.VISIBLE);
                tvContact2.setText(propertyDetails.getAdditionalPhone_2());

                if(propertyDetails.getCountryCodeTwo()!=null && !propertyDetails.getCountryCodeTwo().isEmpty()){
                    tvContact2.setText(String.format("%s %s", propertyDetails.getCountryCodeTwo(), propertyDetails.getAdditionalPhone_2()));
                }
            }
           /* if(propertyDetails.getAdditionalPhone_1().isEmpty() && propertyDetails.getAdditionalPhone_2().isEmpty()){
                if (!propertyDetails.getOwnerPhone().isEmpty()) {
                    tvContact3.setVisibility(View.VISIBLE);
                    tvContact3.setText(propertyDetails.getOwnerPhone());
                }
            }*/



            closeContactOwner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            tvContact1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callingPhoneNo = String.format("%s %s", propertyDetails.getCountryCodeone(), propertyDetails.getAdditionalPhone_1());
                    alertDialog.dismiss();
                    initiateIntent(value );

                }
            });
            tvContact2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                    callingPhoneNo = String.format("%s %s", propertyDetails.getCountryCodeTwo(), propertyDetails.getAdditionalPhone_2());
                    initiateIntent(value );
                }
            });
            tvContact3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                    callingPhoneNo = propertyDetails.getOwnerPhone();
                    initiateIntent(value );

                }
            });
            alertDialog.setContentView(phoneDialogView);
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.show();
        } else {
            callingPhoneNo = propertyDetails.getOwnerPhone();
            if (!propertyDetails.getAdditionalPhone_1().isEmpty()) {
                tvCallPropertyOwner.setVisibility(View.VISIBLE);
                callingPhoneNo = propertyDetails.getAdditionalPhone_1();

               if(!callingPhoneNo.substring(0,1).equalsIgnoreCase("+")){
                    if(propertyDetails.getCountryCodeone()!=null && !propertyDetails.getCountryCodeone().isEmpty()){
                        callingPhoneNo = propertyDetails.getCountryCodeone()+propertyDetails.getAdditionalPhone_1();
                    }
                }

                initiateIntent(value );
            } else if (!propertyDetails.getAdditionalPhone_2().isEmpty()) {
                tvCallPropertyOwner.setVisibility(View.VISIBLE);
                callingPhoneNo = propertyDetails.getAdditionalPhone_2();

                if(!callingPhoneNo.substring(0,1).equalsIgnoreCase("+")){
                    if(propertyDetails.getCountryCodeTwo()!=null && !propertyDetails.getCountryCodeTwo().isEmpty()){
                    callingPhoneNo = propertyDetails.getCountryCodeTwo()+propertyDetails.getAdditionalPhone_2();
                }
                }

                initiateIntent(value );
            } else if (!propertyDetails.getOwnerPhone().isEmpty()) {
                tvCallPropertyOwner.setVisibility(View.VISIBLE);
                callingPhoneNo = propertyDetails.getOwnerPhone();
                if(!callingPhoneNo.substring(0,1).equalsIgnoreCase("+")){
                    if(propertyDetails.getUserCountryCode()!=null && !propertyDetails.getUserCountryCode().isEmpty()){
                        callingPhoneNo = propertyDetails.getUserCountryCode()+propertyDetails.getOwnerPhone();
                        Log.d("test",callingPhoneNo);
                    }
                }
                initiateIntent(value);

            }

        }
    }

    private void initiateIntent(String value) {
        if (value.equalsIgnoreCase("WhatsApp")) {
            initiateWhatsAppIntent();
        } else {
            initiateCallIntent(callingPhoneNo);
        }
    }

    private void initiateWhatsappClick() {

        progress.show();
        ApiLinks.getClient().create(WhatsappClickApi.class).post(propertyID).enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                initiateContactOwnerPhoneDialog("WhatsApp");
                dismissProgress();

            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                initiateContactOwnerPhoneDialog("WhatsApp");

                dismissProgress();

            }
        });
    }

    private void initiateCallIntent(String ownerPhoneNo) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_VIEW);
            callIntent.setData(Uri.parse("tel:" + ownerPhoneNo));
            startActivity(callIntent);
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
        }
    }

    private void setViewPagerProperties(ArrayList<Fragment> arrayList) {
        adapterSlider = new AdapterSlider(getSupportFragmentManager(), arrayList);
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
        viewPagerTimer = new Timer(); // This will create a new Thread
        viewPagerTimer.schedule(new TimerTask() { // task to be scheduled
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
                Intent intent = new Intent(ActivityPropertyDetails.this, GalleryListActivity.class);
                intent.putExtra("images", imagesArray);
                intent.putExtra("title", propertyDetails.getPropertyName());
                intent.putExtra("videos", videoArray);
                intent.putExtra("videoIcons", arrayListVideoIcons);
                startActivity(intent);
            }
        });
        tabLayout.setVisibility(adapterSlider.getCount() > 1 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void changeOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void setSimilarProjects(ArrayList<BuyPropertiesModel> buyPropertiesModels) {
        similarRecyclerView = findViewById(R.id.project_gallery_horizontel);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(R.string.similar_properties);
        title.setVisibility(View.VISIBLE);
        //String imagePath = propertyDetails.getImagePath();
        String imagePath = propertyDetails.getSimilerImageUrl();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ActivityPropertyDetails.this, LinearLayoutManager.HORIZONTAL, false);

        PropertySimilarAdapter projectSimilarAdapter = new PropertySimilarAdapter(ActivityPropertyDetails.this, imagePath, buyPropertiesModels, new PropertySimilarAdapter.OnItemClickListener() {
            @Override
            public void onContactClick(BuyPropertiesModel item) {
                alertContactOwner();
            }

            @Override
            public void onItemClick(BuyPropertiesModel item) {
                Intent intent = new Intent(ActivityPropertyDetails.this,
                        ActivityPropertyDetails.class);
                intent.putExtra("propertyID", item.getPropertyID());
                intent.putExtra("position", "0");
                startActivity(intent);
                finish();

            }
        });
        similarRecyclerView.setLayoutManager(mLayoutManager);
        similarRecyclerView.setAdapter(projectSimilarAdapter);
    }

    private void declarations() {
        propertyDetails = new PropertyDetailsModel();

        noData = findViewById(R.id.no_data_available);
        progressLayout = (LinearLayout) findViewById(R.id.progress_layout_property_details);
        favButton = (FloatingActionButton) findViewById(R.id.fab_fav);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SessionManager.getLoginStatus(ActivityPropertyDetails.this)) {
                    apiFavProperty();
                } else {
                    startActivity(new Intent(ActivityPropertyDetails.this, ActivityLoginEmail.class));
                }

            }
        });
        locButton = (FloatingActionButton) findViewById(R.id.fab_loc);
        videoButton = (FloatingActionButton) findViewById(R.id.fab_video);
        fabBlueprint = (FloatingActionButton) findViewById(R.id.fab_blueprint);
        amenitiesLayout = (LinearLayout) findViewById(R.id.amenities_lay);
        tvTotalViews = findViewById(R.id.tv_total_views);
        specificationLayout = (LinearLayout) findViewById(R.id.specification_lay);
        layAgentDetails = (LinearLayout) findViewById(R.id.lay_agent_logo);
        similarPropertyLayout = (LinearLayout) findViewById(R.id.layout_similar_properties_content_property_details);
        propertyName = (TextView) findViewById(R.id.property_name_content_property_details);
        offerValue = (TextView) findViewById(R.id.offerValue);
        soldOutDate = (TextView) findViewById(R.id.soldOutDate);
        propertyOfferEndTime = (TextView) findViewById(R.id.propertyOfferEndTime);
        propertyAddress = (TextView) findViewById(R.id.property_address_content_property_details);
        propertyKey = (TextView) findViewById(R.id.propertyKey);
        propertyAddedDate = (TextView) findViewById(R.id.propertyAddedDate);
        propertyPrice = (TextView) findViewById(R.id.price_content_property_details);
        priceperm2 = (TextView) findViewById(R.id.tv_ppm2);
        propertyType = (TextView) findViewById(R.id.property_type_content_property_details);
        progress = new DialogProgress(ActivityPropertyDetails.this);
        propertyStatus = (TextView) findViewById(R.id.property_status_content_property_details);
        propertyPossession = (TextView) findViewById(R.id.possession_date_content_property_details);
        averagePrice = (TextView) findViewById(R.id.avg_price_content_property_details);
        propertyDescription = (ReadMoreTextView) findViewById(R.id.description_content_property_details);
        recyclerAmenities = (RecyclerView) findViewById(R.id.recycler_property_amenities);
        tvCallPropertyOwner = (TextView) findViewById(R.id.tv_call_property_owner);
        contactOwnerByMail = (TextView) findViewById(R.id.contactButton);
        tvAgentName = (TextView) findViewById(R.id.tv_agent_name);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
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
        btnCall = findViewById(R.id.btn_call);
        ivAgentLogo = findViewById(R.id.img_agent_logo);
        button_sell_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SessionManager.getLoginStatus(ActivityPropertyDetails.this)) {
                    startActivity(new Intent(ActivityPropertyDetails.this, PostPropertyPage01Activity.class));
                } else {
                    startActivity(new Intent(ActivityPropertyDetails.this, ActivityLoginEmail.class));
                }
            }
        });
        locButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View targetView = findViewById(R.id.map_property_lay);
                targetView.getParent().requestChildFocus(targetView, targetView);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityPropertyDetails.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setAutoMeasureEnabled(true);
        specificationAdapter = new AdapterPropertySpecification(specificationList);
        recyclerSpecification.setLayoutManager(linearLayoutManager);
        //recyclerSpecification.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerSpecification.setNestedScrollingEnabled(false);
        recyclerSpecification.setHasFixedSize(false);
        recyclerSpecification.setAdapter(specificationAdapter);
        specificationList.addAll(specList1);
        specificationAdapter.notifyDataSetChanged();
        isSpecificationExpanded = false;
        recyclerSpecification.clearFocus();

        similarProperties = findViewById(R.id.tv_similar_properties);
        /*similarPropertiesData = new ArrayList<>();
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(ActivityPropertyDetails.this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager1.setAutoMeasureEnabled(true);
        similarPropertyAdapter = new AdapterPropertyListing(ActivityPropertyDetails.this, similarPropertiesData);
        recyclerSimilarProperties.setLayoutManager(linearLayoutManager1);
        recyclerSimilarProperties.setNestedScrollingEnabled(false);
        recyclerSimilarProperties.setHasFixedSize(false);
        recyclerSimilarProperties.setAdapter(similarPropertyAdapter);
        similarPropertyAdapter.notifyDataSetChanged();*/
//        recyclerSimilarProperties.clearFocus();

        amenitiesData = new ArrayList<>();
        amenitiesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recyclerAmenities.setLayoutManager(amenitiesLayoutManager);
        recyclerAmenities.setNestedScrollingEnabled(false);
        recyclerAmenities.setHasFixedSize(true);
        recyclerAmenities.clearFocus();


        layAgentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityPropertyDetails.this, ActivityAgentDetails.class);
                intent.putExtra(AppConstant.AGENT_ID, agentId);
                intent.putExtra(AppConstant.I_SUB_AGENT_FOR_PARENT, true);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ActivityPropertyDetails.this).toBundle());
            }
        });
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
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, propertyDetails.getPropertyName());
            sendIntent.putExtra(Intent.EXTRA_TEXT, propertyLink);

        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }
        startActivity(sendIntent);
    }


    //TODO Alert
    private void alertMakeAnOffer() {
        alertDialog = new Dialog(ActivityPropertyDetails.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_make_an_offer_popup, null);
        ImageView close = alert_layout.findViewById(R.id.close_make_an_offer);
        final EditText fullName = alert_layout.findViewById(R.id.editText_user_full_name_make_an_offer);
        final EditText email = alert_layout.findViewById(R.id.editText_user_email_address_make_an_offer);
        final EditText phone = alert_layout.findViewById(R.id.editText_phone_make_an_offer);
        final EditText offerPrice = alert_layout.findViewById(R.id.editText_offer_price_make_an_offer);
        final CountryCodePicker codePicker = alert_layout.findViewById(R.id.country_code_make_an_offer);
        codePicker.registerCarrierNumberEditText(phone);
        final EditText message = alert_layout.findViewById(R.id.editText_user_comments_make_an_offer);
        CheckBox cbMortgage = alert_layout.findViewById(R.id.checkBox_info_mortgage_make_an_offer);
        cbMortgage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isOwnerMortgage = "Yes";
                } else {
                    isOwnerMortgage = "No";
                }
            }
        });
        final CheckBox cbTerms = alert_layout.findViewById(R.id.checkBox_terms_conditions_make_an_offer);
        TextView submit = alert_layout.findViewById(R.id.button_submit_make_an_offer);
        cbTerms.setText(Html.fromHtml(checkBoxText));
        cbTerms.setMovementMethod(LinkMovementMethod.getInstance());
        codePicker.setCountryForNameCode(GlobalValues.countryCode);
        offerPrice.addTextChangedListener(new NumberTextWatcherForThousand(offerPrice));
        if (SessionManager.getLoginStatus(ActivityPropertyDetails.this)) {
            fullName.setText(SessionManager.getFullName(ActivityPropertyDetails.this));
            email.setText(SessionManager.getEmail(ActivityPropertyDetails.this));
            phone.setText(SessionManager.getPhone(ActivityPropertyDetails.this));
            codePicker.setCountryForNameCode(SessionManager.getCountryCode(ActivityPropertyDetails.this).toUpperCase());
            offerPrice.clearFocus();
            offerPrice.requestFocus();
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strMessage = message.getText().toString().trim();
                String strCountryCode = codePicker.getSelectedCountryNameCode().toLowerCase();
                String strName = fullName.getText().toString();
                String strEmail = email.getText().toString();
                String strPhone = phone.getText().toString();
                String strOfferPrice = offerPrice.getText().toString().trim();

                if (fullName.getText().toString().trim().equals("")) {
                    fullName.clearFocus();
                    fullName.requestFocus();
                    Toast.makeText(ActivityPropertyDetails.this, getString(R.string.please_enter_name), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    strName = fullName.getText().toString();
                    if (!Utils.isValidName(strName)) {
                        Toast.makeText(ActivityPropertyDetails.this, getString(R.string.please_enter_valid_name),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (email.getText().toString().trim().equals("")) {
                    email.clearFocus();
                    email.requestFocus();
                    Toast.makeText(ActivityPropertyDetails.this, getString(R.string.please_enter_email_address), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    strEmail = email.getText().toString();
                    if (TextUtils.isEmpty(strEmail) || !Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                        Toast.makeText(ActivityPropertyDetails.this, getString(R.string.please_enter_valid_email_address), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            /*    if (!validatePhone(phone.getText().toString(), codePicker)) {
                    return;
                }
                strPhone = phone.getText().toString();*/
                if (phone.getText().toString().trim().equals("")) {
                    phone.clearFocus();
                    phone.requestFocus();
                    Toast.makeText(ActivityPropertyDetails.this, getString(R.string.please_enter_phone_number), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    strPhone = phone.getText().toString();
                    if (!Utils.isValidMobile(strPhone)) {
                        Toast.makeText(ActivityPropertyDetails.this,
                                getString(R.string.valid_mobile_number), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (strOfferPrice.equals("")) {
                    offerPrice.clearFocus();
                    offerPrice.requestFocus();
                    Toast.makeText(ActivityPropertyDetails.this, getString(R.string.please_enter_offer_price), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (strMessage.equals("")) {
                    message.clearFocus();
                    message.requestFocus();
                    Toast.makeText(ActivityPropertyDetails.this, getString(R.string.comment_required), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!cbTerms.isChecked()) {
                    Toast.makeText(ActivityPropertyDetails.this, getString(R.string.accept_terms_conditions), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utils.NoInternetConnection(ActivityPropertyDetails.this)) {
                    postMakeAnOffer(alertDialog, strName, strEmail, strOfferPrice, strPhone, strCountryCode, strMessage, isOwnerMortgage);
                } else {
                    Toast.makeText(ActivityPropertyDetails.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void postMakeAnOffer(final Dialog alertDialog, String strName, String strEmail, String strOfferPrice,
                                 String strPhone, String strCountryCode, String strMessage, String isMortgaged) {
        alertDialog.dismiss();
        progress.show();
        ApiLinks.getClient().create(MakeAnOfferApi.class).post(strName, strEmail, "Yes", strOfferPrice,
                        strPhone, strCountryCode, strMessage, propertyID, isMortgaged)
                .enqueue(new Callback<MakeAnOfferResponse>() {
                    @Override
                    public void onResponse(Call<MakeAnOfferResponse> call, Response<MakeAnOfferResponse> response) {
                        if (response.isSuccessful()) {
                            dismissProgress();
                            String message = response.body().getMessage();
                            if (response.body().getResult().equals("Success")) {
                                alertSuccess(getResources().getString(R.string.thank_you),
                                        getResources().getString(R.string.make_an_offer_success_message));
                            } else {
                                Toast.makeText(ActivityPropertyDetails.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            dismissProgress();
                            Toast.makeText(ActivityPropertyDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MakeAnOfferResponse> call, Throwable t) {
                        dismissProgress();
                    }
                });
    }

    private void alertContactOwner() {
        alertDialog = new Dialog(ActivityPropertyDetails.this);
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
        final TextView title = alert_layout.findViewById(R.id.title);
        title.setText(R.string.email);
        final CheckBox cbMortgage = alert_layout.findViewById(R.id.checkBox_info_mortgage_contact_owner);

        if (purpose.compareToIgnoreCase("Rent") == 0) {
            cbMortgage.setVisibility(View.GONE);
        }
        cbMortgage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cbMortgage.isChecked()) {
                    isMortgaged = "Yes";
                } else {
                    isMortgaged = "No";
                }
            }
        });

        final CheckBox cbTerms = alert_layout.findViewById(R.id.checkBox_terms_conditions_contact_owner);
        TextView submit = alert_layout.findViewById(R.id.button_submit_contact_owner);
        // message.setVisibility(View.GONE);

        cbTerms.setText(Html.fromHtml(checkBoxText));
        cbTerms.setMovementMethod(LinkMovementMethod.getInstance());


        // cbTerms.setText(Html.fromHtml(checkBoxText));
        codePicker.setCountryForNameCode(GlobalValues.countryCode);
        if (SessionManager.getLoginStatus(ActivityPropertyDetails.this)) {
            fullName.setText(SessionManager.getFullName(ActivityPropertyDetails.this));
            email.setText(SessionManager.getEmail(ActivityPropertyDetails.this));
            phone.setText(SessionManager.getPhone(ActivityPropertyDetails.this));
            codePicker.setCountryForNameCode(SessionManager.getCountryCode(ActivityPropertyDetails.this));
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
                String strCountryCode = codePicker.getSelectedCountryNameCode().toLowerCase();
                String strName;
                String strEmail;
                String strPhone;

                if (fullName.getText().toString().trim().equals("")) {
                    fullName.clearFocus();
                    fullName.requestFocus();
                    Toast.makeText(ActivityPropertyDetails.this, getString(R.string.please_enter_name), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    strName = fullName.getText().toString();
                    if (!Utils.isValidName(strName)) {
                        Toast.makeText(ActivityPropertyDetails.this, getString(R.string.please_enter_valid_name),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (email.getText().toString().equals("")) {
                    email.clearFocus();
                    email.requestFocus();
                    Toast.makeText(ActivityPropertyDetails.this, getString(R.string.please_enter_email_address), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    strEmail = email.getText().toString();
                    if (TextUtils.isEmpty(strEmail) || !Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                        Toast.makeText(ActivityPropertyDetails.this, getString(R.string.please_enter_valid_email_address), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
         /*       if (!validatePhone(phone.getText().toString(), codePicker)) {
                    return;
                }
                strPhone = phone.getText().toString();*/
                if (phone.getText().toString().equals("")) {
                    phone.clearFocus();
                    phone.requestFocus();
                    Toast.makeText(ActivityPropertyDetails.this, getString(R.string.please_enter_phone_number), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    strPhone = phone.getText().toString();
                    if (!Utils.isValidMobile(strPhone)) {
                        Toast.makeText(ActivityPropertyDetails.this,
                                getString(R.string.valid_mobile_number), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


                if (!cbTerms.isChecked()) {
                    Toast.makeText(ActivityPropertyDetails.this, getString(R.string.accept_terms_conditions), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utils.NoInternetConnection(ActivityPropertyDetails.this)) {
                    postContactOwner(alertDialog, strName, strEmail, phone.getText().toString(), message.getText().toString(), strCountryCode);
                } else {
                    Toast.makeText(ActivityPropertyDetails.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
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
        alertDialog = new Dialog(ActivityPropertyDetails.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater factory = (LayoutInflater) ActivityPropertyDetails.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_success_popup, null);
        ImageView close = alert_layout.findViewById(R.id.close_success_dialog);
        TextView title = alert_layout.findViewById(R.id.title_alert_success_popup);
        TextView message = alert_layout.findViewById(R.id.message_alert_success_popup);

        TextView tv_property_ID = alert_layout.findViewById(R.id.tv_property_ID);
        TextView type_text = alert_layout.findViewById(R.id.type_text);
        TextView tv_thankyou_name = alert_layout.findViewById(R.id.tv_thankyou_name);
        TextView tv_thankyou_mobile = alert_layout.findViewById(R.id.tv_thankyou_mobile);
        TextView title_alert_success_popup = alert_layout.findViewById(R.id.title_alert_success_popup);
        TextView message_alert_success_popup = alert_layout.findViewById(R.id.message_alert_success_popup);
        LinearLayout enquiryDetails = alert_layout.findViewById(R.id.enquiryDetails);

        if (contactOwnerData != null) {
            enquiryDetails.setVisibility(View.VISIBLE);
            title_alert_success_popup.setVisibility(View.GONE);
            message_alert_success_popup.setVisibility(View.GONE);
            tv_property_ID.setText(contactOwnerData.getKey().trim());
            tv_thankyou_name.setText(contactOwnerData.getOwnerName().trim());
            tv_thankyou_mobile.setText(contactOwnerData.getUserPhone().trim());
            String temp1 = "<a href='" + ApiLinks.BASE_URL_CONTACT_US + "' > <b>Click Here</b> </a>";
            String temp = getResources().getString(R.string.do_not_disclose) + temp1;
            type_text.setText(Html.fromHtml(temp));
            type_text.setMovementMethod(LinkMovementMethod.getInstance());
        }


        title.setText(successTitle);
        message.setText(successMessage);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
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
                ListDialogBox dialogBox = new ListDialogBox(ActivityPropertyDetails.this, nameList, "Local Information", "property");
                //noinspection ConstantConditions
                dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogBox.show();
                dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (selectedPosition != 100) {
                            if (googleMap != null) {
                                googleMap.clear();
                            }
                            setMap();
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
            List<LatLng> views = new ArrayList<>();
            for (int i = 0; i < localInformationList.size(); i++) {
                BitmapDrawable bitmapdraw = Utils.getBitmapDrawableLocal(this, selectedTypeName, i);
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
        ApiLinks.getClient().create(LocalInformationDataApi.class).post(
                        selectedTypeId,
                        propertyDetails.getPropertyLat(),
                        propertyDetails.getPropertyLong())
                .enqueue(new Callback<LocalInformationDataResponse>() {
                    @Override
                    public void onResponse(Call<LocalInformationDataResponse> call, Response<LocalInformationDataResponse> response) {
                        if (response.body().getResponse().equalsIgnoreCase("Success")) {
                            dismissProgress();
                            localInformationList = response.body().getLocalInformation();
                            setLocalInformation();
                        } else {
                            dismissProgress();
                            Toast.makeText(ActivityPropertyDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LocalInformationDataResponse> call, Throwable t) {
                        dismissProgress();
                        Toast.makeText(ActivityPropertyDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void setMap() {
        try {
            int height = 35;
            int width = 35;
            BitmapDrawable bitmapDrawable = (BitmapDrawable) AppCompatResources.getDrawable(this, getMarker());
            Bitmap b = null;
            if (bitmapDrawable != null) {
                b = bitmapDrawable.getBitmap();
            }
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
            Double lat = Double.parseDouble(propertyDetails.getPropertyLat());
            Double lon = Double.parseDouble(propertyDetails.getPropertyLong());
            views = new LatLng(lat, lon);
            markerMain = googleMap.addMarker(new MarkerOptions()
                    .position(views)
                    .title(propertyDetails.getPropertyName())
                    .snippet(propertyDetails.getPropertyCityName())
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

        if (propertyDetails.getPropertyLat() != null && !propertyDetails.getPropertyLat().equals("") &&
                propertyDetails.getPropertyLong() != null && !propertyDetails.getPropertyLong().equals("")) {
            setMap();
        }

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
            Toast.makeText(ActivityPropertyDetails.this, getString(R.string.valid_mobile_number), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!validateMobile(codePicker, mobile)) {
                Toast.makeText(ActivityPropertyDetails.this, getString(R.string.valid_mobile_number),
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
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.rl_property_details_parent_layout));
        if (!Utils.NoInternetConnection(ActivityPropertyDetails.this)) {
            progress.show();

            getPropertyDetails();
            getLocalList();
            if (viewPagerTimer != null) {
                viewPagerTimer.cancel();
            }
        } else {
            Toast.makeText(ActivityPropertyDetails.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }


}
