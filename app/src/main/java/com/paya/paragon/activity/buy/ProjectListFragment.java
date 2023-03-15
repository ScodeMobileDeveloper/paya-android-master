package com.paya.paragon.activity.buy;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.paya.paragon.PropertyProjectListActivity;
import com.paya.paragon.R;
import com.paya.paragon.activity.details.ActivityProjectDetails;
import com.paya.paragon.activity.login.ActivityLoginEmail;
import com.paya.paragon.adapter.BuyProjectListAdapter;
import com.paya.paragon.api.BuyProjects.BuyProjectsListApi;
import com.paya.paragon.api.BuyProjects.BuyProjectsListData;
import com.paya.paragon.api.BuyProjects.BuyProjectsListResponse;
import com.paya.paragon.api.BuyProjects.BuyProjectsModel;
import com.paya.paragon.api.addFavProject.AddFavProjectApi;
import com.paya.paragon.api.addFavProject.AddFavProjectResponse;
import com.paya.paragon.api.contactOwner.ContactOwnerProjectApi;
import com.paya.paragon.api.contactOwner.ContactOwnerResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.CountryCode.CountryCodePicker;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.PropertyProjectType;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.ShortListListener;
import com.paya.paragon.utilities.Utils;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;


@SuppressWarnings({"HardCodedStringLiteral", "unused", "ConstantConditions"})
public class ProjectListFragment extends Fragment {

    public static List<BuyProjectsModel> projectLists;
    List<BuyProjectsModel> projectListsRemain;
    String imageURLProjects;
    String countProjects;
    View view = null;
    RecyclerView recyclerList;
    TextView tvNoItem;
    private LinearLayoutManager mLayoutManager;
    public static BuyProjectListAdapter buyProjectListAdapter;
    BuyProjectListAdapter buySuggestionListAdapter;
    int pageCount = 0;
    boolean loadingMain;
    public Dialog alertDialog;
    private DialogProgress mLoadingDialog;
    String userId;
    String searchPropertyPurpose;
    public ShortListListener shortListListener;
    Context mContext;
    private PropertyProjectType type;

    public ProjectListFragment() {

    }

    @SuppressLint("ValidFragment")
    public ProjectListFragment(List<BuyProjectsModel> projectLists, String imageURLProjects,
                               String countProjects, String searchPropertyPurpose, PropertyProjectType type) {
        this.projectLists = projectLists;
        this.imageURLProjects = imageURLProjects;
        this.countProjects = countProjects;
        this.searchPropertyPurpose = searchPropertyPurpose;
        this.type = type;
    }


    // TODO: Rename and change types and number of parameters
    public static ProjectListFragment newInstance(String param1, String param2) {
        return new ProjectListFragment();
    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_buy_projects, null, false);
        Utils.changeLayoutOrientationDynamically(getActivity(), view);
        setData(getActivity(), projectLists, imageURLProjects, countProjects, searchPropertyPurpose, type);
        return view;
    }

    public void setData(Context context, List<BuyProjectsModel> projectLists, String imageURLProjects,
                        String countProjects, String searchPropertyPurpose, PropertyProjectType type) {
        this.projectLists = projectLists;
        this.imageURLProjects = imageURLProjects;
        this.countProjects = countProjects;
        this.mContext = context;
        this.type = type;
        this.searchPropertyPurpose = searchPropertyPurpose;
        if (SessionManager.getLoginStatus(mContext)) {
            userId = SessionManager.getAccessToken(mContext);
        } else {
            userId = "";
        }
        if (view != null) {

            mLoadingDialog = new DialogProgress(mContext);
            recyclerList = view.findViewById(R.id.rv_list);

            tvNoItem = view.findViewById(R.id.tv_no_item);
            tvNoItem.setVisibility(View.GONE);

            mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            recyclerList.setLayoutManager(mLayoutManager);
            recyclerList.setHasFixedSize(true);
            recyclerList.setNestedScrollingEnabled(false);
            recyclerList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0) {
                        int visibleItemCount = mLayoutManager.getChildCount();
                        int totalItemCount = mLayoutManager.getItemCount();
                        int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                        if (loadingMain) {
                            if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                loadingMain = false;
                                apiGetProjects();
                            }
                        }
                    }
                }
            });

            setList();
        }
    }

    public void setList() {
        pageCount = 1;
        if (projectLists == null || projectLists.size() == 0) {
            tvNoItem.setVisibility(View.VISIBLE);
        } else {
            boolean loadMore;
            if (projectLists.size() == 20) {
                loadMore = true;
                loadingMain = true;
            } else {
                loadMore = false;
                loadingMain = false;
            }
            buyProjectListAdapter = new BuyProjectListAdapter(imageURLProjects, loadMore, projectLists,
                    getActivity(), new BuyProjectListAdapter.ItemClickAdapterListener() {
                @Override
                public void itemClick(View v, int position) {
                    String itemId = projectLists.get(position).getProjectID();
                    Intent intent = new Intent(getActivity(),ActivityProjectDetails.class);
                    intent.putExtra("projectID", itemId);
                    intent.putExtra("position", position);
                    intent.putExtra(Utils.TYPE , ProjectListFragment.this.type);
                    intent.putExtra("purpose", searchPropertyPurpose);
                    startActivity(intent);
                }

                @Override
                public void itemCallClick(int position) {
                    alertContactBuilder(getActivity(), position);
                }

                @Override
                public void itemShareClick(int position) {
                    String shareBody = PropertyProjectListActivity.siteUrl + "" + projectLists.get(position).getLink();
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "share"));
                }

                @Override
                public void itemFavClick(int position) {
                    if (SessionManager.getLoginStatus(requireActivity())) {
                        String projectID = projectLists.get(position).getProjectID();
                        apiFavProject(projectID, userId, position);
                    } else {
                        startActivity(new Intent(getActivity(), ActivityLoginEmail.class));
                    }
                }
            });
            recyclerList.setAdapter(buyProjectListAdapter);
        }
    }

    public void setRemain() {
        pageCount++;
        boolean loadMore;
        int lastPosition = 0;
        if (projectListsRemain == null || projectListsRemain.size() == 0) {
            loadMore = false;
            loadingMain = false;
        } else {
            Log.e("Remain  List Size", projectListsRemain.size() + "");
            if (projectListsRemain.size() == 20) {
                loadMore = true;
                loadingMain = true;
            } else {
                loadMore = false;
                loadingMain = false;
            }
            lastPosition = projectLists.size();
            projectLists.addAll(projectListsRemain);

        }
        if (loadMore) {
            buyProjectListAdapter.notifyDataSetChanged();
        } else {
            buyProjectListAdapter = new BuyProjectListAdapter(imageURLProjects, false,
                    projectLists, getActivity(), new BuyProjectListAdapter.ItemClickAdapterListener() {
                @Override
                public void itemClick(View v, int position) {
                    String itemId = projectLists.get(position).getProjectID();
                    Intent intent = new Intent(getActivity(),
                            ActivityProjectDetails.class);
                    intent.putExtra("projectID", itemId);
                    intent.putExtra("position", position);
                    intent.putExtra("purpose", searchPropertyPurpose);
                    startActivity(intent);
                }

                @Override
                public void itemCallClick(int position) {
                    alertContactBuilder(getActivity(), position);
                }

                @Override
                public void itemShareClick(int position) {
                    String shareBody = PropertyProjectListActivity.siteUrl + "" + projectLists.get(position).getLink();
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "share"));
                }

                @Override
                public void itemFavClick(int position) {
                    if (SessionManager.getLoginStatus(requireActivity())) {
                        String projectID = projectLists.get(position).getProjectID();
                        apiFavProject(projectID, userId, position);
                    } else {
                        startActivity(new Intent(getActivity(), ActivityLoginEmail.class));
                    }
                }
            });
            recyclerList.setAdapter(buyProjectListAdapter);
            mLayoutManager.scrollToPosition(lastPosition);
        }
    }

    public void setListener(ShortListListener listener) {
        shortListListener = listener;
    }


    //TODO API Calls
    //get projects
    public void apiGetProjects() {
        Log.e("pageCount", pageCount + "");
        String location_id = "2";
        if (!SessionManager.getLocationId(getActivity()).equals("-1"))
            location_id = SessionManager.getLocationId(getActivity());

        ApiLinks.getClient().create(BuyProjectsListApi.class).post(userId,
                SessionManager.getLanguageID(requireActivity()),
                location_id,
                searchPropertyPurpose, pageCount + "", "Project",
                GlobalValues.selectedSortValue,
                GlobalValues.selectedMinPrice,
                GlobalValues.selectedMaxPrice,
                GlobalValues.selectedPropertyTypeID,
                GlobalValues.countryID,
                GlobalValues.selectedBedroomsNumber,
                GlobalValues.selectedFurnishedStaticValue,
                GlobalValues.selectedRegionId,
                "",
                "",
                "",
                "",
                "").enqueue(new Callback<BuyProjectsListResponse>() {
            @Override
            public void onResponse(Call<BuyProjectsListResponse> call, Response<BuyProjectsListResponse> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getResponse();
                    if (status.equals("Success")) {
                        BuyProjectsListData data = response.body().getData();
                        projectListsRemain = data.getProjectLists();
                        setRemain();
                    }
                } else {
                    Log.e("Status", "Failed");
                }
            }

            @Override
            public void onFailure(Call<BuyProjectsListResponse> call, Throwable t) {
            }
        });
    }

    //shortlist project
    public void apiFavProject(String id, String userID, final int position) {
        mLoadingDialog.show();
        ApiLinks.getClient().create(AddFavProjectApi.class).post(id, userID).enqueue(new Callback<AddFavProjectResponse>() {
            @Override
            public void onResponse(Call<AddFavProjectResponse> call, Response<AddFavProjectResponse> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    Log.e("Status", message);
                    if (response.body().getResponse().equalsIgnoreCase("Success")) {
                        String projectFavourite = projectLists.get(position).getProjectFavourite();
                        if (projectFavourite.equals("1")) {
                            projectLists.get(position).setProjectFavourite("0");
                            shortListListener.decreaseClick();
                        } else {
                            projectLists.get(position).setProjectFavourite("1");
                            shortListListener.increaseClick();
                        }
                        buyProjectListAdapter.notifyDataSetChanged();
                        mLoadingDialog.dismiss();
                    } else {
                        mLoadingDialog.dismiss();
                        Toast.makeText(getContext(), "Failed to Shortlist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mLoadingDialog.dismiss();
                    Log.e("Status", "Failed");
                    Toast.makeText(getContext(), "Failed to Shortlist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddFavProjectResponse> call, Throwable t) {
                mLoadingDialog.dismiss();
                Toast.makeText(getContext(), "Failed to Shortlist", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postContactOwner(FragmentActivity activity, final Dialog alertDialog, String strName, String strEmail, String strPhone,
                                  String strCountryCode, int position) {
        if (!Utils.NoInternetConnection(activity)) {
            alertDialog.dismiss();
            mLoadingDialog.show();
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
                        public void onResponse(Call<ContactOwnerResponse> call, Response<ContactOwnerResponse> response) {
                            try {
                                if (response.isSuccessful()) {
                                    String message = response.body().getResponse();
                                    Log.e("postContactOwner", message);
                                    int code = response.body().getCode();
                                    if (code == 4000) {
                                        alertSuccess(getActivity(), getResources().getString(R.string.thank_you_for_contact),
                                                getResources().getString(R.string.contact_owner_success_message));
                                    } else {
                                        alertDialog.dismiss();
                                        Log.e("postContactOwner", response.body().getMessage());
                                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    mLoadingDialog.dismiss();
                                } else {
                                    alertDialog.dismiss();
                                    Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                                    mLoadingDialog.dismiss();
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
                            mLoadingDialog.dismiss();
                            alertDialog.dismiss();
                        }
                    });
        } else {
            alertDialog.dismiss();
            Utils.showAlertNoInternet(activity);
        }
    }


    //TODO Alerts
    private void alertContactBuilder(final FragmentActivity activity, final int position) {
        alertDialog = new Dialog(activity);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_contact_agent_popup, null);
        ImageView close = alert_layout.findViewById(R.id.close_contact_owner);
        final EditText fullName = alert_layout.findViewById(R.id.editText_user_full_name_contact_owner);
        final EditText email = alert_layout.findViewById(R.id.editText_user_email_address_contact_owner);
        final EditText phone = alert_layout.findViewById(R.id.editText_phone_contact_owner);
        final CountryCodePicker codePicker = alert_layout.findViewById(R.id.country_code_contact_owner);
        final EditText message = alert_layout.findViewById(R.id.editText_user_message_contact_owner);
        TextView submit = alert_layout.findViewById(R.id.button_submit_contact_owner);
        CheckBox cbMortgage = alert_layout.findViewById(R.id.checkBox_info_mortgage_make_an_offer);
        final CheckBox cbTerms = alert_layout.findViewById(R.id.checkBox_terms_conditions_make_an_offer);
        message.setVisibility(View.GONE);
        final TextView title = alert_layout.findViewById(R.id.title);
        title.setText(R.string.contact_builder);
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
                String strCountryCode = codePicker.getSelectedCountryNameCode().toLowerCase();
                String strName = fullName.getText().toString();
                String strEmail = email.getText().toString();
                String strPhone = phone.getText().toString();
                String alertValidation = Utils.validateContactPopUp(getActivity(), strName, strEmail, strPhone,cbTerms);
                if (alertValidation.equals("ok")) {
                    if (!Utils.NoInternetConnection(activity)) {
                        postContactOwner(activity, alertDialog, strName, strEmail, strPhone, strCountryCode, position);
                    } else {
                        Toast.makeText(activity, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (!alertValidation.equalsIgnoreCase("notOk")) {
                        Toast.makeText(getActivity(), alertValidation, Toast.LENGTH_SHORT).show();
                    }
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

}
