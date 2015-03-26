package com.javaadash.tc2.core.interfaces.message;

import java.util.List;

import com.javaadash.tc2.core.card.CardDescription;


public class StartGameMessage {

  private String opponent;
  private List<CardDescription> myHand;
  private List<CardDescription> myCharacters;
  private List<CardDescription> myOpponentCharacters;

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

  public List<CardDescription> getMyHand() {
    return myHand;
  }

  public void setMyHand(List<CardDescription> myHand) {
    this.myHand = myHand;
  }

  public List<CardDescription> getMyCharacters() {
    return myCharacters;
  }

  public void setMyCharacters(List<CardDescription> myCharacters) {
    this.myCharacters = myCharacters;
  }

  public List<CardDescription> getMyOpponentCharacters() {
    return myOpponentCharacters;
  }

  public void setMyOpponentCharacters(List<CardDescription> myOpponentCharacters) {
    this.myOpponentCharacters = myOpponentCharacters;
  }

  @Override
  public String toString() {
    return "StartGameMessage [opponent=" + opponent + ", myHand=" + myHand + ", myCharacters="
        + myCharacters + ", myOpponentCharacters=" + myOpponentCharacters + "]";
  }

}
