package org.stackoverflow.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.Date;
import java.util.List;

/**
 * Class description...
 *
 * @author ashahab
 */
public class Answer extends HasDateBase implements HasDate, HasAuthor, IsSerializable{
  private String text;
  private List<Comment> comments;
  private User author;


  public User author() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }
}
