package br.com.monitoratec.app.dagger.subcomponent;

import br.com.monitoratec.app.dagger.module.presentation.PresenterModule;
import br.com.monitoratec.app.dagger.scope.PerActivity;
import br.com.monitoratec.app.presentation.ui.auth.AuthActivity;
import br.com.monitoratec.app.presentation.ui.repos.ReposActivity;
import dagger.Subcomponent;

/**
 * Dagger UI {@link Subcomponent} (per activity scope).
 *
 * Created by falvojr on 1/13/17.
 */
@PerActivity
@Subcomponent(modules = {PresenterModule.class})
public interface ActivitySubcomponent {

    //TODO (16) Dagger: Componente responsavel por prover a injecao das Activities
    //Importante: @PerActivity e @Subcomponent

    void inject(AuthActivity activity);
    void inject(ReposActivity reposActivity);
}
