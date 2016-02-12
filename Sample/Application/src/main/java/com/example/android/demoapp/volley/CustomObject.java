package com.example.android.demoapp.volley;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by sagar on 2/12/2016.
 */
public class CustomObject extends RealmObject implements Serializable{
    private long ID;
    private String uri, title;

    public CustomObject() {}
    public CustomObject(long ID, String uri, String title) {
        this.ID = ID;
        this.uri = uri;
        this.title = title;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
