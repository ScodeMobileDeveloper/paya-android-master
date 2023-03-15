package com.paya.paragon.activity.localExpert;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.paya.paragon.R;
import com.paya.paragon.activity.GalleryListActivity;
import com.paya.paragon.activity.login.ActivityLoginEmail;
import com.paya.paragon.adapter.AdapterLocalExpertRating;
import com.paya.paragon.adapter.ExpertServiceAdapter;
import com.paya.paragon.api.contactMerchantLocalExpert.ContactMerchantLocalExpertApi;
import com.paya.paragon.api.contactMerchantLocalExpert.ContactMerchantLocalExpertResponse;
import com.paya.paragon.api.contactMerchantLocalExpert.DownLoadVoucherAPI;
import com.paya.paragon.api.getProfileDetails.BaseResponse;
import com.paya.paragon.api.localExpertCategory.LocalExpertCategoryData;
import com.paya.paragon.api.localExpertDetials.LocalExpertDetailsApi;
import com.paya.paragon.api.localExpertDetials.LocalExpertDetailsResponse;
import com.paya.paragon.api.localExpertDetials.LocalExpertGalleryImgsModel;
import com.paya.paragon.api.localExpertDetials.LocalExpertInfo;
import com.paya.paragon.api.localExpertDetials.LocalExpertRatingModel;
import com.paya.paragon.api.localExpertDetials.LocalExpertServiceModel;
import com.paya.paragon.api.localExpertRating.LocalExpertRatingAPI;
import com.paya.paragon.api.localExpertRating.LocalExpertRatingResponse;
import com.paya.paragon.classes.ExpandableTextView;
import com.paya.paragon.classes.ReadMoreTextView;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.CountryCode.CountryCodePicker;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.ListBusinessDialogeBox;
import com.paya.paragon.utilities.LocalExportRatingBox;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("HardCodedStringLiteral")
public class ActivityLocalExpertDetails extends AppCompatActivity {

    String expertId = "", expertName = "", phoneNumberValue = "";
    RecyclerView recyclerRating;
    AdapterLocalExpertRating adapterLocalExpertRating;
    ArrayList<LocalExpertRatingModel> userRatings;
    ArrayList<LocalExpertServiceModel> expertServiceModels;
    List<LocalExpertGalleryImgsModel> expertGalleryImgsModel;

    DialogProgress progress;
    LocalExpertInfo localExpert;
    TextView name, establishedYear, address, merchantName,
            merchantDesignation, phone_num, userEmail, userCompanyUrlKey,
            userLinkedin, userTwitter, city_name, tvEst;
    TextView  businessTitle, contactOwner,
            listBusiness, button_download_voucher, expert_title_service;
    ReadMoreTextView businessDescription, tv_highlights;
    LinearLayout layoutAbout, layoutProgress, serviceLay,galleryLay;
    ExpandableTextView expertCategories;
    ArrayList<LocalExpertCategoryData> categories;
    public Dialog alertDialog;
    String imageURL, seviceURL;
    ImageView imageLogo;
    private RecyclerView expert_service_recycler;

    FloatingActionButton fab_2;
    //rating
    public static String rate = "no", rateTitle = "", rateComment = "", rateCount = "", userUrlKey = "";
    ImageView iv_user;
    private String reviewUrl, contactImageURL, resVoucher;
    String categoryId;
    boolean isValid;
    boolean isTextViewClicked = false, isTextViewClickedHighLight = false;
    private TextView tv_readmore_expert, tv_highlights_text, tv_readmore_highlight;
    private String highlights = "";
    private LinearLayout lnr_main;
    private ImageView imgCall;

    SliderView sliderView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_local_expert_details);
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.rl_local_expert_details_parent_layout));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        mTitle.setText("");//TODO after getting expert name
        mTitle.setTypeface(mTitle.getTypeface(), Typeface.BOLD);

        declarations();

        expertId = getIntent().getStringExtra("localExpertId");

        expertName = getIntent().getStringExtra("localExpertName");
        categoryId = getIntent().getStringExtra("categoryId");
        userUrlKey = getIntent().getStringExtra("userUrlKey");
        mTitle.setText(expertName);

        if (!Utils.NoInternetConnection(ActivityLocalExpertDetails.this)) {
            getLocalExpertDetails();
        } else {
            progress.dismiss();
            Toast.makeText(ActivityLocalExpertDetails.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }

        listBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListBusinessDialogeBox dialogBox = new ListBusinessDialogeBox(ActivityLocalExpertDetails.this, categoryId);
                dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogBox.show();
                dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {

                    }
                });
            }
        });

        contactOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (!Utils.NoInternetConnection(ActivityLocalExpertDetails.this)) {
                    alertContactMerchant(1);
                } else {
                    Toast.makeText(ActivityLocalExpertDetails.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }*/
                contactAgent();
            }
        });

        fab_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SessionManager.getLoginStatus(ActivityLocalExpertDetails.this)) {
                    rate = "no";
                    rateTitle = "";
                    rateComment = "";
                    rateCount = "";
                    LocalExportRatingBox dialogBox = new LocalExportRatingBox(ActivityLocalExpertDetails.this);
                    dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogBox.show();
                    dialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            if (rate.equals("yes")) {
                                rateExpertDetails();
                            }
                        }
                    });
                } else {
                    Intent intentLogin = new Intent(ActivityLocalExpertDetails.this, ActivityLoginEmail.class);
                    startActivity(intentLogin);
                }
            }
        });
    }

    private void rateExpertDetails() {
        progress.show();
        ApiLinks.getClient().create(LocalExpertRatingAPI.class).post(
                expertId, rateCount, rateTitle, rateComment, SessionManager.getAccessToken(ActivityLocalExpertDetails.this))
                .enqueue(new Callback<LocalExpertRatingResponse>() {
                    @Override
                    public void onResponse(Call<LocalExpertRatingResponse> call, Response<LocalExpertRatingResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getResponse().equals("Success")) {
                                alertSuccess(getResources().getString(R.string.after_expert_rating_heading),
                                        getResources().getString(R.string.after_expert_rating_body));
                            } else {
                                Toast.makeText(ActivityLocalExpertDetails.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ActivityLocalExpertDetails.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                        }
                        progress.dismiss();
                        layoutProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<LocalExpertRatingResponse> call, Throwable t) {
                        Toast.makeText(ActivityLocalExpertDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        layoutProgress.setVisibility(View.GONE);
                        progress.dismiss();
                    }
                });
    }

    private void getLocalExpertDetails() {
        progress.show();
        ApiLinks.getClient().create(LocalExpertDetailsApi.class).post(
                expertId,
                SessionManager.getLanguageID(this))
                .enqueue(new Callback<LocalExpertDetailsResponse>() {
                    @Override
                    public void onResponse(Call<LocalExpertDetailsResponse> call, Response<LocalExpertDetailsResponse> response) {
                        if (response.isSuccessful()) {
                            int code = response.body().getCode();
                            if (code == 100) {
                                userRatings.addAll(response.body().getData().getRating());
                                expertGalleryImgsModel.addAll(response.body().getData().getBusiness().getGalleryImgs());
                                expertServiceModels.addAll(response.body().getData().getServices());
                                localExpert = response.body().getData().getBusiness();
                                contactImageURL = response.body().getData().getProfileUrl();
                                reviewUrl = response.body().getData().getReviewUrl();
                                resVoucher = response.body().getData().getResVoucher();
                                imageURL = response.body().getData().getImageURL();
                                seviceURL = response.body().getData().getSeviceURL();
                                highlights = response.body().getData().getVoucherHighlights();
                                if (!TextUtils.isEmpty(highlights)) {
                                    tv_highlights.setVisibility(View.VISIBLE);
                                    tv_highlights_text.setVisibility(View.VISIBLE);
                                    tv_readmore_highlight.setVisibility(View.VISIBLE);
                                    tv_highlights.setText(highlights);
                                }
                                //set logo image
                                String ImageFile = localExpert.getBusinessCImage();
                                ImageFile.replace(" ", "%20");
                                Utils.loadUrlImage(iv_user, contactImageURL + ImageFile, R.drawable.no_image, true);

                                if(response.body().getData().getBusiness().getGalleryImgs()!=null && !response.body().getData().getBusiness().getGalleryImgs().isEmpty()){
                                    galleryLay.setVisibility(View.VISIBLE);
                                    SliderAdapter sliderAdapter = new SliderAdapter(response.body().getData().getBusiness().getGalleryImgs(),response.body().getData().getBusiness().getImageUrl(),response.body().getData().getBusiness().getBusinessName());
                                    sliderView.setSliderAdapter(sliderAdapter);
                                }else {
                                   galleryLay.setVisibility(View.GONE);

                                }
                                setData();
                            } else {
                                Toast.makeText(ActivityLocalExpertDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ActivityLocalExpertDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                        progress.dismiss();
                        layoutProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<LocalExpertDetailsResponse> call, Throwable t) {
                        Toast.makeText(ActivityLocalExpertDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        layoutProgress.setVisibility(View.GONE);
                        progress.dismiss();
                    }
                });
    }


    //TODO Set data
    private void setData() {
        String nameText = localExpert.getBusinessName() + (!TextUtils.isEmpty(localExpert.getEstYear()) ? ("\n" + getString(R.string.established_in) + " " + localExpert.getEstYear()) : "");
        name.setText(nameText);
        ArrayList<String> arr_location = new ArrayList<>();
        String strAddress = "";
        if (localExpert.getLocAddress1() != null && !localExpert.getLocAddress1().equals("")) {
            strAddress = strAddress + localExpert.getLocAddress1();
            arr_location.add(localExpert.getLocAddress1());
        }

        if (resVoucher != null && !resVoucher.equals("") /*&& SessionManager.getLoginStatus(this)*/) {
            button_download_voucher.setVisibility(View.VISIBLE);
            button_download_voucher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertContactMerchant(2);
                }
            });
        } else button_download_voucher.setVisibility(View.GONE);


        if (localExpert.getLocAddress2() != null && !localExpert.getLocAddress2().equals("")) {
            strAddress = strAddress + ", " + localExpert.getLocAddress2();
            arr_location.add(localExpert.getLocAddress2());
        }
        if (localExpert.getCityName() != null && !localExpert.getCityName().equals("")) {
            strAddress = strAddress + ", " + localExpert.getCityName();
            arr_location.add(localExpert.getCityName());
        }
        if (localExpert.getStateName() != null && !localExpert.getStateName().equals("")) {
            strAddress = strAddress + ", " + localExpert.getStateName();
            arr_location.add(localExpert.getStateName());
        }
        if (localExpert.getCountryName() != null && !localExpert.getCountryName().equals("")) {
            strAddress = strAddress + ", " + localExpert.getCountryName();
            arr_location.add(localExpert.getCountryName());
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String fulladdress = String.join(",", arr_location);
            city_name.setText(fulladdress);
        } else {
            city_name.setText(strAddress);
        }

        address.setVisibility(View.GONE);

        userEmail.setText(localExpert.getUserEmail());
        phone_num.setText(localExpert.getUserPhone());


        String merchant = "";
        if (localExpert.getBusinessCFirstName() != null && !localExpert.getBusinessCFirstName().equals("")) {
            merchant = localExpert.getBusinessCFirstName();
        }
        if (localExpert.getUserLastName() != null && !localExpert.getUserLastName().equals("")) {
            if (merchant.trim().equals(""))
                merchant = localExpert.getUserLastName();
            else merchant = merchant + " " + localExpert.getUserLastName();
        }
        if (merchant.trim().equals(""))
            merchantName.setVisibility(View.GONE);
        else {
            merchantName.setVisibility(View.VISIBLE);
            merchantName.setText(merchant);
        }
        if (localExpert.getUserPhone() != null && !localExpert.getUserPhone().equals("")) {
            phone_num.setText(localExpert.getUserPhone());
            phoneNumberValue = localExpert.getUserPhone();

            phone_num.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contactAgent();
                }
            });
        } else {
            phone_num.setVisibility(View.GONE);
            imgCall.setVisibility(View.GONE);
        }
        if (localExpert.getUserEmail() != null && !localExpert.getUserEmail().equals("")) {
            userEmail.setText(localExpert.getUserEmail());
        } else {
            userEmail.setVisibility(View.GONE);
        }


        if (localExpert.getUserLinkedin() != null && !localExpert.getUserLinkedin().equals("")) {
            userLinkedin.setText(localExpert.getUserLinkedin());
        } else {
            userLinkedin.setVisibility(View.GONE);
        }
        if (localExpert.getUserTwitter() != null && !localExpert.getUserTwitter().equals("")) {
            userTwitter.setText(localExpert.getUserEmail());
        } else {
            userTwitter.setVisibility(View.GONE);
        }
        if (localExpert.getUserCompanyUrlKey() != null && !localExpert.getUserCompanyUrlKey().equals("")) {
            userCompanyUrlKey.setText(localExpert.getUserCompanyUrlKey());
        } else {
            userCompanyUrlKey.setVisibility(View.GONE);
        }


        if (localExpert.getBusinessCDesignation() != null && !localExpert.getBusinessCDesignation().equals("")) {
            merchantDesignation.setText(localExpert.getBusinessCDesignation());
        } else {
            merchantDesignation.setText("");
        }

      /*  categories = localExpert.getCategory();
        String strCategory = "";
        for (LocalExpertCategoryData data: categories){
            strCategory = strCategory + data.getUserCategoryName() + ", ";
        }
        strCategory = strCategory.substring(0, strCategory.length()-2);*/
        String strCategory = localExpert.getUserCategoryNames();
        expertCategories.setText(strCategory);

        businessTitle.setText(getString(R.string.about) + " " + localExpert.getBusinessName());

      /*  businessDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readMoreOrLess();
            }
        });*/
        tv_readmore_expert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readMoreOrLess();
            }
        });
        tv_readmore_highlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readMoreOrLess_highlights();
            }
        });
        if (localExpert.getBusinessService() != null && !localExpert.getBusinessService().equals("")) {

            businessDescription.setText(localExpert.getBusinessService());
        } else {
            businessDescription.setText("");
            layoutAbout.setVisibility(View.GONE);
        }


        tv_highlights.setOnLayoutListener(new ReadMoreTextView.OnLayoutListener() {
            @Override
            public void onLayouted(TextView view) {
                int lineCount = view.getLineCount();


                if (lineCount < 3) {
                    tv_readmore_highlight.setVisibility(View.GONE);
                } else {
                    tv_readmore_highlight.setVisibility(View.VISIBLE);
                }

            }
        });

        businessDescription.setOnLayoutListener(new ReadMoreTextView.OnLayoutListener() {
            @Override
            public void onLayouted(TextView view) {
                int lineCount = view.getLineCount();


                if (lineCount < 3) {
                    tv_readmore_expert.setVisibility(View.GONE);
                } else {
                    tv_readmore_expert.setVisibility(View.VISIBLE);
                }

            }
        });

        if (userRatings != null && userRatings.size() > 0) {
            findViewById(R.id.ratingLay).setVisibility(View.VISIBLE);
            adapterLocalExpertRating = new AdapterLocalExpertRating(ActivityLocalExpertDetails.this, userRatings, contactImageURL);
            recyclerRating.setAdapter(adapterLocalExpertRating);
            adapterLocalExpertRating.notifyDataSetChanged();
        } else findViewById(R.id.ratingLay).setVisibility(View.GONE);


        if (expertServiceModels != null && expertServiceModels.size() > 0) {
            String serviceText = getString(R.string.services) + "(" + expertServiceModels.size() + ")";
            expert_title_service.setText(serviceText);
            findViewById(R.id.serviceLay).setVisibility(View.VISIBLE);
            ExpertServiceAdapter expertServiceAdapter = new ExpertServiceAdapter(ActivityLocalExpertDetails.this, seviceURL, expertServiceModels);
            expert_service_recycler.setAdapter(expertServiceAdapter);

        } else findViewById(R.id.serviceLay).setVisibility(View.GONE);

        layoutProgress.setVisibility(View.GONE);
        //set logo image
        Utils.loadUrlImage(imageLogo, imageURL + localExpert.getBusinessLogo(), R.drawable.no_image, false);
    }

    private void contactAgent() {
        if (ContextCompat.checkSelfPermission(ActivityLocalExpertDetails.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ActivityLocalExpertDetails.this,
                    new String[]{Manifest.permission.CALL_PHONE}, 123);
        } else {
            try {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumberValue));
                startActivity(intent);
            } catch (Exception e) {
                FirebaseCrashlytics.getInstance().recordException(e);
                Toast.makeText(ActivityLocalExpertDetails.this, "No Application found to perform this action",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 123: {
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumberValue));
                    startActivity(intent);
                } catch (Exception e) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                    Toast.makeText(ActivityLocalExpertDetails.this, "No Application found to perform this action",
                            Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void declarations() {
        imgCall = findViewById(R.id.btn_call);
        iv_user = findViewById(R.id.iv_user);
        userRatings = new ArrayList<>();
        expertServiceModels = new ArrayList<>();
        expertGalleryImgsModel = new ArrayList<>();
        recyclerRating = (RecyclerView) findViewById(R.id.recycler_rating_review_listing);
        recyclerRating.setNestedScrollingEnabled(false);
        recyclerRating.setLayoutManager(new LinearLayoutManager(ActivityLocalExpertDetails.this));

        adapterLocalExpertRating = new AdapterLocalExpertRating(ActivityLocalExpertDetails.this, userRatings, contactImageURL);
        recyclerRating.setAdapter(adapterLocalExpertRating);
        progress = new DialogProgress(this);
        localExpert = new LocalExpertInfo();
        fab_2 = findViewById(R.id.fab_2);
        imageLogo = findViewById(R.id.expert_logo_local_expert_details);


        name = (TextView) findViewById(R.id.expert_name_local_expert_details);
        tv_highlights = (ReadMoreTextView) findViewById(R.id.tv_highlights);
        tv_highlights_text = (TextView) findViewById(R.id.tv_highlights_text);
        tv_readmore_highlight = (TextView) findViewById(R.id.tv_readmore_highlight);
        // tvEst=findViewById(R.id.tv_est);
        establishedYear = (TextView) findViewById(R.id.expert_year_local_expert_details);
        address = (TextView) findViewById(R.id.expert_address_local_expert_details);
        merchantName = (TextView) findViewById(R.id.business_cf_name_local_expert_details);
        merchantDesignation = (TextView) findViewById(R.id.business_cf_designation_local_expert_details);
        expertCategories = (ExpandableTextView) findViewById(R.id.expandable_text_categories_local_expert_detail);
        businessTitle = (TextView) findViewById(R.id.expert_title_local_expert_details);
        businessDescription = (ReadMoreTextView) findViewById(R.id.expert_profile_description_local_expert_details);
        button_download_voucher = (TextView) findViewById(R.id.button_download_voucher);
        layoutAbout = (LinearLayout) findViewById(R.id.layout_expert_about_local_expert_details);

        layoutProgress = (LinearLayout) findViewById(R.id.progressbar_layout_local_expert_details);
        listBusiness = (TextView) findViewById(R.id.list_your_business_local_expert_details);
        contactOwner = (TextView) findViewById(R.id.contact_local_expert_details);
        expert_title_service = (TextView) findViewById(R.id.expert_title_service);
        serviceLay = findViewById(R.id.serviceLay);
        expert_service_recycler = findViewById(R.id.expert_service_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        expert_service_recycler.setLayoutManager(llm);
        //   expert_service_recycler.setLayoutManager(new LinearLayoutManager(ActivityLocalExpertDetails.this));

        phone_num = findViewById(R.id.phone_num);
        userEmail = findViewById(R.id.userEmail);
        userCompanyUrlKey = findViewById(R.id.userCompanyUrlKey);
        userLinkedin = findViewById(R.id.userLinkedin);
        userTwitter = findViewById(R.id.userTwitter);
        city_name = findViewById(R.id.city_name);
        tv_readmore_expert = findViewById(R.id.tv_readmore_expert);
        categories = new ArrayList<>();

        sliderView = findViewById(R.id.imageSlider);
        galleryLay = findViewById(R.id.gallery_lay);




    }


    //TODO Alert
    /*type:1->contact, type->2download_pdf*/
    private void alertContactMerchant(final int type) {
        alertDialog = new Dialog(ActivityLocalExpertDetails.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_contact_expert_owner_popup, null);
        ImageView close = alert_layout.findViewById(R.id.close_contact_expert_owner);
        TextView contactOwnerTitle = alert_layout.findViewById(R.id.title_contact_expert_owner);
        final EditText fullName = alert_layout.findViewById(R.id.editText_user_full_name_contact_expert_owner);
        final EditText enquiryMessgdeEdt = alert_layout.findViewById(R.id.editText_user_message_contact_expert_owner);
        final EditText email = alert_layout.findViewById(R.id.editText_user_email_address_contact_expert_owner);
        final EditText phone = alert_layout.findViewById(R.id.editText_phone_contact_expert_owner);
        final CountryCodePicker codePicker = alert_layout.findViewById(R.id.country_code_contact_expert_owner);
        codePicker.registerCarrierNumberEditText(phone);
        final EditText message = alert_layout.findViewById(R.id.editText_user_message_contact_expert_owner);
        final ProgressBar progress_bar = alert_layout.findViewById(R.id.progress_bar);
        lnr_main = alert_layout.findViewById(R.id.lnr_main);

        progress_bar.setVisibility(View.INVISIBLE);
        final TextView submit = alert_layout.findViewById(R.id.button_submit_contact_expert_owner);
        codePicker.setCountryForNameCode(GlobalValues.countryCode);
        if (type == 1) {
            contactOwnerTitle.setText(getString(R.string.email) + " " + expertName);
        } else if (type == 2) {
            contactOwnerTitle.setText(getString(R.string.voucher_download));
        }
        if (SessionManager.getLoginStatus(ActivityLocalExpertDetails.this)) {
            fullName.setText(SessionManager.getFullName(ActivityLocalExpertDetails.this));
            email.setText(SessionManager.getEmail(ActivityLocalExpertDetails.this));
            phone.setText(SessionManager.getPhone(ActivityLocalExpertDetails.this));
            String countryCode = SessionManager.getCountryCode(ActivityLocalExpertDetails.this);
            if (countryCode == null || countryCode.equals("")) {
                codePicker.setCountryForNameCode(GlobalValues.countryCode);
            } else {
                codePicker.setCountryForNameCode(countryCode);
            }
            message.clearFocus();
            message.requestFocus();
        } else {
            codePicker.setCountryForNameCode(GlobalValues.countryCode);
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        submit.setVisibility(View.VISIBLE);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strMessage = message.getText().toString().trim();
                String strCountryCode = codePicker.getSelectedCountryCode().toLowerCase();
                String strCountryCodeName = codePicker.getSelectedCountryNameCode().toLowerCase();
                String strName = fullName.getText().toString().trim();
                String strEmail = email.getText().toString().trim();
                String strPhone = phone.getText().toString();
                if (!Utils.NoInternetConnection(ActivityLocalExpertDetails.this)) {
                    if (validateFullName(strName)) {
                        if (validateEmail(strEmail)) {
                            if (validatePhone(phone.getText().toString(), codePicker)) {
                                if (validateAddress(strMessage)) {
                                    progress_bar.setVisibility(View.VISIBLE);
                                    submit.setVisibility(View.GONE);
                                    if (type == 1) {
                                        postContactMerchant(alertDialog, strName, strEmail, strPhone, strCountryCode, strMessage);

                                    } else if (type == 2) {
                                        HashMap<String, String> map = new HashMap<>();
                                        map.put("contactName", strName);
                                        map.put("contactEmail", strEmail);
                                        map.put("contactPhno", strPhone);

                                        map.put("businessID", expertId);
                                        map.put("enquiryType", "Voucher");
                                        map.put("expertKey", userUrlKey);
                                        map.put("voucherDownload", "yes");
                                        map.put("countryCode", strCountryCodeName);
                                        map.put("contactEnquiry", enquiryMessgdeEdt.getText().toString());
                                        postDownloadVoucherData(alertDialog, map);
                                    }


                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(ActivityLocalExpertDetails.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.setContentView(alert_layout);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private boolean validateFullName(String strName) {


        if (strName.equals("")) {
            Toast.makeText(ActivityLocalExpertDetails.this, getString(R.string.enter_full_name), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Utils.isValidName(strName)) {
            Toast.makeText(ActivityLocalExpertDetails.this, getString(R.string.please_enter_valid_name), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean validateEmail(String email) {
        if (email.equals("")) {
            Toast.makeText(ActivityLocalExpertDetails.this, getString(R.string.email_required), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                return true;
            } else {
                Toast.makeText(ActivityLocalExpertDetails.this, getString(R.string.please_enter_valid_email_address), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

    }

    /*    private boolean validatePhone(String strPhone) {
            if (strPhone.equals("")) {
                Toast.makeText(ActivityLocalExpertDetails.this, getString(R.string.valid_phone_number),Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }*/
    private boolean validatePhone(String mobile, final CountryCodePicker codePicker) {
        if (mobile.equals("")) {
            Toast.makeText(ActivityLocalExpertDetails.this, getString(R.string.valid_mobile_number), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!Utils.isValidMobile(mobile)) {
                Toast.makeText(ActivityLocalExpertDetails.this, getString(R.string.valid_mobile_number),
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

    private boolean validateAddress(String address) {
        if (address.equals("")) {
            Toast.makeText(ActivityLocalExpertDetails.this, "Enter your enquiry", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    private void postContactMerchant(final Dialog alertDialog, String strName, String strEmail, String strPhone,
                                     String strCountryCode, String strMessage) {
        progress.show();
        ApiLinks.getClient().create(ContactMerchantLocalExpertApi.class).post(strName, strEmail, strPhone, strMessage,
                expertId, "LocalExperts", SessionManager.getAccessToken(ActivityLocalExpertDetails.this), strCountryCode)
                .enqueue(new Callback<ContactMerchantLocalExpertResponse>() {
                    @Override
                    public void onResponse(Call<ContactMerchantLocalExpertResponse> call, Response<ContactMerchantLocalExpertResponse> response) {
                        alertDialog.dismiss();
                        if (response.isSuccessful()) {
                            String message = response.body().getResponse();
                            if (message.equals("Success")) {
                                alertSuccess(getResources().getString(R.string.thank_you_for_contact),
                                        getResources().getString(R.string.contact_owner_success_message));
                            } else {

                            }

                        } else {
                            Toast.makeText(ActivityLocalExpertDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                        progress.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ContactMerchantLocalExpertResponse> call, Throwable t) {
                        progress.dismiss();
                        alertDialog.dismiss();
                        Toast.makeText(ActivityLocalExpertDetails.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void postDownloadVoucherData(final Dialog alertDialog, HashMap<String, String> map) {
        progress.show();

        ApiLinks.getClient().create(DownLoadVoucherAPI.class).postVoucherDownLoadDetails(map).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                progress.dismiss();
                alertDialog.dismiss();
                if (response.body().getResponse().compareToIgnoreCase("Success") == 0) {

                    alertDownLoadVoucher();
                } else {
                    Toast.makeText(ActivityLocalExpertDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                progress.dismiss();
            }
        });


    }

    private void alertSuccess(String successTitle, String successMessage) {
        alertDialog = new Dialog(ActivityLocalExpertDetails.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }


    private void alertDownLoadVoucher() {
        alertDialog = new Dialog(ActivityLocalExpertDetails.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.download_success, null);

        TextView tv_download = alert_layout.findViewById(R.id.tv_download);
        ImageView close_success_dialog = alert_layout.findViewById(R.id.close_success_dialog);
        tv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resVoucher != null && !resVoucher.equals("")) {

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(resVoucher));
                    startActivity(i);
                    //alertDialog.dismiss();
                } else {
                    Toast.makeText(ActivityLocalExpertDetails.this, "No link available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        close_success_dialog.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void readMoreOrLess() {
        if (isTextViewClicked) {

            businessDescription.setMaxLines(2);
            isTextViewClicked = false;
            tv_readmore_expert.setText(R.string.read_more);
        } else {

            businessDescription.setMaxLines(Integer.MAX_VALUE);
            isTextViewClicked = true;
            tv_readmore_expert.setText(R.string.read_less);
        }
    }

    private void readMoreOrLess_highlights() {
        if (isTextViewClickedHighLight) {

            tv_highlights.setMaxLines(2);
            isTextViewClickedHighLight = false;
            tv_readmore_highlight.setText(R.string.read_more);
        } else {

            tv_highlights.setMaxLines(Integer.MAX_VALUE);
            isTextViewClickedHighLight = true;
            tv_readmore_highlight.setText(R.string.read_less);
        }
    }

    public class SliderAdapter extends SliderViewAdapter<SliderViewHolder>{
        ArrayList<LocalExpertGalleryImgsModel> galleryImgsModels;
        String imageUrl,companyName;
        ArrayList<String> imagesList = new ArrayList<>();
        public SliderAdapter(ArrayList<LocalExpertGalleryImgsModel> galleryImgs, String imageUrl, String businessName){
            this.galleryImgsModels = galleryImgs;
            this.imageUrl  = imageUrl;
            this.companyName = businessName;


            for(int i = 0;i<galleryImgsModels.size();i++){
                imagesList.add(imageUrl+galleryImgsModels.get(i).getGalleryImageName());

            }
        }
        @Override
        public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_slider,null);
            return new SliderViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
            Utils.loadUrlImage(viewHolder.imageView,imageUrl+galleryImgsModels.get(position).getGalleryImageName(),R.drawable.property_image,false);


            viewHolder.itemView.setOnClickListener(view->{
                Intent intent = new Intent(ActivityLocalExpertDetails.this, GalleryListActivity.class);
                intent.putExtra("images", imagesList);
                intent.putExtra("title",companyName);

                startActivity(intent);
            });
        }

        @Override
        public int getCount() {
            return galleryImgsModels.size();
        }
    }

    private class SliderViewHolder extends SliderViewAdapter.ViewHolder {
        ShapeableImageView imageView;

        public SliderViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_cover);

        }
    }
}
