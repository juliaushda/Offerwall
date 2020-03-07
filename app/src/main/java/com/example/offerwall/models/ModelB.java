package com.example.offerwall.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelB {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("contents")
    @Expose
    private String contents;

    @SerializedName("url")
    @Expose
    private String url;

    public String getType() {
        return type;
    }

    public String getContents() {
        return contents;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
