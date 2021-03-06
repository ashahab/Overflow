<%@ extends ronin.RoninTemplate %>
<%@ params(title : String, content()) %>

<html>
 <head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <!-- ExtJS -->
    <link rel="stylesheet" type="text/css" href="../public/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="../public/resources/css/custom.css"/>
    <script type="text/javascript" src="../public/ext/ext-all-debug-w-comments.js"></script>
    <script type="text/javascript" src="../public/searchfield.js"></script>
    <script type="text/javascript" src="../public/editWindow.js"></script>
    <script type="text/javascript" src="../public/addAnswersToolbar.js"></script>
    <script type="text/javascript" src="../public/searchResult.js"></script>
    <script type="text/javascript" src="../public/editQuestion.js"></script>
   <title>${title}</title>
 </head>
 <body>
   <div id="content"><% content() %></div>
  </body>
</html>