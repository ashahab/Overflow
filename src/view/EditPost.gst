<%@ extends ronin.RoninTemplate %>
<%@ params(aPost : db.model.Post) %>
<% uses controller.Overflow %>
<% uses db.model.Post %>
<script type="text/javascript" >
Ext.require([
    //'Ext.form.*',
    //'Ext.layout.container.Column',
    //'Ext.tab.Panel'
    '*'
]);
alert("poo before!");
Ext.onReady(function() {
alert("poo!");
    Ext.QuickTips.init();

    var bd = Ext.getBody();

    var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';

    bd.createChild({tag: 'h2', html: 'Create a post'});


    var top = Ext.widget({
        xtype: 'form',
        id: 'postForm',
        collapsible: true,
        frame: true,
        title: 'Creating posts',
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
                    fieldLabel: 'Title',
                    afterLabelTextTpl: required,
                    name: 'first',
                    anchor:'95%',
                    value: ''
                }]
            }
			]
        }, {
            xtype: 'htmleditor',
            name: 'bio',
            fieldLabel: 'Biography',
            height: 200,
            anchor: '100%'
        }],

        buttons: [{
            text: 'Save'
        },{
            text: 'Cancel'
        }]
    });

    top.render(document.body);

});
</script>
