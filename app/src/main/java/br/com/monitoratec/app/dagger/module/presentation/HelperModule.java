package br.com.monitoratec.app.dagger.module.presentation;

import android.content.Context;

import br.com.monitoratec.app.presentation.helper.AppHelper;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

/**
 * Module for app helpers.
 *
 * Created by falvojr on 1/13/17.
 */
@Module
public class HelperModule {

    //TODO (12) Dagger: Disponibiliza um helper que depende de Context ;)

    @Provides
    @Reusable
    AppHelper provideTextHelper(Context context) {
        return new AppHelper(context);
    }
}
