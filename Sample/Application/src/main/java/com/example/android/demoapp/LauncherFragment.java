
package com.example.android.demoapp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.demoapp.volley.CustomObject;
import com.example.android.demoapp.volley.RequestHandler;
import com.example.android.demoapp.volley.RequestType;
import com.example.android.demoapp.volley.ResponseListener;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class LauncherFragment extends Fragment implements ResponseListener {

    private static final String TAG = "LauncherFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<CustomObject> mDataset;

    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    TextView emptyView;
    ProgressBar progressBar;

    @Override
    public void onSuccess(Bundle bundle) {
        mDataset = (ArrayList<CustomObject>) bundle.getSerializable(ResponseListener.KEY_DATA);
        mRecyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        if (mDataset != null)
            mRecyclerView.setAdapter(new CustomAdapter(mDataset));

    }

    @Override
    public void onError(Exception e) {
        emptyView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        emptyView.setText("" + e.getMessage());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view_frag, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        emptyView = (TextView) rootView.findViewById(R.id.emptyView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        initDataset();
        return rootView;
    }

    private void initDataset() {
        Realm realm = Realm.getInstance(getContext());
        if (realm.where(CustomObject.class).count() <= 0)
            new RequestHandler().requestNetworkCall(RequestType.GET, RequestHandler.BASE_URL + RequestHandler.REQ_METHOD_GET_DATA, this);
        else {
            Toast.makeText(getActivity(), "get from local", Toast.LENGTH_SHORT).show();
            ArrayList<CustomObject> objects = new ArrayList<>();

            RealmResults<CustomObject> objectList = realm.where(CustomObject.class).notEqualTo("title", "null").findAll();
            for (int i = 0; i < objectList.size(); i++)
                objects.add(objectList.get(i));
            Bundle bundle = new Bundle();
            bundle.putSerializable(ResponseListener.KEY_DATA, objects);
            onSuccess(bundle);
        }
    }
}
