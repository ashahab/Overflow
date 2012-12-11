package org.stackoverflow.server.dao;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.test.TestGraphDatabaseFactory;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 10/7/12
 * Time: 11:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuestionDaoTest {
    private static GraphDatabaseService graphDb;

    @BeforeClass
    public static void startDb() {
        graphDb = new TestGraphDatabaseFactory().newImpermanentDatabaseBuilder().newGraphDatabase();
    }

    @AfterClass
    public static void stopDb() {
        graphDb.shutdown();
    }
    @Test
    @Ignore
    public void should_save_node() {
        Transaction tx = graphDb.beginTx();

        Node n = null;
        try
        {
            n = graphDb.createNode();
            n.setProperty( "name", "Nancy" );
            tx.success();
        }
        catch ( Exception e )
        {
            tx.failure();
        }
        finally
        {
            tx.finish();
        }

        assertThat( n.getId()).isGreaterThan(0l);
        Node foundNode = graphDb.getNodeById( n.getId() );
        assertThat( foundNode.getId()).isEqualTo( n.getId() );
        assertThat(foundNode.getProperty( "name" )).isEqualTo( "Nancy" );
    }

    public void should_query_index() {

    }
}
