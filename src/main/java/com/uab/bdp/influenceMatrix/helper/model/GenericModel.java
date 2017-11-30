package com.uab.bdp.influenceMatrix.helper.model;

import com.google.gson.annotations.SerializedName;

public class GenericModel {
    @SerializedName("comment")
    private Double comment;
    @SerializedName("submission")
    private Double submission;

    public Double getComment() {
        return comment;
    }

    public Double getSubmission() {
        return submission;
    }
}
