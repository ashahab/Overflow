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
  private OverflowServiceAsync service = GWT.create(OverflowService.class);
  public AskQuestionActivity(AskQuestionPlace askQuestionPlace, ClientFactory clientFactory) {
    _clientFactory = clientFactory;
  }

  @Override
  public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
    final AskQuestionView view = _clientFactory.getAskQuestionView();
    //set the
    view.setPresenter(this);
    containerWidget.setWidget(view.asWidget());
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
  }

  @Override
  public void goTo(Place place) {
    _clientFactory.getPlaceController().goTo(place);
  }
}
