package com.paya.paragon.classes;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class ReadMoreTextView extends androidx.appcompat.widget.AppCompatTextView {
    public ReadMoreTextView(Context context) {
        super(context);
    }

    public ReadMoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReadMoreTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public interface OnLayoutListener {
        void onLayouted(TextView view);
    }

    private OnLayoutListener mOnLayoutListener;

    public void setOnLayoutListener(OnLayoutListener listener) {
        mOnLayoutListener = listener;
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (mOnLayoutListener != null) {
            mOnLayoutListener.onLayouted(this);
        }
    }
}
