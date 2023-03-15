package com.paya.paragon.utilities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.paya.paragon.R;
import com.paya.paragon.api.ListYourBusinessSubmit.ListYourBusinessApi;
import com.paya.paragon.api.ListYourBusinessSubmit.ListYourBusinessResponse;
import com.paya.paragon.api.cityList.CityListingApi;
import com.paya.paragon.api.cityList.CityListingResponse;
import com.paya.paragon.api.index.LocationInfo;
import com.paya.paragon.api.localExpertCategory.LocalExpertCategoryApi;
import com.paya.paragon.api.localExpertCategory.LocalExpertCategoryData;
import com.paya.paragon.api.localExpertCategory.LocalExpertCategoryResponse;
import com.paya.paragon.classes.CustomSpinner;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.CountryCode.CountryCodePicker;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListBusinessDialogeBox extends Dialog {

    ProgressBar progressBar;
    ImageView ivClose;
    EditText edtBusinessName, edtName, edtPhone, edtAddress, edtEmail;
    TextView tvCity, tvCategory, tvSubmit;
    CountryCodePicker codePicker;
    CustomSpinner spinnerCity;
    CustomSpinner spinnerCategory;
    Context context;
    String city = "", cityId = "", category = "", categoryId = "";
    String contactName, emailAddress, phoneNumber;
    String businessName, address;
    String phoneCode;
    String countryCode;
    ArrayList<String> cityList;
    ArrayList<String> categoryList;
    ArrayAdapter<String> adapterCity;
    ArrayAdapter<String> adapterCategory;

    String country_code_alphabets = "";
    ArrayList<LocalExpertCategoryData> categoriesList;
    ArrayList<LocationInfo> citiesList;
    boolean isValid;
    public Dialog alertDialog;

    public ListBusinessDialogeBox(Context context, String categoryId) {
        super(context);
        this.context = context;
        this.categoryId = categoryId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.list_business_dialoge_box);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        ivClose = findViewById(R.id.iv_close);
        edtBusinessName = findViewById(R.id.edt_business_name);
        tvCity = findViewById(R.id.tv_city);
        tvCategory = findViewById(R.id.tv_category);
        edtName = findViewById(R.id.edt_name);
        edtPhone = findViewById(R.id.edt_phone);
        edtAddress = findViewById(R.id.edt_address);
        edtEmail = findViewById(R.id.edt_email);
        tvSubmit = findViewById(R.id.tv_submit);
        codePicker = findViewById(R.id.country_code);
        codePicker.registerCarrierNumberEditText(edtPhone);
        spinnerCity = findViewById(R.id.spinner_city);
        spinnerCategory = findViewById(R.id.spinner_category);

        if (SessionManager.getLoginStatus(context)) {
            contactName = SessionManager.getFullName(context);
            emailAddress = SessionManager.getEmail(context);
            phoneNumber = SessionManager.getPhone(context);
            countryCode = SessionManager.getCountryCode(context);
            if (countryCode == null || countryCode.equals("")) {
                codePicker.setCountryForNameCode(GlobalValues.countryCode);
            } else {
                codePicker.setCountryForNameCode(countryCode);
            }

        } else {
            codePicker.setCountryForNameCode(GlobalValues.countryCode);
            contactName = "";
            emailAddress = "";
            phoneNumber = "";
        }
        edtName.setText(contactName);
        edtEmail.setText(emailAddress);
        edtPhone.setText(phoneNumber);


        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (citiesList != null) {
                    spinnerCity.performClick();
                }
            }
        });
        tvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categoryList != null) {
                    spinnerCategory.performClick();
                }
            }
        });
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    city = spinnerCity.getAdapter().getItem(position).toString();
                    cityId = citiesList.get(position - 1).getCityID();
                    tvCity.setText(city);
                } else {
                    tvCity.setText(R.string.select_city);
                    city = "";
                    cityId = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tvCity.setText(R.string.select_city);
                city = "";
                cityId = "";
            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = spinnerCategory.getAdapter().getItem(position).toString();
                categoryId = categoriesList.get(position).getUserCategoryID();
                tvCategory.setText(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
           /*     tvCategory.setText("Select Category");
                category = "";
                categoryId="";*/
            }
        });


        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListBusinessDialogeBox.this.dismiss();
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                businessName = edtBusinessName.getText().toString().trim();
                contactName = edtName.getText().toString().trim();
                phoneNumber = edtPhone.getText().toString().trim();
                address = edtAddress.getText().toString().trim();
                emailAddress = edtEmail.getText().toString().trim();
                phoneCode = codePicker.getSelectedCountryCode().toLowerCase();
                country_code_alphabets = codePicker.getSelectedCountryCode();


                if (validateBusinessName()) {
                    if (validateCity()) {
                        if (validateCategory()) {
                            if (validateName()) {
                                if (validatePhone(phoneNumber, codePicker)) {
                                    if (validateAddress()) {
                                        if (validateEmail()) {
                                            progressBar.setVisibility(View.VISIBLE);
                                            tvSubmit.setVisibility(View.GONE);
                                            submitList();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


            }
        });
        getLocalExpertCategories();
        getCity();

    }

    //submit
    public void submitList() {
        ApiLinks.getClient().create(ListYourBusinessApi.class).post(businessName, categoryId, emailAddress, phoneCode,
                contactName, address,
                cityId, phoneNumber, phoneNumber, "listBusiness", "Save", country_code_alphabets)
                .enqueue(new Callback<ListYourBusinessResponse>() {
                    @Override
                    public void onResponse(Call<ListYourBusinessResponse> call, Response<ListYourBusinessResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getResponse().equals("success")) {
                                alertSuccess(context.getString(R.string.success), response.body().getMessage());
                                ListBusinessDialogeBox.this.dismiss();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                tvSubmit.setVisibility(View.VISIBLE);
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            ListBusinessDialogeBox.this.dismiss();
                        } else {
                            Toast.makeText(context, context.getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ListYourBusinessResponse> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //get category list
    public void getLocalExpertCategories() {
        Log.e("categoryId", categoryId);
        ApiLinks.getClient().create(LocalExpertCategoryApi.class).post(SessionManager.getLanguageID(context))
                .enqueue(new Callback<LocalExpertCategoryResponse>() {
                    @Override
                    public void onResponse(Call<LocalExpertCategoryResponse> call, Response<LocalExpertCategoryResponse> response) {
                        if (response.isSuccessful()) {
                            int code = response.body().getCode();
                            if (code == 100) {
                                categoriesList = response.body().getData();
                                categoryList = new ArrayList<>();
                                // categoryList.add("Select Category");
                                for (int i = 0; i < categoriesList.size(); i++) {
                                    categoryList.add(categoriesList.get(i).getUserCategoryName());
                                    if (categoryId.equals(categoriesList.get(i).getUserCategoryID())) {
                                        category = categoriesList.get(i).getUserCategoryName();
                                        categoryId = categoriesList.get(i).getUserCategoryID();
                                        tvCategory.setText(category);
                                    }
                                }
                                adapterCategory = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,
                                        categoryList);
                                spinnerCategory.setAdapter(adapterCategory);
                            } else {
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, context.getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LocalExpertCategoryResponse> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getCity() {

        ApiLinks.getClient().create(CityListingApi.class).post("" + SessionManager.getLanguageID(context))
                .enqueue(new Callback<CityListingResponse>() {
                    @Override
                    public void onResponse(Call<CityListingResponse> call, Response<CityListingResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getResponse();
                            if (message != null && message.equalsIgnoreCase("Success")) {
                                citiesList = response.body().getData().getCityList();
                                cityList = new ArrayList<>();
                                cityList.add("Select City");
                                for (int i = 0; i < citiesList.size(); i++) {
                                    cityList.add(citiesList.get(i).getCityName());
                                }
                                adapterCity = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,
                                        cityList);
                                spinnerCity.setAdapter(adapterCity);
                            } else
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(context, context.getString(R.string.no_response), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<CityListingResponse> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
    //get city list
   /* public void getCity() {
          ApiLinks.getClient().create(IndexApi.class).post("Buy", "221").enqueue(new Callback<IndexResponse>() {
            @Override
            public void onResponse(Call<IndexResponse> call, Response<IndexResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getResponse();
                    if (status.equals("Success")) {
                        citiesList = response.body().getData().getLocations();
                        cityList = new ArrayList<>();
                        cityList.add("Select City");
                        for(int i=0;i<citiesList.size();i++){
                            cityList.add(citiesList.get(i).getCityName());
                        }
                        adapterCity = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,
                                cityList);
                        spinnerCity.setAdapter(adapterCity);
                    }
                } else {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IndexResponse> call, Throwable t) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private boolean validateBusinessName() {
        if (businessName.equals("")) {
            Toast.makeText(context, context.getString(R.string.enter_business_name), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateCity() {
        if (cityId.equals("")) {
            Toast.makeText(context, context.getString(R.string.enter_city), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateCategory() {
        if (categoryId.equals("")) {
            Toast.makeText(context, context.getString(R.string.enter_category), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /*    private boolean validatePhone() {
            if (phoneNumber.equals("")) {
                Toast.makeText(context,context.getString(R.string.please_enter_phone_number),Toast.LENGTH_SHORT).show();
                return false;
            }else if (!Utils.isValidMobile(phoneNumber)) {
                Toast.makeText(context,
                        context.getString(R.string.valid_mobile_number), Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }*/
    private boolean validatePhone(String mobile, final CountryCodePicker codePicker) {
        if (mobile.equals("")) {
            Toast.makeText(getContext(), getContext().getString(R.string.valid_mobile_number), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!Utils.isValidMobile(mobile)) {
                Toast.makeText(getContext(), getContext().getString(R.string.valid_mobile_number),
                        Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }

        }
    }

    private boolean validateMobile(CountryCodePicker codePicker, String mobile) {

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

    private boolean validateAddress() {
        if (address.equals("")) {
            Toast.makeText(context, context.getString(R.string.please_enter_address), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateEmail() {
        if (emailAddress.equals("")) {
            Toast.makeText(context, context.getString(R.string.please_enter_email_address), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = emailAddress;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                return true;
            } else {
                Toast.makeText(context, context.getString(R.string.please_enter_valid_email_address), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    private boolean validateName() {
        if (contactName.equals("") || (!contactName.matches("^[\\p{L} .'-]+$"))) {
            Toast.makeText(context, context.getString(R.string.please_enter_valid_name), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void alertSuccess(String successTitle, String successMessage) {
        alertDialog = new Dialog(getContext());
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
}
