package com.example.sirovarweather.data.api;

import com.example.sirovarweather.data.model.GeocodingResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingApiService {
    @GET("v1/search")
    Call<GeocodingResponse> search(@Query("name") String name, @Query("count") int count,
                                   @Query("language") String language, @Query("format") String format);
}
