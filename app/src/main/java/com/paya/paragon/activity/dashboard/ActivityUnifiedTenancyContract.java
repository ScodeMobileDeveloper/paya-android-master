package com.paya.paragon.activity.dashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.paya.paragon.BuildConfig;
import com.paya.paragon.R;
import com.paya.paragon.activity.ActivityHome;
import com.paya.paragon.activity.PaymentActivity;
import com.paya.paragon.activity.postProperty.PostPropertyPage01Activity;
import com.paya.paragon.activity.postRequirements.ActivityRequirementPurpose;
import com.paya.paragon.adapter.AdapterTenancyContracts;
import com.paya.paragon.api.downLoadPDF.DownloadPDFApi;
import com.paya.paragon.api.tenancyContractList.TenancyContractListApi;
import com.paya.paragon.api.tenancyContractList.TenancyContractListResponse;
import com.paya.paragon.api.tenancyContractList.TenancyContractModel;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@SuppressWarnings("HardCodedStringLiteral")
public class ActivityUnifiedTenancyContract extends AppCompatActivity implements View.OnClickListener {


    static ArrayList<TenancyContractModel> contractModels;
    AdapterTenancyContracts adapterTenancyContracts;
    RecyclerView recyclerContracts;
    DialogProgress progressBar;
    static String fileUrl = "";
    static String pdfDownloadBaseUrl = "";

    String mPermissionStorage = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int REQUEST_CODE_PERMISSION = 1;
    File futureStudioIconFile;
    private DialogProgress mLoadingDialog;
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
    TextView tvNoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unified_tenancy);
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.drawer_layout_unified_tenancy));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mLoadingDialog = new DialogProgress(this);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(16);
        mTitle.setText(R.string.unified_tenancy_contract);
        mProfileImage = toolbar.findViewById(R.id.profile_image_my_account);
        declarations();

        if (!Utils.NoInternetConnection(ActivityUnifiedTenancyContract.this)) {
            getUnifiedTenancyContracts();
        } else {
            Utils.NoInternetConnection(ActivityUnifiedTenancyContract.this);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.ic_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payThroughUrl();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1148) {
            if (resultCode == Activity.RESULT_OK) {

                getUnifiedTenancyContracts();
            }
        }
    }

    public void payThroughUrl() {
        String new_url = "https://hod.co/mobile/en/index/ApptenancyContract/";
        String url = new_url/*BuildConfig.APP_BASE_URL_WEB_VIEW + "index/ApptenancyContract/"*/,
                sUrl = ApiLinks.BASE_URL_WEB_VIEW + "dashboard/activity/MyTenancy/", lUrl = BuildConfig.APP_BASE_URL + "user/paymentFailedMob";

        Intent intent = new Intent(this, PaymentActivity.class);


        String postData = "";

        postData = "userID=" + SessionManager.getUserId(ActivityUnifiedTenancyContract.this) + "" +

                intent.putExtra("payUrl", url);
        intent.putExtra("post", postData);
        intent.putExtra("sUrl", sUrl);
        intent.putExtra("lUrl", lUrl);
        Log.d("PayURLTEST", url + postData);
        startActivityForResult(intent, 1148);
    }

    //TODO API Calls
    private void getUnifiedTenancyContracts() {
        progressBar.show();
        ApiLinks.getClient().create(TenancyContractListApi.class).post(
                SessionManager.getAccessToken(this),
                SessionManager.getLanguageID(this))
                .enqueue(new Callback<TenancyContractListResponse>() {
                    @Override
                    public void onResponse(Call<TenancyContractListResponse> call, Response<TenancyContractListResponse> response) {
                        if (response.isSuccessful()) {
                            String status = response.body().getResponse();
                            String message = response.body().getMessage();
                            if (status.equals("Success")) {
                                tvNoItem.setVisibility(View.GONE);
                                progressBar.hide();
                                contractModels = response.body().getData().getContracts();
                                adapterTenancyContracts = new AdapterTenancyContracts(ActivityUnifiedTenancyContract.this,
                                        contractModels, new AdapterTenancyContracts.ItemClickAdapterListener() {
                                    @Override
                                    public void downloadClick(View v, int position) {
                                        String pdfLink = contractModels.get(position).getTenancy_usrLink();
                                      /*  if (ContextCompat.checkSelfPermission(ActivityUnifiedTenancyContract.this, mPermissionStorage)
                                                != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions((Activity) ActivityUnifiedTenancyContract.this, new String[]{mPermissionStorage}, REQUEST_CODE_PERMISSION);
                                        } else {*/
                                        if (pdfLink == null || pdfLink.equals("")) {
                                            Toast.makeText(ActivityUnifiedTenancyContract.this, getString(R.string.no_pdf_file_found), Toast.LENGTH_LONG).show();
                                        } else {
                                            String url = pdfLink;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                            //downloadFile(pdfLink);
                                            // downloadAndOpenPDF(ActivityUnifiedTenancyContract.this,pdfLink);
                                        }


                                    }
                                });
                                recyclerContracts.setAdapter(adapterTenancyContracts);
                                adapterTenancyContracts.notifyDataSetChanged();
                            } else {
                                tvNoItem.setVisibility(View.VISIBLE);
                                progressBar.hide();
                            }
                        } else {
                            progressBar.hide();
                            Toast.makeText(ActivityUnifiedTenancyContract.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TenancyContractListResponse> call, Throwable t) {
                        progressBar.hide();
                        Toast.makeText(ActivityUnifiedTenancyContract.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void downloadAndOpenPDF(final Context context, final String pdfUrl) {
        String filename = pdfUrl.substring(pdfUrl.lastIndexOf("/") + 1);
        filename = filename.replace("php", "pdf");
        final File tempFile = new File(getExternalFilesDir(null), "b.pdf");
    /*    if ( tempFile.exists() ) {
            Log.e("Path",tempFile.getAbsolutePath());
            return;
        }*/
        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(pdfUrl));
        r.setDestinationInExternalFilesDir(context, null, "b.pdf");
        final DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                long downloadId = intent.getLongExtra(null, -1);
                Cursor c = dm.query(new DownloadManager.Query().setFilterById(downloadId));
                if (c.moveToFirst()) {
                    int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        Uri pathUri = Uri.parse(new File(getExternalFilesDir(null) + "/b.pdf").toString());
                        //Uri path = Uri.fromFile(tempFile);
                        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pdfOpenintent.setDataAndType(pathUri, "application/pdf");
                        try {
                            startActivity(pdfOpenintent);
                        } catch (Exception e) {
                            Toast.makeText(ActivityUnifiedTenancyContract.this, "Pdf reader not found", Toast.LENGTH_SHORT).show();
                            Log.e("Exception", e + "");
                        }
                    }
                }
                c.close();
            }
        };
        context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        dm.enqueue(r);
    }

    public void downloadFile(String tenancy_usrLink) {
        fileUrl = tenancy_usrLink.substring(tenancy_usrLink.lastIndexOf("/") + 1);
        pdfDownloadBaseUrl = tenancy_usrLink.substring(0, tenancy_usrLink.length() - fileUrl.length());
        Log.e("pdfDownloadBaseUrl", "pdfDownloadBaseUrl:" + pdfDownloadBaseUrl);
        Log.e("fileUrl", "fileUrl" + fileUrl);
        if (!Utils.NoInternetConnection(ActivityUnifiedTenancyContract.this)) {
            downloadPdf();
        } else {
            Utils.NoInternetConnection(ActivityUnifiedTenancyContract.this);
        }
    }

    public void downloadPdf() {
        mLoadingDialog.show();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(pdfDownloadBaseUrl);
        Retrofit retrofitFile = builder.build();
        DownloadPDFApi downloadService = retrofitFile.create(DownloadPDFApi.class);
        Call<ResponseBody> call = downloadService.downloadFileWithDynamicUrlAsync(fileUrl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mLoadingDialog.dismiss();
                Log.e("download resp", response.message().toString());
                if (response != null) {
                    if (response.body() != null) {
                        writeResponseBodyToDisk(response.body());
                        Uri path = Uri.fromFile(futureStudioIconFile);
                        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pdfOpenintent.setDataAndType(path, "application/pdf");
                        try {
                            startActivity(pdfOpenintent);
                        } catch (ActivityNotFoundException e) {

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("download resp", t.toString());
                mLoadingDialog.dismiss();
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + fileUrl);
            Log.e("Path", getExternalFilesDir(null) + File.separator + fileUrl);
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("Status", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    ///TODO Set Data
    private void declarations() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_unified_tenancy);
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
        recyclerContracts = (RecyclerView) findViewById(R.id.recycler_unified_tenancy_contracts);
        progressBar = new DialogProgress(this);
        recyclerContracts.setLayoutManager(new LinearLayoutManager(ActivityUnifiedTenancyContract.this));
        contractModels = new ArrayList<>();

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
                SessionManager.logout(ActivityUnifiedTenancyContract.this);
                Intent intent = new Intent(ActivityUnifiedTenancyContract.this, ActivityHome.class);
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
            drawer.closeDrawer(Gravity.START); //CLOSE Nav Drawer!
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(Gravity.END); //CLOSE Nav Drawer!
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
        Intent intent = new Intent(ActivityUnifiedTenancyContract.this, ActivityHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClick(View view) {
        if (view == mProfileImage) {
            drawer.openDrawer(Gravity.END);

        }
        if (view == profileLayout) {
            toggleDrawer();
            Intent in = new Intent(ActivityUnifiedTenancyContract.this, ProfileActivity.class);
            startActivity(in);

        } else if (view == btnPostFreeAd) {
            toggleDrawer();
            Intent intent = new Intent(ActivityUnifiedTenancyContract.this, PostPropertyPage01Activity.class);
            startActivity(intent);

        } else if (view == btnPostRequirement) {
            toggleDrawer();
            Intent intent = new Intent(ActivityUnifiedTenancyContract.this, ActivityRequirementPurpose.class);
            startActivity(intent);

        } else if (view == textSavedSearches) {
            toggleDrawer();
            Intent intent = new Intent(ActivityUnifiedTenancyContract.this, ActivitySavedSearchList.class);
            startActivity(intent);

        } else if (view == textShortlisted) {
            toggleDrawer();
            Intent intent = new Intent(ActivityUnifiedTenancyContract.this, ActivityShortlisted.class);
            startActivity(intent);

        } else if (view == textPostedRequirements) {
            toggleDrawer();
            Intent intent = new Intent(ActivityUnifiedTenancyContract.this, ActivityPostedRequirements.class);
            startActivity(intent);

        } else if (view == textAgents) {
            toggleDrawer();
            Intent intent = new Intent(ActivityUnifiedTenancyContract.this, ActivityMyAgents.class);
            startActivity(intent);

        } else if (view == textPropertyEnquiry) {
            toggleDrawer();
            Intent intent = new Intent(ActivityUnifiedTenancyContract.this, ActivityMyProperties.class);
            startActivity(intent);

        } else if (view == textSettings) {
            toggleDrawer();
            Intent intent = new Intent(ActivityUnifiedTenancyContract.this, ActivitySettings.class);
            startActivity(intent);

        } else if (view == textSubscriptions) {
            toggleDrawer();
            Intent intent = new Intent(ActivityUnifiedTenancyContract.this, ActivityMySubscriptions.class);
            startActivity(intent);

        } else if (view == textEnquiriesOffers) {
            toggleDrawer();
            Intent intent = new Intent(ActivityUnifiedTenancyContract.this, ActivityEnquiriesOffers.class);
            startActivity(intent);

        } else if (view == textViewingRequest) {
            toggleDrawer();
            Intent intent = new Intent(ActivityUnifiedTenancyContract.this, ActivityViewingRequests.class);
            startActivity(intent);

        } else if (view == textUnifiedTenancy) {
            toggleDrawer();


        } else if (view == textOpenHouse) {
            toggleDrawer();
            Intent intent = new Intent(ActivityUnifiedTenancyContract.this, ActivityOpenHouse.class);
            startActivity(intent);

        } else if (view == textMyQuestions) {
            toggleDrawer();
            Intent intent = new Intent(ActivityUnifiedTenancyContract.this, ActivityMyQuestions.class);
            startActivity(intent);

        } else if (view == textLogout) {
            toggleDrawer();
            alertLogout();

        } else if (view == textHome) {
            toggleDrawer();
            Intent intent = new Intent(ActivityUnifiedTenancyContract.this, ActivityHome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
    }
}
