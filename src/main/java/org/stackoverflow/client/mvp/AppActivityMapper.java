package org.stackoverflow.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import org.stackoverflow.client.ClientFactory;
import org.stackoverflow.client.places.BaseProfilerPlace;

public class AppActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;

	/**
	 * AppActivityMapper associates each Place with its corresponding
	 * {@link com.google.gwt.activity.shared.Activity}
	 * 
	 * @param clientFactory
	 *            Factory to be passed to activities
	 */
	public AppActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	/**
	 * Map each Place to its corresponding Activity. This would be a great use
	 * for GIN.
	 */
	@Override
	public Activity getActivity(Place place) {
    if(place instanceof BaseProfilerPlace){
      BaseProfilerPlace baseProfilerPlace = (BaseProfilerPlace) place;
      return baseProfilerPlace.createActivity(clientFactory);
    }
		return null;
	}

}
