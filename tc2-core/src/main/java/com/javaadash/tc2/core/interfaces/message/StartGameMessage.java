package com.javaadash.tc2.core.interfaces.message;


public class StartGameMessage {

  private String opponent;

  public StartGameMessage() {}

  public StartGameMessage(String opponent) {
    this.opponent = opponent;
  }

  public String getOpponent() {
    return opponent;
  }

  public void setOpponent(String opponent) {
    this.opponent = opponent;
  }

  @Override
  public String toString() {
    return "StartGameMessage [opponent=" + opponent + "]";
  }
}
