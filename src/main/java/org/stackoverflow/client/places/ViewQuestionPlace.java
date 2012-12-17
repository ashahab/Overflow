package org.stackoverflow.client.places;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.PlaceTokenizer;
import org.stackoverflow.client.ClientFactory;
import org.stackoverflow.client.activity.ViewQuestionActivity;
import org.stackoverflow.shared.model.Question;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 12/16/12
 * Time: 8:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class ViewQuestionPlace extends BaseProfilerPlace {
    private String _question;
    public ViewQuestionPlace(String postedQuestion) {
       _question = postedQuestion;
    }

    @Override
    public Activity createActivity(ClientFactory clientFactory) {
        return new ViewQuestionActivity(this, clientFactory);

    }

    public String getQuestionId() {
        return _question;
    }

    public static class Tokenizer implements PlaceTokenizer<ViewQuestionPlace> {
        @Override
        public ViewQuestionPlace getPlace(String token) {
            return new ViewQuestionPlace(token);
        }

        @Override
        public String getToken(ViewQuestionPlace place) {
            return place.getQuestionId();
        }
    }
}
