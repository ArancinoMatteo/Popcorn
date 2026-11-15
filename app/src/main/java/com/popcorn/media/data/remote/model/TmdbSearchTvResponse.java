package com.popcorn.media.data.remote.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TmdbSearchTvResponse {
    @SerializedName("results")
    private List<TmdbTvResult> results;

    public List<TmdbTvResult> getResults() {
        return results;
    }
}
