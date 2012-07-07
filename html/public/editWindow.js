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
                    handler:this.onSave,
                    scope: this
                },
                {
                    text:'Cancel',
                    disabled:false,
                    handler:function () {
                        this.hide();
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

        this.addEvents({
            /**
             * @event eventadd
             * Fires after a new event is added
             * @param {Ext.calendar.form.EventWindow} this
             * @param {Ext.calendar.EventRecord} rec The new {@link Ext.calendar.EventRecord record} that was added
             */
            eventadd: true,
            /**
             * @event eventupdate
             * Fires after an existing event is updated
             * @param {Ext.calendar.form.EventWindow} this
             * @param {Ext.calendar.EventRecord} rec The new {@link Ext.calendar.EventRecord record} that was updated
             */
            eventupdate: true,
            /**
             * @event eventdelete
             * Fires after an event is deleted
             * @param {Ext.calendar.form.EventWindow} this
             * @param {Ext.calendar.EventRecord} rec The new {@link Ext.calendar.EventRecord record} that was deleted
             */
            eventdelete: true,
            /**
             * @event eventcancel
             * Fires after an event add/edit operation is canceled by the user and no store update took place
             * @param {Ext.calendar.form.EventWindow} this
             * @param {Ext.calendar.EventRecord} rec The new {@link Ext.calendar.EventRecord record} that was canceled
             */
            eventcancel: true,
            /**
             * @event editdetails
             * Fires when the user selects the option in this window to continue editing in the detailed edit form
             * (by default, an instance of {@link Ext.calendar.EventEditForm}. Handling code should hide this window
             * and transfer the current event record to the appropriate instance of the detailed form by showing it
             * and calling {@link Ext.calendar.EventEditForm#loadRecord loadRecord}.
             * @param {Ext.calendar.form.EventWindow} this
             * @param {Ext.calendar.EventRecord} rec The {@link Ext.calendar.EventRecord record} that is currently being edited
             */
            editdetails: true
        });
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
    onSave: function(){
        if(!this.formPanel.form.isValid()){
            return;
        }

        this.formPanel.form.submit({
            url:'${TargetURL}',
            waitMsg:'Saving...',
            success:function (fp, o) {
                Ext.Msg.alert('Success', 'Your post has been saved.');
//                config.record.data.Text = editor.getValue();
//                myStore.commitChanges();
            }
        });
    }
});