package ru.slavapmk.shtp.components.versions;

public class Version {
    public final int code;
    public final String name;
    public final String downloadUrl;

    public Version(int code, String name, String downloadUrl) {
        this.code = code;
        this.name = name;
        this.downloadUrl = downloadUrl;
    }
}
