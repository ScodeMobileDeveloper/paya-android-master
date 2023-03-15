package com.paya.paragon.activity.postRequirements;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.RelativeSizeSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paya.paragon.R;
import com.paya.paragon.adapter.AdapterDistricts;
import com.paya.paragon.api.districtList.DistrictListingApi;
import com.paya.paragon.api.districtList.DistrictListingData;
import com.paya.paragon.api.districtList.DistrictListingResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.model.RequirementModel;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"RedundantCast", "HardCodedStringLiteral"})
public class
ActivityRequirementLocation extends AppCompatActivity {

    TextView selectorLocality, textAddNewLocality, btnContinue;
    EditText editCity, editState, editLocality;
    public static int PLACE_AUTOCOMPLETE_REQUEST_CODE = 300;
    ArrayList<DistrictListingData> districtsList = new ArrayList<>();
    DialogProgress mLoading;
    Geocoder geocoder;
    Dialog dialog;
    Double latitude, longitude;
    String locality = "";
    private boolean locationManualModeEntry = false;
    private static RequirementModel requirementModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_requirement_location);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow_black);

        declarations();

        if (!Utils.NoInternetConnection(ActivityRequirementLocation.this)) {
            getDistrictList();
        } else {
            Utils.showAlertNoInternet(ActivityRequirementLocation.this);
        }

        selectorLocality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getLocationIntent();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

        editCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCityListSpinner();

            }
        });

    }


    //TODO Set Data
    private void declarations() {
        mLoading = new DialogProgress(ActivityRequirementLocation.this);
        selectorLocality = findViewById(R.id.text_location_selector_requirement_location);
        btnContinue = findViewById(R.id.text_continue_requirement_location);
        textAddNewLocality = findViewById(R.id.add_new_location_requirement_location);
        editState = findViewById(R.id.edit_state_requirement_location);
        editCity = findViewById(R.id.edit_city_requirement_location);
        editLocality = findViewById(R.id.edit_locality_requirement_location);
        geocoder = new Geocoder(ActivityRequirementLocation.this, Locale.getDefault());
        requirementModel = SessionManager.getPostRequirement(ActivityRequirementLocation.this);

        SpannableString strLocality = new SpannableString(getResources()
                .getString(R.string.location_selector_manual));
        strLocality.setSpan(new RelativeSizeSpan(1f),
                strLocality.length() - 28, strLocality.length(), 0);
        ClickableSpan add = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                enableEdit();
                editLocality.clearFocus();
                editLocality.requestFocus();
                locationManualModeEntry = true;
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.textOrange));
                textAddNewLocality.setHighlightColor(Color.TRANSPARENT);
            }
        };
        strLocality.setSpan(add, strLocality.length() - 28, strLocality.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textAddNewLocality.setText(strLocality);
        textAddNewLocality.setMovementMethod(LinkMovementMethod.getInstance());

        disableEdit();

        if (requirementModel.getLocality() != null && !requirementModel.getLocality().equals("")) {
            editLocality.setText(requirementModel.getLocality());
        }
        if (requirementModel.getCity() != null && !requirementModel.getCity().equals("")) {
            editCity.setText(requirementModel.getCity());
        }
        if (requirementModel.getState() != null && !requirementModel.getState().equals("")) {
            editState.setText(requirementModel.getState());
        }
        if (requirementModel.getLocalitySearchText() != null
                && !requirementModel.getLocalitySearchText().equals("")) {
            selectorLocality.setText(requirementModel.getLocalitySearchText());
        }

        if (requirementModel.getRequirementAction().equalsIgnoreCase("edit")){
            locationManualModeEntry = true;
        }
    }

    private static final LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(23.63936, 68.14712),
            new LatLng(28.20453, 97.34466));

  /*  private void getLocationIntent() {
        try {
            AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(Place.TYPE_COUNTRY)
                    .setCountry("IN")
                    .build();
            Intent intent = new PlaceAutocomplete
                    .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(ActivityRequirementLocation.this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                getDataFromPlacesApi(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Utils.showToastMessage(ActivityRequirementLocation.this, "Error").show();
            } else if (resultCode == RESULT_CANCELED) {
                Log.v("location api", "user cancelled.");
            }
        }
    }

    public void getDataFromPlacesApi(Place place) {
        locality = place.getName().toString();
        selectorLocality.setText(locality);
        latitude = place.getLatLng().latitude;
        longitude = place.getLatLng().longitude;
        try {
            ArrayList<Address> addressList = (ArrayList<Address>) geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList.size() > 0) {
                Address address = addressList.get(0);
                if (address.getAdminArea() != null && !address.getAdminArea().equals("")) {
                    editState.setText(address.getAdminArea());
                    editCity.setText(address.getAdminArea());
                } else {

                }
                ///editState.setText(locality);
                editLocality.setText(locality);
               // editCity.setText(locality);
                requirementModel.setCountry(address.getCountryName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @SuppressLint("SetTextI18n")
    private void enableEdit() {
        editCity.setEnabled(true);
        editLocality.setEnabled(true);
        editState.setEnabled(false);
       // editState.setText("Kerala");
        editCity.setHint(R.string.select);
        editCity.setHintTextColor(getResources().getColor(R.color.black));
        editCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
    }

    private void disableEdit() {
        editCity.setEnabled(false);
        editLocality.setEnabled(false);
        editState.setEnabled(false);
        editCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    private void checkValidation() {
        if (!locationManualModeEntry) {
            if (selectorLocality.getText().toString().equals("")) {
                showToastMessage("Please enter Locality");
                selectorLocality.clearFocus();
                selectorLocality.requestFocus();
                return;
            } else {
                if (requirementModel.getRequirementAction().equals("post")) {
                    requirementModel.setLocalitySearchText("");
                } else {
                    requirementModel.setLocalitySearchText(selectorLocality.getText().toString());
                }
            }
        }
        if (editLocality.getText().toString().trim().equals("")) {
            showToastMessage("Please enter Locality");
            editLocality.clearFocus();
            editLocality.requestFocus();
           // editLocality.setEnabled(true);
            return;
        } else {
            requirementModel.setLocality(editLocality.getText().toString());
        }
        if (editCity.getText().toString().equals("")) {
            showToastMessage("Please select City");
            editCity.clearFocus();
            editCity.requestFocus();
            editCity.setEnabled(true);
            return;
        } else {
            requirementModel.setCity(editCity.getText().toString());
        }
        if (editState.getText().toString().equals("")) {
            showToastMessage("Please enter State");
            editState.clearFocus();
            editState.requestFocus();
            editState.setEnabled(true);
            return;
        } else {
            requirementModel.setState(editState.getText().toString());
        }
        nextActivity();
    }

    private void nextActivity() {
        if (requirementModel.getCountry()== null){
            requirementModel.setCountry("Dubai");
        }
        requirementModel.setLatitude(latitude);
        requirementModel.setLongitude(longitude);
        requirementModel.setLocalitySearchText(selectorLocality.getText().toString());
        SessionManager.setPostRequirement(ActivityRequirementLocation.this, requirementModel);
        startActivity(new Intent(ActivityRequirementLocation.this, ActivityRequirementFeatures.class));
    }

    private void showToastMessage(String message) {
        Toast.makeText(ActivityRequirementLocation.this, message, Toast.LENGTH_SHORT).show();
    }

    private void showCityListSpinner() {
        dialog = new Dialog(this);
        @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.show_price_list_selector, null);
        final RecyclerView recyclerCityList = (RecyclerView) view.findViewById(R.id.recycler_price_list);
        recyclerCityList.setLayoutManager(new LinearLayoutManager(this));
        AdapterDistricts adapter = new AdapterDistricts(districtsList);

        recyclerCityList.setAdapter(adapter);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.show();

        adapter.setDistrictInterface(new AdapterDistricts.SelectDistrictInterface() {
            @Override
            public void onDistrictSelected(DistrictListingData district) {
                editState.setEnabled(false);
                editCity.setText(district.getCityName());
                editState.setText(district.getCityName());
                dialog.dismiss();
            }
        });

    }


    //TODO API Calls
    public void getDistrictList() {
        mLoading.show();
        ApiLinks.getClient().create(DistrictListingApi.class).post("" + SessionManager.getStateID(this))
                .enqueue(new Callback<DistrictListingResponse>() {
                    @Override
                    public void onResponse(Call<DistrictListingResponse> call,
                                           Response<DistrictListingResponse> response) {
                        if (response.isSuccessful()) {
                            int code = response.body().getCode();
                            String status = response.body().getResponse();
                            String message = response.body().getMessage();
                            if (code == 4004 && status.equalsIgnoreCase("Success")) {
                                districtsList = response.body().getData();
                            } else
                                Toast.makeText(ActivityRequirementLocation.this, message, Toast.LENGTH_SHORT).show();
                            mLoading.dismiss();
                        } else {
                            Toast.makeText(ActivityRequirementLocation.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                            mLoading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<DistrictListingResponse> call, Throwable t) {
                        Toast.makeText(ActivityRequirementLocation.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        mLoading.dismiss();
                    }
                });
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
        finish();
    }
}
