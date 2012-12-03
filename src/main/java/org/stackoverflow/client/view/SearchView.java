package org.stackoverflow.client.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Class description...
 *
 * @author ashahab
 */
public interface SearchView extends IsWidget{
  void setPresenter(Presenter presenter);

  /**
   * Hooks where clicking a link should take this
   */
  public interface Presenter {
    void goTo(Place place);
  }
}
