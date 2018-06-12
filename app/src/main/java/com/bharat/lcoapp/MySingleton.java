package com.bharat.lcoapp;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Bharat on 1/23/2018.
 */

public class MySingleton {

    private static MySingleton mySingleton;
    private static Context myContext;
    private RequestQueue requestQueue;

    private MySingleton(Context context){
        myContext = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(myContext.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getInstance(Context context){
        if(mySingleton==null){
            mySingleton = new MySingleton(context);
        }
        return mySingleton;

    }

    public void addToRequestQueue(Request request){
        requestQueue.add(request);

    }



}
