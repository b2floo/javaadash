package com.javaadash.tc2.core.interfaces.player;

import java.util.LinkedList;

import com.javaadash.tc2.core.card.CardDescription;

public class PlayerData {

  private Player player = null;
  private LinkedList<CardDescription> playedCards = new LinkedList<CardDescription>();
  private Integer playerState;

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  // TODO utility methods
  public LinkedList<CardDescription> getPlayedCards() {
    return playedCards;
  }

  public Integer getPlayerState() {
    return playerState;
  }

  public void setPlayerState(Integer playerState) {
    this.playerState = playerState;
  }

}
