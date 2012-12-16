package org.stackoverflow.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.stackoverflow.shared.model.User;

public interface LoginServiceAsync {
    void loginServer(String name, String password, AsyncCallback<User> async);

    void loginFromSessionServer(AsyncCallback<User> async);

    void changePassword(String name, String newPassword, AsyncCallback<Boolean> async);

    void logout(AsyncCallback<Void> async);
}
