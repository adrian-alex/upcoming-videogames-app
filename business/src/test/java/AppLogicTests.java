import com.antocistudios.business.controller.AppLogicController;
import com.antocistudios.business.logic.AppLogicControllerImpl;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.security.InvalidParameterException;
import java.util.List;

import mock.MockApiControllerImpl;
import mock.MockEntityCacheControllerImpl;
import mock.MockPrefControllerImpl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Adrian Antoci.
 *
 * The tests are made to unit test and validate the logic behavior.
 */

public class AppLogicTests {


    public AppLogicTests(){

    }

    @Test
    public void testOnViewAttachedInvalidParams(){
        AppLogicController mockStream = mock(AppLogicControllerImpl.class);
        doThrow(new InvalidParameterException()).when(mockStream).onViewAttached(null);
    }

    @Test
    public void testOnViewAttachedValidParams(){
        AppLogicController service = new AppLogicControllerImpl(new MockApiControllerImpl(1), new MockPrefControllerImpl(), new MockEntityCacheControllerImpl(false));

        AppLogicControllerImpl.UICallback event = mock(AppLogicControllerImpl.UICallback.class);

        service.onViewAttached(event);

        //check if the progress bar was displayed
        ArgumentCaptor<Boolean> progressBarCaptor = ArgumentCaptor.forClass(Boolean.class);
        verify(event, times(2)).onDisplayProgressBar(progressBarCaptor.capture());
        boolean capturedValue = progressBarCaptor.getValue();
        assertFalse(capturedValue);

        //check if results where returned
        ArgumentCaptor<List> moviesCaptor = ArgumentCaptor.forClass(List.class);

        verify(event, times(1)).addMovieCardList(moviesCaptor.capture());
        List capturedList = moviesCaptor.getValue();
        assertEquals(capturedList.size(), 1);
    }

    @Test
      public void testOnViewAttachedNoIC(){
        AppLogicController service = new AppLogicControllerImpl(new MockApiControllerImpl(2), new MockPrefControllerImpl(), new MockEntityCacheControllerImpl(false));

        AppLogicControllerImpl.UICallback event = mock(AppLogicControllerImpl.UICallback.class);

        service.onViewAttached(event);

        //check if the offline message was returned
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(event, times(1)).displayInfoMessage(messageCaptor.capture());
        String capturedValue = messageCaptor.getValue();
        assertNotNull(capturedValue);
    }

    @Test
    public void testOnViewAttachedFailed(){
        AppLogicController service = new AppLogicControllerImpl(new MockApiControllerImpl(-1), new MockPrefControllerImpl(), new MockEntityCacheControllerImpl(false));

        AppLogicControllerImpl.UICallback event = mock(AppLogicControllerImpl.UICallback.class);

        service.onViewAttached(event);

        //check if the error message was returned
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(event, times(1)).displayInfoMessage(messageCaptor.capture());
        String capturedValue = messageCaptor.getValue();
        assertNotNull(capturedValue);
    }

    @Test
    public void testOnViewAttachedNoICCachedInvoked(){
        AppLogicController service = new AppLogicControllerImpl(new MockApiControllerImpl(2), new MockPrefControllerImpl(), new MockEntityCacheControllerImpl(true));

        AppLogicControllerImpl.UICallback event = mock(AppLogicControllerImpl.UICallback.class);

        service.onViewAttached(event);

        //check if no internet connection the cache list was returned
        ArgumentCaptor<List> moviesCaptor = ArgumentCaptor.forClass(List.class);

        verify(event, times(1)).setMovieCardList(moviesCaptor.capture());
        List capturedList = moviesCaptor.getValue();
        assertEquals(capturedList.size(), 1);
    }





}
