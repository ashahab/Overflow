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
  function viewPost(post : Post) {
    Layout.render(Writer, "View", \ -> {
      view.ViewPost.render(Writer, post)
    })
  }
  function nextPost(post: Post) {
    var nextPost = Post.selectWhere("\"id\" > :pId ", {"pId"->post.Id}).orderBy(Post#id, ASC).first()
    redirect(#viewPost(nextPost))
  }
  function previousPost(post: Post) {
    var nextPost = Post.selectWhere("\"id\" < :pId ", {"pId"->post.Id}).orderBy(Post#id, DESC).first()
    redirect(#viewPost(nextPost))
  }
  function create() {
    Layout.render(Writer, "Create", \ -> {
      EditPost.render(Writer, new Post())
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

  function save(post : Post) {
    if(post.New) {
      post.Posted = new Timestamp(System.currentTimeMillis())
    }
    post.update()

    var client = getCachedClient()
    var irb = client.prepareIndex("posts", "post", post.Id.toString()).
        setSource(post.toJSON());
    irb.execute().actionGet()
    redirect(#viewPost(post))
  }

  function delete(post : Post) {
    post.delete()
    redirect(#all())
  }


  static function snip(pBody : String) : String{
    if(pBody.length() <= 100){
      return pBody;
    }
    return    pBody.substring(0, 100) + "...";
  }

  function edit(post : Post) {
    Layout.render(Writer, "Edit", \->
        EditPost.render(Writer, post))
  }

  function all() {
    var myPosts = Post.selectAll().orderBy(Post#Posted, DESC).toList()
    Layout.render(Writer, "All", \ -> AllPosts.render(Writer, myPosts))
  }

  function allSnippet(startPage: int) {
    var pageSize = 10
    var posts = Post.selectAll().orderBy(Post#Posted, DESC).page(:pageSize = pageSize, :startOffset = startPage).iterator()
    Layout.render(Writer, "All Snippets", \ -> AllSnippet.render(Writer, posts, startPage, pageSize))
  }
  
  static function renderPosts(posts: java.util.Iterator<db.model.Post>, pageSize: int, startOffset: int) {
      Posts.render(Writer, posts, pageSize, startOffset);
  }

  function pagedPosts(pageSize: int, startOffset: int) {
    print("Invoked with startOffset " + startOffset)
    var posts = Post.selectAll().orderBy(Post#Posted, DESC).page(:pageSize = pageSize, :startOffset = startOffset).iterator()
    Posts.render(Writer, posts, pageSize, startOffset);
  }
}