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

    var tb = Ext.create('Ext.toolbar.Toolbar', {renderTo:document.body});
    tb.add(
    {
              id: 'titlePane',
              xtype:'panel',
              html:'<h2>Search posts</h2>'
            },
    {
            text:'Ask new question',
            xtype: 'button',
            handler: function(){
               document.location.href = '${urlFor(Overflow #create())}';
            }
        });
    Ext.create("gw.stackoverflow.RelatedResults",{
      search: true,
      targetUrl:'${TargetURL}'
      });
});
</script>
<%}%>