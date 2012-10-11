package org.stackoverflow.server.dao;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
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

    private static enum RelTypes implements RelationshipType
    {
        USERS_REFERENCE,
        USER
    }
    private static final String DB_PATH = "neo4j-store";
    private static final String USERNAME_KEY = "username";
    private GraphDatabaseService _graphDb;
    private Index<Node> _nodeIndex;

    public QuestionDao(GraphDatabaseService graphDb) {
        _graphDb = graphDb;
        _nodeIndex = _graphDb.index().forNodes(INDEX_NAME);
    }
    public Question save (Question question){
        //create a question node, and link it to a corresponding user node
        return  question;
    }

    public User save (User user){
        String username = user.getName();
        Node node = _graphDb.createNode();
        node.setProperty( USERNAME_KEY, username );
        _nodeIndex.add( node, USERNAME_KEY, username );
        Transaction tx = _graphDb.beginTx();
        return user;
    }

    private static String idToUserName( final int id )
    {
        return "user" + id;
    }

    private Node createAndIndexUser( final String username )
    {
        Node node = _graphDb.createNode();
        node.setProperty( USERNAME_KEY, username );
        _nodeIndex.add( node, USERNAME_KEY, username );
        return node;
    }
}
