package br.com.monitoratec.app.dagger.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Named;
import javax.inject.Singleton;

import br.com.monitoratec.app.R;
import dagger.Module;
import dagger.Provides;

/**
 * Created by falvojr on 1/12/17.
 */
@Module
public class PreferenceModule {

    @Provides
    @Singleton
    @Named("secret")
    SharedPreferences providesSharedPreferences(Context context) {
        final String fileName = context.getString(R.string.sp_file);
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    @Named("default")
    SharedPreferences providesSharedPreferencesDefault(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
