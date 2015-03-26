package com.javaadash.tc2.core.interfaces.message;

import java.util.List;

import com.javaadash.tc2.core.card.CardDescription;

public class UpdateGameMessage {
  private Integer turn;
  private Integer gameState;
  private List<CardDescription> myHand;
  private List<CardDescription> myCharacters;
  private List<CardDescription> myBoard;
  private List<CardDescription> myDiscard;
  private List<CardDescription> myOpponentCharacters;
  private List<CardDescription> myOpponentBoard;
  private List<CardDescription> myOpponentDiscard;

  public Integer getTurn() {
    return turn;
  }

  public void setTurn(Integer turn) {
    this.turn = turn;
  }

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

  public List<CardDescription> getMyBoard() {
    return myBoard;
  }

  public void setMyBoard(List<CardDescription> myBoard) {
    this.myBoard = myBoard;
  }

  public List<CardDescription> getMyDiscard() {
    return myDiscard;
  }

  public void setMyDiscard(List<CardDescription> myDiscard) {
    this.myDiscard = myDiscard;
  }

  public List<CardDescription> getMyOpponentBoard() {
    return myOpponentBoard;
  }

  public void setMyOpponentBoard(List<CardDescription> myOpponentBoard) {
    this.myOpponentBoard = myOpponentBoard;
  }

  public List<CardDescription> getMyOpponentDiscard() {
    return myOpponentDiscard;
  }

  public void setMyOpponentDiscard(List<CardDescription> myOpponentDiscard) {
    this.myOpponentDiscard = myOpponentDiscard;
  }

  @Override
  public String toString() {
    return "UpdateGameMessage [gameState=" + gameState + ", myHand=" + myHand + ", myCharacters="
        + myCharacters + ", myBoard=" + myBoard + ", myDiscard=" + myDiscard
        + ", myOpponentCharacters=" + myOpponentCharacters + ", myOpponentBoard=" + myOpponentBoard
        + ", myOpponentDiscard=" + myOpponentDiscard + "]";
  }
}
