package com.uab.bdp.helper.model;

import com.google.gson.annotations.SerializedName;
import com.uab.bdp.categorization.CGMapper;
import com.uab.bdp.categorization.CGReducer;
import com.uab.bdp.categorization.model.CGSubmission;

public class GenericModel {
    @SerializedName("comment")
    private Double comment;
    @SerializedName("submission")
    private Double submission;

    @SerializedName(CGMapper.IMAGE_KEY)
    private Double image;
    @SerializedName(CGMapper.RICH_KEY)
    private Double audio;
    @SerializedName(CGMapper.VIDEO_KEY)
    private Double video;
    @SerializedName(CGSubmission.IS_SELF_KEY)
    private Double is_self;
    @SerializedName(CGSubmission.SELF_TEXT_KEY)
    private Double selftext;
    @SerializedName(CGReducer.MISC_KEY)
    private Double misc;

    public Double getComment() {
        return comment;
    }

    public Double getSubmission() {
        return submission;
    }

    public Double getImage() {
        return image;
    }

    public Double getAudio() {
        return audio;
    }

    public Double getVideo() {
        return video;
    }

    public Double getIs_self() {
        return is_self;
    }

    public Double getSelftext() {
        return selftext;
    }

    public Double getMisc() {
        return misc;
    }
}
