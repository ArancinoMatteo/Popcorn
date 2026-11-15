package com.popcorn.media.data.scanner;

import android.content.Context;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.database.Cursor;

import androidx.documentfile.provider.DocumentFile;

import com.popcorn.media.data.local.AppDatabase;
import com.popcorn.media.data.local.entity.MediaItem;
import com.popcorn.media.data.remote.TmdbApiClient;
import com.popcorn.media.data.remote.TmdbApiService;
import com.popcorn.media.data.remote.model.TmdbMovieResult;
import com.popcorn.media.data.remote.model.TmdbSearchMovieResponse;
import com.popcorn.media.data.remote.model.TmdbSearchTvResponse;
import com.popcorn.media.data.remote.model.TmdbTvResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Response;

public class MediaScanner {

    private static final List<String> VIDEO_EXTENSIONS = Arrays.asList(
            "mp4", "mkv", "avi", "mov", "webm", "m4v", "ts", "3gp", "flv"
    );

    public interface ScanCallback {
        void onScanProgress(int current, int total, String currentFile);
        void onScanComplete(int count);
    }

    public static void scanFolderUri(Context context, Uri folderUri, ScanCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            DocumentFile root = DocumentFile.fromTreeUri(context, folderUri);
            if (root == null || !root.exists()) {
                if (callback != null) callback.onScanComplete(0);
                return;
            }

            List<DocumentFile> videoFiles = new ArrayList<>();
            findVideoFiles(root, videoFiles);

            int total = videoFiles.size();
            int current = 0;

            for (DocumentFile file : videoFiles) {
                current++;
                String fileName = file.getName();
                if (fileName == null) continue;

                if (callback != null) {
                    callback.onScanProgress(current, total, fileName);
                }

                String uriStr = file.getUri().toString();

                // Skip if already in database
                MediaItem existing = AppDatabase.getInstance(context).mediaDao().getMediaByUri(uriStr);
                if (existing != null) {
                    continue;
                }

                MediaItem item = FilenameParser.parseFilename(fileName, uriStr);
                enrichMetadataFromTmdb(context, item);

                AppDatabase.getInstance(context).mediaDao().insert(item);
            }

            if (callback != null) {
                callback.onScanComplete(current);
            }
        });
    }

    public static void scanSingleFile(Context context, Uri fileUri, ScanCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            String fileName = getFileNameFromUri(context, fileUri);
            if (fileName == null) fileName = "Video_" + System.currentTimeMillis() + ".mp4";

            MediaItem item = FilenameParser.parseFilename(fileName, fileUri.toString());
            enrichMetadataFromTmdb(context, item);

            AppDatabase.getInstance(context).mediaDao().insert(item);

            if (callback != null) {
                callback.onScanComplete(1);
            }
        });
    }

    private static void findVideoFiles(DocumentFile directory, List<DocumentFile> outList) {
        DocumentFile[] files = directory.listFiles();
        for (DocumentFile file : files) {
            if (file.isDirectory()) {
                findVideoFiles(file, outList);
            } else if (file.isFile()) {
                String name = file.getName();
                if (name != null && isVideoFile(name)) {
                    outList.add(file);
                }
            }
        }
    }

    private static boolean isVideoFile(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            String ext = fileName.substring(dotIndex + 1).toLowerCase();
            return VIDEO_EXTENSIONS.contains(ext);
        }
        return false;
    }

    private static void enrichMetadataFromTmdb(Context context, MediaItem item) {
        String apiKey = TmdbApiClient.getApiKey(context);
        TmdbApiService apiService = TmdbApiClient.getService();

        try {
            if ("TV_SHOW".equals(item.getType())) {
                Response<TmdbSearchTvResponse> response = apiService.searchTv(
                        apiKey, item.getTitle(), item.getYear(), "it-IT"
                ).execute();

                if (response.isSuccessful() && response.body() != null && !response.body().getResults().isEmpty()) {
                    TmdbTvResult result = response.body().getResults().get(0);
                    item.setTmdbId(result.getId());
                    item.setTitle(result.getName());
                    item.setOverview(result.getOverview());
                    item.setPosterPath(result.getPosterPath());
                    item.setBackdropPath(result.getBackdropPath());
                    item.setRating(result.getVoteAverage());
                }
            } else {
                Response<TmdbSearchMovieResponse> response = apiService.searchMovie(
                        apiKey, item.getTitle(), item.getYear(), "it-IT"
                ).execute();

                if (response.isSuccessful() && response.body() != null && !response.body().getResults().isEmpty()) {
                    TmdbMovieResult result = response.body().getResults().get(0);
                    item.setTmdbId(result.getId());
                    item.setTitle(result.getTitle());
                    item.setOverview(result.getOverview());
                    item.setPosterPath(result.getPosterPath());
                    item.setBackdropPath(result.getBackdropPath());
                    item.setRating(result.getVoteAverage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFileNameFromUri(Context context, Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index >= 0) {
                        result = cursor.getString(index);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            if (result != null) {
                int cut = result.lastIndexOf('/');
                if (cut != -1) {
                    result = result.substring(cut + 1);
                }
            }
        }
        return result;
    }
}
