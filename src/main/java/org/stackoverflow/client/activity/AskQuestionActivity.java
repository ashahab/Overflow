package org.stackoverflow.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import org.stackoverflow.client.ClientFactory;
import org.stackoverflow.client.events.LoggedInEvent;
import org.stackoverflow.client.events.LoggedInEventHandler;
import org.stackoverflow.client.places.AskQuestionPlace;
import org.stackoverflow.client.view.AskQuestionView;

/**
 * Class description...
 *
 * @author ashahab
 */
public class AskQuestionActivity extends AbstractActivity implements AskQuestionView.Presenter{
  private ClientFactory _clientFactory;

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

      }
    });
  }

  @Override
  public void goTo(Place place) {
    //To change body of implemented methods use File | Settings | File Templates.
  }
}
