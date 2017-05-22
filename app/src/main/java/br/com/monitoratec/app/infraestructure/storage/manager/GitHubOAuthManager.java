package br.com.monitoratec.app.infraestructure.storage.manager;

import javax.inject.Inject;

import br.com.monitoratec.app.model.entity.AccessToken;
import br.com.monitoratec.app.model.repository.GitHubOAuthRepository;
import br.com.monitoratec.app.infraestructure.storage.service.GitHubOAuthService;
import io.reactivex.Observable;

/**
 * Manager for {@link GitHubOAuthRepository}.
 *
 * Created by falvojr on 1/13/17.
 */
public class GitHubOAuthManager implements GitHubOAuthRepository {

    private final GitHubOAuthService mGitHubOAuthService;

    @Inject
    public GitHubOAuthManager(GitHubOAuthService gitHubOAuthService) {
        mGitHubOAuthService = gitHubOAuthService;
    }

    @Override
    public Observable<AccessToken> getAccessToken(String clientId, String clientSecret, String code) {
        return mGitHubOAuthService.getAccessToken(clientId, clientSecret, code);
    }
}
