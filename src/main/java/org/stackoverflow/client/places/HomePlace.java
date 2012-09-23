package org.stackoverflow.client.places;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.PlaceTokenizer;
import org.stackoverflow.client.ClientFactory;
import org.stackoverflow.client.activity.HomeActivity;

/**
 * Class description...
 *
 * @author ashahab
 */
public class HomePlace extends BaseProfilerPlace {
  @Override
  public Activity createActivity(ClientFactory clientFactory) {
    return new HomeActivity(this, clientFactory);
  }

  public static class Tokenizer implements PlaceTokenizer<HomePlace> {
    @Override
    public HomePlace getPlace(String token) {
      return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getToken(HomePlace place) {
      return null;
    }
  }
}
