/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 7/12/12
 * Time: 11:02 AM
 * To change this template use File | Settings | File Templates.
 */
Ext.require([
    //'Ext.form.*',
    //'Ext.layout.container.Column',
    //'Ext.tab.Panel'
    '*'
]);
Ext.define('SearchResult', {
    extend:'Ext.data.Model',
    fields:[
        {name:'Id', type:'int'},
        {name:'Title', type:'string'},
        {name:'Body', type:'string'}
    ]
});
Ext.define('gw.stackoverflow.RelatedResults', {
    extend:'Ext.panel.Panel',
    constructor:function (config) {
        var store = Ext.create('Ext.data.Store', {
            model:'SearchResult',
            proxy:{
                type:'ajax',
                url:config.targetUrl,
                reader:{
                    type:'json',
                    root:'Hits',
                    record:'Source',
                    totalProperty:"totalHits"
                }
            },
            autoLoad: config.autoload
        });

        function renderResult(value, p, record) {

            var res = '<a class="link" href="/Overflow/viewPost?post=' + record.data.Id + '" target="_blank">' + record.data.Title + '</a>'
                    + '<br>'
                    + Ext.util.Format.substr(record.data.Body, 0, 100)
                ;
            return res;
        };

        var searchField = new Ext.ux.form.SearchField({
            store: store,
            width: 300,
            emptyText: 'Ask!'
        });
        if(!config.search){
            searchField = null;
        }
        this.callParent([Ext.apply({
                layout: 'fit',
                frame: true,
                items:Ext.create('Ext.grid.Panel', {
                    border:false,
//                    forceFit:true,
                    height:430,
                    store:store,
                    loadMask:true,
                    columns:[
                        {
                            forceFit:true,
                            flex: 1,
//                            width: "100%",
                            header:'Results',
                            dataIndex:'Title',
                            renderer:renderResult
                        }
                    ],

                    viewConfig:{
                        enableRowBody:true,
                        showPreview:true,
                        getRowClass:function (record, rowIndex, p, store) {
                            p.body = '<p class="excerpt">' + record.data.Body + '</p>';
                        }
                    },
                    bbar:new Ext.PagingToolbar({
                        pageSize:8,
                        store:store,
                        displayInfo:true
                    }),

                    tbar:searchField,
                    renderTo:Ext.getBody()
                })},
            config)]);
    }
});