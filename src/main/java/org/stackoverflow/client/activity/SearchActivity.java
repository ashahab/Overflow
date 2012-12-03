package org.stackoverflow.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import org.stackoverflow.client.ClientFactory;
import org.stackoverflow.client.places.SearchPlace;
import org.stackoverflow.client.view.SearchView;

public class SearchActivity extends AbstractActivity implements
        SearchView.Presenter {
    // Used to obtain views, eventBus, placeController
    // Alternatively, could be injected via GIN
    private ClientFactory _clientFactory;

    public SearchActivity(SearchPlace place, ClientFactory clientFactory) {
        this._clientFactory = clientFactory;
        //
    }

    /**
     * Invoked by the ActivityManager to start a new Activity
     */
    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        final SearchView searchView = _clientFactory.getHomeView();
        //set the
        searchView.setPresenter(this);
        containerWidget.setWidget(searchView.asWidget());
    }

    /**
     * Navigate to a new Place in the browser
     */
    public void goTo(Place place) {
        _clientFactory.getPlaceController().goTo(place);
    }
}
