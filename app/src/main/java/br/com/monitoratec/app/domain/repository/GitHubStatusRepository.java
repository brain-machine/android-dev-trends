package br.com.monitoratec.app.domain.repository;

import br.com.monitoratec.app.domain.entity.Status;
import io.reactivex.Observable;

/**
 * Repository interface da API GitHub Status.
 * <p>
 * Created by falvojr on 1/13/17.
 */
public interface GitHubStatusRepository {

    Observable<Status> getLastStatus();
}
