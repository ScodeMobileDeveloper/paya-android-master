package com.paya.paragon.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.paya.paragon.utilities.FragmentImageView;

import java.util.ArrayList;


public class AdapterSlider extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> list;
    public AdapterSlider(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        try {
            FragmentImageView fragmentImageView = (FragmentImageView) list.get(position);
            fragmentImageView.update();
            return fragmentImageView;
        }catch (ClassCastException e){
            return list.get(position);        }
    }

    @Override
    public int getCount() {
        return list.size();
    }
    public void addValue(ArrayList<Fragment> list, boolean clear){
        if(clear)
            this.list.clear();
        this.list=list;
        notifyDataSetChanged();
    }
}
