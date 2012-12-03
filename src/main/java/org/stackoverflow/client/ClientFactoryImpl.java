package org.stackoverflow.client;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import org.stackoverflow.client.view.*;


public class ClientFactoryImpl implements ClientFactory {
    private static final EventBus eventBus = new SimpleEventBus();
    private static final PlaceController placeController = new PlaceController(eventBus);


    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public PlaceController getPlaceController() {
        return placeController;
    }

    @Override
    public SearchView getHomeView() {
        return new SearchViewImpl();
    }

    @Override
    public AskQuestionView getAskQuestionView() {
        return new AskQuestionViewImpl();
    }

    @Override
    public LoginView getLoginView() {
        return new LoginViewImpl();
    }
}
