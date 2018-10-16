package com.glackemi.alyusra;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Created by AlexBoey on 5/21/2017.
 */

public class API {

    public static String URL ="https://40.85.224.168:3000/orders";
   // public static String URL ="http://192.168.1.102:8080/mbongo/";

    public static void POST(final Context context, final Map<String,String> params, final VolleyCallback callback ) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Log.i("# REQUEST",URL);
        Log.i("# PARAMS",params.toString());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String results) {
                        Log.i("# results",results);
                        callback.onSuccess(results);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams(){

                return params;
            }
        };
        queue.add(stringRequest);
    }

    public static void GET(payActivity payActivity, Map<String, String> params, VolleyCallback volleyCallback) {
    }

    public interface VolleyCallback{
        void onSuccess(String result);
    }

}
