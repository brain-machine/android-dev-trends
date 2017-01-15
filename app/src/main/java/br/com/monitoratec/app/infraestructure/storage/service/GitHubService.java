package br.com.monitoratec.app.infraestructure.storage.service;

import java.util.List;

import br.com.monitoratec.app.domain.entity.Repo;
import br.com.monitoratec.app.domain.entity.User;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Interface Retrofit da API GitHub Status.
 *
 * Created by falvojr on 1/9/17.
 */
public interface GitHubService {

    String BASE_URL = "https://api.github.com/";

    @GET("user")
    Observable<User> getUser(@Header("Authorization") String credential);
    @GET("user/repos")
    Observable<List<Repo>> getRepos(@Header("Authorization") String credential);
}
