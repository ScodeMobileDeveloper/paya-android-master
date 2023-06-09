

package com.paya.paragon.utilities.zoomdismiss;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AlertDialog;


import com.paya.paragon.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageViewer implements OnDismissListener, DialogInterface.OnKeyListener {

    private static final String TAG = ImageViewer.class.getSimpleName();

    private Builder builder;
    private AlertDialog dialog;
    private ImageViewerView viewer;

    protected ImageViewer(Builder builder) {
        this.builder = builder;
        createDialog();
    }

    /**
     * Displays the built viewer if passed urls list isn't empty
     */
    public void show() {
        if (!builder.urls.isEmpty()) {
            dialog.show();
        } else {
            Log.e(TAG, "Urls list cannot be empty! Viewer ignored.");
        }
    }

    private void createDialog() {
        viewer = new ImageViewerView(builder.context);
        viewer.setUrls(builder.urls, builder.startPosition);
        viewer.setOnDismissListener(this);
        viewer.setBackgroundColor(builder.backgroundColor);


        dialog = new AlertDialog.Builder(builder.context,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
                .setView(viewer)
                .setOnKeyListener(this)
                .create();
        ImageView close_image = viewer.findViewById(R.id.close_image);
        close_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("onClick:__", "####*******________" );
                dialog.cancel();
            }
        });
    }

    /**
     * Fires when swipe to dismiss was initiated
     */
    @Override
    public void onDismiss() {
        Log.e("onClick:__", "####*******________" );
        dialog.cancel();
    }

    /**
     * Resets image on {@literal KeyEvent.KEYCODE_BACK} to normal scale if needed, otherwise - hide the viewer.
     */
    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_UP &&
                !event.isCanceled()) {
            if (viewer.isScaled()) {
                viewer.resetScale();
            } else {
                Log.e("onClick:__", "####*******________" );
                dialog.cancel();
            }
        }
        return true;
    }

    /**
     * Builder class for {@link ImageViewer}
     */
    public static class Builder {

        private Context context;
        private List<String> urls;
        private
        @ColorInt
        int backgroundColor = 0xF2FFFFFF;
        private int startPosition;

        /**
         * Constructor using a context and images urls array for this builder and the {@link ImageViewer} it creates.
         */
        public Builder(Context context, String[] urls) {
            this(context, new ArrayList<>(Arrays.asList(urls)));
        }

        /**
         * Constructor using a context and images urls list for this builder and the {@link ImageViewer} it creates.
         */
        public Builder(Context context, List<String> urls) {
            this.context = context;
            this.urls = urls;
        }

        /**
         * Set background color resource for viewer
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        @SuppressWarnings("deprecation")
        public Builder setBackgroundColorRes(@ColorRes int color) {
            return this.setBackgroundColor(context.getResources().getColor(color));
        }

        /**
         * Set background color int for viewer
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setBackgroundColor(@ColorInt int color) {
            this.backgroundColor = color;
            return this;
        }

        /**
         * Set background color int for viewer
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setStartPosition(int position) {
            this.startPosition = position;
            return this;
        }

        /**
         * Creates a {@link ImageViewer} with the arguments supplied to this builder. It does not
         * {@link ImageViewer#show()} the dialog. This allows the user to do any extra processing
         * before displaying the dialog. Use {@link #show()} if you don't have any other processing
         * to do and want this to be created and displayed.
         */
        public ImageViewer build() {
            return new ImageViewer(this);
        }

        /**
         * Creates a {@link ImageViewer} with the arguments supplied to this builder and
         * {@link ImageViewer#show()}'s the dialog.
         */
        public ImageViewer show() {
            ImageViewer dialog = build();
            dialog.show();
            return dialog;
        }
    }
}
