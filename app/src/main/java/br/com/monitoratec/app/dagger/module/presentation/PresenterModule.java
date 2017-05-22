package br.com.monitoratec.app.dagger.module.presentation;

import br.com.monitoratec.app.dagger.scope.PerActivity;
import br.com.monitoratec.app.model.repository.GitHubOAuthRepository;
import br.com.monitoratec.app.model.repository.GitHubRepository;
import br.com.monitoratec.app.model.repository.GitHubStatusRepository;
import br.com.monitoratec.app.presentation.ui.auth.AuthContract;
import br.com.monitoratec.app.presentation.ui.auth.AuthPresenter;
import br.com.monitoratec.app.presentation.ui.repos.ReposContract;
import br.com.monitoratec.app.presentation.ui.repos.ReposPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Module for presenters.
 *
 * Created by falvojr on 1/13/17.
 */
@Module
public class PresenterModule {

    //TODO (14) Dagger: Prove os Presenter's (MVP)
    //Importante: @PerActivity (escopo)

    @PerActivity
    @Provides
    AuthContract.Presenter providesAuthPresenter(
            GitHubRepository gitHubRepository,
            GitHubStatusRepository gitHubStatusRepository,
            GitHubOAuthRepository gitHubOAuthRepository) {
        return new AuthPresenter(gitHubRepository,
                gitHubStatusRepository,
                gitHubOAuthRepository);
    }

    @PerActivity
    @Provides
    ReposContract.Presenter providesResposPresenter(
            GitHubRepository gitHubRepository) {
        return new ReposPresenter(gitHubRepository);
    }
}
