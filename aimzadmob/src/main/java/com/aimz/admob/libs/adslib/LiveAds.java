package com.aimz.admob.libs.adslib;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aimz.admob.libs.R;
import com.aimz.admob.libs.admob.AdmobAds;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LiveAds {

    public static void getLiveAds(Context context, String packageName) {
        if(SharedPrefUtils.getStringData(context, Constants.APP_ID) == null) {
            storeAds(context);
        }

        if (InternetConnection.checkConnection(context)) {
            fetchData(context, packageName);
        }

//        InHouseAds.getInHouseAds(context, packageName);
    }

    private static void storeAds(Context context) {
        SharedPrefUtils.saveData(context, Constants.SHOW_ADMOB, true);

        String appid = SharedPrefUtils.getStringData(context, Constants.APP_ID);
        if (appid == null) {
            SharedPrefUtils.saveData(context, Constants.APP_ID, "no");

        }
        String inters = SharedPrefUtils.getStringData(context, Constants.INTERSTIAL);
        if (inters == null) {
            SharedPrefUtils.saveData(context, Constants.INTERSTIAL, "no");

        }
        String banner = SharedPrefUtils.getStringData(context, Constants.BANNER);
        if (banner == null) {
            SharedPrefUtils.saveData(context, Constants.BANNER, "no");

        }
        String native1 = SharedPrefUtils.getStringData(context, Constants.NATIVE_AD);
        if (native1 == null) {
            SharedPrefUtils.saveData(context, Constants.NATIVE_AD, "no");

        }
        String openad = SharedPrefUtils.getStringData(context, Constants.OPEN_AD);
        if (openad == null) {
            SharedPrefUtils.saveData(context, Constants.OPEN_AD, "no");
        }

        String applovinInter = SharedPrefUtils.getStringData(context, Constants.APPLOVIN_INTER);
        if (applovinInter == null) {
            SharedPrefUtils.saveData(context, Constants.APPLOVIN_INTER, "no");
        }
        String applovinNative = SharedPrefUtils.getStringData(context, Constants.APPLOVIN_NATIVE);
        if (applovinNative == null) {
            SharedPrefUtils.saveData(context, Constants.APPLOVIN_NATIVE, "no");
        }
        String applovinBanner = SharedPrefUtils.getStringData(context, Constants.APPLOVIN_BANNER);
        if (applovinBanner == null) {
            SharedPrefUtils.saveData(context, Constants.APPLOVIN_BANNER, "no");
        }
    }

    private static void fetchData(Context context, String packageName) {
        RequestQueue queue = Volley.newRequestQueue(context); // this = context
        StringRequest postRequest = new StringRequest(Request.Method.POST, context.getString(R.string.ads_lib_base_url) + "fetchidsbypackage.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response1", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                SharedPrefUtils.saveData(context, Constants.APP_ID, jsonObject.getString("appid"));
                                SharedPrefUtils.saveData(context, Constants.INTERSTIAL, jsonObject.getString("inters"));
                                SharedPrefUtils.saveData(context, Constants.BANNER, jsonObject.getString("banner"));
                                SharedPrefUtils.saveData(context, Constants.NATIVE_AD, jsonObject.getString("native"));
                                SharedPrefUtils.saveData(context, Constants.OPEN_AD, jsonObject.getString("openad"));

                                SharedPrefUtils.saveData(context, Constants.APPLOVIN_INTER, jsonObject.getString("applovinInter"));
                                SharedPrefUtils.saveData(context, Constants.APPLOVIN_NATIVE, jsonObject.getString("applovinNative"));
                                SharedPrefUtils.saveData(context, Constants.APPLOVIN_BANNER, jsonObject.getString("applovinBanner"));

                                SharedPrefUtils.saveData(context, Constants.AD_COUNTER, jsonObject.getString("intercounter"));
                                SharedPrefUtils.saveData(context, Constants.SHOW_ADMOB, jsonObject.getBoolean("showAdmob"));
                            }

                            try {
                                boolean showAdmob = SharedPrefUtils.getBooleanData(context, Constants.SHOW_ADMOB);
                                if(showAdmob) {
                                    AdmobAds.loadAdmobInters(context);
                                }else {
//                                    AppLovinAds.Companion.loadInterstitialAd(context, (Activity) context);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        try {
                            Log.d("Response1", "Error.Response" + error.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("packagename", packageName);

                return params;
            }
        };
        queue.add(postRequest);
    }
}
