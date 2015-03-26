package com.javaadash.tc2.core.interfaces.message;

import java.util.List;

import com.javaadash.tc2.core.card.CardDescription;

public class UpdateGameMessage {
  private Integer gameState;
  private List<CardDescription> myHand;
  private List<CardDescription> myCharacters;
  private List<CardDescription> myOpponentCharacters;

  public Integer getGameState() {
    return gameState;
  }

  public void setGameState(Integer gameState) {
    this.gameState = gameState;
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
    return "UpdateGameMessage [gameState=" + gameState + ", myHand=" + myHand + ", myCharacters="
        + myCharacters + ", myOpponentCharacters=" + myOpponentCharacters + "]";
  }
}
