package com.antocistudios.uv.domain;


import com.antocistudios.business.controller.AppLogicController;
import com.antocistudios.business.controller.IApiController;
import com.antocistudios.business.controller.IEntityCacheController;
import com.antocistudios.business.controller.ISharedPrefController;
import com.antocistudios.business.logic.AppLogicControllerImpl;
import com.antocistudios.datalayer.controllerImpl.ApiControllerImpl;
import com.antocistudios.datalayer.controllerImpl.EntityCacheControllerImpl;
import com.antocistudios.datalayer.controllerImpl.SharedPrefControllerImpl;
import com.antocistudios.uv.presenter.GameClassifierPresenter;
import com.antocistudios.uv.presenter.MainActivityPresenter;
import com.antocistudios.uv.presenter.implementation.GameClassifierPresenterImpl;
import com.antocistudios.uv.presenter.implementation.MainActivityPresenterImpl;
import com.antocistudios.uv.ui.activity.MainActivity;
import com.antocistudios.uv.ui.fragment.GameClassifierFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Adrian Antoci.
 *
 */
@Module(
        injects = {MainActivity.class, GameClassifierFragment.class},
        complete = false,
        library = true
)
public class MainActivityModule {

    /*--------------FOR THE ACTIVITY-----------------*/

    @Provides
    MainActivityPresenter provideMainActivityPresenter(MainActivityPresenterImpl mainActivityPresenter){
        return mainActivityPresenter;
    }

    @Provides
    @Singleton//one instance for all the fragments, are all from the same instance
    GameClassifierPresenter provideGameClassifierPresenter(GameClassifierPresenterImpl gameClassifierPresenter){
        return gameClassifierPresenter;
    }

    /*--------------FOR THE BUSINESS-----------------*/
    @Provides
    @Singleton //Depends if we want the logic to be always the same instance
    AppLogicController provideMainLogin(AppLogicControllerImpl appLogicController) {
        return appLogicController;
    }

    /*--------------FOR THE DATA MODULE-----------------*/
    @Provides
    ISharedPrefController provideISharedPrefController(SharedPrefControllerImpl sharedPrefController) {
        return sharedPrefController;
    }

    @Provides
    IApiController provideApiControllerImpl(ApiControllerImpl apiController) {
        return apiController;
    }

    @Provides
    IEntityCacheController provideEntityCacheControllerImpl(EntityCacheControllerImpl entityCacheController) {
        return entityCacheController;
    }

}
