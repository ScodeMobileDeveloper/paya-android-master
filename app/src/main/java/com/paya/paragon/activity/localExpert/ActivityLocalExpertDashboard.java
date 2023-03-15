package com.paya.paragon.activity.localExpert;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.adapter.AdapterLocalExpertDashboard;
import com.paya.paragon.api.localExpertCategory.LocalExpertCategoryApi;
import com.paya.paragon.api.localExpertCategory.LocalExpertCategoryData;
import com.paya.paragon.api.localExpertCategory.LocalExpertCategoryResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"HardCodedStringLiteral", "SuspiciousNameCombination"})
public class ActivityLocalExpertDashboard extends AppCompatActivity {
    RecyclerView recyclerExpertCategories;
    ArrayList<LocalExpertCategoryData> categories;
    AdapterLocalExpertDashboard adapterLocalExperts;

    DialogProgress progress_bar;
    Boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_local_expert_dashboard);
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.rl_local_expert_dashboard_parent_layout));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        mTitle.setText(R.string.local_experts);
        mTitle.setTypeface(mTitle.getTypeface(), Typeface.BOLD);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / 3;

        declarations();

        if (!Utils.NoInternetConnection(ActivityLocalExpertDashboard.this)) {
            getLocalExpertCategories(dpWidth);
        } else {
            Toast.makeText(ActivityLocalExpertDashboard.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    private void declarations() {
        progress_bar = new DialogProgress(this);
        recyclerExpertCategories = (RecyclerView) findViewById(R.id.recycler_local_experts_dashboard);
        LinearLayoutManager mLayoutManager = new GridLayoutManager(ActivityLocalExpertDashboard.this, 3);
        recyclerExpertCategories.setLayoutManager(mLayoutManager);
        categories = new ArrayList<>();
    }

    public void getLocalExpertCategories(final float dpWidth) {
        progress_bar.show();
        ApiLinks.getClient().create(LocalExpertCategoryApi.class).post(
                SessionManager.getLanguageID(ActivityLocalExpertDashboard.this))
                .enqueue(new Callback<LocalExpertCategoryResponse>() {
                    @Override
                    public void onResponse(Call<LocalExpertCategoryResponse> call, Response<LocalExpertCategoryResponse> response) {
                        progress_bar.dismiss();
                        firstTime = false;
                        if (response.isSuccessful()) {
                            int code = response.body().getCode();
                            if (code == 100) {
                                if(response.body()!= null && response.body().getData()!= null){
                                    categories.addAll(response.body().getData());
                                    String imageUrl = response.body().getIconPath();
                                    adapterLocalExperts = new AdapterLocalExpertDashboard(ActivityLocalExpertDashboard.this, categories, dpWidth, imageUrl);
                                    recyclerExpertCategories.setAdapter(adapterLocalExperts);
                                    adapterLocalExperts.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(ActivityLocalExpertDashboard.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ActivityLocalExpertDashboard.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LocalExpertCategoryResponse> call, Throwable t) {
                        progress_bar.dismiss();
                    }
                });
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
