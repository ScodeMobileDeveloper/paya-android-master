package com.paya.paragon.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.paya.paragon.BuildConfig;
import com.paya.paragon.R;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

public class CalculatorWebView extends AppCompatActivity {
    private boolean loadingFinished = true;
    private boolean redirect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_web_view);
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.animation_layout));
        findViewById(R.id.topBar).bringToFront();
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        setWebViewData();
    }

    private void setWebViewData() {
        getWebView(R.id.webview_content).setBackgroundColor(0x00000000);
        try {
            getWebView(R.id.webview_content).setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        } catch (Exception e) {
            Log.d("neeraj", "Error : " + e.getMessage());
            e.printStackTrace();
        }
        String termAndPrivacyData = "<font color='black'>" + BuildConfig.APP_BASE_URL + "calculator/appView/" + "</font>";
        getWebView(R.id.webview_content).getSettings().setLoadsImagesAutomatically(true);
        getWebView(R.id.webview_content).setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        getWebView(R.id.webview_content).setOverScrollMode(View.OVER_SCROLL_NEVER);
        getWebView(R.id.webview_content).getSettings().setJavaScriptEnabled(true);
        String myUrl = ApiLinks.CALCULATOR_URL + "calculators.html";
        getWebView(R.id.webview_content).loadUrl(myUrl);

        getWebView(R.id.webview_content).setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!loadingFinished) {
                    redirect = true;
                }
                loadingFinished = false;

                // If any Url is there
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loadingFinished = false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!redirect) {
                    loadingFinished = true;
                }

                if (loadingFinished && !redirect) {
                    // HIDE LOADING IT HAS FINISHED
                    try {
                        view.setBackgroundColor(0x00000000);
                        if (Build.VERSION.SDK_INT >= 11)
                            view.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    redirect = false;
                }
            }
        });
    }

    private WebView getWebView(int id) {
        return (WebView) findViewById(id);
    }
}
