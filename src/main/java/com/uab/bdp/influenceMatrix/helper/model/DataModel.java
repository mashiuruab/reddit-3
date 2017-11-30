package com.uab.bdp.influenceMatrix.helper.model;

import com.google.gson.annotations.SerializedName;
import com.uab.bdp.model.Submission;

public class DataModel {
    @SerializedName(Submission.NUM_COMMENTS_KEY)
    private Double num_comments;
    @SerializedName(Submission.DOWN_VOTE_KEY)
    private Double downs;
    @SerializedName(Submission.OVER_18_KEY)
    private Double over_18;
    @SerializedName(Submission.UP_VOTE_KEY)
    private Double ups;

    public Double getNum_comments() {
        return num_comments;
    }

    public Double getDowns() {
        return downs;
    }

    public Double getOver_18() {
        return over_18;
    }

    public Double getUps() {
        return ups;
    }
}
