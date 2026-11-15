package com.popcorn.media.data.remote;

import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TmdbApiClient {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String PREF_NAME = "popcorn_prefs";
    private static final String KEY_TMDB_API_KEY = "tmdb_api_key";
    
    // Default fallback demo/public TMDB API key if user hasn't supplied one
    private static final String DEFAULT_API_KEY = "38b556b6adff7fa7ee5f73909772bf6d";

    private static TmdbApiService service;

    public static synchronized TmdbApiService getService() {
        if (service == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(TmdbApiService.class);
        }
        return service;
    }

    public static String getApiKey(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String savedKey = prefs.getString(KEY_TMDB_API_KEY, null);
        if (savedKey != null && !savedKey.trim().isEmpty()) {
            return savedKey.trim();
        }
        return DEFAULT_API_KEY;
    }

    public static void setApiKey(Context context, String apiKey) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_TMDB_API_KEY, apiKey).apply();
    }
}
