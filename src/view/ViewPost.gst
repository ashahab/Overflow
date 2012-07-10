    <%@ extends ronin.RoninTemplate %>
<%@ params(post: db.model.Question) %>
<%

        uses controller.Overflow %>
<% uses controller.CommentsCx

        %>
<script type="text/javascript" >
Ext.require([
//'Ext.form.*',
//'Ext.layout.container.Column',
//'Ext.tab.Panel'
'*'
]);
Ext.onReady(function() {

var BasePanel = function (config) {

 Ext.apply(config,{items: [{
     title: 'Search results',
     region: 'east',
     margins: '5 0 0 5',
     width: 800,
     cmargins: '5 5 0 5', // adjust top margin when collapsed
     id: 'west-region-container',
     layout: 'fit',

     frame: true
 }, {
       region: 'center',     // center region is required, no width/height specified
       xtype: 'panel',
       layout: 'fit',
       margins: '5 5 0 0',
       items: [{
           xtype:'panel',
           border: 'false',
           layout: {
                   type: 'vbox',
                   align: 'stretch'
           },
           items:[{
              id: 'titlePane',
              xtype:'panel',
              html:'<b>Title: </b>${h(post.Title)}'
            },{
              id: 'bodyPane',
              xtype:'panel',
              html:'<b>Body: </b>${h(post.Body)}'
            },{
              id: 'postedPane',
              xtype:'panel',
              html:'<b>Posted: </b>${h(post.Posted as String)}'
            }]
       }]
     }]});
        //call the base constructor
        BasePanel.superclass.constructor.call(this, config);
    }

    Ext.extend(BasePanel, Ext.Panel, {
        height: 100,
        layout: 'border'

    });
    var win = new BasePanel({renderTo:document.body});
});

</script>
<div id="content"><% CommentsCx.viewComments(post)%></div>
