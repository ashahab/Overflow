<%@ extends ronin.RoninTemplate %>
<%@ params(aPost: db.model.Post,  aComment: db.model.Comment) %>
<% uses controller.Overflow %>
<% uses db.model.Comment %>
<% uses db.model.Post %>
<% uses controller.CommentsCx %>
<script>
        // wait for the DOM to be loaded
        $(document).ready(function() {
         var options = {
        target:        '<%urlFor(CommentsCx#viewComments(aPost)) %>',   // target element(s) to be updated with server response
        //beforeSubmit:  showRequest,  // pre-submit callback
        //success:       showResponse  // post-submit callback

        // other available options:
        //url:       url         // override for form's 'action' attribute
        //type:      type        // 'get' or 'post', override for form's 'method' attribute
        //dataType:  null        // 'xml', 'script', or 'json' (expected server response type)
        //clearForm: true        // clear all form fields after successful submit
        //resetForm: true        // reset the form after successful submit

        // $.ajax options can be used here too, for example:
        //timeout:   3000
    };
            // bind 'myForm' and provide a simple callback function
            $('#saveComment').submit(function() {
              // submit the form
              alert("submitting!");
              $(this).ajaxSubmit();
              // return false to prevent normal browser submit and page navigation
              return false;
             });
        });
    </script>
<% using(target(CommentsCx#saveComment(db.model.Post, db.model.Comment))) { %>
<form id="saveComment" method="post" action="${TargetURL}">
  <% if(not aPost.New) { %>
  <input type="hidden" name="${n(Post)}" value="${aPost.id}">
  <% } %>
  <% if(not aComment.New) { %>
  <input type="hidden" name="${n(Comment)}" value="${aComment.id}">
  <% } %>
  <input type="text" name="${n(Comment#Name)}" value="${h(aComment.Name)}"><br>
  <textarea name="${n(Comment#Text)}" rows=20 columns=80>${h(aComment.Text)}</textarea><br>
  <input type="submit">
</form>
<% } %>