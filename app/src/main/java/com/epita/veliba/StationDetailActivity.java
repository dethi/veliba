package com.epita.veliba;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.epita.veliba.service.Station;

import java.util.List;

public class StationDetailActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private StationPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final Intent intent = getIntent();
        final int itemId = (intent != null && intent.hasExtra("itemId")) ? Integer.parseInt(getIntent().getExtras().getString("itemId")) : 0;

        List<Station> data = StationRecyclerViewAdapter.mValues;
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new StationPagerAdapter(getSupportFragmentManager(), data);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(itemId);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_group:
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
}
