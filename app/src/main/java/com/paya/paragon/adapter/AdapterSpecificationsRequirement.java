package com.paya.paragon.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.postProperty.attributeListing.AllAttributesData;
import com.paya.paragon.classes.CustomSpinner;
import com.paya.paragon.classes.MultiSelectData;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.model.SpecificationInfo;

import java.util.ArrayList;


@SuppressWarnings({"HardCodedStringLiteral", "ConstantConditions"})
public class AdapterSpecificationsRequirement extends RecyclerView.Adapter<AdapterSpecificationsRequirement.SpecificationViewHolder> {

    private ArrayList<AllAttributesData> mAllAttributesData;
    private Context mContext;
    private ArrayList<Integer> mFeatureCountList;
    private String action;
    private int count = 0;

    public AdapterSpecificationsRequirement(Context context, ArrayList<AllAttributesData> allAttributesList, String action) {
        this.mAllAttributesData = allAttributesList;
        this.mContext = context;
        this.action = action;
    }

    public AdapterSpecificationsRequirement(Context context, ArrayList<AllAttributesData> allAttributesList,
                                            ArrayList<Integer> countList, String action) {
        this.mAllAttributesData = allAttributesList;
        this.mContext = context;
        this.mFeatureCountList = countList;
        this.action = action;
    }

    @NonNull
    @Override
    public SpecificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_specifications_model,
                parent, false);
        return new SpecificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SpecificationViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {
        holder.setIsRecyclable(false);

        holder.mainTitle.setVisibility(View.GONE);
        final AllAttributesData data = mAllAttributesData.get(position);
        holder.title.setText(data.getAttrName());
        holder.editText.clearFocus();


        if (mFeatureCountList != null && mFeatureCountList.size() != 0) {
            if (position == 0) {
                holder.mainTitle.setVisibility(View.VISIBLE);
                holder.mainTitle.setText(data.getAttrGroupName());
            } else {
                if (count < mFeatureCountList.size()) {
                    if (position == mFeatureCountList.get(count)) {
                        holder.mainTitle.setVisibility(View.VISIBLE);
                        holder.mainTitle.setText(data.getAttrGroupName());
                        count++;
                    }
                }
            }
        }


        //TODO Post requirement
        if (action.equalsIgnoreCase("post")) {
            if (data.getSelection().equalsIgnoreCase("single")) {
                holder.layoutSpinner.setVisibility(View.VISIBLE);
                holder.layoutEdit.setVisibility(View.GONE);
                holder.layoutMultiSelect.setVisibility(View.GONE);
                String[] dataList = data.getAttrOptName().split(",");
                ArrayList<SpecificationInfo> specificationSpinnerList = new ArrayList<>();
                specificationSpinnerList.add(new SpecificationInfo("Select", "0"));
                for (String item : dataList) {
                    String[] itemSplit = item.split("_");
                    SpecificationInfo info = new SpecificationInfo();
                    info.setAttrOptName(itemSplit[0]);
                    info.setAttributeID(itemSplit[1]);
                    info.setAttrID(data.getAttrID());
                    info.setAttrGroupName(data.getAttrGroupName());
                    info.setPropertyTypeID(data.getPropertyTypeID());
                    specificationSpinnerList.add(info);
                }
                AdapterSpecificationsSpinner adapterSpinner = new AdapterSpecificationsSpinner((Activity) mContext, R.layout.item_price_listing_model,
                        R.id.text_price_list_item, specificationSpinnerList);
                holder.spinner.setAdapter(adapterSpinner);

            } else if (data.getSelection().equalsIgnoreCase("multy")) {
                holder.layoutSpinner.setVisibility(View.GONE);
                holder.layoutMultiSelect.setVisibility(View.VISIBLE);
                holder.layoutEdit.setVisibility(View.GONE);
                holder.recyclerMultiSelect.setLayoutManager(new LinearLayoutManager(mContext,
                        LinearLayoutManager.HORIZONTAL, false));
                holder.recyclerMultiSelect.setHasFixedSize(true);
                ArrayList<MultiSelectData> multiSelectData = new ArrayList<>();
                AdapterMultiSelectFeature adapterMultiSelect;
                if (data.getAttrOptName() != null && !data.getAttrOptName().equals("")) {
                    String[] bedRoomList = data.getAttrOptName().split(",");
                    for (String item : bedRoomList) {
                        String[] itemSplit = item.split("_");
                        MultiSelectData selectionInfo = new MultiSelectData();
                        selectionInfo.setAttrOptName(itemSplit[0]);
                        selectionInfo.setAttributeID(item);
                        selectionInfo.setAttrID(data.getAttrID());
                        selectionInfo.setPropertyTypeID(data.getPropertyTypeID());
                        selectionInfo.setSelection(data.getSelection());
                        selectionInfo.setAttrName(data.getAttrName());
                        selectionInfo.setAttrGroupName(data.getAttrGroupName());
                        multiSelectData.add(selectionInfo);
                    }
                    adapterMultiSelect = new AdapterMultiSelectFeature(multiSelectData, data.getPropertyAttrSelectedOptionID(),
                            new AdapterMultiSelectFeature.ItemClickAdapterListener() {
                                @Override
                                public void itemClick(String selectionValues) {
                                    mAllAttributesData.get(position).setPropertyAttrSelectedOptionID(
                                            data.getAttrID() + "_" + data.getPropertyTypeID() + ":" + selectionValues);
                                }
                            });
                    holder.recyclerMultiSelect.setAdapter(adapterMultiSelect);
                    adapterMultiSelect.notifyDataSetChanged();
                    holder.recyclerMultiSelect.setVisibility(View.VISIBLE);
                }
                holder.editText.clearFocus();

            } else {
                holder.layoutSpinner.setVisibility(View.GONE);
                holder.layoutMultiSelect.setVisibility(View.GONE);
                holder.layoutEdit.setVisibility(View.VISIBLE);
            }

            //TODO Edit Requirement
        } else if (action.equalsIgnoreCase("edit")) {
            if (data.getSelection().equalsIgnoreCase("single")) {
                holder.layoutSpinner.setVisibility(View.VISIBLE);
                holder.layoutEdit.setVisibility(View.GONE);
                holder.layoutMultiSelect.setVisibility(View.GONE);
                String[] dataList = data.getAttrOptName().split(",");
                ArrayList<SpecificationInfo> specificationSpinnerList = new ArrayList<>();
                specificationSpinnerList.add(new SpecificationInfo("Select", "0"));
                int selectionCount = 0, count = 0;
                for (String item : dataList) {
                    String[] itemSplit = item.split("_");
                    SpecificationInfo info = new SpecificationInfo();
                    info.setAttrOptName(itemSplit[0]);
                    info.setAttributeID(itemSplit[1]);
                    info.setAttrID(data.getAttrID());
                    info.setAttrGroupName(data.getAttrGroupName());
                    info.setPropertyTypeID(data.getPropertyTypeID());
                    specificationSpinnerList.add(info);
                    if (data.getPropertyAttrSelectedOptionID() != null
                            && !data.getPropertyAttrSelectedOptionID().equals("")) {
                        if (info.getAttributeID().equals(data.getPropertyAttrSelectedOptionID())) {
                            selectionCount = count;
                        } else {
                            count++;
                        }
                    }
                }
                AdapterSpecificationsSpinner adapterSpinner = new AdapterSpecificationsSpinner((Activity) mContext, R.layout.item_price_listing_model,
                        R.id.text_price_list_item, specificationSpinnerList);
                holder.spinner.setAdapter(adapterSpinner);
                if (data.getPropertyAttrSelectedOptionID() != null
                        && !data.getPropertyAttrSelectedOptionID().equals("")) {
                    holder.spinner.setSelection(selectionCount + 1);
                }
            } else if (data.getSelection().equalsIgnoreCase("multy")) {
                holder.layoutSpinner.setVisibility(View.GONE);
                holder.layoutMultiSelect.setVisibility(View.VISIBLE);
                holder.layoutEdit.setVisibility(View.GONE);
                holder.recyclerMultiSelect.setLayoutManager(new LinearLayoutManager(mContext,
                        LinearLayoutManager.HORIZONTAL, false));
                holder.recyclerMultiSelect.setHasFixedSize(true);
                ArrayList<MultiSelectData> multiSelectData = new ArrayList<>();
                AdapterMultiSelectFeature adapterMultiSelect;
                if (data.getAttrOptName() != null && !data.getAttrOptName().equals("")) {
                    String[] bedRoomList = data.getAttrOptName().split(",");
                    for (String item : bedRoomList) {
                        String[] itemSplit = item.split("_");
                        MultiSelectData selectionInfo = new MultiSelectData();
                        selectionInfo.setAttrOptName(itemSplit[0]);
                        selectionInfo.setAttributeID(item);
                        selectionInfo.setAttrID(data.getAttrID());
                        selectionInfo.setPropertyTypeID(data.getPropertyTypeID());
                        selectionInfo.setSelection(data.getSelection());
                        selectionInfo.setAttrName(data.getAttrName());
                        selectionInfo.setAttrGroupName(data.getAttrGroupName());
                        multiSelectData.add(selectionInfo);
                    }
                    adapterMultiSelect = new AdapterMultiSelectFeature(multiSelectData,
                            data.getPropertyAttrSelectedOptionID(), new AdapterMultiSelectFeature.ItemClickAdapterListener() {
                        @Override
                        public void itemClick(String selectionValues) {
                            mAllAttributesData.get(position).setPropertyAttrSelectedOptionID(
                                    data.getAttrID() + "_" + data.getPropertyTypeID() + ":" + selectionValues);
                        }
                    });

                    holder.recyclerMultiSelect.setAdapter(adapterMultiSelect);
                    adapterMultiSelect.notifyDataSetChanged();
                    holder.recyclerMultiSelect.setVisibility(View.VISIBLE);
                }
            } else {
                holder.layoutSpinner.setVisibility(View.GONE);
                holder.layoutMultiSelect.setVisibility(View.GONE);
                holder.layoutEdit.setVisibility(View.VISIBLE);
                if (data.getPropertyAttrSelectedOptionID() != null
                        && !data.getPropertyAttrSelectedOptionID().equals("")
                        && !data.getPropertyAttrSelectedOptionID().equals("0")) {
                    holder.editText.setText(data.getPropertyAttrSelectedOptionID());
                }
            }

        } else {//TODO Post Property
            if (data.getAttrOptName() != null && !data.getAttrOptName().equals("")) {
                holder.layoutSpinner.setVisibility(View.VISIBLE);
                holder.layoutEdit.setVisibility(View.GONE);
                holder.layoutMultiSelect.setVisibility(View.GONE);
                ArrayList<SpecificationInfo> specificationSpinnerList = new ArrayList<>();

                String[] dataList = data.getAttrOptName().split(",");
                specificationSpinnerList.add(new SpecificationInfo("Select", "0"));
                int selectionCount = 0, count1 = 0;
                for (String item : dataList) {
                    String[] itemSplit = item.split("_");
                    SpecificationInfo info = new SpecificationInfo();
                    info.setAttrOptName(itemSplit[0]);
                    info.setAttributeID(itemSplit[1]);
                    info.setAttrID(data.getAttrID());
                    info.setAttrGroupName(data.getAttrGroupName());
                    info.setPropertyTypeID(data.getPropertyTypeID());
                    specificationSpinnerList.add(info);

                    if (data.getPropertyAttrSelectedOptionID() != null
                            && !data.getPropertyAttrSelectedOptionID().equals("")) {
                        if (info.getAttributeID().equals(data.getPropertyAttrSelectedOptionID())) {
                            selectionCount = count1;
                        } else {
                            count1++;
                        }
                    }
                }
                AdapterSpecificationsSpinner adapterSpinner = new AdapterSpecificationsSpinner((Activity) mContext,
                        R.layout.item_price_listing_model,
                        R.id.text_price_list_item, specificationSpinnerList);
                holder.spinner.setAdapter(adapterSpinner);

                if (data.getPropertyAttrSelectedOptionID() != null
                        && !data.getPropertyAttrSelectedOptionID().equals("")) {
                    holder.spinner.setSelection(selectionCount + 1);
                }

                //selected items marked in next filter page click
                int itemPosition = -1;
                if (GlobalValues.savedAttributes != null && GlobalValues.savedAttributes.size() != 0) {
                    for (SpecificationInfo info : GlobalValues.savedAttributes) {
                        if (info.getAttrID().equals(data.getAttrID())) {
                            for (int i = 0; i < adapterSpinner.getCount(); i++) {
                                if (adapterSpinner.getItem(i).getAttrOptName().equals(info.getAttrOptName())) {
                                    itemPosition = i;
                                }
                            }
                        }
                    }
                }
                if (itemPosition != -1) {
                    holder.spinner.setSelection(itemPosition);
                }
            } else {
                holder.layoutSpinner.setVisibility(View.GONE);
                holder.layoutMultiSelect.setVisibility(View.GONE);
                holder.layoutEdit.setVisibility(View.VISIBLE);
                if (data.getPropertyAttrSelectedOptionID() != null
                        && !data.getPropertyAttrSelectedOptionID().equals("")
                        && !data.getPropertyAttrSelectedOptionID().equals("0")) {
                    holder.editText.setText(data.getPropertyAttrSelectedOptionID());
                }
            }
        }


        //TODO Click Listener
        holder.selectorText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.spinner.performClick();
            }
        });


        holder.editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    holder.editText.clearFocus();
                    holder.editText.setCursorVisible(false);
                    return true;
                }
                return false;
            }
        });

        holder.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    holder.editText.clearFocus();
                    holder.editText.setCursorVisible(false);
                    InputMethodManager inputMethodManager =
                            (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert inputMethodManager != null;
                    inputMethodManager.hideSoftInputFromWindow(holder.editText.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });


        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                mAllAttributesData.get(position).setPropertyAttrSelectedOptionID(mAllAttributesData
                        .get(position).getAttrID() + "_" + data.getPropertyTypeID() + ":" + charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int spinnerPosition, long id) {
                if (spinnerPosition != 0) {
                    SpecificationInfo selectedItem = (SpecificationInfo) holder.spinner.getAdapter()
                            .getItem(spinnerPosition);
                    holder.selectorText.setText(selectedItem.getAttrOptName());
                    mAllAttributesData.get(position).setPropertyAttrSelectedOptionID(selectedItem.getAttrID()
                            + "_" + selectedItem.getPropertyTypeID() + ":" + selectedItem.getAttributeID());

                } else {
                    holder.selectorText.setText(R.string.select);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return mAllAttributesData.size();
    }

    static class SpecificationViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutSpinner;
        LinearLayout layoutEdit;
        LinearLayout layoutMultiSelect;
        TextView title;
        TextView mainTitle;
        TextView selectorText;
        CustomSpinner spinner;
        EditText editText;
        RecyclerView recyclerMultiSelect;

        SpecificationViewHolder(View itemView) {
            super(itemView);
            this.layoutSpinner = itemView.findViewById(R.id.layout_specification_spinner);
            this.layoutEdit = itemView.findViewById(R.id.layout_specification_edit_text);
            this.layoutMultiSelect = itemView.findViewById(R.id.layout_specification_multi_select);
            this.mainTitle = itemView.findViewById(R.id.title_specification_item_spec_model);
            this.title = itemView.findViewById(R.id.title_specification_spinner_type);
            this.selectorText = itemView.findViewById(R.id.selector_text_specification_spinner_type);
            this.spinner = itemView.findViewById(R.id.spinner_specification_spinner_type);
            this.layoutSpinner = itemView.findViewById(R.id.layout_specification_spinner);
            this.editText = itemView.findViewById(R.id.edit_text_specification);
            this.recyclerMultiSelect = itemView.findViewById(R.id.recycler_multi_select_specification_model);
        }
    }

}
