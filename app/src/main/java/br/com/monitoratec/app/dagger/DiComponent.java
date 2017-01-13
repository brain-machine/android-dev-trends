package br.com.monitoratec.app.dagger;

import javax.inject.Singleton;

import br.com.monitoratec.app.dagger.module.ApplicationModule;
import br.com.monitoratec.app.dagger.module.PreferenceModule;
import br.com.monitoratec.app.dagger.module.infraestructure.ManagerModule;
import br.com.monitoratec.app.dagger.module.infraestructure.NetworkModule;
import br.com.monitoratec.app.dagger.module.infraestructure.ServiceModule;
import br.com.monitoratec.app.dagger.module.presentation.HelperModule;
import dagger.Component;

/**
 * Created by falvojr on 1/12/17.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        HelperModule.class,
        PreferenceModule.class,
        NetworkModule.class,
        ServiceModule.class,
        ManagerModule.class
})
public interface DiComponent {
    UiComponent uiComponent();
}
