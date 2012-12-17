package org.stackoverflow.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.stackoverflow.shared.model.Question;
import org.stackoverflow.shared.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 12/2/12
 * Time: 10:44 AM
 * To change this template use File | Settings | File Templates.
 */
public interface OverflowServiceAsync {
    void login(String userName, AsyncCallback<User> async);

    void createUsers(AsyncCallback<Void> async);

    void postQuestion(String question, String description, AsyncCallback<Question> async);

    void findQuestion(String questionId, AsyncCallback<Question> async);
}
