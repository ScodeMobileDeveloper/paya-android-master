package com.paya.paragon.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.activity.details.ActivityAgentDetails;
import com.paya.paragon.api.agentList.AgentDataListDATAItemObject;
import com.paya.paragon.api.newAgent.AgentPropertyBasicDetails;
import com.paya.paragon.api.newAgent.NewAgentDetailsAndPropertyStatus;
import com.paya.paragon.databinding.ItemNewAgentPropertyBasicDetailsRowBinding;
import com.paya.paragon.databinding.LayoutAgentInfoBinding;
import com.paya.paragon.utilities.AppConstant;
import com.paya.paragon.utilities.Utils;
import com.paya.paragon.viewmodel.ActivityAgentDetailViewModel;
import com.paya.paragon.viewmodel.AgentListFragmentViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AgentDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private static final int LAYOUT_AGENT_INFO = 0;
    private static final int LAYOUT_SUB_AGENT = 1;
    private static final int LAYOUT_AGENT_PROPERTIES = 2;
    AgentPropertyDetailsListCallBack callBack;
    private List<AgentPropertyBasicDetails> propertyList;
    private List<AgentDataListDATAItemObject> agentList;
    NewAgentDetailsAndPropertyStatus agentDetails;
    private int viewType;
    private String iElamentTransaction;
    private String facebookURl = "";
    private String linkedInURl = "";
    private String twitterURl = "";
    private String agentUserId;
    private String agentContactNoWithCode = "";
    private int agentPropertyCount;
    private ActivityAgentDetailViewModel activityAgentDetailViewModel;
    private int fileStartIndex = 0;
    private int responseReturnSize = 20;
    private int totalAgentDataCount = 0;
    AgentDataListAdapter subagentDataListAdapter;
    private boolean loadMoreSubAgent = false;
    AgentLayoutInfoViewHolder agentLayoutInfoViewHolder;
    public AgentDetailsAdapter(ActivityAgentDetails activityAgentDetails, NewAgentDetailsAndPropertyStatus agentDetails, List<AgentDataListDATAItemObject> agentList, List<AgentPropertyBasicDetails> propertyList, String iElementTransaction, int agentPropertCount, ActivityAgentDetailViewModel viewModel) {
        this.context = activityAgentDetails;
        this.agentDetails = agentDetails;
        this.propertyList = propertyList;
        this.agentList = agentList;
        this.iElamentTransaction = iElementTransaction;
        this.agentPropertyCount = agentPropertCount;
        this.activityAgentDetailViewModel = viewModel;

    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            viewType = LAYOUT_AGENT_INFO;
        else
            viewType= LAYOUT_AGENT_PROPERTIES;

        return viewType;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if(viewType==LAYOUT_AGENT_INFO){
             LayoutAgentInfoBinding binding = LayoutAgentInfoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            viewHolder =  new AgentLayoutInfoViewHolder(binding);
        }else if(viewType == LAYOUT_AGENT_PROPERTIES){

            ItemNewAgentPropertyBasicDetailsRowBinding binding = ItemNewAgentPropertyBasicDetailsRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            viewHolder =  new AgentPropertiesViewHolder(binding);
        }

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()){
            case LAYOUT_AGENT_INFO:
                 agentLayoutInfoViewHolder = (AgentLayoutInfoViewHolder) holder;
                agentLayoutInfoViewHolder.onBind();
                break;
            case LAYOUT_AGENT_PROPERTIES:
                AgentPropertiesViewHolder agentPropertiesViewHolder = (AgentPropertiesViewHolder) holder;
                agentPropertiesViewHolder.onBind();
                break;

        }
    }

    @Override
    public int getItemCount() {
            return propertyList.size();
    }
    public void updateList(List<AgentPropertyBasicDetails> updatePropertyList) {
        int insertPosition = this.propertyList.size();
        this.propertyList.addAll(updatePropertyList);
        notifyItemRangeInserted(insertPosition, updatePropertyList.size());
    }
    public void updateSubAgentList(List<AgentDataListDATAItemObject> agentList){

        agentLayoutInfoViewHolder.agentInfoBinding.progressBar.setVisibility(View.GONE);
        subagentDataListAdapter.updateList(agentList);


    }


    public class AgentLayoutInfoViewHolder extends RecyclerView.ViewHolder{
        LayoutAgentInfoBinding agentInfoBinding;

        public AgentLayoutInfoViewHolder(@NotNull LayoutAgentInfoBinding binding) {
            super(binding.getRoot());
            this.agentInfoBinding = binding;
        }
        public void onBind(){
            if(agentList!=null&&!agentList.isEmpty()){
                agentInfoBinding.subAgentParentLay.setVisibility(View.VISIBLE);

                activityAgentDetailViewModel.updateLoadMoreSubAgentBoolean();
                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                agentInfoBinding.rvSubAgentList.setLayoutManager(layoutManager);
                subagentDataListAdapter = new AgentDataListAdapter(context,agentList,AppConstant.AGENT_DETAILS);
                agentInfoBinding.rvSubAgentList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            int visibleItemCount = layoutManager.getChildCount();
                            int totalItemCount = layoutManager.getItemCount();
                            int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                        if (fileStartIndex <= totalAgentDataCount && loadMoreSubAgent) {

                            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                    loadMoreSubAgent = false;
                                    agentInfoBinding.progressBar.setVisibility(View.VISIBLE);
                                    activityAgentDetailViewModel.initiateAPICall(AgentListFragmentViewModel.m_AGENT_LIST_SECOND_OR_MORE_HIT);
                                }
                            }

                    }
                });
                subagentDataListAdapter.setCallBack(new AgentDataListAdapter.AgentDataListAdapterCallBack() {
                    @Override
                    public void onItemClick(int adapterPosition, AgentDataListDATAItemObject agentData, ImageView imgAgentLogo) {
                        if (agentData.getUserID() != null && !agentData.getUserID().isEmpty()) {
                            Intent intent = new Intent(context, ActivityAgentDetails.class);
                            intent.putExtra(AppConstant.AGENT_ID, agentData.getUserID());
                            intent.putExtra(AppConstant.I_SUB_AGENT_FOR_PARENT, false);
                            intent.putExtra(AppConstant.I_ELEMENT_TRANSACTION, ViewCompat.getTransitionName(imgAgentLogo));
                            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, imgAgentLogo, ViewCompat.getTransitionName(imgAgentLogo));
                            context.startActivity(intent, optionsCompat.toBundle());
                        }
                    }
                });


                agentInfoBinding.rvSubAgentList.setAdapter(subagentDataListAdapter);

            }else {
                agentInfoBinding.subAgentParentLay.setVisibility(View.GONE);
            }
            agentInfoBinding.imgAgentLogo.setTransitionName(iElamentTransaction);
            Utils.loadUrlImage(agentInfoBinding.imgAgentLogo, (agentDetails.getLogo() != null && !agentDetails.getLogo().isEmpty()) ? agentDetails.getImageBaseUrl() + "" + agentDetails.getLogo() : "", R.drawable.no_image_user, false);
            agentInfoBinding.tvAgentName.setText(agentDetails.getName());
            if (agentDetails.getPhone() != null && !agentDetails.getPhone().isEmpty()) {
                agentInfoBinding.tvAgentMobileNo.setVisibility(View.VISIBLE);
                String agentContactNo = agentDetails.getPhone();
                String firstLetter = String.valueOf(agentContactNo.charAt(0));
                agentContactNoWithCode = (firstLetter.equals("0") || firstLetter.equals("+")) ? agentContactNo : ("0" + agentContactNo);
                agentInfoBinding.tvAgentMobileNo.setText(agentContactNoWithCode);
            } else {
                agentInfoBinding.tvAgentMobileNo.setVisibility(View.GONE);
            }
            if (agentDetails.getAddress() != null && !agentDetails.getAddress().isEmpty()) {
                agentInfoBinding.tvAgentAddress.setVisibility(View.VISIBLE);
                agentInfoBinding.tvAgentAddress.setText(agentDetails.getAddress());
            } else {
                agentInfoBinding.tvAgentAddress.setVisibility(View.GONE);
            }
            if (agentDetails.getAbout() != null && !agentDetails.getAbout().isEmpty()) {
                agentInfoBinding.llAbout.setVisibility(View.VISIBLE);
                agentInfoBinding.tvAgentDetailsAbout.setText(agentDetails.getAddress());
            } else {
                agentInfoBinding.llAbout.setVisibility(View.GONE);
            }
            agentInfoBinding.tvAgentPropertiesCount.setText(context.getString(R.string.text_agent_properties_count, agentPropertyCount));
            if(agentPropertyCount==0){
                agentInfoBinding.tvAgentPropertiesLabel.setVisibility(View.GONE);
            }else {
                agentInfoBinding.tvAgentPropertiesLabel.setVisibility(View.VISIBLE);

            }
            if (!isFaceBookPresent(agentDetails) && !isLinkedPresent(agentDetails) && !isTwitterPresent(agentDetails)) {
                agentInfoBinding.trSocialLogos.setVisibility(View.GONE);
            } else {
                agentInfoBinding.trSocialLogos.setVisibility(View.VISIBLE);
                agentInfoBinding.imgFacebook.setVisibility(isFaceBookPresent(agentDetails) ? View.VISIBLE : View.GONE);
                agentInfoBinding.imgLinkedIn.setVisibility(isLinkedPresent(agentDetails) ? View.VISIBLE : View.GONE);
                agentInfoBinding.imgTwitter.setVisibility(isTwitterPresent(agentDetails) ? View.VISIBLE : View.GONE);
            }
            agentInfoBinding.tvAgentMobileNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onContactAgentClick();

                }
            });
            agentInfoBinding.imgFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onIntentOfParticularUrl(facebookURl);
                }
            });
            agentInfoBinding.imgLinkedIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onIntentOfParticularUrl(linkedInURl);
                }
            });
            agentInfoBinding.imgTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onIntentOfParticularUrl(twitterURl);

                }
            });

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
        private void onIntentOfParticularUrl(String url) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        }
        public void onContactAgentClick() {
            Intent callIntent = new Intent(Intent.ACTION_VIEW);
            callIntent.setData(Uri.parse("tel:" + agentContactNoWithCode));
            context.startActivity(callIntent);
        }
    }

    public class AgentPropertiesViewHolder extends RecyclerView.ViewHolder{
        ItemNewAgentPropertyBasicDetailsRowBinding binding;
         public AgentPropertiesViewHolder(@NonNull ItemNewAgentPropertyBasicDetailsRowBinding binding){
             super(binding.getRoot());
             this.binding = binding;
         }
         public void onBind(){
             AgentPropertyBasicDetails details = propertyList.get(getAdapterPosition());
             Utils.loadUrlImage(binding.imgPropertyCoverImage, details.getImageUrl() + details.getPropertyImageName(), R.drawable.no_image_placeholder, false);
             binding.tvAPName.setText(details.getPropertyName());
             if (details != null && details.getPropertyAddress() != null && !details.getPropertyAddress().isEmpty()) {
                 binding.tvAPAddress.setVisibility(View.VISIBLE);
                 binding.tvAPAddress.setText(details.getPropertyAddress());
             } else {
                 binding.tvAPAddress.setVisibility(View.GONE);
             }
             binding.tvAPPrice.setText(details.getPropertyPrice());
             binding.getRoot().setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if (callBack != null) {
                         callBack.onPropertyItemClickCallback(details);
                     }
                 }
             });
         }
    }
    public void setCallBack(AgentPropertyDetailsListCallBack callBack) {
        this.callBack = callBack;
    }

    public interface AgentPropertyDetailsListCallBack{
        void onPropertyItemClickCallback(AgentPropertyBasicDetails agentPropertyBasicDetails);
    }

}
