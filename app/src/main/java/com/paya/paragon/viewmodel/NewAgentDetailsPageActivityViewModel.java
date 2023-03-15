package com.paya.paragon.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.paya.paragon.api.PayaAPICall;
import com.paya.paragon.api.agentList.AgentDataListDATAItemObject;
import com.paya.paragon.api.agentList.AgentDataListDATAResponse;
import com.paya.paragon.api.newAgent.AgentPropertyBasicDetails;
import com.paya.paragon.api.newAgent.NewAgentDetailsAndPropertyStatus;
import com.paya.paragon.api.newAgent.NewAgentDetailsDATAResponse;
import com.paya.paragon.base.BaseViewModel;
import com.paya.paragon.utilities.AppConstant;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NewAgentDetailsPageActivityViewModel extends BaseViewModel implements PayaAPICall.PayaAPICallListener {

    public static final int m_AGENT_DETAILS = 200001;
    public static final int m_AGENT_PROPERTY_LIST_LIST = 200021;
    public static final int m_AGENT_PROPERTY_LIST_SECOND_LIST = 200022;

    public static String LIMIT_FROM = "1";
    public static String LIMIT_TO = "20";

    private PayaAPICall payaAPICall;
    private NewAgentDetailsPageActivityCallBack callBack;
    public String agentUserId = "";
    private String sortPropertyCount = AppConstant.SORT_BY_DESC;
    private int fileStartIndex = 0;
    private int responseReturnSize = 20;
    private int fileStartIndexProperties = 0;
    private int responseReturnSizeProperties = 20;
    private int totalAgentDataCount = 0;
    private int totalAgentPropertyDataCount = 0;
    private boolean isSubAgentAPICalled = false;
    private boolean isAgentPropertiesAPICalled = false;
    private boolean isSubAgentForParent = false;
    private boolean loadMoreSubAgent = false;
    private boolean loadMoreAgentProperties = false;


    public NewAgentDetailsPageActivityViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public void init(NewAgentDetailsPageActivityCallBack callBack, String agentId, boolean isSubAgentForParent) {
        this.callBack = callBack;
        this.agentUserId = agentId;
        this.isSubAgentForParent = isSubAgentForParent;
        payaAPICall = new PayaAPICall(this);
        callBack.showLoader();
        initiateAPICall(m_AGENT_DETAILS);
    }

    @Override
    public void initiateAPICall(int apiTransactionID) {
        if (m_AGENT_DETAILS == apiTransactionID) {
            this.isSubAgentAPICalled = false;
            this.isAgentPropertiesAPICalled = false;
            payaAPICall.initiateAgentDetailsAPICall(agentUserId, apiTransactionID);
        } else if (AgentListFragmentViewModel.m_AGENT_LIST_FIRST_HIT == apiTransactionID ||
                AgentListFragmentViewModel.m_AGENT_LIST_SECOND_OR_MORE_HIT == apiTransactionID) {
            payaAPICall.initiateAgentSubAgentListAPICall(fileStartIndex, responseReturnSize, sortPropertyCount, "", agentUserId, apiTransactionID);
        } else if (m_AGENT_PROPERTY_LIST_LIST == apiTransactionID || m_AGENT_PROPERTY_LIST_SECOND_LIST == apiTransactionID) {
            payaAPICall.initiateAgentPropertyListAPICall(agentUserId, apiTransactionID,fileStartIndexProperties,responseReturnSizeProperties);
        }
    }

    @Override
    public void onSuccess(int apiTransactionCode, String response) {
        if (m_AGENT_DETAILS == apiTransactionCode) {
            updateAgentDetails(response);
        } else if (AgentListFragmentViewModel.m_AGENT_LIST_FIRST_HIT == apiTransactionCode ||
                AgentListFragmentViewModel.m_AGENT_LIST_SECOND_OR_MORE_HIT == apiTransactionCode) {
            initiateAgentListData(response, apiTransactionCode);
        } else if (m_AGENT_PROPERTY_LIST_LIST == apiTransactionCode || m_AGENT_PROPERTY_LIST_SECOND_LIST == apiTransactionCode) {
            updateAgentPropertyList(response,apiTransactionCode);
        }
    }

    @Override
    public void onFailure(int apiTransactionCode, String message) {
        if (AppConstant.NETWORK_FAILURE == apiTransactionCode) {
            callBack.noInternetMessage();
        } else if (AgentListFragmentViewModel.m_AGENT_LIST_FIRST_HIT == apiTransactionCode ||
                AgentListFragmentViewModel.m_AGENT_LIST_SECOND_OR_MORE_HIT == apiTransactionCode) {
            initiateAgentListData(null, apiTransactionCode);
        } else if (m_AGENT_PROPERTY_LIST_LIST == apiTransactionCode) {
            isAgentPropertiesAPICalled = true;
            dismissLoaderInitialAfterAllAPICallCompleted();
        }
    }

    private void updateAgentDetails(String response) {
        if (response != null && !response.isEmpty()) {
            NewAgentDetailsDATAResponse agentDetailsDATAResponse = new Gson().fromJson(response, NewAgentDetailsDATAResponse.class);
            if (agentDetailsDATAResponse != null && agentDetailsDATAResponse.getData() != null && agentDetailsDATAResponse.getData().getUserData() != null) {
                NewAgentDetailsAndPropertyStatus agentDetails = agentDetailsDATAResponse.getData().getUserData();
                callBack.updateAgentDetailsInUI(agentDetails, agentDetailsDATAResponse.getData().getAgentPropertyCount());
                totalAgentPropertyDataCount = agentDetailsDATAResponse.getData().getAgentPropertyCount();
                    if (agentDetailsDATAResponse.getData().getAgentPropertyCount() > 0) {
                        initiateAPICall(m_AGENT_PROPERTY_LIST_LIST);
                    }

                    else {
                        isAgentPropertiesAPICalled = true;
                    }
                    if (isSubAgentForParent) {
                        initiateAPICall(AgentListFragmentViewModel.m_AGENT_LIST_FIRST_HIT);
                    } else {
                        isSubAgentAPICalled = true;
                    }
                dismissLoaderInitialAfterAllAPICallCompleted();
            } else {
                callBack.dismissLoader();
                callBack.apiPageDataNotFound();
            }
        } else {
            callBack.dismissLoader();
            callBack.apiPageDataNotFound();
        }
    }

    private void initiateAgentListData(String response, int apiTransactionCode) {
        isSubAgentAPICalled = true;
        if (response != null && !response.isEmpty()) {
            fileStartIndex = fileStartIndex + responseReturnSize;
            AgentDataListDATAResponse agentDataListDATAResponse = new Gson().fromJson(response, AgentDataListDATAResponse.class);
            totalAgentDataCount = agentDataListDATAResponse.getTotalCount();
            callBack.initiateSubAgentListAdapter(agentDataListDATAResponse.getData(), apiTransactionCode);
        } else {
            if (AgentListFragmentViewModel.m_AGENT_LIST_FIRST_HIT == apiTransactionCode) {
                callBack.showNoAgentListDataFound(true);
            } else {
                callBack.loadMoreProgressBarVisibility(false);
            }
        }
        if (AgentListFragmentViewModel.m_AGENT_LIST_FIRST_HIT == apiTransactionCode) {
            dismissLoaderInitialAfterAllAPICallCompleted();
        } else {
            callBack.dismissLoader();
        }
    }

    private void dismissLoaderInitialAfterAllAPICallCompleted() {
        if (isSubAgentAPICalled && isAgentPropertiesAPICalled) {
            callBack.dismissLoader();
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
                    if (fileStartIndex <= totalAgentDataCount && loadMoreSubAgent) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loadMoreSubAgent = false;
                            callBack.loadMoreProgressBarVisibility(true);
                            initiateAPICall(AgentListFragmentViewModel.m_AGENT_LIST_SECOND_OR_MORE_HIT);
                        }
                    }
                }
            }
        };
    }
  /*  public RecyclerView.OnScrollListener onAgentPropertyListScrollLister(LinearLayoutManager layoutManager){
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                    if (fileStartIndexProperties <= totalAgentPropertyDataCount && loadMoreAgentProperties) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loadMoreAgentProperties = false;
                            callBack.loadMoreAgentPropertyProgressBarVisibility(true);
                            initiateAPICall(m_AGENT_PROPERTY_LIST_SECOND_LIST);
                        }
                    }
                }
            }
        };
    }*/
    public NestedScrollView.OnScrollChangeListener onAgentPropertyListScrollLister(LinearLayoutManager layoutManager){
        return new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY > 0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                    if (fileStartIndexProperties <= totalAgentPropertyDataCount && loadMoreAgentProperties) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loadMoreAgentProperties = false;
                            callBack.loadMoreAgentPropertyProgressBarVisibility(true);
                            initiateAPICall(m_AGENT_PROPERTY_LIST_SECOND_LIST);
                        }
                    }
                }

            }
        };
    }


    public void updateLoadMoreSubAgentBoolean() {
        loadMoreSubAgent = fileStartIndex <= totalAgentDataCount;
    }
    public void updateLoadMoreAgentPropertiesBoolean() {
        loadMoreAgentProperties = fileStartIndexProperties <= totalAgentPropertyDataCount;
    }
    private void updateAgentPropertyList(String response, int apiTransactionCode) {
        isAgentPropertiesAPICalled = true;
        if (response != null && !response.isEmpty()) {
            fileStartIndexProperties = fileStartIndexProperties + responseReturnSizeProperties;
            NewAgentDetailsDATAResponse agentDetailsDATAResponse = new Gson().fromJson(response, NewAgentDetailsDATAResponse.class);

            if (agentDetailsDATAResponse != null && agentDetailsDATAResponse.getData() != null && agentDetailsDATAResponse.getData().getAgentPropertyBasicDetailsList() != null
                    && !agentDetailsDATAResponse.getData().getAgentPropertyBasicDetailsList().isEmpty()) {
                    callBack.updateAgentPropertiesList(agentDetailsDATAResponse.getData().getAgentPropertyBasicDetailsList(),apiTransactionCode);
            }

        }

        dismissLoaderInitialAfterAllAPICallCompleted();
    }

    public void onFacebookClick() {
        callBack.onFacebookClick();
    }

    public void onTwitterClick() {
        callBack.onTwitterClick();
    }

    public void onLinkedInClick() {
        callBack.onLinkedInClick();
    }

    public void onContactAgentClick() {
        callBack.onContactAgentClick();
    }

    public interface NewAgentDetailsPageActivityCallBack {
        void showLoader();

        void dismissLoader();

        void noInternetMessage();

        void showToastMessage(int messageId, String message);

        void apiPageDataNotFound();

        void updateAgentDetailsInUI(NewAgentDetailsAndPropertyStatus agentDetails, int agentPropertyCount);

        void initiateSubAgentListAdapter(List<AgentDataListDATAItemObject> agentList, int apiTransactionCode);

        void showNoAgentListDataFound(boolean isDataNotFount);

        void loadMoreProgressBarVisibility(boolean visibility);

        void loadMoreAgentPropertyProgressBarVisibility(boolean visibility);

        void updateAgentPropertiesList(List<AgentPropertyBasicDetails> propertyList,int apiTransactionCode);

        void onFacebookClick();

        void onTwitterClick();

        void onLinkedInClick();

        void onContactAgentClick();
    }
}
