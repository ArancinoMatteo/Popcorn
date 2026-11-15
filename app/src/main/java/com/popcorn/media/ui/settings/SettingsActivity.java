package com.popcorn.media.ui.settings;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.popcorn.media.R;
import com.popcorn.media.data.local.AppDatabase;
import com.popcorn.media.data.remote.TmdbApiClient;

public class SettingsActivity extends AppCompatActivity {

    private TextInputEditText etApiKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageButton btnBack = findViewById(R.id.btn_settings_back);
        etApiKey = findViewById(R.id.et_api_key);
        MaterialButton btnSaveApiKey = findViewById(R.id.btn_save_api_key);
        MaterialButton btnClearLibrary = findViewById(R.id.btn_clear_library);

        btnBack.setOnClickListener(v -> finish());

        // Load existing API Key
        String currentKey = TmdbApiClient.getApiKey(this);
        etApiKey.setText(currentKey);

        btnSaveApiKey.setOnClickListener(v -> {
            String newKey = etApiKey.getText() != null ? etApiKey.getText().toString().trim() : "";
            TmdbApiClient.setApiKey(this, newKey);
            Toast.makeText(this, "Chiave API TMDB salvata!", Toast.LENGTH_SHORT).show();
        });

        btnClearLibrary.setOnClickListener(v -> {
            AppDatabase.databaseWriteExecutor.execute(() -> {
                AppDatabase.getInstance(getApplicationContext()).mediaDao().deleteAll();
                runOnUiThread(() -> Toast.makeText(this, "Libreria svuotata con successo", Toast.LENGTH_SHORT).show());
            });
        });
    }
}
