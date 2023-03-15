package com.paya.paragon.activity.postRequirements;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.paya.paragon.R;
import com.paya.paragon.adapter.AdapterSelectedRequirementFeatures;
import com.paya.paragon.api.postProperty.attributeListing.AllAttributesData;
import com.paya.paragon.utilities.DialogProgress;

import java.util.ArrayList;

@SuppressWarnings({"RedundantCast", "HardCodedStringLiteral"})
public class ActivityRequirementConfirm extends AppCompatActivity {

    DialogProgress mLoading;
    TextView btnModify, btnConfirm;
    TextView textPropertyFor, textType, textPropertyType, textPriceRange;
    TextView textName, textEmail, textPhone, textLocality, textConfirmEdit;
    RecyclerView recyclerFeatures;
    LinearLayout layoutFeatures, layoutConfirm;
    ArrayList<AllAttributesData> selectedFeaturesList = new ArrayList<>();
    AdapterSelectedRequirementFeatures adapterRequirementFeatures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_requirement_confirm);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(16);
        mTitle.setText(R.string.post_requirement);

        declaration();

        /* setDataToUI();*/

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* requirementModel.setRequirementAction("edit");
                SessionManager.setPostRequirement(ActivityRequirementConfirm.this, requirementModel);
                startActivity(new Intent(ActivityRequirementConfirm.this,
                        ActivityRequirementPropertyType.class));*/
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*requirementModel = new RequirementModel();
                SessionManager.setPostRequirement(ActivityRequirementConfirm.this, requirementModel);
                startActivity(new Intent(ActivityRequirementConfirm.this,
                        ActivityRequirementSubmitted.class));
                finish();*/
            }
        });

        textConfirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* startActivity(new Intent(ActivityRequirementConfirm.this,
                        ActivityRequirementSubmitted.class));
                finish();*/
            }
        });
    }

    //TODO Set Data
    private void declaration() {
        mLoading = new DialogProgress(ActivityRequirementConfirm.this);
        btnModify = findViewById(R.id.text_modify_requirement_confirm);
        btnConfirm = findViewById(R.id.text_confirm_requirement_confirm);
        textPropertyFor = findViewById(R.id.text_property_for_requirement_confirm);
        textType = findViewById(R.id.text_type_requirement_confirm);
        textPropertyType = findViewById(R.id.text_property_type_requirement_confirm);
        textPriceRange = findViewById(R.id.text_price_range_requirement_confirm);
        textLocality = findViewById(R.id.text_locality_info_requirement_confirm);
        textName = findViewById(R.id.text_name_requirement_confirm);
        textEmail = findViewById(R.id.text_email_requirement_confirm);
        textPhone = findViewById(R.id.text_phone_requirement_confirm);
        textConfirmEdit = findViewById(R.id.text_confirm_requirement_confirm_edit);
        layoutFeatures = findViewById(R.id.layout_features_requirement_confirm);
        layoutConfirm = findViewById(R.id.layout_confirm_message_requirement_confirm);
        recyclerFeatures = findViewById(R.id.recycler_features_listing_requirement_confirm);
        recyclerFeatures.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        recyclerFeatures.setNestedScrollingEnabled(false);
        recyclerFeatures.setHasFixedSize(true);

        adapterRequirementFeatures = new AdapterSelectedRequirementFeatures(setSortedList(selectedFeaturesList));
        recyclerFeatures.setAdapter(adapterRequirementFeatures);

        if (selectedFeaturesList.size() == 0) {
            layoutFeatures.setVisibility(View.GONE);
        }

      /*  if (GlobalValues.editPostRequirement) {
            textConfirmEdit.setVisibility(View.VISIBLE);
            layoutConfirm.setVisibility(View.GONE);
        } else {
            textConfirmEdit.setVisibility(View.GONE);
            layoutConfirm.setVisibility(View.VISIBLE);
        }*/
    }

 /*   private void setDataToUI() {
        textPropertyFor.setText(GlobalValues.purposePostRequirement);
        if (GlobalValues.purposePostRequirement.equals("Sell")) {
            textPropertyFor.setText(R.string.buy);
        }
        textType.setText(GlobalValues.selectedTypePostRequirement);

        StringBuilder selectedProperties = new StringBuilder();
        for (String item : GlobalValues.selectedPropertyTypesPostReq) {
            if (selectedProperties.toString().equals("")) {
                selectedProperties = new StringBuilder(item);
            } else {
                selectedProperties.append(", ").append(item);
            }
        }
        textPropertyType.setText(selectedProperties.toString());

        String price = "";
        if (GlobalValues.minPricePostRequirement != null && !GlobalValues.minPricePostRequirement.equals("")) {
            price = GlobalValues.minPriceValuePostRequirement;
        }
        if (GlobalValues.maxPricePostRequirement != null && !GlobalValues.maxPricePostRequirement.equals("")) {
            if (price.equals("")) {
                price = "Maximum " + GlobalValues.maxPriceValuePostRequirement;
            } else {
                price = price + " - " + GlobalValues.maxPriceValuePostRequirement;
            }
        } else {
            if (price.equals("")) {
                price = "Budget not specified";
            } else {
                price = "Minimum " + price;
            }
        }
        textPriceRange.setText(price);

        textName.setText(GlobalValues.contactNamePostRequirement);
        textEmail.setText(GlobalValues.contactEmailPostRequirement);
        textPhone.setText(GlobalValues.contactPhonePostRequirement);
        String locality = GlobalValues.localityPostRequirement + "\n" +
                GlobalValues.cityPostRequirement + "\n" +
                GlobalValues.statePostRequirement;
        textLocality.setText(locality);
    }*/


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

    }

    private ArrayList<AllAttributesData> setSortedList(ArrayList<AllAttributesData> allAttributesList) {
        ArrayList<String> propertyTypeIDs = new ArrayList<>();
        ArrayList<AllAttributesData> tempList = new ArrayList<>();
        ArrayList<AllAttributesData> tempMultiList = new ArrayList<>();
        for (AllAttributesData data : allAttributesList) {
            if (propertyTypeIDs.size() == 0) {
                propertyTypeIDs.add(data.getPropertyTypeID());
            } else {
                if (!propertyTypeIDs.contains(data.getPropertyTypeID())) {
                    propertyTypeIDs.add(data.getPropertyTypeID());
                }
            }
        }
        for (int i = 0; i < propertyTypeIDs.size(); i++) {
            String typeId = propertyTypeIDs.get(i);
            for (int j = 0; j < allAttributesList.size(); j++) {
                AllAttributesData item = allAttributesList.get(j);
                if (item.getPropertyTypeID().equals(typeId)) {
                    if (item.getSelection().equals("multy")) {
                        tempMultiList.add(item);
                    } else {
                        tempList.add(item);
                    }
                }
            }
        }
        //TODO
        ArrayList<AllAttributesData> sortedMultiList = new ArrayList<>();
        if (tempMultiList.size() > 0) {
            for (int i = 0; i < propertyTypeIDs.size(); i++) {
                String typeId = propertyTypeIDs.get(i);
                if (sortedMultiList.size() == 0) {
                    AllAttributesData addingData = tempMultiList.get(0);
                    for (int j = 1; j < tempMultiList.size(); j++) {
                        AllAttributesData tempMultiItem = tempMultiList.get(j);
                        if (typeId.equals(tempMultiItem.getPropertyTypeID())) {
                            if (addingData.getPropertyTypeID().equals(tempMultiItem.getPropertyTypeID())
                                    && addingData.getAttrID().equals(tempMultiItem.getAttrID())) {
                                addingData.setAttrOptName(addingData.getAttrOptName()
                                        + ", " + tempMultiItem.getAttrOptName());
                                tempMultiList.remove(j);
                                j = j - 1;
                            }
                        }
                    }
                    sortedMultiList.add(addingData);
                    tempMultiList.remove(0);
                    if (sortedMultiList.size() > 0 && tempMultiList.size() > 0) {
                        addingData = tempMultiList.get(0);
                        for (int j = 1; j < tempMultiList.size(); j++) {
                            AllAttributesData tempMultiItem = tempMultiList.get(j);

                            if (typeId.equals(tempMultiItem.getPropertyTypeID())) {

                                if (addingData.getPropertyTypeID().equals(tempMultiItem.getPropertyTypeID())
                                        && addingData.getAttrID().equals(tempMultiItem.getAttrID())) {
                                    addingData.setAttrOptName(addingData.getAttrOptName()
                                            + ", " + tempMultiItem.getAttrOptName());
                                    tempMultiList.remove(j);
                                    j = j - 1;
                                }
                            }
                        }
                        sortedMultiList.add(addingData);
                        tempMultiList.remove(0);
                    }
                } else {
                    if (tempMultiList.size() > 0) {
                        AllAttributesData addingData = tempMultiList.get(0);
                        for (int j = 1; j < tempMultiList.size(); j++) {
                            AllAttributesData tempMultiItem = tempMultiList.get(j);

                            if (typeId.equals(tempMultiItem.getPropertyTypeID())) {

                                if (addingData.getPropertyTypeID().equals(tempMultiItem.getPropertyTypeID())
                                        && addingData.getAttrID().equals(tempMultiItem.getAttrID())) {
                                    addingData.setAttrOptName(addingData.getAttrOptName()
                                            + ", " + tempMultiItem.getAttrOptName());
                                    tempMultiList.remove(j);
                                    j = j - 1;
                                }
                            }
                        }
                        sortedMultiList.add(addingData);
                        tempMultiList.remove(0);
                    }
                }
            }
        }
        if (sortedMultiList.size() > 0) {
            tempList.addAll(sortedMultiList);
        }
        ArrayList<AllAttributesData> result = new ArrayList<>();
        for (int i = 0; i < propertyTypeIDs.size(); i++) {
            String typeId = propertyTypeIDs.get(i);
            for (int j = 0; j < tempList.size(); j++) {
                AllAttributesData item = tempList.get(j);
                if (item.getPropertyTypeID().equals(typeId)) {
                    result.add(item);
                }
            }
        }
        return result;
    }
}
