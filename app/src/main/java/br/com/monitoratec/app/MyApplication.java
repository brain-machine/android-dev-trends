package br.com.monitoratec.app;

import android.app.Application;

import br.com.monitoratec.app.dagger.DaggerDiComponent;
import br.com.monitoratec.app.dagger.DiComponent;
import br.com.monitoratec.app.dagger.UiComponent;
import br.com.monitoratec.app.dagger.module.ApplicationModule;

/**
 * Created by falvojr on 1/12/17.
 */
public class MyApplication extends Application {

    private DiComponent mDiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mDiComponent = DaggerDiComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public UiComponent getDaggerUiComponent() {
        return mDiComponent.uiComponent();
    }
}
