/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 7/10/12
 * Time: 10:17 AM
 * To change this template use File | Settings | File Templates.
 */

Ext.define('gw.stackoverflow.AddAnswers', {
    extend:'Ext.toolbar.Toolbar',
    requires:[
        'Ext.form.Panel'
    ],
    constructor:function (config) {
        this.callParent([Ext.apply({


                items:[
                    {
                        text:'Add Answers',
                        handler:function () {

                            var editor = new Ext.form.HtmlEditor({
                                xtype:'htmleditor',
                                name:config.answerTextFieldName,
                                id:config.answerTextFieldName,
                                fieldLabel:'Answer',
                                height:200,
                                anchor:'100%'
                            });
                            editor.setValue("Please provide a pithy answer...");
                            var hiddenPost = {
                                xtype:'hidden',
                                name:config.questionFieldName,
                                value:config.postId
                            };

                            var top = new Ext.form.FormPanel({
                                id:'commentForm',
                                collapsible:true,
                                frame:true,
                                title:'Creating answer',
                                bodyPadding:'5 5 0',
                                width:600,
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
                                                    {
                                                        xtype:'textfield',
                                                        fieldLabel:'Author',
                                                        name:config.authorFieldName,
                                                        id:config.authorFieldName,
                                                        anchor:'95%',
                                                        value:'Abin'
                                                    }
                                                ]
                                            }
                                        ]
                                    },
                                    editor
                                    ,
                                    hiddenPost
                                ],

                                buttons:[
                                    {
                                        text:'Save',
                                        formBind:true,
                                        handler:function () {
                                            var form = top.getForm();
                                            if (form.isValid()) {
                                                form.submit({
                                                    url:config.targetUrl,
                                                    waitMsg:'Saving...',
                                                    success:function (form, action) {
                                                        var record = Ext.create('Answer', {
                                                            Id:action.result.Id,
                                                            Posted:action.result.Posted,
                                                            Author:Ext.getCmp(config.authorFieldName).getValue(),
                                                            Text:Ext.getCmp(config.answerTextFieldName).getValue()
                                                        });
                                                        config.store.add(record);
                                                        config.store.commitChanges();
                                                        top.destroy();
                                                    },
                                                    failure: function(form, action){
                                                        var record = Ext.create('Answer', {
                                                            Id:action.result.Id,
                                                            Posted:action.result.Posted,
                                                            Author:Ext.getCmp(config.authorFieldName).getValue(),
                                                            Text:Ext.getCmp(config.answerTextFieldName).getValue()
                                                        });
                                                        config.store.add(record);
                                                        config.store.commitChanges();
                                                        top.destroy();
                                                    }
                                                });
                                            }
                                        }
                                    },
                                    {
                                        text:'Cancel',
                                        handler:function () {
                                            top.destroy();
                                        }
                                    }
                                ]
                            });

                            top.render(document.body);

                        }
                    }
                ]},
            config)]);
    }
});