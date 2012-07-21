/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 7/10/12
 * Time: 10:17 AM
 * To change this template use File | Settings | File Templates.
 */

Ext.define('gw.stackoverflow.AddAnswers', {
    extend:'Ext.form.Panel',
    frame:true,
    title:'Creating answer',

    fieldDefaults:{
        labelAlign:'top',
        msgTarget:'side'
    },

    initComponent:function () {
        this.editor = new Ext.form.field.HtmlEditor({
            name:this.answerTextFieldName,
            id:this.answerTextFieldName,
            fieldLabel:'Answer',
            anchor:'100%',
            layout:'fit'
        });
        this.items = [
            {
                xtype:'hidden',
                name:this.authorFieldName,
                id:this.authorFieldName,
                value:'Abin'
            },
            this.editor,
            {
                xtype:'hidden',
                name:this.questionFieldName,
                value:this.postId
            }
        ];
        this.bbar =
            [{
                text:'Post your answer',
                formBind:true,
                scope: this,
                handler:this.onSave
            }
        ];
        this.callParent(arguments);
    },
    onSave: function () {
        var myForm = this.getForm();
        if (myForm.isValid()) {
            myForm.submit({
                url:this.targetUrl,
                waitMsg:'Saving...',
                success:function(form, action){
                    var record = Ext.create('Answer', {
                        Id:action.result.Id,
                        Posted:action.result.Posted,
                        Author:Ext.getCmp(form.authorFieldName).value,
                        Text:Ext.getCmp(form.answerTextFieldName).value
                    });
                    form.store.add(record);
                    form.store.commitChanges();
                    form.owner.editor.setValue("");
                },
                failure:function(form, action){
                    var record = Ext.create('Answer', {
                        Id:action.result.Id,
                        Posted:action.result.Posted,
                        Author:Ext.getCmp(form.authorFieldName).value,
                        Text:Ext.getCmp(form.answerTextFieldName).value
                    });
                    form.store.add(record);
                    form.store.commitChanges();
                    form.owner.editor.setValue("");
                }
            });
        }
    },
    onCancel: function () {
        this.destroy();
    }
});