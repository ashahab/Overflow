package org.stackoverflow.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import org.stackoverflow.client.view.AskQuestionView;
import org.stackoverflow.client.view.HomeView;


/**
 * Class description...
 *
 * @author ashahab
 */
public interface ClientFactory
{
  EventBus getEventBus();
  PlaceController getPlaceController();
  HomeView getHomeView();

  AskQuestionView getAskQuestionView();

}
