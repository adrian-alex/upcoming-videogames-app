package com.antocistudios.uv.presenter.event;

import com.antocistudios.business.entity.VideoGame;

import java.util.List;

/**
 * Created by Adrian Antoci.
 */
public class DisplayGameList {
    private List<VideoGame> itemList;
    private int platform;

    public DisplayGameList(int platform, List<VideoGame> itemList) {
        this.itemList = itemList;
        this.platform = platform;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public List<VideoGame> getItemList() {
        return itemList;
    }

    public void setItemList(List<VideoGame> itemList) {
        this.itemList = itemList;
    }
}
