package br.com.monitoratec.app.infraestructure.storage.manager;

import javax.inject.Inject;

import br.com.monitoratec.app.domain.entity.User;
import br.com.monitoratec.app.domain.repository.GitHubRepository;
import br.com.monitoratec.app.infraestructure.storage.service.GitHubService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Manager for {@link GitHubRepository}.
 *
 * Created by falvojr on 1/13/17.
 */
public class GitHubManager implements GitHubRepository {

    private final GitHubService mGitHubService;

    @Inject
    public GitHubManager(GitHubService gitHubService) {
        mGitHubService = gitHubService;
    }

    @Override
    public Observable<User> getUser(String authorization) {
        return mGitHubService.basicAuth(authorization)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
