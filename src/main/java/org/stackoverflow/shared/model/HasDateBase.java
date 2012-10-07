package org.stackoverflow.shared.model;

import java.util.Date;

/**
 * Class description...
 *
 * @author ashahab
 */
public abstract class HasDateBase {
  private Date posted;
  public Date postedOn() {
    return posted;
  }

  public void setPosted(Date posted) {
    this.posted = posted;
  }
}
