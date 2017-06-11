package com.epita.veliba;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epita.veliba.service.Station;
import com.epita.veliba.service.StationItem;

import java.text.DateFormat;

public class PlaceHolderFragment extends Fragment {

    private StationItem mItem;

    public PlaceHolderFragment() {
        mItem = new StationItem();
    }

    public void setStation(Station station) {
        mItem = station.getItem();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.station_detail, container, false);


        TextView station_id = (TextView) rootView.findViewById(R.id.station_id);
        TextView station_name = (TextView) rootView.findViewById(R.id.station_name);
        ImageView mStatusView = (ImageView) rootView.findViewById(R.id.status);
        TextView available_bike_stands = (TextView) rootView.findViewById(R.id.available_bike_stands);
        TextView bike_stands = (TextView) rootView.findViewById(R.id.bike_stands);
        TextView address = (TextView) rootView.findViewById(R.id.address);
        TextView last_update = (TextView) rootView.findViewById(R.id.last_update);

        String[] stationName = mItem.fields.name.split(" - ");
        station_id.setText(stationName[0]);
        station_name.setText(stationName[1]);
        bike_stands.setText(String.valueOf(mItem.fields.bikeStands));
        available_bike_stands.setText(String.valueOf(mItem.fields.availableBikeStands));
        address.setText(TextUtils.join("\n", mItem.fields.address.split(" - ")));
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double lat = mItem.fields.position.get(0);
                Double lng = mItem.fields.position.get(1);
                Uri gmmIntentUri = Uri.parse(String.format("geo:%.0f,%.0f?q=%.0f,%.0f(%s)", lat, lng, lat, lng, mItem.fields.name));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        DateFormat dateFormat = DateFormat.getDateInstance();
        last_update.setText(dateFormat.format(mItem.fields.lastUpdate));

        int color = ContextCompat.getColor(mStatusView.getContext(), mItem.fields.status.value ? R.color.success : R.color.error);
        mStatusView.setColorFilter(color);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Velib");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, mItem.fields.name);
                sharingIntent.setType("text/plain");
                startActivity(Intent.createChooser(sharingIntent, getResources().getText(R.string.share_to)));
            }
        });

        return rootView;
    }
}