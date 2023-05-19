package ru.slavapmk.shtp.components.versions.github;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubAPI {
    @GET("repos/{author}/{repo}/releases")
    Observable<List<GithubVersion>> login(@Path("author") String author, @Path("repo") String repo);
}
