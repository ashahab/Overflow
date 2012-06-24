<%@ extends ronin.RoninTemplate %>
<% uses controller.Overflow %>
<% uses controller.Search %>
<% uses java.lang.String %>
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


    var top = new Ext.form.FormPanel({
//        standardSubmit: true,
        id: 'searchForm',
        collapsible: true,
        frame: true,
        title: 'Searching posts',
        bodyPadding: '5 5 0',
        width: 600,
        fieldDefaults: {
            labelAlign: 'top',
            msgTarget: 'side'
        },

        items: [{
            xtype: 'container',
            anchor: '100%',
            layout: 'hbox',
            items:[{
                xtype: 'container',
                flex: 1,
                layout: 'anchor',
                items: [{
                    xtype:'textfield',
                    fieldLabel: 'Question',
                    afterLabelTextTpl: required,
                    id: 'question',
                    name: 'question',
                    anchor:'95%',
                    value: ''
                }]
            }
			]
        }],

        buttons: [{
            text: 'Search',
            formBind: true,
           handler: function() {
              Ext.Ajax.request({
                  url: '${TargetURL}',
                  method: 'POST',
                  params: {question: Ext.getCmp('question').getValue()},
                  success: function (result, request){
                      var hits = Ext.decode(result.responseText).Hits;
                      Ext.each(hits, function(hit) {
                        alert(hit.Source);
                      });
                  },
                  failure: function (result, request){
                      alert('Error in server' + result.responseText);
                  }
              });
            }
          }]
    });

    top.render(document.body);

});
</script>
<%}%>