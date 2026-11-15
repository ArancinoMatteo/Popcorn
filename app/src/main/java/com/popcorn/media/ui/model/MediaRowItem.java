package com.popcorn.media.ui.model;

import com.popcorn.media.data.local.entity.MediaItem;

import java.util.List;

public class MediaRowItem {

    private final String title;
    private final List<MediaItem> items;

    public MediaRowItem(String title, List<MediaItem> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public List<MediaItem> getItems() {
        return items;
    }
}
