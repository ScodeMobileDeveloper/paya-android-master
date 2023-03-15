package com.paya.paragon.utilities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.paya.paragon.R;


public class FragmentImageView extends Fragment {
    private String url;
    private String previewPath, largePath, fileName;
    private int id = 0;
    ImageView imageView;

    public void setData(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public int getIds() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_view, null, false);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        Utils.changeLayoutOrientationDynamically(getActivity(), view.findViewById(R.id.ll_parent_view));
        Utils.loadUrlImage(imageView, url, R.drawable.no_image_placeholder, false);
        return view;
    }

    public void update() {
        if (imageView != null && url != null) {
            Utils.loadUrlImage(imageView, url, R.drawable.no_image_placeholder, false);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public String getPreviewPath() {
        return previewPath;
    }

    public void setPreviewPath(String previewPath) {
        this.previewPath = previewPath;
    }

    public String getLargePath() {
        return largePath;
    }

    public void setLargePath(String largePath) {
        this.largePath = largePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
