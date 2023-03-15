package com.paya.paragon.activity.postProperty;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.paya.paragon.R;
import com.paya.paragon.activity.GalleryListActivity;
import com.paya.paragon.activity.dashboard.ActivityMyProperties;
import com.paya.paragon.activity.dashboard.ActivityYouTubeVideo;
import com.paya.paragon.adapter.AdapterAmenities;
import com.paya.paragon.adapter.AdapterPropertySpecification;
import com.paya.paragon.adapter.AdapterSlider;
import com.paya.paragon.api.FetchLocalInformationData.LocalInformationData;
import com.paya.paragon.api.FetchLocalInformationData.LocalInformationDataApi;
import com.paya.paragon.api.FetchLocalInformationData.LocalInformationDataResponse;
import com.paya.paragon.api.localInformationList.LocalInformationListApi;
import com.paya.paragon.api.localInformationList.LocalInformationListData;
import com.paya.paragon.api.localInformationList.LocalInformationListResponse;
import com.paya.paragon.api.propertyAddSendMail.PropertySendMailApi;
import com.paya.paragon.api.propertyAddSendMail.PropertySendMailResponse;
import com.paya.paragon.api.propertyDetails.AmenitiesModel;
import com.paya.paragon.api.propertyDetails.PropertyDetailsLoginedApi;
import com.paya.paragon.api.propertyDetails.PropertyDetailsModel;
import com.paya.paragon.api.propertyDetails.PropertyDetailsResponse;
import com.paya.paragon.api.propertyDetails.SpecificationModel;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.ExtendedViewPager;
import com.paya.paragon.utilities.FragmentImageView;
import com.paya.paragon.utilities.ListDialogBox;
import com.paya.paragon.utilities.PopupImageViewer;
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
public class ActivityPostPropertyPreview extends AppCompatActivity implements OnMapReadyCallback, PopupImageViewer.PreviewCallBack {

    boolean isReadMoreClicked = false, isSpecificationExpanded = false;
    private NestedScrollView nestedScrollView;
    TextView readMore, moreSpecification, tvLocal, mTitle;
    TextView propertyName, propertyAddress, propertyPrice, propertyKey, propertyAddedDate, propertyType,priceperm2;
    TextView propertyStatus, propertyPossession, averagePrice, propertyDescription;
    TextView modify, confirm;
    LinearLayout progressLayout, amenitiesLayout, similarPropertyLayout, layContentPreview,amenitiesOverallLayout;
    LinearLayout layoutBottomAction;
    FloatingActionButton locButton, videoButton, fabBlueprint;
    RecyclerView recyclerSpecification, recyclerAmenities;

    String propertyID = "", imageUrl = "", selectedTypeId = "", selectedTypeName = "";
    ArrayList<SpecificationModel> specificationList, specList1, specList2;
    ArrayList<AmenitiesModel> amenitiesData;

    AdapterPropertySpecification specificationAdapter;
    AdapterAmenities amenitiesAdapter;

    private GoogleMap googleMap;
    public Dialog alertDialog;
    DialogProgress progress;
    PropertyDetailsModel propertyDetails;
    PropertyDetailsModel.PropertyImages propertyImages;

    private ExtendedViewPager productImageViewPager;
    private AdapterSlider adapterSlider;

    List<String> nameList;
    public static int selectedPosition = 100;

    List<LocalInformationData> localInformationList;
    List<LocalInformationListData> localList;
    Marker markerMain = null, markerSub = null;
    private DialogProgress mLoadingDialog;
    LatLng views;
    private String isEdit;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000; // time in milliseconds between successive task executions.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post_property_preview);
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.rl_post_property_review_parent_layout));
        initializeMap();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);

        mTitle.setTypeface(mTitle.getTypeface(), Typeface.BOLD);

        propertyID = getIntent().getStringExtra("propertyID");

        isEdit = getIntent().getStringExtra("isEdit");

        declarations();

        setUpMap();

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

        if (!Utils.NoInternetConnection(ActivityPostPropertyPreview.this)) {
            getPropertyDetails();
            getLocalList();
        } else {
            Toast.makeText(ActivityPostPropertyPreview.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentModify = new Intent(ActivityPostPropertyPreview.this,
                        PostPropertyPage01Activity.class);
                intentModify.putExtra("action", "edit");
                intentModify.putExtra("propertyId", propertyID);
                startActivity(intentModify);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPropertyMail();
            }
        });

    }

    private void initializeMap() {
        MapsInitializer.initialize(getApplicationContext());
    }

    private void sendPropertyMail() {
        progress.show();
        ApiLinks.getClient().create(PropertySendMailApi.class).post(
                propertyID,
                SessionManager.getAccessToken(this),
                isEdit)
                .enqueue(new Callback<PropertySendMailResponse>() {
                    @Override
                    public void onResponse(Call<PropertySendMailResponse> call, Response<PropertySendMailResponse> response) {
                        progress.dismiss();
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String status = response.body().getResponse();
                            if (status.equalsIgnoreCase("Success")) {
                                Intent intentConfirm = new Intent(ActivityPostPropertyPreview.this,
                                        ActivityMyProperties.class);
                                intentConfirm.putExtra("confirm", "true");
                                intentConfirm.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intentConfirm);
                                finish();
                            } else if (response.body().getCode() != null && response.body().getCode() == 409) {
                                Utils.showAlertLogout(ActivityPostPropertyPreview.this, message);
                            } else {
                                Toast.makeText(ActivityPostPropertyPreview.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<PropertySendMailResponse> call, Throwable t) {
                        Toast.makeText(ActivityPostPropertyPreview.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                });
    }

    //TODO API Calls
    private void getPropertyDetails() {
        progress.show();
        Log.d("myproperties",propertyID+"");

        ApiLinks.getClient().create(PropertyDetailsLoginedApi.class).post(propertyID,
                "" + SessionManager.getLanguageID(this),
                SessionManager.getAccessToken(ActivityPostPropertyPreview.this))
                .enqueue(new Callback<PropertyDetailsResponse>() {
                    @Override
                    public void onResponse(Call<PropertyDetailsResponse> call,
                                           Response<PropertyDetailsResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String status = response.body().getResponse();
                            if (status.equalsIgnoreCase("Success")) {
                                propertyDetails = response.body().getData().getProperty();
                                imageUrl = propertyDetails.getPropertyImages().getImageUrl();
                                propertyImages = propertyDetails.getPropertyImages();
                                amenitiesData = new ArrayList<>();
                                amenitiesData.addAll(response.body().getData().getProperty().getLstAmenities());
                                if(amenitiesData.size()<=0){
                                    amenitiesOverallLayout.setVisibility(View.GONE);
                                }else {
                                    amenitiesOverallLayout.setVisibility(View.VISIBLE);

                                    amenitiesAdapter = new AdapterAmenities(ActivityPostPropertyPreview.this, amenitiesData, propertyDetails.getAmenitiesImgPath());
                                    recyclerAmenities.setAdapter(amenitiesAdapter);
                                    recyclerAmenities.scrollToPosition(0);
                                    amenitiesAdapter.notifyDataSetChanged();
                                    recyclerAmenities.clearFocus();
                                }

                                specificationList = new ArrayList<>();
                                specificationList.addAll(response.body().getData().getProperty().getPropertyAtrributes());
                                specificationAdapter = new AdapterPropertySpecification(specificationList);
                                recyclerSpecification.setAdapter(specificationAdapter);
                                recyclerSpecification.scrollToPosition(0);
                                specificationAdapter.notifyDataSetChanged();
                                recyclerSpecification.clearFocus();
                                setDataToUI();
                            } else {
                                Toast.makeText(ActivityPostPropertyPreview.this, message, Toast.LENGTH_SHORT).show();
                            }
                            progress.dismiss();
                        } else {
                            Toast.makeText(ActivityPostPropertyPreview.this, "No Response", Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }
                        progressLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<PropertyDetailsResponse> call, Throwable t) {
                        Toast.makeText(ActivityPostPropertyPreview.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                        progressLayout.setVisibility(View.GONE);
                    }
                });


    }


    //TODO Set ShortListedCountData
    private void setDataToUI() {
        propertyName.setText(propertyDetails.getPropertyName());
        mTitle.setText(propertyDetails.getPropertyName());
        if (propertyDetails.getPropertyLat() != null && !propertyDetails.getPropertyLat().equals("") &&
                propertyDetails.getPropertyLong() != null && !propertyDetails.getPropertyLong().equals("")) {
            setMap();
        }
        String loc1 = "", loc2 = "", location = "";
        if (propertyDetails.getProjectName() != null && !propertyDetails.getProjectName().equals("")) {
            loc1 = "in " + propertyDetails.getProjectName() + " ";
        }
        if (propertyDetails.getProjectUserCompanyName() != null && propertyDetails.getProjectUserCompanyName().equals("")) {
            loc2 = "By " + propertyDetails.getProjectUserCompanyName() + " ";
        }

        if (propertyDetails.getPropertyLocationName() != null && !propertyDetails.getPropertyLocationName().equals("")) {
            location = propertyDetails.getPropertyLocationName();
        } else if (propertyDetails.getPropertyLocality() != null && !propertyDetails.getPropertyLocality().equals("")) {
            location = propertyDetails.getPropertyLocality();
        } else ((TextView) findViewById(R.id.tv_location)).setVisibility(View.GONE);
        //loc1 = loc1 + loc2;
        propertyAddress.setText(loc1);
        ((TextView) findViewById(R.id.tv_location)).setText(location);
        String price = getString(R.string.currency_symbol) + " " + propertyDetails.getPropertyPrice();;
        if(!propertyDetails.getCurrencyID_5().equalsIgnoreCase("0.00")){
            price = getString(R.string.currency_symbol)+" "+propertyDetails.getPropertyPrice();

        }else if(!propertyDetails.getCurrencyID_1().equalsIgnoreCase("0.00")){
            price = getString(R.string.iqd_currency_symbol) + " " + propertyDetails.getPropertyPrice();
        }

        propertyPrice.setText(price);
        if(propertyDetails.getPropertyPricePerM2() != null && !propertyDetails.getPropertyPricePerM2().equals("0")){
            priceperm2.setText(getString(R.string.priceinM));
        }else {
            priceperm2.setText(getString(R.string.total_price));
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
        if (propertyDetails.getBluePrintimageURL() != null && !propertyDetails.getBluePrintimageURL().equals("")) {
            fabBlueprint.show();
            fabBlueprint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<String> bluePrint = new ArrayList<>();
                    bluePrint.add(propertyDetails.getBluePrintimageURL());
                    Intent intent = new Intent(ActivityPostPropertyPreview.this, GalleryListActivity.class);
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
        if (propertyDetails.getPossessionStatus() != null && !propertyDetails.getPossessionStatus().equals("")) {
            propertyPossession.setText(propertyDetails.getPossessionStatus());
        } else {
            propertyPossession.setVisibility(View.INVISIBLE);
        }


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

            //  findViewById(R.id.area_lay).setVisibility(View.GONE);
            //findViewById(R.id.area_ic_lay).setVisibility(View.GONE);
        }


        // ((TextView)findViewById(R.id.propertyBuiltUpArea)).setText(propertyBuiltUpArea);
        ((TextView) findViewById(R.id.area_textView)).setText(propertyBuiltUpArea + " "+getString(R.string.meter_square));

        int size = propertyDetails.getPropertyVideos().size();
        if (size > 0) {
            videoButton.show();
        } else {
            videoButton.hide();
        }
        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent video = new Intent(ActivityPostPropertyPreview.this, ActivityYouTubeVideo.class);
                video.putExtra("id", propertyDetails.getPropertyVideos().get(0).getPropertyVideoYoutubeID());
                startActivity(video);
            }
        });
       /* String propertyUnitPricePerSqftS = propertyDetails.getPropertySqrFeet();
        if (propertyUnitPricePerSqftS != null && !propertyUnitPricePerSqftS.equals("") && !propertyUnitPricePerSqftS.equals("0")) {
            propertyUnitPricePerSqftS =  propertyUnitPricePerSqftS +  " " + getString(R.string.meter_square);
            averagePrice.setText(propertyUnitPricePerSqftS);
            averagePrice.setVisibility(View.VISIBLE);

        }*/


        if (propertyDetails.getPropertyDescription() != null && !propertyDetails.getPropertyDescription().equals("")) {
            propertyDescription.setText(propertyDetails.getPropertyDescription());
        } else {
            propertyDescription.setVisibility(View.GONE);
            readMore.setVisibility(View.GONE);
        }
        propertyDescription.post(new Runnable() {
            @Override
            public void run() {
                int lineCount = propertyDescription.getLineCount();
                if (lineCount < 3) {
                    readMore.setVisibility(View.GONE);
                } else {
                    readMore.setVisibility(View.VISIBLE);
                }
            }
        });

        if (propertyDetails.getPropertyLat() != null && propertyDetails.getPropertyLong() != null
                && !propertyDetails.getPropertyLat().equals("") && !propertyDetails.getPropertyLong().equals("")) {
            setMap();
        }

        if (amenitiesData.size() == 0) {
            amenitiesLayout.setVisibility(View.GONE);
        }

        ArrayList<Fragment> arrayList = new ArrayList<>();
        ArrayList<PropertyDetailsModel.Images> images = propertyImages.getImages();
        for (int i = 0; i < images.size(); i++) {
            FragmentImageView imF = new FragmentImageView();
            PropertyDetailsModel.Images images1 = images.get(i);
            imF.setData(imageUrl + images1.getPropertyImageName());
            imF.setId(Integer.parseInt(images1.getPropertyImageID()));
            imF.setPreviewPath(imageUrl);
            imF.setLargePath(imageUrl);
            imF.setFileName(images1.getPropertyImageName());
            arrayList.add(imF);
        }
        if (images.size() == 0) {
            if (propertyImages.getPropCoverImage() != null
                    && !propertyImages.getPropCoverImage().equals("")) {
                FragmentImageView imF = new FragmentImageView();
                imF.setData(imageUrl + propertyImages.getPropCoverImage());
                imF.setPreviewPath(imageUrl);
                imF.setLargePath(imageUrl);
                imF.setFileName(propertyImages.getPropCoverImage());
                arrayList.add(imF);
            }
        }
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
                ArrayList<String> imageList = new ArrayList<>();
                for (int i = 0; i < adapterSlider.getCount(); i++) {
                    FragmentImageView selected = (FragmentImageView) adapterSlider.getItem(i);
                    imageList.add(selected.getLargePath() + selected.getFileName());
                }
                Intent intent = new Intent(ActivityPostPropertyPreview.this, GalleryListActivity.class);
                intent.putExtra("images", imageList);
                intent.putExtra("videos", new ArrayList<String>());
                intent.putExtra("videoIcons", new ArrayList<String>());
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


        if (adapterSlider.getCount() > 1) {
            tabLayout.setVisibility(View.VISIBLE);
        } else {
            tabLayout.setVisibility(View.GONE);
        }

        layContentPreview.setVisibility(View.VISIBLE);
        layoutBottomAction.setVisibility(View.VISIBLE);
    }


    @Override
    public void changeOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void declarations() {
        propertyDetails = new PropertyDetailsModel();

        mLoadingDialog = new DialogProgress(ActivityPostPropertyPreview.this);
        progressLayout = (LinearLayout) findViewById(R.id.progress_layout_property_details);
        locButton = (FloatingActionButton) findViewById(R.id.fab_loc);
        videoButton = (FloatingActionButton) findViewById(R.id.fab_video);
        fabBlueprint = (FloatingActionButton) findViewById(R.id.fab_blueprint);
        amenitiesLayout = (LinearLayout) findViewById(R.id.layout_amenities_property_details);
        similarPropertyLayout = (LinearLayout) findViewById(R.id.layout_similar_properties_content_property_details);
        layContentPreview = (LinearLayout) findViewById(R.id.layout_content_property_preview);
        layContentPreview.setVisibility(View.INVISIBLE);
        layoutBottomAction = (LinearLayout) findViewById(R.id.layout_confirm_message_property_confirm);
        layoutBottomAction.setVisibility(View.INVISIBLE);
        propertyName = (TextView) findViewById(R.id.property_name_content_property_details);
        propertyAddress = (TextView) findViewById(R.id.property_address_content_property_details);
        propertyKey = (TextView) findViewById(R.id.propertyKey);
        propertyAddedDate = (TextView) findViewById(R.id.propertyAddedDate);
        propertyPrice = (TextView) findViewById(R.id.price_content_property_details);
        priceperm2 = (TextView) findViewById(R.id.tv_ppm2);
        propertyType = (TextView) findViewById(R.id.property_type_content_property_details);
        progress = new DialogProgress(ActivityPostPropertyPreview.this);
        propertyStatus = (TextView) findViewById(R.id.property_status_content_property_details);
        propertyPossession = (TextView) findViewById(R.id.possession_date_content_property_details);
        averagePrice = (TextView) findViewById(R.id.avg_price_content_property_details);
        propertyDescription = (TextView) findViewById(R.id.description_content_property_details);
        modify = (TextView) findViewById(R.id.text_modify_post_confirm);
        confirm = (TextView) findViewById(R.id.text_confirm_post_confirm);
        recyclerAmenities = (RecyclerView) findViewById(R.id.recycler_property_amenities);
        productImageViewPager = (ExtendedViewPager) findViewById(R.id.productImageViewPager);
        productImageViewPager.setOffscreenPageLimit(10);
        specificationList = new ArrayList<>();
        specList1 = new ArrayList<>();
        specList2 = new ArrayList<>();
        readMore = (TextView) findViewById(R.id.read_more_less_content_property_details);
        recyclerSpecification = (RecyclerView) findViewById(R.id.recycler_property_specification);
        moreSpecification = (TextView) findViewById(R.id.more_specification_property_details);

        amenitiesOverallLayout = findViewById(R.id.amenities_lay);
        locButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View targetView = findViewById(R.id.map_property_lay);
                targetView.getParent().requestChildFocus(targetView, targetView);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityPostPropertyPreview.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setAutoMeasureEnabled(true);
        specificationAdapter = new AdapterPropertySpecification(specificationList);
        recyclerSpecification.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerSpecification.setNestedScrollingEnabled(false);
        recyclerSpecification.setHasFixedSize(false);
        recyclerSpecification.setAdapter(specificationAdapter);
        specificationList.addAll(specList1);
        specificationAdapter.notifyDataSetChanged();
        isSpecificationExpanded = false;
        recyclerSpecification.clearFocus();

        amenitiesData = new ArrayList<>();
        recyclerAmenities.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                true));
        recyclerAmenities.setNestedScrollingEnabled(false);
        recyclerAmenities.setHasFixedSize(true);
        recyclerAmenities.clearFocus();
    }


    //TODO Alert
    private void getLocalList() {
        ApiLinks.getClient().create(LocalInformationListApi.class).post("0")
                .enqueue(new Callback<LocalInformationListResponse>() {
                    @Override
                    public void onResponse(Call<LocalInformationListResponse> call,
                                           Response<LocalInformationListResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            localList = response.body().getLocalList();
                            nameList = new ArrayList<>();
                            for (int i = 0; i < localList.size(); i++) {
                                nameList.add(localList.get(i).getLocalinfoTypeTitle());
                            }
                        } else {
                            Toast.makeText(ActivityPostPropertyPreview.this, "No Response", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LocalInformationListResponse> call, Throwable t) {
                        Toast.makeText(ActivityPostPropertyPreview.this, "No Response", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    //TODO Google Map
    private void setUpMap() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
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
                ListDialogBox dialogBox = new ListDialogBox(ActivityPostPropertyPreview.this, nameList,
                        "Local Information", "propertyPreview");
                //noinspection ConstantConditions
                dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogBox.show();
                dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (selectedPosition != 100) {
                            if(googleMap != null){
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
        int height = 40;
        int width = 40;
        List<LatLng> views = new ArrayList<>();
        for (int i = 0; i < localInformationList.size(); i++) {
            BitmapDrawable bitmapdraw = getBitmapDrawableLocal(selectedTypeName);
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
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(views.get(i)));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(12.0F));
        }
    }

    private void getLocalData(String selectedTypeId) {
        mLoadingDialog.show();
        ApiLinks.getClient().create(LocalInformationDataApi.class).post(
                selectedTypeId,
                propertyDetails.getPropertyLat(),
                propertyDetails.getPropertyLong())
                .enqueue(new Callback<LocalInformationDataResponse>() {
                    @Override
                    public void onResponse(Call<LocalInformationDataResponse> call, Response<LocalInformationDataResponse> response) {
                        if (response.isSuccessful()) {
                            mLoadingDialog.dismiss();
                            localInformationList = response.body().getLocalInformation();
                            setLocalInformation();
                        } else {
                            mLoadingDialog.dismiss();
                            localInformationList = new ArrayList<>();
                            Toast.makeText(ActivityPostPropertyPreview.this, "No Response", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LocalInformationDataResponse> call, Throwable t) {
                        mLoadingDialog.dismiss();
                        localInformationList = new ArrayList<>();
                        Toast.makeText(ActivityPostPropertyPreview.this, "No Response", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void setMap() {
        int height = 174;
        int width = 133;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_paya_logo_with_map_marker);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        Double lat = Double.parseDouble(propertyDetails.getPropertyLat());
        Double lon = Double.parseDouble(propertyDetails.getPropertyLong());
        views = new LatLng(lat, lon);
        //TODO
        markerMain = googleMap.addMarker(new MarkerOptions()
                .position(views)
                .title(propertyDetails.getPropertyName())
                .snippet(propertyDetails.getPropertyCityName())
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
        );
        markerMain.showInfoWindow();
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(views));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12.0F));
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

    public BitmapDrawable getBitmapDrawable(String value) {
        BitmapDrawable bitmapDrawable;
        switch (value) {
            case "Apartment":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_apartment);
                break;
            case "Villa":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_villa);
                break;
            case "Flat":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_apartment);
                break;
            case "House":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_house);
                break;
            case "Agricultural Land":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_agricultural_land);
                break;
            case "Commercial Office Space":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_commercial_office_space);
                break;
            case "Office":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_office_it_park);
                break;
            case "Commercial Shop":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_commercial_shop);
                break;
            case "Commercial Showroom":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_locationcommercial_showroom);
                break;
            case "Co-Working Space":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_co_working_space);
                break;
            case "Farm House":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_locationfarm_house);
                break;
            case "Industrial Land":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_industrial_land);
                break;
            case "Industrial Shed":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_industrial_shed);
                break;
            case "Office IT-park":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_office_it_park);
                break;
            case "Penthouse":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_penthouse);
                break;
            case "Residential House":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_residential_house);
                break;
            case "Residential Land":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_residential_land);
                break;
            case "Shop":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_shop);
                break;
            case "Studio Apartment":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_studio_apartment);
                break;
            case "Warehouse":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_warehouse);
                break;
            default:
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_apartment);
                break;
        }
        return bitmapDrawable;

    }

    public BitmapDrawable getBitmapDrawableLocal(String value) {
        BitmapDrawable bitmapDrawable;
        switch (value) {
            case "ATM":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_atm);
                break;
            case "Bank":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_bank);
                break;
            case "Bus Station":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_bus_station);
                break;
            case "Food":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_food);
                break;
            case "Gym":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_gym);
                break;
            case "Hospital":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_hospital);
                break;
            case "Pharmacy":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_pharmacy);
                break;
            case "Restaurant":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_food);
                break;
            case "School":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_school);
                break;
            case "Temple":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_temple);
                break;
            case "Train":
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_train);
                break;
            default:
                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_location_bank);
                break;
        }
        return bitmapDrawable;

    }

    @Override
    public void onBackPressed() {
    }

}
