package org.stackoverflow.server.service;

import com.google.common.base.Preconditions;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.stackoverflow.client.service.OverflowService;
import org.stackoverflow.server.dao.QuestionDao;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 12/2/12
 * Time: 10:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class OverflowServiceImpl extends RemoteServiceServlet implements OverflowService {
    @Override
    public String login(String userName) {
        GraphDatabaseService graphDatabaseService
                = GraphDbService.getGraphDb();
        QuestionDao dao = new QuestionDao(graphDatabaseService);
        Node user = Preconditions.checkNotNull(dao.findUser(userName));
        String name = Preconditions.checkNotNull((String)user.getProperty("name"));
        return name;
    }
}
