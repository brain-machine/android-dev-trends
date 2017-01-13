package br.com.monitoratec.app.domain.repository;

import br.com.monitoratec.app.domain.entity.User;
import rx.Observable;

/**
 * Repository interface da API GitHub Status.
 * <p>
 * Created by falvojr on 1/13/17.
 */
public interface GitHubRepository {

    Observable<User> getUser(String credential);
}
