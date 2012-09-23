package org.stackoverflow.client.places;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;
import org.stackoverflow.client.ClientFactory;

/**
 * Class description...
 *
 * @author ashahab
 */
public abstract class BaseProfilerPlace extends Place {
  public abstract Activity createActivity(ClientFactory clientFactory);
}
