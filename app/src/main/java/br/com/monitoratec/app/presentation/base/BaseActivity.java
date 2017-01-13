package br.com.monitoratec.app.presentation.base;

import android.support.v7.app.AppCompatActivity;

import br.com.monitoratec.app.MyApplication;
import br.com.monitoratec.app.dagger.UiComponent;

/**
 * Created by falvojr on 1/12/17.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected MyApplication getMyApplication() {
        return (MyApplication) getApplication();
    }

    protected UiComponent getDaggerUiComponent() {
        return this.getMyApplication().getDaggerUiComponent();
    }

}
