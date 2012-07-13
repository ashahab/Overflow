package controller
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
uses org.apache.commons.lang3.StringEscapeUtils;
/**
 * Created by IntelliJ IDEA.
 * User: Abin_Shahab
 * Date: 2/20/12
 * Time: 8:58 PM
 * To change this template use File | Settings | File Templates.
 */
class Overflow extends RoninController{

  function index() {
    all();
  }
  function viewPost(post : Question) {
    Layout.render(Writer, "View", \ -> {
      view.ViewPost.render(Writer, post)
    })
  }
  function nextPost(post: Question) {
    var nextPost = Question.selectWhere("\"id\" > :pId ", {"pId"->post.Id}).orderBy(Question#id, ASC).first()
    redirect(#viewPost(nextPost))
  }
  function previousPost(post: Question) {
    var nextPost = Question.selectWhere("\"id\" < :pId ", {"pId"->post.Id}).orderBy(Question#id, DESC).first()
    redirect(#viewPost(nextPost))
  }
  function create() {
    Layout.render(Writer, "Create", \ -> {
      EditPost.render(Writer, new Question())
    })
  }
  static function getCachedClient() :TransportClient{
    return cache(\-> createClient(), :store = APPLICATION, :name = "elasticClient");
  }
  private static function createClient() : TransportClient{
    var client = new TransportClient()
        .addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300))
    print("created client: " + client);
    return client
  }

  function save(post : Question) {
    if(post.New) {
      post.Posted = new Timestamp(System.currentTimeMillis())
    }
    post.Title = StringEscapeUtils.escapeEcmaScript(post.Title);
    post.Body = StringEscapeUtils.escapeEcmaScript(post.Body);
    post.update()

    var client = getCachedClient()
    var irb = client.prepareIndex("posts", "post", post.Id.toString()).
        setSource(post.toJSON(:depth=1, :include={post#Id, post#Title, post#Body, post#Posted}));
    irb.execute().actionGet()
    redirect(#viewPost(post))
  }

  function delete(post : Question) {
    var client = getCachedClient()
    var response = client.prepareDelete("posts", "post", post.Id.toString())
        .execute()
        .actionGet();
    post.delete()
  }


  static function snip(pBody : String) : String{
    if(pBody.length() <= 100){
      return pBody;
    }
    return    pBody.substring(0, 100) + "...";
  }

  function edit(post : Question) {
    Layout.render(Writer, "Edit", \->
        EditPost.render(Writer, post))
  }

  function all() {
    var myPosts = Question.selectAll().orderBy(Question#Posted, DESC).toList()
    print("posts " + myPosts);
    Layout.render(Writer, "All", \ -> AllPosts.render(Writer, myPosts))
  }
}