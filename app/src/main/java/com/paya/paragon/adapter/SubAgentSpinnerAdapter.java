package com.paya.paragon.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.paya.paragon.R;
import com.paya.paragon.api.agentList.AgentDataListDATAItemObject;

import java.util.List;

public class SubAgentSpinnerAdapter extends ArrayAdapter<AgentDataListDATAItemObject> {

    private LayoutInflater inflater;

    public SubAgentSpinnerAdapter(Activity context, int resourceId, int testViewId, List<AgentDataListDATAItemObject> subAgentSpinnerData) {
        super(context, resourceId, testViewId, subAgentSpinnerData);
        inflater = context.getLayoutInflater();
    }

    @NonNull
    @Override
    @SuppressLint({"ViewHolder", "InflateParams"})
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return itemView(position);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return itemView(position);
    }

    private View itemView(int position) {
        AgentDataListDATAItemObject info = getItem(position);
        View itemView = inflater.inflate(R.layout.item_price_listing_model, null, true);
        TextView txtTitle = itemView.findViewById(R.id.text_price_list_item);
        txtTitle.setText(info.getName());
        return itemView;
    }


}
