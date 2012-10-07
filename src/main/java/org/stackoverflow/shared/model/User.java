package org.stackoverflow.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Class description...
 *
 * @author ashahab
 */
public class User implements IsSerializable {
  private String id;
  private String name;
  private String userName;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
