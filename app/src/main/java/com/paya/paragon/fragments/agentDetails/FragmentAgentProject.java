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
import com.paya.paragon.adapter.AgentProjectListAdapter;
import com.paya.paragon.api.agentList.AgentList;
import com.paya.paragon.api.agentProjects.AgentProjectListApi;
import com.paya.paragon.api.agentProjects.AgentProjectModel;
import com.paya.paragon.api.agentProjects.AgentProjectResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.ExtendedFragment;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

@SuppressWarnings("RedundantCast")
public class FragmentAgentProject extends ExtendedFragment {
    private AgentList agentList;
    private DialogProgress progress;
    private RecyclerView recyclerView;

    private ArrayList<AgentProjectModel> projectLists;
    private ArrayList<AgentProjectModel> projectListsRemain;
    private String imagePath = "", companyImageURL = "";
    public static int totalPropertyCount = 0, paginationLimitProperty = 10;
    public static int pageCountProperty = 0, scrolledPagesProperty = 0;
    private SwipeRefreshLayout swipe;
    AgentProjectListAdapter agentProjectListAdapter;
    private LinearLayoutManager mLayoutManager;
    private TextView noData;
    private String purpose = "";
    int pageCount = 0;
    boolean loadingMain;
    String totalCount = "0";
    @Override
    public View onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container,
                                     @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agent_project, null, false);
        view = declarations(view);

        getProjectList();
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
        if (projectLists == null || projectLists.size() == 0) {
            noData.setVisibility(View.VISIBLE);
        } else {
            boolean loadMore = false;
            if (projectLists.size() == 10) {
                loadMore = true;
                loadingMain = true;
            } else {
                loadMore = false;
                loadingMain = false;
            }
            agentProjectListAdapter = new AgentProjectListAdapter(getActivity(),
                    imagePath, projectLists,false, loadMore, totalCount);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(agentProjectListAdapter);
        }
    }
    public void setRemain() {
        pageCount++;
        boolean loadMore = false;
        int lastPosition = 0;
        if (projectListsRemain == null || projectListsRemain.size() == 0) {
            loadMore = false;
            loadingMain = false;
        } else {

            if (projectListsRemain.size() == 10) {
                loadMore = true;
                loadingMain = true;
            } else {
                loadMore = false;
                loadingMain = false;
            }
            lastPosition = projectLists.size();
            for (int i = 0; i < projectListsRemain.size(); i++) {
                projectLists.add(projectListsRemain.get(i));
            }
        }
        if (loadMore) {
            agentProjectListAdapter.notifyDataSetChanged();
        } else {
            agentProjectListAdapter = new AgentProjectListAdapter(getActivity(),
                    imagePath, projectLists,false, loadMore, totalCount);
            recyclerView.setAdapter(agentProjectListAdapter);
            mLayoutManager.scrollToPosition(lastPosition);
        }
    }
    public void getProjectList() {
        progress.show();
        String userId = SessionManager.getAccessToken(getActivity());
        ApiLinks.getClient().create(AgentProjectListApi.class).post(agentList.getUserID(), "" + pageCount)
                .enqueue(new Callback<AgentProjectResponse>() {
                    @Override
                    public void onResponse(Call<AgentProjectResponse> call, Response<AgentProjectResponse> response) {
                        String message = response.body().getMessage();
                        if (response.body().getResponse().equalsIgnoreCase("Success")) {
                            imagePath = response.body().getData().getImageURL();
                            totalCount = response.body().getData().getTotalCount();
                            projectLists = response.body().getData().getProjectLists();
                           setList();
                            } else {
                                noData.setVisibility(View.VISIBLE);
                                noData.setText(response.body().getMessage());
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }
                            progress.dismiss();
                        }


                    @Override
                    public void onFailure(Call<AgentProjectResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }

                });


    } public void getProjectListRemain() {
        String userId = SessionManager.getAccessToken(getActivity());
        ApiLinks.getClient().create(AgentProjectListApi.class).post(agentList.getUserID(), "" + pageCount)
                .enqueue(new Callback<AgentProjectResponse>() {
                    @Override
                    public void onResponse(Call<AgentProjectResponse> call, Response<AgentProjectResponse> response) {
                        String message = response.body().getMessage();
                        if (response.body().getResponse().equalsIgnoreCase("Success")) {

                            projectListsRemain = response.body().getData().getProjectLists();
                           setRemain();
                            } else {

                            }

                        }


                    @Override
                    public void onFailure(Call<AgentProjectResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }

                });


    }

    private View declarations(View view) {
        GlobalValues.isAgentPropertyScrollOver = false;
        progress = new DialogProgress(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.agentProjectListRecycler);
        projectLists = new ArrayList<>();
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
                            getProjectListRemain();
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
                            getProjectList();
                        } else {
                            progress.dismiss();
                            noData.setVisibility(View.VISIBLE);
                        }

            }
        });
        return view;
    }

    public void setData(AgentList data) {
        this.agentList = data;
    }
}
