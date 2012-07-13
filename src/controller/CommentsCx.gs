package controller

uses ronin.RoninController
uses db.model.Answer
uses db.model.Question
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

  static function viewComments(post: Question){
    view.AllComments.render(Writer, post)
  }

  function saveComment(post: Question, answer : Answer):String {
    if(answer.New) {
      answer.Posted = new Timestamp(System.currentTimeMillis())
      post.Answers.add(answer);
    }
    var client = Overflow.getCachedClient()
    var irb = client.prepareIndex("posts", "answer", answer.Id.toString()).
        setSource(answer.toJSON(:depth=1, :include={answer#Id, answer#Posted, answer#Id, answer#Text}));
    irb.execute().actionGet()
    answer.update()
    print("answer: " + answer.toJSON(:depth=1, :include={answer#Posted, answer#Id}).chop() + ", success:true}");
    return answer.toJSON(:depth=1, :include={answer#Posted, answer#Id}).chop() + ", success:true}";
  }
  function deleteComment(answer : Answer) {
    var client = Overflow.getCachedClient()
    var response = client.prepareDelete("twitter", "tweet", "1")
        .execute()
        .actionGet();
    answer.delete()
  }

}
