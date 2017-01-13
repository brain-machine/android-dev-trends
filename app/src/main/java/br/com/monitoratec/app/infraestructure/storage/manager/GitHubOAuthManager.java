package br.com.monitoratec.app.infraestructure.storage.manager;

import br.com.monitoratec.app.domain.entity.AccessToken;
import br.com.monitoratec.app.domain.repository.GitHubOAuthRepository;
import br.com.monitoratec.app.infraestructure.storage.service.GitHubOAuthService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Manager for {@link GitHubOAuthRepository}.
 *
 * Created by falvojr on 1/13/17.
 */
public class GitHubOAuthManager implements GitHubOAuthRepository {

    private final GitHubOAuthService mGitHubOAuthService;

    public GitHubOAuthManager(GitHubOAuthService gitHubOAuthService) {
        mGitHubOAuthService = gitHubOAuthService;
    }

    @Override
    public Observable<AccessToken> accessToken(String clientId, String clientSecret, String code) {
        return mGitHubOAuthService.accessToken(clientId, clientSecret, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
