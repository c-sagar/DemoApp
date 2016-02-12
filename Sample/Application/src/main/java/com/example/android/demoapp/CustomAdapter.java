
package com.example.android.demoapp;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.android.demoapp.volley.CustomObject;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private ArrayList<CustomObject> objectList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final NetworkImageView imageView;
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            textView = (TextView) v.findViewById(R.id.textView);
            imageView = (NetworkImageView) v.findViewById(R.id.imageView);
        }

        public NetworkImageView getImageView() {
            return imageView;
        }

        public TextView getTextView() {
            return textView;
        }
    }


    public CustomAdapter(ArrayList<CustomObject> objectList) {
        this.objectList = objectList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        viewHolder.getTextView().setText(objectList.get(position).getTitle());
        viewHolder.getImageView().setImageUrl(objectList.get(position).getUri(), AppDelegate.getInstance().getImageLoader());
        viewHolder.getImageView().setErrorImageResId(R.drawable.ic_launcher);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "" + objectList.get(position).getTitle(), Snackbar.LENGTH_LONG).show();

            }
        });
    }
    @Override
    public int getItemCount() {
        return objectList.size();
    }
}
