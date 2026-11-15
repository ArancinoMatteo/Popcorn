package com.popcorn.media.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.popcorn.media.data.local.entity.MediaItem;

import java.util.List;

@Dao
public interface MediaDao {

    @Query("SELECT * FROM media_items ORDER BY addedTimestamp DESC")
    LiveData<List<MediaItem>> getAllMedia();

    @Query("SELECT * FROM media_items WHERE lastPositionMs > 0 AND isWatched = 0 ORDER BY addedTimestamp DESC")
    LiveData<List<MediaItem>> getContinueWatching();

    @Query("SELECT * FROM media_items ORDER BY addedTimestamp DESC LIMIT 20")
    LiveData<List<MediaItem>> getRecentlyAdded();

    @Query("SELECT * FROM media_items WHERE type = 'MOVIE' ORDER BY addedTimestamp DESC")
    LiveData<List<MediaItem>> getMovies();

    @Query("SELECT * FROM media_items WHERE type = 'TV_SHOW' GROUP BY title ORDER BY addedTimestamp DESC")
    LiveData<List<MediaItem>> getTvShows();

    @Query("SELECT * FROM media_items WHERE title = :title AND seasonNumber = :seasonNumber AND type = 'TV_SHOW' ORDER BY episodeNumber ASC")
    LiveData<List<MediaItem>> getEpisodesForShow(String title, int seasonNumber);

    @Query("SELECT * FROM media_items WHERE uriString = :uriString LIMIT 1")
    MediaItem getMediaByUri(String uriString);

    @Query("SELECT * FROM media_items WHERE id = :id LIMIT 1")
    LiveData<MediaItem> getMediaById(long id);

    @Query("SELECT * FROM media_items WHERE id = :id LIMIT 1")
    MediaItem getMediaByIdSync(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(MediaItem item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MediaItem> items);

    @Update
    void update(MediaItem item);

    @Query("UPDATE media_items SET lastPositionMs = :positionMs, isWatched = :isWatched WHERE id = :id")
    void updatePlaybackPosition(long id, long positionMs, boolean isWatched);

    @Query("DELETE FROM media_items")
    void deleteAll();
}
