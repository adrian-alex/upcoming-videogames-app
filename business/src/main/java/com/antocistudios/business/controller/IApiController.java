package com.antocistudios.business.controller;

import com.antocistudios.business.callback.ApiFeedCallback;

import java.util.Date;

/**
 * Created by Adrian Antoci.
 *
 */
public interface IApiController {
    void fetchGamesByPlatform(int platform, ApiFeedCallback apiFeedCallback);
    void fetchGamesByDate(Date date, ApiFeedCallback apiFeedCallback);
    boolean cancelRunningRequests();
}
