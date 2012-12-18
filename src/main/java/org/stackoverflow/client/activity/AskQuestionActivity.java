package org.stackoverflow.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import org.stackoverflow.client.ClientFactory;
import org.stackoverflow.client.events.LoggedInEvent;
import org.stackoverflow.client.events.LoggedInEventHandler;
import org.stackoverflow.client.places.AskQuestionPlace;
import org.stackoverflow.client.places.ViewQuestionPlace;
import org.stackoverflow.client.service.OverflowService;
import org.stackoverflow.client.service.OverflowServiceAsync;
import org.stackoverflow.client.view.AskQuestionView;
import org.stackoverflow.shared.model.Question;

/**
 * Class description...
 *
 * @author ashahab
 */
public class AskQuestionActivity extends AbstractActivity implements AskQuestionView.Presenter{
  private ClientFactory _clientFactory;
  private AskQuestionPlace _place;
  private OverflowServiceAsync service = GWT.create(OverflowService.class);
  public AskQuestionActivity(AskQuestionPlace askQuestionPlace, ClientFactory clientFactory) {
    _clientFactory = clientFactory;
    _place = askQuestionPlace;
  }

  @Override
  public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
    final AskQuestionView view = _clientFactory.getAskQuestionView();
    //set the
    view.setPresenter(this);
    containerWidget.setWidget(view.asWidget());
    if(_place.get_token().equals("question")){
    view.getButton().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        String question = view.getNameField().getValue();
        String description = view.getEditor().getValue();
        //make async call to save to the db
        service.postQuestion(question, description, new AsyncCallback<Question>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert(throwable.toString());
            }

            @Override
            public void onSuccess(Question postedQuestion) {
//               if posted, then show the question posted
                //go to the viewQuestion
                goTo(new ViewQuestionPlace(postedQuestion.getId()));
            }
        });

      }
    });
    } else {

        service.findQuestion(_place.get_token(), new AsyncCallback<Question>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert(throwable.toString());
            }

            @Override
            public void onSuccess(final Question question) {
                view.getNameField().setValue(question.getQuery());
                view.getEditor().setValue(question.getDescription());

                view.getButton().addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent clickEvent) {
                        //create a question with the
                        question.setQuery(view.getNameField().getValue());
                        question.setDescription(view.getEditor().getValue());
                        service.updateQuestion(question, new AsyncCallback<Question>() {
                            @Override
                            public void onFailure(Throwable throwable) {
                                Window.alert(throwable.toString());
                            }

                            @Override
                            public void onSuccess(Question question) {
                                goTo(new ViewQuestionPlace(question.getId()));
                            }
                        });
                    }
                });
            }
        });
    }
  }

  @Override
  public void goTo(Place place) {
    _clientFactory.getPlaceController().goTo(place);
  }
}
