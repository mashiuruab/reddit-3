package com.uab.bdp.KMeans.TFIDF.model;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("body")
    private String body;
    @SerializedName("subreddit id")
    private String subredditId;
    @SerializedName("subreddit")
    private String subreddit;

    public String getBody() {
        return body;
    }

    public String getSubredditId() {
        return subredditId;
    }

    public String getSubreddit() {
        return subreddit;
    }
}
