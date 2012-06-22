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
uses db.model.Post
uses db.model.Comment
uses view.EditPost
uses view.EditPostComment
uses java.lang.System
uses java.sql.Timestamp
uses view.AllPosts
uses view.AllSnippet
uses java.util.Iterator
uses java.lang.Integer
uses view.Posts
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
  function ask(question: String) {
    print("question: " + question);
    //make  a query to elastic search
    var client = Overflow.getCachedClient()
    var qb = QueryBuilders.queryString(question);
    var response = client.prepareSearch({"posts"})
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(qb)
        .setFrom(0).setSize(60)
        .execute()
        .actionGet();
    //get the answers and return
    var docs = response.getHits().getHits();
    for (var sd in docs) {
      print("hit: " + sd.getSource().get("Body"))
    }
    //print to a page nicely
  }
}