package com.antocistudios.datalayer.controllerImpl;

import android.content.Context;

import com.antocistudios.business.Const;
import com.antocistudios.business.callback.ApiFeedCallback;
import com.antocistudios.business.controller.IApiController;
import com.antocistudios.business.entity.VideoGame;
import com.antocistudios.datalayer.async.GamesFetchAsyncTask;
import com.antocistudios.datalayer.async.callback.GamesFetchResponse;
import com.antoistudios.manele.gameEndpoint.model.Game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Adrian Antoci.
 *
 * The class responsibility is to execute and handle the backend responses.
 *
 */
public class ApiControllerImpl implements IApiController {

    private Context mContext;

    @Inject
    public ApiControllerImpl(Context context){
        mContext = context;
    }

    @Override
    public void fetchGamesByPlatform(final int platform, final ApiFeedCallback apiFeedCallback) {

        new GamesFetchAsyncTask(mContext, platform, new GamesFetchResponse() {
            @Override
            public void onResponse(int platform, List<Game> responseDTOList, int responseCode) {
                handleResponse(responseDTOList, responseCode, platform, apiFeedCallback);
            }
        }).execute();
    }

    @Override
    public void fetchGamesByDate(Date date, ApiFeedCallback apiFeedCallback) {
        //TODO
    }

    @Override
    public boolean cancelRunningRequests() {
        return true;
    }

    /**
     * Handles the backend response.
     *
     * @param responseDTOList
     * @param responseCode
     * @param platform
     * @param apiFeedCallback
     */
    private void handleResponse(List<Game> responseDTOList, int responseCode, int platform, ApiFeedCallback apiFeedCallback) {
        switch (responseCode){
            case Const.RESPONSE_CODE_FAIL:
            case Const.RESPONSE_CODE_NO_IC:
                apiFeedCallback.onResult(platform, null, responseCode);
                break;
            case Const.RESPONSE_CODE_SUCCESS:
                List<VideoGame> itemList = convertToBusinessEntity(responseDTOList, platform);
                apiFeedCallback.onResult(platform, itemList, responseCode);
                break;
        }
    }

    /**
     * Converts the backend DTO to business entity.
     *
     * @param responseDTOList List of DTOs
     * @param platform
     * @return List of movie business entities
     */
    private List<VideoGame> convertToBusinessEntity(List<Game> responseDTOList, int platform) {
        List<VideoGame> entityList = new ArrayList<>();

        for (int i=0;i<responseDTOList.size();i++){

            Game gameDTO =  responseDTOList.get(i);

            VideoGame videoGame = new VideoGame();
            videoGame.setDescription(gameDTO.getDescription());
            videoGame.setPosterURL(gameDTO.getPosterURL());
            videoGame.setTitle(gameDTO.getTitle());
            videoGame.setReleaseDate(new Date(gameDTO.getReleaseDate().getValue()));
            videoGame.setPlatform(platform);

            entityList.add(videoGame);
        }
        return entityList;
    }


}
