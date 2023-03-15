package com.paya.paragon.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.paya.paragon.R;
import com.paya.paragon.api.postProperty.PostPropertyLocalityList.state.StateListItem;

import java.util.List;

public class AdapterStateList extends ArrayAdapter<StateListItem> {
    private LayoutInflater inflater;

    public AdapterStateList(@NonNull Activity context, @LayoutRes int resource,
                                        @IdRes int textViewResourceId, @NonNull List<StateListItem> objects) {
        super(context, resource, textViewResourceId, objects);
        inflater = context.getLayoutInflater();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        StateListItem info = getItem(position);
        View itemView = inflater.inflate(R.layout.item_price_listing_model, null, true);
        TextView txtTitle = (TextView) itemView.findViewById(R.id.text_price_list_item);
        assert info != null;
        txtTitle.setText(info.getStateName());
        return itemView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        StateListItem info = getItem(position);
        View itemView = inflater.inflate(R.layout.item_price_listing_model, null, true);
        TextView txtTitle = (TextView) itemView.findViewById(R.id.text_price_list_item);
        assert info != null;
        txtTitle.setText(info.getStateName());
        return itemView;
    }


}
