package com.javaadash.tc2.core.game.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaadash.tc2.client.TC2PlayerInterface;
import com.javaadash.tc2.client.message.StartGameMessage;
import com.javaadash.tc2.core.player.Player;
import com.javaadash.tc2.server.TC2ServerInterface;
import com.javaadash.tc2.server.message.JoinGameMessage;

public class TC2Server implements TC2ServerInterface {
  private Logger log = LoggerFactory.getLogger(TC2Server.class);

  private TC2Lobby lobby = new TC2Lobby();

  @Override
  public void joinAnyGame(JoinGameMessage msg, TC2PlayerInterface playerInterface) {

    Player newPlayer = new Player(msg.getUsername(), playerInterface);
    Player opponent = lobby.joinOrWait(newPlayer);

    if (opponent != null) {
      try {
        newPlayer.getPlayerInterface().startGame(
            new StartGameMessage("Welcome, you start a game against " + opponent.getName()));
        opponent.getPlayerInterface().startGame(
            new StartGameMessage("Welcome, you start a game against " + newPlayer.getName()));
      } catch (IOException e) {
        log.error("Error while sending start message to players" + e.getMessage(), e);
      }
    }
  }

}
