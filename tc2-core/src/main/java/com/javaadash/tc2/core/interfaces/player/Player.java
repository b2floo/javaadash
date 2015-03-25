package com.javaadash.tc2.core.interfaces.player;

import java.io.Serializable;

import com.javaadash.tc2.core.board.InGameDeck;
import com.javaadash.tc2.core.card.Deck;
import com.javaadash.tc2.core.exceptions.TC2CoreException;

public class Player implements Serializable {
  private static final long serialVersionUID = 1354384669678934166L;

  private InGameDeck ingameDeck = null;
  private String name = null;
  private int score = 0;
  private TC2PlayerInterface playerInterface;

  public Player() {

  }

  public Player(String name, Deck deck, int initialHandSize, TC2PlayerInterface playerInterface)
      throws TC2CoreException {
    if (!deck.isFull())
      throw new TC2CoreException("The selected deck is not full");
    this.ingameDeck = new InGameDeck(deck, initialHandSize);
    this.name = name;
    this.playerInterface = playerInterface;
  }

  public String getName() {
    return name;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public InGameDeck getIngameDeck() {
    return ingameDeck;
  }

  public TC2PlayerInterface getPlayerInterface() {
    return playerInterface;
  }

  public void setPlayerInterface(TC2PlayerInterface playerInterface) {
    this.playerInterface = playerInterface;
  }

  @Override
  public String toString() {
    return name;
  }
}
