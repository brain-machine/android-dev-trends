package br.com.monitoratec.app.infraestructure.storage.service;

import br.com.monitoratec.app.model.entity.AccessToken;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Interface Retrofit da API GitHub OAuth.
 * <p>
 * Created by falvojr on 1/11/17.
 */
public interface GitHubOAuthService {

    //TODO (06) Retrofit: GitHub OAuth URL

    String BASE_URL = "https://github.com/login/oauth/";

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("access_token")
    Observable<AccessToken> getAccessToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("code") String code);
}
