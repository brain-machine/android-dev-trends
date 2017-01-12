package br.com.monitoratec.app.dagger.module;

import javax.inject.Named;
import javax.inject.Singleton;

import br.com.monitoratec.app.domain.GitHubApi;
import br.com.monitoratec.app.domain.GitHubOAuthApi;
import br.com.monitoratec.app.domain.GitHubStatusApi;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

import static br.com.monitoratec.app.dagger.module.NetworkModule.RETROFIT_GITHUB;
import static br.com.monitoratec.app.dagger.module.NetworkModule.RETROFIT_GITHUB_OAUTH;
import static br.com.monitoratec.app.dagger.module.NetworkModule.RETROFIT_GITHUB_STATUS;

/**
 * Created by falvojr on 1/12/17.
 */
@Module
public class ServiceModule {

    @Singleton
    @Provides
    GitHubApi providesGitHub(@Named(RETROFIT_GITHUB) Retrofit retrofit) {
        return retrofit.create(GitHubApi.class);
    }

    @Singleton
    @Provides
    GitHubStatusApi providesGitHubStatus(@Named(RETROFIT_GITHUB_STATUS) Retrofit retrofit) {
        return retrofit.create(GitHubStatusApi.class);
    }

    @Singleton
    @Provides
    GitHubOAuthApi providesGitHubOAuth(@Named(RETROFIT_GITHUB_OAUTH) Retrofit retrofit) {
        return retrofit.create(GitHubOAuthApi.class);
    }

}