package com.javaadash.tc2.core.interfaces.player;

import java.io.IOException;
import java.io.Serializable;

import com.javaadash.tc2.core.exceptions.TC2CoreException;
import com.javaadash.tc2.core.interfaces.message.EndGameMessage;
import com.javaadash.tc2.core.interfaces.message.StartGameMessage;
import com.javaadash.tc2.core.interfaces.message.UpdateGameMessage;

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
  void endGame(EndGameMessage msg);
}
