package com.example.imac_gsensor;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.imac.Module.StringVolley;
import com.imac.Module.StringVolleyPOST;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Service;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private WebView webView1, webView2;
    private SensorManager aSensorManager;
	private Sensor bSensor;
	private float gravity[] = new float[3];
	public static RequestQueue RQ;
	private JSONObject msgToCloud;
	private float x,y,z;
	private long lastUpdateTime = System.currentTimeMillis();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        RelativeLayout rel = (RelativeLayout) findViewById(R.id.space1);

        FragmentManager fm = getFragmentManager();
        // 撘��Fragment鈭
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.space1, new Fragment1());

        transaction.replace(R.id.space2, new Fragment2());
//
//        transaction.replace(R.id.space3, new Fragment3());
//
//        transaction.replace(R.id.space4, new Fragment4());

        transaction.commit();
        
        
        
        RQ = Volley.newRequestQueue(this);
        
    	aSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		bSensor = aSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
//		aSensorManager.registerListener(this, bSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    WebViewClient mWebViewClient2 = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };
}
