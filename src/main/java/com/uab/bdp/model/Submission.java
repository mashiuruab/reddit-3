package com.uab.bdp.model;

import com.google.gson.annotations.SerializedName;

public class Submission {
    @SerializedName("num_comments")
    private Integer numberOfComments;
    @SerializedName("author")
    private String author;

    /*TODO:: need to investigate more  on this 2 "media_embed" and "secure_media_embed"*/
    @SerializedName("media_embed")
    private Object mediaEmbed;
    @SerializedName("secure_media_embed")
    private Object secureMediaEmbed;

    @SerializedName("ups")
    private Integer ups;
    @SerializedName("downs")
    private Integer downs;
    @SerializedName("name")
    private String name;
    @SerializedName("created_utc")
    private String created;

    @SerializedName("subreddit")
    private String subreddit;
    @SerializedName("subreddit_id")
    private String subreddit_id;

    @SerializedName("over_18")
    private Boolean over_18;

    public Integer getNumberOfComments() {
        return numberOfComments;
    }

    public String getAuthor() {
        return author;
    }

    public Object getMediaEmbed() {
        return mediaEmbed;
    }

    public Object getSecureMediaEmbed() {
        return secureMediaEmbed;
    }

    public Integer getUps() {
        return ups;
    }

    public Integer getDowns() {
        return downs;
    }

    public String getName() {
        return name;
    }

    public String getCreated() {
        return created;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getSubreddit_id() {
        return subreddit_id;
    }

    public Boolean getOver_18() {
        return over_18;
    }
}
