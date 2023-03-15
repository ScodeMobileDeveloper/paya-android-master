package com.paya.paragon.activity.buy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

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
import com.google.maps.android.clustering.ClusterManager;
import com.paya.paragon.PropertyProjectListActivity;
import com.paya.paragon.R;
import com.paya.paragon.activity.details.ActivityProjectDetails;
import com.paya.paragon.activity.details.ActivityPropertyDetails;
import com.paya.paragon.api.BuyProjects.BuyProjectsModel;
import com.paya.paragon.api.FetchLocalInformationData.LocalInformationData;
import com.paya.paragon.api.FetchLocalInformationData.LocalInformationDataApi;
import com.paya.paragon.api.FetchLocalInformationData.LocalInformationDataResponse;
import com.paya.paragon.api.buy_properties.BuyPropertiesModel;
import com.paya.paragon.api.localInformationList.LocalInformationListApi;
import com.paya.paragon.api.localInformationList.LocalInformationListData;
import com.paya.paragon.api.localInformationList.LocalInformationListResponse;
import com.paya.paragon.base.BaseViewModelActivity;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.databinding.ActivityLocationsBinding;
import com.paya.paragon.model.PropertyProjectItems;
import com.paya.paragon.utilities.CustomClusterRender;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.ExtensionKt;
import com.paya.paragon.utilities.ListDialogBox;
import com.paya.paragon.utilities.PropertyProjectType;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;
import com.paya.paragon.viewmodel.LocationActivityViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationsActivity extends BaseViewModelActivity<LocationActivityViewModel> implements OnMapReadyCallback, LocationActivityViewModel.LocationActivityCallBack {
    String from;
    String propertyID;
    private GoogleMap mMap;
    List<BuyPropertiesModel> propertyLists;
    List<BuyProjectsModel> projectLists;
    RelativeLayout rlHead;
    TextView tvLocal;
    List<String> nameList;
    public static int selectedPosition = 100;

    List<LocalInformationData> localInformationList;
    List<LocalInformationListData> localList;
    Marker markerMain = null;
    Marker markerSub = null;
    String selectedTypeId = "";
    String selectedTypeName = "";
    private DialogProgress mLoadingDialog;
    List<LatLng> views;
    FloatingActionButton fabList;
    HashMap<String, String> savedLocation;
    String latitude, longitude;
    int count_proeprty;
    private PropertyProjectType type;
    private ActivityLocationsBinding binding;
    private LocationActivityViewModel viewModel;

    private ClusterManager<PropertyProjectItems> clusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_locations);
        initializeMap();
        initializeToolBar();
        from = getIntent().getStringExtra("from");
        Intent intent = getIntent();
        if (from.equals("Properties")) {
            binding.toolbarTitle.setText(getResources().getString(R.string.properties));
//            Bundle args = intent.getBundleExtra("bundle_property");
//            propertyLists = (ArrayList<BuyPropertiesModel>) args.getSerializable("property_list_array");
            this.propertyLists = PropertyProjectListActivity.propertyLists_new;
        } else if (from.equals("Projects")) {
            binding.toolbarTitle.setText(getString(R.string.projects));
            this.projectLists = PropertyProjectListActivity.projectLists;
        }
        onCreateViewModel();
        type = (PropertyProjectType) intent.getSerializableExtra(Utils.TYPE);
        mLoadingDialog = new DialogProgress(LocationsActivity.this);
        savedLocation = SessionManager.getLocationValues(LocationsActivity.this);
        latitude = savedLocation.get("lat");
        longitude = savedLocation.get("long");
        getLocalList();
        rlHead = findViewById(R.id.rl_head);
        tvLocal = findViewById(R.id.tv_local);
        fabList = findViewById(R.id.fab_revert_to_list);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        rlHead.setOnClickListener(view -> {
            ListDialogBox dialogBox = new ListDialogBox(LocationsActivity.this, nameList, "Local Information", "location");
            dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogBox.show();
            dialogBox.setOnDismissListener(dialogInterface -> {
                if (selectedPosition != 100) {
                    if (mMap != null) {
                        mMap.clear();
                    }
                    //setMap();
                    tvLocal.setText(localList.get(selectedPosition).getLocalinfoTypeTitle());
                    selectedTypeId = localList.get(selectedPosition).getLocalinfoTypeID();
                    selectedTypeName = localList.get(selectedPosition).getLocalinfoTypeTitle();
                    getLocalData(selectedTypeId);
                }
            });
        });

        fabList.setOnClickListener(v -> finish());

    }

    private void initializeToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        mTitle.setTypeface(mTitle.getTypeface(), Typeface.BOLD);
    }

    @Override
    public void showLoader() {
        showAnimatedProgressBar(this);
    }

    @Override
    public void dismissLoader() {
        dismissAnimatedProgressBar();
    }

    @Override
    public Context getLocationActivityContext() {
        return LocationsActivity.this;
    }

    @Override
    public void loadPropertyMapData(List<BuyPropertiesModel> propertyLists) {
        this.propertyLists.addAll(propertyLists);
        if (mMap != null) {
            mMap.clear();
        }
        setMap();
    }

    @Override
    public void loadProjectMapData(List<BuyProjectsModel> projectLists) {
        this.projectLists.addAll(projectLists);
        if (mMap != null) {
            mMap.clear();
        }
        setMap();
    }

    @Override
    public LocationActivityViewModel onCreateViewModel() {
        viewModel = ViewModelProviders.of(this).get(LocationActivityViewModel.class);
        viewModel.init(this, getIntent(), from);
        binding.setViewmodel(viewModel);
        return viewModel;
    }

    private void initializeMap() {
        MapsInitializer.initialize(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.ll_location_parent_layout));
    }

    private void getLocalData(String selectedTypeId) {
        mLoadingDialog.show();
        ApiLinks.getClient().create(LocalInformationDataApi.class).post(selectedTypeId, latitude, longitude)
                .enqueue(new Callback<LocalInformationDataResponse>() {
                    @Override
                    public void onResponse(Call<LocalInformationDataResponse> call, Response<LocalInformationDataResponse> response) {
                        if (response.body().getResponse().equalsIgnoreCase("Success")) {
                            localInformationList = response.body().getLocalInformation();
                            setLocalInformation();
                        } else {
                            Toast.makeText(LocationsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        dismissDialog();
                    }

                    @Override
                    public void onFailure(Call<LocalInformationDataResponse> call, Throwable t) {
                        dismissDialog();
                        Toast.makeText(LocationsActivity.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void dismissDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dismissDialog();
        super.onDestroy();
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
                            Toast.makeText(LocationsActivity.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LocalInformationListResponse> call, Throwable t) {
                        Toast.makeText(LocationsActivity.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void setLocalInformation() {
        int height = 40;
        int width = 40;
        List<LatLng> views = new ArrayList<>();
        for (int i = 0; i < localInformationList.size(); i++) {
            BitmapDrawable bitmapdraw = Utils.getBitmapDrawableLocal(this, selectedTypeName, i);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
            double lat = ExtensionKt.formatToDouble(localInformationList.get(i).getInfoLat());
            double lon = ExtensionKt.formatToDouble(localInformationList.get(i).getInfoLng());
            views.add(new LatLng(lat, lon));
            markerSub = mMap.addMarker(new MarkerOptions()
                    .position(views.get(i))
                    .title(localInformationList.get(i).getInfoName())
                    .snippet(localInformationList.get(i).getInfoAddress())
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
            );
            markerSub.showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(views.get(i)));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0F));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMap();
    }

    @SuppressLint("PotentialBehaviorOverride")
    public void setMap() {
        if (from.equals("Properties")) {
            clusterManager = new ClusterManager<>(this, mMap);
            CustomClusterRender customClusterRender = new CustomClusterRender(this, mMap, clusterManager, getMarker());
            for (int i = 0; i < propertyLists.size(); i++) {
                count_proeprty = i;
                double lat = ExtensionKt.formatToDouble(propertyLists.get(i).getProLatitude());
                double lon = ExtensionKt.formatToDouble(propertyLists.get(i).getProLongitude());
                mMap.setOnCameraIdleListener(clusterManager);
                mMap.setOnMarkerClickListener(clusterManager.getMarkerManager());
                addClusterItme(new LatLng(lat, lon), propertyLists.get(i).getPropertyName(), propertyLists.get(i).getCityLocName(), propertyLists.get(i).getPropertyID());
                if (i == 0) {
                    if (viewModel.loadMore) {
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(mMap.getCameraPosition()));
                    } else {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 10f));
                    }
                }
                clusterManager.setOnClusterItemInfoWindowClickListener(item -> startActivity(new Intent(LocationsActivity.this, ActivityPropertyDetails.class).
                        putExtra("propertyID", item.getId())));

                clusterManager.setRenderer(customClusterRender);
                clusterManager.cluster();

            }
        } else if (from.equals("Projects")) {
            int height = 35;
            int width = 35;
            List<LatLng> views = new ArrayList<>();
            if (projectLists != null) {
                for (int i = 0; i < projectLists.size(); i++) {
                    BitmapDrawable bitmapdraw = (BitmapDrawable) AppCompatResources.getDrawable(this, getMarker());
                    Bitmap b = bitmapdraw.getBitmap();
                    final int projectId = i;
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                    double lat = ExtensionKt.formatToDouble(projectLists.get(i).getProLatitude());
                    double lon = ExtensionKt.formatToDouble(projectLists.get(i).getProLongitude());
                    views.add(new LatLng(lat, lon));
                    markerMain = mMap.addMarker(new MarkerOptions()
                            .position(views.get(i))
                            .title(projectLists.get(i).getProjectName())
                            .snippet(projectLists.get(i).getCityLocName())
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                    );
                    markerMain.setTag(String.valueOf(i));
                    markerMain.showInfoWindow();
                    mMap.setOnInfoWindowClickListener(marker -> {
                        if (marker != null && marker.getTag() != null) {
                            int temp = ExtensionKt.formatToInt(marker.getTag().toString());
                            startActivity(new Intent(LocationsActivity.this, ActivityProjectDetails.class).
                                    putExtra("projectID", projectLists.get(temp).getProjectID()));
                        }
                    });
                    if (i == 0)
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 12.0f));
                }
            }
        }
    }

    private void addClusterItme(LatLng latLng, String propertyName, String cityLocName, String iD) {
        clusterManager.addItem(new PropertyProjectItems(latLng, propertyName, cityLocName, iD));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
