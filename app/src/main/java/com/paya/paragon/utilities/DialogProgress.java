package com.paya.paragon.utilities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.paya.paragon.R;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import java.io.IOException;



public class DialogProgress extends Dialog {
    private String text="";
    private GifImageView gifImageView;
    public static TextView tvProgress;
    public static Context context;

    public DialogProgress(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public DialogProgress(@NonNull Context context, String text) {
        super(context, R.style.full_screen_dialog);
        this.text = text;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.prograss_dialog);
        Window window = getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        gifImageView = findViewById(R.id.gifLoader);
        tvProgress = findViewById(R.id.tv_progress);

        try {
            GifDrawable gifFromAssets = new GifDrawable(context.getAssets(), "loader.gif");
            gifImageView.setImageDrawable(gifFromAssets);
        } catch (IOException e) {
            LinearLayout linearLayout = findViewById(R.id.holder);
            linearLayout.removeAllViews();
            ProgressBar progressBar = new ProgressBar(context);
            linearLayout.addView(progressBar);
            e.printStackTrace();
        }

        setCancelable(false);
    }
    public static  void setProgress(String text){
        tvProgress.setVisibility(View.VISIBLE);
        if(text.equals("100")){
            tvProgress.setText(context.getString(R.string.upload)+"...");

        }else {
            tvProgress.setText(text+"%");

        }

    }
}
