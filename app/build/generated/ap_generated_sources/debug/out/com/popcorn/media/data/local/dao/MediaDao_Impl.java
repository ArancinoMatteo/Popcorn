package com.popcorn.media.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.popcorn.media.data.local.entity.MediaItem;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MediaDao_Impl implements MediaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MediaItem> __insertionAdapterOfMediaItem;

  private final EntityDeletionOrUpdateAdapter<MediaItem> __updateAdapterOfMediaItem;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePlaybackPosition;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public MediaDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMediaItem = new EntityInsertionAdapter<MediaItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `media_items` (`id`,`uriString`,`fileName`,`title`,`year`,`type`,`seasonNumber`,`episodeNumber`,`posterPath`,`backdropPath`,`overview`,`rating`,`durationMs`,`genres`,`lastPositionMs`,`isWatched`,`addedTimestamp`,`tmdbId`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final MediaItem entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getUriString() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getUriString());
        }
        if (entity.getFileName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getFileName());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getTitle());
        }
        if (entity.getYear() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getYear());
        }
        if (entity.getType() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getType());
        }
        statement.bindLong(7, entity.getSeasonNumber());
        statement.bindLong(8, entity.getEpisodeNumber());
        if (entity.getPosterPath() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getPosterPath());
        }
        if (entity.getBackdropPath() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getBackdropPath());
        }
        if (entity.getOverview() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getOverview());
        }
        statement.bindDouble(12, entity.getRating());
        statement.bindLong(13, entity.getDurationMs());
        if (entity.getGenres() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getGenres());
        }
        statement.bindLong(15, entity.getLastPositionMs());
        final int _tmp = entity.isWatched() ? 1 : 0;
        statement.bindLong(16, _tmp);
        statement.bindLong(17, entity.getAddedTimestamp());
        statement.bindLong(18, entity.getTmdbId());
      }
    };
    this.__updateAdapterOfMediaItem = new EntityDeletionOrUpdateAdapter<MediaItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `media_items` SET `id` = ?,`uriString` = ?,`fileName` = ?,`title` = ?,`year` = ?,`type` = ?,`seasonNumber` = ?,`episodeNumber` = ?,`posterPath` = ?,`backdropPath` = ?,`overview` = ?,`rating` = ?,`durationMs` = ?,`genres` = ?,`lastPositionMs` = ?,`isWatched` = ?,`addedTimestamp` = ?,`tmdbId` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final MediaItem entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getUriString() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getUriString());
        }
        if (entity.getFileName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getFileName());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getTitle());
        }
        if (entity.getYear() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getYear());
        }
        if (entity.getType() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getType());
        }
        statement.bindLong(7, entity.getSeasonNumber());
        statement.bindLong(8, entity.getEpisodeNumber());
        if (entity.getPosterPath() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getPosterPath());
        }
        if (entity.getBackdropPath() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getBackdropPath());
        }
        if (entity.getOverview() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getOverview());
        }
        statement.bindDouble(12, entity.getRating());
        statement.bindLong(13, entity.getDurationMs());
        if (entity.getGenres() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getGenres());
        }
        statement.bindLong(15, entity.getLastPositionMs());
        final int _tmp = entity.isWatched() ? 1 : 0;
        statement.bindLong(16, _tmp);
        statement.bindLong(17, entity.getAddedTimestamp());
        statement.bindLong(18, entity.getTmdbId());
        statement.bindLong(19, entity.getId());
      }
    };
    this.__preparedStmtOfUpdatePlaybackPosition = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE media_items SET lastPositionMs = ?, isWatched = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM media_items";
        return _query;
      }
    };
  }

  @Override
  public long insert(final MediaItem item) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfMediaItem.insertAndReturnId(item);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<MediaItem> items) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfMediaItem.insert(items);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final MediaItem item) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfMediaItem.handle(item);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updatePlaybackPosition(final long id, final long positionMs,
      final boolean isWatched) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePlaybackPosition.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, positionMs);
    _argIndex = 2;
    final int _tmp = isWatched ? 1 : 0;
    _stmt.bindLong(_argIndex, _tmp);
    _argIndex = 3;
    _stmt.bindLong(_argIndex, id);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdatePlaybackPosition.release(_stmt);
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public LiveData<List<MediaItem>> getAllMedia() {
    final String _sql = "SELECT * FROM media_items ORDER BY addedTimestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"media_items"}, false, new Callable<List<MediaItem>>() {
      @Override
      @Nullable
      public List<MediaItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUriString = CursorUtil.getColumnIndexOrThrow(_cursor, "uriString");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSeasonNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "seasonNumber");
          final int _cursorIndexOfEpisodeNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeNumber");
          final int _cursorIndexOfPosterPath = CursorUtil.getColumnIndexOrThrow(_cursor, "posterPath");
          final int _cursorIndexOfBackdropPath = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropPath");
          final int _cursorIndexOfOverview = CursorUtil.getColumnIndexOrThrow(_cursor, "overview");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfGenres = CursorUtil.getColumnIndexOrThrow(_cursor, "genres");
          final int _cursorIndexOfLastPositionMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPositionMs");
          final int _cursorIndexOfIsWatched = CursorUtil.getColumnIndexOrThrow(_cursor, "isWatched");
          final int _cursorIndexOfAddedTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "addedTimestamp");
          final int _cursorIndexOfTmdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "tmdbId");
          final List<MediaItem> _result = new ArrayList<MediaItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MediaItem _item;
            _item = new MediaItem();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpUriString;
            if (_cursor.isNull(_cursorIndexOfUriString)) {
              _tmpUriString = null;
            } else {
              _tmpUriString = _cursor.getString(_cursorIndexOfUriString);
            }
            _item.setUriString(_tmpUriString);
            final String _tmpFileName;
            if (_cursor.isNull(_cursorIndexOfFileName)) {
              _tmpFileName = null;
            } else {
              _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            }
            _item.setFileName(_tmpFileName);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _item.setTitle(_tmpTitle);
            final String _tmpYear;
            if (_cursor.isNull(_cursorIndexOfYear)) {
              _tmpYear = null;
            } else {
              _tmpYear = _cursor.getString(_cursorIndexOfYear);
            }
            _item.setYear(_tmpYear);
            final String _tmpType;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmpType = null;
            } else {
              _tmpType = _cursor.getString(_cursorIndexOfType);
            }
            _item.setType(_tmpType);
            final int _tmpSeasonNumber;
            _tmpSeasonNumber = _cursor.getInt(_cursorIndexOfSeasonNumber);
            _item.setSeasonNumber(_tmpSeasonNumber);
            final int _tmpEpisodeNumber;
            _tmpEpisodeNumber = _cursor.getInt(_cursorIndexOfEpisodeNumber);
            _item.setEpisodeNumber(_tmpEpisodeNumber);
            final String _tmpPosterPath;
            if (_cursor.isNull(_cursorIndexOfPosterPath)) {
              _tmpPosterPath = null;
            } else {
              _tmpPosterPath = _cursor.getString(_cursorIndexOfPosterPath);
            }
            _item.setPosterPath(_tmpPosterPath);
            final String _tmpBackdropPath;
            if (_cursor.isNull(_cursorIndexOfBackdropPath)) {
              _tmpBackdropPath = null;
            } else {
              _tmpBackdropPath = _cursor.getString(_cursorIndexOfBackdropPath);
            }
            _item.setBackdropPath(_tmpBackdropPath);
            final String _tmpOverview;
            if (_cursor.isNull(_cursorIndexOfOverview)) {
              _tmpOverview = null;
            } else {
              _tmpOverview = _cursor.getString(_cursorIndexOfOverview);
            }
            _item.setOverview(_tmpOverview);
            final double _tmpRating;
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
            _item.setRating(_tmpRating);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            _item.setDurationMs(_tmpDurationMs);
            final String _tmpGenres;
            if (_cursor.isNull(_cursorIndexOfGenres)) {
              _tmpGenres = null;
            } else {
              _tmpGenres = _cursor.getString(_cursorIndexOfGenres);
            }
            _item.setGenres(_tmpGenres);
            final long _tmpLastPositionMs;
            _tmpLastPositionMs = _cursor.getLong(_cursorIndexOfLastPositionMs);
            _item.setLastPositionMs(_tmpLastPositionMs);
            final boolean _tmpIsWatched;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsWatched);
            _tmpIsWatched = _tmp != 0;
            _item.setWatched(_tmpIsWatched);
            final long _tmpAddedTimestamp;
            _tmpAddedTimestamp = _cursor.getLong(_cursorIndexOfAddedTimestamp);
            _item.setAddedTimestamp(_tmpAddedTimestamp);
            final int _tmpTmdbId;
            _tmpTmdbId = _cursor.getInt(_cursorIndexOfTmdbId);
            _item.setTmdbId(_tmpTmdbId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<MediaItem>> getContinueWatching() {
    final String _sql = "SELECT * FROM media_items WHERE lastPositionMs > 0 AND isWatched = 0 ORDER BY addedTimestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"media_items"}, false, new Callable<List<MediaItem>>() {
      @Override
      @Nullable
      public List<MediaItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUriString = CursorUtil.getColumnIndexOrThrow(_cursor, "uriString");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSeasonNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "seasonNumber");
          final int _cursorIndexOfEpisodeNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeNumber");
          final int _cursorIndexOfPosterPath = CursorUtil.getColumnIndexOrThrow(_cursor, "posterPath");
          final int _cursorIndexOfBackdropPath = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropPath");
          final int _cursorIndexOfOverview = CursorUtil.getColumnIndexOrThrow(_cursor, "overview");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfGenres = CursorUtil.getColumnIndexOrThrow(_cursor, "genres");
          final int _cursorIndexOfLastPositionMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPositionMs");
          final int _cursorIndexOfIsWatched = CursorUtil.getColumnIndexOrThrow(_cursor, "isWatched");
          final int _cursorIndexOfAddedTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "addedTimestamp");
          final int _cursorIndexOfTmdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "tmdbId");
          final List<MediaItem> _result = new ArrayList<MediaItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MediaItem _item;
            _item = new MediaItem();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpUriString;
            if (_cursor.isNull(_cursorIndexOfUriString)) {
              _tmpUriString = null;
            } else {
              _tmpUriString = _cursor.getString(_cursorIndexOfUriString);
            }
            _item.setUriString(_tmpUriString);
            final String _tmpFileName;
            if (_cursor.isNull(_cursorIndexOfFileName)) {
              _tmpFileName = null;
            } else {
              _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            }
            _item.setFileName(_tmpFileName);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _item.setTitle(_tmpTitle);
            final String _tmpYear;
            if (_cursor.isNull(_cursorIndexOfYear)) {
              _tmpYear = null;
            } else {
              _tmpYear = _cursor.getString(_cursorIndexOfYear);
            }
            _item.setYear(_tmpYear);
            final String _tmpType;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmpType = null;
            } else {
              _tmpType = _cursor.getString(_cursorIndexOfType);
            }
            _item.setType(_tmpType);
            final int _tmpSeasonNumber;
            _tmpSeasonNumber = _cursor.getInt(_cursorIndexOfSeasonNumber);
            _item.setSeasonNumber(_tmpSeasonNumber);
            final int _tmpEpisodeNumber;
            _tmpEpisodeNumber = _cursor.getInt(_cursorIndexOfEpisodeNumber);
            _item.setEpisodeNumber(_tmpEpisodeNumber);
            final String _tmpPosterPath;
            if (_cursor.isNull(_cursorIndexOfPosterPath)) {
              _tmpPosterPath = null;
            } else {
              _tmpPosterPath = _cursor.getString(_cursorIndexOfPosterPath);
            }
            _item.setPosterPath(_tmpPosterPath);
            final String _tmpBackdropPath;
            if (_cursor.isNull(_cursorIndexOfBackdropPath)) {
              _tmpBackdropPath = null;
            } else {
              _tmpBackdropPath = _cursor.getString(_cursorIndexOfBackdropPath);
            }
            _item.setBackdropPath(_tmpBackdropPath);
            final String _tmpOverview;
            if (_cursor.isNull(_cursorIndexOfOverview)) {
              _tmpOverview = null;
            } else {
              _tmpOverview = _cursor.getString(_cursorIndexOfOverview);
            }
            _item.setOverview(_tmpOverview);
            final double _tmpRating;
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
            _item.setRating(_tmpRating);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            _item.setDurationMs(_tmpDurationMs);
            final String _tmpGenres;
            if (_cursor.isNull(_cursorIndexOfGenres)) {
              _tmpGenres = null;
            } else {
              _tmpGenres = _cursor.getString(_cursorIndexOfGenres);
            }
            _item.setGenres(_tmpGenres);
            final long _tmpLastPositionMs;
            _tmpLastPositionMs = _cursor.getLong(_cursorIndexOfLastPositionMs);
            _item.setLastPositionMs(_tmpLastPositionMs);
            final boolean _tmpIsWatched;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsWatched);
            _tmpIsWatched = _tmp != 0;
            _item.setWatched(_tmpIsWatched);
            final long _tmpAddedTimestamp;
            _tmpAddedTimestamp = _cursor.getLong(_cursorIndexOfAddedTimestamp);
            _item.setAddedTimestamp(_tmpAddedTimestamp);
            final int _tmpTmdbId;
            _tmpTmdbId = _cursor.getInt(_cursorIndexOfTmdbId);
            _item.setTmdbId(_tmpTmdbId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<MediaItem>> getRecentlyAdded() {
    final String _sql = "SELECT * FROM media_items ORDER BY addedTimestamp DESC LIMIT 20";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"media_items"}, false, new Callable<List<MediaItem>>() {
      @Override
      @Nullable
      public List<MediaItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUriString = CursorUtil.getColumnIndexOrThrow(_cursor, "uriString");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSeasonNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "seasonNumber");
          final int _cursorIndexOfEpisodeNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeNumber");
          final int _cursorIndexOfPosterPath = CursorUtil.getColumnIndexOrThrow(_cursor, "posterPath");
          final int _cursorIndexOfBackdropPath = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropPath");
          final int _cursorIndexOfOverview = CursorUtil.getColumnIndexOrThrow(_cursor, "overview");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfGenres = CursorUtil.getColumnIndexOrThrow(_cursor, "genres");
          final int _cursorIndexOfLastPositionMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPositionMs");
          final int _cursorIndexOfIsWatched = CursorUtil.getColumnIndexOrThrow(_cursor, "isWatched");
          final int _cursorIndexOfAddedTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "addedTimestamp");
          final int _cursorIndexOfTmdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "tmdbId");
          final List<MediaItem> _result = new ArrayList<MediaItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MediaItem _item;
            _item = new MediaItem();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpUriString;
            if (_cursor.isNull(_cursorIndexOfUriString)) {
              _tmpUriString = null;
            } else {
              _tmpUriString = _cursor.getString(_cursorIndexOfUriString);
            }
            _item.setUriString(_tmpUriString);
            final String _tmpFileName;
            if (_cursor.isNull(_cursorIndexOfFileName)) {
              _tmpFileName = null;
            } else {
              _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            }
            _item.setFileName(_tmpFileName);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _item.setTitle(_tmpTitle);
            final String _tmpYear;
            if (_cursor.isNull(_cursorIndexOfYear)) {
              _tmpYear = null;
            } else {
              _tmpYear = _cursor.getString(_cursorIndexOfYear);
            }
            _item.setYear(_tmpYear);
            final String _tmpType;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmpType = null;
            } else {
              _tmpType = _cursor.getString(_cursorIndexOfType);
            }
            _item.setType(_tmpType);
            final int _tmpSeasonNumber;
            _tmpSeasonNumber = _cursor.getInt(_cursorIndexOfSeasonNumber);
            _item.setSeasonNumber(_tmpSeasonNumber);
            final int _tmpEpisodeNumber;
            _tmpEpisodeNumber = _cursor.getInt(_cursorIndexOfEpisodeNumber);
            _item.setEpisodeNumber(_tmpEpisodeNumber);
            final String _tmpPosterPath;
            if (_cursor.isNull(_cursorIndexOfPosterPath)) {
              _tmpPosterPath = null;
            } else {
              _tmpPosterPath = _cursor.getString(_cursorIndexOfPosterPath);
            }
            _item.setPosterPath(_tmpPosterPath);
            final String _tmpBackdropPath;
            if (_cursor.isNull(_cursorIndexOfBackdropPath)) {
              _tmpBackdropPath = null;
            } else {
              _tmpBackdropPath = _cursor.getString(_cursorIndexOfBackdropPath);
            }
            _item.setBackdropPath(_tmpBackdropPath);
            final String _tmpOverview;
            if (_cursor.isNull(_cursorIndexOfOverview)) {
              _tmpOverview = null;
            } else {
              _tmpOverview = _cursor.getString(_cursorIndexOfOverview);
            }
            _item.setOverview(_tmpOverview);
            final double _tmpRating;
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
            _item.setRating(_tmpRating);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            _item.setDurationMs(_tmpDurationMs);
            final String _tmpGenres;
            if (_cursor.isNull(_cursorIndexOfGenres)) {
              _tmpGenres = null;
            } else {
              _tmpGenres = _cursor.getString(_cursorIndexOfGenres);
            }
            _item.setGenres(_tmpGenres);
            final long _tmpLastPositionMs;
            _tmpLastPositionMs = _cursor.getLong(_cursorIndexOfLastPositionMs);
            _item.setLastPositionMs(_tmpLastPositionMs);
            final boolean _tmpIsWatched;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsWatched);
            _tmpIsWatched = _tmp != 0;
            _item.setWatched(_tmpIsWatched);
            final long _tmpAddedTimestamp;
            _tmpAddedTimestamp = _cursor.getLong(_cursorIndexOfAddedTimestamp);
            _item.setAddedTimestamp(_tmpAddedTimestamp);
            final int _tmpTmdbId;
            _tmpTmdbId = _cursor.getInt(_cursorIndexOfTmdbId);
            _item.setTmdbId(_tmpTmdbId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<MediaItem>> getMovies() {
    final String _sql = "SELECT * FROM media_items WHERE type = 'MOVIE' ORDER BY addedTimestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"media_items"}, false, new Callable<List<MediaItem>>() {
      @Override
      @Nullable
      public List<MediaItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUriString = CursorUtil.getColumnIndexOrThrow(_cursor, "uriString");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSeasonNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "seasonNumber");
          final int _cursorIndexOfEpisodeNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeNumber");
          final int _cursorIndexOfPosterPath = CursorUtil.getColumnIndexOrThrow(_cursor, "posterPath");
          final int _cursorIndexOfBackdropPath = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropPath");
          final int _cursorIndexOfOverview = CursorUtil.getColumnIndexOrThrow(_cursor, "overview");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfGenres = CursorUtil.getColumnIndexOrThrow(_cursor, "genres");
          final int _cursorIndexOfLastPositionMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPositionMs");
          final int _cursorIndexOfIsWatched = CursorUtil.getColumnIndexOrThrow(_cursor, "isWatched");
          final int _cursorIndexOfAddedTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "addedTimestamp");
          final int _cursorIndexOfTmdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "tmdbId");
          final List<MediaItem> _result = new ArrayList<MediaItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MediaItem _item;
            _item = new MediaItem();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpUriString;
            if (_cursor.isNull(_cursorIndexOfUriString)) {
              _tmpUriString = null;
            } else {
              _tmpUriString = _cursor.getString(_cursorIndexOfUriString);
            }
            _item.setUriString(_tmpUriString);
            final String _tmpFileName;
            if (_cursor.isNull(_cursorIndexOfFileName)) {
              _tmpFileName = null;
            } else {
              _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            }
            _item.setFileName(_tmpFileName);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _item.setTitle(_tmpTitle);
            final String _tmpYear;
            if (_cursor.isNull(_cursorIndexOfYear)) {
              _tmpYear = null;
            } else {
              _tmpYear = _cursor.getString(_cursorIndexOfYear);
            }
            _item.setYear(_tmpYear);
            final String _tmpType;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmpType = null;
            } else {
              _tmpType = _cursor.getString(_cursorIndexOfType);
            }
            _item.setType(_tmpType);
            final int _tmpSeasonNumber;
            _tmpSeasonNumber = _cursor.getInt(_cursorIndexOfSeasonNumber);
            _item.setSeasonNumber(_tmpSeasonNumber);
            final int _tmpEpisodeNumber;
            _tmpEpisodeNumber = _cursor.getInt(_cursorIndexOfEpisodeNumber);
            _item.setEpisodeNumber(_tmpEpisodeNumber);
            final String _tmpPosterPath;
            if (_cursor.isNull(_cursorIndexOfPosterPath)) {
              _tmpPosterPath = null;
            } else {
              _tmpPosterPath = _cursor.getString(_cursorIndexOfPosterPath);
            }
            _item.setPosterPath(_tmpPosterPath);
            final String _tmpBackdropPath;
            if (_cursor.isNull(_cursorIndexOfBackdropPath)) {
              _tmpBackdropPath = null;
            } else {
              _tmpBackdropPath = _cursor.getString(_cursorIndexOfBackdropPath);
            }
            _item.setBackdropPath(_tmpBackdropPath);
            final String _tmpOverview;
            if (_cursor.isNull(_cursorIndexOfOverview)) {
              _tmpOverview = null;
            } else {
              _tmpOverview = _cursor.getString(_cursorIndexOfOverview);
            }
            _item.setOverview(_tmpOverview);
            final double _tmpRating;
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
            _item.setRating(_tmpRating);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            _item.setDurationMs(_tmpDurationMs);
            final String _tmpGenres;
            if (_cursor.isNull(_cursorIndexOfGenres)) {
              _tmpGenres = null;
            } else {
              _tmpGenres = _cursor.getString(_cursorIndexOfGenres);
            }
            _item.setGenres(_tmpGenres);
            final long _tmpLastPositionMs;
            _tmpLastPositionMs = _cursor.getLong(_cursorIndexOfLastPositionMs);
            _item.setLastPositionMs(_tmpLastPositionMs);
            final boolean _tmpIsWatched;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsWatched);
            _tmpIsWatched = _tmp != 0;
            _item.setWatched(_tmpIsWatched);
            final long _tmpAddedTimestamp;
            _tmpAddedTimestamp = _cursor.getLong(_cursorIndexOfAddedTimestamp);
            _item.setAddedTimestamp(_tmpAddedTimestamp);
            final int _tmpTmdbId;
            _tmpTmdbId = _cursor.getInt(_cursorIndexOfTmdbId);
            _item.setTmdbId(_tmpTmdbId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<MediaItem>> getTvShows() {
    final String _sql = "SELECT * FROM media_items WHERE type = 'TV_SHOW' GROUP BY title ORDER BY addedTimestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"media_items"}, false, new Callable<List<MediaItem>>() {
      @Override
      @Nullable
      public List<MediaItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUriString = CursorUtil.getColumnIndexOrThrow(_cursor, "uriString");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSeasonNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "seasonNumber");
          final int _cursorIndexOfEpisodeNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeNumber");
          final int _cursorIndexOfPosterPath = CursorUtil.getColumnIndexOrThrow(_cursor, "posterPath");
          final int _cursorIndexOfBackdropPath = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropPath");
          final int _cursorIndexOfOverview = CursorUtil.getColumnIndexOrThrow(_cursor, "overview");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfGenres = CursorUtil.getColumnIndexOrThrow(_cursor, "genres");
          final int _cursorIndexOfLastPositionMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPositionMs");
          final int _cursorIndexOfIsWatched = CursorUtil.getColumnIndexOrThrow(_cursor, "isWatched");
          final int _cursorIndexOfAddedTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "addedTimestamp");
          final int _cursorIndexOfTmdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "tmdbId");
          final List<MediaItem> _result = new ArrayList<MediaItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MediaItem _item;
            _item = new MediaItem();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpUriString;
            if (_cursor.isNull(_cursorIndexOfUriString)) {
              _tmpUriString = null;
            } else {
              _tmpUriString = _cursor.getString(_cursorIndexOfUriString);
            }
            _item.setUriString(_tmpUriString);
            final String _tmpFileName;
            if (_cursor.isNull(_cursorIndexOfFileName)) {
              _tmpFileName = null;
            } else {
              _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            }
            _item.setFileName(_tmpFileName);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _item.setTitle(_tmpTitle);
            final String _tmpYear;
            if (_cursor.isNull(_cursorIndexOfYear)) {
              _tmpYear = null;
            } else {
              _tmpYear = _cursor.getString(_cursorIndexOfYear);
            }
            _item.setYear(_tmpYear);
            final String _tmpType;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmpType = null;
            } else {
              _tmpType = _cursor.getString(_cursorIndexOfType);
            }
            _item.setType(_tmpType);
            final int _tmpSeasonNumber;
            _tmpSeasonNumber = _cursor.getInt(_cursorIndexOfSeasonNumber);
            _item.setSeasonNumber(_tmpSeasonNumber);
            final int _tmpEpisodeNumber;
            _tmpEpisodeNumber = _cursor.getInt(_cursorIndexOfEpisodeNumber);
            _item.setEpisodeNumber(_tmpEpisodeNumber);
            final String _tmpPosterPath;
            if (_cursor.isNull(_cursorIndexOfPosterPath)) {
              _tmpPosterPath = null;
            } else {
              _tmpPosterPath = _cursor.getString(_cursorIndexOfPosterPath);
            }
            _item.setPosterPath(_tmpPosterPath);
            final String _tmpBackdropPath;
            if (_cursor.isNull(_cursorIndexOfBackdropPath)) {
              _tmpBackdropPath = null;
            } else {
              _tmpBackdropPath = _cursor.getString(_cursorIndexOfBackdropPath);
            }
            _item.setBackdropPath(_tmpBackdropPath);
            final String _tmpOverview;
            if (_cursor.isNull(_cursorIndexOfOverview)) {
              _tmpOverview = null;
            } else {
              _tmpOverview = _cursor.getString(_cursorIndexOfOverview);
            }
            _item.setOverview(_tmpOverview);
            final double _tmpRating;
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
            _item.setRating(_tmpRating);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            _item.setDurationMs(_tmpDurationMs);
            final String _tmpGenres;
            if (_cursor.isNull(_cursorIndexOfGenres)) {
              _tmpGenres = null;
            } else {
              _tmpGenres = _cursor.getString(_cursorIndexOfGenres);
            }
            _item.setGenres(_tmpGenres);
            final long _tmpLastPositionMs;
            _tmpLastPositionMs = _cursor.getLong(_cursorIndexOfLastPositionMs);
            _item.setLastPositionMs(_tmpLastPositionMs);
            final boolean _tmpIsWatched;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsWatched);
            _tmpIsWatched = _tmp != 0;
            _item.setWatched(_tmpIsWatched);
            final long _tmpAddedTimestamp;
            _tmpAddedTimestamp = _cursor.getLong(_cursorIndexOfAddedTimestamp);
            _item.setAddedTimestamp(_tmpAddedTimestamp);
            final int _tmpTmdbId;
            _tmpTmdbId = _cursor.getInt(_cursorIndexOfTmdbId);
            _item.setTmdbId(_tmpTmdbId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<MediaItem>> getEpisodesForShow(final String title, final int seasonNumber) {
    final String _sql = "SELECT * FROM media_items WHERE title = ? AND seasonNumber = ? AND type = 'TV_SHOW' ORDER BY episodeNumber ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (title == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, title);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, seasonNumber);
    return __db.getInvalidationTracker().createLiveData(new String[] {"media_items"}, false, new Callable<List<MediaItem>>() {
      @Override
      @Nullable
      public List<MediaItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUriString = CursorUtil.getColumnIndexOrThrow(_cursor, "uriString");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSeasonNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "seasonNumber");
          final int _cursorIndexOfEpisodeNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeNumber");
          final int _cursorIndexOfPosterPath = CursorUtil.getColumnIndexOrThrow(_cursor, "posterPath");
          final int _cursorIndexOfBackdropPath = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropPath");
          final int _cursorIndexOfOverview = CursorUtil.getColumnIndexOrThrow(_cursor, "overview");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfGenres = CursorUtil.getColumnIndexOrThrow(_cursor, "genres");
          final int _cursorIndexOfLastPositionMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPositionMs");
          final int _cursorIndexOfIsWatched = CursorUtil.getColumnIndexOrThrow(_cursor, "isWatched");
          final int _cursorIndexOfAddedTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "addedTimestamp");
          final int _cursorIndexOfTmdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "tmdbId");
          final List<MediaItem> _result = new ArrayList<MediaItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MediaItem _item;
            _item = new MediaItem();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpUriString;
            if (_cursor.isNull(_cursorIndexOfUriString)) {
              _tmpUriString = null;
            } else {
              _tmpUriString = _cursor.getString(_cursorIndexOfUriString);
            }
            _item.setUriString(_tmpUriString);
            final String _tmpFileName;
            if (_cursor.isNull(_cursorIndexOfFileName)) {
              _tmpFileName = null;
            } else {
              _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            }
            _item.setFileName(_tmpFileName);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _item.setTitle(_tmpTitle);
            final String _tmpYear;
            if (_cursor.isNull(_cursorIndexOfYear)) {
              _tmpYear = null;
            } else {
              _tmpYear = _cursor.getString(_cursorIndexOfYear);
            }
            _item.setYear(_tmpYear);
            final String _tmpType;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmpType = null;
            } else {
              _tmpType = _cursor.getString(_cursorIndexOfType);
            }
            _item.setType(_tmpType);
            final int _tmpSeasonNumber;
            _tmpSeasonNumber = _cursor.getInt(_cursorIndexOfSeasonNumber);
            _item.setSeasonNumber(_tmpSeasonNumber);
            final int _tmpEpisodeNumber;
            _tmpEpisodeNumber = _cursor.getInt(_cursorIndexOfEpisodeNumber);
            _item.setEpisodeNumber(_tmpEpisodeNumber);
            final String _tmpPosterPath;
            if (_cursor.isNull(_cursorIndexOfPosterPath)) {
              _tmpPosterPath = null;
            } else {
              _tmpPosterPath = _cursor.getString(_cursorIndexOfPosterPath);
            }
            _item.setPosterPath(_tmpPosterPath);
            final String _tmpBackdropPath;
            if (_cursor.isNull(_cursorIndexOfBackdropPath)) {
              _tmpBackdropPath = null;
            } else {
              _tmpBackdropPath = _cursor.getString(_cursorIndexOfBackdropPath);
            }
            _item.setBackdropPath(_tmpBackdropPath);
            final String _tmpOverview;
            if (_cursor.isNull(_cursorIndexOfOverview)) {
              _tmpOverview = null;
            } else {
              _tmpOverview = _cursor.getString(_cursorIndexOfOverview);
            }
            _item.setOverview(_tmpOverview);
            final double _tmpRating;
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
            _item.setRating(_tmpRating);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            _item.setDurationMs(_tmpDurationMs);
            final String _tmpGenres;
            if (_cursor.isNull(_cursorIndexOfGenres)) {
              _tmpGenres = null;
            } else {
              _tmpGenres = _cursor.getString(_cursorIndexOfGenres);
            }
            _item.setGenres(_tmpGenres);
            final long _tmpLastPositionMs;
            _tmpLastPositionMs = _cursor.getLong(_cursorIndexOfLastPositionMs);
            _item.setLastPositionMs(_tmpLastPositionMs);
            final boolean _tmpIsWatched;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsWatched);
            _tmpIsWatched = _tmp != 0;
            _item.setWatched(_tmpIsWatched);
            final long _tmpAddedTimestamp;
            _tmpAddedTimestamp = _cursor.getLong(_cursorIndexOfAddedTimestamp);
            _item.setAddedTimestamp(_tmpAddedTimestamp);
            final int _tmpTmdbId;
            _tmpTmdbId = _cursor.getInt(_cursorIndexOfTmdbId);
            _item.setTmdbId(_tmpTmdbId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public MediaItem getMediaByUri(final String uriString) {
    final String _sql = "SELECT * FROM media_items WHERE uriString = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (uriString == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, uriString);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUriString = CursorUtil.getColumnIndexOrThrow(_cursor, "uriString");
      final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfSeasonNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "seasonNumber");
      final int _cursorIndexOfEpisodeNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeNumber");
      final int _cursorIndexOfPosterPath = CursorUtil.getColumnIndexOrThrow(_cursor, "posterPath");
      final int _cursorIndexOfBackdropPath = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropPath");
      final int _cursorIndexOfOverview = CursorUtil.getColumnIndexOrThrow(_cursor, "overview");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
      final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
      final int _cursorIndexOfGenres = CursorUtil.getColumnIndexOrThrow(_cursor, "genres");
      final int _cursorIndexOfLastPositionMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPositionMs");
      final int _cursorIndexOfIsWatched = CursorUtil.getColumnIndexOrThrow(_cursor, "isWatched");
      final int _cursorIndexOfAddedTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "addedTimestamp");
      final int _cursorIndexOfTmdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "tmdbId");
      final MediaItem _result;
      if (_cursor.moveToFirst()) {
        _result = new MediaItem();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpUriString;
        if (_cursor.isNull(_cursorIndexOfUriString)) {
          _tmpUriString = null;
        } else {
          _tmpUriString = _cursor.getString(_cursorIndexOfUriString);
        }
        _result.setUriString(_tmpUriString);
        final String _tmpFileName;
        if (_cursor.isNull(_cursorIndexOfFileName)) {
          _tmpFileName = null;
        } else {
          _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
        }
        _result.setFileName(_tmpFileName);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        _result.setTitle(_tmpTitle);
        final String _tmpYear;
        if (_cursor.isNull(_cursorIndexOfYear)) {
          _tmpYear = null;
        } else {
          _tmpYear = _cursor.getString(_cursorIndexOfYear);
        }
        _result.setYear(_tmpYear);
        final String _tmpType;
        if (_cursor.isNull(_cursorIndexOfType)) {
          _tmpType = null;
        } else {
          _tmpType = _cursor.getString(_cursorIndexOfType);
        }
        _result.setType(_tmpType);
        final int _tmpSeasonNumber;
        _tmpSeasonNumber = _cursor.getInt(_cursorIndexOfSeasonNumber);
        _result.setSeasonNumber(_tmpSeasonNumber);
        final int _tmpEpisodeNumber;
        _tmpEpisodeNumber = _cursor.getInt(_cursorIndexOfEpisodeNumber);
        _result.setEpisodeNumber(_tmpEpisodeNumber);
        final String _tmpPosterPath;
        if (_cursor.isNull(_cursorIndexOfPosterPath)) {
          _tmpPosterPath = null;
        } else {
          _tmpPosterPath = _cursor.getString(_cursorIndexOfPosterPath);
        }
        _result.setPosterPath(_tmpPosterPath);
        final String _tmpBackdropPath;
        if (_cursor.isNull(_cursorIndexOfBackdropPath)) {
          _tmpBackdropPath = null;
        } else {
          _tmpBackdropPath = _cursor.getString(_cursorIndexOfBackdropPath);
        }
        _result.setBackdropPath(_tmpBackdropPath);
        final String _tmpOverview;
        if (_cursor.isNull(_cursorIndexOfOverview)) {
          _tmpOverview = null;
        } else {
          _tmpOverview = _cursor.getString(_cursorIndexOfOverview);
        }
        _result.setOverview(_tmpOverview);
        final double _tmpRating;
        _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
        _result.setRating(_tmpRating);
        final long _tmpDurationMs;
        _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
        _result.setDurationMs(_tmpDurationMs);
        final String _tmpGenres;
        if (_cursor.isNull(_cursorIndexOfGenres)) {
          _tmpGenres = null;
        } else {
          _tmpGenres = _cursor.getString(_cursorIndexOfGenres);
        }
        _result.setGenres(_tmpGenres);
        final long _tmpLastPositionMs;
        _tmpLastPositionMs = _cursor.getLong(_cursorIndexOfLastPositionMs);
        _result.setLastPositionMs(_tmpLastPositionMs);
        final boolean _tmpIsWatched;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsWatched);
        _tmpIsWatched = _tmp != 0;
        _result.setWatched(_tmpIsWatched);
        final long _tmpAddedTimestamp;
        _tmpAddedTimestamp = _cursor.getLong(_cursorIndexOfAddedTimestamp);
        _result.setAddedTimestamp(_tmpAddedTimestamp);
        final int _tmpTmdbId;
        _tmpTmdbId = _cursor.getInt(_cursorIndexOfTmdbId);
        _result.setTmdbId(_tmpTmdbId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<MediaItem> getMediaById(final long id) {
    final String _sql = "SELECT * FROM media_items WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"media_items"}, false, new Callable<MediaItem>() {
      @Override
      @Nullable
      public MediaItem call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUriString = CursorUtil.getColumnIndexOrThrow(_cursor, "uriString");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSeasonNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "seasonNumber");
          final int _cursorIndexOfEpisodeNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeNumber");
          final int _cursorIndexOfPosterPath = CursorUtil.getColumnIndexOrThrow(_cursor, "posterPath");
          final int _cursorIndexOfBackdropPath = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropPath");
          final int _cursorIndexOfOverview = CursorUtil.getColumnIndexOrThrow(_cursor, "overview");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfGenres = CursorUtil.getColumnIndexOrThrow(_cursor, "genres");
          final int _cursorIndexOfLastPositionMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPositionMs");
          final int _cursorIndexOfIsWatched = CursorUtil.getColumnIndexOrThrow(_cursor, "isWatched");
          final int _cursorIndexOfAddedTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "addedTimestamp");
          final int _cursorIndexOfTmdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "tmdbId");
          final MediaItem _result;
          if (_cursor.moveToFirst()) {
            _result = new MediaItem();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _result.setId(_tmpId);
            final String _tmpUriString;
            if (_cursor.isNull(_cursorIndexOfUriString)) {
              _tmpUriString = null;
            } else {
              _tmpUriString = _cursor.getString(_cursorIndexOfUriString);
            }
            _result.setUriString(_tmpUriString);
            final String _tmpFileName;
            if (_cursor.isNull(_cursorIndexOfFileName)) {
              _tmpFileName = null;
            } else {
              _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            }
            _result.setFileName(_tmpFileName);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _result.setTitle(_tmpTitle);
            final String _tmpYear;
            if (_cursor.isNull(_cursorIndexOfYear)) {
              _tmpYear = null;
            } else {
              _tmpYear = _cursor.getString(_cursorIndexOfYear);
            }
            _result.setYear(_tmpYear);
            final String _tmpType;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmpType = null;
            } else {
              _tmpType = _cursor.getString(_cursorIndexOfType);
            }
            _result.setType(_tmpType);
            final int _tmpSeasonNumber;
            _tmpSeasonNumber = _cursor.getInt(_cursorIndexOfSeasonNumber);
            _result.setSeasonNumber(_tmpSeasonNumber);
            final int _tmpEpisodeNumber;
            _tmpEpisodeNumber = _cursor.getInt(_cursorIndexOfEpisodeNumber);
            _result.setEpisodeNumber(_tmpEpisodeNumber);
            final String _tmpPosterPath;
            if (_cursor.isNull(_cursorIndexOfPosterPath)) {
              _tmpPosterPath = null;
            } else {
              _tmpPosterPath = _cursor.getString(_cursorIndexOfPosterPath);
            }
            _result.setPosterPath(_tmpPosterPath);
            final String _tmpBackdropPath;
            if (_cursor.isNull(_cursorIndexOfBackdropPath)) {
              _tmpBackdropPath = null;
            } else {
              _tmpBackdropPath = _cursor.getString(_cursorIndexOfBackdropPath);
            }
            _result.setBackdropPath(_tmpBackdropPath);
            final String _tmpOverview;
            if (_cursor.isNull(_cursorIndexOfOverview)) {
              _tmpOverview = null;
            } else {
              _tmpOverview = _cursor.getString(_cursorIndexOfOverview);
            }
            _result.setOverview(_tmpOverview);
            final double _tmpRating;
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
            _result.setRating(_tmpRating);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            _result.setDurationMs(_tmpDurationMs);
            final String _tmpGenres;
            if (_cursor.isNull(_cursorIndexOfGenres)) {
              _tmpGenres = null;
            } else {
              _tmpGenres = _cursor.getString(_cursorIndexOfGenres);
            }
            _result.setGenres(_tmpGenres);
            final long _tmpLastPositionMs;
            _tmpLastPositionMs = _cursor.getLong(_cursorIndexOfLastPositionMs);
            _result.setLastPositionMs(_tmpLastPositionMs);
            final boolean _tmpIsWatched;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsWatched);
            _tmpIsWatched = _tmp != 0;
            _result.setWatched(_tmpIsWatched);
            final long _tmpAddedTimestamp;
            _tmpAddedTimestamp = _cursor.getLong(_cursorIndexOfAddedTimestamp);
            _result.setAddedTimestamp(_tmpAddedTimestamp);
            final int _tmpTmdbId;
            _tmpTmdbId = _cursor.getInt(_cursorIndexOfTmdbId);
            _result.setTmdbId(_tmpTmdbId);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public MediaItem getMediaByIdSync(final long id) {
    final String _sql = "SELECT * FROM media_items WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfUriString = CursorUtil.getColumnIndexOrThrow(_cursor, "uriString");
      final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfSeasonNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "seasonNumber");
      final int _cursorIndexOfEpisodeNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeNumber");
      final int _cursorIndexOfPosterPath = CursorUtil.getColumnIndexOrThrow(_cursor, "posterPath");
      final int _cursorIndexOfBackdropPath = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropPath");
      final int _cursorIndexOfOverview = CursorUtil.getColumnIndexOrThrow(_cursor, "overview");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
      final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
      final int _cursorIndexOfGenres = CursorUtil.getColumnIndexOrThrow(_cursor, "genres");
      final int _cursorIndexOfLastPositionMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPositionMs");
      final int _cursorIndexOfIsWatched = CursorUtil.getColumnIndexOrThrow(_cursor, "isWatched");
      final int _cursorIndexOfAddedTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "addedTimestamp");
      final int _cursorIndexOfTmdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "tmdbId");
      final MediaItem _result;
      if (_cursor.moveToFirst()) {
        _result = new MediaItem();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpUriString;
        if (_cursor.isNull(_cursorIndexOfUriString)) {
          _tmpUriString = null;
        } else {
          _tmpUriString = _cursor.getString(_cursorIndexOfUriString);
        }
        _result.setUriString(_tmpUriString);
        final String _tmpFileName;
        if (_cursor.isNull(_cursorIndexOfFileName)) {
          _tmpFileName = null;
        } else {
          _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
        }
        _result.setFileName(_tmpFileName);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        _result.setTitle(_tmpTitle);
        final String _tmpYear;
        if (_cursor.isNull(_cursorIndexOfYear)) {
          _tmpYear = null;
        } else {
          _tmpYear = _cursor.getString(_cursorIndexOfYear);
        }
        _result.setYear(_tmpYear);
        final String _tmpType;
        if (_cursor.isNull(_cursorIndexOfType)) {
          _tmpType = null;
        } else {
          _tmpType = _cursor.getString(_cursorIndexOfType);
        }
        _result.setType(_tmpType);
        final int _tmpSeasonNumber;
        _tmpSeasonNumber = _cursor.getInt(_cursorIndexOfSeasonNumber);
        _result.setSeasonNumber(_tmpSeasonNumber);
        final int _tmpEpisodeNumber;
        _tmpEpisodeNumber = _cursor.getInt(_cursorIndexOfEpisodeNumber);
        _result.setEpisodeNumber(_tmpEpisodeNumber);
        final String _tmpPosterPath;
        if (_cursor.isNull(_cursorIndexOfPosterPath)) {
          _tmpPosterPath = null;
        } else {
          _tmpPosterPath = _cursor.getString(_cursorIndexOfPosterPath);
        }
        _result.setPosterPath(_tmpPosterPath);
        final String _tmpBackdropPath;
        if (_cursor.isNull(_cursorIndexOfBackdropPath)) {
          _tmpBackdropPath = null;
        } else {
          _tmpBackdropPath = _cursor.getString(_cursorIndexOfBackdropPath);
        }
        _result.setBackdropPath(_tmpBackdropPath);
        final String _tmpOverview;
        if (_cursor.isNull(_cursorIndexOfOverview)) {
          _tmpOverview = null;
        } else {
          _tmpOverview = _cursor.getString(_cursorIndexOfOverview);
        }
        _result.setOverview(_tmpOverview);
        final double _tmpRating;
        _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
        _result.setRating(_tmpRating);
        final long _tmpDurationMs;
        _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
        _result.setDurationMs(_tmpDurationMs);
        final String _tmpGenres;
        if (_cursor.isNull(_cursorIndexOfGenres)) {
          _tmpGenres = null;
        } else {
          _tmpGenres = _cursor.getString(_cursorIndexOfGenres);
        }
        _result.setGenres(_tmpGenres);
        final long _tmpLastPositionMs;
        _tmpLastPositionMs = _cursor.getLong(_cursorIndexOfLastPositionMs);
        _result.setLastPositionMs(_tmpLastPositionMs);
        final boolean _tmpIsWatched;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsWatched);
        _tmpIsWatched = _tmp != 0;
        _result.setWatched(_tmpIsWatched);
        final long _tmpAddedTimestamp;
        _tmpAddedTimestamp = _cursor.getLong(_cursorIndexOfAddedTimestamp);
        _result.setAddedTimestamp(_tmpAddedTimestamp);
        final int _tmpTmdbId;
        _tmpTmdbId = _cursor.getInt(_cursorIndexOfTmdbId);
        _result.setTmdbId(_tmpTmdbId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
