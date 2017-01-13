package br.com.monitoratec.app.presentation.ui.auth;

import br.com.monitoratec.app.domain.entity.Status;
import br.com.monitoratec.app.domain.repository.GitHubOAuthRepository;
import br.com.monitoratec.app.domain.repository.GitHubRepository;
import br.com.monitoratec.app.domain.repository.GitHubStatusRepository;

/**
 * Created by falvojr on 1/13/17.
 */

public class AuthPresenter implements AuthContract.Presenter {

    private AuthContract.View mView;
    private GitHubRepository mGitHubRepository;
    private GitHubStatusRepository mGitHubStatusRepository;
    private GitHubOAuthRepository mGitHubOAuthRepository;

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
    public void loadStatus() {
        mGitHubStatusRepository.lastMessage()
                .subscribe(status -> {
                    mView.onLoadStatusComplete(status.type);
                }, error -> {
                    mView.onLoadStatusComplete(Status.Type.MAJOR);
                });
    }

    @Override
    public void callGetUser(String authorization) {
        mGitHubRepository.getUser(authorization)
                .subscribe(user -> {
                    mView.onAuthSuccess(authorization, user);
                }, error -> {
                    mView.showError(error.getMessage());
                });
    }

    @Override
    public void callAccessToken(String clientId,
                                String clientSecret,
                                String code) {
        mGitHubOAuthRepository.accessToken(clientId, clientSecret, code)
                .subscribe(entity -> {
                    callGetUser(entity.getAuthCredential());
                }, error -> {
                    mView.showError(error.getMessage());
                });
    }
}