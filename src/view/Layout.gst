<%@ extends ronin.RoninTemplate %>
<%@ params(title : String, content()) %>

<html>
 <head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <!-- ExtJS -->
    <link rel="stylesheet" type="text/css" href="../public/resources/css/ext-all.css" />
    <script type="text/javascript" src="../public/ext-all.js"></script>
    <script type="text/javascript" src="../public/searchfield.js"></script>
   <title>${title}</title>
 </head>
 <body>
   <div id="content"><% content() %></div>
  </body>
</html>