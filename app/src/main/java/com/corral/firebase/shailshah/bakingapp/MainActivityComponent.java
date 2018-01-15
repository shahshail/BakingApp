package com.corral.firebase.shailshah.bakingapp;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by shailshah on 1/15/18.
 */

@Component(modules={MainActivityModule.class})
@Singleton
public interface MainActivityComponent {
    Context context();

    void inject(MainActivity mainActivity);
}