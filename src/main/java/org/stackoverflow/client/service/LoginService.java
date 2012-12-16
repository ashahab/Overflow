package org.stackoverflow.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.stackoverflow.shared.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 12/16/12
 * Time: 10:08 AM
 * To change this template use File | Settings | File Templates.
 */
@RemoteServiceRelativePath("loginService")
public interface LoginService extends RemoteService
{

    User loginServer(String name, String password);

    User loginFromSessionServer();

    boolean changePassword(String name, String newPassword);

    void logout();
}
