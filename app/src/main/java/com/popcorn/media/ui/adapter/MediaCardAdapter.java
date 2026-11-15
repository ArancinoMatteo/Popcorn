package com.popcorn.media.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.popcorn.media.R;
import com.popcorn.media.data.local.entity.MediaItem;

import java.util.ArrayList;
import java.util.List;

public class MediaCardAdapter extends RecyclerView.Adapter<MediaCardAdapter.ViewHolder> {

    public interface OnMediaClickListener {
        void onMediaClick(MediaItem item);
    }

    private final List<MediaItem> items = new ArrayList<>();
    private final OnMediaClickListener listener;

    public MediaCardAdapter(OnMediaClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<MediaItem> newItems) {
        this.items.clear();
        if (newItems != null) {
            this.items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_media_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MediaItem item = items.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivPoster;
        final TextView tvFallbackTitle;
        final ProgressBar pbWatchProgress;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvFallbackTitle = itemView.findViewById(R.id.tv_fallback_title);
            pbWatchProgress = itemView.findViewById(R.id.pb_watch_progress);
        }

        void bind(MediaItem item, OnMediaClickListener listener) {
            if (item.getPosterPath() != null && !item.getPosterPath().isEmpty()) {
                ivPoster.setVisibility(View.VISIBLE);
                tvFallbackTitle.setVisibility(View.GONE);
                Glide.with(itemView.getContext())
                        .load(item.getPosterPath())
                        .placeholder(R.color.netflix_card_bg)
                        .centerCrop()
                        .into(ivPoster);
            } else {
                ivPoster.setVisibility(View.GONE);
                tvFallbackTitle.setVisibility(View.VISIBLE);
                tvFallbackTitle.setText(item.getTitle() != null ? item.getTitle() : item.getFileName());
            }

            // Progress bar for continue watching
            if (item.getLastPositionMs() > 0 && item.getDurationMs() > 0) {
                pbWatchProgress.setVisibility(View.VISIBLE);
                int progress = (int) ((item.getLastPositionMs() * 100) / item.getDurationMs());
                pbWatchProgress.setProgress(Math.max(progress, 5));
            } else {
                pbWatchProgress.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onMediaClick(item);
                }
            });
        }
    }
}
