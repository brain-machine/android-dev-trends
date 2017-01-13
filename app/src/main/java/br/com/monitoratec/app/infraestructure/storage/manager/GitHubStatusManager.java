package br.com.monitoratec.app.infraestructure.storage.manager;

import br.com.monitoratec.app.domain.entity.Status;
import br.com.monitoratec.app.domain.repository.GitHubStatusRepository;
import br.com.monitoratec.app.infraestructure.storage.service.GitHubStatusService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Manager for {@link GitHubStatusRepository}.
 *
 * Created by falvojr on 1/13/17.
 */
public class GitHubStatusManager implements GitHubStatusRepository {

    private final GitHubStatusService mGitHubStatusService;

    public GitHubStatusManager(GitHubStatusService gitHubStatusService) {
        mGitHubStatusService = gitHubStatusService;
    }

    @Override
    public Observable<Status> lastMessage() {
        return mGitHubStatusService.lastMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
