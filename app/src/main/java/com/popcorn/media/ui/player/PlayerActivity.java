package com.popcorn.media.ui.player;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.popcorn.media.R;
import com.popcorn.media.data.local.AppDatabase;
import com.popcorn.media.data.local.entity.MediaItem;

public class PlayerActivity extends AppCompatActivity {

    public static final String EXTRA_MEDIA_ID = "extra_media_id";

    private PlayerView playerView;
    private ExoPlayer player;
    private long mediaId = -1;
    private com.popcorn.media.data.local.entity.MediaItem currentMediaItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Keep screen on during playback & fullscreen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_player);

        mediaId = getIntent().getLongExtra(EXTRA_MEDIA_ID, -1);
        playerView = findViewById(R.id.player_view);

        ImageButton btnBack = findViewById(R.id.btn_player_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        loadMediaAndStartPlayer();
    }

    private void loadMediaAndStartPlayer() {
        if (mediaId == -1) {
            Toast.makeText(this, "Errore: Video non trovato", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        AppDatabase.databaseWriteExecutor.execute(() -> {
            com.popcorn.media.data.local.entity.MediaItem item =
                    AppDatabase.getInstance(this).mediaDao().getMediaByIdSync(mediaId);

            if (item == null) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Errore caricamento media", Toast.LENGTH_SHORT).show();
                    finish();
                });
                return;
            }

            this.currentMediaItem = item;

            runOnUiThread(() -> {
                TextView tvTitle = findViewById(R.id.tv_player_title);
                if (tvTitle != null) {
                    tvTitle.setText(item.getTitle() != null ? item.getTitle() : item.getFileName());
                }
                initializePlayer(item);
            });
        });
    }

    private void initializePlayer(com.popcorn.media.data.local.entity.MediaItem item) {
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        androidx.media3.common.MediaItem mediaItem = androidx.media3.common.MediaItem.fromUri(Uri.parse(item.getUriString()));
        player.setMediaItem(mediaItem);

        // Resume position if available
        if (item.getLastPositionMs() > 0) {
            player.seekTo(item.getLastPositionMs());
        }

        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == Player.STATE_READY && player.getDuration() > 0) {
                    currentMediaItem.setDurationMs(player.getDuration());
                } else if (playbackState == Player.STATE_ENDED) {
                    saveProgress(true);
                }
            }
        });

        player.setPlayWhenReady(true);
        player.prepare();
    }

    private void saveProgress(boolean isEnded) {
        if (player == null || currentMediaItem == null) return;

        long currentPos = player.getCurrentPosition();
        long duration = player.getDuration();
        boolean isWatched = isEnded || (duration > 0 && currentPos >= duration * 0.95);

        AppDatabase.databaseWriteExecutor.execute(() -> {
            AppDatabase.getInstance(getApplicationContext()).mediaDao()
                    .updatePlaybackPosition(currentMediaItem.getId(), isWatched ? 0 : currentPos, isWatched);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            saveProgress(false);
            player.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            saveProgress(false);
            player.release();
            player = null;
        }
    }
}
