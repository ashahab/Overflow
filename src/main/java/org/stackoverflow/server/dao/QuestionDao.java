package org.stackoverflow.server.dao;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.ReadableIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stackoverflow.shared.model.Question;
import org.stackoverflow.shared.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 10/7/12
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuestionDao {

    public static final String INDEX_NAME = "nodes";
    public static Logger _logger = LoggerFactory.getLogger(QuestionDao.class);
    private ReadableIndex<Node> _index;

    public static enum RelTypes implements RelationshipType
    {
        USERS_REFERENCE,
        USER
    }
    public static final String DB_PATH = "neo4j-store";
    public static final String USERNAME_KEY = "username";
    private GraphDatabaseService _graphDb;
//    private Index<Node> _nodeIndex;

    public QuestionDao(GraphDatabaseService graphDb) {
        _graphDb = graphDb;
        _index = _graphDb.index()
                .getNodeAutoIndexer()
                .getAutoIndex();
    }
    public Question save (Question question){
        //create a question node, and link it to a corresponding user node
        return  question;
    }

    public User save (User user){
        String username = user.getName();
        Node node = _graphDb.createNode();
        node.setProperty( USERNAME_KEY, username );
        return user;
    }

    public static String idToUserName( final int id )
    {
        return "user" + id;
    }

    public Node createAndIndexUser(final String username)
    {
        Node node = _graphDb.createNode();
        node.setProperty( USERNAME_KEY, username );
        node.setProperty("name", "Name of " +username);
        return node;
    }

    public Node findUser(String userName){
        _logger.debug("userName: " + userName);

        return _index.get(USERNAME_KEY, userName.trim()).getSingle();
    }


}
