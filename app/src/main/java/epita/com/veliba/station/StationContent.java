package epita.com.veliba.station;

import com.google.gson.annotations.SerializedName;
import epita.com.veliba.api.VelibService;
import epita.com.veliba.api.SearchResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.*;

public class StationContent {

    public static final List<StationItem> ITEMS = new ArrayList<>();
    public static final Map<String, StationItem> ITEM_MAP = new HashMap<String, StationItem>();

    public static final VelibService velibService;

    static {
        Retrofit apiClient = new Retrofit.Builder()
                .baseUrl(VelibService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        velibService = apiClient.create(VelibService.class);
        Call<SearchResponse> velibList = velibService.listStation(0, 10);
        velibList.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    final SearchResponse items = response.body();
                    if (items != null && items.records.size() > 0) {
                        for (StationItem item : items.records) {
                            addItem(item);
                        }
                    }
                } else {
                    System.out.println("Error");
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                System.out.println("Failure");
            }
        });
    }

    private static void addItem(StationItem item) {
        ITEMS.add(item);
        //ITEM_MAP.put(item.id, item);
    }
}
