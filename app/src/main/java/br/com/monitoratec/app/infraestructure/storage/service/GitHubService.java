package br.com.monitoratec.app.infraestructure.storage.service;

import java.util.List;

import br.com.monitoratec.app.model.entity.Repo;
import br.com.monitoratec.app.model.entity.User;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Interface Retrofit da API GitHub Status.
 *
 * Created by falvojr on 1/9/17.
 */
public interface GitHubService {

    //TODO (05) Retrofit: GitHub API

    String BASE_URL = "https://api.github.com/";

    @GET("user")
    Observable<User> getUser(@Header("Authorization") String credential);
    @GET("user/repos")
    Observable<List<Repo>> getRepos(@Header("Authorization") String credential);
}
