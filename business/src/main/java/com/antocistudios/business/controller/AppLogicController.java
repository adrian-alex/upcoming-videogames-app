package com.antocistudios.business.controller;

import com.antocistudios.business.logic.AppLogicControllerImpl;

/**
 * Created by Adrian Antoci.
 *
 */
public interface AppLogicController {

     void onActivityViewAttached(AppLogicControllerImpl.ActivityUICallback callback);
     void onActivityViewDetached();
     void onFragmentSelected(int fragmentIndex, AppLogicControllerImpl.GameClassifierUICallback callback);
     void onTryAgain();

}
