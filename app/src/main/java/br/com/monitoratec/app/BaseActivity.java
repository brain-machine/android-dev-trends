package br.com.monitoratec.app;

import android.support.v7.app.AppCompatActivity;

import br.com.monitoratec.app.dagger.DiComponent;

/**
 * Created by falvojr on 1/12/17.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected MyApplication getMyApplication() {
        return (MyApplication) getApplication();
    }

    protected DiComponent getDaggerDiComponent() {
        return this.getMyApplication().getDaggerDiComponent();
    }

}
