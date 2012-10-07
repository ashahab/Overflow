package org.stackoverflow.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.Date;

/**
 * Class description...
 *
 * @author ashahab
 */
public class Comment extends HasDateBase implements HasDate, HasAuthor, IsSerializable {
  private String text;
  private User author;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public User author() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

}
