package com.javaadash.tc2.core.player;

import java.io.Serializable;

public class Player implements Serializable {
  private static final long serialVersionUID = 1354384669678934166L;
  private String name;

  public Player(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
