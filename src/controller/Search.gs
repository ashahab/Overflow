package controller
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
/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 6/21/12
 * Time: 3:13 AM
 * To change this template use File | Settings | File Templates.
 */
class Search extends RoninController {
   function index() {
     question();
   }
  function question(){
    Layout.render(Writer, "question", \ -> {
      view.Search.render(Writer)
    })
  }
  function ask() {

  }
}