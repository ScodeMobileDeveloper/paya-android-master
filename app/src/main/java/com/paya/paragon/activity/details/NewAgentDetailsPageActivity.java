package com.paya.paragon.activity.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.paya.paragon.R;
import com.paya.paragon.adapter.AgentDataListAdapter;
import com.paya.paragon.adapter.newAgent.NewAgentPropertyBasicDetailsListAdapter;
import com.paya.paragon.api.agentList.AgentDataListDATAItemObject;
import com.paya.paragon.api.newAgent.AgentPropertyBasicDetails;
import com.paya.paragon.api.newAgent.NewAgentDetailsAndPropertyStatus;
import com.paya.paragon.base.BaseViewModelActivity;
import com.paya.paragon.databinding.ActivityNewAgentDetailsBinding;
import com.paya.paragon.utilities.AppConstant;
import com.paya.paragon.utilities.PaginationListener;
import com.paya.paragon.utilities.PropertyProjectType;
import com.paya.paragon.utilities.Utils;
import com.paya.paragon.viewmodel.AgentListFragmentViewModel;
import com.paya.paragon.viewmodel.NewAgentDetailsPageActivityViewModel;

import java.util.List;

public class NewAgentDetailsPageActivity extends BaseViewModelActivity<NewAgentDetailsPageActivityViewModel> implements
        NewAgentDetailsPageActivityViewModel.NewAgentDetailsPageActivityCallBack {

    private ActivityNewAgentDetailsBinding binding;
    private NewAgentDetailsPageActivityViewModel viewModel;

    private AgentDataListAdapter subAgentAdapter;
    private NewAgentPropertyBasicDetailsListAdapter agentPropertyAdapter;

    private String facebookURl = "";
    private String linkedInURl = "";
    private String twitterURl = "";
    private String agentUserId;
    private String agentContactNoWithCode = "";

    private boolean isSubAgentForParent = false;

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, binding.llAgentDetailsLayout);
    }

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_agent_details);
        getIntentData();
        initiateToolBar();
        binding.setViewModel(onCreateViewModel());
    }



    private void getIntentData() {
        if (getIntent() != null && getIntent().hasExtra(AppConstant.AGENT_ID)) {
            agentUserId = getIntent().getStringExtra(AppConstant.AGENT_ID);
        }
        if (getIntent() != null && getIntent().hasExtra(AppConstant.I_SUB_AGENT_FOR_PARENT)) {
            isSubAgentForParent = getIntent().getBooleanExtra(AppConstant.I_SUB_AGENT_FOR_PARENT, false);
            Log.d("subAgent",isSubAgentForParent+" ");
        }
    }

    private void initiateToolBar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow_black);
        binding.toolbarTitle.setTextSize(16);
    }

    @Override
    public NewAgentDetailsPageActivityViewModel onCreateViewModel() {
        viewModel = ViewModelProviders.of(this).get(NewAgentDetailsPageActivityViewModel.class);
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
        Utils.showAlertNoInternet(NewAgentDetailsPageActivity.this);
    }

    @Override
    public void showToastMessage(int messageId, String message) {
        Toast.makeText(this, message != null ? message : messageId > 0 ? getString(messageId) : getString(R.string.please_try_after_some_times), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void apiPageDataNotFound() {
        binding.llAgentDetailsLayout.setVisibility(View.GONE);
    }

    @Override
    public void updateAgentDetailsInUI(NewAgentDetailsAndPropertyStatus agentDetails, int agentPropertyCount) {
        binding.llAgentDetailsLayout.setVisibility(View.VISIBLE);
        binding.imgAgentLogo.setTransitionName(getIntent().getExtras().getString(AppConstant.I_ELEMENT_TRANSACTION));
        Utils.loadUrlImage(binding.imgAgentLogo, (agentDetails.getLogo() != null && !agentDetails.getLogo().isEmpty()) ? agentDetails.getImageBaseUrl() + "" + agentDetails.getLogo() : "", R.drawable.no_image_user, false);
        binding.toolbarTitle.setText(agentDetails.getName());
        binding.tvAgentName.setText(agentDetails.getName());
        if (agentDetails.getPhone() != null && !agentDetails.getPhone().isEmpty()) {
            binding.tvAgentMobileNo.setVisibility(View.VISIBLE);
            binding.contactButton.setVisibility(View.VISIBLE);
            String agentContactNo = agentDetails.getPhone();
            String firstLetter = String.valueOf(agentContactNo.charAt(0));
            agentContactNoWithCode = (firstLetter.equals("0") || firstLetter.equals("+")) ? agentContactNo : ("0" + agentContactNo);
            binding.tvAgentMobileNo.setText(agentContactNoWithCode);
        } else {
            binding.tvAgentMobileNo.setVisibility(View.GONE);
            binding.contactButton.setVisibility(View.GONE);
        }
        if (agentDetails.getAddress() != null && !agentDetails.getAddress().isEmpty()) {
            binding.tvAgentAddress.setVisibility(View.VISIBLE);
            binding.tvAgentAddress.setText(agentDetails.getAddress());
        } else {
            binding.tvAgentAddress.setVisibility(View.GONE);
        }
        if (agentDetails.getAbout() != null && !agentDetails.getAbout().isEmpty()) {
            binding.llAbout.setVisibility(View.VISIBLE);
            binding.tvAgentDetailsAbout.setText(agentDetails.getAddress());
        } else {
            binding.llAbout.setVisibility(View.GONE);
        }
        binding.tvAgentPropertiesCount.setText(getString(R.string.text_agent_properties_count, agentPropertyCount));
        if (!isFaceBookPresent(agentDetails) && !isLinkedPresent(agentDetails) && !isTwitterPresent(agentDetails)) {
            binding.trSocialLogos.setVisibility(View.GONE);
        } else {
            binding.trSocialLogos.setVisibility(View.VISIBLE);
            binding.imgFacebook.setVisibility(isFaceBookPresent(agentDetails) ? View.VISIBLE : View.GONE);
            binding.imgLinkedIn.setVisibility(isLinkedPresent(agentDetails) ? View.VISIBLE : View.GONE);
            binding.imgTwitter.setVisibility(isTwitterPresent(agentDetails) ? View.VISIBLE : View.GONE);
        }
    }

    private boolean isFaceBookPresent(NewAgentDetailsAndPropertyStatus agentDetails) {
        facebookURl = agentDetails.getFacebook();
        return agentDetails.getFacebook() != null && !agentDetails.getFacebook().isEmpty();
    }

    private boolean isLinkedPresent(NewAgentDetailsAndPropertyStatus agentDetails) {
        linkedInURl = agentDetails.getLinkedin();
        return agentDetails.getLinkedin() != null && !agentDetails.getLinkedin().isEmpty();
    }

    private boolean isTwitterPresent(NewAgentDetailsAndPropertyStatus agentDetails) {
        twitterURl = agentDetails.getTwitter();
        return agentDetails.getTwitter() != null && !agentDetails.getTwitter().isEmpty();
    }

    @Override
    public void initiateSubAgentListAdapter(List<AgentDataListDATAItemObject> agentList, int apiTransactionCode) {
        if (AgentListFragmentViewModel.m_AGENT_LIST_FIRST_HIT == apiTransactionCode) {
            subAgentAdapter = null;
        }
        loadMoreProgressBarVisibility(false);
        if (agentList != null && !agentList.isEmpty()) {
            viewModel.updateLoadMoreSubAgentBoolean();
            showNoAgentListDataFound(false);
            if (subAgentAdapter == null) {
                subAgentAdapter = new AgentDataListAdapter(this, agentList, AppConstant.AGENT_DETAILS);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                binding.rvList.setLayoutManager(layoutManager);
                binding.rvList.setHasFixedSize(false);
                SnapHelper snapHelper = new LinearSnapHelper();
                snapHelper.attachToRecyclerView(binding.rvList);
                binding.rvList.addOnScrollListener(viewModel.onAgentListScrollListener(layoutManager));
                subAgentAdapter.setCallBack(new AgentDataListAdapter.AgentDataListAdapterCallBack() {
                    @Override
                    public void onItemClick(int adapterPosition, AgentDataListDATAItemObject agentData, ImageView imgAgentLogo) {
                        if (agentData.getUserID() != null && !agentData.getUserID().isEmpty()) {
                            Intent intent = new Intent(NewAgentDetailsPageActivity.this, NewAgentDetailsPageActivity.class);
                            intent.putExtra(AppConstant.AGENT_ID, agentData.getUserID());
                            intent.putExtra(AppConstant.I_SUB_AGENT_FOR_PARENT, false);
                            intent.putExtra(AppConstant.I_ELEMENT_TRANSACTION, ViewCompat.getTransitionName(imgAgentLogo));
                            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(NewAgentDetailsPageActivity.this, imgAgentLogo, ViewCompat.getTransitionName(imgAgentLogo));
                            startActivity(intent, optionsCompat.toBundle());
                        }
                    }
                });
                binding.rvList.setAdapter(subAgentAdapter);
            } else {
                subAgentAdapter.updateList(agentList);
            }
        }
    }

    @Override
    public void showNoAgentListDataFound(boolean isDataNotFount) {
        binding.llSubAgent.setVisibility(isDataNotFount ? View.GONE : View.VISIBLE);
    }

    @Override
    public void loadMoreProgressBarVisibility(boolean visibility) {
        binding.progressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @Override
    public void loadMoreAgentPropertyProgressBarVisibility(boolean visibility) {
        binding.agentListProgressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);

    }

    @Override
    public void updateAgentPropertiesList(List<AgentPropertyBasicDetails> propertyList,int apiTransactionCode) {
        binding.llAgentProperties.setVisibility(View.VISIBLE);
        if (NewAgentDetailsPageActivityViewModel.m_AGENT_PROPERTY_LIST_LIST == apiTransactionCode) {
            agentPropertyAdapter = null;
        }
        loadMoreAgentPropertyProgressBarVisibility(false);
        if(propertyList != null && !propertyList.isEmpty()){
            viewModel.updateLoadMoreAgentPropertiesBoolean();
            if(agentPropertyAdapter == null){

                agentPropertyAdapter = new NewAgentPropertyBasicDetailsListAdapter(propertyList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                binding.rvPropertyList.setLayoutManager(layoutManager);
                binding.rvPropertyList.setHasFixedSize(false);
      /*  SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.rvPropertyList);*/
                binding.nestedScrollAgentDetails.setOnScrollChangeListener(viewModel.onAgentPropertyListScrollLister(layoutManager));

                agentPropertyAdapter.setCallBack(new NewAgentPropertyBasicDetailsListAdapter.NewAgentPropertyBasicDetailsListAdapterCallBack() {
                    @Override
                    public void onPropertyItemClickCallback(AgentPropertyBasicDetails details) {
                        Intent intent = new Intent(NewAgentDetailsPageActivity.this, ActivityPropertyDetails.class);
                        intent.putExtra("propertyID", details.getId());
                        intent.putExtra(Utils.TYPE, details.getPurpose().equalsIgnoreCase("Sell") ? PropertyProjectType.BUY : PropertyProjectType.RENT);
                        intent.putExtra("purpose", details.getPurpose());
                        startActivity(intent);
                    }
                });
                binding.rvPropertyList.setAdapter(agentPropertyAdapter);

            }else {
                agentPropertyAdapter.updateList(propertyList);

            }
        }





    }

    @Override
    public void onFacebookClick() {
        onIntentOfParticularUrl(facebookURl);

    }

    @Override
    public void onTwitterClick() {
        onIntentOfParticularUrl(twitterURl);
    }

    @Override
    public void onLinkedInClick() {
        onIntentOfParticularUrl(linkedInURl);
    }

    @Override
    public void onContactAgentClick() {
        Intent callIntent = new Intent(Intent.ACTION_VIEW);
        callIntent.setData(Uri.parse("tel:" + agentContactNoWithCode));
        startActivity(callIntent);
    }

    private void onIntentOfParticularUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
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
