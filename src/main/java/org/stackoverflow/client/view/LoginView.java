package org.stackoverflow.client.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 12/1/12
 * Time: 10:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface LoginView extends IsWidget {
    void setPresenter(Presenter presenter);

    /**
     * Hooks where clicking a link should take this
     */
    public interface Presenter {
        void goTo(Place place);

        String login(String text);
    }
}
