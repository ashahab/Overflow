package org.stackoverflow.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widget.client.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import org.stackoverflow.client.places.AskQuestionPlace;
import org.stackoverflow.client.places.SearchPlace;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 12/1/12
 * Time: 10:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginViewImpl extends Composite implements LoginView {
    private Presenter _presenter;
    private VerticalLayoutContainer _panel;

    public LoginViewImpl() {
        Viewport viewport = new Viewport();
        _panel = new VerticalLayoutContainer();
        HorizontalLayoutContainer horiz = new HorizontalLayoutContainer();
        final TextField userField = new TextField();
        userField.setEmptyText("User");
        PasswordField passwordField = new PasswordField();
        passwordField.setEmptyText("Password");
        TextButton newQ = new TextButton();
        newQ.setText("Login!");
        newQ.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                _presenter.login(userField.getText());
            }
        });
        horiz.add(userField);
        horiz.add(newQ);

        _panel.add(horiz);
        _panel.add(passwordField);
        viewport.add(_panel);
        initWidget(viewport);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        _presenter = presenter;
    }
}
