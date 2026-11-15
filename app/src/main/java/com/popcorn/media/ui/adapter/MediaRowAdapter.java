package com.popcorn.media.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.popcorn.media.R;
import com.popcorn.media.data.local.entity.MediaItem;
import com.popcorn.media.ui.model.MediaRowItem;

import java.util.ArrayList;
import java.util.List;

public class MediaRowAdapter extends RecyclerView.Adapter<MediaRowAdapter.ViewHolder> {

    private final List<MediaRowItem> rows = new ArrayList<>();
    private final MediaCardAdapter.OnMediaClickListener listener;

    public MediaRowAdapter(MediaCardAdapter.OnMediaClickListener listener) {
        this.listener = listener;
    }

    public void setRows(List<MediaRowItem> newRows) {
        this.rows.clear();
        if (newRows != null) {
            this.rows.addAll(newRows);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_media_row, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MediaRowItem rowItem = rows.get(position);
        holder.bind(rowItem);
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvRowTitle;
        final RecyclerView rvMediaCards;
        final MediaCardAdapter cardAdapter;

        ViewHolder(@NonNull View itemView, MediaCardAdapter.OnMediaClickListener listener) {
            super(itemView);
            tvRowTitle = itemView.findViewById(R.id.tv_row_title);
            rvMediaCards = itemView.findViewById(R.id.rv_media_cards);

            cardAdapter = new MediaCardAdapter(listener);
            rvMediaCards.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            rvMediaCards.setAdapter(cardAdapter);
        }

        void bind(MediaRowItem rowItem) {
            tvRowTitle.setText(rowItem.getTitle());
            cardAdapter.setItems(rowItem.getItems());
        }
    }
}
