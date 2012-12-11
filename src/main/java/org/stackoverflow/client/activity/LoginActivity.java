package org.stackoverflow.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import org.stackoverflow.client.ClientFactory;
import org.stackoverflow.client.events.LoggedInEvent;
import org.stackoverflow.client.places.LoginPlace;
import org.stackoverflow.client.places.SearchPlace;
import org.stackoverflow.client.service.OverflowService;
import org.stackoverflow.client.service.OverflowServiceAsync;
import org.stackoverflow.client.view.LoginView;
import org.stackoverflow.client.view.SearchView;
import org.stackoverflow.shared.model.User;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 12/1/12
 * Time: 11:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginActivity extends AbstractActivity implements
        LoginView.Presenter {
    private ClientFactory _clientFactory;
    private OverflowServiceAsync service = GWT.create(OverflowService.class);

    public LoginActivity(LoginPlace loginPlace, ClientFactory clientFactory) {
        _clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        final LoginView searchView = _clientFactory.getLoginView();
        //set the
        searchView.setPresenter(this);
        containerWidget.setWidget(searchView.asWidget());
    }

    @Override
    public void goTo(Place place) {
        _clientFactory.getPlaceController().goTo(place);
    }

    @Override
    public String login(final String userName) {

        service.login(userName, new AsyncCallback<User>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert(throwable.toString());
            }

            @Override
            public void onSuccess(User user) {
                _clientFactory.getEventBus().fireEvent(new LoggedInEvent(user));
                String sessionID = user.getId();
                final long DURATION = 1000 * 60 * 60 * 24 * 14; //duration remembering login. 2 weeks in this example.
                Date expires = new Date(System.currentTimeMillis() + DURATION);
                Cookies.setCookie("sid", sessionID, expires, null, "/", false);
                goTo(new SearchPlace(user.getName()));
            }
        }

        );
        return null;
    }
}
