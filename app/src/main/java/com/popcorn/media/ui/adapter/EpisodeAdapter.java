package com.popcorn.media.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.popcorn.media.R;
import com.popcorn.media.data.local.entity.MediaItem;

import java.util.ArrayList;
import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {

    public interface OnEpisodeClickListener {
        void onEpisodeClick(MediaItem item);
    }

    private final List<MediaItem> episodes = new ArrayList<>();
    private final OnEpisodeClickListener listener;

    public EpisodeAdapter(OnEpisodeClickListener listener) {
        this.listener = listener;
    }

    public void setEpisodes(List<MediaItem> newEpisodes) {
        this.episodes.clear();
        if (newEpisodes != null) {
            this.episodes.addAll(newEpisodes);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_episode, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MediaItem episode = episodes.get(position);
        holder.bind(episode, listener);
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivThumb;
        final TextView tvTitle;
        final TextView tvFilename;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.iv_episode_thumb);
            tvTitle = itemView.findViewById(R.id.tv_episode_title);
            tvFilename = itemView.findViewById(R.id.tv_episode_filename);
        }

        void bind(MediaItem item, OnEpisodeClickListener listener) {
            int epNum = item.getEpisodeNumber() > 0 ? item.getEpisodeNumber() : getAdapterPosition() + 1;
            String name = item.getTitle() != null ? item.getTitle() : item.getFileName();
            tvTitle.setText(epNum + ". " + name);
            tvFilename.setText(item.getFileName());

            String imagePath = item.getBackdropPath() != null ? item.getBackdropPath() : item.getPosterPath();
            if (imagePath != null && !imagePath.isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(imagePath)
                        .placeholder(R.color.netflix_card_bg)
                        .centerCrop()
                        .into(ivThumb);
            } else {
                ivThumb.setImageResource(R.color.netflix_card_bg);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEpisodeClick(item);
                }
            });
        }
    }
}
