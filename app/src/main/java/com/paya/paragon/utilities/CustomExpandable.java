package com.paya.paragon.utilities;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ExpandableListView;


public class CustomExpandable extends ExpandableListView {
    private boolean expanded=true;
    public CustomExpandable(Context context) {
        super(context);
    }

    public CustomExpandable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomExpandable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomExpandable(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private boolean block=false;
    public void blockScroll(boolean block){
        this.block=block;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        if(ev.getAction()== MotionEvent.ACTION_MOVE&&block)
            return true;
        return super.dispatchTouchEvent(ev);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        // HACK! TAKE THAT ANDROID!
        if (isExpanded())
        {
            // Calculate entire height by providing a very large height hint.
            // But do not use the highest 2 bits of this integer; those are
            // reserved for the MeasureSpec mode.
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);

            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();
        }
        else
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setExpanded(boolean expanded)
    {
        this.expanded = expanded;
    }
    public boolean isExpanded()
    {
        return expanded;
    }
}
