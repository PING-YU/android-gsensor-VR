package com.example.imac_gsensor;

import org.json.JSONObject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.imac.Module.StringVolley;

public class accelerometerDetect extends Activity implements
		SensorEventListener {
	private TextView text_x;
	private TextView text_y;
	private TextView text_z;
	private SensorManager aSensorManager;
	private Sensor bSensor;
	private float gravity[] = new float[3];
	public static RequestQueue RQ;
	private JSONObject msgToCloud;
	private WebView mWebView = null;
	private float x,y,z;
	private long lastUpdateTime = System.currentTimeMillis();
	/** Called when the activity is first created. */
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main); 
		
		RQ = Volley.newRequestQueue(this);
        FragmentManager fm = getFragmentManager();
        // 撘��Fragment鈭
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.space1, new Fragment1());
        transaction.replace(R.id.space2, new Fragment2());
        transaction.commit();
		
//		mWebView = (WebView)findViewById(R.id.webview);
//		 mWebView.setWebViewClient(mWebViewClient);
//		  mWebView.loadUrl("http://root:r00tme@172.16.2.26/axis-cgi/mjpg/video.cgi");
//		  mWebView.setInitialScale(getScale());
//		  
//		  mWebView.getSettings().setLoadWithOverviewMode(true); 
//		  mWebView.getSettings().setUseWideViewPort(true); 
		  
//		text_x = (TextView) findViewById(R.id.TextView01); 
//		text_y = (TextView) findViewById(R.id.TextView02);
//		text_z = (TextView) findViewById(R.id.TextView03);

		aSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		bSensor = aSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		aSensorManager.registerListener(this, bSensor, SensorManager.SENSOR_DELAY_NORMAL);
	} 
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}  

	public void onSensorChanged(SensorEvent event) {
		   long nowTime = System.currentTimeMillis();  
		   if(nowTime-lastUpdateTime>600){
			   lastUpdateTime = System.currentTimeMillis();
				gravity[0] = event.values[0];
				gravity[1] = event.values[1];
				gravity[2] = event.values[2];
				
				if(Math.abs(x-gravity[0])<300){
					x = gravity[0]-180; 
				}
				y = gravity[1];
				
				if(Math.abs(gravity[2]-z)>15)
					z = gravity[2]-90;
//				Log.e("123", "http://172.16.2.134/hackathon/index.php?pan="+x+"&tilt="+z+"&="+y);
				new StringVolley(RQ)
				.setInfo("http://172.16.2.134/hackathon/index.php?pan="+x+"&tilt="+z+"&zoom=1", new StringVolley.OnStringResponse() {
					public void OnString(int state, StringVolley SV,
							String data) {
						if (state == StringVolley.STATE_OK) {// 傳送成功
							Log.e("OK", "ok");
					} else {// 傳送失敗 
						Log.e("e", "error");
						}
						SV.clean();
					} 
				}).commit(Request.Method.GET);  
		   }
//			text_x.setText("X = " + gravity[0]);   
//			text_y.setText("Y = " + gravity[1]);
//			text_z.setText("Z = " + gravity[2]);
	}
	 WebViewClient mWebViewClient = new WebViewClient() {
		  public boolean shouldOverrideUrlLoading(WebView view, String url) {
		   view.loadUrl(url);
		   return true;
		  }
	};
	@Override 
	protected void onPause() {
		// TODO Auto-generated method stub
		/* 取消註冊SensorEventListener */
//		aSensorManager.unregisterListener(this);
		Toast.makeText(this, "Unregister accelerometerListener",
				Toast.LENGTH_LONG).show(); 
		super.onPause();
	}
	
    WebViewClient mWebViewClient2 = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };
}