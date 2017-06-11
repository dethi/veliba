package com.epita.veliba;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epita.veliba.service.Station;
import com.epita.veliba.service.StationItem;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class StationRecyclerViewAdapter extends RecyclerView.Adapter<StationViewHolder> {

    public static List<Station> mValues = new ArrayList<>();
    public static List<Station> sValues;

    public StationRecyclerViewAdapter() {
        mValues = new ArrayList<>();
        sValues = new ArrayList<>();
    }

    public void setData(List<StationItem> data) {
        mValues = new ArrayList<>();
        int index = 0;
        for (StationItem r : data) {
            mValues.add(new Station(index++, r));
        }
        setSearch("");
    }

    public void setSearch(String query) {
        sValues = new ArrayList<>();

        query = query.trim();
        if (query.isEmpty())
            sValues.addAll(mValues);
        else {
            Pattern pattern = Pattern.compile(Pattern.quote(query), Pattern.CASE_INSENSITIVE);
            for (Station s : mValues) {
                if (pattern.matcher(s.getItem().fields.name).find()) {
                    sValues.add(s);
                }
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public StationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.station_list_content, parent, false);
        return new StationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StationViewHolder holder, int position) {
        holder.mItem = sValues.get(position);
        holder.mContentView.setText(holder.mItem.getItem().fields.name);

        boolean isOpen = holder.mItem.getItem().fields.status.value;
        int color = ContextCompat.getColor(holder.mStatusView.getContext(), isOpen ? R.color.success : R.color.error);
        holder.mStatusView.setColorFilter(color);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent i = new Intent(context, StationDetailActivity.class);
                i.putExtra("itemId", holder.mItem.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sValues.size();
    }

}
