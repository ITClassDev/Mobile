package ru.slavapmk.shtp.components.versions;

import io.reactivex.rxjava3.core.Observable;

public interface VersionManager {
    Observable<Version> getLastVersion();
}
