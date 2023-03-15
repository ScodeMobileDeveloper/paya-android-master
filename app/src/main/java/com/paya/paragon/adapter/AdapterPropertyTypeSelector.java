package com.paya.paragon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.index.PropertyTypeMain;
import com.paya.paragon.api.index.PropertyTypeSub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ConstantConditions")
public class AdapterPropertyTypeSelector extends BaseExpandableListAdapter {

    private final Context mContext;
    private final List<String> mLevelOneData;
    private final Map<String, List<PropertyTypeSub>> mLevelTwoData;
    private final ArrayList<PropertyTypeMain> mParentList;

    public AdapterPropertyTypeSelector(Context context, ArrayList<PropertyTypeMain> propertyTypeList) {
        this.mContext = context;
        this.mParentList = propertyTypeList;

        //First Level
        mLevelOneData = new ArrayList<>();
        mLevelTwoData = new HashMap<>();

        for (PropertyTypeMain parent : propertyTypeList) {
            if (parent.getSubCategory().size() > 0) {
                mLevelOneData.add(parent.getPropertyTypeName());
            }
        }

        //Second level
        for (PropertyTypeMain parent : propertyTypeList) {
            mLevelTwoData.put(parent.getPropertyTypeName(), parent.getSubCategory());
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.mLevelTwoData.get(this.mLevelOneData.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        PropertyTypeSub child = (PropertyTypeSub) getChild(groupPosition, childPosition);
        String titleData = child.getPropertyTypeName();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.show_property_type_selector_list_content_2, parent, false);
        }
        TextView lblListHeader = convertView.findViewById(R.id.type_selector_level_two_text);
        lblListHeader.setText(titleData);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.mParentList.get(groupPosition).getSubCategory().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mLevelOneData.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.mLevelOneData.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = mParentList.get(groupPosition).getPropertyTypeName();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.show_property_type_selector_list_content_1, parent, false);
        }
        TextView lblListHeader = convertView.findViewById(R.id.type_selector_level_one_text);
        lblListHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
