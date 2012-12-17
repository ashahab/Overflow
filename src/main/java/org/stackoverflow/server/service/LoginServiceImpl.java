package org.stackoverflow.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.neo4j.graphdb.GraphDatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stackoverflow.client.service.LoginService;
import org.stackoverflow.server.dao.QuestionDao;
import org.stackoverflow.shared.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 12/16/12
 * Time: 10:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
    private static final long serialVersionUID = 4456105400553118785L;
    public static final String LOGGED_IN_USER = "logged_in_user";
    private Logger _logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Override
    public User loginServer(String userName, String password) {
        //validate username and password
        GraphDatabaseService graphDatabaseService
                = GraphDbService.getGraphDb();
        QuestionDao dao = new QuestionDao(graphDatabaseService);
        _logger.debug("finding user: " + userName);
        User user = dao.findUser(userName);
        if (user != null) {
            getThreadLocalRequest().getSession().setAttribute(LOGGED_IN_USER, user);
            _logger.debug("Logged in: " + user);
            //store the user/session id
            storeUserInSession(user);
        }
        return user;
    }

    @Override
    public User loginFromSessionServer() {
        return getUserAlreadyFromSession(this.getThreadLocalRequest());
    }

    @Override
    public void logout() {
        deleteUserFromSession();
    }

    @Override
    public boolean changePassword(String name, String newPassword) {
        // change password logic
        return false;
    }

    public static User getUserAlreadyFromSession(HttpServletRequest threadLocalRequest) {
        User user = null;
        HttpServletRequest httpServletRequest = threadLocalRequest;
        HttpSession session = httpServletRequest.getSession();
        Object userObj = session.getAttribute(LOGGED_IN_USER);
        if (userObj != null && userObj instanceof User) {
            user = (User) userObj;
        }
        return user;
    }

    private void storeUserInSession(User user) {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute(LOGGED_IN_USER, user);
    }

    private void deleteUserFromSession() {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute(LOGGED_IN_USER);
    }

}

