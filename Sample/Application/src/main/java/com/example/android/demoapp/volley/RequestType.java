package com.example.android.demoapp.volley;

import android.support.annotation.IntDef;

import com.android.volley.Request;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.example.android.demoapp.volley.RequestType.GET;
import static com.example.android.demoapp.volley.RequestType.POST;

/**
 * Created by sagar on 2/12/2016.
 */
@IntDef ({GET, POST})
@Retention(RetentionPolicy.SOURCE)
public @interface RequestType {
    public static final int GET = Request.Method.GET;
    public static final int POST = Request.Method.POST;

}
