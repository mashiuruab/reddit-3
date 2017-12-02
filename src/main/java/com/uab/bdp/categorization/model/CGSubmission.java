package com.uab.bdp.categorization.model;

import com.google.gson.annotations.SerializedName;

public class CGSubmission {
    public static final String IS_SELF_KEY = "is_self";
    public static final String SELF_TEXT_KEY = "selftext";
    public static final String PREVIEW_KEY = "preview";
    public static final String MEDIA_KEY = "media";
    public static final String CREATED_DATE_KEY = "created_utc";

    @SerializedName(IS_SELF_KEY)
    private Boolean isSelfKey;
    @SerializedName(SELF_TEXT_KEY)
    private String selfText;
    @SerializedName(PREVIEW_KEY)
    private Object preview;
    @SerializedName(MEDIA_KEY)
    private CGMedia media;
    @SerializedName(CREATED_DATE_KEY)
    private String created_utc;

    public Boolean getSelfKey() {
        return isSelfKey;
    }

    public String getSelfText() {
        return selfText;
    }

    public Object getPreview() {
        return preview;
    }

    public CGMedia getMedia() {
        return media;
    }

    public String getCreated_utc() {
        return created_utc;
    }
}
