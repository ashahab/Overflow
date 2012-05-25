<%@ extends ronin.RoninTemplate %>
<%@ params(aPost : db.model.Post) %>
<% uses controller.Overflow %>
<% uses db.model.Post %>
 
<% using(target(Overflow #save(Post))) { %>
<form method="post" action="${TargetURL}">
  <% if(not aPost.New) { %>
  <input type="hidden" name="${n(Post)}" value="${aPost.id}">
  <% } %>
  <input type="text" name="${n(Post#Title)}" value="${h(aPost.Title)}"><br>
  <textarea name="${n(Post#Body)}" rows=20 columns=80>${h(aPost.Body)}</textarea><br>
  <input type="submit">
</form>
<% } %>