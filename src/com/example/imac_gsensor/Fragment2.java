package com.example.imac_gsensor;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by hamburger on 2016/3/19.
 */
public class Fragment2 extends Fragment {

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    private WebView webView1;

    private int webviewContentWidth;

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
        webView1.setInitialScale(198);

//        webView1.addJavascriptInterface(new JavaScriptInterface() , "HTMLOUT");

//        webView1.getSettings().setLoadWithOverviewMode(true);
//        webView1.getSettings().setUseWideViewPort(true);

        return v;
    }

    WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
//            webView1.scrollTo(60, 200);

            return true;
        }
    };

    class JavaScriptInterface {
        public void getContentWidth(String value) {
            if (value != null) {
                webviewContentWidth = Integer.parseInt(value);

                Log.e("webviewContentWidth", webviewContentWidth + "");
            }
        }
    }
}