package haberapp.ahmetcemkaya.com.haberapp;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.ClipboardManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by cem.kaya on 03.03.2015.
 */
public class WebViewActivity extends ActionBarActivity {

    String url = null;
    String description = null;
    TextView descriptionText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Bundle extras;

        descriptionText = (TextView)findViewById(R.id.descriptionText);

        AdView mAdView = (AdView) findViewById(R.id.adViewWeb);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        if(savedInstanceState == null)
        {
            extras = getIntent().getExtras();

            if(extras !=null) {
                url = extras.getString("url");
                description = extras.getString("content");
            }
        }
        else
        {
            url = (String) savedInstanceState.getSerializable("url");
            description = (String) savedInstanceState.getSerializable("content");
        }
        WebView myWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        myWebView.setWebViewClient(new NewsWebViewClient());

        if(url != null)
        myWebView.loadUrl(url);
        else
        myWebView.loadUrl("http://www.haberapp.net");

        if(description != null)
            descriptionText.setText(description);
        else
            myWebView.loadUrl("http://www.haberapp.net");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.webview, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

if(id == R.id.action_copy)
{
    ClipboardManager ClipMan = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    ClipMan.setText(url);
     Toast info = Toast.makeText(this,"Haber linki kopyalandı",Toast.LENGTH_SHORT);
     info.show();
}
        return super.onOptionsItemSelected(item);

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
            view.setVisibility(View.VISIBLE);
            findViewById(R.id.adViewWeb).setVisibility(View.INVISIBLE);
            findViewById(R.id.progress_bar_webview).setVisibility(View.INVISIBLE);
            findViewById(R.id.descriptionText).setVisibility(View.INVISIBLE);
        }
    }
}
