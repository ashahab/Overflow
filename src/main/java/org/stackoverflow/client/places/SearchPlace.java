package org.stackoverflow.client.places;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.PlaceTokenizer;
import org.stackoverflow.client.ClientFactory;
import org.stackoverflow.client.activity.SearchActivity;

/**
 * Class description...
 *
 * @author ashahab
 */
public class SearchPlace extends BaseProfilerPlace {
  private String _token;
  public SearchPlace(String token){
    _token = token;
  }
  @Override
  public Activity createActivity(ClientFactory clientFactory) {
    return new SearchActivity(this, clientFactory);
  }

  public static class Tokenizer implements PlaceTokenizer<SearchPlace> {
    @Override
    public SearchPlace getPlace(String token) {
      return new SearchPlace(token);
    }

    @Override
    public String getToken(SearchPlace place) {
      return place._token;
    }
  }
}
