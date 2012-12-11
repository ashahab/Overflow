package org.stackoverflow.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 12/3/12
 * Time: 11:00 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseActivity extends AbstractActivity {
    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        checkLogin();
        doStart(containerWidget, eventBus);
    }

    private void checkLogin() {
        String sessionID = Cookies.getCookie("sid");
        if ( sessionID != null ) checkWithServerIfSessionIdIsStillLegal();
        else displayLoginBox();
    }

    protected abstract void doStart(AcceptsOneWidget containerWidget, EventBus eventBus);
}
