package com.paya.paragon.utilities;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public abstract class ControllerPopupWindow {
    private Activity activity;
    private @LayoutRes
    int layoutRes;
    private View parentView;
    private LayoutInflater inflater;

    //private int animationStyle= R.style.Animation;

    private View convertView;

    private PopupWindow popupWindow;

    public ControllerPopupWindow(Activity activity, int layoutRes) {
        this.activity = activity;
        this.parentView = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        this.layoutRes = layoutRes;
        init();
    }

    /*public void setAnimationStyle(@StyleRes int animationStyle) {
        this.animationStyle = animationStyle;
    }*/

    private void init() {
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layoutRes, null, false);
        popupWindow = new PopupWindow(convertView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ControllerPopupWindow.this.onDismiss();
            }
        });
    }

    public abstract void onCreate(@Nullable View view);

    public abstract boolean onShow();

    public abstract boolean onDismiss();

    public void show() {
        onCreate(convertView);
        if (onShow()) {
            popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
            popupWindow.setFocusable(true);
            popupWindow.update();
        }
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    @Nullable
   /* //public User getCurrentUser(){
        UserManager userManager=new UserManager(activity);
        return userManager.getCurrentUser();
    }*/
    public View findViewById(@IdRes int id) {
        return convertView.findViewById(id);
    }

    public void finish() {
        popupWindow.dismiss();
    }

    public String getString(@StringRes int string) {
        return activity.getString(string);
    }
}
