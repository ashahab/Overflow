package org.stackoverflow.client.view;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.widget.core.client.form.HtmlEditor;

/**
 * Class description...
 *
 * @author ashahab
 */
public interface AskQuestionView extends IsWidget {
  public void setPresenter(Presenter presenter);

  HasValue<String> getNameField();

  TakesValue<String> getEditor();

  HasClickHandlers getButton();

  public interface Presenter {
    void goTo(Place place);
  }
}
