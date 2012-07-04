<%@ extends ronin.RoninTemplate %>
<%@ params(post : db.model.Post) %>
<% uses controller.CommentsCx %>
<% uses db.model.Post %>
<% uses db.model.Comment %>
<script type="text/javascript" >
Ext.require([
    //'Ext.form.*',
    //'Ext.layout.container.Column',
    //'Ext.tab.Panel'
    '*'
]);
Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.define('Comment', {
      extend: 'Ext.data.Model',
      fields: [
          {name: 'Name', type: 'string'},
          {name: 'Posted', type: 'date'},
          {name: 'Text', type: 'string'}
      ]
    });
    var myStore = Ext.create('Ext.data.Store', {
        model: 'Comment',
        proxy: {
            type: 'memory'
        },
        autoDestroy: true
    });
    <% for (comment in post.Comments) { %>
      var record = new Comment({
        Name: '${comment.Name}',
        Posted: '${comment.Posted}',
        Text: '${comment.Text}'
      });
      myStore.add(record);
      myStore.commitChanges();
    <% } %>

    var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
        clicksToMoveEditor: 1,
        autoCancel: false
    });

    var rowBodyFeature = Ext.create('Ext.grid.feature.RowBody', {
        collectData: true,
        getAdditionalData : function(data, rowIndex, record, orig) {
            var headerCt = this.view.headerCt, colspan = headerCt.getColumnCount();
            return {
                rowBody : record.data.Text,
                rowBodyCls : this.rowBodyCls,
                rowBodyColspan : colspan
            };
        }
    });
    Ext.create('Ext.grid.Panel', {
        border: false,
        height: 430,
        width: '95%',
        store: myStore,
        layout: 'hbox',
//        features: [rowBodyFeature],
        columns: [
        {
            header: 'Answers',
            dataIndex: 'Name',
            width: '80%',
            renderer: function (value, p, record){
                return '<b>'+record.data.Name + '</b><br>'
                + record.data.Text;
            },
            editor: {
                allowBlank: false
            }
        },
        {
            xtype: 'datecolumn',
            header: 'Posted',
            dataIndex: 'Posted',
            field: {
                xtype: 'datefield',
                allowBlank: false,
                format: 'm/d/Y',
                minValue: '01/01/2006',
                minText: 'Cannot have a start date before the company existed!',
                maxValue: Ext.Date.format(new Date(), 'm/d/Y')
            },
            hidden: true
        },
        {
            header: 'Answer',
            dataIndex: 'Text',
            width: 800,
            editor: {
                xtype: 'htmleditor',
                height: 250,
                allowBlank: false
            },
            hidden: true
        }],

        viewConfig: {
            forceFit:true,
            showPreview:true
        },
        renderTo: document.body
    });
    Ext.create('Ext.toolbar.Toolbar', {
      renderTo: document.body,
      width   : 400,
      items: [{
           text: 'Add Answers',
           handler: function () {
               <%var aComment = new Comment();
                aComment.Name="New Answer"
                aComment.Text="Please provide a pithy answer..."%>
               <% using(target(CommentsCx#saveComment(db.model.Post, db.model.Comment))) { %>
                       var editor = new Ext.form.HtmlEditor({
                           xtype: 'htmleditor',
                           name: '${n(Comment#Text)}',
                           id: '${n(Comment#Text)}',
                           fieldLabel: '${n(Comment#Text)}',
                           height: 200,
                           anchor: '100%'
                          });
                      editor.setValue('${h(aComment.Text)}');
                      var hiddenPost = {
                            xtype:'hidden',
                            name: '${n(Post)}',
                            value: '${post.id}'
                          };

                      var top = new Ext.form.FormPanel({
                          id: 'commentForm',
                          collapsible: true,
                          frame: true,
                          title: 'Creating answer',
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
                                      fieldLabel: '${n(Comment#Name)}',
                                      name: '${n(Comment#Name)}',
                                      id: '${n(Comment#Name)}',
                                      anchor:'95%',
                                      value: '${h(aComment.Name)}'
                                  }]
                              }]
                          }, editor
                            ,hiddenPost
                          ],

                          buttons: [{
                              text: 'Save',
                              formBind: true,
                             handler: function() {
                                  var form = top.getForm();
                                  if(form.isValid()){
                                      form.submit({
                                          url: '${TargetURL}',
                                          waitMsg: 'Saving...',
                                          success: function(fp, o) {
//                                              Ext.Msg.alert('Success', 'Your post has been saved.');
                                              var record = Ext.create('Comment',{
                                                  Name: Ext.getCmp('${n(Comment#Name)}').getValue(),
                                                  Posted: new Date(),
                                                  Text: Ext.getCmp('${n(Comment#Text)}').getValue(),
                                              });
                                              myStore.add(record);
                                              myStore.commitChanges();
                                              top.destroy();
                                          }
                                      });
                                  }
                              }
                            },{
                              text: 'Cancel',
                              handler: function() {
                              }
                          }]
                      });

                      top.render(document.body);

                     }
               <% } %>
           }]
    });
});
</script>
