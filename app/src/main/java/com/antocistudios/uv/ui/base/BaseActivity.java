package com.antocistudios.uv.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.antocistudios.uv.UpcomingVideogames;
import com.antocistudios.uv.domain.ActivityModule;

import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by Adrian Antoci.
 *
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static String tag = null;

    private ObjectGraph activityScopeGraph;

    public BaseActivity(String inTag)    {
        tag = inTag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void inject(Object object) {
        activityScopeGraph.inject(object);
    }

    protected abstract List<Object> getModules();

    private void injectDependencies() {
        UpcomingVideogames abodeApplication = (UpcomingVideogames) getApplication();
        List<Object> activityScopeModules = getModules();
        activityScopeModules.add(new ActivityModule(this));
        activityScopeGraph = abodeApplication.plus(activityScopeModules);
        inject(this);
    }
}
