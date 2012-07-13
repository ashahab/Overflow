<%@ extends ronin.RoninTemplate %>
<%@ params(post: db.model.Question) %>
<%uses controller.Overflow %>
<% uses controller.Search %>
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
    <% for (answer in post.Answers) { print("answer: " + answer);%>
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
      store: myStore,
      layout: 'hbox',
      bbar: new Ext.PagingToolbar({
          pageSize: 8,
          store: myStore,
          displayInfo: true
      }),

      columns: [
      {
        xtype: 'actioncolumn'
        , width: 40
        , items: [{ // Delete button
          icon: 'http://whatisextjs.com/BAHO/icons/cancel.png'
          , handler:deleteAnswer
        },{ // Save Button
            icon: 'http://whatisextjs.com/BAHO/icons/note_edit.png',
            style: 'margin-left: 5px;',
            handler: editAnswer
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
      }

     ],

      viewConfig: {
          forceFit:true,
          showPreview:true
      }
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

 <%}%>
var searchPanel = Ext.create("gw.stackoverflow.RelatedResults",{
<% using(target(Search #getRelated(String))) { %>
  targetUrl:'${TargetURL}?query=${post.Title}&id=${post.Id}'
  <%}%>
});
var postBody = Ext.htmlDecode('${h(post.Body)}')
var BasePanel = function (config) {

 Ext.apply(config,{items: [ {
       columnWidth: .55,
       xtype: 'panel',
       layout: 'vbox',

       margins: '5 5 0 0',
       items: [{
           xtype:'panel',
           border: 'false',
           layout: {
                   type: 'vbox',
                   align: 'stretch',
                   flex:2,
           },
           items:[{
              id: 'titlePane',
              xtype:'panel',
              html:'<b>Title: </b>${h(post.Title)}'
            },{
              id: 'bodyPane',
              xtype:'panel',
              html:postBody
            },{
              id: 'postedPane',
              xtype:'panel',
              html:'<b>Posted: </b>${h(post.Posted as String)}'
            },gridPanel, answerBar]
       }]
     },searchPanel]});
        //call the base constructor
        BasePanel.superclass.constructor.call(this, config);
    }

    Ext.extend(BasePanel, Ext.Panel, {

        layout: 'column'

    });
    var win = new BasePanel({renderTo:document.body});



     function editAnswer(grid, rowIndex, colindex) {
      <% using(target(CommentsCx #saveComment(Question,Answer))) { %>
      // Working with grid row data

      var editWindow = Ext.create("gw.stackoverflow.EventWindow",{
        answerTextFieldName: '${n(Answer#Text)}',
        questionFieldName: '${n(Question)}',
        answerFieldName: '${n(Answer)}',
        record:grid.getStore().getAt(rowIndex),
        targetUrl:'${TargetURL}',
        store:myStore,
        gridView:gridPanel.getView()
      });
      editWindow.show();
      Ext.getBody().mask();
      <%}%>
    } // eo handler

    function deleteAnswer(grid, rowIndex, colindex) {
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
});

</script>

