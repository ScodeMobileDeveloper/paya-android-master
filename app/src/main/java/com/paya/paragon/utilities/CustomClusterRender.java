package com.paya.paragon.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import org.jetbrains.annotations.NotNull;

public class CustomClusterRender extends DefaultClusterRenderer {
    private GoogleMap map;
    private ClusterManager clusterManager;
    private int id;
    private Context context;

    public CustomClusterRender(Context context, GoogleMap map, ClusterManager clusterManager, int iconId) {
        super(context, map, clusterManager);
        this.map = map;
        this.clusterManager = clusterManager;
        this.context = context;
        this.id = iconId;
    }

    @Override
    protected void onBeforeClusterItemRendered(@NonNull @NotNull ClusterItem item, @NonNull @NotNull MarkerOptions markerOptions) {
        BitmapDrawable bitmapdraw = (BitmapDrawable) context.getResources().getDrawable(id);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 35, 35, false);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
    }

    @Override
    protected void onClusterItemRendered(@NonNull ClusterItem clusterItem, @NonNull Marker marker) {
        marker.setPosition(clusterItem.getPosition());
        marker.showInfoWindow();
    }
}
