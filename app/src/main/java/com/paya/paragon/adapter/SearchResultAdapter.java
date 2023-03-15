package com.paya.paragon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import com.paya.paragon.R;
import com.paya.paragon.api.listLocProject.RegionDataChild;
import com.paya.paragon.api.listLocProject.SearchDataLocProj;

import java.util.ArrayList;

public class SearchResultAdapter extends BaseExpandableListAdapter
{
	private Context context;
	private ArrayList<SearchDataLocProj> searchDataLocProjs;
	private String childName = "", parentName = "";


	public SearchResultAdapter(Context context, ArrayList<SearchDataLocProj> searchDataLocProjs)
	{
		this.context = context;
		this.searchDataLocProjs = searchDataLocProjs;
	}

	@Override
	public RegionDataChild getChild(int groupPosition, int childPosition)
	{
		return searchDataLocProjs.get(groupPosition).getChild().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View childConvertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		if (childConvertView == null)
		{
			childConvertView = LayoutInflater.from(context).inflate(R.layout.show_property_type_selector_list_content_2, null);
			holder = new ViewHolder();
			holder.categoryChildText = (TextView) childConvertView.findViewById(R.id.type_selector_level_two_text);
			holder.categoryChildText.setSelected(true);
			holder.categoryChildText.setSingleLine(true);
			childConvertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) childConvertView.getTag();
		}

		childName = searchDataLocProjs.get(groupPosition).getChild().get(childPosition).getOriginalText();

		holder.categoryChildText.setText(childName);
/*
		holder.categoryChildText.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

			}
		});*/

		return childConvertView;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		return searchDataLocProjs.get(groupPosition).getChild().size();
	}

	@Override
	public SearchDataLocProj getGroup(int groupPosition)
	{
		return searchDataLocProjs.get(groupPosition);
	}

	@Override
	public int getGroupCount()
	{
		return searchDataLocProjs.size();
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		return groupPosition;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded, View groupConvertView, ViewGroup parent)
	{

		ViewHolder holder = null;
		if (groupConvertView == null)
		{
			groupConvertView = LayoutInflater.from(context).inflate(R.layout.show_property_type_selector_list_content_1, null);
			holder = new ViewHolder();
			holder.categoryGroupText = (TextView) groupConvertView.findViewById(R.id.type_selector_level_one_text);
			holder.categoryGroupText.setSelected(true);
			holder.categoryGroupText.setSingleLine(true);
			groupConvertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) groupConvertView.getTag();
		}

		// parentName =
		// searchDataLocProjs.get(groupPosition).getDealCategoryName().trim() + " ("
		// + searchDataLocProjs.get(groupPosition).getDealCount().trim() + ")";
		parentName = searchDataLocProjs.get(groupPosition).getName();
		holder.categoryGroupText.setText(parentName.toUpperCase());
		//holder.icon.setImageDrawable(context.getDrawable(R.drawable.settings));
		

	/*	holder.categoryGroupText.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

			}
		});*/

	/*	if (searchDataLocProjs.get(groupPosition).getChild().size() > 0)
		{
			holder.categoryExtndImg.setVisibility(View.VISIBLE);
			if (!isExpanded)
			{
				holder.categoryExtndImg.animate().rotation(0).setDuration(100);
			} else
			{
				holder.categoryExtndImg.animate().rotation(90).setDuration(100);
			}
		} else
		{

			holder.categoryExtndImg.setVisibility(View.GONE);
		}
        holder.categoryExtndImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fireExpandRequest(groupPosition,getGroup(groupPosition));
            }
        });
		groupConvertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fireGroupSelect(groupPosition,getGroup(groupPosition));
            }
        });*/
		return groupConvertView;
	}

	@Override
	public boolean hasStableIds()
	{
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return true;
	}

	class ViewHolder
	{
		TextView categoryChildText, categoryGroupText;

	}

	public interface OnGroupExpandListener{
        void onExpandRequest(int position, SearchDataLocProj sideMenuCategory);
        void onGroupSelection(int position, SearchDataLocProj sideMenuCategory);
    }
    private OnGroupExpandListener onGroupExpandListener;

    public void setOnGroupExpandListener(OnGroupExpandListener onGroupExpandListener) {
        this.onGroupExpandListener = onGroupExpandListener;
    }
    private void fireExpandRequest(int pos,SearchDataLocProj sideMenuCategory){
        if(onGroupExpandListener!=null)
            onGroupExpandListener.onExpandRequest(pos,sideMenuCategory);
    }
    private void fireGroupSelect(int pos,SearchDataLocProj sideMenuCategory){
        if(onGroupExpandListener!=null)
            onGroupExpandListener.onGroupSelection(pos,sideMenuCategory);
    }
}
