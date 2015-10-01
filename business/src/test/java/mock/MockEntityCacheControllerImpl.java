package mock;

import com.antocistudios.business.controller.IEntityCacheController;
import com.antocistudios.business.entity.VideoGame;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Adrian Antoci.
 *
 */
public class MockEntityCacheControllerImpl implements IEntityCacheController {

    private boolean mReturnValues;
    public MockEntityCacheControllerImpl(boolean returnValues){
        mReturnValues = returnValues;
    }


    @Override
    public void store(int platform, List<VideoGame> itemList) {

    }

    @Override
    public void clear() {

    }

    @Override
    public List<VideoGame> getCachedList(int platform) {
        List<VideoGame> movies = new ArrayList<>();

        VideoGame videoGame = new VideoGame();
        videoGame.setShortDescription("Test short description");
        videoGame.setPlatform(platform);
        videoGame.setTitle("Test title");
        videoGame.setReleaseDate(new Date());
        videoGame.setDescription("Test description");
        videoGame.setPosterURL("https://google.com");

        movies.add(videoGame);

        return movies;
    }
}
