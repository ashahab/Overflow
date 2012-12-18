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
import org.stackoverflow.client.places.AskQuestionPlace;
import org.stackoverflow.client.places.ViewQuestionPlace;
import org.stackoverflow.client.service.OverflowService;
import org.stackoverflow.client.service.OverflowServiceAsync;
import org.stackoverflow.client.view.ViewQuestionView;
import org.stackoverflow.shared.model.Question;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 12/16/12
 * Time: 8:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class ViewQuestionActivity extends AbstractActivity implements ViewQuestionView.Presenter{
    private ClientFactory _clientFactory;
    private OverflowServiceAsync service = GWT.create(OverflowService.class);
    private ViewQuestionPlace _place;

    public ViewQuestionActivity(ViewQuestionPlace place, ClientFactory clientFactory){
        _clientFactory = clientFactory;
        _place = place;
    }
    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        final ViewQuestionView view = _clientFactory.getViewQuestionView();
        //set the
        view.setPresenter(this);
        containerWidget.setWidget(view.asWidget());
        service.findQuestion(_place.getQuestionId(), new AsyncCallback<Question>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert(throwable.toString());
            }

            @Override
            public void onSuccess(final Question question) {
                view.getQueryLabel().setHTML(question.getQuery());
                view.getDescLabel().setHTML(question.getDescription());
                view.getPostedLabel().setHTML(question.postedOn().toString());
                view.getUserLabel().setHTML(question.author().getName());
                view.getEditButton().addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent clickEvent) {
                        goTo(new AskQuestionPlace(question.getId() + ""));
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
