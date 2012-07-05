<%@ extends ronin.RoninTemplate %>
<%@ params(post : db.model.Question) %>
<% uses controller.CommentsCx %>
<% uses db.model.Question %>
<% uses db.model.Answer %>
<script type="text/javascript" >
Ext.require([
    //'Ext.form.*',
    //'Ext.layout.container.Column',
    //'Ext.tab.Panel'
    '*'
]);
Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.define('Answer', {
      extend: 'Ext.data.Model',
      fields: [
          {name: 'Name', type: 'string'},
          {name: 'Posted', type: 'date'},
          {name: 'Text', type: 'string'}
      ]
    });
    var myStore = Ext.create('Ext.data.Store', {
        model: 'Answer',
        proxy: {
            type: 'memory'
        },
        autoDestroy: true
    });
    <% for (answer in post.Answers) { %>
      var record = new Answer({
        Author: '${answer.Author}',
        Posted: '${answer.Posted}',
        Text: '${answer.Text}'
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
               <%var aComment = new Answer();
                aComment.Author="New Answer"
                aComment.Text="Please provide a pithy answer..."%>
               <% using(target(CommentsCx#saveComment(db.model.Question, db.model.Answer))) { %>
                       var editor = new Ext.form.HtmlEditor({
                           xtype: 'htmleditor',
                           name: '${n(Answer#Text)}',
                           id: '${n(Answer#Text)}',
                           fieldLabel: '${n(Answer#Text)}',
                           height: 200,
                           anchor: '100%'
                          });
                      editor.setValue('${h(aComment.Text)}');
                      var hiddenPost = {
                            xtype:'hidden',
                            name: '${n(Question)}',
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
                                      fieldLabel: '${n(Answer#Author)}',
                                      name: '${n(Answer#Author)}',
                                      id: '${n(Answer#Author)}',
                                      anchor:'95%',
                                      value: '${h(aComment.Author)}'
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
                                              var record = Ext.create('Answer',{
                                                  Author: Ext.getCmp('${n(Answer#Author)}').getValue(),
                                                  Posted: new Date(),
                                                  Text: Ext.getCmp('${n(Answer#Text)}').getValue(),
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
