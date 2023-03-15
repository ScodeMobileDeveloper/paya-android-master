package com.paya.paragon.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.paya.paragon.R;
import com.paya.paragon.utilities.DialogProgress;

public class PaymentActivity extends AppCompatActivity {

    private WebView webView;
    //private ImageView favIcon;
    private TextView mTitle;
    private String postUrl, post;
    private String sUrl, lUrl;
    DialogProgress progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        webView = findViewById(R.id.webView);

        postUrl = getIntent().getStringExtra("payUrl");
        post = getIntent().getStringExtra("post");
        Log.e("post values", "" + post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);

        mTitle.setTypeface(mTitle.getTypeface(), Typeface.BOLD);
        sUrl = getIntent().getStringExtra("sUrl");
        lUrl = getIntent().getStringExtra("lUrl");
        progress = new DialogProgress(PaymentActivity.this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        // webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        /*findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });*/
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap icon) {
                progress.show();
                // favIcon.setImageBitmap(icon);
                Log.d("WebViewTest", "STA : " + url);
                Log.d("WebViewTest", "S_L : " + lUrl);
                Log.d("WebViewTest", "S_S : " + sUrl);

                if (url.equalsIgnoreCase(sUrl)) {
                    progress.dismiss();
                    setResult(Activity.RESULT_OK);
                    finish();
                } else if (url.equalsIgnoreCase(lUrl)) {
                    progress.dismiss();
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                }
                super.onPageStarted(view, url, icon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (progress.isShowing())
                    progress.dismiss();
                if (!view.getTitle().contains(".co"))
                    mTitle.setText(view.getTitle());

                Log.d("WebViewTest", "FIN : " + url);
                Log.d("WebViewTest", "F_L : " + lUrl);
                Log.d("WebViewTest", "F_S : " + sUrl);
                // favIcon.setImageBitmap(view.getFavicon());
            }
        });
        //webView.loadUrl(postUrl);
        webView.postUrl(postUrl, post.getBytes());
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
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
