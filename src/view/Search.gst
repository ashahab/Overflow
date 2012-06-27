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
    function messagesLoaded(messages) {
      messages.each(function(msg){
        alert("Title: " + msg.get("Title") + " Body: " + msg.get("Body"));
      });
    }
    Ext.define('SearchResult', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'Title', type: 'string'},
        {name: 'Body',  type: 'string'}
    ]
    });
    var store = Ext.create('Ext.data.Store', {
        model: 'SearchResult',
        proxy: {
            type: 'ajax',
            url: '${TargetURL}',
            reader: {
                type: 'json',
                root: 'Hits',
                record: 'Source',
                totalProperty: "totalHits"
            }
    }

    ,listeners: {
          load: messagesLoaded
        }
    });
    function renderResult(value, p, record){
        var res = record.data.Title +" : "+ record.data.Body;
        return res;
    }
    Ext.create('Ext.grid.Panel', {
        border: false,
        width: 485,
        height: 430,
        store: store,
        loadMask: true,
        columns: [{
            header: 'Results',
            dataIndex: 'Title',
            width: 480,
            renderer: renderResult
        }],

        viewConfig: {
            forceFit:true,
            enableRowBody:true,
            showPreview:true,
            getRowClass : function(record, rowIndex, p, store){
                p.body = '<p class="excerpt">'+record.data.Body+'</p>';
            }
        },
        tbar: [
            '->',
            new Ext.ux.form.SearchField({
                store: store,
                width: 200,
                emptyText: 'Ask!'
            })
        ],
        bbar: new Ext.PagingToolbar({
            pageSize: 8,
            store: store,
            displayInfo: true
        }),
        renderTo: Ext.getBody()
    });
/*
    var top = new Ext.form.FormPanel({
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
              store.load({params: {question: Ext.getCmp('question').getValue()}});
            }
          }]
    });

    top.render(document.body);
*/
});
</script>
<%}%>