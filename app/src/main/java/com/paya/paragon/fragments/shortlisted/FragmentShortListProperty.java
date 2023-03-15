package com.paya.paragon.fragments.shortlisted;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.paya.paragon.R;
import com.paya.paragon.activity.details.ActivityPropertyDetails;
import com.paya.paragon.adapter.AdapterShortlistedProperties;
import com.paya.paragon.api.contactOwner.ContactOwnerPropertyApi;
import com.paya.paragon.api.contactOwner.ContactOwnerResponse;
import com.paya.paragon.api.deleteShortlistedProperty.DeleteShortlistedPropertyApi;
import com.paya.paragon.api.deleteShortlistedProperty.DeleteShortlistedPropertyResponse;
import com.paya.paragon.api.shoertlistedProperties.ShortlistedPropertyListApi;
import com.paya.paragon.api.shoertlistedProperties.ShortlistedPropertyListResponse;
import com.paya.paragon.api.shoertlistedProperties.ShortlistedPropertyModel;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.CountryCode.CountryCodePicker;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.PropertyProjectType;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("HardCodedStringLiteral")
public class FragmentShortListProperty extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView rvList;
    LinearLayoutManager mLayoutManager;
    DialogProgress dialogProgress;
    TextView noProperties;
    public Dialog alertDialog;
    public boolean isTabCreated = false;

    ArrayList<ShortlistedPropertyModel> propertyLists;
    AdapterShortlistedProperties adapterProperties;
    private CountUpdateListener countUpdateListener;

    private String isMortgaged = "No";

    @SuppressLint("ValidFragment")
    public FragmentShortListProperty(CountUpdateListener countUpdateListener) {
        // Required empty public constructor
        this.countUpdateListener = countUpdateListener;

    }

    public FragmentShortListProperty() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_short_list_property, container, false);
        isTabCreated = true;

        dialogProgress = new DialogProgress(getActivity());
        noProperties = v.findViewById(R.id.text_no_properties_shortlisted);
        noProperties.setVisibility(View.GONE);
        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh_fragment_listing);
        rvList = v.findViewById(R.id.recycler_view_fragment_listing);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvList.setLayoutManager(mLayoutManager);

        if (!Utils.NoInternetConnection(getActivity())) {
            getShortlistedPropertyListing();
        } else {
            noProperties.setVisibility(View.VISIBLE);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utils.NoInternetConnection(getActivity())) {
                    Utils.showAlertNoInternet(getActivity());
                    noProperties.setVisibility(View.VISIBLE);
                    dialogProgress.dismiss();
                } else {
                    getShortlistedPropertyListing();
                    noProperties.setVisibility(View.GONE);
                }
            }
        });
        return v;
    }


    //TODO API Calls
    public void getShortlistedPropertyListing() {
        if (!isTabCreated)
            return;
        if (adapterProperties != null)
            adapterProperties.clear();
        dialogProgress.show();
        ApiLinks.getClient().create(ShortlistedPropertyListApi.class).post(
                SessionManager.getAccessToken(getActivity()), SessionManager.getLanguageID(getActivity()))
                .enqueue(new Callback<ShortlistedPropertyListResponse>() {
                    @Override
                    public void onResponse(Call<ShortlistedPropertyListResponse> call,
                                           Response<ShortlistedPropertyListResponse> response) {
                        try {
                            if (response.isSuccessful()) {
                                swipeRefreshLayout.setRefreshing(false);
                                String message = response.body().getResponse();
                                int code = response.body().getCode();
                                if (code == 4004 && message.equalsIgnoreCase("Success")) {
                                    GlobalValues.shortlistedPropertiesImageUrl = response.body().getData().getImgPath();
                                    propertyLists = response.body().getData().getPropertyList();
                                    adapterProperties = new AdapterShortlistedProperties(getActivity(), propertyLists,
                                            new AdapterShortlistedProperties.ItemClickAdapterListener() {
                                                @Override
                                                public void deleteClick(int position) {
                                                    alertDialogUnFollowProperty(getActivity(), position);
                                                }

                                                @Override
                                                public void viewDetailsClick(int position) {
                                                    Intent intent = new Intent(getActivity(), ActivityPropertyDetails.class);
                                                    intent.putExtra("propertyID", propertyLists.get(position).getPropertyID());
                                                    intent.putExtra(Utils.TYPE, propertyLists.get(position).getPropertyPurpose().equalsIgnoreCase("Sell") ? PropertyProjectType.BUY : PropertyProjectType.RENT);
                                                    intent.putExtra("position", "0");
                                                    startActivity(intent);
                                                }

                                                @Override
                                                public void contactUser(int position) {
                                                    alertContactOwner(getActivity(), position);
                                                }


                                            });
                                    rvList.setAdapter(adapterProperties);
                                    countUpdateListener.updateCount(propertyLists.size());
                                    dialogProgress.dismiss();
                                } else {
                                    dialogProgress.dismiss();
                                    noProperties.setVisibility(View.VISIBLE);
                                    countUpdateListener.updateCount(0);
                                }
                            } else {
                                dialogProgress.dismiss();
                                //Toast.makeText(activity, "No Shortlisted Properties", Toast.LENGTH_SHORT).show();
                                noProperties.setVisibility(View.VISIBLE);
                                countUpdateListener.updateCount(0);
                            }
                        } catch (Exception e) {
                            FirebaseCrashlytics.getInstance().recordException(e);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ShortlistedPropertyListResponse> call, Throwable t) {
                        //Toast.makeText(activity,  getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        dialogProgress.dismiss();
                        try {
                            noProperties.setVisibility(View.VISIBLE);
                            countUpdateListener.updateCount(propertyLists.size());
                        } catch (Exception e) {
                            FirebaseCrashlytics.getInstance().recordException(e);
                        }
                    }
                });
    }

    public void deleteShortlistedProperty(final FragmentActivity activity, final String propertyID, final int position) {
        if (!Utils.NoInternetConnection(activity)) {
            ApiLinks.getClient().create(DeleteShortlistedPropertyApi.class).post(propertyID,
                    SessionManager.getAccessToken(activity))
                    .enqueue(new Callback<DeleteShortlistedPropertyResponse>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onResponse(Call<DeleteShortlistedPropertyResponse> call,
                                               Response<DeleteShortlistedPropertyResponse> response) {
                            try {
                                if (response.isSuccessful()) {
                                    String message = response.body().getResponse();
                                    int code = response.body().getCode();
                                    if (code == 4004 && message.equalsIgnoreCase("Success")) {
                                        propertyLists.remove(position);
                                        if (propertyLists.size() == 0) {
                                            noProperties.setVisibility(View.VISIBLE);
                                        }


                                        adapterProperties.notifyDataSetChanged();
                                        countUpdateListener.updateCount(propertyLists.size());
                                    }
                                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    dialogProgress.dismiss();
                                } else {
                                    Toast.makeText(activity, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                                    dialogProgress.dismiss();
                                }
                            } catch (Exception e) {
                                FirebaseCrashlytics.getInstance().recordException(e);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<DeleteShortlistedPropertyResponse> call, Throwable t) {
                            dialogProgress.dismiss();
                            Toast.makeText(activity, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Utils.showAlertNoInternet(activity);
        }
    }

    private void postContactOwner(FragmentActivity activity, final Dialog alertDialog, String strName, String strEmail, String strPhone,
                                  String strCountryCode, int position) {
        if (!Utils.NoInternetConnection(activity)) {
            alertDialog.dismiss();
            dialogProgress.show();
            ApiLinks.getClient().create(ContactOwnerPropertyApi.class)
                    .post(SessionManager.getAccessToken(activity),
                            propertyLists.get(position).getPropertyID(),
                            "Property",
                            "Listing",
                            strEmail,
                            strName,
                            strPhone,
                            "",
                            strCountryCode, isMortgaged)
                    .enqueue(new Callback<ContactOwnerResponse>() {
                        @Override
                        public void onResponse(Call<ContactOwnerResponse> call,
                                               Response<ContactOwnerResponse> response) {
                            try {
                                if (response.isSuccessful()) {
                                    String message = response.body().getResponse();
                                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                                        //alertDialog.dismiss();
                                        alertSuccess(getActivity(), getResources().getString(R.string.thank_you_for_contact),
                                                getResources().getString(R.string.contact_owner_success_message));
                                    } else {
                                        alertDialog.dismiss();
                                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                    }
                                    dialogProgress.dismiss();
                                } else {
                                    alertDialog.dismiss();
                                    Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                                    dialogProgress.dismiss();
                                }
                            } catch (Exception e) {
                                FirebaseCrashlytics.getInstance().recordException(e);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ContactOwnerResponse> call, Throwable t) {
                            alertDialog.dismiss();
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            dialogProgress.dismiss();
                            alertDialog.dismiss();
                        }
                    });
        } else {
            alertDialog.dismiss();
            Utils.showAlertNoInternet(activity);
        }
    }


    //TODO Alert Delete
    @SuppressLint("SetTextI18n")
    private void alertDialogUnFollowProperty(final FragmentActivity activity, final int position) {
        alertDialog = new Dialog(activity);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_popup, null);
        LinearLayout cancel = alert_layout.findViewById(R.id.CancelPopUp);
        LinearLayout ok = alert_layout.findViewById(R.id.ApplyPopUP);
        TextView title = alert_layout.findViewById(R.id.alertTitle);
        title.setText(getString(R.string.are_you_sure_to_remove_this_property));

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
                deleteShortlistedProperty(activity, propertyLists.get(position).getPropertyID(),
                        position);
            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void alertContactOwner(final FragmentActivity activity, final int position) {
        alertDialog = new Dialog(activity);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_contact_agent_popup, null);
        ImageView close = alert_layout.findViewById(R.id.close_contact_owner);
        final EditText fullName = alert_layout.findViewById(R.id.editText_user_full_name_contact_owner);
        final EditText email = alert_layout.findViewById(R.id.editText_user_email_address_contact_owner);
        final EditText phone = alert_layout.findViewById(R.id.editText_phone_contact_owner);
        final EditText message = alert_layout.findViewById(R.id.editText_user_message_contact_owner);
        final CountryCodePicker codePicker = alert_layout.findViewById(R.id.country_code_contact_owner);
        TextView submit = alert_layout.findViewById(R.id.button_submit_contact_owner);

        final CheckBox cbMortgage = alert_layout.findViewById(R.id.checkBox_info_mortgage_contact_owner);

        message.setVisibility(View.GONE);

        if (SessionManager.getLoginStatus(activity)) {
            fullName.setText(SessionManager.getFullName(activity));
            email.setText(SessionManager.getEmail(activity));
            phone.setText(SessionManager.getPhone(activity));
            codePicker.setCountryForNameCode(SessionManager.getCountryCode(activity));
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strCountryCode = codePicker.getSelectedCountryCode().toLowerCase();
                String strName = fullName.getText().toString();
                String strEmail = email.getText().toString();
                String strPhone = phone.getText().toString();
                if (fullName.getText().toString().trim().equals("")) {
                    fullName.clearFocus();
                    fullName.requestFocus();
                    Toast.makeText(activity, getString(R.string.please_enter_name), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    strName = fullName.getText().toString();
                    if (!Utils.isValidName(strName)) {
                        Toast.makeText(activity, getString(R.string.please_enter_valid_name),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (email.getText().toString().equals("")) {
                    email.clearFocus();
                    email.requestFocus();
                    Toast.makeText(activity, getString(R.string.please_enter_email_address), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    strEmail = email.getText().toString();
                    if (TextUtils.isEmpty(strEmail) || !Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                        Toast.makeText(activity, getString(R.string.please_enter_valid_email_address), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (phone.getText().toString().equals("")) {
                    phone.clearFocus();
                    phone.requestFocus();
                    Toast.makeText(activity, getString(R.string.please_enter_phone_number), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    strPhone = phone.getText().toString();
                    if (!Utils.isValidMobile(strPhone)) {
                        Toast.makeText(activity,
                                getString(R.string.valid_mobile_number), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (cbMortgage.isChecked()) {
                    isMortgaged = "Yes";
                }
                if (!Utils.NoInternetConnection(activity)) {
                    postContactOwner(activity, alertDialog, strName, strEmail, strPhone, strCountryCode, position);
                } else {
                    Toast.makeText(activity, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void alertSuccess(FragmentActivity activity, String successTitle, String successMessage) {
        alertDialog = new Dialog(activity);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
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


    //TODO Count Update
    public interface CountUpdateListener {
        void updateCount(int count);
    }
}
