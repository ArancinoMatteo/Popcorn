package com.popcorn.media.data.scanner;

import com.popcorn.media.data.local.entity.MediaItem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilenameParser {

    private static final Pattern TV_PATTERN = Pattern.compile(
            "(?i)(.*?)[._\\s-]+S(\\d{1,2})E(\\d{1,2})", Pattern.CASE_INSENSITIVE);

    private static final Pattern TV_ALT_PATTERN = Pattern.compile(
            "(?i)(.*?)[._\\s-]+(\\d{1,2})x(\\d{1,2})", Pattern.CASE_INSENSITIVE);

    private static final Pattern YEAR_PATTERN = Pattern.compile(
            "[._\\s-\\[\\(](19\\d{2}|20\\d{2})[._\\s-\\]\\)]");

    public static MediaItem parseFilename(String rawFileName, String uriString) {
        MediaItem item = new MediaItem();
        item.setUriString(uriString);
        item.setFileName(rawFileName);
        item.setAddedTimestamp(System.currentTimeMillis());

        // Remove extension
        String nameWithoutExt = rawFileName;
        int dotIndex = rawFileName.lastIndexOf('.');
        if (dotIndex > 0) {
            nameWithoutExt = rawFileName.substring(0, dotIndex);
        }

        // Check for TV show patterns (S01E02 or 1x02)
        Matcher tvMatcher = TV_PATTERN.matcher(nameWithoutExt);
        boolean isTvShow = false;
        String cleanTitle = nameWithoutExt;

        if (tvMatcher.find()) {
            isTvShow = true;
            cleanTitle = tvMatcher.group(1);
            try {
                item.setSeasonNumber(Integer.parseInt(tvMatcher.group(2)));
                item.setEpisodeNumber(Integer.parseInt(tvMatcher.group(3)));
            } catch (NumberFormatException ignored) {}
        } else {
            Matcher tvAltMatcher = TV_ALT_PATTERN.matcher(nameWithoutExt);
            if (tvAltMatcher.find()) {
                isTvShow = true;
                cleanTitle = tvAltMatcher.group(1);
                try {
                    item.setSeasonNumber(Integer.parseInt(tvAltMatcher.group(2)));
                    item.setEpisodeNumber(Integer.parseInt(tvAltMatcher.group(3)));
                } catch (NumberFormatException ignored) {}
            }
        }

        // Check for Year
        Matcher yearMatcher = YEAR_PATTERN.matcher(cleanTitle);
        if (yearMatcher.find()) {
            item.setYear(yearMatcher.group(1));
            cleanTitle = cleanTitle.substring(0, yearMatcher.start());
        }

        // Clean up title (replace dots/underscores with spaces & remove technical tags)
        cleanTitle = cleanTitle.replaceAll("[._]", " ").trim();
        cleanTitle = cleanTitle.replaceAll("(?i)\\b(1080p|720p|4k|2160p|bluray|webrip|h264|x264|hevc|x265|hdr|dvdrip|aac)\\b", "").trim();

        if (cleanTitle.isEmpty()) {
            cleanTitle = nameWithoutExt;
        }

        item.setTitle(cleanTitle);
        item.setType(isTvShow ? "TV_SHOW" : "MOVIE");

        return item;
    }
}
