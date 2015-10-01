package com.antocistudios.business.callback;

import com.antocistudios.business.entity.VideoGame;

import java.util.List;

/**
 * Created by Adrian Antoci.
 */
public interface ApiFeedCallback {
    void onResult(int platform, List<VideoGame> videoGameList, int responseCode);
}
