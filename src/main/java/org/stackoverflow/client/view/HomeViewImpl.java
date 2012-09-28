package org.stackoverflow.client.view;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.form.TextField;

/**
 * Class description...
 *
 * @author ashahab
 */
public class HomeViewImpl extends Composite implements HomeView {
  private Presenter _presenter;
  private String _fileName;

  private VerticalLayoutContainer _panel;


  public HomeViewImpl() {
    Viewport viewport = new Viewport();
    _panel = new VerticalLayoutContainer();
    HorizontalLayoutContainer horiz = new HorizontalLayoutContainer();
    TextField searchField = new TextField();
    searchField.setEmptyText("Ask!");
    TextButton newQ = new TextButton();
    newQ.setText("Add new question!");
    horiz.add(searchField);
    horiz.add(newQ);

    _panel.add(horiz);

    viewport.add(_panel);
    initWidget(viewport);
  }
  @Override
  public void setPresenter(Presenter presenter) {
    _presenter = presenter;
  }

}
