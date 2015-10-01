package com.antocistudios.uv;

import android.app.Application;
import android.os.StrictMode;

import com.antocistudios.moviecatalog.BuildConfig;
import com.antocistudios.uv.domain.DomainModule;

import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by Adrian Antoci.
 *
 */
public class UpcomingVideogames extends Application{

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDependencyInjector();
        stringMode();
    }

    private void initializeDependencyInjector(){
        objectGraph = ObjectGraph.create(new DomainModule(this));
        objectGraph.inject(this);
        objectGraph.injectStatics();
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }

    public ObjectGraph plus(List<Object> modules) {
        if (modules == null) {
            throw new IllegalArgumentException(
                    "You can't plus a null module, review your getModules() implementation");
        }
        objectGraph = objectGraph.plus(modules.toArray());
        return objectGraph;
    }

    private void stringMode(){
        if (BuildConfig.DEBUG){
            StrictMode.enableDefaults();
        }
    }
}
