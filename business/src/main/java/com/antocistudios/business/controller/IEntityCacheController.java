package com.antocistudios.business.controller;

import com.antocistudios.business.entity.VideoGame;

import java.util.List;

/**
 * Created by Adrian Antoci.
 *
 */
public interface IEntityCacheController {
    void store(int platform, List<VideoGame> itemList);
    void clear();
    List<VideoGame> getCachedList(int platform);
}
