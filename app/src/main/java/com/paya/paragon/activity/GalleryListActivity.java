package com.paya.paragon.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.paya.paragon.R;
import com.paya.paragon.base.BaseAppCompactActivity;
import com.paya.paragon.databinding.ActivityPropertyProjectImageListBinding;
import com.paya.paragon.fragments.ProjectAndPropertyImageListFragment;
import com.paya.paragon.fragments.ProjectAndPropertyVideoListFragment;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

public class GalleryListActivity extends BaseAppCompactActivity {

    private ActivityPropertyProjectImageListBinding binding;
    private String langName;

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, binding.getRoot());
        if (!langName.equalsIgnoreCase(SessionManager.getLanguageName(this)))
            recreate();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_property_project_image_list);
        langName = SessionManager.getLanguageName(this);
        initiateToolbar();
        initiateViews();
    }

    private void initiateToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        binding.toolbarTitle.setTextSize(18);
        binding.toolbarTitle.setText(getIntent() != null && getIntent().hasExtra("title") ? getIntent().getStringExtra("title") : getString(R.string.app_name));
        binding.toolbarTitle.setTypeface(binding.toolbarTitle.getTypeface(), Typeface.BOLD);
    }

    private void initiateViews() {
        GalleryViewPagerAdapter galleryViewPagerAdapter = new GalleryViewPagerAdapter(getSupportFragmentManager());
        LinearLayout title1 = null;
        LinearLayout title2 = null;
        if (!getImageListFromIntent().isEmpty()) {
            galleryViewPagerAdapter.addFragment(ProjectAndPropertyImageListFragment.newInstance(getImageListFromIntent()), getTitleText() + getImageListFromIntent().size() + ")");
            View customTabView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_custom_gallery_list_tab, null, false);
            title1 = customTabView.findViewById(R.id.ll_custom_tab);
            TextView ctTitle = customTabView.findViewById(R.id.ct_title);
            TextView ctCount = customTabView.findViewById(R.id.ct_count);
            ctTitle.setText(getTitleText());
            ctCount.setText(String.valueOf(getImageListFromIntent().size()));
        }
        if (!getVideoListFromIntent().isEmpty()) {
            View customTabView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_custom_gallery_list_tab, null, false);
            title2 = customTabView.findViewById(R.id.ll_custom_tab);
            TextView ctTitle = customTabView.findViewById(R.id.ct_title);
            TextView ctCount = customTabView.findViewById(R.id.ct_count);
            galleryViewPagerAdapter.addFragment(ProjectAndPropertyVideoListFragment.newInstance(getVideoListFromIntent(), getVideoIconList()), "Video(" + getVideoListFromIntent().size() + ")");
            ctTitle.setText("Video");
            ctCount.setText(String.valueOf(getVideoListFromIntent().size()));
        }

        binding.vpGallery.setAdapter(galleryViewPagerAdapter);
        binding.vpGallery.setOffscreenPageLimit(2);
        binding.tbGalleryTitle.setupWithViewPager(binding.vpGallery);
        if (!getImageListFromIntent().isEmpty()) {
            binding.tbGalleryTitle.getTabAt(0).setCustomView(title1);
        }
        if (!getVideoListFromIntent().isEmpty()) {
            binding.tbGalleryTitle.getTabAt(!getImageListFromIntent().isEmpty() ? 1 : 0).setCustomView(title2);
        }

        binding.tbGalleryTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView selectedTabTitle = tab.getCustomView().findViewById(R.id.ct_title);
                TextView selectedTabCount = tab.getCustomView().findViewById(R.id.ct_count);
                selectedTabTitle.setTextColor(ContextCompat.getColor(GalleryListActivity.this, R.color.yellow));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView selectedTabTitle = tab.getCustomView().findViewById(R.id.ct_title);
                TextView selectedTabCount = tab.getCustomView().findViewById(R.id.ct_count);
                selectedTabTitle.setTextColor(ContextCompat.getColor(GalleryListActivity.this, R.color.white));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private String getTitleText() {
        return getIntent() != null && getIntent().hasExtra("floor") && getIntent().getStringExtra("floor") != null &&
                getIntent().getStringExtra("floor").equalsIgnoreCase("yes") ? getString(R.string.blueprint) : "Gallery";
    }

    private ArrayList<String> getVideoIconList() {
        return getIntent() != null && getIntent().getStringArrayListExtra("videoIcons") != null &&
                !getIntent().getStringArrayListExtra("videoIcons").isEmpty() ? getIntent().getStringArrayListExtra("videoIcons") : new ArrayList<String>();
    }

    public ArrayList<String> getVideoListFromIntent() {
        return getIntent() != null && getIntent().getStringArrayListExtra("videos") != null &&
                !getIntent().getStringArrayListExtra("videos").isEmpty() ? getIntent().getStringArrayListExtra("videos") : new ArrayList<String>();
    }

    public ArrayList<String> getImageListFromIntent() {
        return getIntent() != null && getIntent().getStringArrayListExtra("images") != null &&
                !getIntent().getStringArrayListExtra("images").isEmpty() ? getIntent().getStringArrayListExtra("images") : new ArrayList<String>();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static class GalleryViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList = new ArrayList<>();
        private List<String> fragmentTitleList = new ArrayList<>();

        public GalleryViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String fragmentTitle) {
            fragmentList.add(fragment);
            fragmentTitleList.add(fragmentTitle);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
