package com.antocistudios.business.logic;

import com.antocistudios.business.Const;
import com.antocistudios.business.callback.ApiFeedCallback;
import com.antocistudios.business.controller.AppLogicController;
import com.antocistudios.business.controller.IApiController;
import com.antocistudios.business.controller.IEntityCacheController;
import com.antocistudios.business.controller.ISharedPrefController;
import com.antocistudios.business.entity.VideoGame;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Adrian Antoci.
 *
 * The main logic. It contains the dependencies that are going to be used in order to run the app.
 * More components can be added and be converged here if necessary.
 *
 * It is also possible to have a base logic that will handle common activity events and then a feature specific logic.
 * This could happen then the app becomes more complex.
 *
 *
 * For the purpose of this demo everything will be handled here for convenience.
 *
 */
public class AppLogicControllerImpl implements AppLogicController{

    public interface ActivityUICallback{
        void displayInfoMessage(String msg);
    }

    public interface GameClassifierUICallback{
        void displayItems(int platform, List<VideoGame> itemList);
    }

    public IApiController mApiController; //from here we can make all the network calls
    public ISharedPrefController mSharedPrefController; //local persistence
    public IEntityCacheController mEntityCacheController; //we can use this controller to cache entities when required.

    private ActivityUICallback mActivityUICallback; // All the UI related events can be called from here
    private GameClassifierUICallback mGameClassifierUICallback;

    private int mCurrentSelectedPlatform;

    /**
     * Default constructor
     */
    @Inject
    public AppLogicControllerImpl(IApiController apiController,
                                  ISharedPrefController sharedPrefController,
                                  IEntityCacheController entityCacheController){
        mApiController = apiController;
        mSharedPrefController = sharedPrefController;
        mEntityCacheController = entityCacheController;
    }


    /**
     * MainActivity view attached event
     */
    @Override
    public void onActivityViewAttached(ActivityUICallback callback){
        if (callback == null) {
            throw new InvalidParameterException("The view callback cannot be null");
        }

        mActivityUICallback = callback;
    }

    /**
     * MainActivity view detached event
     */
    @Override
    public void onActivityViewDetached(){
        mApiController.cancelRunningRequests();
        mActivityUICallback = null;
        mGameClassifierUICallback = null;
    }

    /**
     *  Should be called every time the fragment is selected
     *
     * @param platform The fragment platform
     */
    @Override
    public void onFragmentSelected(int platform, AppLogicControllerImpl.GameClassifierUICallback callback){
        if (mGameClassifierUICallback == null) {
            mGameClassifierUICallback = callback;
        }
        mCurrentSelectedPlatform = platform;
        //fetch
        mApiController.fetchGamesByPlatform(platform, apiFeedCallback);
    }


    @Override
    public void onTryAgain() {
        mApiController.fetchGamesByPlatform(mCurrentSelectedPlatform, apiFeedCallback);
    }


    private ApiFeedCallback apiFeedCallback = new ApiFeedCallback() {
        @Override
        public void onResult(int platform, List<VideoGame> itemList, int responseCode) {
            processApiFeedResponse(platform, itemList, responseCode);
        }
    };

    private void processApiFeedResponse(int platform, List<VideoGame> itemList, int responseCode) {

        switch (responseCode){
            case Const.RESPONSE_CODE_SUCCESS:
                boolean isValid = validateMovieEntityList(itemList);
                if (isValid) {
                    //convert date to short text description
                    itemList = generateShortDescription(itemList);

                    //send the data to the UI
                    mGameClassifierUICallback.displayItems(platform, itemList);
                    //cache it
                    mEntityCacheController.store(platform, itemList);

                } else {
                   throw new IllegalArgumentException("invalid entities");
                }
                break;

            case Const.RESPONSE_CODE_FAIL:
                mActivityUICallback.displayInfoMessage("Please try again");
                break;

            case Const.RESPONSE_CODE_NO_IC:
                mActivityUICallback.displayInfoMessage("You are offline");
                List<VideoGame> cachedList = mEntityCacheController.getCachedList(platform);

                if (cachedList != null) {//if we have something cached then display
                    //send the data to the UI
                    mGameClassifierUICallback.displayItems(platform, cachedList);
                }
                break;

            default:
                throw new RuntimeException("Unhandled response event");

        }
    }

    private List<VideoGame> generateShortDescription(List<VideoGame> itemList) {
        List<VideoGame> result = new ArrayList<>();
        Date today = new Date();
        
        for (VideoGame videoGame : itemList){
            long days = calculateDaysDifference(today, videoGame.getReleaseDate());
            if (days < 30){
                videoGame.setShortDescription(days +" days left");
            }else if (days >30 && days < 60){
                videoGame.setShortDescription("1 month left");
            }else if (days >60 && days < 90){
                videoGame.setShortDescription("2 months left");
            }else{
                boolean sameYear = sameYear(today, videoGame.getReleaseDate());
                if (sameYear){
                    videoGame.setShortDescription("in 3 months");
                }else{
                    videoGame.setShortDescription("next year");
                }
            }
            result.add(videoGame);
        }
        return result;
    }

    private boolean sameYear(Date today, Date releaseDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int thisYear = cal.get(Calendar.YEAR);
        cal.setTime(releaseDate);
        int releaseYear = cal.get(Calendar.YEAR);
        return thisYear == releaseYear;
    }

    private long calculateDaysDifference(Date today, Date releaseDate) {
        long diff = releaseDate.getTime() - today.getTime();
        return diff / (24 * 60 * 60 * 1000);
    }

    private boolean validateMovieEntityList(List<VideoGame> itemList) {
        for (VideoGame videoGame : itemList){
            if (videoGame.getPosterURL() == null || videoGame.getPosterURL().isEmpty() ||
                videoGame.getTitle() == null || videoGame.getTitle().isEmpty()) {
                return false;
            }
        }
        return true;
    }

}
