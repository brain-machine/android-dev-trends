package br.com.monitoratec.app.domain;

import br.com.monitoratec.app.domain.entity.Status;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interface da API GitHub Status.
 *
 * Created by falvojr on 1/9/17.
 */
public interface GitHubStatusApi {

    String BASE_URL = "https://status.github.com/api/";

    @GET("last-message.json")
    Call<Status> lastMessage();
}
