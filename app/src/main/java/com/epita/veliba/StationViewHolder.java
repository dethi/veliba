package com.epita.veliba;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epita.veliba.service.Station;

public class StationViewHolder extends RecyclerView.ViewHolder {

    public final View mView;
    public final TextView mContentView;
    public final ImageView mStatusView;
    public Station mItem;

    public StationViewHolder(View view) {
        super(view);
        mView = view;
        mContentView = (TextView) view.findViewById(R.id.content);
        mStatusView = (ImageView) view.findViewById(R.id.status);
    }
}
