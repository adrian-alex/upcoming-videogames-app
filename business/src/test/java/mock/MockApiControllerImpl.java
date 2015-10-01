package mock;

import com.antocistudios.business.callback.ApiFeedCallback;
import com.antocistudios.business.controller.IApiController;
import com.antocistudios.business.entity.VideoGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian Antoci.
 *
 */
public class MockApiControllerImpl implements IApiController {

    private int mResponseType;

    public MockApiControllerImpl(int requiredResultType){
        mResponseType = requiredResultType;
    }

    @Override
    public void requestFeedItems(int startFrom, int numberOfElements, ApiFeedCallback apiFeedCallback) {
        List<VideoGame> movies = new ArrayList();
        VideoGame videoGame = new VideoGame(-1,"Test movie","Test URL");
        movies.add(videoGame);

        apiFeedCallback.onResult(platform, movies, mResponseType);
    }

    @Override
    public boolean cancelRunningRequests() {
        return true;
    }
}
