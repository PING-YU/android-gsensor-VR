package com.example.imac_gsensor;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by hamburger on 2016/3/19.
 */
public class Fragment4 extends Fragment {

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    private WebView webView1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout, container, false);

        webView1 = (WebView) v.findViewById(R.id.web1);
        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.setWebViewClient(mWebViewClient);
        webView1.loadUrl("http://root:r00tme@172.16.2.26/axis-cgi/mjpg/video.cgi");


        webView1.getSettings().setLoadWithOverviewMode(true);
        webView1.getSettings().setUseWideViewPort(true);

        return v;
    }

    WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
//            webView1.scrollTo(view.getContentHeight(), 300);
            return true;
        }
    };
}