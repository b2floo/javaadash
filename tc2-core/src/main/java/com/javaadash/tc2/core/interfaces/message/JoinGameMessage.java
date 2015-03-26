package com.javaadash.tc2.core.interfaces.message;

public class JoinGameMessage {
  private String username = null;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    return "JoinGameMessage [username=" + username + "]";
  }

}
