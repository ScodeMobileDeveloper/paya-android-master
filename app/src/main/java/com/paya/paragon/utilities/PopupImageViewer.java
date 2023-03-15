package com.paya.paragon.utilities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.paya.paragon.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Anu Martin on 4/18/2018 - 11:02 AM
 */
public class PopupImageViewer implements View.OnTouchListener {
    private Context context;
    private View parent, convertView;
    private PopupWindow popupWindow;
    private LayoutInflater inflater;
    private ImageView imageView;
    private ImageButton imageButton;
    private ProgressBar progressBar;
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    private PreviewCallBack callback;

    public PopupImageViewer(Context context, View parent, String image, PreviewCallBack callback) {
        this.context = context;
        this.callback = callback;
        this.parent = parent;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.popup_image_viewer, null, false);
        popupWindow = new PopupWindow(convertView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        imageView = convertView.findViewById(R.id.imageView);
        imageButton = convertView.findViewById(R.id.buttonClose);
        progressBar = convertView.findViewById(R.id.progressBar);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        downloadImage(image);
    }

    public void show() {
        popupWindow.setAnimationStyle(R.style.popupAnimation);
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.update();
    }

    private void dismiss() {
        callback.changeOrientation();
        popupWindow.dismiss();
    }

    private Bitmap bitmap;

    private void downloadImage(String url) {
        progressBar.setVisibility(View.VISIBLE);
        WorkThread workThread = new WorkThread(url, new OnCompleteListener() {
            @Override
            public void onDone(boolean status, Bitmap content) {
                progressBar.setVisibility(View.GONE);
                imageView.setImageBitmap(content);
                repostionImage();
                if (status)
                    imageView.setOnTouchListener(PopupImageViewer.this);
            }
        });
        workThread.execute();
    }

    private String TAG = "ZoomTestLog123";

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub

        ImageView view = (ImageView) v;
        dumpEvent(event);


        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                Log.d(TAG, "mode=DRAG");
                mode = DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM");
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                Log.d(TAG, "mode=NONE");
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY()
                            - start.y);
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }

        view.setImageMatrix(matrix);
        return true;
    }

    private void dumpEvent(MotionEvent event) {
        String names[] = {"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?"};
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(
                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }
        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }
        sb.append("]");
        Log.d(TAG, sb.toString());
    }


    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    class WorkThread extends AsyncTask<Void, Bitmap, Bitmap> {
        private String url;
        private boolean status = false;
        private OnCompleteListener onCompleteListener;

        public WorkThread(String url, OnCompleteListener onCompleteListener) {
            this.url = url;
            this.onCompleteListener = onCompleteListener;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            bitmap = getBitmapFromURL(url);
            status = bitmap != null;
            publishProgress(bitmap);
            return null;
        }

        @Override
        protected void onProgressUpdate(Bitmap... values) {
            super.onProgressUpdate(values);
            if (!status)
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.no_image);
            onCompleteListener.onDone(status, bitmap);
        }
    }

    private interface OnCompleteListener {
        void onDone(boolean status, Bitmap content);
    }

    private Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            return null;
        }
    }

    private void repostionImage() {
        RectF drawableRect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF viewRect = new RectF(0, 0, imageView.getWidth(), imageView.getHeight());
        //Matrix matrix=new Matrix();
        matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);
        imageView.setImageMatrix(matrix);
    }

    public interface PreviewCallBack{
        void changeOrientation();
    }
}
