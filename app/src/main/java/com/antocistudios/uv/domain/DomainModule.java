package com.antocistudios.uv.domain;

import android.content.Context;

import com.antocistudios.uv.UpcomingVideogames;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Adrian Antoci.
 *
 */
@Module(
        injects = {UpcomingVideogames.class},
        addsTo = DomainAddModule.class,
        library = true
)
public final class DomainModule {
    private Context context;

    public DomainModule(Context context){
        this.context=context;
    }

    //@Named("application")
    @Provides
    public Context providerApplicationContext(){
        return context;
    }

}
