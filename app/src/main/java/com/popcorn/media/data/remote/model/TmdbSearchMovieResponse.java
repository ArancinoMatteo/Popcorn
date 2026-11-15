package com.popcorn.media.data.remote.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TmdbSearchMovieResponse {
    @SerializedName("results")
    private List<TmdbMovieResult> results;

    public List<TmdbMovieResult> getResults() {
        return results;
    }
}
