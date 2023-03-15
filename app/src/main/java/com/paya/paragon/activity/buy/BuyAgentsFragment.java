package com.paya.paragon.activity.buy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.activity.AgentDetailsActivity;
import com.paya.paragon.adapter.BuyAgentListAdapter;
import com.paya.paragon.api.BuyAgents.BuyAgentsListApi;
import com.paya.paragon.api.BuyAgents.BuyAgentsListData;
import com.paya.paragon.api.BuyAgents.BuyAgentsListResponse;
import com.paya.paragon.api.BuyAgents.BuyAgentsModel;
import com.paya.paragon.api.agentList.AgentList;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

@SuppressWarnings({"HardCodedStringLiteral", "unused"})
public class BuyAgentsFragment extends Fragment {

    List<BuyAgentsModel> agentLists;
    List<BuyAgentsModel> agentListsRemain;
    String imageURLAgents;
    String countAgent;
    View view = null;
    RecyclerView rvList;
    TextView tvNoItem;
    private LinearLayoutManager mLayoutManager;
    BuyAgentListAdapter buyAgentListAdapter;
    boolean loadingMain;
    int pageCount = 0;
    String userId;
    String searchPropertyPurpose;
    Context mContext;

    public BuyAgentsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public BuyAgentsFragment(List<BuyAgentsModel> agentLists, String imageURLAgents, String countAgent, String searchPropertyPurpose) {
        this.agentLists = agentLists;
        this.imageURLAgents = imageURLAgents;
        this.countAgent = countAgent;
        this.searchPropertyPurpose = searchPropertyPurpose;
    }

    // TODO: Rename and change types and number of parameters
    public static BuyAgentsFragment newInstance(String param1, String param2) {
        return new BuyAgentsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_buy_agents, container, false);
        setData(getActivity(), agentLists, imageURLAgents, countAgent, searchPropertyPurpose);
        return view;
    }

    public void setData(Context context, List<BuyAgentsModel> agentLists, String imageURLAgents, String countAgent,
                        String searchPropertyPurpose) {
        this.agentLists = agentLists;
        this.imageURLAgents = imageURLAgents;
        this.countAgent = countAgent;
        this.mContext = context;
        this.searchPropertyPurpose = searchPropertyPurpose;
        if (SessionManager.getLoginStatus(mContext)) {
            userId = SessionManager.getAccessToken(mContext);
        } else {
            userId = "";
        }
        if (view != null) {
            rvList = view.findViewById(R.id.rv_list);
            tvNoItem = view.findViewById(R.id.tv_no_item);
            tvNoItem.setVisibility(View.GONE);
            mLayoutManager = new LinearLayoutManager(mContext);
            rvList.setLayoutManager(mLayoutManager);
            rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0) {
                        int visibleItemCount = mLayoutManager.getChildCount();
                        int totalItemCount = mLayoutManager.getItemCount();
                        int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                        if (loadingMain) {
                            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                loadingMain = false;
                                apiGetAgents();
                            }
                        }
                    }
                }
            });
            setList();
        }
    }

    //get Agents
    public void apiGetAgents() {
        Log.e("pageCount", pageCount + "");
        String location_id = "2";
        if (!SessionManager.getLocationId(mContext).equals("-1"))
            location_id = SessionManager.getLocationId(mContext);

        ApiLinks.getClient().create(BuyAgentsListApi.class).post(userId,
                "" + SessionManager.getLanguageID(mContext),
                location_id,
                searchPropertyPurpose, pageCount + "", "Agent",
                GlobalValues.selectedSortValue,
                GlobalValues.selectedMinPrice,
                GlobalValues.selectedMaxPrice,
                GlobalValues.selectedPropertyTypeID,
                "99",
                GlobalValues.selectedBedroomsNumber,
                GlobalValues.selectedRegionId,
                "",
                "",
                "",
                "",
                "").enqueue(new Callback<BuyAgentsListResponse>() {
            @Override
            public void onResponse(Call<BuyAgentsListResponse> call, Response<BuyAgentsListResponse> response) {
                if (response.isSuccessful()) {
                    BuyAgentsListData data = response.body().getData();
                    agentListsRemain = data.getAgentLists();
                    setRemain();
                } else {
                    Log.e("Status", "Failed");
                }
            }

            @Override
            public void onFailure(Call<BuyAgentsListResponse> call, Throwable t) {
            }
        });
    }

    public void setList() {
        pageCount = 1;
        if (agentLists == null || agentLists.size() == 0) {
            tvNoItem.setVisibility(View.VISIBLE);
        } else {
            boolean loadMore;
            if (agentLists.size() == 20) {
                loadMore = true;
                loadingMain = true;
            } else {
                loadMore = false;
                loadingMain = false;
            }
            buyAgentListAdapter = new BuyAgentListAdapter(imageURLAgents, loadMore, agentLists, getActivity(), new BuyAgentListAdapter.ItemClickAdapterListener() {
                @Override
                public void itemClick(View v, int position) {
                    BuyAgentsModel buyAgentsModel = agentLists.get(position);
                    AgentList agentListData = new AgentList();
                    agentListData.setUserID(buyAgentsModel.getUserID());
                    agentListData.setUserCompanyName(buyAgentsModel.getUserCompanyName());
                    agentListData.setUserUrlKey(buyAgentsModel.getUserUrlKey());
                    agentListData.setProjectCount(buyAgentsModel.getProjectCount());

                    agentListData.setSellCount(buyAgentsModel.getSellCount());
                    agentListData.setRentCount(buyAgentsModel.getRentCount());

                    Intent intent = new Intent(getActivity(),
                            AgentDetailsActivity.class);
                    intent.putExtra("agentDetail", agentListData);
                    startActivity(intent);

                }
            });
            rvList.setAdapter(buyAgentListAdapter);
        }


    }

    public void setRemain() {
        pageCount++;
        boolean loadMore;
        int lastPosition = 0;
        if (agentListsRemain == null || agentListsRemain.size() == 0) {
            loadMore = false;
            loadingMain = false;
        } else {
            Log.e("Remain  List Size", agentListsRemain.size() + "");
            if (agentListsRemain.size() == 20) {
                loadMore = true;
                loadingMain = true;
            } else {
                loadMore = false;
                loadingMain = false;
            }
            lastPosition = agentLists.size();
            agentLists.addAll(agentListsRemain);
        }
        if (loadMore) {
            buyAgentListAdapter.notifyDataSetChanged();
        } else {
            buyAgentListAdapter = new BuyAgentListAdapter(imageURLAgents, false, agentLists,
                    mContext, new BuyAgentListAdapter.ItemClickAdapterListener() {
                @Override
                public void itemClick(View v, int position) {
                    //Toast.makeText(mContext, "Item click", Toast.LENGTH_SHORT).show();
                }
            });
            rvList.setAdapter(buyAgentListAdapter);
            mLayoutManager.scrollToPosition(lastPosition);
        }
    }

}
