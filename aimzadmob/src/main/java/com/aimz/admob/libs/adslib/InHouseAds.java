package com.aimz.admob.libs.adslib;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aimz.admob.libs.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InHouseAds {

    private static ArrayList<InHouseModel> modelArrayList = new ArrayList<>();

    public static void getInHouseAds(Context context, String packageName) {
        getData(context, packageName);
    }

    private static void getData(Context context, String packageName) {
        RequestQueue queue = Volley.newRequestQueue(context); // this = context

        StringRequest getRequest = new StringRequest(Request.Method.POST, context.getString(R.string.ads_lib_base_url) + "inhouseadsfetchbypkg.php",
                response -> {
                    // display response
                    Log.d("Response1", response.toString());
                    Log.i("MyLog", response.toString());
                    try {
                        modelArrayList.clear();
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            modelArrayList.add(new InHouseModel(
                                    jsonObject.getString("adstitle"),
                                    jsonObject.getString("adssubtext"),
                                    jsonObject.getString("adsrating"),
                                    jsonObject.getString("adsicon"),
                                    jsonObject.getString("adsimage"),
                                    jsonObject.getString("adsvideo"),
                                    jsonObject.getBoolean("isVideo"),
                                    jsonObject.getString("packagename")));


                        }
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
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("packagename", packageName);

                return params;
            }
        };

// add it to the RequestQueue
        queue.add(getRequest);
    }

    public static ArrayList<InHouseModel> getModelAdsList() {
        return modelArrayList;
    }
}
