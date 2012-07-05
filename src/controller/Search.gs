package controller
/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 6/21/12
 * Time: 3:13 AM
 * To change this template use File | Settings | File Templates.
 */
uses ronin.config.*
uses ronin.RoninController
uses view.Layout
uses view.ViewPost
uses db.model.Question
uses db.model.Comment
uses view.EditPost
uses java.lang.System
uses java.sql.Timestamp
uses view.AllPosts
uses java.util.Iterator
uses java.lang.Integer
uses javax.swing.DefaultBoundedRangeModel
uses com.sun.tools.internal.xjc.model.Model
uses org.elasticsearch.node.NodeBuilder;
uses org.elasticsearch.client.transport.TransportClient;
uses org.elasticsearch.common.transport.InetSocketTransportAddress;
uses org.elasticsearch.action.index.IndexRequestBuilder
uses org.elasticsearch.index.query.FilterBuilders;
uses org.elasticsearch.index.query.QueryBuilders;
uses org.elasticsearch.index.query.QueryStringQueryBuilder;
uses org.elasticsearch.action.search.SearchType;
uses org.elasticsearch.search.SearchHit;

class Search extends RoninController {
   function index() {
     question();
   }
  function question(){
    Layout.render(Writer, "question", \ -> {
      view.Search.render(Writer)
    })
  }
  function ask(query: String) : String{
    print("question: " + query);
    //make  a query to elastic search
    var client = Overflow.getCachedClient()
    var qb = QueryBuilders.queryString(query);
    var response = client.prepareSearch({"posts"})
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(qb)
        .setFrom(0).setSize(60)
        .execute()
        .actionGet();
    //get the answers and return
    print("response: " + response.Hits.toJSON());
    //print to a page nicely
    //render to layout with the map as an input
    return response.Hits.toJSON()
  }
}