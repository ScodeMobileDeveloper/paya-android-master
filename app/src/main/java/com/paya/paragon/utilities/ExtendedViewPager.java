package com.paya.paragon.utilities;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class ExtendedViewPager extends ViewPager {

    private OnItemClickListener mOnItemClickListener;

    public ExtendedViewPager(Context context) {
        super(context);
        setup();
    }

    public ExtendedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        final GestureDetector tapGestureDetector = new    GestureDetector(getContext(), new TapGestureListener());

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tapGestureDetector.onTouchEvent(event);
                return false;
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private class TapGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if(mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getCurrentItem());
            }
            return true;
        }
    }
    public void next(){
        PagerAdapter pagerAdapter=getAdapter();
        int index=getCurrentItem()+1;
        int adapterSize=pagerAdapter.getCount();
        if(index<adapterSize){
            setCurrentItem(index,true);
        }else{
            setCurrentItem(index,true);
        }
    }
    public void previous(){
        PagerAdapter pagerAdapter=getAdapter();
        int index=getCurrentItem()-1;
        int adapterSize=0;
        if(index>adapterSize){
            setCurrentItem(index,true);
        }else{
            setCurrentItem(0,true);
        }
    }
}
