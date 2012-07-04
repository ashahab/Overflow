package controller

uses ronin.RoninController
uses db.model.Comment
uses db.model.Post
uses view.Posts
uses view.EditPostComment
uses view.Layout
uses java.sql.Timestamp
uses java.lang.System
uses view.AllComments

/**
 * Created by IntelliJ IDEA.
 * User: Abin_Shahab
 * Date: 3/2/12
 * Time: 9:47 AM
 * To change this template use File | Settings | File Templates.
 */
class CommentsCx extends RoninController{

  static function viewComments(post: Post){
    view.AllComments.render(Writer, post)
  }
  function createComment(post: Post) {
    Layout.render(Writer, "Comment", \ -> {
      EditPostComment.render(Writer, post, new Comment());
    })
  }
  function saveComment(post: Post, comment : Comment) {
    print("comment: " + comment)
    if(comment.New) {
      comment.Posted = new Timestamp(System.currentTimeMillis())
      post.Comments.add(comment);
    }
    var client = Overflow.getCachedClient()
    var irb = client.prepareIndex("posts", "comment", comment.Id.toString()).
        setSource(comment.toJSON());
    irb.execute().actionGet()
    comment.update()
  }
  function deleteComment(post: Post, comment : Comment) {
    comment.delete()
    redirect(overflow #viewPost(post))
  }
  function editComment(post: Post, comment : Comment) {
    Layout.render(Writer, "Edit Comment", \->
        EditPostComment.render(Writer, post, comment))
  }
}
