package com.paya.paragon.activity.postRequirements;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paya.paragon.R;
import com.paya.paragon.adapter.AdapterSpecificationsRequirement;
import com.paya.paragon.api.RequirementAttributeListing.RequirementAllAttributes;
import com.paya.paragon.api.RequirementAttributeListing.RequirementAttributeListingApi;
import com.paya.paragon.api.RequirementAttributeListing.RequirementAttributeListingResponse;
import com.paya.paragon.api.postProperty.attributeListing.AllAttributesData;
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
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"RedundantCast", "StringConcatenationInLoop", "HardCodedStringLiteral"})
public class ActivityRequirementFeatures extends AppCompatActivity {

    DialogProgress mLoading;
    TextView btnContinue;
    RecyclerView recyclerSpecifications;
    AdapterSpecificationsRequirement adapterSpecifications;
    ArrayList<AllAttributesData> specificationList = new ArrayList<>();
    String propertyTypeIDList = "";
    boolean isNeeded = false, isCommon = false;
    ArrayList<String> current = new ArrayList<>();
    ArrayList<String> common = new ArrayList<>();
    ArrayList<String> needed = new ArrayList<>();
    ArrayList<String> old = new ArrayList<>();
    LinearLayoutManager layoutManager;
    private static RequirementModel requirementModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_requirement_features);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow_black);

        declarations();

        if (!Utils.NoInternetConnection(ActivityRequirementFeatures.this)) {
            if (requirementModel.getRequirementAction().equalsIgnoreCase("post")) {
                getAttributes(propertyTypeIDList);
            } else {
                propertyTypeIDList = "";
                current = new ArrayList<>(requirementModel.getPropertyTypeIds());
                old = new ArrayList<>(requirementModel.getPropertyTypeIdsEdit());
                if (current.size() == old.size()) {
                    common.addAll(current);
                    common.retainAll(old);
                    needed.addAll(current);
                    needed.removeAll(old);
                    if (needed.size() > 0) {
                        for (String id : needed) {
                            if (propertyTypeIDList.equals("")) {
                                propertyTypeIDList = id;
                            } else {
                                propertyTypeIDList = propertyTypeIDList + "," + id;
                            }
                        }
                        isNeeded = true;
                    }
                    if (common.size() > 0) {
                        isCommon = true;
                    }
                } else {
                    if (current.size() > old.size()) {
                        common.addAll(current);
                        common.retainAll(old);
                        needed.addAll(current);
                        needed.removeAll(old);
                        if (needed.size() > 0) {
                            for (String id : needed) {
                                if (propertyTypeIDList.equals("")) {
                                    propertyTypeIDList = id;
                                } else {
                                    propertyTypeIDList = propertyTypeIDList + "," + id;
                                }
                            }
                            isNeeded = true;
                        }
                        if (common.size() > 0) {
                            isCommon = true;
                        }
                    } else {
                        common.addAll(current);
                        common.retainAll(old);
                        needed.addAll(current);
                        needed.removeAll(old);
                        if (needed.size() > 0) {
                            for (String id : needed) {
                                if (propertyTypeIDList.equals("")) {
                                    propertyTypeIDList = id;
                                } else {
                                    propertyTypeIDList = propertyTypeIDList + "," + id;
                                }
                            }
                            isNeeded = true;
                        }
                        if (common.size() > 0) {
                            isCommon = true;
                        }
                    }
                }
                if (isNeeded) {
                    getAttributes(propertyTypeIDList);
                } else {
                    ArrayList<AllAttributesData> commonData = new ArrayList<>();
                    ArrayList<Integer> sizeList = new ArrayList<>();
                    int count = 0;
                    for (String commonId : common) {
                        for (AllAttributesData data : requirementModel.getAttributeList()) {
                            if (data.getPropertyTypeID().equals(commonId)) {
                                commonData.add(data);
                                count++;
                            } else {
                                if (count > 0) {
                                    sizeList.add(count);
                                    count = 0;
                                }
                            }
                        }
                    }
                    sizeList.add(count);
                    specificationList.addAll(commonData);
                    requirementModel.setFeaturesSizeList(sizeList);
                    adapterSpecifications = new AdapterSpecificationsRequirement(ActivityRequirementFeatures.this,
                            specificationList, sizeList, requirementModel.getRequirementAction());
                    recyclerSpecifications.setAdapter(adapterSpecifications);
                    adapterSpecifications.notifyDataSetChanged();
                }
            }
        } else {
            Utils.showAlertNoInternet(ActivityRequirementFeatures.this);
        }

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity();
            }
        });
    }

    private void nextActivity() {
        requirementModel.setAttributeList(specificationList);
        SessionManager.setPostRequirement(ActivityRequirementFeatures.this, requirementModel);
        startActivity(new Intent(ActivityRequirementFeatures.this, ActivityRequirementContact.class));
    }


    //TODO Set Data
    private void declarations() {
        btnContinue = findViewById(R.id.text_continue_requirement_features);
        recyclerSpecifications = findViewById(R.id.recycler_specifications_requirement_specification);
        recyclerSpecifications.setNestedScrollingEnabled(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerSpecifications.setLayoutManager(layoutManager);
        mLoading = new DialogProgress(ActivityRequirementFeatures.this);
        requirementModel = SessionManager.getPostRequirement(ActivityRequirementFeatures.this);

        if (requirementModel.getPropertyTypeIds() != null
                && requirementModel.getPropertyTypeIds().size() > 0) {
            for (String id : requirementModel.getPropertyTypeIds()) {
                if (propertyTypeIDList.equals("")) {
                    propertyTypeIDList = id;
                } else {
                    propertyTypeIDList = propertyTypeIDList + "," + id;
                }
            }
        }
    }

    private void setDataToUI(ArrayList<RequirementAllAttributes> allAttributes) {
        ArrayList<Integer> sizeList = new ArrayList<>();
        if (!isNeeded && !isCommon) {
            for (int j = 0; j < allAttributes.size(); j++) {
                RequirementAllAttributes attributes = allAttributes.get(j);
                if (attributes.getSubAttributes() != null && attributes.getSubAttributes().size() > 0) {
                    specificationList.addAll(attributes.getSubAttributes());
                    sizeList.add(specificationList.size());
                }
            }
        } else {
            ArrayList<AllAttributesData> commonData = new ArrayList<>();
            if (isCommon) {
                int count = 0;
                for (String commonId : common) {
                    for (AllAttributesData data : requirementModel.getAttributeList()) {
                        if (data.getPropertyTypeID().equals(commonId)) {
                            commonData.add(data);
                            count++;
                        } else {
                            if (count > 0) {
                                if (sizeList.size() > 0) {
                                    int current = sizeList.get(sizeList.size()-1);
                                    sizeList.add(current + count);
                                    count = 0;
                                } else {
                                    sizeList.add(count);
                                    count = 0;
                                }
                            }
                        }
                    }
                }
                int current = sizeList.get(sizeList.size()-1);
                sizeList.add(current + count);
                specificationList.addAll(commonData);
            }
            if (isNeeded) {
                for (int j = 0; j < allAttributes.size(); j++) {
                    RequirementAllAttributes attributes = allAttributes.get(j);
                    if (attributes.getSubAttributes() != null && attributes.getSubAttributes().size() > 0) {
                        specificationList.addAll(attributes.getSubAttributes());
                        if (sizeList.size()>0){
                            int current = sizeList.get(sizeList.size()-1);
                            sizeList.add(current + attributes.getSubAttributes().size());
                        } else {
                            sizeList.add(attributes.getSubAttributes().size());
                        }
                    }
                }
            }
        }
        requirementModel.setFeaturesSizeList(sizeList);

        adapterSpecifications = new AdapterSpecificationsRequirement(ActivityRequirementFeatures.this,
                specificationList, sizeList, requirementModel.getRequirementAction());
        recyclerSpecifications.setAdapter(adapterSpecifications);
        adapterSpecifications.notifyDataSetChanged();

    }


    //TODO API Calls
    public void getAttributes(String propertyTypeIDList) {
        mLoading.show();
        ApiLinks.getClient().create(RequirementAttributeListingApi.class)
                .post(propertyTypeIDList)
                .enqueue(new Callback<RequirementAttributeListingResponse>() {
                    @Override
                    public void onResponse(Call<RequirementAttributeListingResponse> call,
                                           Response<RequirementAttributeListingResponse> response) {
                        if (response.isSuccessful()) {
                            int code = response.body().getCode();
                            String status = response.body().getResponse();
                            String message = response.body().getMessage();
                            if (code == 4004 && status.equalsIgnoreCase("Success")) {
                                if (response.body().getData().getAllAttributes().size() > 0) {
                                    setDataToUI(response.body().getData().getAllAttributes());
                                }
                            } else
                                Toast.makeText(ActivityRequirementFeatures.this, message, Toast.LENGTH_SHORT).show();
                            mLoading.dismiss();
                        } else {
                            Toast.makeText(ActivityRequirementFeatures.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                            mLoading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<RequirementAttributeListingResponse> call, Throwable t) {
                        Toast.makeText(ActivityRequirementFeatures.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
