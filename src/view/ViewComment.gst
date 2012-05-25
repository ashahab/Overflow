<%@ extends ronin.RoninTemplate %>
<%@ params(post:  db.model.Post, comment : db.model.Comment) %>
<% uses controller.CommentsCx %>
    <div class="comment">
      <div class="commentAuthor">${comment.Name} - ${comment.Posted}</div>
      <div class="commentBody">${comment.Text}</div>
    </div>
