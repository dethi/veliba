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

public class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<ViewHolder> {

    public static List<Station> mValues;
    public static List<Station> sValues;

    public SimpleItemRecyclerViewAdapter() {
        mValues = new ArrayList<>();
        sValues = new ArrayList<>();
    }

    public void setData(List<StationItem> myDataset) {
        mValues = new ArrayList<>();
        int index = 0;
        for (StationItem r : myDataset) {
            mValues.add(new Station(index++, r));
        }
        setSearch("");
    }

    public void setSearch(String query) {
        sValues = new ArrayList<>();
        for (Station s : mValues) {
            if (query.isEmpty() || Pattern.compile(Pattern.quote(query), Pattern.CASE_INSENSITIVE).matcher(s.getItem().fields.name).find()) {
                sValues.add(s);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.station_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
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
                i.putExtra("itemId", String.valueOf(holder.mItem.getId()));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sValues.size();
    }

}
