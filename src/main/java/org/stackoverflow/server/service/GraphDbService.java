package org.stackoverflow.server.service;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.stackoverflow.server.dao.QuestionDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 12/2/12
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class GraphDbService implements ServletContextListener {
    public static GraphDatabaseService _graphDb;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        _graphDb = new GraphDatabaseFactory().
                newEmbeddedDatabaseBuilder(QuestionDao.DB_PATH).
                setConfig( GraphDatabaseSettings.node_keys_indexable, QuestionDao.USERNAME_KEY ).
                setConfig( GraphDatabaseSettings.relationship_keys_indexable, "relProp1,relProp2" ).
                setConfig( GraphDatabaseSettings.node_auto_indexing, "true" ).
                setConfig( GraphDatabaseSettings.relationship_auto_indexing, "true" ).
                newGraphDatabase();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        _graphDb.shutdown();
    }

    public static GraphDatabaseService getGraphDb() {
        return _graphDb;
    }
}
