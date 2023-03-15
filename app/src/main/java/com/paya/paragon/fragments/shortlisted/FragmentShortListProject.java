package com.paya.paragon.fragments.shortlisted;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.paya.paragon.R;
import com.paya.paragon.activity.details.ActivityProjectDetails;
import com.paya.paragon.adapter.AdapterShortlistedProjects;
import com.paya.paragon.api.contactOwner.ContactOwnerProjectApi;
import com.paya.paragon.api.contactOwner.ContactOwnerResponse;
import com.paya.paragon.api.deleteShortlistedProject.DeleteShortlistedProjectApi;
import com.paya.paragon.api.deleteShortlistedProject.DeleteShortlistedProjectResponse;
import com.paya.paragon.api.shortlistedProjects.ShortlistedProjectListApi;
import com.paya.paragon.api.shortlistedProjects.ShortlistedProjectListResponse;
import com.paya.paragon.api.shortlistedProjects.ShortlistedProjectModel;
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
public class FragmentShortListProject extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView rvList;
    LinearLayoutManager mLayoutManager;
    DialogProgress dialogProgress;
    TextView noProjects;
    public Dialog alertDialog;
    public boolean isTabCreated = false;

    ArrayList<ShortlistedProjectModel> projectLists;
    AdapterShortlistedProjects adapterProjects;
    private CountUpdateListener countUpdateListener;

    public FragmentShortListProject() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public FragmentShortListProject(CountUpdateListener countUpdateListener) {
        this.countUpdateListener = countUpdateListener;
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentShortListProject newInstance(String param1, String param2) {
        return new FragmentShortListProject();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_short_list_project, container, false);
        isTabCreated = true;

        dialogProgress = new DialogProgress(getActivity());
        noProjects = v.findViewById(R.id.text_no_projects_shortlisted);
        noProjects.setVisibility(View.GONE);
        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh_fragment_listing);
        rvList = v.findViewById(R.id.recycler_view_fragment_listing);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvList.setLayoutManager(mLayoutManager);

        if (!Utils.NoInternetConnection(getActivity())) {
            getShortlistedProjectListing();
        } else {
            noProjects.setVisibility(View.VISIBLE);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utils.NoInternetConnection(getActivity())) {
                    Utils.showAlertNoInternet(getActivity());
                    noProjects.setVisibility(View.VISIBLE);
                } else {
                    noProjects.setVisibility(View.GONE);
                    getShortlistedProjectListing();
                }
            }
        });


        return v;
    }

    //TODO API Call
    public void getShortlistedProjectListing() {
        if (!isTabCreated)
            return;
        if (adapterProjects != null)
            adapterProjects.clear();
        dialogProgress.show();
        ApiLinks.getClient().create(ShortlistedProjectListApi.class).post(
                SessionManager.getAccessToken(getActivity()), SessionManager.getLanguageID(getActivity()))
                .enqueue(new Callback<ShortlistedProjectListResponse>() {
                    @Override
                    public void onResponse(Call<ShortlistedProjectListResponse> call,
                                           Response<ShortlistedProjectListResponse> response) {
                        try {
                            if (response.isSuccessful()) {
                                swipeRefreshLayout.setRefreshing(false);
                                String message = response.body().getResponse();
                                int code = response.body().getCode();
                                if (code == 4004 && message.equalsIgnoreCase("Success")) {
                                    GlobalValues.shortlistedProjectsImageUrl = response.body().getData().getImgPath();
                                    projectLists = response.body().getData().getProjectList();
                                    adapterProjects = new AdapterShortlistedProjects(getActivity(), projectLists,
                                            new AdapterShortlistedProjects.ItemClickAdapterListener() {
                                                @Override
                                                public void deleteClick(int position) {
                                                    alertDialogUnFollowProject(getActivity(), position);
                                                }

                                                @Override
                                                public void viewDetailsClick(int position) {
                                                    Intent intent = new Intent(getActivity(),
                                                            ActivityProjectDetails.class);
                                                    intent.putExtra("projectID", projectLists.get(position)
                                                            .getProjectID());
                                                    intent.putExtra(Utils.TYPE, PropertyProjectType.NEW_PROJECT);
                                                    intent.putExtra("position", "0");
                                                    startActivity(intent);
                                                }

                                                @Override
                                                public void contactBuilder(int position) {
                                                    alertContactOwner(getActivity(), position);
                                                }
                                            });
                                    rvList.setAdapter(adapterProjects);
                                    dialogProgress.dismiss();
                                    countUpdateListener.updateCount(projectLists.size());
                                } else {
                                    dialogProgress.dismiss();
                                    noProjects.setVisibility(View.VISIBLE);
                                    countUpdateListener.updateCount(0);
                                }
                            } else {
                                dialogProgress.dismiss();
                                //Toast.makeText(activity, "No Shortlisted Projects", Toast.LENGTH_SHORT).show();
                                noProjects.setVisibility(View.VISIBLE);
                                countUpdateListener.updateCount(0);
                            }
                        } catch (Exception e) {
                            FirebaseCrashlytics.getInstance().recordException(e);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ShortlistedProjectListResponse> call, Throwable t) {
                        //Toast.makeText(activity,  getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        dialogProgress.dismiss();
                        noProjects.setVisibility(View.VISIBLE);
                        countUpdateListener.updateCount(0);
                    }
                });

    }

    public void deleteShortlistedProject(FragmentActivity activity, final String projectID, final int position) {
        if (!Utils.NoInternetConnection(getActivity())) {
            dialogProgress.show();
            ApiLinks.getClient().create(DeleteShortlistedProjectApi.class)
                    .post(projectID,
                            SessionManager.getAccessToken(activity))
                    .enqueue(new Callback<DeleteShortlistedProjectResponse>() {
                        @Override
                        public void onResponse(Call<DeleteShortlistedProjectResponse> call,
                                               Response<DeleteShortlistedProjectResponse> response) {
                            try {
                                if (response.isSuccessful()) {
                                    String message = response.body().getResponse();
                                    int code = response.body().getCode();
                                    if (message.equalsIgnoreCase("Success")) {
                                        projectLists.remove(position);
                                        if (projectLists.size() == 0) {
                                            noProjects.setVisibility(View.VISIBLE);
                                        }
                                        adapterProjects.notifyDataSetChanged();
                                        countUpdateListener.updateCount(projectLists.size());
                                    }
                                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    dialogProgress.dismiss();
                                } else {
                                    Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                                    dialogProgress.dismiss();
                                }
                            } catch (Exception e) {
                                FirebaseCrashlytics.getInstance().recordException(e);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<DeleteShortlistedProjectResponse> call, Throwable t) {
                            dialogProgress.dismiss();
                            Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Utils.showAlertNoInternet(getActivity());
        }
    }

    private void postContactOwner(FragmentActivity activity, final Dialog alertDialog, String strName, String strEmail, String strPhone,
                                  String strCountryCode, int position) {
        if (!Utils.NoInternetConnection(activity)) {
            alertDialog.dismiss();
            dialogProgress.show();
            ApiLinks.getClient().create(ContactOwnerProjectApi.class)
                    .post(SessionManager.getAccessToken(activity),
                            projectLists.get(position).getProjectID(),
                            "Project",
                            "Listing",
                            strEmail,
                            strName,
                            strPhone,
                            strCountryCode)
                    .enqueue(new Callback<ContactOwnerResponse>() {
                        @Override
                        public void onResponse(Call<ContactOwnerResponse> call,
                                               Response<ContactOwnerResponse> response) {
                            try {
                                if (response.isSuccessful()) {
                                    String message = response.body().getResponse();
                                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
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
    private void alertDialogUnFollowProject(final FragmentActivity activity, final int position) {
        alertDialog = new Dialog(activity);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_popup, null);
        LinearLayout cancel = alert_layout.findViewById(R.id.CancelPopUp);
        LinearLayout ok = alert_layout.findViewById(R.id.ApplyPopUP);
        TextView title = alert_layout.findViewById(R.id.alertTitle);
        title.setText(getString(R.string.are_you_sure_to_remove_this_project));

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
                deleteShortlistedProject(activity, projectLists.get(position).getProjectID(),
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
        TextView title = alert_layout.findViewById(R.id.title);
        title.setText(R.string.contact_builder);

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
