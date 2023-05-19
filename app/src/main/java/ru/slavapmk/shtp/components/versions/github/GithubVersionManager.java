package ru.slavapmk.shtp.components.versions.github;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.slavapmk.shtp.Values;
import ru.slavapmk.shtp.components.versions.Version;
import ru.slavapmk.shtp.components.versions.VersionManager;

public class GithubVersionManager implements VersionManager {
    private final String author, repo;

    GithubAPI api = new Retrofit.Builder()
            .client(new OkHttpClient.Builder().addInterceptor(Values.INSTANCE.getHttpLoggingInterceptor()).build())
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build().create(GithubAPI.class);

    public GithubVersionManager(String author, String repo) {
        this.author = author;
        this.repo = repo;
    }

    @Override
    public Observable<Version> getLastVersion() {
        return api.login(author, repo)
                .map(githubVersion -> new Version(
                        Integer.parseInt(githubVersion.get(0).tag_name),
                        githubVersion.get(0).name.replaceFirst("Release v", ""),
                        githubVersion.get(0).html_url
                ));
    }
}
