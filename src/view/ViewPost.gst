<%@ extends ronin.RoninTemplate %>
<%@ params(post : db.model.Post) %>
<% uses controller.Overflow %>
<% uses controller.CommentsCx %>
  <div class="header">${h(post.Title)}</div>
  <div class="body">${h(post.Body)}</div>
  <div class="posted">Posted on ${post.Posted}</div>
  <div><a href="${urlFor(Overflow #edit(post))}">Edit post</a>
  <a href="${urlFor(Overflow #delete(post))}">Delete post</a></div>

 <div id="content"><% CommentsCx.viewComments(post) %></div>
  <a href="${urlFor(Overflow #previousPost(post))}">Previous</a></div>
  <a href="${urlFor(Overflow #nextPost(post))}">Next</a></div>