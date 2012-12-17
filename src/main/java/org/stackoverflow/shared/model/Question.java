package org.stackoverflow.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.Date;
import java.util.List;

/**
 * Class description...
 *
 * @author ashahab
 */
public class Question extends HasDateBase implements HasDate, HasAuthor, IsSerializable {
  private String id;
  private String query;
  private String description;
  private Boolean answered;
  private User user;
  private List<Comment> comments;

  public List<Comment> getComments() {
    return comments;
  }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  public User author() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getAnswered() {
    return answered;
  }

  public void setAnswered(Boolean answered) {
    this.answered = answered;
  }
}
