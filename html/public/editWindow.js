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
            items:[config.editor, config.hiddenAnswerId, config.hiddenQuestionId]
        };
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
        /*
         "blur" : function(){
         this.focus()
         }
         */
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