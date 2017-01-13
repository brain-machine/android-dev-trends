package br.com.monitoratec.app.dagger.module.infraestructure;

import javax.inject.Singleton;

import br.com.monitoratec.app.domain.repository.GitHubOAuthRepository;
import br.com.monitoratec.app.domain.repository.GitHubRepository;
import br.com.monitoratec.app.domain.repository.GitHubStatusRepository;
import br.com.monitoratec.app.infraestructure.storage.manager.GitHubManager;
import br.com.monitoratec.app.infraestructure.storage.manager.GitHubOAuthManager;
import br.com.monitoratec.app.infraestructure.storage.manager.GitHubStatusManager;
import br.com.monitoratec.app.infraestructure.storage.service.GitHubOAuthService;
import br.com.monitoratec.app.infraestructure.storage.service.GitHubService;
import br.com.monitoratec.app.infraestructure.storage.service.GitHubStatusService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by falvojr on 1/13/17.
 */
@Module
public class ManagerModule {

    @Singleton
    @Provides
    GitHubRepository providesGitHubRepository(
            GitHubService gitHubService) {
        return new GitHubManager(gitHubService);
    }

    @Singleton
    @Provides
    GitHubStatusRepository providesGitHubStatusRepository(
            GitHubStatusService gitHubStatusService) {
        return new GitHubStatusManager(gitHubStatusService);
    }

    @Singleton
    @Provides
    GitHubOAuthRepository providesGitHubOAuthRepository(
            GitHubOAuthService gitHubOAuthService) {
        return new GitHubOAuthManager(gitHubOAuthService);
    }

}
