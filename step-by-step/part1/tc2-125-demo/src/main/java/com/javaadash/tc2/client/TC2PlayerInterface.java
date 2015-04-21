package com.javaadash.tc2.client;

import java.io.IOException;
import java.io.Serializable;

import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.server.message.EndGameMessage;
import com.javaadash.tc2.server.message.StartGameMessage;
import com.javaadash.tc2.server.message.UpdateGameMessage;

public interface TC2PlayerInterface extends Serializable {
  /**
   * Setup the game to start
   */
  void startGame(StartGameMessage message) throws IOException, TC2CoreException;

  /**
   * Update cards location and settings
   */
  void updateGameStatus(UpdateGameMessage message) throws IOException, TC2CoreException;

  /**
   * Notify end of game
   */
  void endGame(EndGameMessage msg) throws IOException, TC2CoreException;
}
