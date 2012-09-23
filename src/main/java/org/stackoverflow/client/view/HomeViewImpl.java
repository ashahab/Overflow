package org.stackoverflow.client.view;

import com.google.gwt.user.client.Command;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;

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

  }
  @Override
  public void setPresenter(Presenter presenter) {
    _presenter = presenter;
  }

  @Override
  public void setOnFinishUploaderHandler(final Command upload){

  }

}
