package br.com.monitoratec.app.dagger;

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
public interface UiComponent {
    void inject(AuthActivity activity);
    void inject(ReposActivity reposActivity);
}
