package com.paya.paragon.activity.postProperty;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.paya.paragon.R;
import com.paya.paragon.adapter.CityAdapter;
import com.paya.paragon.adapter.SearchResultAdapter;
import com.paya.paragon.api.cityList.CityListingApi;
import com.paya.paragon.api.cityList.CityListingResponse;
import com.paya.paragon.api.index.LocationInfo;
import com.paya.paragon.api.listLocProject.ListLocProjApi;
import com.paya.paragon.api.listLocProject.ListLocProjectData;
import com.paya.paragon.api.listLocProject.ListLocProjectResponse;
import com.paya.paragon.api.listLocProject.LocalitiesApi;
import com.paya.paragon.api.listLocProject.RegionDataChild;
import com.paya.paragon.api.listLocProject.SearchDataLocProj;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.databinding.ActivityPostPropertyPage02Binding;
import com.paya.paragon.utilities.ExtensionKt;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class PostPropertyPage02Activity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener {

    private SupportMapFragment supportMapFragment;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap googleMap;
    private LocationRequest locationRequest;

    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 101;


    private ActivityPostPropertyPage02Binding binding;

    ArrayList<LocationInfo> Citylist = new ArrayList<>();
    ArrayList<LocationInfo> SearchedCityList;
    private ListLocProjectData NeighbourhoodList;
    private ArrayList<SearchDataLocProj> searchDataLocProjs;
    String locationId;
    Double latitude = 33.2232, longitude = 43.6793;

    CityAdapter cityAdapter;
    private RegionDataChild regionDataChild;
    Boolean mCityDropdownclicked = false, mEditTextFocused = false;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_property_page_02);
        binding.setActivity(this);
        setMapFragment();

        try {
            if (PostPropertyPage01Activity.m_PostPropertyAPIData.getGoogleCityName() != null && !PostPropertyPage01Activity.m_PostPropertyAPIData.getGoogleCityName().isEmpty()) {
                binding.cityTv.setText(PostPropertyPage01Activity.m_PostPropertyAPIData.getSelectedCity());
                locationId = GlobalValues.selectedAddlocationCityID;
            } else {
                GlobalValues.regionDataTempAP = new ArrayList<>();
            }

            if (PostPropertyPage01Activity.m_PostPropertyAPIData.getPropertyAddress1() != null && !PostPropertyPage01Activity.m_PostPropertyAPIData.getPropertyAddress1().isEmpty()) {
                binding.addressEt.setText(PostPropertyPage01Activity.m_PostPropertyAPIData.getPropertyAddress1());
            }
            if (PostPropertyPage01Activity.m_PostPropertyAPIData.getNeighbourhood() != null && !PostPropertyPage01Activity.m_PostPropertyAPIData.getNeighbourhood().isEmpty()) {
                binding.searchEditText.setText(PostPropertyPage01Activity.m_PostPropertyAPIData.getNeighbourhood());
                binding.searchEditText.clearFocus();
            }
            String propLatitude = PostPropertyPage01Activity.m_PostPropertyAPIData.getPropertyLatitude();
            if (!TextUtils.isEmpty(propLatitude)) {
                longitude = ExtensionKt.formatToDouble(propLatitude);
            }
            String propLongitude = PostPropertyPage01Activity.m_PostPropertyAPIData.getPropertyLongitude();
            if (!TextUtils.isEmpty(propLongitude)) {
                longitude = ExtensionKt.formatToDouble(propLongitude);
            }
        } catch (Exception exception) {
            Timber.e(exception);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, binding.cl02Parent);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setMapFragment() {
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        binding.cityTv.setOnClickListener(view -> {

            if (!mCityDropdownclicked) {
                binding.cityRcv.setVisibility(View.VISIBLE);
                binding.searchResultLay.setVisibility(View.GONE);

                cityAdapter = new CityAdapter(Citylist, locationInfo -> {
                    binding.cityTv.setText(locationInfo.getCityName());
                    binding.searchEditText.setText("");
                    binding.searchEditText.clearFocus();
                    binding.cityTv.clearFocus();
                    binding.cityRcv.setVisibility(View.GONE);
                    locationId = locationInfo.getCityID();
                    NeighbourhoodList = null;
                    PostPropertyPage01Activity.m_PostPropertyAPIData.setSelectedCity(locationInfo.getCityName());

                    PostPropertyPage01Activity.m_PostPropertyAPIData.setGoogleCityName(locationInfo.getCityID());
                    GlobalValues.selectedAddlocationCityID = locationInfo.getCityID();
//                    binding.searchEditText.requestFocus();
//                    binding.searchEditText.performClick();
                    getNeighbourhoodList("", locationId, 0);
                    binding.searchEditText.setText("");
                });
                binding.cityRcv.setAdapter(cityAdapter);
                mCityDropdownclicked = true;
            } else {
                binding.cityRcv.setVisibility(View.GONE);

                mCityDropdownclicked = false;
            }

        });
        getCityList();
        /*binding.searchEditText.setOnTouchListener((view, motionEvent) -> {
            if (binding.searchResultLay.getVisibility() == View.VISIBLE) {
                binding.searchEditText.clearFocus();
                mEditTextFocused = false;

                Utils.hideKeyboard(PostPropertyPage02Activity.this, binding.searchEditText);
                return true;
            }

            return false;
        });*/
        binding.searchEditText.setOnClickListener(view -> {

            if (binding.searchResultLay.getVisibility() == View.VISIBLE) {
                binding.searchResultLay.setVisibility(View.GONE);
            } else {
                if (locationId != null && !locationId.isEmpty()) {
                    if (NeighbourhoodList == null) {
                        getNeighbourhoodList("", locationId, 1);
                    } else {
                        setSearchResult(NeighbourhoodList);
                    }
                } else {
                    Toast.makeText(PostPropertyPage02Activity.this, R.string.text_pls_select_city, Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*binding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() > 0) {
                    Timber.tag("focus").d("Edit Listener");

                    if (!mEditTextFocused) {
                        Timber.tag("focus").d("Inside condition");

                        getNeighbourhoodList(editable.toString().trim(), locationId, 1);
                    }
                } else {
                    binding.searchResultLay.setVisibility(View.GONE);
                }

            }
        });*/


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable == binding.addressEt.getEditableText()) {
                    PostPropertyPage01Activity.m_PostPropertyAPIData.setPropertyAddress1(binding.addressEt.getText().toString().trim());
                } else if (editable == binding.tvLatitude.getEditableText()) {
                    PostPropertyPage01Activity.m_PostPropertyAPIData.setPropertyLatitude(binding.tvLatitude.getText().toString().trim());

                } else if (editable == binding.tvLongitude.getEditableText()) {
                    PostPropertyPage01Activity.m_PostPropertyAPIData.setPropertyLongitude(binding.tvLongitude.getText().toString().trim());

                }
            }
        };

        binding.addressEt.addTextChangedListener(textWatcher);
        binding.tvLatitude.addTextChangedListener(textWatcher);
        binding.tvLongitude.addTextChangedListener(textWatcher);

    }

    @Override
    public void onMapReady(GoogleMap map) {

        googleMap = map;
        googleMap.getUiSettings().setAllGesturesEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                googleMap.setMyLocationEnabled(true);
//                googleMap.getUiSettings().setMapToolbarEnabled(true);
//                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
//                googleMap.getUiSettings().setScrollGesturesEnabled(false);
                checkLocationService(0);
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                );
            }
        } else {
//            googleMap.setMyLocationEnabled(true);
//            googleMap.getUiSettings().setMapToolbarEnabled(true);
//            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
//            googleMap.getUiSettings().setScrollGesturesEnabled(false);
//

            checkLocationService(0);
        }

//

//        googleMap.setOnCameraMoveStartedListener(this);
//        googleMap.setOnCameraIdleListener(this);
//        googleMap.setOnCameraMoveListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
//            googleMap.setMyLocationEnabled(true);
//            googleMap.getUiSettings().setMapToolbarEnabled(true);
//            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            checkLocationService(0);
        }
    }

    /*private void fetchCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            );
            return;
        } else {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        if (PostPropertyPage01Activity.m_PostPropertyAPIData.getPropertyLatitude() != null &&
                                PostPropertyPage01Activity.m_PostPropertyAPIData.getPropertyLongitude() != null) {
                            currentLatLng = new LatLng(Double.parseDouble("33.2232"),
                                    Double.parseDouble("43.6793"));
                        }
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 8f));
                    }
                }
            });

        }
    }*/

    private void checkLocationService(int from) {
        LatLng loc = new LatLng(latitude, longitude);
        if (from == 0)
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 3f));
        else
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 10f));



       /* locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000);
        locationRequest.setFastestInterval(2 * 1000);


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                locationSettingsResponse.getLocationSettingsStates();
                fetchCurrentLocation();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    try {
                        ((ResolvableApiException) e).startResolutionForResult(PostPropertyPage02Activity.this, REQUEST_CHECK_SETTINGS);
                    } catch (Exception ex) {
                        FirebaseCrashlytics.getInstance().recordException(e);
                    }
                }
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int REQUEST_CHECK_SETTINGS = 101;
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                checkLocationService(0);
//                fetchCurrentLocation();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public void onCameraMoveStarted(int i) {
        if (googleMap != null) {
            googleMap.clear();
        }
    }

    @Override
    public void onCameraIdle() {
        LatLng latLng = googleMap.getCameraPosition().target;
//        getAddressUsingLatLong(latLng.latitude, latLng.longitude);
    }

    private void getAddressUsingLatLong(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (!addresses.isEmpty()) {
                for (Address address : addresses) {
                    setAddressFromMap(address, latitude, longitude);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setAddressFromMap(Address address, double latitude, double longitude) {
        PostPropertyPage01Activity.m_PostPropertyAPIData.setGoogleSearchText(address.getLocality());
        PostPropertyPage01Activity.m_PostPropertyAPIData.setGoogleLocality(address.getLocality());
        if (address.getAdminArea() != null &&
                address.getSubAdminArea() != null &&
                address.getAdminArea().equalsIgnoreCase("Erbil Governorate") &&
                address.getSubAdminArea().equalsIgnoreCase("Erbil")) {

            PostPropertyPage01Activity.m_PostPropertyAPIData.setGoogleStateName("Kurdistan Region");
        } else {
            PostPropertyPage01Activity.m_PostPropertyAPIData.setGoogleStateName(address.getAdminArea());
        }
        PostPropertyPage01Activity.m_PostPropertyAPIData.setGoogleCountryName(address.getCountryName());
        PostPropertyPage01Activity.m_PostPropertyAPIData.setPropertyLatitude(String.valueOf(latitude));
        PostPropertyPage01Activity.m_PostPropertyAPIData.setPropertyLongitude(String.valueOf(longitude));
        PostPropertyPage01Activity.m_PostPropertyAPIData.setPropertyZipCode(address.getPostalCode());
        PostPropertyPage01Activity.m_PostPropertyAPIData.setGoogleCityName(address.getLocality() != null && !address.getLocality().isEmpty() ? address.getLocality() + ", " + address.getSubAdminArea() : address.getSubAdminArea());
        try {
            PostPropertyPage01Activity.m_PostPropertyAPIData.setPropertyAddress1(address.getAddressLine(0));
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            PostPropertyPage01Activity.m_PostPropertyAPIData.setPropertyAddress1(address.getLocality());
        }
        PostPropertyPage01Activity.m_PostPropertyAPIData.setPropertyAddress2("");


     /*   binding.tvAddress.setText(PostPropertyPage01Activity.m_PostPropertyAPIData.getPropertyAddress1());
        binding.tvCity.setText(address.getLocality() != null && !address.getLocality().isEmpty() ? address.getLocality() + ", " + address.getSubAdminArea() : address.getSubAdminArea());
        binding.tvLatitude.setText(Utils.getDecimalFormat(latitude, Utils.DECIMAL_4));
        binding.tvLongitude.setText(Utils.getDecimalFormat(longitude, Utils.DECIMAL_4));*/
    }

    @Override
    public void onCameraMoveCanceled() {

    }

    @Override
    public void onCameraMove() {

    }

    public void onBackClick() {
        onBackPressed();
    }


    public void onNextClick() {


        if (PostPropertyPage01Activity.m_PostPropertyAPIData.getGoogleCityName() != null && !PostPropertyPage01Activity.m_PostPropertyAPIData.getGoogleCityName().isEmpty()) {
            if (PostPropertyPage01Activity.m_PostPropertyAPIData.getNeighbourhood() != null && !PostPropertyPage01Activity.m_PostPropertyAPIData.getNeighbourhood().isEmpty()) {
                startActivity(new Intent(PostPropertyPage02Activity.this, PostPropertyPage05Activity.class));

            } else {
                Toast.makeText(this, "Please select neighbourhood.", Toast.LENGTH_SHORT).show();

            }


        } else {
            Toast.makeText(this, getString(R.string.no_address_found), Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(PostPropertyPage02Activity.this, PostPropertyPage05Activity.class));


        }
    }


    public void getCityList() {
        ApiLinks.getClient().create(CityListingApi.class).post("" + SessionManager.getLanguageID(this))
                .enqueue(new Callback<CityListingResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CityListingResponse> call, @NonNull Response<CityListingResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getResponse();
                            if (message != null && message.equalsIgnoreCase("Success")) {
                                Citylist.addAll(response.body().getData().getCityList());
                                for (LocationInfo locationInfo : Citylist) {
                                    if (SessionManager.getLocationId(PostPropertyPage02Activity.this).equalsIgnoreCase(locationInfo.getCityID())) {
                                        binding.cityTv.setText(locationInfo.getCityName());
                                        PostPropertyPage01Activity.m_PostPropertyAPIData.setGoogleCityName(locationInfo.getCityID());
                                        SessionManager.setLocationName(PostPropertyPage02Activity.this, locationInfo.getCityName(), locationInfo.getCityLat(), locationInfo.getCityLng());
                                        locationId = locationInfo.getCityID();
                                        NeighbourhoodList = null;
                                        getNeighbourhoodList("", locationId, 0);
                                        break;
                                    }
                                }
                            } else {
                                Toast.makeText(PostPropertyPage02Activity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CityListingResponse> call, @NonNull Throwable t) {

                    }
                });
    }

    public void citySearchList(String newText) {
        SearchedCityList = new ArrayList<>();
        for (LocationInfo info : Citylist) {
            if (info.getCityName().toLowerCase().contains(newText.toLowerCase())) {
                SearchedCityList.add(info);
            }
        }
        if (!SearchedCityList.isEmpty()) {
            cityAdapter.updateCity(SearchedCityList);
        }
    }

    public void getNeighbourhoodList(String query, String locationId, int value) {
//        binding.searchProgress.setVisibility(View.GONE);
        ApiLinks.getClient().create(LocalitiesApi.class).post(
                        locationId,
                        SessionManager.getLanguageID(this),
                        query
                        )
                .enqueue(new Callback<ListLocProjectResponse>() {
                    @Override
                    public void onResponse(Call<ListLocProjectResponse> call, Response<ListLocProjectResponse> response) {
                        if (response != null && response.body() != null) {
                            String message = response.body().getMessage() != null ? response.body().getMessage() : "";
                            if (response.body().getResponse().equalsIgnoreCase("Success")) {

                                Timber.tag("focus").e(String.valueOf(value));
                                NeighbourhoodList = response.body().getData();

                                if (value == 1)
                                    setSearchResult(NeighbourhoodList);
                            } else {

                                binding.searchResultLay.setVisibility(View.GONE);
                            }

                        } else {

                        }
//                        binding.searchProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ListLocProjectResponse> call, Throwable t) {

                    }
                });
    }

    public void setSearchResult(ListLocProjectData locProjectData) {

        SearchDataLocProj searchDataLocProj = new SearchDataLocProj();
        SearchDataLocProj searchDataLocProj1 = new SearchDataLocProj();
        SearchDataLocProj searchDataLocProj2 = new SearchDataLocProj();
        searchDataLocProjs = new ArrayList<>();
        if (locProjectData.getLocation().size() > 0) {
            searchDataLocProj.setName("Community");
            locProjectData.addOtherLocation();
            searchDataLocProj.setChild(locProjectData.getLocation());
            searchDataLocProjs.add(searchDataLocProj);
        }
        if (locProjectData.getProjects().size() > 0) {
            searchDataLocProj1.setName("Projects");
            locProjectData.addOtherLocation();
            searchDataLocProj1.setChild(locProjectData.getProjects());
            searchDataLocProjs.add(searchDataLocProj1);
        }

        runOnUiThread(() -> {
            binding.searchResultLay.setVisibility(View.VISIBLE);
            binding.searchResult.setNestedScrollingEnabled(false);
            final SearchResultAdapter searchResultAdapter = new SearchResultAdapter(PostPropertyPage02Activity.this,
                    searchDataLocProjs);
            binding.searchResult.setAdapter(searchResultAdapter);

            for (int i = 0; i < searchResultAdapter.getGroupCount(); i++)
                binding.searchResult.expandGroup(i);

            binding.searchResult.setOnGroupClickListener((parent, v, groupPosition, id) -> parent.isGroupExpanded(groupPosition));

            binding.searchResult.setOnChildClickListener((expandableListView, view, i, i1, l) -> {
                regionDataChild = searchResultAdapter.getChild(i, i1);
                if (regionDataChild != null) {
                    mEditTextFocused = true;


                    binding.searchEditText.setText(regionDataChild.getOriginalText());
                    PostPropertyPage01Activity.m_PostPropertyAPIData.setNeighbourhood(regionDataChild.getOriginalText());
                    PostPropertyPage01Activity.m_PostPropertyAPIData.setGoogleLocality(regionDataChild.getId());
//                    Utils.hideKeyboard(PostPropertyPage02Activity.this, binding.searchEditText);
                    searchDataLocProjs.clear();
                    binding.searchResultLay.setVisibility(View.GONE);
//                    binding.searchEditText.clearFocus();
                    latitude = ExtensionKt.formatToDouble(regionDataChild.getLatitude());
                    longitude = ExtensionKt.formatToDouble(regionDataChild.getLongnitude());
                    PostPropertyPage01Activity.m_PostPropertyAPIData.setPropertyLatitude(String.valueOf(latitude));
                    PostPropertyPage01Activity.m_PostPropertyAPIData.setPropertyLongitude(String.valueOf(longitude));
                    checkLocationService(1);
                    binding.addressEt.setText("");
                }
                return false;
            });
        });


    }


}

