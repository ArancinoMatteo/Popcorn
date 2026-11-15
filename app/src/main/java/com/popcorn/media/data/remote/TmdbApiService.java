package com.popcorn.media.data.remote;

import com.popcorn.media.data.remote.model.TmdbSearchMovieResponse;
import com.popcorn.media.data.remote.model.TmdbSearchTvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TmdbApiService {

    @GET("search/movie")
    Call<TmdbSearchMovieResponse> searchMovie(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("year") String year,
            @Query("language") String language
    );

    @GET("search/tv")
    Call<TmdbSearchTvResponse> searchTv(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("first_air_date_year") String year,
            @Query("language") String language
    );
}
