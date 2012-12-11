package org.stackoverflow.client.events;

import com.google.gwt.event.shared.EventHandler;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 12/3/12
 * Time: 9:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface LoggedInEventHandler extends EventHandler {
    public void onLoggedIn(LoggedInEvent loggedInEvent);
}
