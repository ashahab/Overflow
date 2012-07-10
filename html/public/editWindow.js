/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 7/6/12
 * Time: 5:52 PM
 * To change this template use File | Settings | File Templates.
 */


Ext.define('gw.stackoverflow.EventWindow', {
    extend:'Ext.window.Window',

    requires:[
        'Ext.form.Panel'
    ],

    constructor:function (config) {
        var editor = new Ext.form.HtmlEditor({
            xtype: 'htmleditor',
            name: config.answerTextFieldName,
            fieldLabel: 'Text',
            height: 200,
            anchor: '100%'
        });
        editor.setValue(config.record.data.Text);
        var hiddenQuestionId = {
            xtype:'hidden',
            name: config.questionFieldName,
            value: config.postId
        };
        var hiddenAnswerId = {
            xtype:'hidden',
            name: config.answerFieldName,
            value: config.record.data.Id
        };
        var formPanelCfg = {
            xtype:'form',
            fieldDefaults:{
                msgTarget:'side',
                labelWidth:65
            },
            frame:false,
            bodyStyle:'background:transparent;padding:5px 10px 10px;',
            bodyBorder:false,
            border:false,
            items:[editor, hiddenAnswerId, hiddenQuestionId]
        };
        config.editor = editor;
        this.callParent([Ext.apply({
            title:'Edit answer',
            width:600,
            height:250,
            autocreate:true,
            border:true,
            closeAction:'hide',
            modal:false,
            resizable:false,
            buttonAlign:'left',
            savingMessage:'Saving changes...',
            layout:'fit',
            items: formPanelCfg,
            fbar:[
                {
                    text:'Save',
                    disabled:false,
                    handler:function(){this.onSave(config);},
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
    initComponent: function() {
        this.callParent();

        this.formPanel = this.items.items[0];
    },
    listeners:{
        scope:this,
        "close":function () {
            Ext.getBody().unmask();
        }
    },


    // private
    onSave: function(config){
        if(!this.formPanel.form.isValid()){
            return;
        }

        this.formPanel.form.submit({
            url:config.targetUrl,
            waitMsg:'Saving...',
            success:function (fp, o) {
                config.record.data.Text = config.editor.getValue();
                config.store.commitChanges();
                config.gridView.refresh();

                Ext.getBody().unmask();
                fp.owner.ownerCt.hide();
            }
        });
    }
});