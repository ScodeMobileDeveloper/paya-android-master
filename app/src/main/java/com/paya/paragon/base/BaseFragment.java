package com.paya.paragon.base;

import android.app.Dialog;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.paya.paragon.R;
import com.paya.paragon.utilities.DialogProgress;

public class BaseFragment extends Fragment implements PageProgressLoader {

    protected Dialog dialog = null;
    protected DialogProgress dialogProgress = null;

    @Override
    public void showProgressBar(Context context) {
        try {
            if (dialog == null) {
                dialog = new Dialog(context, R.style.CustomProgressTheme);
                dialog.setContentView(R.layout.custom_progress);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismissProgressBar() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showCustomProgressBar(Context context) {
        //todo
    }

    @Override
    public void dismissCustomProgressBar() {
        //todo
    }

    @Override
    public void showAnimatedProgressBar(Context context) {
        if (dialogProgress == null) {
            dialogProgress = new DialogProgress(context);
        }
        dialogProgress.show();
    }

    @Override
    public void dismissAnimatedProgressBar() {
        try {
            if (dialogProgress != null && dialogProgress.isShowing()) {
                dialogProgress.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
