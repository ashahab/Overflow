package org.stackoverflow.client.places;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import org.stackoverflow.client.ClientFactory;
import org.stackoverflow.client.activity.AskQuestionActivity;

/**
 * Class description...
 *
 * @author ashahab
 */
public class AskQuestionPlace extends BaseProfilerPlace {
  private String _token;
  public AskQuestionPlace(String token){
    _token = token;
  }

    public String get_token() {
        return _token;
    }

    @Override
  public Activity createActivity(ClientFactory clientFactory) {
    return new AskQuestionActivity(this, clientFactory);
  }

  public static class Tokenizer implements PlaceTokenizer<AskQuestionPlace> {
    @Override
    public AskQuestionPlace getPlace(String token) {
      return new AskQuestionPlace(token);
    }

    @Override
    public String getToken(AskQuestionPlace place) {
      return place._token;
    }
  }
}
