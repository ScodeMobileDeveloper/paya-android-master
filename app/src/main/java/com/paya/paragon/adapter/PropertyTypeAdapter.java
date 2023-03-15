package com.paya.paragon.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.index.PropertyTypeSub;
import com.paya.paragon.utilities.Utils;

import java.util.List;

public class PropertyTypeAdapter extends RecyclerView.Adapter<PropertyTypeAdapter.MyViewHolder> {

    private List<PropertyTypeSub> typeMainList;
    private final OnItemClickListener listener;
    private int row_index = -1;
    private String imagePath = "";
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView category_image;
        public LinearLayout cardView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            category_image = (ImageView) view.findViewById(R.id.category_image);
            cardView = (LinearLayout) view.findViewById(R.id.cardView);

        }


        public void bind(final PropertyTypeSub typeMain, final OnItemClickListener listener, final int position) {
            title.setText(typeMain.getPropertyTypeName());
            String image = imagePath + typeMain.getPropertyTypeIcon();
            Utils.loadUrlImage(category_image, image, R.drawable.no_image, false);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    row_index = position;
                    cardView.setSelected(true);
                    listener.onItemClick(typeMain);
                    notifyDataSetChanged();
                }
            });

            if (row_index == position) {
                cardView.setSelected(true);
            } else {
                cardView.setSelected(false);
            }
        }
    }


    public interface OnItemClickListener {
        void onItemClick(PropertyTypeSub item);
    }

    public PropertyTypeAdapter(Context context, String imagePath, List<PropertyTypeSub> typeMainList, OnItemClickListener listener) {
        this.typeMainList = typeMainList;
        this.listener = listener;
        this.imagePath = imagePath;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nod_property_type, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(typeMainList.get(position), listener, position);

        //  holder.category_image.setText(typeMain.getGenre());
    }

    @Override
    public int getItemCount() {
        return typeMainList.size();
    }
}
