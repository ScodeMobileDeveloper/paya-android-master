package com.paya.paragon.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
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
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.model.SpecificationInfo;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;


@SuppressWarnings({"HardCodedStringLiteral", "ConstantConditions"})
public class AdapterSpecificationsFilter extends RecyclerView.Adapter<AdapterSpecificationsFilter.SpecificationViewHolder> {

    private ArrayList<AllAttributesData> mAllAttributesData;
    private Context mContext;

    public AdapterSpecificationsFilter(Context context, ArrayList<AllAttributesData> allAttributesList) {
        this.mAllAttributesData = allAttributesList;
        this.mContext = context;
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



        //TODO Filter setting
        if (data.getAttrOptName() != null && !data.getAttrOptName().equals("")) {
            holder.layoutSpinner.setVisibility(View.VISIBLE);
            holder.layoutEdit.setVisibility(View.GONE);
            holder.layoutMultiSelect.setVisibility(View.GONE);
            ArrayList<SpecificationInfo> specificationSpinnerList = new ArrayList<>();

            String[] dataList = data.getAttrOptName().split(",");
            specificationSpinnerList.add(new SpecificationInfo(mContext.getString(R.string.select), "0"));
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

                if (GlobalValues.selectedFurnishedValue != null
                        && !GlobalValues.selectedFurnishedValue.equals("")) {
                    if (info.getAttributeID().equals(GlobalValues.selectedFurnishedValue)) {
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

            if (GlobalValues.selectedFurnishedValue != null
                    && !GlobalValues.selectedFurnishedValue.equals("")) {
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
                    && !data.getPropertyAttrSelectedOptionID().equals("")) {
                holder.editText.setText(data.getPropertyAttrSelectedOptionID());
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

                mAllAttributesData.get(position).setPropertyAttrSelectedOptionID(charSequence.toString());
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
                    mAllAttributesData.get(position).setPropertyAttrSelectedOptionID(selectedItem.getAttrOptName());
                    GlobalValues.selectedFurnishedValue = selectedItem.getAttributeID();

                    if(Utils.ATTR_FURNISHED_ID.equalsIgnoreCase(mAllAttributesData.get(position).getAttrID())){

                        // Hardcoded value for furnished status
                        String selectedValue = "";
                        if(spinnerPosition==1){
                            selectedValue = "All";

                        }else if(spinnerPosition==2){
                            selectedValue = "Yes";

                        }else if(spinnerPosition ==3){
                            selectedValue = "No";

                        }else if(spinnerPosition == 4){
                            selectedValue ="Partially";
                        }
                        GlobalValues.selectedFurnishedStaticValue = selectedValue;
                    }
                } else {
                    holder.selectorText.setText(R.string.select);
                    mAllAttributesData.get(position).setPropertyAttrSelectedOptionID("");

                    GlobalValues.selectedFurnishedValue = "";
                    GlobalValues.selectedFurnishedStaticValue = "";

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mAllAttributesData.get(position).setPropertyAttrSelectedOptionID("");
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
