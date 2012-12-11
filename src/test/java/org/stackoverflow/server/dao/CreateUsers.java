package org.stackoverflow.server.dao;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.graphdb.index.ReadableIndex;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 11/30/12
 * Time: 11:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class CreateUsers {

    private static GraphDatabaseService _graphDb;

    public static void main (String... args){
        _graphDb = new GraphDatabaseFactory().
                newEmbeddedDatabaseBuilder(QuestionDao.DB_PATH).
                setConfig( GraphDatabaseSettings.node_keys_indexable, QuestionDao.USERNAME_KEY ).
                setConfig( GraphDatabaseSettings.relationship_keys_indexable, "relProp1,relProp2" ).
                setConfig( GraphDatabaseSettings.node_auto_indexing, "true" ).
                setConfig( GraphDatabaseSettings.relationship_auto_indexing, "true" ).
                newGraphDatabase();


        createUsers();

    }

    public static void createUsers() {
        Transaction tx = _graphDb.beginTx();
        try
        {
            QuestionDao dao = new QuestionDao(_graphDb);


            // Create users sub reference node
            Node usersReferenceNode = _graphDb.createNode();
            _graphDb.getReferenceNode().createRelationshipTo(
                    usersReferenceNode, QuestionDao.RelTypes.USERS_REFERENCE);
            // Create some users and index their names with the IndexService
            for ( int id = 0; id < 100; id++ )
            {
                Node userNode = dao.createAndIndexUser(dao.idToUserName(id));
                usersReferenceNode.createRelationshipTo( userNode,
                        QuestionDao.RelTypes.USER );
            }
            // END SNIPPET: addUsers
            System.out.println( "Users created" );

            ReadableIndex<Node> autoNodeIndex = _graphDb.index()
                    .getNodeAutoIndexer()
                    .getAutoIndex();

            // Delete the persons and remove them from the index
            for (int i = 0; i < 100; i++){
                System.out.println("User: " + dao.findUser(dao.idToUserName(i)).getUserName());
            }
            tx.success();
        }
        finally
        {
            tx.finish();
            System.out.println( "Shutting down database ..." );
            _graphDb.shutdown();
        }
    }

}
