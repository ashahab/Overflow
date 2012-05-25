<%@ extends ronin.RoninTemplate %>
<%uses java.util.Iterator%>
<%@ params(myPosts : Iterator<db.model.Post>, startPage: int, pageSize: int) %>
<% uses controller.Overflow %>
<% uses view.Posts %>
<div class="header">All Posts</div>
<%Overflow.renderPosts(myPosts, pageSize, startPage)%>
<div><a href="${urlFor(Overflow #create())}">New post</a> </div>
