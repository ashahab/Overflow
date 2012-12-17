package org.stackoverflow.client.view;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.HtmlEditor;
import com.sencha.gxt.widget.core.client.form.TextField;


/**
 * Class description...
 *
 * @author ashahab
 */
public class AskQuestionViewImpl extends Composite implements AskQuestionView {
  private Presenter _presenter;
  private VerticalLayoutContainer _panel;

  private final TextField _nameField;
  private final HtmlEditor _editor;
  private final Button _button;

  @Override
  public void setPresenter(Presenter presenter) {
    _presenter = presenter;
  }


  public AskQuestionViewImpl(){
    _panel = new VerticalLayoutContainer();
    _nameField = new TextField();
    _nameField.setEmptyText("Type your question here");
    _nameField.setWidth(1000);
    Label label = new Label("Title:");
    HorizontalPanel horizontalPanel = new HorizontalPanel();
    horizontalPanel.add(label);
    horizontalPanel.add(_nameField);
    _editor = new HtmlEditor();
    _editor.setWidth(900);
    _editor.setHeight(500);
    _button = new Button("<h1>Post your question</h1>");
    _button.setSize("500px", "100px");
    _panel.add(horizontalPanel);
    _panel.add(_editor);
    _panel.add(_button);
    initWidget(_panel);
  }

  @Override
  public TextField getNameField() {
    return _nameField;
  }

  @Override
  public HtmlEditor getEditor() {
    return _editor;
  }

  @Override
  public Button getButton() {
    return _button;
  }
}
