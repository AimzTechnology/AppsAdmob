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
import com.aimz.admob.libs.admob.ENUMS;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TestAds {
    public static void getTestAds(Context context, int showAdmob, String packageName) {
        if(SharedPrefUtils.getStringData(context, Constants.APP_ID) == null) {
            storeAds(context);
        }

//        if (InternetConnection.checkConnection(context)) {
            if(showAdmob == ENUMS.ADMOB) {
                fetchData(context);
                SharedPrefUtils.saveData(context, Constants.SHOW_ADMOB, true);
            }
           /* else if(showAdmob == ENUMS.APPLOVIN){
                fetchApplovin(context, packageName);
                SharedPrefUtils.saveData(context, Constants.SHOW_ADMOB, false);
            }*/
//        }

//        InHouseAds.getInHouseAds(context, packageName);
    }

    private static void storeAds(Context context) {
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

        String adCounter = SharedPrefUtils.getStringData(context, Constants.AD_COUNTER);
        if (adCounter == null) {
            SharedPrefUtils.saveData(context, Constants.AD_COUNTER, "0");
        }
    }

    private static void fetchData(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context); // this = context

        StringRequest getRequest = new StringRequest(Request.Method.GET,
                context.getString(R.string.ads_lib_base_url) + "fetchtestads.php",
                response -> {
                    // display response
                    Log.d("Response1", response.toString());
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            SharedPrefUtils.saveData(context, Constants.APP_ID, jsonObject.getString("appid"));
                            SharedPrefUtils.saveData(context, Constants.INTERSTIAL, jsonObject.getString("inter"));
                            SharedPrefUtils.saveData(context, Constants.BANNER, jsonObject.getString("banner"));
                            SharedPrefUtils.saveData(context, Constants.NATIVE_AD, jsonObject.getString("native"));
                            SharedPrefUtils.saveData(context, Constants.OPEN_AD, jsonObject.getString("openad"));
                            SharedPrefUtils.saveData(context, Constants.AD_COUNTER, jsonObject.getString("intercounter"));
                        }
                        AdmobAds.loadAdmobInters(context);
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
        );

// add it to the RequestQueue
        queue.add(getRequest);
    }


}
