package org.stackoverflow.client.events;


import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import org.stackoverflow.shared.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 12/3/12
 * Time: 9:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoggedInEvent extends GwtEvent {
    private static final Type<LoggedInEventHandler> TYPE = new Type<LoggedInEventHandler>();;
    private User _user;
    LoggedInEvent(){}
    public LoggedInEvent(User user){
       _user = user;
    }
    @Override
    public Type getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Object o) {

    }

    @Override
    protected void dispatch(EventHandler eventHandler) {
        ((LoggedInEventHandler)eventHandler).onLoggedIn(this);
    }

    public static HandlerRegistration register(EventBus eventBus, LoggedInEventHandler handler) {
        return eventBus.addHandler(TYPE, handler);
    }
}
