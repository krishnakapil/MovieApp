package test.cone.com.androidtest.model;

import com.google.gson.annotations.SerializedName;

public class MediaResponse {
    private int page;

    @SerializedName("total_pages")
    private int totalPages;
    private int totalResults;
    private Media[] results;

    public void setPage(int page) {
        this.page = page;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public void setResults(Media[] results) {
        this.results = results;
    }

    public Media[] getResults() {
        return results;
    }

    public void setMediaType(boolean isMovie) {
        if (results == null) {
            return;
        }

        for (Media media : results) {
            if (media != null) {
                media.setIsMovie(isMovie);
            }
        }
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }
}