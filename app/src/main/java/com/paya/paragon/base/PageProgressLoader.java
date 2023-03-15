package com.paya.paragon.base;

import android.content.Context;

public interface PageProgressLoader {

    void showProgressBar(Context context);
    void dismissProgressBar();
    void showCustomProgressBar(Context context);
    void dismissCustomProgressBar();
    void showAnimatedProgressBar(Context context);
    void dismissAnimatedProgressBar();

}
