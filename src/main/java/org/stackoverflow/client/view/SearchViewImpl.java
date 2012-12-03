package org.stackoverflow.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.widget.client.TextButton;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.form.TextField;
import org.stackoverflow.client.places.AskQuestionPlace;

/**
 * Class description...
 *
 * @author ashahab
 */
public class SearchViewImpl extends Composite implements SearchView {
  private Presenter _presenter;

  private VerticalLayoutContainer _panel;


  public SearchViewImpl() {
    Viewport viewport = new Viewport();
    _panel = new VerticalLayoutContainer();
    HorizontalLayoutContainer horiz = new HorizontalLayoutContainer();
    TextField searchField = new TextField();
    searchField.setEmptyText("Ask!");
    TextButton newQ = new TextButton();
    newQ.setText("Add new question!");
    newQ.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        _presenter.goTo(new AskQuestionPlace("question"));
      }
    });
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
