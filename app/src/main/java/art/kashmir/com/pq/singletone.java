package art.kashmir.com.pq;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by HP on 15-12-2017.
 */

public class singletone {
    private static singletone mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private singletone(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized singletone getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new singletone(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


}

   // Here are some examples of performing RequestQueue operations using the singleton class:

// Get a RequestQueue

