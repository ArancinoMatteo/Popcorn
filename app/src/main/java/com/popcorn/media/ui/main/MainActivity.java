package com.popcorn.media.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.popcorn.media.R;
import com.popcorn.media.data.local.AppDatabase;
import com.popcorn.media.data.local.entity.MediaItem;
import com.popcorn.media.data.scanner.MediaScanner;
import com.popcorn.media.ui.adapter.MediaCardAdapter;
import com.popcorn.media.ui.adapter.MediaRowAdapter;
import com.popcorn.media.ui.detail.MediaDetailBottomSheet;
import com.popcorn.media.ui.model.MediaRowItem;
import com.popcorn.media.ui.player.PlayerActivity;
import com.popcorn.media.ui.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MediaCardAdapter.OnMediaClickListener {

    private MediaRowAdapter mainRowAdapter;
    private View heroBannerView;
    private ImageView ivHeroBackdrop;
    private TextView tvHeroTitle;
    private TextView tvHeroMeta;
    private MaterialButton btnHeroPlay;
    private LinearLayout btnHeroMyList;
    private LinearLayout btnHeroInfoContainer;

    private LinearLayout llSearchContainer;
    private TextInputEditText etSearchQuery;
    private LinearLayout llEmptyState;
    private LinearLayout llScanProgress;
    private TextView tvScanStatus;
    private BottomNavigationView bottomNavigation;

    private MediaItem currentHeroItem;
    private String selectedFilter = "ALL"; // "ALL", "TV_SHOWS", "MOVIES"
    private String searchQuery = "";

    private final ActivityResultLauncher<Uri> folderPickerLauncher =
            registerForActivityResult(new ActivityResultContracts.OpenDocumentTree(), uri -> {
                if (uri != null) {
                    takePersistableUriPermission(uri);
                    startFolderScan(uri);
                }
            });

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    openFolderPicker();
                } else {
                    Toast.makeText(this, "Permesso di lettura media necessario", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupRecyclerView();
        setupBottomNavigation();
        setupFilterChips();
        observeDatabase();
    }

    private void initViews() {
        ImageButton btnImportFolder = findViewById(R.id.btn_import_folder);
        ImageButton btnTopSettings = findViewById(R.id.btn_top_settings);
        MaterialButton btnImportEmpty = findViewById(R.id.btn_import_empty);

        heroBannerView = findViewById(R.id.hero_banner);
        ivHeroBackdrop = findViewById(R.id.iv_hero_backdrop);
        tvHeroTitle = findViewById(R.id.tv_hero_title);
        tvHeroMeta = findViewById(R.id.tv_hero_meta);
        btnHeroPlay = findViewById(R.id.btn_hero_play);
        btnHeroMyList = findViewById(R.id.btn_hero_my_list);
        btnHeroInfoContainer = findViewById(R.id.btn_hero_info_container);

        llSearchContainer = findViewById(R.id.ll_search_container);
        etSearchQuery = findViewById(R.id.et_search_query);

        llEmptyState = findViewById(R.id.ll_empty_state);
        llScanProgress = findViewById(R.id.ll_scan_progress);
        tvScanStatus = findViewById(R.id.tv_scan_status);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        btnImportFolder.setOnClickListener(v -> checkPermissionAndOpenPicker());
        btnImportEmpty.setOnClickListener(v -> checkPermissionAndOpenPicker());

        if (btnTopSettings != null) {
            btnTopSettings.setOnClickListener(v -> {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
            });
        }

        btnHeroPlay.setOnClickListener(v -> {
            if (currentHeroItem != null) {
                Intent intent = new Intent(this, PlayerActivity.class);
                intent.putExtra(PlayerActivity.EXTRA_MEDIA_ID, currentHeroItem.getId());
                startActivity(intent);
            }
        });

        if (btnHeroMyList != null) {
            btnHeroMyList.setOnClickListener(v ->
                    Toast.makeText(this, "Aggiunto alla tua Lista", Toast.LENGTH_SHORT).show());
        }

        if (btnHeroInfoContainer != null) {
            btnHeroInfoContainer.setOnClickListener(v -> {
                if (currentHeroItem != null) {
                    MediaDetailBottomSheet bottomSheet = MediaDetailBottomSheet.newInstance(currentHeroItem.getId());
                    bottomSheet.show(getSupportFragmentManager(), "media_detail");
                }
            });
        }

        if (etSearchQuery != null) {
            etSearchQuery.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    searchQuery = s.toString().trim().toLowerCase();
                    updateRows();
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }

    private void setupRecyclerView() {
        RecyclerView rvMainRows = findViewById(R.id.rv_main_rows);
        mainRowAdapter = new MediaRowAdapter(this);
        rvMainRows.setLayoutManager(new LinearLayoutManager(this));
        rvMainRows.setAdapter(mainRowAdapter);
    }

    private void setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                llSearchContainer.setVisibility(View.GONE);
                heroBannerView.setVisibility(View.VISIBLE);
                selectedFilter = "ALL";
                updateRows();
                return true;
            } else if (itemId == R.id.nav_search) {
                llSearchContainer.setVisibility(View.VISIBLE);
                heroBannerView.setVisibility(View.GONE);
                if (etSearchQuery != null) etSearchQuery.requestFocus();
                return true;
            } else if (itemId == R.id.nav_library) {
                checkPermissionAndOpenPicker();
                return true;
            } else if (itemId == R.id.nav_settings) {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    private void setupFilterChips() {
        TextView chipTvShows = findViewById(R.id.tv_chip_tv_shows);
        TextView chipMovies = findViewById(R.id.tv_chip_movies);
        TextView chipAll = findViewById(R.id.tv_chip_all);

        if (chipTvShows != null) {
            chipTvShows.setOnClickListener(v -> {
                selectedFilter = "TV_SHOWS";
                Toast.makeText(this, "Filtro: Serie TV", Toast.LENGTH_SHORT).show();
                updateRows();
            });
        }

        if (chipMovies != null) {
            chipMovies.setOnClickListener(v -> {
                selectedFilter = "MOVIES";
                Toast.makeText(this, "Filtro: Film", Toast.LENGTH_SHORT).show();
                updateRows();
            });
        }

        if (chipAll != null) {
            chipAll.setOnClickListener(v -> {
                selectedFilter = "ALL";
                Toast.makeText(this, "Tutti i contenuti", Toast.LENGTH_SHORT).show();
                updateRows();
            });
        }
    }

    private void observeDatabase() {
        AppDatabase db = AppDatabase.getInstance(this);

        db.mediaDao().getAllMedia().observe(this, allItems -> {
            if (allItems == null || allItems.isEmpty()) {
                llEmptyState.setVisibility(View.VISIBLE);
                heroBannerView.setVisibility(View.GONE);
                mainRowAdapter.setRows(new ArrayList<>());
            } else {
                llEmptyState.setVisibility(View.GONE);
                if (llSearchContainer.getVisibility() != View.VISIBLE) {
                    heroBannerView.setVisibility(View.VISIBLE);
                }
                updateHeroBanner(allItems.get(0));
            }
        });

        // Observe rows
        db.mediaDao().getContinueWatching().observe(this, continueItems -> updateRows());
        db.mediaDao().getRecentlyAdded().observe(this, recentItems -> updateRows());
        db.mediaDao().getMovies().observe(this, movieItems -> updateRows());
        db.mediaDao().getTvShows().observe(this, tvItems -> updateRows());
    }

    private void updateRows() {
        AppDatabase db = AppDatabase.getInstance(this);
        List<MediaRowItem> rowItems = new ArrayList<>();

        List<MediaItem> continueWatching = filterItems(db.mediaDao().getContinueWatching().getValue());
        if (continueWatching != null && !continueWatching.isEmpty()) {
            rowItems.add(new MediaRowItem("🔴 Continua a guardare", continueWatching));
        }

        List<MediaItem> recentlyAdded = filterItems(db.mediaDao().getRecentlyAdded().getValue());
        if (recentlyAdded != null && !recentlyAdded.isEmpty()) {
            rowItems.add(new MediaRowItem("🍿 Aggiunti di recente", recentlyAdded));
        }

        if (!"TV_SHOWS".equals(selectedFilter)) {
            List<MediaItem> movies = filterItems(db.mediaDao().getMovies().getValue());
            if (movies != null && !movies.isEmpty()) {
                rowItems.add(new MediaRowItem("🎬 Film", movies));
            }
        }

        if (!"MOVIES".equals(selectedFilter)) {
            List<MediaItem> tvShows = filterItems(db.mediaDao().getTvShows().getValue());
            if (tvShows != null && !tvShows.isEmpty()) {
                rowItems.add(new MediaRowItem("📺 Serie TV", tvShows));
            }
        }

        mainRowAdapter.setRows(rowItems);
    }

    private List<MediaItem> filterItems(List<MediaItem> inputList) {
        if (inputList == null) return null;
        List<MediaItem> filtered = new ArrayList<>();

        for (MediaItem item : inputList) {
            // Apply category filter
            if ("TV_SHOWS".equals(selectedFilter) && !"TV_SHOW".equals(item.getType())) continue;
            if ("MOVIES".equals(selectedFilter) && !"MOVIE".equals(item.getType())) continue;

            // Apply search query filter
            if (!searchQuery.isEmpty()) {
                String title = item.getTitle() != null ? item.getTitle().toLowerCase() : "";
                String filename = item.getFileName() != null ? item.getFileName().toLowerCase() : "";
                if (!title.contains(searchQuery) && !filename.contains(searchQuery)) {
                    continue;
                }
            }

            filtered.add(item);
        }
        return filtered;
    }

    private void updateHeroBanner(MediaItem item) {
        this.currentHeroItem = item;
        tvHeroTitle.setText(item.getTitle() != null ? item.getTitle() : item.getFileName());

        StringBuilder meta = new StringBuilder();
        if (item.getYear() != null) meta.append(item.getYear()).append(" • ");
        if (item.getRating() > 0) meta.append("★ ").append(String.format("%.1f", item.getRating())).append(" • ");
        meta.append("TV_SHOW".equals(item.getType()) ? "Serie TV" : "Film");
        tvHeroMeta.setText(meta.toString());

        if (item.getBackdropPath() != null && !item.getBackdropPath().isEmpty()) {
            Glide.with(this)
                    .load(item.getBackdropPath())
                    .placeholder(R.color.netflix_card_bg)
                    .centerCrop()
                    .into(ivHeroBackdrop);
        } else if (item.getPosterPath() != null && !item.getPosterPath().isEmpty()) {
            Glide.with(this)
                    .load(item.getPosterPath())
                    .placeholder(R.color.netflix_card_bg)
                    .centerCrop()
                    .into(ivHeroBackdrop);
        }
    }

    private void checkPermissionAndOpenPicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO)
                    == PackageManager.PERMISSION_GRANTED) {
                openFolderPicker();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_VIDEO);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                openFolderPicker();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

    private void openFolderPicker() {
        folderPickerLauncher.launch(null);
    }

    private void takePersistableUriPermission(Uri uri) {
        try {
            int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
            getContentResolver().takePersistableUriPermission(uri, takeFlags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startFolderScan(Uri folderUri) {
        llScanProgress.setVisibility(View.VISIBLE);
        tvScanStatus.setText("Avvio scansione...");

        MediaScanner.scanFolderUri(this, folderUri, new MediaScanner.ScanCallback() {
            @Override
            public void onScanProgress(int current, int total, String currentFile) {
                runOnUiThread(() -> tvScanStatus.setText("Scansione (" + current + "/" + total + "): " + currentFile));
            }

            @Override
            public void onScanComplete(int count) {
                runOnUiThread(() -> {
                    llScanProgress.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Scansione completata (" + count + " file)", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @Override
    public void onMediaClick(MediaItem item) {
        MediaDetailBottomSheet bottomSheet = MediaDetailBottomSheet.newInstance(item.getId());
        bottomSheet.show(getSupportFragmentManager(), "media_detail");
    }
}
