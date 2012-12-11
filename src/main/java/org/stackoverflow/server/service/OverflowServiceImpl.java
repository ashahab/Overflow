package org.stackoverflow.server.service;

import com.google.common.base.Preconditions;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.ReadableIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stackoverflow.client.service.OverflowService;
import org.stackoverflow.server.dao.QuestionDao;
import org.stackoverflow.shared.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 12/2/12
 * Time: 10:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class OverflowServiceImpl extends RemoteServiceServlet implements OverflowService {
    public static final String LOGGED_IN_USER = "logged_in_user";
    private Logger _logger = LoggerFactory.getLogger(OverflowServiceImpl.class);
    @Override
    public User login(String userName) {
        GraphDatabaseService graphDatabaseService
                = GraphDbService.getGraphDb();
        QuestionDao dao = new QuestionDao(graphDatabaseService);
        User user = Preconditions.checkNotNull(dao.findUser(userName));
        getThreadLocalRequest().getSession().setAttribute(LOGGED_IN_USER, user);
        _logger.debug("Logged in: " + user);
        return user;
    }

    @Override
    public void createUsers() {
        GraphDatabaseService graphDb
                = GraphDbService.getGraphDb();
        Transaction tx = graphDb.beginTx();


        try
        {
            QuestionDao dao = new QuestionDao(graphDb);

            // Create users sub reference node
            Node usersReferenceNode = graphDb.createNode();
            graphDb.getReferenceNode().createRelationshipTo(
                    usersReferenceNode, QuestionDao.RelTypes.USERS_REFERENCE);
            // Create some users and index their names with the IndexService
            for ( int id = 0; id < 100; id++ )
            {
                Node userNode = dao.createAndIndexUser(dao.idToUserName(id));
                usersReferenceNode.createRelationshipTo( userNode,
                        QuestionDao.RelTypes.USER );
            }
            // END SNIPPET: addUsers
            _logger.debug( "Users created" );



            // Delete the persons and remove them from the index
            for (int i = 0; i < 100; i++){
                _logger.debug("User: " + dao.findUser(dao.idToUserName(i)).getUserName());
            }
            tx.success();
        }
        finally
        {
            tx.finish();
        }
    }
}
