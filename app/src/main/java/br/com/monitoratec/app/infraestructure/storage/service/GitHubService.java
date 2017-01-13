package br.com.monitoratec.app.infraestructure.storage.service;

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
    Observable<User> basicAuth(@Header("Authorization") String credential);
}
