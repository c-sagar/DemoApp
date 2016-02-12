package com.example.android.demoapp.volley;

import android.os.Bundle;

/**
 * Created by sagar on 2/12/2016.
 */
public interface ResponseListener {
    public static final String KEY_DATA = "bundle_key_data";

    public void onSuccess(Bundle bundle);
    public void onError(Exception e);
}
