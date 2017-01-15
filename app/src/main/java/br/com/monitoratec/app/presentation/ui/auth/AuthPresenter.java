package br.com.monitoratec.app.presentation.ui.auth;

import javax.inject.Inject;

import br.com.monitoratec.app.domain.entity.Status;
import br.com.monitoratec.app.domain.repository.GitHubOAuthRepository;
import br.com.monitoratec.app.domain.repository.GitHubRepository;
import br.com.monitoratec.app.domain.repository.GitHubStatusRepository;

/**
 * GitHub authentication presenter.
 *
 * Created by falvojr on 1/13/17.
 */
public class AuthPresenter implements AuthContract.Presenter {

    private AuthContract.View mView;
    private GitHubRepository mGitHubRepository;
    private GitHubStatusRepository mGitHubStatusRepository;
    private GitHubOAuthRepository mGitHubOAuthRepository;

    @Inject
    public AuthPresenter(GitHubRepository gitHubRepository,
                         GitHubStatusRepository gitHubStatusRepository,
                         GitHubOAuthRepository gitHubOAuthRepository) {
        mGitHubRepository = gitHubRepository;
        mGitHubStatusRepository = gitHubStatusRepository;
        mGitHubOAuthRepository = gitHubOAuthRepository;
    }

    @Override
    public void setView(AuthContract.View view) {
        mView = view;
    }

    @Override
    public void getStatus() {
        mGitHubStatusRepository.getLastStatus()
                .subscribe(status -> {
                    mView.onGetStatusComplete(status.type);
                }, error -> {
                    mView.onGetStatusComplete(Status.Type.MAJOR);
                });
    }

    @Override
    public void getUser(String authorization) {
        mGitHubRepository.getUser(authorization)
                .subscribe(user -> {
                    mView.onGetUserComplete(authorization, user);
                }, error -> {
                    mView.showError(error.getMessage());
                });
    }

    @Override
    public void getAccessTokenAndUser(String clientId,
                                      String clientSecret,
                                      String code) {
        mGitHubOAuthRepository.getAccessToken(clientId, clientSecret, code)
                .subscribe(entity -> {
                    getUser(entity.getAuthCredential());
                }, error -> {
                    mView.showError(error.getMessage());
                });
    }
}