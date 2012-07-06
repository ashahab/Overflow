<%@ extends ronin.RoninTemplate %>
<%@ params(post : db.model.Question) %>
<% uses controller.CommentsCx %>
<% uses controller.Overflow %>
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
          {name: 'Id', type: 'int'},
          {name: 'Author', type: 'string'},
          {name: 'Posted', type: 'date', format: 'm/d/Y'},
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
    <% for (answer in post.Answers) { print("answer: " + answer)%>
      var record = new Answer({
        Id: '${answer.Id}',
        Author: '${answer.Author}',
        Posted: '${answer.Posted}',
        Text: '${answer.Text}'
      });
      myStore.add(record);
      myStore.commitChanges();
    <% } %>


    Ext.create('Ext.grid.Panel', {
        border: false,
        height: 430,
        width: '95%',
        store: myStore,
        layout: 'hbox',
        columns: [
        {
          xtype: 'actioncolumn'
          , width: 40
          , items: [{ // Delete button
            icon: 'http://whatisextjs.com/BAHO/icons/cancel.png'
            , handler: function(grid, rowIndex, colindex) {
              // Working with grid row data
              var record = grid.getStore().getAt(rowIndex);
              <% using(target(CommentsCx #deleteComment(Answer))) { %>
              Ext.Ajax.request({
                  url: '${TargetURL}',
                  success: function (){
                    myStore.remove(record);
                    myStore.commitChanges();
                  },
                  params: { '${n(Answer)}': record.data.Id },
                  failure: function (){alert('Fail...');}
              });
              <%}%>
            } // eo handler
          },{ // Save Button
            icon: 'http://whatisextjs.com/BAHO/icons/note_edit.png'
            // style no go :(
            , style: 'margin-left: 5px;'
            , handler: function(grid, rowIndex, colindex) {
              // Working with grid row data
              var record = grid.getStore().getAt(rowIndex);
              Ext.Msg.alert('Save', 'Save user: ' + record.data.Id);
            } // eo handler
          }]
        },
        {
            header: 'Answers',
            dataIndex: 'Text',
            width: '80%',
            renderer: function (value, p, record){
                return '<b>'+record.data.Author + '</b>'+ ' - ' + record.data.Posted + '<br>'
                + record.data.Text;
            },
            editor: {
                xtype: 'htmleditor',
                height: 250,
                allowBlank: false
            },
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
            dataIndex: 'Author',
            width: 800,
            editor: {
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
                aComment.Author="Abin"
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
                                          success: function(form,action) {
//                                              Ext.Msg.alert('Success', 'Your post has been saved.');
                                              var record = Ext.create('Answer',{
                                                  Id: action.result.data.Id,
                                                  Author: Ext.getCmp('${n(Answer#Author)}').getValue(),
                                                  Posted: Ext.getCmp('${n(Answer#Posted)}').getValue(),
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
