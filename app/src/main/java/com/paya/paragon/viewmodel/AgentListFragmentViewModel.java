package com.paya.paragon.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.paya.paragon.api.PayaAPICall;
import com.paya.paragon.api.agentList.AgentDataListDATAItemObject;
import com.paya.paragon.api.agentList.AgentDataListDATAResponse;
import com.paya.paragon.base.BaseViewModel;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.AppConstant;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AgentListFragmentViewModel extends BaseViewModel implements PayaAPICall.PayaAPICallListener {

    private AgentListFragmentViewModelCallBack callBack;
    private PayaAPICall payaAPICall;
    private int fileStartIndex = 0;
    private int responseReturnSize = 20;
    private int totalAgentDataCount = 0;
    private String searchKeywords = "";
    public static final int m_AGENT_LIST_FIRST_HIT = 100020;
    public static final int m_AGENT_LIST_SECOND_OR_MORE_HIT = 100021;
    private boolean loadMore = false;

    public AgentListFragmentViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public void init(AgentListFragmentViewModelCallBack callBack) {
        this.callBack = callBack;
        payaAPICall = new PayaAPICall(this);
        callBack.showLoader();
        initiateAgentListCall(m_AGENT_LIST_FIRST_HIT);
    }

    private void initiateAgentListCall(int apiCallTransactionID) {
        payaAPICall.initiateAgentListCall(fileStartIndex, responseReturnSize, GlobalValues.selectedAgentSortByCount, searchKeywords, apiCallTransactionID);
    }

    public void onSortClick() {
        callBack.showLoader();
        fileStartIndex = 0;
        responseReturnSize = 20;
        initiateAgentListCall(m_AGENT_LIST_FIRST_HIT);
    }

    public void onSearchAgentTextChanges(CharSequence searchKeywords) {
        this.searchKeywords = searchKeywords.toString();
        callBack.updateSearchIcons(searchKeywords.toString().trim().length() > 0);
    }

    public void onSearchUsingKeyWords() {
        callBack.hideKeyboardAutomatically();
        if (searchKeywords.trim().length() > 0) {
            callBack.showLoader();
            fileStartIndex = 0;
            responseReturnSize = 20;
            initiateAgentListCall(m_AGENT_LIST_FIRST_HIT);
        }
    }

    public void onClearSearchKeyWords() {
        callBack.hideKeyboardAutomatically();
        if (!searchKeywords.isEmpty()) {
            callBack.showLoader();
            fileStartIndex = 0;
            responseReturnSize = 20;
            searchKeywords = "";
            callBack.updateSearchIcons(false);
            initiateAgentListCall(m_AGENT_LIST_FIRST_HIT);
        }
    }

    @Override
    public void onSuccess(int apiTransactionCode, String response) {
        initiateAgentListData(response, apiTransactionCode);
    }

    @Override
    public void onFailure(int apiTransactionCode, String message) {
        if(AppConstant.NETWORK_FAILURE == apiTransactionCode){
            callBack.noInternetMessage();
        }else{

            initiateAgentListData(null, apiTransactionCode);
        }
    }

    private void initiateAgentListData(String response, int apiTransactionCode) {
        callBack.dismissLoader();
        if (response != null && !response.isEmpty()) {
            fileStartIndex = fileStartIndex + responseReturnSize;
            AgentDataListDATAResponse agentDataListDATAResponse = new Gson().fromJson(response, AgentDataListDATAResponse.class);
            totalAgentDataCount = agentDataListDATAResponse.getTotalCount();
            callBack.updateTotalAgentCount(totalAgentDataCount);
            callBack.initiateAgentListAdapter(agentDataListDATAResponse.getData(), apiTransactionCode);
        } else {
            if (m_AGENT_LIST_FIRST_HIT == apiTransactionCode) {
                callBack.showNoAgentListDataFound(true);
                callBack.updateTotalAgentCount(0);
            } else {
                callBack.loadMoreProgressBarVisibility(false);
            }
        }
    }

    public RecyclerView.OnScrollListener onAgentListScrollListener(LinearLayoutManager layoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                    if (fileStartIndex <= totalAgentDataCount && loadMore) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loadMore = false;
                            callBack.loadMoreProgressBarVisibility(true);
                            initiateAgentListCall(m_AGENT_LIST_SECOND_OR_MORE_HIT);
                        }
                    }
                }
            }
        };
    }

    public void updateLoadMoreBoolean() {
        loadMore = fileStartIndex <= totalAgentDataCount;
    }

    public interface AgentListFragmentViewModelCallBack {
        void showLoader();

        void dismissLoader();

        void initiateAgentListAdapter(List<AgentDataListDATAItemObject> agentList, int apiTransactionCode);

        void showNoAgentListDataFound(boolean isDataNotFount);

        void loadMoreProgressBarVisibility(boolean visibility);

        void updateTotalAgentCount(int totalAgentDataCount);

        void updateSearchIcons(boolean isSearchTextPresent);

        void hideKeyboardAutomatically();

        void noInternetMessage();
    }
}
