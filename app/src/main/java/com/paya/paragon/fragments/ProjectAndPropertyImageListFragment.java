package com.paya.paragon.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.paya.paragon.R;
import com.paya.paragon.adapter.PropertyProjectImageListAdapter;
import com.paya.paragon.databinding.FragmentPropertyAndProjectImageListBinding;
import com.paya.paragon.utilities.zoomdismiss.ImageViewer;
import com.paya.paragon.utilities.zoomdismiss.drawee.Fresco;

import java.util.ArrayList;

public class ProjectAndPropertyImageListFragment extends Fragment implements PropertyProjectImageListAdapter.PropertyProjectImageListAdapterCallback {


    private FragmentPropertyAndProjectImageListBinding binding;

    public static ProjectAndPropertyImageListFragment newInstance(ArrayList<String> imageList) {
        ProjectAndPropertyImageListFragment fragment = new ProjectAndPropertyImageListFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("Images", imageList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_property_and_project_image_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setImageListAdapter();
    }

    public void setImageListAdapter() {
        PropertyProjectImageListAdapter adapter = new PropertyProjectImageListAdapter(this, getImageListFromIntent());
        binding.rvImageList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvImageList.setNestedScrollingEnabled(false);
        binding.rvImageList.setAdapter(adapter);
    }

    public ArrayList<String> getImageListFromIntent() {
        return getArguments() != null && !getArguments().getStringArrayList("Images").isEmpty() ? getArguments().getStringArrayList("Images") : new ArrayList<String>();
    }

    @Override
    public void onImagePreviewClick(String imageUrl, int position) {
        Fresco.initialize(getActivity());
        ImageViewer imageViewer = new ImageViewer.Builder(getActivity(), getImageListFromIntent()).setStartPosition(position).build();
        imageViewer.show();
    }
}
