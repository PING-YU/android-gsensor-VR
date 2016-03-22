package com.imac.Module;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class StringVolley {
	public static int STATE_OK = 0, STATE_ERROR = 1;
	private StringRequest SR;
	private Map<String, String> params;
	private String url;
	private OnStringResponse OSR;
	private RequestQueue RQ;
	private String TAG = "NaN";

	public interface OnStringResponse {
		public void OnString(int state, StringVolley SV, String data);
	}

	public StringVolley(RequestQueue RQ) {
		this.RQ = RQ;
		this.params = new HashMap<String, String>();
	}

	public StringVolley setInfo(String url, OnStringResponse OSR) {
		this.url = url;
		this.OSR = OSR;
		return this;
	}

	public StringVolley setTAG(String tag) {
		this.TAG = tag;
		return this;
	}

	public StringVolley addParams(String key, String val) {
		params.put(key, val);
		return this;
	}

	public void commit(int Method) {
		if (Request.Method.POST == Method) {
			SR = new StringRequest(Request.Method.POST,
					url/* + URLConvert() */, new Response.Listener<String>() {
						public void onResponse(String arg0) {
							if (OSR != null)
								OSR.OnString(STATE_OK, StringVolley.this, arg0);
						}
					}, new Response.ErrorListener() {
						public void onErrorResponse(VolleyError arg0) {
							if (OSR != null)
								OSR.OnString(STATE_ERROR, StringVolley.this,
										null);
						}
					}) {
				protected Map<String, String> getParams()
						throws AuthFailureError {
					return params;
				}
			};
		} else {
			SR = new StringRequest(Request.Method.GET, url + URLConvert(),
					new Response.Listener<String>() {
						public void onResponse(String arg0) {
							if (OSR != null)
								OSR.OnString(STATE_OK, StringVolley.this, arg0);
						}
					}, new Response.ErrorListener() {
						public void onErrorResponse(VolleyError arg0) {
							if (OSR != null)
								OSR.OnString(STATE_ERROR, StringVolley.this,
										null);
						}
					}) {
			};
		}
		SR.setTag(TAG);
		RQ.add(SR);
	}

	public String URLConvert() {
		StringBuffer SB = new StringBuffer();
		SB.append("?");
		Iterator<String> iterator = params.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			SB.append(key);
			SB.append("=");
			SB.append(params.get(key));
			SB.append("&");
		}
		SB.deleteCharAt(SB.length() - 1);
		return SB.toString();
	}

	public StringRequest getSR() {
		return SR;
	}

	public void clean() {
		if (params != null)
			params.clear();
		params = null;
		SR.cancel();
		SR = null;
		RQ.cancelAll(TAG);
	}
}
