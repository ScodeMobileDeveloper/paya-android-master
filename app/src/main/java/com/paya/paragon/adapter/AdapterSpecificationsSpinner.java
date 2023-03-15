package com.paya.paragon.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.model.SpecificationInfo;

import java.util.List;



@SuppressWarnings({"RedundantCast", "HardCodedStringLiteral"})
@SuppressLint({"ViewHolder", "InflateParams"})
public class AdapterSpecificationsSpinner extends ArrayAdapter<SpecificationInfo> {

    private LayoutInflater inflater;

    public AdapterSpecificationsSpinner(@NonNull Activity context, @LayoutRes int resource,
                                        @IdRes int textViewResourceId, @NonNull List<SpecificationInfo> objects) {
        super(context, resource, textViewResourceId, objects);
        inflater = context.getLayoutInflater();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        SpecificationInfo info = getItem(position);
        View itemView = inflater.inflate(R.layout.item_price_listing_model, null, true);
        TextView txtTitle = (TextView) itemView.findViewById(R.id.text_price_list_item);
        assert info != null;
        txtTitle.setText(info.getAttrOptName());
        return itemView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SpecificationInfo info = getItem(position);
        View itemView = inflater.inflate(R.layout.item_price_listing_model, null, true);
        TextView txtTitle = (TextView) itemView.findViewById(R.id.text_price_list_item);
        assert info != null;
        txtTitle.setText(info.getAttrOptName());
        return itemView;
    }
}
