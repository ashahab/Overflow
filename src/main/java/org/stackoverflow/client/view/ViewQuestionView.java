package org.stackoverflow.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 12/16/12
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ViewQuestionView extends IsWidget{
    public void setPresenter(Presenter presenter);

    HasText getQueryLabel();

    HasText getDescLabel();

    HasClickHandlers getEditButton();

    HasText getPostedLabel();

    HasText getUserLabel();

    public interface Presenter {
        void goTo(Place place);
    }
}
