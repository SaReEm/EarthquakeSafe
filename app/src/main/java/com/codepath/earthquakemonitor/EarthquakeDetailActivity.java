package com.codepath.earthquakemonitor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.codepath.earthquakemonitor.models.Earthquake;

import org.parceler.Parcels;

/**
 * Created by emilie on 10/14/17.
 */

public class EarthquakeDetailActivity extends AppCompatActivity {
    Earthquake earthquake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        earthquake = (Earthquake) Parcels.unwrap(getIntent().getParcelableExtra("earthquake"));

        WebView webView = (WebView) findViewById(R.id.wbEarthquake);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(earthquake.getUrl());
                return true;
            }
        });
        webView.loadUrl(earthquake.getUrl());
    }
}
