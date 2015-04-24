package com.javaadash.tc2.core.player;

import java.io.Serializable;

import com.javaadash.tc2.client.TC2PlayerInterface;

public class Player implements Serializable {
  private static final long serialVersionUID = 1354384669678934166L;

  private String name;
  private TC2PlayerInterface playerInterface;

  public Player(String name, TC2PlayerInterface playerInterface) {
    this.name = name;
    this.playerInterface = playerInterface;
  }

  public String getName() {
    return name;
  }

  public TC2PlayerInterface getPlayerInterface() {
    return playerInterface;
  }
}
