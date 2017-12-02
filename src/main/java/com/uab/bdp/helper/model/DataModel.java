package com.uab.bdp.helper.model;

import com.google.gson.annotations.SerializedName;
import com.uab.bdp.model.Submission;

public class DataModel {
    public static final String NUM_SUBMISSIONS_KEY = "numberOfSubmission";
    public static final String NUM_COMMENTS_KEY = "numberOfComments";

    @SerializedName(NUM_SUBMISSIONS_KEY)
    private Double num_submissions;
    @SerializedName(NUM_COMMENTS_KEY)
    private Double numOfComments;

    @SerializedName(Submission.NUM_COMMENTS_KEY)
    private Double num_comments;
    @SerializedName(Submission.DOWN_VOTE_KEY)
    private Double downs;
    @SerializedName(Submission.OVER_18_KEY)
    private Double over_18;
    @SerializedName(Submission.UP_VOTE_KEY)
    private Double ups;

    public Double getNumOfComments() {
        return numOfComments;
    }

    public Double getNum_submissions() {
        return num_submissions;
    }

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
