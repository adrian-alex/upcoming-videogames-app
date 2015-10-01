package com.antocistudios.uv.presenter;

import com.antocistudios.uv.presenter.view.MainActivityView;

/**
 * Created by Adrian Antoci.
 *
 */
public interface MainActivityPresenter {
     void onViewAttached();
     void onViewDetached();
     void setView(MainActivityView view);
     void onTryAgain();
}
