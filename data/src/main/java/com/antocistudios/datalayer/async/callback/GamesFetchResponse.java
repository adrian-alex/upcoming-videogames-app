package com.antocistudios.datalayer.async.callback;


import com.antoistudios.manele.gameEndpoint.model.Game;

import java.util.List;

/**
 * Created by Adrian Antoci.
 *
 */
public interface GamesFetchResponse {
    void onResponse(int platform, List<Game> gameList, int responseCode);
}
