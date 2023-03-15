package com.paya.paragon.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
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
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.api.postProperty.attributeListing.AllAttributesData;
import com.paya.paragon.classes.CustomSpinner;
import com.paya.paragon.model.SpecificationInfo;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings({"HardCodedStringLiteral", "ConstantConditions"})
public class AdapterSpecifications extends RecyclerView.Adapter<AdapterSpecifications.SpecificationViewHolder> {

    private List<AllAttributesData> mAllAttributesData;
    private Context mContext;
    private String action;
    private int count = 0;
    private AdapterSpecificationsCallback callback;

    public AdapterSpecifications(Context context, List<AllAttributesData> allAttributesList, String action, AdapterSpecificationsCallback callback) {
        this.mAllAttributesData = allAttributesList;
        this.mContext = context;
        this.action = action;
        this.callback = callback;
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

        holder.trTitle.setVisibility(View.GONE);
        final AllAttributesData data = mAllAttributesData.get(position);
        holder.tvSpinnerTitle.setText(data.getAttrID().equals(Utils.ATTR_AREA_ID) ? data.getAttrName() + " " + mContext.getString(R.string.meter_square) : data.getAttrName());
        boolean isMandatoryFields = Utils.getAttributeDefaultList().contains(data.getAttrID());
        holder.tvStar.setVisibility(isMandatoryFields ? View.VISIBLE : View.GONE);
        holder.tvSpinnerStar.setVisibility(isMandatoryFields ? View.VISIBLE : View.GONE);

        callback.isPhoneNoMandatory(data.getAttrID().equals(Utils.ATTR_PHONE_NO_ID));
        callback.isAreaMandatory(data.getAttrID().equals(Utils.ATTR_AREA_ID));
        callback.isBathRoomMandatory(data.getAttrID().equals(Utils.ATTR_BATH_ROOM_ID));
        callback.isKitchenMandatory(data.getAttrID().equals(Utils.ATTR_KITCHEN_ID));
        callback.isLivingRoomMandatory(data.getAttrID().equals(Utils.ATTR_LIVING_ROOM_ID));
        callback.isBedRoomMandatory(data.getAttrID().equals(Utils.ATTR_BED_ROOM_ID));
        callback.isFloorNumberMandatory(data.getAttrID().equals(Utils.ATTR_FLOOR_NUMBER_ID));

        holder.editText.clearFocus();

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
            for (int i = 0; i < adapterSpinner.getCount(); i++) {
                if (adapterSpinner.getItem(i).getAttributeID().equals(Utils.getPreviousEditedValue(data.getAttrID()))) {
                    itemPosition = i;
                }
            }

            if (itemPosition != -1) {
                holder.spinner.setSelection(itemPosition);
            }
        } else {
            holder.layoutSpinner.setVisibility(View.GONE);
            holder.layoutMultiSelect.setVisibility(View.GONE);
            holder.layoutEdit.setVisibility(View.VISIBLE);
            if (Utils.ATTR_PHONE_NO_ID.equalsIgnoreCase(data.getAttrID())) {
                holder.editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                if (Utils.getAttrAmenMap() != null && !Utils.getAttrAmenMap().isEmpty() && Utils.getAttrAmenMap().containsKey(Utils.ATTR_PHONE_NO_ID)) {
                    holder.editText.setClickable(false);
                    holder.editText.setFocusable(false);
                }
            }
            if (Utils.ATTR_AREA_ID.equalsIgnoreCase(data.getAttrID())) {
                holder.editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            }
            if(Utils.ATTR_FLOOR_NUMBER_ID.equalsIgnoreCase(data.getAttrID())){
                holder.editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            }
            holder.editText.setText(Utils.getPreviousEditedValue(data.getAttrID()));
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
                Utils.setPreviousEditedValue(data.getAttrID(), charSequence.toString(), true, false);
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
                    Utils.setPreviousEditedValue(data.getAttrID(), selectedItem.getAttributeID(), true, false);
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
        TextView tvSpinnerTitle;
        TextView tvTitle;
        TextView tvStar;
        TextView tvSpinnerStar;
        TableRow trSpinnerTitle;
        TableRow trTitle;
        TextView selectorText;
        CustomSpinner spinner;
        EditText editText;
        RecyclerView recyclerMultiSelect;

        SpecificationViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.title_specification_item_spec_model);
            this.trTitle = itemView.findViewById(R.id.tr_title_specification_item_spec_model);
            this.tvSpinnerTitle = itemView.findViewById(R.id.title_specification_spinner_type);
            this.trSpinnerTitle = itemView.findViewById(R.id.tr_title_specification_spinner_type);

            this.tvStar = itemView.findViewById(R.id.tv_star);
            this.tvSpinnerStar = itemView.findViewById(R.id.tv_spinner_star);

            this.layoutSpinner = itemView.findViewById(R.id.layout_specification_spinner);
            this.layoutEdit = itemView.findViewById(R.id.layout_specification_edit_text);
            this.layoutMultiSelect = itemView.findViewById(R.id.layout_specification_multi_select);
            this.selectorText = itemView.findViewById(R.id.selector_text_specification_spinner_type);
            this.spinner = itemView.findViewById(R.id.spinner_specification_spinner_type);
            this.layoutSpinner = itemView.findViewById(R.id.layout_specification_spinner);
            this.editText = itemView.findViewById(R.id.edit_text_specification);
            this.recyclerMultiSelect = itemView.findViewById(R.id.recycler_multi_select_specification_model);
        }
    }


    public interface AdapterSpecificationsCallback {
        void isPhoneNoMandatory(boolean isMandatory);

        void isAreaMandatory(boolean isMandatory);

        void isBathRoomMandatory(boolean isMandatory);

        void isKitchenMandatory(boolean isMandatory);

        void isLivingRoomMandatory(boolean isMandatory);

        void isBedRoomMandatory(boolean isMandatory);

        void isFloorNumberMandatory(boolean isMandatory);
    }
}
