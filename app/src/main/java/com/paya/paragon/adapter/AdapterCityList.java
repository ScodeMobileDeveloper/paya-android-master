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
import com.paya.paragon.api.postProperty.PostPropertyLocalityList.city.CityListItem;

import java.util.List;


public class AdapterCityList extends ArrayAdapter<CityListItem> {
    private LayoutInflater inflater;

    public AdapterCityList(@NonNull Activity context, @LayoutRes int resource,
                           @IdRes int textViewResourceId, @NonNull List<CityListItem> objects) {
        super(context, resource, textViewResourceId, objects);
        inflater = context.getLayoutInflater();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        CityListItem info = getItem(position);
        View itemView = inflater.inflate(R.layout.item_price_listing_model, null, true);
        TextView txtTitle = (TextView) itemView.findViewById(R.id.text_price_list_item);
        assert info != null;
        txtTitle.setText(info.getCityName());
        return itemView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CityListItem info = getItem(position);
        View itemView = inflater.inflate(R.layout.item_price_listing_model, null, true);
        TextView txtTitle = (TextView) itemView.findViewById(R.id.text_price_list_item);
        assert info != null;
        txtTitle.setText(info.getCityName());
        return itemView;
    }


}

























