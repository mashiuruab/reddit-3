package com.uab.bdp.categorization.model;

import com.google.gson.annotations.SerializedName;

public class CGMedia {
    @SerializedName("oembed")
    private OEMBED oembed;

    public OEMBED getOembed() {
        return oembed;
    }
}
