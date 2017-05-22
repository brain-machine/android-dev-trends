package br.com.monitoratec.app.presentation.ui.auth;

import javax.inject.Inject;

import br.com.monitoratec.app.model.entity.Status;
import br.com.monitoratec.app.model.repository.GitHubOAuthRepository;
import br.com.monitoratec.app.model.repository.GitHubRepository;
import br.com.monitoratec.app.model.repository.GitHubStatusRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(status -> {
                    mView.onGetStatusComplete(status.type);
                }, error -> {
                    mView.onGetStatusComplete(Status.Type.MAJOR);
                });
    }

    @Override
    public void getUser(String authorization) {
        mGitHubRepository.getUser(authorization)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entity -> {
                    getUser(entity.getAuthCredential());
                }, error -> {
                    mView.showError(error.getMessage());
                });
    }
}