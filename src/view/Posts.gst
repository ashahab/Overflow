<%@ extends ronin.RoninTemplate %>
<%uses java.util.Iterator%>
<%@ params(posts: Iterator <db.model.Post>,  pageSize: int, startOffset: int) %>
<% uses controller.Overflow%>
<% var p = 0%>
       <% for (post in posts index i) { %>
  <div class="postListEntry">
    ${i}. <a href="${urlFor(Overflow #viewPost(post))}">${post.Title}</a>
    <div><a href="${urlFor(Overflow #viewPost(post))}">${Overflow.snip(post.Body)}</a> </div>
    <a href="${urlFor(Overflow #delete(post))}">Delete post</a>
  </div>
<%if (i > = pageSize - 1) {
      p = i
      break;
    }
    }

    %>
<%if (p > = pageSize - 1) {%>
<div><a href="${urlFor(Overflow #pagedPosts(pageSize, startOffset + pageSize))}">More</a> </div>
<%}%>