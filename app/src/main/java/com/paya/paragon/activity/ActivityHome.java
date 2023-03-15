package com.paya.paragon.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.paya.paragon.R;
import com.paya.paragon.PropertyProjectListActivity;
import com.paya.paragon.activity.dashboard.ActivityMyProperties;
import com.paya.paragon.activity.dashboard.ActivitySavedSearchList;
import com.paya.paragon.activity.localExpert.ActivityLocalExpertDashboard;
import com.paya.paragon.activity.login.ActivityLoginEmail;
import com.paya.paragon.activity.postProperty.PostPropertyPage01Activity;
import com.paya.paragon.activity.postRequirements.ActivityRequirementPurpose;
import com.paya.paragon.activity.search.SearchOptionActivity;
import com.paya.paragon.api.langaugeList.LanguageList;
import com.paya.paragon.api.langaugeList.LanguageListApi;
import com.paya.paragon.api.langaugeList.LanguageListResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("HardCodedStringLiteral")
public class ActivityHome extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    public DrawerLayout drawer;
    NavigationView navigationView;
    LinearLayout navLogin, navLogout;
    LinearLayout navBuy, navRent, navExpertCommunity, navListProperty, navDashboard;
    LinearLayout navSavedSearch, navMyProperty, navSavingsCalculator, navHowMuchWorth;
    LinearLayout navPostRequirements;
    LinearLayout navContactUs;

    MenuItem like, notification;
    TextView buy, rent, newProjects, listProperty, languageText, languageTextIcon;
    TextView localExperts, contactUs;
    public static Activity ActivityHome;
    private String langName = "", langCode = "/en/";
    DialogProgress progress;
    int finalI = 0;
    private ArrayList<LanguageList> languageLists;

    String phoneNumberValue = "+964 7501297777";

    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private String PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=com.paya.paragon&hl=en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home1);
        langName = SessionManager.getLanguageName(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_icon);
        ActivityHome = this;
        declarations();
        SessionManager.setStateID(this, 1);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.menu_icon);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (langName.contains(Utils.LAG_ARABIC)) {
                    langCode = "/ar/";
                } else if (langName.contains(Utils.LAG_KURDISH)) {
                    langCode = "/ku/";
                }
                drawer.openDrawer(GravityCompat.START);
            }
        });
        toggle.syncState();
        if (langName.contains(Utils.LAG_ARABIC))
            langCode = "/ar/";
        else if (langName.contains(Utils.LAG_KURDISH))
            langCode = "/ku/";
        else langCode = "/en/";
        navigationView.setNavigationItemSelectedListener(this);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.NoInternetConnection(ActivityHome.this)) {
                    Intent intent = new Intent(ActivityHome.this, SearchOptionActivity.class);
                    intent.putExtra("comingFrom", "Buy");
                    startActivity(intent);
                } else {
                    Utils.showAlertNoInternet(ActivityHome.this);
                }
            }
        });

        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.NoInternetConnection(ActivityHome.this)) {
                    Intent intent = new Intent(ActivityHome.this, SearchOptionActivity.class);
                    intent.putExtra("comingFrom", "Rent");
                    startActivity(intent);
                } else {
                    Utils.showAlertNoInternet(ActivityHome.this);
                }
            }
        });

        navRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
                Intent intent = new Intent(ActivityHome.this, SearchOptionActivity.class);
                intent.putExtra("comingFrom", "Rent");
                startActivity(intent);
            }
        });

        navBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
                Intent intent = new Intent(ActivityHome.this, SearchOptionActivity.class);
                intent.putExtra("comingFrom", "Buy");
                startActivity(intent);
            }
        });

        navExpertCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
                Intent intent = new Intent(ActivityHome.this, ActivityLocalExpertDashboard.class);
                startActivity(intent);
            }
        });

        navPostRequirements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
                Intent intent = new Intent(ActivityHome.this, ActivityRequirementPurpose.class);
                intent.putExtra("action", "new");
                startActivity(intent);
            }
        });

        navListProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
                if (SessionManager.getLoginStatus(ActivityHome.this)) {
                    Intent intent = new Intent(ActivityHome.this, PostPropertyPage01Activity.class);
                    startActivity(intent);
                } else {
                    Intent intentLogin = new Intent(ActivityHome.this, ActivityLoginEmail.class);
                    startActivity(intentLogin);
                }
            }
        });

        navMyProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
                if (SessionManager.getLoginStatus(ActivityHome.this)) {
                    Intent intent = new Intent(ActivityHome.this, ActivityMyProperties.class);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(ActivityHome.this, ActivityLoginEmail.class).putExtra("comingFrom", "menu"));

                }
            }
        });
        navDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
                if (SessionManager.getLoginStatus(ActivityHome.this)) {
                    Intent intent = new Intent(ActivityHome.this, ActivityMyProperties.class);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(ActivityHome.this, ActivityLoginEmail.class).putExtra("comingFrom", "menu"));

                }
            }
        });

        navSavingsCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
                Intent intent = new Intent(ActivityHome.this, CalculatorWebView.class);
                intent.putExtra("url", langCode + "calculator/appView/");
                startActivity(intent);
            }
        });

        navLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
                startActivity(new Intent(ActivityHome.this, ActivityLoginEmail.class).putExtra("comingFrom", "menu"));

            }
        });

        localExperts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
                Intent intent = new Intent(ActivityHome.this, ActivityLocalExpertDashboard.class);
                startActivity(intent);
            }
        });

        ///////////////////////////
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ActivityHome.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ActivityHome.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 123);
                } else {
                    try {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumberValue));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(ActivityHome.this, "No Application found to perform this action",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        navSavedSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
                if (SessionManager.getLoginStatus(ActivityHome.this)) {
                    Intent intent = new Intent(ActivityHome.this, ActivitySavedSearchList.class);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(ActivityHome.this, ActivityLoginEmail.class).putExtra("comingFrom", "menu"));

                }
            }
        });

        newProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ActivityHome.this, PropertyProjectListActivity.class);
                in.putExtra("searchPropertyPurpose", "Sell");
                in.putExtra("newProject", true);
                GlobalValues.clearBuyGlobalValues();
                startActivity(in);
            }
        });

        navLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
                SessionManager.logout(ActivityHome.this);
                navLogin.setVisibility(View.VISIBLE);
                navLogout.setVisibility(View.GONE);
                navDashboard.setVisibility(View.GONE);
                navLogin.requestFocus();
                Intent intent = new Intent(ActivityHome.this, ActivityHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
        firebaseTokenRetrive();
    }

    private void firebaseTokenRetrive() {
        if (Utils.isGooglePlayServiceActivated(this)) {
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                return;
                            }

                            // Get new Instance ID token
                            String token = task.getResult().getToken();
                        }
                    });
        } else {
            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 123: {

                if (ContextCompat.checkSelfPermission(ActivityHome.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                } else {
                    try {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumberValue));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(ActivityHome.this, "No Application found to perform this action",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                return;
            }
        }
    }

    public void toggleDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START); //CLOSE Nav Drawer!
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END); //CLOSE Nav Drawer!
        }
    }

    private void initializeFirebaseRemoteConfig() {
        try {

            mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
            FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(0)
                    .build();
            mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
            mFirebaseRemoteConfig.setDefaultsAsync(R.xml.firebase_remote_config_default);
            mFirebaseRemoteConfig.fetchAndActivate();
            checkAppUpdate();
        } catch (Exception e) {

        }
    }

    private void checkAppUpdate() throws Exception {
        try {
            String currentAppVersion = getAppVersion();
            String softUpdateVersion = mFirebaseRemoteConfig.getString("soft_update_version_no");
            String forceUpdateVersion = mFirebaseRemoteConfig.getString("force_update_version_no");
            if (!TextUtils.isEmpty(currentAppVersion) && !TextUtils.isEmpty(forceUpdateVersion) && !TextUtils.isEmpty(softUpdateVersion)) {
                if (Integer.parseInt(currentAppVersion.replace(".", "")) < Integer.parseInt(forceUpdateVersion.replace(".", ""))) {
                    forceUpdatePopUp();
                    return;
                } else if (Integer.parseInt(currentAppVersion.replace(".", "")) < Integer.parseInt(softUpdateVersion.replace(".", ""))) {
                    softUpdateUpdatePopUp();
                    return;
                }
            }
        } catch (Exception e) {

        }
    }

    private void forceUpdatePopUp() throws Exception {
        AlertDialog updateApp;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getString(R.string.text_app_update_title))
                .setMessage(getString(R.string.text_force_update_message))
                .setCancelable(false);
        alertDialog.setPositiveButton(getString(R.string.text_update), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openStoreAction();
                dialog.dismiss();
            }
        });
        updateApp = alertDialog.create();
        updateApp.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                updateApp.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(ActivityHome.this, R.color.yellow));
            }
        });
        updateApp.show();
    }

    private void softUpdateUpdatePopUp() throws Exception {
        AlertDialog updateApp;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getString(R.string.text_app_update_title))
                .setMessage(getString(R.string.text_soft_update_message))
                .setCancelable(false);
        alertDialog.setPositiveButton(getString(R.string.text_update), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openStoreAction();
                dialog.dismiss();
            }
        });

        alertDialog.setNegativeButton(getString(R.string.text_skip), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        updateApp = alertDialog.create();
        updateApp.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                updateApp.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(ActivityHome.this, R.color.yellow));
                updateApp.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(ActivityHome.this, R.color.yellow));
            }
        });
        updateApp.show();
    }

    private void openStoreAction() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(applicationLink())));
    }

    private String applicationLink() {
        return String.format(PLAY_STORE_LINK, getPackageName());
    }

    private String getAppVersion() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void declarations() {
        buy = (TextView) findViewById(R.id.button_buy_property_home);

        rent = (TextView) findViewById(R.id.button_rent_property_home);
        newProjects = (TextView) findViewById(R.id.button_new_projects_home);
        //listProperty = (TextView) findViewById(R.id.button_list_property_home);
        localExperts = (TextView) findViewById(R.id.button_local_experts_home);
        contactUs = (TextView) findViewById(R.id.button_call_us_home);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_home);
        navigationView = (NavigationView) findViewById(R.id.nav_view_home);
        navLogin = navigationView.findViewById(R.id.nav_item_login);
        navLogout = navigationView.findViewById(R.id.nav_item_logout);
        navRent = navigationView.findViewById(R.id.nav_item_rent);
        navBuy = navigationView.findViewById(R.id.nav_item_buy);
        navDashboard = navigationView.findViewById(R.id.nav_item_dashboard);
        navExpertCommunity = navigationView.findViewById(R.id.nav_item_expert_community);
        navListProperty = navigationView.findViewById(R.id.nav_item_list_property);
        navPostRequirements = navigationView.findViewById(R.id.nav_item_post_requirements);
        navSavedSearch = navigationView.findViewById(R.id.nav_item_saved_searches);
        navMyProperty = navigationView.findViewById(R.id.nav_item_my_properties);
        navSavingsCalculator = navigationView.findViewById(R.id.nav_item_savings_calculator);
        navHowMuchWorth = navigationView.findViewById(R.id.nav_item_how_much_worth);
        navContactUs = navigationView.findViewById(R.id.nav_item_contact_us);

        progress = new DialogProgress(this);
        languageLists = new ArrayList<>();
        languageText.setText(langName);

        if (langName.equalsIgnoreCase(Utils.LAG_ARABIC)) {
            languageTextIcon.setText("ar");
        } else if (langName.equalsIgnoreCase(Utils.LAG_ENGLISH)) {
            languageTextIcon.setText("en");
        } else languageTextIcon.setText("ku");

        getLanguageList();
        appVersion();
    }

    private void appVersion() {
        TextView appVersion = findViewById(R.id.tv_app_version);
        appVersion.setText(Utils.getPackageVersionName(this));
    }

    public void getLanguageList() {
        // progress.show();
        ApiLinks.getClient().create(LanguageListApi.class).post()
                .enqueue(new Callback<LanguageListResponse>() {
                    @Override
                    public void onResponse(Call<LanguageListResponse> call, Response<LanguageListResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getResponse();
                            if (message.equalsIgnoreCase("Success")) {
                                languageLists.clear();
                                languageLists.addAll(response.body().getData().getLanguageLists());
                                //todo need to remove after coming from API Response
                                LanguageList languageList = new LanguageList();
                                languageList.setLanguageID(Utils.LAG_KURDISH_ID);
                                languageList.setLanguageName(Utils.LAG_KURDISH);
                                languageLists.add(languageList);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LanguageListResponse> call, Throwable t) {
                    }
                });
    }

    private Boolean exit = false;

    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
        } else {
            Toast.makeText(this, getString(R.string.press_back_again),
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2 * 1000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.drawer_layout_home));
        initializeFirebaseRemoteConfig();
        if (SessionManager.getLoginStatus(ActivityHome.this)) {
            navLogin.setVisibility(View.GONE);
            navDashboard.setVisibility(View.VISIBLE);
            navLogout.setVisibility(View.VISIBLE);

        } else {
            navLogin.setVisibility(View.VISIBLE);
            navDashboard.setVisibility(View.GONE);
            navLogout.setVisibility(View.GONE);
        }
        if (!langName.equalsIgnoreCase(SessionManager.getLanguageName(this)))
            recreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        like = menu.findItem(R.id.home_menu_like);
        notification = menu.findItem(R.id.home_menu_alert);
        like.setVisible(false);
        notification.setVisible(false);

        MenuItem menuLanguage = menu.findItem(R.id.home_menu_language);
        menuLanguage.setIcon(langName.contains(Utils.LAG_ENGLISH) ? R.drawable.menu_flag_uk : langName.contains(Utils.LAG_ARABIC) ? R.drawable.menu_flag_iraq : R.drawable.flag_kurdish);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.home_menu_alert:
                return true;
            case R.id.home_menu_language:
                navigateToLanguageActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToLanguageActivity() {
        Intent intent = new Intent(this, LanguageActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        toggleDrawer();
        return true;
    }

    public void toggleLanguage(LanguageList languageList) {
        String languageText = languageList.getLanguageName().equalsIgnoreCase(Utils.LAG_ARABIC) ? "ar" :
                languageList.getLanguageName().equalsIgnoreCase(Utils.LAG_ENGLISH) ? "en" : "ku";
        languageTextIcon.setText(languageText);
        Locale locale = new Locale(languageText);
        SessionManager.setLanguageID(this, languageList.getLanguageID());
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        SessionManager.setLanguageName(this, languageList.getLanguageName());
        Intent intent = new Intent(ActivityHome.this, ActivityHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }


}
