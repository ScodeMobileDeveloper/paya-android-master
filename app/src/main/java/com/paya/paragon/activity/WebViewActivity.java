package com.paya.paragon.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paya.paragon.R;
import com.paya.paragon.api.cmsPages.CmsPagesApi;
import com.paya.paragon.api.cmsPages.CmsPagesResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.SessionManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class WebViewActivity extends AppCompatActivity {
private String contentDescription;

    DialogProgress progress;
    TextView mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);

        progress = new DialogProgress(WebViewActivity.this);
        Intent intent=getIntent();
        String comingFrom=intent.getStringExtra("comingFrom");
        loadWebView(comingFrom);

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
    public void loadWebView(String comingFrom) {
        progress.show();
        ApiLinks.getClient().create(CmsPagesApi.class).post("" + SessionManager.getLanguageID(this),comingFrom)
                .enqueue(new Callback<CmsPagesResponse>() {
                    @Override
                    public void onResponse(Call<CmsPagesResponse> call, Response<CmsPagesResponse> response) {
                        if (response.isSuccessful()) {
                            String status = response.body().getResponse();
                            String Message = response.body().getMessage();
                            if (status.equals("Success")) {

                                CmsPagesResponse.CmsPagesData.Cms cms=response.body().getCmsPagesData().getCms();
                                mTitle.setText(cms.getContentTitle());
                                    contentDescription=cms.getContentDescription();
                                    setWebViewData();

                                } else {
                                 Toast.makeText(WebViewActivity.this, Message,Toast.LENGTH_LONG).show();
                                    finish();
                                }

                            }
                        progress.dismiss();

                    }

                    @Override
                    public void onFailure(Call<CmsPagesResponse> call, Throwable t) {

                    }
                });




    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    /*public void loadWebView(String comingFrom) {
        final DialogPrograss dialogPrograss=new DialogPrograss(this);
        dialogPrograss.show();
        PairValues nameValuePairUserID = new PairValues("pageID", comingFrom);
        ArrayList<PairValues> list = new ArrayList<>();
        list.add(nameValuePairUserID);
        PostToServer syncToServerPost = new PostToServer( AppConstants.URL_POST_CMS+ SharedValues.getInt(WebViewActivity.this, DialogLanguage.KEY_LANGUAGE), list);
        syncToServerPost.start(new PostToServer.OnPostEntityListener() {

            @Override
            public void onCompleted(JSONObject jsonObject) {
                try {
                    dialogPrograss.dismiss();
                    if (jsonObject.getString("response").equals("Success")) {

                        jsonObject = jsonObject.getJSONObject("results");
                        ((TextView) findViewById(R.id.title)).setText(jsonObject.getString("contentTitle"));
                        contentDescription=jsonObject.getString("contentDescription");
                        setWebViewData();

                    } else {
                        com.ispg.jihazi.utility.Toast.makeText(WebViewActivity.this, jsonObject.getString("errorMsg"),Toast.LENGTH_LONG).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                dialogPrograss.dismiss();
                com.ispg.jihazi.utility.Toast.makeText(WebViewActivity.this, error,Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }*/
    private boolean loadingFinished = true;
    private boolean redirect = false;
    private void setWebViewData()
    {
        getWebView(R.id.webview_content).setBackgroundColor(0x00000000);
        try
        {
            getWebView(R.id.webview_content).setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        } catch (Exception e)
        {
            Log.d("neeraj", "Error : " + e.getMessage());
            e.printStackTrace();
        }
        String termAndPrivacyData = "<font color='black'>" + contentDescription + "</font>";
        getWebView(R.id.webview_content).getSettings().setLoadsImagesAutomatically(true);
        getWebView(R.id.webview_content).setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        getWebView(R.id.webview_content).setOverScrollMode(View.OVER_SCROLL_NEVER);
        getWebView(R.id.webview_content).getSettings().setJavaScriptEnabled(true);
        getWebView(R.id.webview_content).loadData(termAndPrivacyData, "text/html", "UTF-8");
        getWebView(R.id.webview_content).setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                if (!loadingFinished)
                {
                    redirect = true;
                }
                loadingFinished = false;

                // If any Url is there
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                loadingFinished = false;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                if (!redirect)
                {
                    loadingFinished = true;
                }

                if (loadingFinished && !redirect)
                {
                    // HIDE LOADING IT HAS FINISHED
                  /*  if (progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }*/

                    try
                    {
                        view.setBackgroundColor(0x00000000);
                        if (Build.VERSION.SDK_INT >= 11)
                            view.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                } else
                {
                    redirect = false;
                }
            }
        });
    }

    private WebView getWebView(int id)
    {
        return (WebView) findViewById(id);
    }
}
