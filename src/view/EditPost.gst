<%@ extends ronin.RoninTemplate %>
<%@ params(aPost : db.model.Post) %>
<% uses controller.Overflow %>
<% uses db.model.Post %>
<% using(target(Overflow #save(Post))) { %>
<script type="text/javascript" >
Ext.require([
    //'Ext.form.*',
    //'Ext.layout.container.Column',
    //'Ext.tab.Panel'
    '*'
]);
//alert("poo before!");
Ext.onReady(function() {
    Ext.QuickTips.init();

    var bd = Ext.getBody();

    var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';

    bd.createChild({tag: 'h2', html: 'Create a post'});

    var editor = new Ext.form.HtmlEditor({
            xtype: 'htmleditor',
            name: '${n(Post#Body)}',
            fieldLabel: '${n(Post#Body)}',
            height: 200,
            anchor: '100%'
        });
    editor.setValue('${h(aPost.Body)}');
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
                    fieldLabel: '${n(Post#Title)}',
                    afterLabelTextTpl: required,
                    name: '${n(Post#Title)}',
                    anchor:'95%',
                    value: '${h(aPost.Title)}'
                }]
            }
			]
        }, editor],

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