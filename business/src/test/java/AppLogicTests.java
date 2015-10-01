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
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Matchers.anyInt;
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
        doThrow(new InvalidParameterException()).when(mockStream).onActivityViewAttached(null);
    }

    @Test
    public void testOnViewAttachedValidParams(){
        AppLogicController logic = new AppLogicControllerImpl(new MockApiControllerImpl(1), new MockPrefControllerImpl(), new MockEntityCacheControllerImpl(false));

        AppLogicControllerImpl.GameClassifierUICallback event = mock(AppLogicControllerImpl.GameClassifierUICallback.class);

        logic.onFragmentSelected(1, event);

        //check if results where returned
        ArgumentCaptor<List> moviesCaptor = ArgumentCaptor.forClass(List.class);

        verify(event, times(1)).displayItems(anyInt(),moviesCaptor.capture());
        List capturedList = moviesCaptor.getValue();
        assertEquals(capturedList.size(), 1);
    }

    @Test
      public void testOnApiNoIC(){
        AppLogicController service = new AppLogicControllerImpl(new MockApiControllerImpl(2), new MockPrefControllerImpl(), new MockEntityCacheControllerImpl(false));

        AppLogicControllerImpl.ActivityUICallback activityUICallback = mock(AppLogicControllerImpl.ActivityUICallback.class);
        AppLogicControllerImpl.GameClassifierUICallback fragmentUICallback = mock(AppLogicControllerImpl.GameClassifierUICallback.class);

        service.onActivityViewAttached(activityUICallback);
        service.onFragmentSelected(1, fragmentUICallback);

        //check if the offline message was returned
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(activityUICallback, times(1)).displayInfoMessage(messageCaptor.capture());
        String capturedValue = messageCaptor.getValue();
        assertNotNull(capturedValue);
    }

    @Test
    public void testOnApiCallFailed(){
        AppLogicController service = new AppLogicControllerImpl(new MockApiControllerImpl(-1), new MockPrefControllerImpl(), new MockEntityCacheControllerImpl(false));

        AppLogicControllerImpl.ActivityUICallback activityUICallback = mock(AppLogicControllerImpl.ActivityUICallback.class);
        AppLogicControllerImpl.GameClassifierUICallback fragmentUICallback = mock(AppLogicControllerImpl.GameClassifierUICallback.class);

        service.onActivityViewAttached(activityUICallback);
        service.onFragmentSelected(1, fragmentUICallback);

        //check if the error message was returned
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(activityUICallback, times(1)).displayInfoMessage(messageCaptor.capture());
        String capturedValue = messageCaptor.getValue();
        assertNotNull(capturedValue);
    }

    @Test
    public void testOnViewAttachedNoICCachedInvoked(){
        AppLogicController service = new AppLogicControllerImpl(new MockApiControllerImpl(2), new MockPrefControllerImpl(), new MockEntityCacheControllerImpl(true));

        AppLogicControllerImpl.ActivityUICallback activityUICallback = mock(AppLogicControllerImpl.ActivityUICallback.class);
        AppLogicControllerImpl.GameClassifierUICallback fragmentUICallback = mock(AppLogicControllerImpl.GameClassifierUICallback.class);

        service.onActivityViewAttached(activityUICallback);
        service.onFragmentSelected(1, fragmentUICallback);

        //check if no internet connection the cache list was returned
        ArgumentCaptor<List> itemsCaptor = ArgumentCaptor.forClass(List.class);

        verify(fragmentUICallback, times(1)).displayItems(anyInt(), itemsCaptor.capture());
        List capturedList = itemsCaptor.getValue();
        assertEquals(capturedList.size(), 1);
    }





}
