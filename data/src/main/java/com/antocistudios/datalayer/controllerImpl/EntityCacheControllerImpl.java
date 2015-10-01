package com.antocistudios.datalayer.controllerImpl;

import android.content.Context;
import android.content.SharedPreferences;

import com.antocistudios.business.controller.IEntityCacheController;
import com.antocistudios.business.entity.VideoGame;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

/**
 * Created by Adrian Antoci.
 *
 * The class responsibility is to cache and retrieve entities.
 */
public class EntityCacheControllerImpl implements IEntityCacheController {

    private ExecutorService mThreadExecutor = Executors.newSingleThreadExecutor();
    private final SharedPreferences mSharedPreferences;

    @Inject
    public EntityCacheControllerImpl(Context context){
        mSharedPreferences = context.getSharedPreferences(this.getClass().getCanonicalName(), Context.MODE_PRIVATE);
    }

    @Override
    public void store(final int platform, final List<VideoGame> itemList) {
        mThreadExecutor.execute(new Runnable() {//put in a serial queue, just in case is heavy
            @Override
            public void run() {
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                String rawString = new Gson().toJson(itemList.toArray(new VideoGame[itemList.size()]));
                editor.putString(String.valueOf(platform), rawString);
                editor.apply();
            }
        });
    }

    @Override
    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }

    @Override
    public List<VideoGame> getCachedList(int platform) {
        String rawString = mSharedPreferences.getString(String.valueOf(platform), null);
        if (rawString!=null){
            return Arrays.asList(new Gson().fromJson(rawString, VideoGame[].class));
        }
        return null;
    }
}
