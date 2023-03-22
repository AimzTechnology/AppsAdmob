package com.aimz.admob.libs.adslib;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aimz.admob.libs.R;

import java.util.HashMap;
import java.util.Map;

public class AdsClick {

    public static void onAdClick(Context context, String appName, String packageName,
                                  String adPkgName, String adType) {
        RequestQueue queue = Volley.newRequestQueue(context); // this = context
        StringRequest getRequest = new StringRequest(Request.Method.POST,
                context.getString(R.string.ads_lib_base_url) + "adclick.php",
                response -> {
                    // display response
                    Log.d("Response1", response.toString());
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    try {
                        Log.d("Response1", "Error.Response" + error.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("appname", appName);
                params.put("packagename", packageName);
                params.put("adpkgname", adPkgName);
                params.put("adtype", adType);

                return params;
            }
        };

        // add it to the RequestQueue
        queue.add(getRequest);

    }

}
