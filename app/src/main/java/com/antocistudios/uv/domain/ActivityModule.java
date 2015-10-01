package com.antocistudios.uv.domain;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Adrian Antoci.
 *
 */
@Module(library = true)
public final class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @ActivityContext @Provides
    Context provideActivityContext() {
        return activity;
    }
}
