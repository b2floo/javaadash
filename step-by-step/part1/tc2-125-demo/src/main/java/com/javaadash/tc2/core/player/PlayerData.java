package com.javaadash.tc2.core.player;

import java.util.LinkedList;

import com.javaadash.tc2.core.card.CardProxy;

public class PlayerData {

  private Player player = null;
  private LinkedList<CardProxy> playedCards = new LinkedList<CardProxy>();
  private Integer playerState;

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public LinkedList<CardProxy> getPlayedCards() {
    return playedCards;
  }

  public Integer getPlayerState() {
    return playerState;
  }

  public void setPlayerState(Integer playerState) {
    this.playerState = playerState;
  }
}
