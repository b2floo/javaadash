package com.javaadash.tc2.core.interfaces.player;

import java.util.LinkedList;
import java.util.Queue;

import com.javaadash.tc2.core.card.CardDescription;

public class PlayerData {

  private Player player = null;
  private Queue<CardDescription> playedCards = new LinkedList<CardDescription>();
  private Integer playerState;

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  // TODO utility methods
  public Queue<CardDescription> getPlayedCards() {
    return playedCards;
  }

  public Integer getPlayerState() {
    return playerState;
  }

  public void setPlayerState(Integer playerState) {
    this.playerState = playerState;
  }

}
