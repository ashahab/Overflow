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

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 12/2/12
 * Time: 10:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class OverflowServiceImpl extends RemoteServiceServlet implements OverflowService {
    private Logger _logger = LoggerFactory.getLogger(OverflowServiceImpl.class);
    @Override
    public String login(String userName) {
        GraphDatabaseService graphDatabaseService
                = GraphDbService.getGraphDb();
        QuestionDao dao = new QuestionDao(graphDatabaseService);
        Node user = Preconditions.checkNotNull(dao.findUser(userName));
        String name = Preconditions.checkNotNull((String)user.getProperty("name"));
        return name;
    }

    @Override
    public void createUsers() {
        GraphDatabaseService graphDb
                = GraphDbService.getGraphDb();
        Transaction tx = graphDb.beginTx();


        try
        {
            QuestionDao dao = new QuestionDao(graphDb);
//            for (int i = 0; i < 100; i++){
//                Node user = dao.findUser(dao.idToUserName(i));
//                _logger.debug("User: " + user.getProperty(QuestionDao.USERNAME_KEY));
//                for(Relationship r: user.getRelationships()){
//                    r.delete();
//                }
//                user.delete();
//            }

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
                _logger.debug("User: " + dao.findUser(dao.idToUserName(i)).getProperty(QuestionDao.USERNAME_KEY));
            }
            tx.success();
        }
        finally
        {
            tx.finish();
        }
    }
}
