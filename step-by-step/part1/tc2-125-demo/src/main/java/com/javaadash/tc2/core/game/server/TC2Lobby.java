package com.javaadash.tc2.core.game.server;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.javaadash.tc2.core.player.Player;

public class TC2Lobby {

  private Queue<Player> waitingPlayers = new LinkedBlockingQueue<Player>();

  public Player joinOrWait(Player newPlayer) {
    Player waitingPlayer = waitingPlayers.poll();

    if (waitingPlayer == null) {
      waitingPlayers.offer(newPlayer);
    }

    return waitingPlayer;
  }

}
