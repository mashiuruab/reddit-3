package com.uab.bdp.domain.model;

import com.google.gson.annotations.SerializedName;

public class DomainModel {
    public static final String CREATED_UTC_KEY = "created_utc";
    public static final String DOMAIN_KEY = "domain";

    @SerializedName(CREATED_UTC_KEY)
    private String created;
    @SerializedName(DOMAIN_KEY)
    private String domain;

    public String getCreated() {
        return created;
    }

    public String getDomain() {
        return domain;
    }
}
