package com.paya.paragon.activity.dashboard;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.paya.paragon.R;
import com.lyft.android.scissors.CropView;
import com.paya.paragon.activity.login.ActivitySignUp;
import com.paya.paragon.utilities.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class ProfileImageCropActivity extends AppCompatActivity {
    CropView crop;
    Button btn_cancel, btn_save;
    Bitmap croppedBitmap, finalbitmap;
    String final_img_path;
    String whichScreen;

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.col_profile_image_crop_parent_layout));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image_crop);
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.col_profile_image_crop_parent_layout));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        crop = findViewById(R.id.crop);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_save = findViewById(R.id.btn_save);
        whichScreen = getIntent().getStringExtra("whichScreen");
        try {
            if(whichScreen.equalsIgnoreCase("signup")){
                crop.setImageBitmap(ActivitySignUp.bt);

            }else {
                crop.setImageBitmap(ProfileActivity.bt);

            }
        } catch (Exception e) {
            Log.e("Error", e + "");
            Toast.makeText(ProfileImageCropActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
        }
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                croppedBitmap = crop.crop();
                finalbitmap = getResizedBitmap(croppedBitmap, 350, 350);
                Calendar calender = Calendar.getInstance();
                Long lo = calender.getTimeInMillis();
                savePhoto(finalbitmap, lo + ".png");
                finish();
            }
        });

    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public void savePhoto(Bitmap imaginative, String filenameone) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, filenameone);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            imaginative.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        final_img_path = directory.getAbsolutePath() + "/" + filenameone;
        if(whichScreen.equalsIgnoreCase("signup")){
            ActivitySignUp.selectedFinalImageUrl = final_img_path;

        }else {
            ProfileActivity.selectedFinalImageUrl = final_img_path;

        }

    }

}
