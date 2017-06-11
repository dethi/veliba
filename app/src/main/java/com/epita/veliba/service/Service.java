package com.epita.veliba.service;


import android.support.annotation.NonNull;

import com.epita.veliba.utils.Consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class Service {
    public static final List<StationItem> stations = new ArrayList<>();
    private static final VelibService velibService;
    private static final AtomicBoolean fetchingData = new AtomicBoolean(false);
    private static Integer lastIndex = 0;

    static {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(VelibService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        velibService = retrofit.create(VelibService.class);
    }

    public static void listStation(final Consumer<List<StationItem>> callback) {
        if (lastIndex > 0) {
            callback.accept(stations);
        } else {
            if (fetchingData.compareAndSet(false, true)) {
                Call<SearchResponse> listStationRequest = velibService.listStation(lastIndex, 100);
                listStationRequest.enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
                        if (response.isSuccessful()) {
                            SearchResponse data = response.body();
                            if (data != null) {
                                stations.addAll(data.records);
                                lastIndex += data.records.size();
                            }

                            fetchingData.set(false);
                            callback.accept(stations);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {
                        fetchingData.set(false);
                    }
                });
            }
        }
    }
}