Ext.define('gw.stackoverflow.EditQuestion', {
    extend:'Ext.window.Window',

    requires:[
        'Ext.form.Panel'
    ],

    constructor:function (config) {

        var editor = new Ext.form.field.HtmlEditor({
            xtype:'htmleditor',
            name:config.questionBodyName,
            fieldLabel:'Description',
            height:200,
            anchor:'100%'
        });
        editor.setValue(config.question.data.Body);
        var hidden = {
            xtype:'hidden',
            name:config.questionIdName,
            value:config.question.data.Id
        };
        var title = {
            id: 'titleField',
                xtype:'textfield',
            fieldLabel:'Title',
            required: 'true',
            name:config.questionTitleName,
            anchor:'95%',
            value:config.question.data.Title
        };
        var formPanelCfg = new Ext.form.FormPanel({
            bodyPadding:'5 5 0',
            fieldDefaults:{
                labelAlign:'top',
                msgTarget:'side'
            },
            items:[
                {
                    xtype:'container',
                    anchor:'100%',
                    layout:'hbox',
                    items:[
                        {
                            xtype:'container',
                            flex:1,
                            layout:'anchor',
                            items:[
                                title
                            ]
                        }
                    ]
                },
                editor,
                hidden
            ]
        });
        this.callParent([Ext.apply({
                title:'Edit question',
                width:600,
                height:250,
                autocreate:true,
                closeAction:'hide',
                modal:false,
                resizable:false,
                buttonAlign:'left',
                layout:'fit',
                items: formPanelCfg,
                fbar:[
                    {
                        text:'Save',
                        disabled:false,
                        handler:function(){this.onSave(config.targetUrl, title, editor, formPanelCfg.form);},
                        scope: this
                    },
                    {
                        text:'Cancel',
                        disabled:false,
                        handler:function () {
                            this.hide();
                            Ext.getBody().unmask();
                        },
                        scope: this
                    }
                ]
            },
            config)]);
    },
    listeners:{
        scope:this,
        "close":function () {
            Ext.getBody().unmask();
        }
    },
    onSave: function (targetUrl, title, editor, form) {

        if (form.isValid()) {
            form.submit({
                url:targetUrl,
                waitMsg:'Saving...',
                success:function (fp, o) {
                    var titleLabel = Ext.getCmp('titleLabel');
                    titleLabel.setText(title.value);


                    var bodyPane = Ext.getCmp('bodyPane');
                    bodyPane.html = editor.value;

                    var titlePane = Ext.getCmp('titlePane');
                    titlePane.remove
                    titlePane.insert(1,titleLabel)

                    var outerPane = Ext.getCmp('outerPane');
//                    outerPane.add(bodyPane);
                    titlePane.doLayout();
                    bodyPane.doLayout();
                    outerPane.doLayout();
                    outerPane.doComponentLayout();
                    Ext.getBody().unmask();
                    fp.owner.ownerCt.hide();
                }
            });
        }
    }
});
