package ru.slavapmk.shtp.components.versions.github;

import com.google.gson.annotations.SerializedName;

public class GithubVersion {
    @SerializedName("tag_name")
    String tag;
    String name;
    @SerializedName("html_url")
    String downloadUrl;
}
