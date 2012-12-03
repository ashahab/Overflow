package org.stackoverflow.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 12/2/12
 * Time: 10:44 AM
 * To change this template use File | Settings | File Templates.
 */
@RemoteServiceRelativePath("overflowService")
public interface OverflowService extends RemoteService {
    String login(String userName);
}
