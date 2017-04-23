package com.example.tanvi.NTrusted.Source.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by tanvi on 4/15/2017.
 */

public class VolleySingleton {

    private static VolleySingleton mInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    public Context mContext;

    private VolleySingleton(Context c){
        mContext = c;
        mRequestQueue = Volley.newRequestQueue(mContext);
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    public static VolleySingleton getInstance(Context c){

        if(mInstance == null){
            mInstance = new VolleySingleton(c);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return this.mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return this.mImageLoader;
    }


}
