package com.paya.paragon.activity.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.paya.paragon.R;
import com.paya.paragon.adapter.AgentDataListAdapter;
import com.paya.paragon.adapter.AgentDetailsAdapter;
import com.paya.paragon.api.agentList.AgentDataListDATAItemObject;
import com.paya.paragon.api.newAgent.AgentPropertyBasicDetails;
import com.paya.paragon.api.newAgent.NewAgentDetailsAndPropertyStatus;
import com.paya.paragon.base.BaseViewModelActivity;
import com.paya.paragon.databinding.ActivityAgentInfoBinding;
import com.paya.paragon.utilities.AppConstant;
import com.paya.paragon.utilities.PropertyProjectType;
import com.paya.paragon.utilities.Utils;
import com.paya.paragon.viewmodel.ActivityAgentDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class ActivityAgentDetails extends BaseViewModelActivity<ActivityAgentDetailViewModel> implements ActivityAgentDetailViewModel.ActivityAgentDetailCallBack {

    private ActivityAgentInfoBinding binding;
    private ActivityAgentDetailViewModel viewModel;


    private AgentDetailsAdapter agentDetailsAdapter;
    private AgentDataListAdapter subagentDataListAdapter;
    private String agentUserId, iElementTransaction;
    private boolean isSubAgentForParent = false;
    private String agentContactNoWithCode = "";

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, binding.rlAgentDetailsLay);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_agent_info);
        getIntentData();
        initiateToolBar();
        declarations();
        loadMoreAgentPropertyProgressBarVisibility(false);
        binding.setViewModel(onCreateViewModel());
    }

    private void declarations() {


    }

    private void initiateToolBar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow_black);
        binding.toolbarTitle.setTextSize(16);
    }

    private void getIntentData() {
        if (getIntent() != null && getIntent().hasExtra(AppConstant.AGENT_ID)) {
            agentUserId = getIntent().getStringExtra(AppConstant.AGENT_ID);
        }
        if (getIntent() != null && getIntent().hasExtra(AppConstant.I_SUB_AGENT_FOR_PARENT)) {
            isSubAgentForParent = getIntent().getBooleanExtra(AppConstant.I_SUB_AGENT_FOR_PARENT, false);
        }
        if (getIntent() != null && getIntent().hasExtra(AppConstant.I_ELEMENT_TRANSACTION)) {
            iElementTransaction = getIntent().getExtras().getString(AppConstant.I_ELEMENT_TRANSACTION);

        }

    }


    @Override
    public ActivityAgentDetailViewModel onCreateViewModel() {
        viewModel = ViewModelProviders.of(this).get(ActivityAgentDetailViewModel.class);
        viewModel.init(this, agentUserId, isSubAgentForParent);
        return viewModel;
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
    public void noInternetMessage() {
        dismissAnimatedProgressBar();
        Utils.showAlertNoInternet(ActivityAgentDetails.this);
    }

    @Override
    public void showToastMessage(int messageId, String message) {

    }

    @Override
    public void apiPageDataNotFound() {
        binding.csAgentDetailsLay.setVisibility(View.GONE);
    }

    @Override
    public void updateSugAgentList(List<AgentDataListDATAItemObject> agentList) {

        agentDetailsAdapter.updateSubAgentList(agentList);


    }


    @Override
    public void loadMoreAgentPropertyProgressBarVisibility(boolean visibility) {
        binding.agentListProgressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);

    }


    @Override
    public void updateAgentSubAgentPropertyListToUI(NewAgentDetailsAndPropertyStatus agentDetails, List<AgentDataListDATAItemObject> agentList, List<AgentPropertyBasicDetails> propertyList, int AgentPropertCount, int apiTransactionCode) {
        binding.toolbarTitle.setText(agentDetails.getName());
        if (ActivityAgentDetailViewModel.m_AGENT_PROPERTY_LIST_LIST == apiTransactionCode) {
            agentDetailsAdapter = null;
        }

        loadMoreAgentPropertyProgressBarVisibility(false);
        viewModel.updateLoadMoreAgentPropertiesBoolean();
        if (agentDetails.getPhone() != null && !agentDetails.getPhone().isEmpty()) {
            binding.contactButton.setVisibility(View.VISIBLE);
            String agentContactNo = agentDetails.getPhone();
            String firstLetter = String.valueOf(agentContactNo.charAt(0));
            agentContactNoWithCode = (firstLetter.equals("0") || firstLetter.equals("+")) ? agentContactNo : ("0" + agentContactNo);
        } else {
            binding.contactButton.setVisibility(View.GONE);
        }

        if (agentDetailsAdapter == null) {

            if (propertyList == null) {
                propertyList = new ArrayList<>();

            }
            Log.d("agent", agentUserId);
            AgentPropertyBasicDetails agentPropertyBasicDetails = new AgentPropertyBasicDetails();
            propertyList.add(0, agentPropertyBasicDetails);

            agentDetailsAdapter = new AgentDetailsAdapter(this, agentDetails, agentList, propertyList, iElementTransaction, AgentPropertCount, viewModel);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            binding.rcvAgentDetails.setLayoutManager(layoutManager);
            binding.rcvAgentDetails.setHasFixedSize(false);
            binding.rcvAgentDetails.addOnScrollListener(viewModel.onAgentPropertyListScrollLister(layoutManager));
            agentDetailsAdapter.setCallBack(new AgentDetailsAdapter.AgentPropertyDetailsListCallBack() {
                @Override
                public void onPropertyItemClickCallback(AgentPropertyBasicDetails agentPropertyBasicDetails) {
                    Intent intent = new Intent(ActivityAgentDetails.this, ActivityPropertyDetails.class);
                    intent.putExtra("propertyID", agentPropertyBasicDetails.getId());
                    intent.putExtra(Utils.TYPE, agentPropertyBasicDetails.getPurpose().equalsIgnoreCase("Sell") ? PropertyProjectType.BUY : PropertyProjectType.RENT);
                    intent.putExtra("purpose", agentPropertyBasicDetails.getPurpose());
                    startActivity(intent);
                }
            });
            binding.rcvAgentDetails.setAdapter(agentDetailsAdapter);
        } else {
            agentDetailsAdapter.updateList(propertyList);
        }


    }

    @Override
    public void onContactAgentClick() {
        Intent callIntent = new Intent(Intent.ACTION_VIEW);
        callIntent.setData(Uri.parse("tel:" + agentContactNoWithCode));
        startActivity(callIntent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}