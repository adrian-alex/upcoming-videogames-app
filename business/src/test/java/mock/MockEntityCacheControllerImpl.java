package mock;

import com.antocistudios.business.controller.IEntityCacheController;
import com.antocistudios.business.entity.VideoGame;

import java.util.ArrayList;
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
    public void store(List<VideoGame> itemList) {

    }

    @Override
    public void clear() {

    }

    @Override
    public List<VideoGame> getCachedList() {
        if (mReturnValues){
            List<VideoGame> movies = new ArrayList();
            VideoGame videoGame = new VideoGame(-1,"Test movie","Test URL");
            movies.add(videoGame);
            return movies;
        }
        return null;
    }
}
