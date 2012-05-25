<%@ extends ronin.RoninTemplate %>
<%@ params(post : db.model.Post) %>
<% uses controller.CommentsCx %>
<div><a id="createComment" href="${urlFor(CommentsCx#createComment(post))}">Add comment</a></div>
<div id="log"></div>
<script>
$("#createComment").click(function(event) {
  event.preventDefault();
  $.get('<%=urlFor(CommentsCx#createComment(post)) %>',
   function(data){
     $("#log").html(data);
   });
});
</script>

   <% for (comment in post.Comments) { %>
    <div class="comment">
      <div class="commentAuthor">${comment.Name} - ${comment.Posted}</div>
      <div class="commentBody">${comment.Text}</div>
       <div><a href="${urlFor(CommentsCx#editComment(post, comment))}">Edit</a>
       <a href="${urlFor(CommentsCx#deleteComment(post, comment))}">Delete</a></div>
    </div>
  <% } %>