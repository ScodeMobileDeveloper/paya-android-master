package com.paya.paragon.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.questionCategoryList.CategoriesList;

import java.util.ArrayList;

@SuppressWarnings("RedundantCast")
public class AdapterQuestionCategoryList extends BaseAdapter implements SpinnerAdapter {

    private final Context mContext;
    private final ArrayList<CategoriesList> mNameList;

    public AdapterQuestionCategoryList(Context context, ArrayList<CategoriesList> namesDatas) {
        this.mNameList = namesDatas;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) (mContext).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        convertView = inflater.inflate(R.layout.spinner_layout, parent, false);

        TextView txt = (TextView) convertView.findViewById(R.id.spinner_text);
        txt.setTextSize(18);
        txt.setText(mNameList.get(position).getCategoryName());
        txt.setTextColor(Color.parseColor("#000000"));
        return convertView;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(mContext);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(0, 16, 16, 16);
        txt.setTextSize(16);
        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
        txt.setText(mNameList.get(position).getCategoryName());
        txt.setTextColor(Color.parseColor("#000000"));
        return txt;
    }
}
