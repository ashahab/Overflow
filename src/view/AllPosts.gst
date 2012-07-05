<%@ extends ronin.RoninTemplate %>
<%@ params(posts : List<db.model.Question>) %>
<% uses controller.Overflow %>
 
<div class="header">All Posts</div>
<% for(post in posts) { %>
  <div class="postListEntry">
    <a href="${urlFor(Overflow #viewPost(post))}">${post.Title}</a>
    <a href="${urlFor(Overflow #delete(post))}">Delete post</a>
  </div>
<% } %>
<div><a href="${urlFor(Overflow #create())}">New post</a> </div>