 <%@ extends ronin.RoninTemplate %>
<% uses controller.Overflow %>
<% uses controller.Search %>
<% uses java.lang.String %>
<% uses db.model.Question %>
<% using(target(Search #ask(String))) { %>
<script type="text/javascript" >
Ext.require([
    //'Ext.form.*',
    //'Ext.layout.container.Column',
    //'Ext.tab.Panel'
    '*'
]);

Ext.onReady(function() {
    Ext.QuickTips.init();
    var bd = Ext.getBody();

    var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';

    bd.createChild({tag: 'h2', html: 'Search posts'});
    Ext.create("gw.stackoverflow.RelatedResults",{
      search: true,
      targetUrl:'${TargetURL}'
      });
});
</script>
<%}%>