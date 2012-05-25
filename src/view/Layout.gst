<%@ extends ronin.RoninTemplate %>
<%@ params(title : String, content()) %>

<html>
 <head>
 <script type="text/javascript" src="../public/jquery-1.7.1.min.js"></script>
  <script src="http://malsup.github.com/jquery.form.js"></script>
   <title>${title}</title>
 </head>
 <body>
   <div id="content"><% content() %></div>
  </body>
</html>