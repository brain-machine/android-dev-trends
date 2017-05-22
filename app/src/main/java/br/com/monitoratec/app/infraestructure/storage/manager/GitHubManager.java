package br.com.monitoratec.app.infraestructure.storage.manager;

import java.util.List;

import javax.inject.Inject;

import br.com.monitoratec.app.model.entity.Repo;
import br.com.monitoratec.app.model.entity.User;
import br.com.monitoratec.app.model.repository.GitHubRepository;
import br.com.monitoratec.app.infraestructure.storage.service.GitHubService;
import io.reactivex.Observable;

/**
 * Manager for {@link GitHubRepository}.
 *
 * Created by falvojr on 1/13/17.
 */
public class GitHubManager implements GitHubRepository {

    //TODO (09) Clean Achitecture: Infraestrutura de storage (HTTP, Local etc)
    //Importante: Repository (desacoplar a Infraestrutura do Model/Domain)

    private final GitHubService mGitHubService;

    @Inject
    public GitHubManager(GitHubService gitHubService) {
        mGitHubService = gitHubService;
    }

    @Override
    public Observable<User> getUser(String authorization) {
        return mGitHubService.getUser(authorization);
    }

    @Override
    public Observable<List<Repo>> getRepos(String credential) {
        return mGitHubService.getRepos(credential);
    }
}
