package com.paya.paragon.activity.agent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.paya.paragon.PropertyProjectListActivity;
import com.paya.paragon.R;
import com.paya.paragon.activity.details.ActivityAgentDetails;
import com.paya.paragon.adapter.AgentDataListAdapter;
import com.paya.paragon.api.agentList.AgentDataListDATAItemObject;
import com.paya.paragon.base.BaseViewModelFragment;
import com.paya.paragon.databinding.FragmentAgentBinding;
import com.paya.paragon.utilities.AppConstant;
import com.paya.paragon.utilities.Utils;
import com.paya.paragon.viewmodel.AgentListFragmentViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AgentListFragment extends BaseViewModelFragment<AgentListFragmentViewModel> implements AgentListFragmentViewModel.AgentListFragmentViewModelCallBack,
        PropertyProjectListActivity.PropertyProjectListActivityCallBack {

    private FragmentAgentBinding agentBinding;
    private AgentListFragmentViewModel viewModel;
    private AgentDataListAdapter adapter;


    @Nullable

    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        agentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_agent, null, false);
        Utils.changeLayoutOrientationDynamically(getActivity(), agentBinding.getRoot());
        agentBinding.setViewModel(onCreateViewModel());
        return agentBinding.getRoot();
    }

    @Override
    public AgentListFragmentViewModel onCreateViewModel() {
        viewModel = ViewModelProviders.of(this).get(AgentListFragmentViewModel.class);
        viewModel.init(this);
        if (getActivity() != null && getActivity() instanceof PropertyProjectListActivity) {
            ((PropertyProjectListActivity) getActivity()).setAgentFragmentCallBack(this);
        }
        return viewModel;
    }


    @Override
    public void showLoader() {
        showAnimatedProgressBar(getActivity());
    }

    @Override
    public void dismissLoader() {
        dismissAnimatedProgressBar();
    }

    @Override
    public void initiateAgentListAdapter(List<AgentDataListDATAItemObject> agentList, int apiTransactionCode) {
        if (AgentListFragmentViewModel.m_AGENT_LIST_FIRST_HIT == apiTransactionCode) {
            adapter = null;
        }
        loadMoreProgressBarVisibility(false);
        if (agentList != null && !agentList.isEmpty()) {
            viewModel.updateLoadMoreBoolean();
            showNoAgentListDataFound(false);
            if (adapter == null) {
                adapter = new AgentDataListAdapter(getActivity(), agentList, AppConstant.AGENT_LIST);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                agentBinding.rvList.setLayoutManager(layoutManager);
                agentBinding.rvList.addOnScrollListener(viewModel.onAgentListScrollListener(layoutManager));
                adapter.setCallBack(new AgentDataListAdapter.AgentDataListAdapterCallBack() {
                    @Override
                    public void onItemClick(int adapterPosition, AgentDataListDATAItemObject agentData, ImageView imgAgentLogo) {
                        if (agentData.getUserID() != null && !agentData.getUserID().isEmpty()) {
                            Intent intent = new Intent(getActivity(), ActivityAgentDetails.class);
                            intent.putExtra(AppConstant.AGENT_ID, agentData.getUserID());
                            intent.putExtra(AppConstant.I_SUB_AGENT_FOR_PARENT, true);
                            intent.putExtra(AppConstant.I_ELEMENT_TRANSACTION, ViewCompat.getTransitionName(imgAgentLogo));
                            ActivityOptionsCompat  optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imgAgentLogo, ViewCompat.getTransitionName(imgAgentLogo));
                            startActivity(intent,optionsCompat.toBundle());
                        }
                    }
                });
                agentBinding.rvList.setAdapter(adapter);
            } else {
                adapter.updateList(agentList);
            }
        }
    }

    @Override
    public void showNoAgentListDataFound(boolean isDataNotFount) {
        agentBinding.rvList.setVisibility(isDataNotFount ? View.GONE : View.VISIBLE);
        agentBinding.tvNoItem.setVisibility(isDataNotFount ? View.VISIBLE : View.GONE);

    }

    @Override
    public void loadMoreProgressBarVisibility(boolean visibility) {
        agentBinding.progressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @Override
    public void updateTotalAgentCount(int totalAgentDataCount) {
        if ((PropertyProjectListActivity) getActivity() != null) {
            ((PropertyProjectListActivity) getActivity()).updateTotalAgentCount(totalAgentDataCount);
        }
    }

    @Override
    public void updateSearchIcons(boolean isSearchTextPresent) {
        if ((PropertyProjectListActivity) getActivity() != null) {
            ((PropertyProjectListActivity) getActivity()).updateSearchIcons(isSearchTextPresent);
        }
    }

    @Override
    public void hideKeyboardAutomatically() {
        if ((PropertyProjectListActivity) getActivity() != null) {
            ((PropertyProjectListActivity) getActivity()).hideKeyBoardAutomatically();
        }
    }

    @Override
    public void noInternetMessage() {
        Utils.showAlertNoInternet(getActivity());
    }

    @Override
    public void agentSearchTechChange(CharSequence charSequence) {
        viewModel.onSearchAgentTextChanges(charSequence);
    }

    @Override
    public void agentClearSearchText() {
        viewModel.onClearSearchKeyWords();
    }

    @Override
    public void agentSearchUsingEnterText() {
        viewModel.onSearchUsingKeyWords();
    }

    @Override
    public void agentSortAgentList() {
        viewModel.onSortClick();
    }
}
