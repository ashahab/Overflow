package org.stackoverflow.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import org.stackoverflow.client.ClientFactory;
import org.stackoverflow.client.places.HomePlace;
import org.stackoverflow.client.view.HomeView;

public class HomeActivity extends AbstractActivity implements
		HomeView.Presenter {
	// Used to obtain views, eventBus, placeController
	// Alternatively, could be injected via GIN
	private ClientFactory _clientFactory;

	public HomeActivity(HomePlace place, ClientFactory clientFactory) {
		this._clientFactory = clientFactory;
    //
	}

	/**
	 * Invoked by the ActivityManager to start a new Activity
	 */
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		
	}


	/**
	 * Ask user before stopping this activity
	 */
	@Override
	public String mayStop() {
		return "Please hold on. This activity is stopping.";
	}

	/**
	 * Navigate to a new Place in the browser
	 */
	public void goTo(Place place) {
		_clientFactory.getPlaceController().goTo(place);
	}
}
