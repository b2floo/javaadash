package com.javaadash.tc2.client.message;

public class StartGameMessage {
  private String message;

  public StartGameMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
