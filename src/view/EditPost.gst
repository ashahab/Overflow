<%@ extends ronin.RoninTemplate %>
<%@ params(aPost : db.model.Question) %>
<% uses controller.Overflow %>
<% uses db.model.Question %>
<% using(target(Overflow #save(Question))) { %>
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

    bd.createChild({tag: 'h2', html: 'Create a post'});

    var editor = new Ext.form.field.HtmlEditor({
            xtype: 'htmleditor',
            name: '${n(Question#Body)}',
            fieldLabel: 'Description',
            height: 200,
            anchor: '100%'
        });
    editor.setValue('${h(aPost.Body)}');
    var hidden = {
          xtype:'hidden',
          name: '${n(Question)}',
          value: '${aPost.id}'
        };
    var top = new Ext.form.FormPanel({
        standardSubmit: true,
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
//        beforeAction: function(action){
//          editor.setValue(Ext.htmlEncode(editor.getValue()));
//        },
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
                    fieldLabel: '${n(Question#Title)}',
                    afterLabelTextTpl: required,
                    name: '${n(Question#Title)}',
                    anchor:'95%',
                    value: '${h(aPost.Title)}'
                }]
            }
			]
        }, editor
        <% if(not aPost.New) { %>
        ,hidden
        <% } %>],

        buttons: [{
            text: 'Save',
            formBind: true,
           handler: function() {
                var form = top.getForm();
                if(form.isValid()){
                    form.submit({
                        url: '${TargetURL}',
                        waitMsg: 'Saving...',
                        success: function(fp, o) {
                            Ext.Msg.alert('Success', 'Your post has been saved.');
                        }
                    });
                }
            }
          },{
            text: 'Cancel',
            handler: function() {
              window.location.href='${urlFor(Overflow #viewPost(aPost))}'
            }
        }]
    });

    top.render(document.body);

});
</script>
<% } %>