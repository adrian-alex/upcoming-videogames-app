package com.antocistudios.uv.presenter.implementation;

import com.antocistudios.business.controller.AppLogicController;
import com.antocistudios.business.entity.VideoGame;
import com.antocistudios.business.logic.AppLogicControllerImpl;
import com.antocistudios.uv.presenter.GameClassifierPresenter;
import com.antocistudios.uv.presenter.event.DisplayGameList;

import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

/**
 * Created by Adrian Antoci.
 *
 */
public class GameClassifierPresenterImpl implements GameClassifierPresenter {

    public AppLogicController mAppLogicController;

    @Inject
    public GameClassifierPresenterImpl(AppLogicController appLogicController){
        mAppLogicController = appLogicController;
    }

    @Override
    public void onFragmentViewAttached(int platform) {
        mAppLogicController.onFragmentSelected(platform, fragmentUICallback);
    }

    @Override
    public void onFragmentViewDetached() {
    }

    private AppLogicControllerImpl.GameClassifierUICallback fragmentUICallback = new AppLogicControllerImpl.GameClassifierUICallback() {
        @Override
        public void displayItems(int platform, List<VideoGame> itemList) {
            EventBus.getDefault().post(new DisplayGameList(platform, itemList));
        }
    };
}
