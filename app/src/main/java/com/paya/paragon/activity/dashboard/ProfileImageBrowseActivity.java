package com.paya.paragon.activity.dashboard;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.paya.paragon.R;
import com.paya.paragon.activity.login.ActivitySignUp;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileImageBrowseActivity extends AppCompatActivity {
    float dpWidth;
    Cursor imagecursor;
    private int count;
    private List<String> imagePaths;
    int newCount = 0;
    private ImageAdapter imageAdapter;
    public String whichScreen;

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.col_profile_image_browse_parent_layout));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image_browse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        whichScreen = getIntent().getStringExtra("whichScreen");
        if(whichScreen!=null&&!whichScreen.isEmpty()){
            if(whichScreen.equalsIgnoreCase("signup")){
                mTitle.setText("Select a company logo");

            }else {
                mTitle.setText(R.string.select_profile_image);

            }

        }else {

        }

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        dpWidth = displayMetrics.widthPixels / 3;
        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media._ID;
        imagecursor = managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy);
        this.count = imagecursor.getCount();
        imagePaths = new ArrayList<>();
        for (int i = 0; i < this.count; i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            imagePaths.add(imagecursor.getString(dataColumnIndex));
            newCount++;

        }
        Collections.reverse(imagePaths);
        GridView imagegrid = (GridView) findViewById(R.id.PhoneImageGrid);
        imageAdapter = new ImageAdapter();
        imagegrid.setAdapter(imageAdapter);


    }

    public class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public ImageAdapter() {
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return newCount;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(
                        R.layout.galleryitem, null);
                holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);
                holder.checkbox = (CheckBox) convertView.findViewById(R.id.itemCheckBox);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.imageview.getLayoutParams().height = (int) dpWidth;
            holder.checkbox.setId(position);
            holder.checkbox.setVisibility(View.GONE);
            holder.imageview.setId(position);
            Utils.loadUrlImage(holder.imageview, imagePaths.get(position), R.drawable.no_image, false);
            holder.id = position;
            holder.imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(whichScreen.equalsIgnoreCase("signup")){
                        ActivitySignUp.selectedImageUrl = imagePaths.get(position);

                    }else {
                        ProfileActivity.selectedImageUrl = imagePaths.get(position);

                    }
                    finish();
                }
            });
            return convertView;
        }
    }

    class ViewHolder {
        ImageView imageview;
        CheckBox checkbox;
        int id;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
