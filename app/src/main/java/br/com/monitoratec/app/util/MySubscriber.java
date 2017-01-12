package br.com.monitoratec.app.util;

import android.util.Log;

import rx.Subscriber;

/**
 * Created by falvojr on 1/12/17.
 */
public abstract class MySubscriber<T> extends Subscriber<T> {

    private static final String TAG = MySubscriber.class.getSimpleName();

    @Override
    public void onCompleted() { }

    @Override
    public void onError(Throwable e) {
        String message = e.getMessage();
        Log.d(TAG, message, e);
        onError(message);
    }

    public abstract void onError(String message);
}
