package haberapp.ahmetcemkaya.com.haberapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by cem.kaya on 03.03.2015.
 */
public class WebViewActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Bundle extras;
        String url = null;
        if(savedInstanceState == null)
        {
            extras = getIntent().getExtras();

            if(extras !=null) {
                url = extras.getString("url");
            }
        }
        else
        {
            url = (String) savedInstanceState.getSerializable("url");

        }
        WebView myWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        myWebView.setWebViewClient(new NewsWebViewClient());
        //myWebView.setWebViewClient(new NewsWebViewClient());
        if(url != null)
        myWebView.loadUrl(url);
        else
        myWebView.loadUrl("http://www.haberapp.net");

    }




    private class NewsWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView v, String url)
        {
            v.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // do your stuff here

        }
    }
}
