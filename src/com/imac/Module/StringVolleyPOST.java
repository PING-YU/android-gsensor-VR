package com.imac.Module;

import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


public class StringVolleyPOST {
	public static int STATE_OK = 0, STATE_ERROR = 1;
	private StringRequest SR;

	public interface OnStringResponse {
		public void OnString(int state, StringVolleyPOST SVP, String data);
	}

	public StringVolleyPOST(String url, final OnStringResponse OSR,
			final Map<String, String> map) {
		SR = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>() {
					public void onResponse(String arg0) {
						OSR.OnString(STATE_OK, StringVolleyPOST.this, arg0);
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError arg0) {
						OSR.OnString(STATE_ERROR, StringVolleyPOST.this, null);
					}
				}) {

			protected Map<String, String> getParams() throws AuthFailureError {
				return map;
			}
		};
	}

	public StringRequest getSR() {
		return SR;
	}

	public void clean() {
		SR.cancel();
		SR = null;
	}
}
