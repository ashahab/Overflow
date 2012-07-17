package controller

uses ronin.RoninController
uses db.model.Answer
uses db.model.Question
uses view.Layout
uses java.sql.Timestamp
uses java.lang.System
uses view.AllComments
uses org.apache.commons.lang3.StringEscapeUtils;
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
    answer.Text = StringEscapeUtils.escapeEcmaScript(answer.Text);
    if(answer.New) {
      answer.Posted = new Timestamp(System.currentTimeMillis())
      post.Answers.add(answer);
    }
    var client = Overflow.getCachedClient()
    var irb = client.prepareIndex("posts", "answer", answer.Id.toString()).
        setSource(answer.toJSON(:depth=1, :include={answer#Id, answer#Posted, answer#Author, answer#Text}));
    irb.execute().actionGet()
    print("answer: " + answer)
    answer.update()
    return answer.toJSON(:depth=1, :include={answer#Posted, answer#Id}).chop() + ", success:true}";
  }
  function deleteComment(answer : Answer) {
    var client = Overflow.getCachedClient()
    var response = client.prepareDelete("posts", "answer", answer.Id.toString())
        .execute()
        .actionGet();
    answer.delete()
  }

}
