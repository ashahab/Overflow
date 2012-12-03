package org.stackoverflow.client.places;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.PlaceTokenizer;
import org.stackoverflow.client.ClientFactory;
import org.stackoverflow.client.activity.LoginActivity;
import org.stackoverflow.client.activity.SearchActivity;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 12/1/12
 * Time: 11:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginPlace extends BaseProfilerPlace  {
    private String _token;
    public LoginPlace(String token){
        _token = token;
    }
    @Override
    public Activity createActivity(ClientFactory clientFactory) {
        return new LoginActivity(this, clientFactory);
    }

    public static class Tokenizer implements PlaceTokenizer<LoginPlace> {
        @Override
        public LoginPlace getPlace(String token) {
            return new LoginPlace(token);
        }

        @Override
        public String getToken(LoginPlace place) {
            return place._token;
        }
    }
}
