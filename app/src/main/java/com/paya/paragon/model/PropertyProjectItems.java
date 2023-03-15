package com.paya.paragon.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import org.jetbrains.annotations.NotNull;

public class PropertyProjectItems implements ClusterItem {

    private LatLng latLong;
    private String title;
    private String msg;
    private String  id;

    public PropertyProjectItems(LatLng latLng, String title, String msg, String id) {
        this.latLong = latLng;
        this.title = title;
        this.msg = msg;
        this.id = id;
    }

    @NonNull
    @NotNull
    @Override
    public LatLng getPosition() {
        return latLong;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public String getSnippet() {
        return msg;
    }

    public String getId() {
        return id;
    }
}
