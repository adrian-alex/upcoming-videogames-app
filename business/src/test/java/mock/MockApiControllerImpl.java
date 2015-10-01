package mock;

import com.antocistudios.business.callback.ApiFeedCallback;
import com.antocistudios.business.controller.IApiController;
import com.antocistudios.business.entity.VideoGame;

import java.util.ArrayList;
import java.util.Date;
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
    public void fetchGamesByPlatform(int platform, ApiFeedCallback apiFeedCallback) {
        List<VideoGame> movies = new ArrayList<>();

        VideoGame videoGame = new VideoGame();
        videoGame.setShortDescription("Test short description");
        videoGame.setPlatform(platform);
        videoGame.setTitle("Test title");
        videoGame.setReleaseDate(new Date());
        videoGame.setDescription("Test description");
        videoGame.setPosterURL("https://google.com");

        movies.add(videoGame);

        apiFeedCallback.onResult(platform, movies, mResponseType);
    }

    @Override
    public void fetchGamesByDate(Date date, ApiFeedCallback apiFeedCallback) {

    }

    @Override
    public boolean cancelRunningRequests() {
        return true;
    }
}
