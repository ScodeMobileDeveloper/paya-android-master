package com.paya.paragon.fragments.agentDetails;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.paya.paragon.R;
import com.paya.paragon.adapter.AgentPropertyListAdapter;
import com.paya.paragon.api.agentList.AgentList;
import com.paya.paragon.api.agentProperty.AgentPropertyListApi;
import com.paya.paragon.api.agentProperty.AgentPropertyModel;
import com.paya.paragon.api.agentProperty.AgentPropertyResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.ExtendedFragment;
import com.paya.paragon.utilities.Utils;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

@SuppressWarnings("RedundantCast")
public class FragmentAgentProperty extends ExtendedFragment {
    private AgentList agentList;
    private DialogProgress progress;
    private RecyclerView recyclerView;
    private ArrayList<AgentPropertyModel> agentPropertyModels;
    private ArrayList<AgentPropertyModel> agentPropertyRemain;
    private String imagePath = "", companyImageURL = "";
    public static int totalPropertyCount = 0, paginationLimitProperty = 10;
    public static int pageCountProperty = 0, scrolledPagesProperty = 0;
    private SwipeRefreshLayout swipe;
    AgentPropertyListAdapter agentPropertyListAdapter;
    private TextView noData;
    private String purpose = "";
    private LinearLayoutManager mLayoutManager;

    OkHttpClient client;
    int pageCount = 0;
    boolean loadingMain;
    String totalCount = "0";
    @Override
    public View onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container,
                                     @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agent_project, null, false);
        view = declarations(view);

        getPropertyList();
        return view;
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        } else {
            pageCount=0;
            loadingMain = false;
        }
    }
    public void setList() {
        pageCount = 1;
        if (agentPropertyModels == null || agentPropertyModels.size() == 0) {
            noData.setVisibility(View.VISIBLE);
        } else {
            boolean loadMore = false;
            if (agentPropertyModels.size() == 10) {
                loadMore = true;
                loadingMain = true;
            } else {
                loadMore = false;
                loadingMain = false;
            }
            agentPropertyListAdapter = new AgentPropertyListAdapter(getActivity(),
                    imagePath, agentPropertyModels, loadMore, totalCount);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(agentPropertyListAdapter);
        }
    }

    public void setRemain() {
        pageCount++;
        boolean loadMore = false;
        int lastPosition = 0;
        if (agentPropertyRemain == null || agentPropertyRemain.size() == 0) {
            loadMore = false;
            loadingMain = false;
        } else {

            if (agentPropertyRemain.size() == 10) {
                loadMore = true;
                loadingMain = true;
            } else {
                loadMore = false;
                loadingMain = false;
            }
            lastPosition = agentPropertyModels.size();
            for (int i = 0; i < agentPropertyRemain.size(); i++) {
                agentPropertyModels.add(agentPropertyRemain.get(i));
            }
        }
        if (loadMore) {
            agentPropertyListAdapter.notifyDataSetChanged();
        } else {
            agentPropertyListAdapter = new AgentPropertyListAdapter(getActivity(),
                    imagePath, agentPropertyModels, loadMore, totalCount);
            recyclerView.setAdapter(agentPropertyListAdapter);
            mLayoutManager.scrollToPosition(lastPosition);
        }
    }
    public void getPropertyList() {
        progress.show();
        ApiLinks.getClient().create(AgentPropertyListApi.class).post(agentList.getUserID(), purpose, "" + pageCount)
                .enqueue(new Callback<AgentPropertyResponse>() {
                    @Override
                    public void onResponse(Call<AgentPropertyResponse> call, Response<AgentPropertyResponse> response) {
                        String message = response.body().getMessage();
                        if (response.body().getResponse().equalsIgnoreCase("Success")) {
                            totalPropertyCount = Integer.valueOf(response.body().getData().getTotalCount());
                            imagePath = response.body().getData().getImageURL();
                            totalCount = response.body().getData().getTotalCount();
                            agentPropertyModels = response.body().getData().getPropertyLists();
                            setList();
                        } else {
                            noData.setVisibility(View.VISIBLE);
                            noData.setText(response.body().getMessage());
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        }
                        progress.dismiss();
                    }

                    @Override
                    public void onFailure(Call<AgentPropertyResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                });


    } public void getPropertyListRemain() {
        ApiLinks.getClient().create(AgentPropertyListApi.class).post(agentList.getUserID(), purpose, "" + pageCount)
                .enqueue(new Callback<AgentPropertyResponse>() {
                    @Override
                    public void onResponse(Call<AgentPropertyResponse> call, Response<AgentPropertyResponse> response) {
                        String message = response.body().getMessage();
                        if (response.body().getResponse().equalsIgnoreCase("Success")) {

                            agentPropertyRemain = response.body().getData().getPropertyLists();
                            setRemain();
                        } else {

                        }

                    }

                    @Override
                    public void onFailure(Call<AgentPropertyResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                });


    }

    private View declarations(View view) {
        GlobalValues.isAgentPropertyScrollOver = false;
        progress = new DialogProgress(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.agentProjectListRecycler);
        agentPropertyModels = new ArrayList<>();
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_agentProjectListRecycler);
        noData = (TextView) view.findViewById(R.id.no_data);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            getPropertyListRemain();
                        }
                    }
                }
            }
        });
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                        swipe.setRefreshing(false);
                        if (!Utils.NoInternetConnection(getActivity())) {
                            pageCount = 0;
                            getPropertyList();
                        } else {
                            progress.dismiss();
                            noData.setVisibility(View.VISIBLE);
                        }

            }
        });
        return view;
    }

    public void setData(AgentList data, String purpose) {
        this.agentList = data;
        this.purpose = purpose;
    }
}
