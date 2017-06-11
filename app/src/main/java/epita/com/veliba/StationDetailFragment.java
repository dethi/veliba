package epita.com.veliba;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import epita.com.veliba.station.StationContent;
import epita.com.veliba.station.StationItem;

/**
 * A fragment representing a single Station detail screen.
 * This fragment is either contained in a {@link StationListActivity}
 * in two-pane mode (on tablets) or a {@link StationDetailActivity}
 * on handsets.
 */
public class StationDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private StationItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StationDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = StationContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.station_detail, container, false);


        if (mItem != null) {

            TextView station_name = (TextView) rootView.findViewById(R.id.station_name);
            if (station_name != null)
            {
                station_name.setText(mItem.fields.name);
            }

            TextView station_status = (TextView) rootView.findViewById(R.id.station_status);
            if (station_status != null)
            {
                station_status.setText(mItem.fields.status.value ? "Ouverte" : "Fermée");
            }


            TextView available_bike_stands = (TextView) rootView.findViewById(R.id.available_bike_stands);
            if (available_bike_stands != null)
            {
                available_bike_stands.setText(String.valueOf(mItem.fields.availableBikeStands));
            }

            TextView bike_stands = (TextView) rootView.findViewById(R.id.bike_stands);
            if (bike_stands != null)
            {
                bike_stands.setText(String.valueOf(mItem.fields.bikeStands));
            }


            TextView address = (TextView) rootView.findViewById(R.id.address);
            if (address != null)
            {
                address.setText(mItem.fields.address);
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
            }

            TextView last_update = (TextView) rootView.findViewById(R.id.last_update);
            if (last_update != null)
            {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                last_update.setText(dateFormat.format(mItem.fields.lastUpdate));
            }

        }

        return rootView;
    }
}
