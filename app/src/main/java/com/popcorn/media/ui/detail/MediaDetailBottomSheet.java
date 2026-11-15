package com.popcorn.media.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.popcorn.media.R;
import com.popcorn.media.data.local.AppDatabase;
import com.popcorn.media.data.local.entity.MediaItem;
import com.popcorn.media.ui.adapter.EpisodeAdapter;
import com.popcorn.media.ui.player.PlayerActivity;

public class MediaDetailBottomSheet extends BottomSheetDialogFragment {

    private static final String ARG_MEDIA_ID = "arg_media_id";

    public static MediaDetailBottomSheet newInstance(long mediaId) {
        MediaDetailBottomSheet fragment = new MediaDetailBottomSheet();
        Bundle args = new Bundle();
        args.putLong(ARG_MEDIA_ID, mediaId);
        fragment.setArguments(args);
        return fragment;
    }

    private long mediaId;
    private MediaItem mediaItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mediaId = getArguments().getLong(ARG_MEDIA_ID, -1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_media_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView ivBackdrop = view.findViewById(R.id.iv_detail_backdrop);
        ImageButton btnClose = view.findViewById(R.id.btn_close_detail);
        TextView tvTitle = view.findViewById(R.id.tv_detail_title);
        TextView tvMeta = view.findViewById(R.id.tv_detail_meta);
        TextView tvOverview = view.findViewById(R.id.tv_detail_overview);
        MaterialButton btnPlay = view.findViewById(R.id.btn_detail_play);
        LinearLayout llEpisodesSection = view.findViewById(R.id.ll_episodes_section);
        RecyclerView rvEpisodes = view.findViewById(R.id.rv_episodes);

        btnClose.setOnClickListener(v -> dismiss());

        AppDatabase.getInstance(requireContext()).mediaDao().getMediaById(mediaId)
                .observe(getViewLifecycleOwner(), item -> {
                    if (item == null) return;
                    this.mediaItem = item;

                    tvTitle.setText(item.getTitle() != null ? item.getTitle() : item.getFileName());

                    StringBuilder meta = new StringBuilder();
                    if (item.getYear() != null) meta.append(item.getYear()).append(" • ");
                    if (item.getRating() > 0) meta.append("★ ").append(String.format("%.1f", item.getRating())).append(" • ");
                    meta.append("TV_SHOW".equals(item.getType()) ? "Serie TV" : "Film");
                    tvMeta.setText(meta.toString());

                    tvOverview.setText(item.getOverview() != null && !item.getOverview().isEmpty()
                            ? item.getOverview()
                            : "Nessuna trama disponibile per questo file.");

                    if (item.getBackdropPath() != null && !item.getBackdropPath().isEmpty()) {
                        Glide.with(this)
                                .load(item.getBackdropPath())
                                .placeholder(R.color.netflix_card_bg)
                                .centerCrop()
                                .into(ivBackdrop);
                    } else if (item.getPosterPath() != null && !item.getPosterPath().isEmpty()) {
                        Glide.with(this)
                                .load(item.getPosterPath())
                                .placeholder(R.color.netflix_card_bg)
                                .centerCrop()
                                .into(ivBackdrop);
                    }

                    btnPlay.setOnClickListener(v -> {
                        Intent intent = new Intent(requireContext(), PlayerActivity.class);
                        intent.putExtra(PlayerActivity.EXTRA_MEDIA_ID, item.getId());
                        startActivity(intent);
                        dismiss();
                    });

                    // Handle TV show episodes
                    if ("TV_SHOW".equals(item.getType())) {
                        llEpisodesSection.setVisibility(View.VISIBLE);
                        EpisodeAdapter episodeAdapter = new EpisodeAdapter(episodeItem -> {
                            Intent intent = new Intent(requireContext(), PlayerActivity.class);
                            intent.putExtra(PlayerActivity.EXTRA_MEDIA_ID, episodeItem.getId());
                            startActivity(intent);
                            dismiss();
                        });
                        rvEpisodes.setLayoutManager(new LinearLayoutManager(requireContext()));
                        rvEpisodes.setAdapter(episodeAdapter);

                        AppDatabase.getInstance(requireContext()).mediaDao()
                                .getEpisodesForShow(item.getTitle(), item.getSeasonNumber() > 0 ? item.getSeasonNumber() : 1)
                                .observe(getViewLifecycleOwner(), episodeAdapter::setEpisodes);
                    } else {
                        llEpisodesSection.setVisibility(View.GONE);
                    }
                });
    }
}
