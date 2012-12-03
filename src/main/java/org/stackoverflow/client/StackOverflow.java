package org.stackoverflow.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.*;
import com.google.web.bindery.event.shared.EventBus;
import org.stackoverflow.client.mvp.AppActivityMapper;
import org.stackoverflow.client.mvp.AppPlaceHistoryMapper;
import org.stackoverflow.client.places.LoginPlace;
import org.stackoverflow.client.places.SearchPlace;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 9/17/12
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class StackOverflow implements EntryPoint {
  private Place _defaultPlace = new LoginPlace("home");
  private SimplePanel _appWidget = new SimplePanel();
  public void onModuleLoad() {
    ClientFactory clientFactory = GWT.create(ClientFactory.class);
    EventBus eventBus = clientFactory.getEventBus();
    PlaceController placeController = clientFactory.getPlaceController();
    ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
    ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
    activityManager.setDisplay(_appWidget);

    // Start PlaceHistoryHandler with our PlaceHistoryMapper
    AppPlaceHistoryMapper historyMapper= GWT.create(AppPlaceHistoryMapper.class);
    PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
    historyHandler.register(placeController, eventBus, _defaultPlace);

    RootPanel.get().add(_appWidget);
    // Goes to the place represented on URL else default place
    historyHandler.handleCurrentHistory();
  }
}
