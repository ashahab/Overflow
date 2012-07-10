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
    <% for (answer in post.Answers) {%>
      var record = new Answer({
        Id: '${answer.Id}',
        Author: '${answer.Author}',
        Posted: '${answer.Posted}',
        Text: '${answer.Text}'
      });
      myStore.add(record);
      myStore.commitChanges();
    <% } %>


    var gridPanel = Ext.create('Ext.grid.Panel', {
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
              icon: 'http://whatisextjs.com/BAHO/icons/note_edit.png',
              style: 'margin-left: 5px;',
              handler: function(grid, rowIndex, colindex) {
              <% using(target(CommentsCx #saveComment(Question,Answer))) { %>
              // Working with grid row data
              var record = grid.getStore().getAt(rowIndex);

              var heditor = new Ext.form.HtmlEditor({
                  xtype: 'htmleditor',
                  name: '${n(Answer#Text)}',
                  fieldLabel: '${n(Answer#Text)}',
                  height: 200,
                  anchor: '100%'
              });
              heditor.setValue(record.data.Text);
              var hiddenQuestionId = {
                  xtype:'hidden',
                  name: '${n(Question)}',
                  value: '${post.id}'
              };
              var hiddenAnswerId = {
                  xtype:'hidden',
                  name: '${n(Answer)}',
                  value: record.data.Id
              };

              var editWindow = Ext.create("gw.stackoverflow.EventWindow",{
                hiddenQuestionId:hiddenQuestionId,
                hiddenAnswerId:hiddenAnswerId,
                editor:heditor,
                record:record,
                targetUrl:'${TargetURL}',
                store:myStore,
                gridView:gridPanel.getView()
              });
              editWindow.show();
              Ext.getBody().mask();
              <%}%>
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
     <% using(target(CommentsCx#saveComment(db.model.Question, db.model.Answer))) { %>
        var answerBar = Ext.create("gw.stackoverflow.AddAnswers",{
          answerTextFieldName: '${n(Answer#Text)}',
          questionFieldName: '${n(Question)}',
          answerFieldName: '${n(Answer)}',
          authorFieldName: '${n(Answer#Author)}',
          postId: '${post.id}',
          targetUrl: '${TargetURL}',
          store:myStore
        });
        answerBar.render(document.body);
     <%}%>
});
</script>
