package org.stackoverflow.server.dao;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.ReadableIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stackoverflow.shared.model.Question;
import org.stackoverflow.shared.model.User;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 10/7/12
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuestionDao {

    public static Logger _logger = LoggerFactory.getLogger(QuestionDao.class);
    private ReadableIndex<Node> _index;

    public Question getQuestion(long questionId) {
        Node questionNode = _graphDb.getNodeById(questionId);
        return nodeToQuestion(questionNode);
    }

    private Question nodeToQuestion(Node questionNode) {

        Question question = new Question();
        question.setId(questionNode.getId() + "");
        question.setQuery((String)questionNode.getProperty(QUERY_KEY));
        question.setDescription((String)questionNode.getProperty("description"));
        question.setUser(nodeToUser(questionNode.getSingleRelationship(RelTypes.USER, Direction.OUTGOING).getEndNode()));
        question.setPosted(new Date((Long)questionNode.getProperty("posted")));
        return question;
    }

    public static enum RelTypes implements RelationshipType
    {
        USERS_REFERENCE,
        USER
    }
    public static final String DB_PATH = "neo4j-store";
    public static final String USERNAME_KEY = "username";
    public static final String QUERY_KEY = "query";

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
        Node node = null;
        if(question.getId() == null || question.getId().isEmpty()){
            node = _graphDb.createNode();
            Node userNode = _graphDb.getNodeById(Long.parseLong(question.author().getId()));
            node.createRelationshipTo(userNode, RelTypes.USER);
            node.setProperty("posted", question.postedOn().getTime());
        } else {
            node = _graphDb.getNodeById(Long.parseLong(question.getId()));
        }
        node.setProperty(QUERY_KEY, question.getQuery());
        node.setProperty("description", question.getDescription());
        question.setId(node.getId() + "");
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

    public User findUser(String userName){
        _logger.debug("userName: " + userName);

        return nodeToUser(_index.get(USERNAME_KEY, userName.trim()).getSingle());
    }

    private User nodeToUser(Node userNode) {
        User user = new User((String)userNode.getProperty(USERNAME_KEY));
        user.setName((String)userNode.getProperty("name"));
        user.setId(userNode.getId() + "");
        return user;
    }


}
