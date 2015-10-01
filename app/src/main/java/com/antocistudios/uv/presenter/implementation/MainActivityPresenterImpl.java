package com.antocistudios.uv.presenter.implementation;

import com.antocistudios.business.controller.AppLogicController;
import com.antocistudios.business.logic.AppLogicControllerImpl;
import com.antocistudios.uv.presenter.MainActivityPresenter;
import com.antocistudios.uv.presenter.view.MainActivityView;

import javax.inject.Inject;

/**
 * Created by Adrian Antoci.
 *
 */
public class MainActivityPresenterImpl implements MainActivityPresenter {

    private MainActivityView mView;

    public AppLogicController mAppLogicController;

    @Inject
    public MainActivityPresenterImpl(AppLogicController appLogicController){
        mAppLogicController = appLogicController;
    }

    public void setView(MainActivityView view){
        mView = view;
    }

    @Override
    public void onTryAgain() {
        mAppLogicController.onTryAgain();
    }

    public void onViewAttached(){
        mAppLogicController.onActivityViewAttached(activityUICallback);
    }

    public void onViewDetached(){
        mAppLogicController.onActivityViewDetached();
    }

    private AppLogicControllerImpl.ActivityUICallback activityUICallback = new AppLogicControllerImpl.ActivityUICallback() {
        @Override
        public void displayInfoMessage(String msg) {
            mView.displayInfoMessage(msg);
        }
    };
}
