package br.com.monitoratec.app.dagger.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module for {@link Application} related instances.
 *
 * Created by falvojr on 1/12/17.
 */
@Module
public class ApplicationModule {

    private Application mApplication;

    public ApplicationModule(Application app) {
        mApplication = app;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    Context providesContext(Application app) {
        return app.getBaseContext();
    }

}
