package br.com.monitoratec.app.infraestructure.storage.service;

import br.com.monitoratec.app.domain.entity.Status;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Interface Retrofit da API GitHub Status.
 * <p>
 * Created by falvojr on 1/9/17.
 */
public interface GitHubStatusService {

    String BASE_URL = "https://status.github.com/api/";

    @GET("last-message.json")
    Observable<Status> lastMessage();
}
