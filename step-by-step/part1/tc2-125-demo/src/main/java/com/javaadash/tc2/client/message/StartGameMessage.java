package com.javaadash.tc2.client.message;

/**
 * Message containing description of game to start
 * 
 * @author b2floo
 *
 */
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
