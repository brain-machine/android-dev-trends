package br.com.monitoratec.app.presentation.ui.repos;

import br.com.monitoratec.app.model.repository.GitHubRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * GitHub List Repo's presenter.
 * <p>
 * Created by falvojr on 1/14/17.
 */
public class ReposPresenter implements ReposContract.Presenter {

    //TODO (15) MVP: responde às ações da UI controlando a interação entre a View e o Model.
    //Importante: Contract (desacoplar a View do Presenter)
    //Importante: Repository (desacoplar a View do Model/Domain)
    //Importante: Rx Schedulers

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repos -> {
                    mView.setupRepos(repos);
                }, error -> {
                    mView.showError(error.getMessage());
                });

    }
}
