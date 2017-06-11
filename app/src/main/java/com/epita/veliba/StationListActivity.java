package com.epita.veliba;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.epita.veliba.service.SearchResponse;
import com.epita.veliba.service.StationItem;
import com.epita.veliba.service.VelibService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StationListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static List<StationItem> previousData = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private StationRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        mRecyclerView = (RecyclerView) findViewById(R.id.station_list);
        assert mRecyclerView != null;

        // Tablet mode
        mAdapter = new StationRecyclerViewAdapter();
        mAdapter.setData(previousData);
        mRecyclerView.setAdapter(mAdapter);


        // CHECK CONNEXION
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // connected
        } else {
            // display error
            return;
        }

        // CALL REQUEST
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(VelibService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        VelibService velibService = retrofit.create(VelibService.class);

        Call<SearchResponse> permissionList = velibService.listStation(0, 100);
        permissionList.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    SearchResponse data = response.body();

                    mAdapter.setData(data.records);
                    mAdapter.notifyDataSetChanged();
                    previousData = data.records;
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                // DO NOTHING
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search_action).getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.group_action:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.group_alert)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mAdapter.setSearch(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        mAdapter.setSearch(query);
        return true;
    }

}
