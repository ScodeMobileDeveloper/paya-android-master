package com.paya.paragon.activity.dashboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.PropertyProjectListActivity;
import com.paya.paragon.R;
import com.paya.paragon.activity.postRequirements.ActivityRequirementPropertyType;
import com.paya.paragon.adapter.AdapterPostedRequirements;
import com.paya.paragon.api.postedRequirements.PostedRequirementsApi;
import com.paya.paragon.api.postedRequirements.PostedRequirementsDataModel;
import com.paya.paragon.api.postedRequirements.PostedRequirementsResponse;
import com.paya.paragon.api.postedRequirementsDelete.PostedRequirementsDeleteApi;
import com.paya.paragon.api.postedRequirementsDelete.PostedRequirementsDeleteResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.model.RequirementModel;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"HardCodedStringLiteral", "RedundantCast"})
@SuppressLint("SetTextI18n")
public class ActivityPostedRequirements extends AppCompatActivity {

    ArrayList<PostedRequirementsDataModel> postedRequirements;
    AdapterPostedRequirements adapterPostedRequirements;
    RecyclerView recyclerPostedRequirements;
    DialogProgress dialogProgress;
    public Dialog alertDialog;
    TextView noRecords;
    private static RequirementModel requirementModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_posted_requirements);

        setToolBar();
        declarations();

        if (!Utils.NoInternetConnection(ActivityPostedRequirements.this)) {
            dialogProgress.show();
            getPostedRequirements();
        } else {
            Utils.showAlertNoInternet(ActivityPostedRequirements.this);
        }
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        mTitle.setText(R.string.posted_requirements);
    }


    //TODO API Calls
    public void getPostedRequirements() {
        ApiLinks.getClient().create(PostedRequirementsApi.class)
                .post(SessionManager.getAccessToken(ActivityPostedRequirements.this), SessionManager.getLanguageID(this))
                .enqueue(new Callback<PostedRequirementsResponse>() {
                    @Override
                    public void onResponse(Call<PostedRequirementsResponse> call,
                                           Response<PostedRequirementsResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getResponse();
                            int code = response.body().getCode();
                            if (code == 4004 && message.equalsIgnoreCase("Success")) {
                                postedRequirements = response.body().getData();
                                adapterPostedRequirements = new AdapterPostedRequirements(ActivityPostedRequirements.this,
                                        postedRequirements, new AdapterPostedRequirements.ItemClickAdapterListener() {
                                    @Override
                                    public void deleteClick(int position) {
                                        alertDialogDeleteRequirement(position);
                                    }

                                    @Override
                                    public void editClick(int position) {
                                        requirementModel.setRequirementAction("edit");
                                        requirementModel.setRequirementID(postedRequirements.get(position).getReqID());
                                        requirementModel.setPurpose(postedRequirements.get(position).getReqPurpose());
                                        SessionManager.setPostRequirement(ActivityPostedRequirements.this,
                                                requirementModel);
                                        startActivity(new Intent(ActivityPostedRequirements.this,
                                                ActivityRequirementPropertyType.class));
                                    }
                                });
                                recyclerPostedRequirements.setAdapter(adapterPostedRequirements);
                                dialogProgress.dismiss();
                            } else {
                                dialogProgress.dismiss();
                                noRecords.setVisibility(View.VISIBLE);
                            }
                        } else {
                            dialogProgress.dismiss();
                            noRecords.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostedRequirementsResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                        noRecords.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void deletePostedRequirements(String requirementID, final int position) {
        dialogProgress.show();
        ApiLinks.getClient().create(PostedRequirementsDeleteApi.class).post(requirementID,
                SessionManager.getAccessToken(ActivityPostedRequirements.this),
                SessionManager.getLanguageID(this))
                .enqueue(new Callback<PostedRequirementsDeleteResponse>() {
                    @Override
                    public void onResponse(Call<PostedRequirementsDeleteResponse> call,
                                           Response<PostedRequirementsDeleteResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getResponse();
                            int code = response.body().getCode();
                            if (code == 4004 && message.equalsIgnoreCase("Success")) {
                                Toast.makeText(ActivityPostedRequirements.this, getString(R.string.deleted), Toast.LENGTH_SHORT).show();
                                postedRequirements.remove(position);
                                adapterPostedRequirements.notifyDataSetChanged();
                                dialogProgress.dismiss();
                                if (postedRequirements.size() == 0) {
                                    noRecords.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(ActivityPostedRequirements.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                                dialogProgress.dismiss();
                            }
                        } else {
                            dialogProgress.dismiss();
                            Toast.makeText(ActivityPostedRequirements.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PostedRequirementsDeleteResponse> call, Throwable t) {
                        dialogProgress.dismiss();
                        Toast.makeText(ActivityPostedRequirements.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //TODO Set Data
    private void declarations() {
        requirementModel = new RequirementModel();
        SessionManager.setPostRequirement(ActivityPostedRequirements.this, requirementModel);
        dialogProgress = new DialogProgress(ActivityPostedRequirements.this);

        recyclerPostedRequirements = (RecyclerView) findViewById(R.id.recycler_posted_requirements);
        recyclerPostedRequirements.setLayoutManager(new LinearLayoutManager(ActivityPostedRequirements.this));

        noRecords = findViewById(R.id.text_no_records_found);
        noRecords.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.rl_posted_requirement_parent));
    }

    //TODO Alert Delete
    private void alertDialogDeleteRequirement(final int position) {
        alertDialog = new Dialog(ActivityPostedRequirements.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert factory != null;
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_popup, null);
        LinearLayout cancel = alert_layout.findViewById(R.id.CancelPopUp);
        LinearLayout ok = alert_layout.findViewById(R.id.ApplyPopUP);
        TextView title = alert_layout.findViewById(R.id.alertTitle);
        title.setText(getString(R.string.are_you_sure_to_remove_this_requirment));

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
                deletePostedRequirements(postedRequirements.get(position).getReqID(), position);
            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
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
        Intent intent = new Intent(this, PropertyProjectListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("searchPropertyPurpose", "Sell");
        startActivity(intent);
        finish();
    }
}
