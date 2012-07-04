 <%@ extends ronin.RoninTemplate %>
<% uses controller.Overflow %>
<% uses controller.Search %>
<% uses java.lang.String %>
<% uses db.model.Post %>
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

    Ext.define('SearchResult', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'Id', type: 'int'},
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
    });
    function renderResult(value, p, record){

        var res = '<a class="link" href="/Overflow/viewPost?post='+record.data.Id+'" target="_blank">'+record.data.Title+'</a>'
        + '<br>'
        + Ext.util.Format.substr(record.data.Body,0,100)
        ;
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
});
</script>
<%}%>