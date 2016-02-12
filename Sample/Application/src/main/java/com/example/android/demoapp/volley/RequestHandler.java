package com.example.android.demoapp.volley;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.demoapp.AppDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by sagar on 2/12/2016.
 */
public class RequestHandler {
    public static final String BASE_URL = "http://private-21c80-care24androidtest.apiary-mock.com/";
    public static final String REQ_METHOD_GET_DATA = "androidtest/getData";
    private static final String TAG = RequestHandler.class.getSimpleName();
    public static String tag_json_obj = "json_obj_req";
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) AppDelegate.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void requestNetworkCall(int reqType, String reqUrl, final ResponseListener responseListener) {
        if (!isNetworkAvailable()) {
            responseListener.onError(new Exception("Network error :("));
            return;
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(reqType, reqUrl,
                new Response.Listener<JSONObject>() {
                    public String title;
                    public long id;
                    public String imageuri;
                    ArrayList<CustomObject> objects = new ArrayList<>();
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray objectArray = response.getJSONArray("data");
                            for (int i = 0; i < objectArray.length(); i++) {
                                id = objectArray.getJSONObject(i).getLong("id");
                                imageuri = objectArray.getJSONObject(i).getString("uri");
                                title = objectArray.getJSONObject(i).getString("title");
                                CustomObject object = new CustomObject(id, imageuri, title);
                                Realm realm = Realm.getInstance(AppDelegate.getInstance());
                                realm.beginTransaction();
                                long count = realm.where(CustomObject.class).equalTo("ID", object.getID()).count();
                                if (count == 0)
                                    realm.copyToRealm(object);
                                realm.commitTransaction();
                                //objects.add(object);
                            }
                            Realm realm = Realm.getInstance(AppDelegate.getInstance());
                            realm.beginTransaction();
                            RealmResults<CustomObject> objectList = realm.where(CustomObject.class).notEqualTo("title", "null").findAll();
                            for (int i = 0; i < objectList.size(); i++)
                                objects.add(objectList.get(i));
                            realm.commitTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(ResponseListener.KEY_DATA, objects);
                            responseListener.onSuccess(bundle);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            responseListener.onError(new Exception("Failed to parse response"));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseListener.onError(error);
            }
        });


// Adding request to request queue
        AppDelegate.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        return;
    }
}
