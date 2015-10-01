package com.antocistudios.datalayer.async;

import android.content.Context;
import android.os.AsyncTask;

import com.antocistudios.business.Const;
import com.antocistudios.datalayer.Utils;
import com.antocistudios.datalayer.async.callback.GamesFetchResponse;
import com.antoistudios.manele.gameEndpoint.GameEndpoint;
import com.antoistudios.manele.gameEndpoint.model.CollectionResponseGame;
import com.antoistudios.manele.gameEndpoint.model.Game;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.List;

/**
 * Created by Adrian Antoci.
 *
 * The async task that executes the call to Google AppEngine and returns the response DTO.
 * The first time we call the endpoint it will take a bit longer until the endpoint awakes.
 */
public class GamesFetchAsyncTask extends AsyncTask<Void, Void, List<Game>> {

    private GameEndpoint mEndpointService = null;
    private GamesFetchResponse mCallback;
    private int mPlatform;
    private Context mContext;

    public GamesFetchAsyncTask(Context context, int platform, GamesFetchResponse callback) {
        mCallback = callback;
        mPlatform = platform;
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        GameEndpoint.Builder builder = new GameEndpoint.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);    }
                });
        mEndpointService = builder.build();
    }
    @Override
    protected List<Game> doInBackground(Void... params) {
        try {
            CollectionResponseGame result = mEndpointService.fetchGamesByPlatform(mPlatform).execute();
            return result.getItems();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Game> response) {
        super.onPostExecute(response);

        int responseCode = Const.RESPONSE_CODE_FAIL;

        if (response != null){
            responseCode = Const.RESPONSE_CODE_SUCCESS;
        }else if (!Utils.hasIC(mContext)){
            responseCode = Const.RESPONSE_CODE_NO_IC;
        }

        mCallback.onResponse(mPlatform, response, responseCode);
    }
}

