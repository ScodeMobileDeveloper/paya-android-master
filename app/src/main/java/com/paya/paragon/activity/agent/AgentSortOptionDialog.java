package com.paya.paragon.activity.agent;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.paya.paragon.R;
import com.paya.paragon.databinding.DialogAgentSortOptionsBinding;
import com.paya.paragon.utilities.AppConstant;

public class AgentSortOptionDialog extends Dialog {

    private DialogAgentSortOptionsBinding binding;
    private Context context;
    private String previousSortByName;
    private String previousSortByCount;
    private AgentSortOptionDialogCallBack callBack;


    public AgentSortOptionDialog(Context context, String previousSortByName, String previousSortByCount) {
        super(context);
        this.context = context;
        this.previousSortByName = previousSortByName;
        this.previousSortByCount = previousSortByCount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_agent_sort_options, null, false);
        setContentView(binding.getRoot());
        initiateViewWithPreviousData();
        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitClick();
            }
        });
    }

    private void onSubmitClick() {
        String sortByName = binding.rbNameAsc.isChecked() ? AppConstant.SORT_BY_ASC : AppConstant.SORT_BY_DESC;
        String sortByCount = binding.rbCountHigh.isChecked() ? AppConstant.SORT_BY_ASC : AppConstant.SORT_BY_DESC;
        if (sortByName.equalsIgnoreCase(previousSortByName) && sortByCount.equalsIgnoreCase(previousSortByCount)) {
            callBack.onSubmitClick("", "");
        } else {
            callBack.onSubmitClick(sortByName, sortByCount);
        }
    }

    private void initiateViewWithPreviousData() {
        binding.rbNameAsc.setChecked(AppConstant.SORT_BY_ASC.equalsIgnoreCase(previousSortByName));
        binding.rbNameDesc.setChecked(AppConstant.SORT_BY_DESC.equalsIgnoreCase(previousSortByName));
        binding.rbCountHigh.setChecked(AppConstant.SORT_BY_DESC.equalsIgnoreCase(previousSortByCount));
        binding.rbCountLow.setChecked(AppConstant.SORT_BY_ASC.equalsIgnoreCase(previousSortByCount));
    }

    public void setCallBack(AgentSortOptionDialogCallBack callBack) {
        this.callBack = callBack;
    }

    public interface AgentSortOptionDialogCallBack {
        void onSubmitClick(String sortByName, String sortByCount);
    }
}
