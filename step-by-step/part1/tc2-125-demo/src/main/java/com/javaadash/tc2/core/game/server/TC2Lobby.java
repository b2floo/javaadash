package com.javaadash.tc2.core.game.server;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.javaadash.tc2.core.player.Player;

/**
 * The lobby for games. A player can either register or join a pending game.
 * 
 * @author b2floo
 *
 */
public class TC2Lobby {

  private Queue<Player> waitingPlayers = new LinkedBlockingQueue<Player>();

  /**
   * Start a game with a waiting player or register in queue
   * 
   * @param newPlayer The player that wants to start the game
   * @return An opponent if one is available
   */
  public Player joinOrWait(Player newPlayer) {
    Player waitingPlayer = waitingPlayers.poll();

    if (waitingPlayer == null) {
      waitingPlayers.offer(newPlayer);
    }

    return waitingPlayer;
  }

}
