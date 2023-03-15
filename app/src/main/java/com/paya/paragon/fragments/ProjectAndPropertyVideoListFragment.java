package com.paya.paragon.fragments;

import android.content.Intent;
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
import com.paya.paragon.activity.dashboard.ActivityYouTubeVideo;
import com.paya.paragon.adapter.PropertyProjectImageListAdapter;
import com.paya.paragon.databinding.FragmentPropertyAndProjectVideoListBinding;

import java.util.ArrayList;

public class ProjectAndPropertyVideoListFragment extends Fragment implements PropertyProjectImageListAdapter.PropertyProjectImageListAdapterCallback {

    private FragmentPropertyAndProjectVideoListBinding binding;

    public static ProjectAndPropertyVideoListFragment newInstance(ArrayList<String> videoList, ArrayList<String> videoIconList) {
        ProjectAndPropertyVideoListFragment fragment = new ProjectAndPropertyVideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("Video", videoList);
        bundle.putStringArrayList("videoIcons", videoIconList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_property_and_project_video_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setVideoListAdapter();
    }

    public void setVideoListAdapter() {
        PropertyProjectImageListAdapter adapter = new PropertyProjectImageListAdapter(this, getVideoIconListFromIntent());
        binding.rvImageList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvImageList.setNestedScrollingEnabled(false);
        binding.rvImageList.setAdapter(adapter);
    }

    public ArrayList<String> getVideoIconListFromIntent() {
        return getArguments() != null && !getArguments().getStringArrayList("videoIcons").isEmpty() ? getArguments().getStringArrayList("videoIcons") : new ArrayList<String>();
    }

    public ArrayList<String> getVideoListFromIntent() {
        return getArguments() != null && !getArguments().getStringArrayList("Video").isEmpty() ? getArguments().getStringArrayList("Video") : new ArrayList<String>();
    }

    @Override
    public void onImagePreviewClick(String imageUrl, int position) {
        Intent video = new Intent(getActivity(), ActivityYouTubeVideo.class);
        video.putExtra("id", getVideoListFromIntent().get(position));
        startActivity(video);
    }
}
