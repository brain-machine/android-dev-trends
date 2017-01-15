package br.com.monitoratec.app.presentation.ui.repos;

import java.util.List;

import br.com.monitoratec.app.domain.entity.Repo;

/**
 * GitHub list {@link Repo} MVP contract.
 *
 * Created by falvojr on 1/14/17.
 */
public interface ReposContract {
    interface View {
        void setupRepos(List<Repo> repos);

        void startNewRepoActivity();

        void showError(String message);
    }

    interface Presenter {
        void setView(ReposContract.View view);

        void loadRepos(String credential);
    }
}
