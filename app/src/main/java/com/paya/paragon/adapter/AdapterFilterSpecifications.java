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

import java.util.ArrayList;

@SuppressWarnings("HardCodedStringLiteral")
public class AdapterFilterSpecifications extends RecyclerView.Adapter<AdapterFilterSpecifications.SpecificationViewHolder> {

    private ArrayList<AllAttributesData> mAllAttributesData;
    private Context mContext;
    private SpecificationInfo editTextSpecification;
    private AllAttributesData editTextAttribute;
    private ItemClickAdapterListener itemClickAdapterListener;

    public AdapterFilterSpecifications(ArrayList<AllAttributesData> allAttributesList, Context context,
                                       ItemClickAdapterListener itemClickAdapterListener) {
        this.mAllAttributesData = allAttributesList;
        this.mContext = context;
        this.itemClickAdapterListener = itemClickAdapterListener;
        if (GlobalValues.savedAttributes == null) {
            GlobalValues.savedAttributes = new ArrayList<>();
        }
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
        AllAttributesData data = mAllAttributesData.get(position);
        holder.title.setText(data.getAttrName());
        holder.editText.clearFocus();


        if (data.getAttrOptName() != null && !data.getAttrOptName().equals("")) {
            holder.layoutSpinner.setVisibility(View.VISIBLE);
            holder.layoutEdit.setVisibility(View.GONE);
            holder.layoutMultiSelect.setVisibility(View.GONE);
            String[] dataList = data.getAttrOptName().split(",");
            ArrayList<SpecificationInfo> specificationSpinnerList = new ArrayList<>();
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
                SpecificationInfo selectedItem = adapterSpinner.getItem(selectionCount + 1);
                boolean isAdded = false;
                assert selectedItem != null;
                holder.selectorText.setText(selectedItem.getAttrOptName());
                if (GlobalValues.savedAttributes.size() > 0) {
                    for (int i = 0; i < GlobalValues.savedAttributes.size(); i++) {
                        SpecificationInfo item = GlobalValues.savedAttributes.get(i);
                        if (item.getAttrID().equals(selectedItem.getAttrID())
                                && item.getAttrGroupName().equals(selectedItem.getAttrGroupName())) {
                            GlobalValues.savedAttributes.get(i).setAttrOptName(selectedItem.getAttrOptName());
                            GlobalValues.savedAttributes.get(i).setAttrID(selectedItem.getAttributeID());
                            GlobalValues.savedAttributes.get(i).setAttrGroupName(selectedItem.getAttrGroupName());
                            GlobalValues.savedAttributes.get(i).setPropertyTypeID(selectedItem.getPropertyTypeID());
                            isAdded = true;
                        }
                    }
                    if (!isAdded) {
                        GlobalValues.savedAttributes.add(selectedItem);
                    }
                } else {
                    GlobalValues.savedAttributes.add(selectedItem);
                }
            }

        } else {
            holder.layoutSpinner.setVisibility(View.GONE);
            holder.layoutMultiSelect.setVisibility(View.GONE);
            holder.layoutEdit.setVisibility(View.VISIBLE);
            if (data.getPropertyAttrSelectedOptionID() != null
                    && !data.getPropertyAttrSelectedOptionID().equals("")) {
                holder.editText.setText(data.getPropertyAttrSelectedOptionID());
                editTextSpecification = new SpecificationInfo();
                editTextSpecification.setAttrID(mAllAttributesData.get(position).getAttrID());
                editTextSpecification.setAttrGroupName(mAllAttributesData.get(position).getAttrGroupName());
                editTextSpecification.setPropertyTypeID(mAllAttributesData.get(position).getPropertyTypeID());
                editTextSpecification.setAttributeID(data.getPropertyAttrSelectedOptionID());
                editTextAttribute = new AllAttributesData();
                editTextAttribute = mAllAttributesData.get(position);
                editTextAttribute.setAttrOptName(data.getPropertyAttrSelectedOptionID());
                boolean isAdded = false;
                if (editTextSpecification.getAttrID() != null && !editTextSpecification.getAttrID().equals("")) {
                    for (int i = 0; i < GlobalValues.savedAttributes.size(); i++) {
                        SpecificationInfo item = GlobalValues.savedAttributes.get(i);
                        if (item.getAttrID().equals(editTextSpecification.getAttrID()) &&
                                item.getAttrGroupName().equals(editTextSpecification.getAttrGroupName())) {
                            GlobalValues.savedAttributes.get(i).setAttributeID(editTextSpecification.getAttributeID());
                            GlobalValues.savedAttributes.get(i).setAttrID(editTextSpecification.getAttrID());
                            isAdded = true;
                        }
                    }
                }
                if (!isAdded) {
                    GlobalValues.savedAttributes.add(editTextSpecification);
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
            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextSpecification = new SpecificationInfo();
                editTextSpecification.setAttrID(mAllAttributesData.get(position).getAttrID());
                editTextSpecification.setAttrGroupName(mAllAttributesData.get(position).getAttrGroupName());
                editTextSpecification.setPropertyTypeID(mAllAttributesData.get(position).getPropertyTypeID());
                editTextSpecification.setAttributeID(s.toString());
                editTextAttribute = new AllAttributesData();
                editTextAttribute = mAllAttributesData.get(position);
                editTextAttribute.setAttrOptName(s.toString());
                boolean isAdded = false;
                if (editTextSpecification.getAttrID() != null && !editTextSpecification.getAttrID().equals("")) {
                    for (int i = 0; i < GlobalValues.savedAttributes.size(); i++) {
                        SpecificationInfo item = GlobalValues.savedAttributes.get(i);
                        if (item.getAttrID().equals(editTextSpecification.getAttrID()) &&
                                item.getAttrGroupName().equals(editTextSpecification.getAttrGroupName())) {
                            GlobalValues.savedAttributes.get(i).setAttributeID(editTextSpecification.getAttributeID());
                            GlobalValues.savedAttributes.get(i).setAttrID(editTextSpecification.getAttrID());
                            isAdded = true;
                        }
                    }
                }
                if (!isAdded) {
                    GlobalValues.savedAttributes.add(editTextSpecification);
                }
            }
        });

        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int spinnerPosition, long id) {
                if (spinnerPosition != 0) {
                    SpecificationInfo selectedItem = (SpecificationInfo) holder.spinner.getAdapter().getItem(spinnerPosition);
                    boolean isAdded = false;
                    holder.selectorText.setText(selectedItem.getAttrOptName());
                    if (GlobalValues.savedAttributes.size() > 0) {
                        for (int i = 0; i < GlobalValues.savedAttributes.size(); i++) {
                            SpecificationInfo item = GlobalValues.savedAttributes.get(i);
                            if (item.getAttrID().equals(selectedItem.getAttrID())
                                    && item.getAttrGroupName().equals(selectedItem.getAttrGroupName())) {
                                GlobalValues.savedAttributes.get(i).setAttrOptName(selectedItem.getAttrOptName());
                                GlobalValues.savedAttributes.get(i).setAttrID(selectedItem.getAttributeID());
                                GlobalValues.savedAttributes.get(i).setAttrGroupName(selectedItem.getAttrGroupName());
                                GlobalValues.savedAttributes.get(i).setPropertyTypeID(selectedItem.getPropertyTypeID());
                                isAdded = true;
                            }
                        }
                        if (!isAdded) {
                            GlobalValues.savedAttributes.add(selectedItem);
                        }
                    } else {
                        GlobalValues.savedAttributes.add(selectedItem);
                    }
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

    public interface ItemClickAdapterListener {

        void spinnerClick(int position);

        void editClick(int position);
    }

}
