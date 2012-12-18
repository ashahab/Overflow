package org.stackoverflow.client.view;

import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;

/**
 * Created with IntelliJ IDEA.
 * User: ashahab
 * Date: 12/16/12
 * Time: 7:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class ViewQuestionViewImpl extends Composite implements ViewQuestionView  {
    private Presenter _presenter;
    private HTML _queryLabel;
    private HTML _descLabel;
    private Button _editButton;
    private HTML _postedLabel;
    private HTML _userLabel;

    @Override
    public void setPresenter(Presenter presenter) {
        _presenter = presenter;
    }
    public ViewQuestionViewImpl() {
        VerticalLayoutContainer _panel = new VerticalLayoutContainer();
        HorizontalPanel queryPanel = new HorizontalPanel();

        _queryLabel = new HTML();
        queryPanel.add(new HTML("<h1>Query:</h1>"));
        queryPanel.add(_queryLabel);

        HorizontalPanel descPanel = new HorizontalPanel();
        _descLabel = new HTML();
        descPanel.add(new HTML("<h1>Description:</h1>"));
        descPanel.add(_descLabel);

        _postedLabel = new HTML();
        HorizontalPanel postedPanel = new HorizontalPanel();
        postedPanel.add(new HTML("<h1>Posted:</h1>"));
        postedPanel.add(_postedLabel);
        _userLabel = new HTML();
        HorizontalPanel userPanel = new HorizontalPanel();
        userPanel.add(new HTML("<h1>User:</h1>"));
        userPanel.add(_userLabel);
        _editButton = new Button("Edit");
        _panel.add(queryPanel);
        _panel.add(descPanel);

        _panel.add(postedPanel);
        _panel.add(userPanel);
        _panel.add(_editButton);
        initWidget(_panel);
    }

    @Override
    public HTML getQueryLabel() {
        return _queryLabel;
    }

    @Override
    public HTML getDescLabel() {
        return _descLabel;
    }

    @Override
    public HTML getPostedLabel() {
        return _postedLabel;
    }

    @Override
    public HTML getUserLabel() {
        return _userLabel;
    }

    @Override
    public Button getEditButton() {
        return _editButton;

    }
}
