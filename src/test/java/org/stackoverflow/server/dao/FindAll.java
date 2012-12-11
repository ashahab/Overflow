package org.stackoverflow.server.dao;

import com.google.common.base.Preconditions;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 11/30/12
 * Time: 11:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class FindAll {
    public static void main (String... args){
        GraphDatabaseService graphDb = new GraphDatabaseFactory().
                newEmbeddedDatabaseBuilder(QuestionDao.DB_PATH).
                setConfig( GraphDatabaseSettings.node_keys_indexable, QuestionDao.USERNAME_KEY ).
                setConfig( GraphDatabaseSettings.relationship_keys_indexable, "relProp1,relProp2" ).
                setConfig( GraphDatabaseSettings.node_auto_indexing, "true" ).
                setConfig( GraphDatabaseSettings.relationship_auto_indexing, "true" ).
                newGraphDatabase();

        QuestionDao dao = new QuestionDao(graphDb);
        Preconditions.checkNotNull(dao.findUser("user45"));

    }
}
