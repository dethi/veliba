package epita.com.veliba.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VelibService {
    public static final String ENDPOINT = "https://opendata.paris.fr";

    @GET("/api/records/1.0/search?dataset=stations-velib-disponibilites-en-temps-reel")
    Call<SearchResponse> listStation(@Query("start") int start, @Query("rows") int rows);
}
