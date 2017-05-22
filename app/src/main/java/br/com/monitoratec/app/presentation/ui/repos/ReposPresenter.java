package br.com.monitoratec.app.presentation.ui.repos;

import br.com.monitoratec.app.domain.repository.GitHubRepository;

/**
 * GitHub List Repo's presenter.
 * <p>
 * Created by falvojr on 1/14/17.
 */
public class ReposPresenter implements ReposContract.Presenter {

    private final GitHubRepository mGitHubRepository;
    private ReposContract.View mView;

    public ReposPresenter(GitHubRepository gitHubRepository) {
        mGitHubRepository = gitHubRepository;
    }

    @Override
    public void setView(ReposContract.View view) {
        mView = view;
    }

    @Override
    public void loadRepos(String credential) {
        mGitHubRepository.getRepos(credential)
                .subscribe(repos -> {
                    mView.setupRepos(repos);
                }, error -> {
                    mView.showError(error.getMessage());
                });

    }
}
