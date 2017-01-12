package br.com.monitoratec.app.dagger;

import javax.inject.Singleton;

import br.com.monitoratec.app.BaseActivity;
import br.com.monitoratec.app.MainActivity;
import br.com.monitoratec.app.dagger.module.ApplicationModule;
import br.com.monitoratec.app.dagger.module.NetworkModule;
import br.com.monitoratec.app.dagger.module.PreferenceModule;
import br.com.monitoratec.app.dagger.module.ServiceModule;
import dagger.Component;

/**
 * Created by falvojr on 1/12/17.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        PreferenceModule.class,
        NetworkModule.class,
        ServiceModule.class
})
public interface DiComponent {
    void inject(BaseActivity activity);
    void inject(MainActivity activity);
}
