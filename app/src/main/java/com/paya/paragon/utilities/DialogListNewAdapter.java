package com.paya.paragon.utilities;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.base.commonClass.GlobalValues;

import java.util.List;

public class DialogListNewAdapter extends RecyclerView.Adapter<DialogListNewAdapter.ViewHolder> {
    private List<String> list;
    private String from;
    private String color = "#FFC513";
    private String black = "#000000";

    public DialogListNewAdapter(List<String> list, Context context, String from) {
        this.list = list;
        this.from = from;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dloage_content_list_style,
                parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.text.setText(list.get(position));
        if (position == list.size() - 1) {
            holder.label.setVisibility(View.GONE);
        } else {
            holder.label.setVisibility(View.VISIBLE);
        }
        if (from.equals("sort")) {
            if (GlobalValues.selectedSortText != null) {
                if (list.get(position).equals(GlobalValues.selectedSortText)) {
                    holder.text.setTextColor(Color.parseColor(color));
                } else {
                    holder.text.setTextColor(Color.parseColor(black));
                }
            }

        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        View label;
        protected TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tvcontent);
            label = itemView.findViewById(R.id.label);
        }

    }
}
