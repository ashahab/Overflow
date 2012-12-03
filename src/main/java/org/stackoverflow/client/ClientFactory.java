package org.stackoverflow.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import org.stackoverflow.client.view.AskQuestionView;
import org.stackoverflow.client.view.LoginView;
import org.stackoverflow.client.view.SearchView;


/**
 * Class description...
 *
 * @author ashahab
 */
public interface ClientFactory
{
  EventBus getEventBus();
  PlaceController getPlaceController();
  SearchView getHomeView();

  AskQuestionView getAskQuestionView();

    LoginView getLoginView();

}
